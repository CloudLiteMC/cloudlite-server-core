package com.burchard36.cloudlite.config;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlockDropData {

    @Getter
    protected final Material dropMaterial;
    @Getter
    protected final int dropAmount;
    @Getter
    protected final int dropExperience;
    protected final String requiresType;

    public BlockDropData(ConfigurationSection config) {
        this.dropAmount = config.getInt("Amount");
        this.dropMaterial = Material.valueOf(config.getString("DropMaterial"));
        this.dropExperience = config.getInt("Experience");
        this.requiresType = config.getString("FortuneToolType");
    }

    public boolean hasExperienceDrops() {
        return this.dropExperience > 0;
    }

    public final List<ItemStack> createDrops(ItemStack toolUsed) {
        final List<ItemStack> items = new ArrayList<>();
        int totalAmount = this.getDropAmount();
        if (this.canUseFortune(toolUsed)) totalAmount = this.getDropAmountWithFortune(toolUsed);
        int remainder = totalAmount % 64;
        int totalStacks = totalAmount / 64;
        if (totalStacks > 1) {
            for (int x = 0; x < totalStacks; x++) {
                items.add(new ItemStack(this.dropMaterial, 64));
            }
        }
        items.add(new ItemStack(this.dropMaterial, remainder));
        return items;
    }

    public boolean canUseFortune(ItemStack itemStack) {
        final String itemStackNameMaterial = itemStack.getType().name();
        if (this.requiresType.equals("HOE")
                || this.requiresType.equals("SHOVEL")
                || this.requiresType.equals("AXE")
                || this.requiresType.equals("PICKAXE"))
            return itemStackNameMaterial.endsWith("_" + this.requiresType);
        return itemStackNameMaterial.endsWith(this.requiresType);
    }

    public final int getDropAmountWithFortune(ItemStack itemStack) {
        return this.getDropAmount() * this.getDropCount(itemStack);
    }

    private int getDropCount(ItemStack is) {
        int i = is.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        int j = ThreadLocalRandom.current().nextInt(i + 2) - 1;
        if (j < 0) {
            j = 0;
        }

        return j + 1;

    }

}
