package com.burchard36.cloudlite.config;

import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;

public interface Config {

    @NonNull
    String getFileName();


    void deserialize(FileConfiguration configuration);
}
