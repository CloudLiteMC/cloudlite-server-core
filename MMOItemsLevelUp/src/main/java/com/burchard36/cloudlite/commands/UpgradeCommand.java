package com.burchard36.cloudlite.commands;

import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.MMOItemsLevelUp;
import com.burchard36.cloudlite.config.MMOItemLevelUpData;
import com.burchard36.cloudlite.gui.UpgradeItemGui;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class UpgradeCommand implements CommandExecutor {

    protected final MMOItemsLevelUp moduleInstance;

    public UpgradeCommand(final MMOItemsLevelUp moduleInstance) {
        this.moduleInstance = moduleInstance;
    }

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
        final ItemStack itemInMainHand = player.getInventory().getItemInMainHand().clone();
        final boolean hasUpgrade = this.moduleInstance.getLevelUpConfig().hasUpgrade(itemInMainHand);
        if (!hasUpgrade) {
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
            player.sendMessage(convert("&cThere is no more upgrades for this item!"));
            return false;
        }

        final MMOItemLevelUpData levelUpData = this.moduleInstance.getLevelUpConfig().getUpgrade(itemInMainHand);
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        CloudLiteCore.INSTANCE.getGuiManager().openInventoryTo(player, new UpgradeItemGui(itemInMainHand, levelUpData));
        return false;
    }
}
