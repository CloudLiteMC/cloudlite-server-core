package com.burchard36.cloudlite;

public final class ServerPlugin extends CloudLiteCore {
    @Override
    public void onLoad() {
        super.onLoad();
        this.getModuleLoader().registerModule(new CloudLiteMusicPlayer());
    }
}
