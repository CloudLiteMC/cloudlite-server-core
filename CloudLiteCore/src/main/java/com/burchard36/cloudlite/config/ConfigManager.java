package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.utils.StringUtils;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
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
            final Class<? extends T> config,
            @Nullable final List<T> modify
    ) {

        final File directoryFile = new File(this.pluginInstance.getDataFolder(), fromDirectory);
        final File[] dir = directoryFile.listFiles();
        assert dir != null;
        Bukkit.getLogger().info("Loading %s directories!".formatted(dir.length));
        List<T> configs = new ArrayList<>();
        if (modify != null) configs = modify;

        for (final File file : dir) {
            try {
                BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                if (basicFileAttributes.isRegularFile()) {
                    Bukkit.getLogger().info("Loading regular file %s".formatted(fromDirectory + "/" + file.getName()));
                    final String fileName = fromDirectory + "/" + file.getName();
                    final T configInstanceCreate = config.getDeclaredConstructor().newInstance();
                    configInstanceCreate.setFileName(fileName);
                    configs.add(this.getConfig(configInstanceCreate));
                    continue;
                }

                if (basicFileAttributes.isDirectory()) {
                    Bukkit.getLogger().info("Loading directory recursively %s".formatted(fromDirectory + "/" + file.getName()));
                    configs.addAll(this.getRecursiveConfig(fromDirectory + "/" + file.getName(), config, configs));
                }
            } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return configs;
    }

    public void reloadAll() {
        this.configurationFiles.forEach(config -> {
            final File configFile = new File(this.pluginInstance.getDataFolder(), config.getFileName());
            final FileConfiguration configData = YamlConfiguration.loadConfiguration(configFile);
            config.deserialize(configData);
        });
    }

}
