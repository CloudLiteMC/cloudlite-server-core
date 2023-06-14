package com.burchard36.cloudlite.config;

import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class MMOItemLevelUpConfig implements Config {
    @Override
    public @NonNull String getFileName() {
        return "mmo-levelup/levels.yml";
    }

    @Override
    public void deserialize(FileConfiguration configuration) {
        final ConfigurationSection config = configuration.getConfigurationSection("MMOItemUpgrades");

        assert config != null;
        for (String startingMMOItem : config.getKeys(false)) {

        }
    }
}
