package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.MMOItemsLevelUp;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ConfigItem {

    protected final Material material;
    protected final int amount;
    protected ItemStack compressedItem;
    protected ItemStack superCompressedItem;
    protected ItemStack megaCompressedItem;

    public ConfigItem(final ConfigurationSection config) {
        final AutoCompressorConfig compressorConfig = MMOItemsLevelUp.getCompressorConfig();
        if (config.isString("Material")) {
            final String material = config.getString("Material");
            assert material != null;
            if (material.startsWith("SUPER_COMPRESSED_")) {
                Material superCompressedMaterial = Material.valueOf(material.replace("SUPER_COMPRESSED_", ""));

                this.superCompressedItem = compressorConfig.getSuperCompressed(superCompressedMaterial);
            } else this.superCompressedItem = null;

            if (material.startsWith("COMPRESSED_")) {
                Material compressedMaterial = Material.valueOf(material.replace("COMPRESSED_", ""));
                this.compressedItem = compressorConfig.getCompressed(compressedMaterial);
            } else this.compressedItem = null;

            if (material.startsWith("MEGA_COMPRESSED_")) {
                Material compressedMaterial = Material.valueOf(material.replace("MEGA_COMPRESSED_", ""));
                this.megaCompressedItem = compressorConfig.getMegaCompressed(compressedMaterial);
            } else this.megaCompressedItem = null;

            this.material = Material.valueOf(config.getString("Material"));
        } else throw new IllegalArgumentException("Material may not be null!");


        if (config.isInt("Amount")) {
            this.amount = config.getInt("Amount");
        } else this.amount = 1;

        if (this.compressedItem != null) {
            this.compressedItem.setAmount(this.amount);
        }

        if (this.superCompressedItem != null) {
            this.superCompressedItem.setAmount(this.amount);
        }

        if (this.megaCompressedItem != null) {
            this.megaCompressedItem.setAmount(this.amount);
        }
    }

    public ItemStack getItem() {
        if (this.compressedItem != null) return this.compressedItem;
        if (this.superCompressedItem != null) return this.superCompressedItem;
        if (this.megaCompressedItem != null) return this.megaCompressedItem;
        return new ItemStack(material, amount);
    }

}
