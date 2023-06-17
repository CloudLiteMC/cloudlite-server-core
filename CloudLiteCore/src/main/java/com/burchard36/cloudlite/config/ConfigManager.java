package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.utils.StringUtils;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private final JavaPlugin pluginInstance;
    private final List<Config> configurationFiles;

    public ConfigManager(final JavaPlugin pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.configurationFiles = new ArrayList<>();
    }

    @SneakyThrows
    public <T extends Config> T getConfig(final T config) {
        if (this.configurationFiles.contains(config)) throw new IllegalStateException("Config %s is already registered".formatted(config.getClass().getName()));
        final File configFile = new File(this.pluginInstance.getDataFolder(), config.getFileName());
        if (!configFile.exists()) {
            this.pluginInstance.saveResource(config.getFileName(), false);
            Bukkit.getLogger().info(StringUtils.convert("&bCreating new config file&f%s&b...").formatted(config.getFileName()));
            return this.getConfig(config);
        }

        final FileConfiguration configData = YamlConfiguration.loadConfiguration(configFile);
        config.deserialize(configData);
        this.configurationFiles.add(config);
        return config;
    }

    public <T extends DynamicConfig> List<T> getRecursiveConfig(
            final String fromDirectory,
            final T config,
            @Nullable final List<T> modify) {
        final File[] dir = new File(this.pluginInstance.getDataFolder(), fromDirectory).listFiles();
        if (dir == null || dir.length == 0) {
            if (modify != null) return modify;
            return List.of();
        }
        List<T> configs = new ArrayList<>();
        if (modify != null) configs = modify;

        for (final File file : dir) {
            if (file.isFile()) {
                final String fileName = fromDirectory + "/" +  file.getName();
                config.setFileName(fileName);
                configs.add(this.getConfig(config));
                continue;
            }

            if (file.isDirectory()) {
                return this.getRecursiveConfig(fromDirectory + "/" + file.getName(), config, configs);
            }
        }
        return List.of();
    }

    public void reloadAll() {
        this.configurationFiles.forEach(config -> {
            final File configFile = new File(this.pluginInstance.getDataFolder(), config.getFileName());
            final FileConfiguration configData = YamlConfiguration.loadConfiguration(configFile);
            config.deserialize(configData);
        });
    }

}
