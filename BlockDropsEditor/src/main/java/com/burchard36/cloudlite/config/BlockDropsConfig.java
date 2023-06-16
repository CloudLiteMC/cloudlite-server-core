package com.burchard36.cloudlite.config;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class BlockDropsConfig implements Config {

    private final HashMap<Material, BlockDropData> blockDropData = new HashMap<>();
    @Override
    public @NonNull String getFileName() {
        return "blocks/block-drops.yml";
    }

    @Override
    public void deserialize(FileConfiguration configuration) {
        this.blockDropData.clear();
        for (final String materialName : configuration.getKeys(false)) {
            final ConfigurationSection config = configuration.getConfigurationSection(materialName);
            final Material material = Material.getMaterial(materialName);
            assert config != null;
            blockDropData.put(material, new BlockDropData(config));
        }
    }

    public final BlockDropData getDropData(final Material material) {
        return this.blockDropData.get(material);
    }
}
