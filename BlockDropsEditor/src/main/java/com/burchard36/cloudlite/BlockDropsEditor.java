package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.BlockDropsConfig;
import com.burchard36.cloudlite.events.BlockDropEvent;
import com.burchard36.cloudlite.module.PluginModule;
import lombok.Getter;


public final class BlockDropsEditor implements PluginModule {

    @Getter
    private CloudLiteCore pluginInstance;
    @Getter
    private BlockDropsConfig blockDropsConfig;

    @Override
    public void loadModule(CloudLiteCore coreInstance) {
        this.pluginInstance = coreInstance;
        this.blockDropsConfig = this.pluginInstance.getConfigManager().getConfig(new BlockDropsConfig());
    }

    @Override
    public void enableModule() {
        CloudLiteCore.registerEvent(new BlockDropEvent(this));
    }

    @Override
    public void disableModule() {

    }

    @Override
    public void reload() {
        this.blockDropsConfig = this.pluginInstance.getConfigManager().getConfig(new BlockDropsConfig());
    }
}
