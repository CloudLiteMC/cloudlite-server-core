package com.burchard36.cloudlite.config;

import lombok.Getter;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class MMOItemLevelUpData {
    @Getter
    protected final String nextMMOItem;
    @Getter
    protected final String mmoItemType;
    @Getter
    protected final int experienceLevelCost;
    @Getter
    protected final List<ItemStack> upgradeItemCosts;

    public MMOItemLevelUpData(final ConfigurationSection config) {
        this.upgradeItemCosts = new ArrayList<>();
        this.nextMMOItem = config.getString("Next");
        this.mmoItemType = config.getString("Type");
        if (config.isInt("Cost.Experience")) {
            this.experienceLevelCost = config.getInt("Cost.Experience");
        } else experienceLevelCost = -1;

        final ConfigurationSection itemCostSection = config.getConfigurationSection("Cost.Items");

        assert itemCostSection != null;
        for (String key : itemCostSection.getKeys(false)) {
            final ConfigurationSection item = itemCostSection.getConfigurationSection(key);
            assert item != null;
            this.upgradeItemCosts.add(new ConfigItem(item).getItem());
        }
    }

    public boolean canAfford(final Player player) {
        if (player.getLevel() < this.experienceLevelCost) return false;
        final Inventory inventory = player.getInventory();
        for (final ItemStack item : this.upgradeItemCosts) {
            if (!inventory.containsAtLeast(item, item.getAmount())) return false;
        }
        return true;
    }

    public void removeCosts(final Player player) {
        final Inventory inventory = player.getInventory();
        for (final ItemStack item : this.upgradeItemCosts) {
            inventory.removeItem(item);
        }

        player.setLevel(player.getLevel() - this.experienceLevelCost);
    }

    public void giveUpgrade(final Player player) {
        final PlayerInventory inventory = player.getInventory();
        inventory.setItemInMainHand(MMOItems.plugin.getItem(this.mmoItemType, this.nextMMOItem));
    }

}
