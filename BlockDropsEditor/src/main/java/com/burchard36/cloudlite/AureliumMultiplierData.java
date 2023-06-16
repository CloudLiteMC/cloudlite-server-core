package com.burchard36.cloudlite;

import com.archyx.aureliumskills.api.event.LootDropCause;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AureliumMultiplierData {
    @Getter
    protected final Player player;
    @Getter
    protected final LootDropCause dropCause;
    @Getter
    protected final Location blockDropLocation;

    public AureliumMultiplierData(
            final Player player,
            final LootDropCause dropCause,
            final Location blockLocation
    ) {
        this.player = player;
        this.dropCause = dropCause;
        this.blockDropLocation = blockLocation;
    }

}
