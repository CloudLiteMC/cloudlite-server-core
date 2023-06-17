package com.burchard36.cloudlite.config;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class MMOItemRecursiveConfig extends DynamicConfig {

    @Getter
    private final HashMap<String, MMOItemLevelUpData> mmoItemLevelUps = new HashMap<>();


    @Override
    public void deserialize(FileConfiguration configuration) {
        this.mmoItemLevelUps.clear();
        for (String startingMMOItem : configuration.getKeys(false)) {
            final ConfigurationSection section = configuration.getConfigurationSection(startingMMOItem);
            assert section != null;
            this.mmoItemLevelUps.put(startingMMOItem, new MMOItemLevelUpData(section));
        }
    }
}
