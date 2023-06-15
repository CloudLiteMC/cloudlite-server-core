package com.burchard36.cloudlite.commands;

import com.burchard36.cloudlite.AutoCompressorModule;
import com.burchard36.cloudlite.guis.ChooseCompressorTypeGui;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CompressCommand implements CommandExecutor {

    protected final AutoCompressorModule moduleInstance;

    public CompressCommand(final AutoCompressorModule moduleInstance) {
        this.moduleInstance = moduleInstance;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Whats with you always trying to use player commands in the console you stupid retarded fuck?");
            return false;
        }
        this.moduleInstance.getPluginInstance().getGuiManager().openInventoryTo((Player) sender, new ChooseCompressorTypeGui(this.moduleInstance));
        return false;
    }
}
