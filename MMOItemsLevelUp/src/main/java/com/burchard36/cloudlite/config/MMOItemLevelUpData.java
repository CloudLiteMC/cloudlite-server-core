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

import java.util.*;

public class MMOItemLevelUpData {
    @Getter
    protected final String nextMMOItem;
    @Getter
    protected final String mmoItemType;
    @Getter
    protected final int experienceLevelCost;

    protected final List<Map.Entry<String, Integer>> upgradeMaterialAndAmountSets;
    protected List<ItemStack> cachedCostItems;

    public MMOItemLevelUpData(final ConfigurationSection config) {
        this.upgradeMaterialAndAmountSets = new ArrayList<>();
        this.cachedCostItems = new ArrayList<>();
        this.nextMMOItem = config.getString("Next");
        this.mmoItemType = config.getString("Type");
        this.experienceLevelCost = config.getInt("Cost.Levels");

        ConfigurationSection itemsSection = config.getConfigurationSection("Cost.Items");

        assert itemsSection != null;
        for (String key : itemsSection.getKeys(false)) {
            final ConfigurationSection itemData = itemsSection.getConfigurationSection(key);
            upgradeMaterialAndAmountSets.add(new Map.Entry<>() {
                @Override
                public String getKey() {
                    return itemData.getString("Material");
                }
                @Override
                public Integer getValue() {
                    return itemData.getInt("Amount");
                }
                @Override
                public Integer setValue(Integer value) {
                    return null;
                }
            });
        }
    }

    public ItemStack getUpgradeItem() {
        return MMOItems.plugin.getItem(this.mmoItemType, this.nextMMOItem);
    }

    public List<ItemStack> getCostItems() {
        if (!this.cachedCostItems.isEmpty()) return this.cachedCostItems;
        final AutoCompressorModule compressorModule =
                (AutoCompressorModule) CloudLiteCore.INSTANCE.getModuleLoader().getModule(AutoCompressorModule.class);

        for (Map.Entry<String, Integer> itemStackData : this.upgradeMaterialAndAmountSets) {
            ItemStack itemStack = compressorModule.getAutoCompressorConfig().fromString(itemStackData.getKey());
            if (itemStack == null) {
                itemStack = ItemUtils.createItemStack(itemStackData.getKey(), itemStackData.getValue(), null, null);
            } else itemStack.setAmount(itemStackData.getValue());
            this.cachedCostItems.add(itemStack);
        }

        return this.cachedCostItems;
    }

    public boolean canAfford(final Player player) {
        if (player.getLevel() < this.experienceLevelCost) return false;
        final Inventory inventory = player.getInventory();
        for (ItemStack item : this.getCostItems()) {
            if (!inventory.containsAtLeast(item, item.getAmount())) return false;
        }
        return true;
    }

    public void removeCosts(final Player player) {
        final Inventory inventory = player.getInventory();
        this.getCostItems().forEach(inventory::removeItem);
        player.setLevel(player.getLevel() - this.experienceLevelCost);
    }

    public void giveUpgrade(final Player player) {
        final PlayerInventory inventory = player.getInventory();
        inventory.setItemInMainHand(MMOItems.plugin.getItem(this.mmoItemType, this.nextMMOItem));
    }

}
