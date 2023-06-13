package com.burchard36.cloudlite;

public final class ServerPlugin extends CloudLiteCore {

    @Override
    public void onEnable() {
        this.getModuleLoader().startModule(new CloudLiteMusicPlayer(this));
    }

    @Override
    public void onServerShutdown() {

    }
}
