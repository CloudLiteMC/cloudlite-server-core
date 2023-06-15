package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.AutoCompressor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;

public class AutoCompressorConfig implements Config {

    @Getter
    private final HashMap<Material, AutoCompressorMaterial> compressedMaterials = new HashMap<>();

    @Override
    public @NonNull String getFileName() {
        return "compressor/config.yml";
    }

    @Override
    public void deserialize(FileConfiguration configuration) {
        this.compressedMaterials.clear();
        for (final String materialName : configuration.getKeys(false)) {
            final Material configMaterial = Material.getMaterial(materialName);
            final ConfigurationSection compressedMaterialSection = configuration.getConfigurationSection(materialName);
            assert compressedMaterialSection != null;
            final AutoCompressorMaterial compressorMaterial = new AutoCompressorMaterial(compressedMaterialSection, configMaterial);
            this.compressedMaterials.put(configMaterial, compressorMaterial);
        }
    }

    @SuppressWarnings("unused")
    public final boolean isCompressable(final ItemStack itemStack) {
        return this.isCompressable(itemStack.getType());
    }

    public final boolean isCompressable(final Material material) {
        return this.compressedMaterials.get(material) != null;
    }

    /**
     * Used to get what the origin material is, if the item is not compress return null
     * @param itemStack ItemStack to check
     * @return null if the item is not some type of compressedItem
     */
    public ItemStack fromItem(final ItemStack itemStack) {
        final PersistentDataContainer dataContainer = Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer();
        for (final AutoCompressorMaterial materialData : this.compressedMaterials.values()) {
            if (dataContainer.has(materialData.getCompressedKey(), PersistentDataType.BYTE)) return materialData.getCompressedItem();
            if (dataContainer.has(materialData.getSuperCompressedKey(), PersistentDataType.BYTE)) return materialData.getSuperCompressedItem();
            if (dataContainer.has(materialData.getMegaCompressedKey(), PersistentDataType.BYTE)) return materialData.getMegaCompressedItem();
        }
        return null;
    }

    public ItemStack fromString(final String compressedMaterialName) {
        for (final AutoCompressorMaterial materialData : this.compressedMaterials.values()) {
            if (materialData.getCompressedKey().getKey().equalsIgnoreCase(compressedMaterialName)) return materialData.getCompressedItem();
            if (materialData.getSuperCompressedKey().getKey().equalsIgnoreCase(compressedMaterialName)) return materialData.getSuperCompressedItem();
            if (materialData.getMegaCompressedKey().getKey().equalsIgnoreCase(compressedMaterialName)) return materialData.getMegaCompressedItem();
        }
        return null;
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

    public @Nullable AutoCompressorMaterial getCompressMaterialData(final Material material) {
        return this.compressedMaterials.get(material);
    }

    public @Nullable AutoCompressorMaterial getCompressMaterialData(final ItemStack itemStack) {
        final PersistentDataContainer dataContainer = Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer();
        for (AutoCompressorMaterial material : this.compressedMaterials.values()) {
            if (dataContainer.has(material.getCompressedKey(), PersistentDataType.BYTE)) return material;
            if (dataContainer.has(material.getSuperCompressedKey(), PersistentDataType.BYTE)) return material;
            if (dataContainer.has(material.getMegaCompressedKey(), PersistentDataType.BYTE)) return material;
        }
        return null;
    }

    public @Nullable AutoCompressorMaterial getCompressMaterialData(final String compressedKeyName) {
        for (AutoCompressorMaterial material : this.compressedMaterials.values()) {
            if (material.getCompressedKey().getKey().equalsIgnoreCase(compressedKeyName)) return material;
            if (material.getSuperCompressedKey().getKey().equalsIgnoreCase(compressedKeyName)) return material;
            if (material.getMegaCompressedKey().getKey().equalsIgnoreCase(compressedKeyName)) return material;
        }
        return null;
    }

}
