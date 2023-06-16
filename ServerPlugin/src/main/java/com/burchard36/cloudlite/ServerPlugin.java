package com.burchard36.cloudlite;

public final class ServerPlugin extends CloudLiteCore {
    @Override
    public void onLoad() {
        // Modules need to get injected before the rest of the plugin
        this.getModuleLoader().registerModule(new CloudLiteMusicPlayer());
        this.getModuleLoader().registerModule(new AutoCompressorModule());
        this.getModuleLoader().registerModule(new MMOItemsLevelUp());
        this.getModuleLoader().registerModule(new BlockDropsEditor());
        super.onLoad();
    }
}
