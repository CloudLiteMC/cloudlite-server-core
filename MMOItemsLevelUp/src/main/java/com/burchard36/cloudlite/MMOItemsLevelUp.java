package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.AutoCompressorConfig;
import com.burchard36.cloudlite.module.PluginModule;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;

public final class MMOItemsLevelUp implements PluginModule {
    private CloudLiteCore pluginInstance;
    @Getter
    private static AutoCompressorConfig compressorConfig;


    @Override
    public void loadModule(CloudLiteCore coreInstance) {
        this.pluginInstance = coreInstance;
        compressorConfig = ((AutoCompressorModule)pluginInstance.getModuleLoader().getModule(AutoCompressorModule.class))
                .getAutoCompressorConfig();
    }

    @Override
    public void enableModule() {

    }

    @Override
    public void disableModule() {

    }

    @Override
    public void onDatabaseLoad(HeadDatabaseAPI headDatabaseAPI) {

    }

    @Override
    public void reload() {

    }
}
