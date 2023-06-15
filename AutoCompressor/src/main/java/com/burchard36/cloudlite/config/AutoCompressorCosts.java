package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.AutoCompressorModule;
import com.burchard36.cloudlite.CompressorPlayer;
import com.burchard36.cloudlite.utils.ItemUtils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AutoCompressorCosts {

    @Getter
    protected final String materialType;
    @Getter
    protected final int materialCost;
    @Getter
    protected final int levelCost;

    public AutoCompressorCosts(final ConfigurationSection config) {
        this.materialType = config.getString("Item.Material");
        this.materialCost = config.getInt("Item.Amount");
        this.levelCost = config.getInt("Experience.Level");
    }

    public ItemStack getItem(final AutoCompressorModule moduleInstance) {
        if (materialType.startsWith("COMPRESSED") || materialType.startsWith("SUPER") || materialType.startsWith("MEGA")) {
            final ItemStack item = moduleInstance.getAutoCompressorConfig().fromString(materialType);
            item.setAmount(this.materialCost);
            return item;
        } else return ItemUtils.createItemStack(this.materialType, this.materialCost, null, (String) null);
    }

    public boolean canAfford(final CompressorPlayer compressorPlayer, final AutoCompressorModule moduleInstance) {
        final Player player = compressorPlayer.getPlayer();
        if (player.getLevel() < this.levelCost) return false;
        final Inventory inventory = player.getInventory();
        return inventory.containsAtLeast(this.getItem(moduleInstance), this.materialCost);
    }

    public void removeCosts(final CompressorPlayer compressorPlayer, final AutoCompressorModule moduleInstance) {
        compressorPlayer.getPlayer().getInventory().removeItem(this.getItem(moduleInstance));
        compressorPlayer.getPlayer().getInventory().remove(this.getItem(moduleInstance));
        compressorPlayer.getPlayer().setLevel(compressorPlayer.getPlayer().getLevel() - this.levelCost);
    }

}
