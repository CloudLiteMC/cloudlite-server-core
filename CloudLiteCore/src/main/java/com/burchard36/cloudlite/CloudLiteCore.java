package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.ConfigManager;
import com.burchard36.cloudlite.module.ModuleLoader;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CloudLiteCore extends JavaPlugin {

    private static CloudLiteCore INSTANCE;
    @Getter
    private ConfigManager configManager;
    @Getter
    private ModuleLoader moduleLoader;

    @Override
    public void onLoad() {
        INSTANCE = this;
        this.configManager = new ConfigManager(this);
        this.moduleLoader = new ModuleLoader(this);
    }

    @Override
    public void onDisable() {
        //TODO Send save signal to modules

        this.onServerShutdown();
    }

    abstract void onServerShutdown();

    public static String convert(final String toConvert) {
        return ChatColor.translateAlternateColorCodes('&', toConvert);
    }

    public static void registerEvent(final Listener eventListener) {
        INSTANCE.getServer().getPluginManager().registerEvents(eventListener, INSTANCE);
    }
}
