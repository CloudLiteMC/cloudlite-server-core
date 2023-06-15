package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.AutoCompressorConfig;
import com.burchard36.cloudlite.events.BreakBlockEvent;
import com.burchard36.cloudlite.module.PluginModule;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;

public final class AutoCompressorModule implements PluginModule {
    @Getter
    private AutoCompressor autoCompressor;
    @Getter
    private AutoCompressorConfig autoCompressorConfig;
    @Getter
    private CloudLiteCore pluginInstance;
    @Override
    public void loadModule(CloudLiteCore coreInstance) {
        this.pluginInstance = coreInstance;
        this.autoCompressor = new AutoCompressor(this);
        this.autoCompressorConfig = this.pluginInstance.getConfigManager().getConfig(new AutoCompressorConfig());
    }

    @Override
    public void enableModule() {
        CloudLiteCore.registerEvent(new BreakBlockEvent(this));
    }

    @Override
    public void disableModule() {

    }

    @Override
    public void onDatabaseLoad(HeadDatabaseAPI headDatabaseAPI) {

    }

    @Override
    public void reload() {
        this.autoCompressorConfig = this.pluginInstance.getConfigManager().getConfig(new AutoCompressorConfig());
    }
}
