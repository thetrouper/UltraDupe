package me.trouper.ultradupe.cmds;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.github.itzispyder.pdk.utils.SchedulerUtils;
import me.trouper.ultradupe.UltraDupe;
import me.trouper.ultradupe.server.functions.DupeBanCheck;
import me.trouper.ultradupe.server.sound.SoundPlayer;
import me.trouper.ultradupe.server.util.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CommandRegistry(value = "ultradupe", permission = @Permission("ultradupe.admin"), printStackTrace = true)
public class UltraDupeCommand implements CustomCommand {

    public static List<UUID> bypassingBans = new ArrayList<>();

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;

        switch (args.get(0).toString()) {
            case "dupebans" -> {
                switch (args.get(1).toString()) {
                    case "item" -> handleItemEdit(p,args);
                    case "material" -> handleMaterialEdit(p,args);
                    default -> DupeBansCommand.handleListBans(p);
                }
            }
            case "toggle" -> {
                switch (args.get(1).toString()) {
                    case "debug" -> {
                        UltraDupe.config.debugMode = !UltraDupe.config.debugMode;
                        UltraDupe.config.save();
                    }
                    case "bypass" -> {
                        if (!bypassingBans.contains(p.getUniqueId())) {
                            bypassingBans.add(p.getUniqueId());
                        } else {
                            bypassingBans.remove(p.getUniqueId());
                        }
                        p.sendMessage(Component.text(Text.prefix("&7You are now %s dupe bans".formatted(bypassingBans.contains(p.getUniqueId()) ? "&aBypassing&7" : "&cObeying&7"))));
                    }
                }
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("dupebans")
                .then(b.arg("item")
                        .then(b.arg("add","remove")))
                .then(b.arg("material")
                        .then(b.arg("add", "remove")))
        ).then(b.arg("toggle")
                .then(b.arg("debug","bypass")));
    }

    private void handleItemEdit(Player p, Args args) {
        if (!p.hasPermission("ultradupe.dupebans.edit")) {
            DupeBansCommand.handleListBans(p);
            return;
        }
        switch (args.get(2).toString()) {
            case "add" -> {
                ItemStack i = p.getInventory().getItemInMainHand().clone();
                if (UltraDupe.dupeBanStorage.bannedItems.contains(i)) {
                    p.sendMessage(Component.text(Text.prefix("&7The material &c%s&7 is already on the dupe ban list.".formatted(Text.cleanName(i.getType().toString())))));
                    return;
                }
                UltraDupe.dupeBanStorage.bannedItems.add(i);
                p.sendMessage(Component.text(Text.prefix("&7You have added &c%s&7 to the dupe ban list.".formatted(Text.cleanName(i.getType().toString())))));
                UltraDupe.dupeBanStorage.save();
            }
            case "remove" -> {
                ItemStack i = p.getInventory().getItemInMainHand().clone();
                if (!UltraDupe.dupeBanStorage.bannedItems.contains(i)) {
                    p.sendMessage(Component.text(Text.prefix("&7The material &c%s&7 is not on the dupe ban list.".formatted(Text.cleanName(i.getType().toString())))));
                    return;
                }
                UltraDupe.dupeBanStorage.bannedItems.remove(i);
                p.sendMessage(Component.text(Text.prefix("&7You have removed &a%s&7 from the dupe ban list.".formatted(Text.cleanName(i.getType().toString())))));
                UltraDupe.dupeBanStorage.save();
            }
        }
    }

    private void handleMaterialEdit(Player p, Args args) {
        if (!p.hasPermission("ultradupe.dupebans.edit")) {
            DupeBansCommand.handleListBans(p);
            return;
        }
        switch (args.get(2).toString()) {
            case "add" -> {
                Material m = p.getInventory().getItemInMainHand().getType();
                if (UltraDupe.dupeBanStorage.bannedMaterials.contains(m)) {
                    p.sendMessage(Component.text(Text.prefix("&7The material &c%s&7 is already on the dupe ban list.".formatted(Text.cleanName(m.toString())))));
                    return;
                }
                p.sendMessage(Component.text(Text.prefix("&7Adding &c%s&7 to the dupe ban list.".formatted(Text.cleanName(m.toString())))));
                UltraDupe.dupeBanStorage.bannedMaterials.add(m);

                UltraDupe.dupeBanStorage.save();
            }
            case "remove" -> {
                Material m = p.getInventory().getItemInMainHand().getType();
                if (!UltraDupe.dupeBanStorage.bannedMaterials.contains(m)) {
                    p.sendMessage(Component.text(Text.prefix("&7The material &c%s&7 is not on the dupe ban list.".formatted(Text.cleanName(m.toString())))));
                    return;
                }
                p.sendMessage(Component.text(Text.prefix("&7Removing&a%s&7 from the dupe ban list.".formatted(Text.cleanName(m.toString())))));
                UltraDupe.dupeBanStorage.bannedMaterials.remove(m);
                UltraDupe.dupeBanStorage.save();
            }
        }
    }
}
