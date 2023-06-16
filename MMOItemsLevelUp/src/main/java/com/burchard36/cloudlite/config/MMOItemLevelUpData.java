package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.AutoCompressorModule;
import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.utils.ItemUtils;
import lombok.Getter;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MMOItemLevelUpData {
    @Getter
    protected final String nextMMOItem;
    @Getter
    protected final String mmoItemType;
    @Getter
    protected final int experienceLevelCost;

    protected final String upgradeMaterialName;
    @Getter
    protected final int upgradeMaterialAmount;
    protected ItemStack cachedCostItem;

    public MMOItemLevelUpData(final ConfigurationSection config) {
        this.nextMMOItem = config.getString("Next");
        this.mmoItemType = config.getString("Type");
        this.experienceLevelCost = config.getInt("Cost.Levels");
        this.upgradeMaterialName = config.getString("Cost.Item.Material");
        this.upgradeMaterialAmount = config.getInt("Cost.Item.Amount");
    }

    public ItemStack getUpgradeItem() {
        return MMOItems.plugin.getItem(this.mmoItemType, this.nextMMOItem);
    }

    public ItemStack getCostItem() {
        if (this.cachedCostItem != null) return this.cachedCostItem;
        final AutoCompressorModule compressorModule = (AutoCompressorModule) CloudLiteCore.INSTANCE.getModuleLoader().getModule(AutoCompressorModule.class);

        final ItemStack compressedItem = compressorModule.getAutoCompressorConfig().fromString(this.upgradeMaterialName);
        if (compressedItem != null) {
            compressedItem.setAmount(this.upgradeMaterialAmount);
            this.cachedCostItem = compressedItem;
        } else this.cachedCostItem = ItemUtils.createItemStack(this.upgradeMaterialName, this.upgradeMaterialAmount, null, null);
        return this.cachedCostItem;
    }

    public boolean canAfford(final Player player) {
        if (player.getLevel() < this.experienceLevelCost) return false;
        final Inventory inventory = player.getInventory();
        return inventory.containsAtLeast(this.getCostItem(), this.upgradeMaterialAmount);
    }

    public void removeCosts(final Player player) {
        final Inventory inventory = player.getInventory();
        inventory.removeItem(this.getCostItem());
        player.setLevel(player.getLevel() - this.experienceLevelCost);
    }

    public void giveUpgrade(final Player player) {
        final PlayerInventory inventory = player.getInventory();
        inventory.setItemInMainHand(MMOItems.plugin.getItem(this.mmoItemType, this.nextMMOItem));
    }

}
