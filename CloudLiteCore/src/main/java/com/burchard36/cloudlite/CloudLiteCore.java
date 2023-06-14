package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.ConfigManager;
import com.burchard36.cloudlite.gui.GuiEvents;
import com.burchard36.cloudlite.gui.GuiManager;
import com.burchard36.cloudlite.module.ModuleLoader;
import lombok.Getter;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

/**
 * Classes wanting to use the API need to extend this class instead
 */
public abstract class CloudLiteCore extends JavaPlugin implements Listener {

    public static CloudLiteCore INSTANCE;
    @Getter
    private ConfigManager configManager;
    @Getter
    private ModuleLoader moduleLoader;
    @Getter
    private Random random;
    @Getter
    private GuiManager guiManager;
    private GuiEvents guiEvents;

    @Override
    public void onLoad() {
        INSTANCE = this;
        random = new Random();
        /* use this time to load things that don't need ant major spigot/world implementations */
        /* Looking at your worldguard */
        Bukkit.getLogger().info(convert("&fInitializing &bConfigManager&f..."));
        this.configManager = new ConfigManager(this);
        Bukkit.getLogger().info(convert("&aDone!"));
        Bukkit.getLogger().info(convert("&fInitializing &bModuleLoader&f..."));
        this.moduleLoader = new ModuleLoader(this);
        Bukkit.getLogger().info(convert("&aDone!"));
        Bukkit.getLogger().info(convert("&fInitializing &bGuiManager&f..."));
        this.guiManager = new GuiManager();
        Bukkit.getLogger().info(convert("&aDone!"));
        Bukkit.getLogger().info(convert("&fSending &bonServerLoadUp&f to registered modules..."));
        this.moduleLoader.onServerLoadUp(this.guiManager);
        Bukkit.getLogger().info(convert("&aDone&f! &bCloudLiteCore&f has finished its onLoad initialization!"));
        Bukkit.getLogger().info(convert("&fIf there was any &cerrors&f please contact a &bdeveloper&f."));
    }
    @Override
    public void onEnable() {
        Bukkit.getLogger().info(convert("&fSending &bonEnable&f to all registered modules..."));
        this.moduleLoader.onServerEnable();
        Bukkit.getLogger().info(convert("&aDone&f! &bCloudLiteCore&f has finished its onEnable initialization!"));
        Bukkit.getLogger().info(convert("&fIf there was any &cerrors&f please review your configs before contacting a &bdeveloper&f."));
    }

    @EventHandler
    public void onDatabaseLoad(final DatabaseLoadEvent loadEvent) {
        this.guiEvents = new GuiEvents(guiManager);
        registerEvent(this.guiEvents);
        Bukkit.getLogger().info(convert("&bHeadDatabaseAPI &fHas connected to its database, injecting API to all modules..."));
        this.moduleLoader.onDatabaseLoad(new HeadDatabaseAPI());
        Bukkit.getLogger().info(convert("&aDone!"));
    }

    @Override
    public void onDisable() {
        //TODO Send save signal to modules


    }

    public static void registerEvent(final Listener eventListener) {
        INSTANCE.getServer().getPluginManager().registerEvents(eventListener, INSTANCE);
    }

    public void loadInventoryModule() {
        registerEvent(this); // finish initialization after heads database initializes
    }
}
