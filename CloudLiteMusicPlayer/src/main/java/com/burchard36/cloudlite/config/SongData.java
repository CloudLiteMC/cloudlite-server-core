package com.burchard36.cloudlite.config;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

public class SongData {

    @Getter
    protected final int minutes;
    @Getter
    protected final int seconds;
    @Getter
    protected final String localKey;
    @Getter
    protected final String songDisplayName;
    @Getter
    protected final String artistName;

    public SongData(final ConfigurationSection config, final String songLocalKey) {
        this.minutes = config.getInt("Length.Minutes");
        this.seconds = config.getInt("Length.Seconds");
        this.songDisplayName = config.getString("Details.SongName");
        this.artistName = config.getString("Details.Artist");
        this.localKey = songLocalKey;
    }

    /**
     * Gets the total amount of ticks this song is supposed to play for
     * @return total tick length of the song
     */
    public final long getTotalTicks() {
        final int tickSeconds = this.getSeconds() * 20;
        final int tickMinutes = (this.minutes * 60) * 20;
        return tickSeconds + tickMinutes;
    }
}
