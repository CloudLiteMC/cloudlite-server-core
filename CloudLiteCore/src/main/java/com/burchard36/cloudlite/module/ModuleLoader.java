package com.burchard36.cloudlite.module;

import com.burchard36.cloudlite.CloudLiteCore;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    protected final List<PluginModule> modules;

    public ModuleLoader() {
        this.modules = new ArrayList<>();
    }

    public void registerModule(final PluginModule pluginModule) {
        if (modules.contains(pluginModule)) throw new IllegalStateException("PluginModule is already initialized!");
        this.modules.add(pluginModule);
    }

    public void onServerLoadUp(final CloudLiteCore coreInstance) {
        this.modules.forEach(m -> m.loadModule(coreInstance));
    }

    public void onDatabaseLoad(final HeadDatabaseAPI headDatabaseAPI) {
        this.modules.forEach(m -> m.onDatabaseLoad(headDatabaseAPI));
    }

    public void onServerEnable() {
        this.modules.forEach(PluginModule::enableModule);
    }

    public void onServerShutdown() {
        this.modules.forEach(PluginModule::disableModule);
    }

    public void reloadModules() {
        this.modules.forEach(PluginModule::reload);
    }
}
