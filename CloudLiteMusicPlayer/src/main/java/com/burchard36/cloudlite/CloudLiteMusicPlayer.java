package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.Config;
import com.burchard36.cloudlite.config.MusicListConfig;
import com.burchard36.cloudlite.events.JoinEvent;
import com.burchard36.cloudlite.module.PluginModule;
import lombok.Getter;

public final class CloudLiteMusicPlayer implements PluginModule {
    @Getter
    private final MusicListConfig musicListConfig;

    public CloudLiteMusicPlayer(final CloudLiteCore coreInstance) {
        this.musicListConfig = coreInstance.getConfigManager().getConfig(new MusicListConfig());
    }

    @Override
    public void startModule() {
        CloudLiteCore.registerEvent(new JoinEvent());
    }

    @Override
    public void reload() {

    }
}