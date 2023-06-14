package com.burchard36.cloudlite.commands;

import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.CloudLiteMusicPlayer;
import com.burchard36.cloudlite.gui.SongListGui;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MusicGuiCommand implements CommandExecutor {

    protected final CloudLiteMusicPlayer moduleInstance;

    public MusicGuiCommand(final CloudLiteMusicPlayer moduleInstance) {
        this.moduleInstance = moduleInstance;
    }

    @Override
    public boolean onCommand(
            @NonNull CommandSender sender,
            @NonNull Command command,
            @NonNull String label,
            String @NonNull [] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("FUCK OFF CONSOLE YOU CANT OPEN GUIS");
            return false;
        }

        CloudLiteCore.getGuiManager().openPaginatedTo((Player) sender, 0, new SongListGui(this.moduleInstance));
        return false;
    }
}
