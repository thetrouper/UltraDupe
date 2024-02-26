package io.github.thetrouper.ultradupe.cmds;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import org.bukkit.command.CommandSender;

@CommandRegistry(value = "ultradupe", permission = @Permission("ultradupe.dupe"), printStackTrace = true)
public class ChatClickCallback implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Args args) {

    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg());
    }
}
