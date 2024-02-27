package io.github.thetrouper.ultradupe.cmds;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.github.thetrouper.ultradupe.UltraDupe;
import io.github.thetrouper.ultradupe.data.DupeBanStorage;
import io.github.thetrouper.ultradupe.data.GUIs.DupeBanGUI;
import io.github.thetrouper.ultradupe.server.functions.DupeBanCheck;
import io.github.thetrouper.ultradupe.server.sound.SoundPlayer;
import io.github.thetrouper.ultradupe.server.util.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandRegistry(value = "dupebans", permission = @Permission("ultradupe.dupebans"), printStackTrace = true)
public class DupeBansCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;
        switch (args.get(0).toString()) {
            case "add" -> {
                if (!p.hasPermission("ultradupe.dupebans.edit")) return;
                Material m = p.getInventory().getItemInMainHand().getType();
                if (UltraDupe.dupeBanStorage.dupeBans.contains(m)) {
                    p.sendMessage(Component.text(Text.prefix("&7The material &c%s&7 is already on the dupe ban listr.".formatted(Text.cleanName(m.toString())))));
                    return;
                }
                UltraDupe.dupeBanStorage.dupeBans.add(m);
                p.sendMessage(Component.text(Text.prefix("&7You have added &c%s&7 to the dupe ban list.".formatted(Text.cleanName(m.toString())))));
                UltraDupe.dupeBanStorage.save();
            }
            case "remove" -> {
                if (!p.hasPermission("ultradupe.dupebans.edit")) return;
                Material m = p.getInventory().getItemInMainHand().getType();
                if (!UltraDupe.dupeBanStorage.dupeBans.contains(m)) {
                    p.sendMessage(Component.text(Text.prefix("&7The material &c%s&7 is not on the dupe ban list.".formatted(Text.cleanName(m.toString())))));
                    return;
                }
                UltraDupe.dupeBanStorage.dupeBans.remove(m);
                p.sendMessage(Component.text(Text.prefix("&7You have removed &a%s&7 from the dupe ban list.".formatted(Text.cleanName(m.toString())))));
                UltraDupe.dupeBanStorage.save();
            }
            default -> {
                StringBuilder dupeBanList = new StringBuilder(Text.prefix("&7There are currently &e%s&7 dupe bans.".formatted(UltraDupe.dupeBanStorage.dupeBans.size())));
                for (Material dupeBan : UltraDupe.dupeBanStorage
                        .dupeBans) {
                    dupeBanList.append(Global.instance.color("\n&6 - &c%s".formatted(Text.cleanName(dupeBan.toString()))));
                }
                p.sendMessage(dupeBanList.toString());
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("add","remove"));
    }
}
