package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.utils.ItemUtils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class AutoCompressorMaterial {

    private ItemStack compressedItem;
    private ItemStack superCompressedItem;
    private ItemStack megaCompressedItem;

    @Getter
    private String compressedMaterialPermission;
    @Getter
    private String superCompressedMaterialPermission;
    @Getter
    private String megaCompressedMaterialPermission;

    public AutoCompressorMaterial(final ConfigurationSection config) {
        this.compressedItem = this.getItemStack(config.getConfigurationSection("Compressor"));
        this.superCompressedItem = this.getItemStack(config.getConfigurationSection("SuperCompressor"));
        this.megaCompressedItem = this.getItemStack(config.getConfigurationSection("MegaCompressor"));

        this.compressedMaterialPermission = config.getString("Compressor.Permission");
        this.superCompressedMaterialPermission = config.getString("SuperCompressor.Permission");
        this.megaCompressedMaterialPermission = config.getString("MegaCompressor.Permission");
    }

    private ItemStack getItemStack(final ConfigurationSection config) {
        final String skullTexture = config.getString("HeadTexture");
        final String displayName = config.getString("DisplayName");
        return ItemUtils.createSkull(skullTexture, displayName, null);
    }

    public ItemStack getCompressedItem() {
        return this.compressedItem.clone();
    }

    public ItemStack getSuperCompressedItem() {
        return this.superCompressedItem.clone();
    }

    public ItemStack getMegaCompressedItem() {
        return this.megaCompressedItem.clone();
    }

}
