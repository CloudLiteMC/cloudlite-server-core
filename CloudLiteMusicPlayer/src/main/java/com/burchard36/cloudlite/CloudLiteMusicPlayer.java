package com.burchard36.cloudlite;

import com.burchard36.cloudlite.config.MusicListConfig;
import com.burchard36.cloudlite.events.JoinEvent;
import com.burchard36.cloudlite.events.SongEndedEvent;
import com.burchard36.cloudlite.events.TexturePackLoadEvent;
import com.burchard36.cloudlite.module.PluginModule;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;

public final class CloudLiteMusicPlayer implements PluginModule {
    private CloudLiteCore pluginInstance;
    @Getter
    private MusicListConfig musicListConfig;
    @Getter
    private MusicPlayer musicPlayer;

    @Override
    public void loadModule(final CloudLiteCore coreInstance) {
        this.pluginInstance = coreInstance;
        this.musicListConfig = this.pluginInstance.getConfigManager().getConfig(new MusicListConfig());
        this.musicPlayer = new MusicPlayer(this);

    }

    @Override
    public void onDatabaseLoad(final HeadDatabaseAPI headDatabaseAPI) {

    }

    @Override
    public void startModule() {
        CloudLiteCore.registerEvent(new JoinEvent(this));
        CloudLiteCore.registerEvent(new SongEndedEvent(this));
        CloudLiteCore.registerEvent(new TexturePackLoadEvent(this));
    }

    @Override
    public void reload() {
        this.musicListConfig = this.pluginInstance.getConfigManager().getConfig(new MusicListConfig());

        this.musicPlayer.setMusicConfig(this.musicListConfig);
    }
}