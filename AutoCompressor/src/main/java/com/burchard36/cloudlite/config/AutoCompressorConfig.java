package com.burchard36.cloudlite.config;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AutoCompressorConfig implements Config {

    private final HashMap<Material, AutoCompressorMaterial> compressedMaterials = new HashMap<>();

    @Override
    public @NonNull String getFileName() {
        return "compressor/config.yml";
    }

    @Override
    public void deserialize(FileConfiguration configuration) {
        for (final String materialName : configuration.getKeys(false)) {
            final AutoCompressorMaterial compressorMaterial = new AutoCompressorMaterial(configuration.getConfigurationSection(materialName));
            this.compressedMaterials.put(Material.getMaterial(materialName), compressorMaterial);
        }
    }

    public final boolean isCompressable(final ItemStack itemStack) {
        return this.isCompressable(itemStack.getType());
    }

    public final boolean isCompressable(final Material material) {
        return this.compressedMaterials.get(material) != null;
    }

    public ItemStack getCompressed(final Material material) {
        if (this.compressedMaterials.get(material) == null) return null;
        return this.compressedMaterials.get(material).getCompressedItem();
    }

    public ItemStack getSuperCompressed(final Material material) {
        if (this.compressedMaterials.get(material) == null) return null;
        return this.compressedMaterials.get(material).getSuperCompressedItem();
    }

    public ItemStack getMegaCompressed(final Material material) {
        if (this.compressedMaterials.get(material) == null) return null;
        return this.compressedMaterials.get(material).getMegaCompressedItem();
    }
}
