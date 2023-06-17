package com.burchard36.cloudlite.config;

import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class DynamicConfig implements Config {

    protected String fileName;

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public @NonNull String getFileName() {
        return this.fileName;
    }

    @Override
    public void deserialize(FileConfiguration configuration) {

    }
}
