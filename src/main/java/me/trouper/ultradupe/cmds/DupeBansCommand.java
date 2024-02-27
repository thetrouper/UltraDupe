package me.trouper.ultradupe.cmds;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.ultradupe.UltraDupe;
import me.trouper.ultradupe.server.util.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandRegistry(value = "dupebans", permission = @Permission("ultradupe.dupebans"), printStackTrace = true)
public class DupeBansCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;
        handleListBans(p);

    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("item")
                .then(b.arg("add","remove"))
        .then(b.arg("material")
                .then(b.arg("add","remove"))));
    }

    protected static void handleListBans(Player p) {
        StringBuilder dupeBanList = new StringBuilder(Text.prefix("&7There are currently &e%s&7 dupe bans.".formatted(UltraDupe.dupeBanStorage.bannedMaterials.size())));
        for (Material dupeBan : UltraDupe.dupeBanStorage
                .bannedMaterials) {
            dupeBanList.append(Global.instance.color("\n&6 - &c%s".formatted(Text.cleanName(dupeBan.toString()))));
        }
        p.sendMessage(dupeBanList.toString());
    }
}
