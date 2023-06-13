package com.burchard36.cloudlite.module;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleLoader {
    protected final JavaPlugin pluginInstance;
    protected final List<PluginModule> modules;

    public ModuleLoader(final JavaPlugin javaPlugin) {
        this.pluginInstance = javaPlugin;
        this.modules = new ArrayList<>();
    }

    public void startModule(final PluginModule pluginModule) {
        if (modules.contains(pluginModule)) throw new IllegalStateException("PluginModule is already initialized!");
        this.modules.add(pluginModule);
        pluginModule.startModule();
    }

    public void reloadModules() {
        this.modules.forEach(PluginModule::reload);
    }

}
