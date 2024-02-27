package io.github.thetrouper.ultradupe.cmds;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.github.itzispyder.pdk.utils.SchedulerUtils;
import io.github.thetrouper.ultradupe.server.functions.DupeBanCheck;
import io.github.thetrouper.ultradupe.server.sound.SoundPlayer;
import io.github.thetrouper.ultradupe.server.util.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

@CommandRegistry(value = "ultradupe", permission = @Permission("ultradupe.dupe"), printStackTrace = true)
public class DupeCommand implements CustomCommand {

    DupeBanCheck check = new DupeBanCheck();
    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;
        SoundPlayer yes = new SoundPlayer(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10,1);
        SoundPlayer no = new SoundPlayer(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10,1);

        if (p.getInventory().getItemInMainHand().isEmpty()) {
            p.sendMessage(Component.text(Text.prefix("That was air... it was duped, but you still didn't get anything.")));
            no.play(p);
            return;
        }
        int factor = args.get(0).toInt();
        ItemStack i = p.getInventory().getItemInMainHand().clone();

        if (check.itemPasses(i)) {
            if (factor == 0) factor = i.getAmount();
            SchedulerUtils.loop(1,factor,(time)->{
                i.setAmount(1);
                yes.play(p);
                p.getInventory().addItem(i);
            });
            p.sendMessage(Component.text(Text.prefix("&7You have duped &a%s %s&7.".formatted(factor, Text.cleanName(i.getType().toString())))));
        } else {
            p.sendMessage(Component.text(Text.prefix("&cYou are not allowed to dupe &7%s&c.".formatted(Text.cleanName(i.getType().toString())))));
            no.play(p);
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("[<INT>]"));
    }
}
