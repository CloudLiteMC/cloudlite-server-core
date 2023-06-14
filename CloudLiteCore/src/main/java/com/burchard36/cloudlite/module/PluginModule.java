package com.burchard36.cloudlite.module;

import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.gui.GuiManager;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;

public interface PluginModule {

    /**
     * Simulates {@link JavaPlugin#onLoad()} for PluginModules
     * */
    void loadModule(final CloudLiteCore coreInstance);

    /**
     * Simulated {@link JavaPlugin#onEnable()} for PluginModules
     */
    void enableModule();

    /**
     * Simulates {@link JavaPlugin#onDisable()} for PluginModules
     */
    void disableModule();
    /**
     * Calls when HeadDatabaseAPI Connects to the HeadDatabase
     */
    void onDatabaseLoad(final HeadDatabaseAPI headDatabaseAPI);

    void reload();

}
