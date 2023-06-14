package com.burchard36.cloudlite.module;

import com.burchard36.cloudlite.gui.GuiManager;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    protected final JavaPlugin pluginInstance;
    protected final List<PluginModule> modules;

    public ModuleLoader(final JavaPlugin javaPlugin) {
        this.pluginInstance = javaPlugin;
        this.modules = new ArrayList<>();
    }

    public void registerModule(final PluginModule pluginModule) {
        if (modules.contains(pluginModule)) throw new IllegalStateException("PluginModule is already initialized!");
        this.modules.add(pluginModule);
    }

    public void onServerLoadUp(final GuiManager guiManager) {

    }

    public void onDatabaseLoad(final HeadDatabaseAPI headDatabaseAPI) {
        this.modules.forEach(m -> m.onDatabaseLoad(headDatabaseAPI));
    }

    public void onServerEnable() {

    }

    public void reloadModules() {
        this.modules.forEach(PluginModule::reload);
    }

}
