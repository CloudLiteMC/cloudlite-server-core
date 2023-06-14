package com.burchard36.cloudlite.commands;

import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(
            @NonNull CommandSender sender,
            @NonNull Command command,
            @NonNull String label,
            String @NonNull [] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("lol dummy you cant upgrade anything");
            return false;
        }

        final Player player = (Player) sender;
        final ItemStack itemInMainHand = player.getInventory().getItemInMainHand();


        return false;
    }
}
