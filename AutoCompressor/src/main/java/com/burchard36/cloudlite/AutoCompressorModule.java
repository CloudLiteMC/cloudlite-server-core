package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.AutoCompressorConfig;
import com.burchard36.cloudlite.module.PluginModule;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.checkerframework.checker.units.qual.A;

public final class AutoCompressorModule implements PluginModule {
    @Getter
    private AutoCompressor autoCompressor;
    @Getter
    private AutoCompressorConfig autoCompressorConfig;
    @Override
    public void loadModule(CloudLiteCore coreInstance) {
        this.autoCompressor = new AutoCompressor();
        this.autoCompressorConfig = new AutoCompressorConfig();
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
