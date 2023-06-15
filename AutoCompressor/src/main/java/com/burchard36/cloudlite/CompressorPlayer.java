package com.burchard36.cloudlite;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CompressorPlayer {

    @Getter
    private final PersistentDataContainer playerContainer;
    @Getter
    private final Player player;

    public CompressorPlayer(final Player player) {
        this.player = player;
        this.playerContainer = this.player.getPersistentDataContainer();
    }

    public final boolean hasKey(final NamespacedKey key, PersistentDataType<?, ?> types) {
        return this.playerContainer.has(key, types);
    }

    public final <T> T getKeyValue(final NamespacedKey key, PersistentDataType<T, T> type) {
        return this.playerContainer.get(key, type);
    }

    public final <T> void giveKey(final NamespacedKey key, PersistentDataType<T, T> type, T value) {
        this.playerContainer.set(key, type, value);
    }

    public final void removeKey(final NamespacedKey key) {
        this.playerContainer.remove(key);
    }
}
