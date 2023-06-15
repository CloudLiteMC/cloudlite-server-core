package com.burchard36.cloudlite.events;

import com.burchard36.cloudlite.AutoCompressorModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockEvent implements Listener {

    protected final AutoCompressorModule moduleInstance;

    public BreakBlockEvent(final AutoCompressorModule module) {
        this.moduleInstance = module;
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        this.moduleInstance.getAutoCompressor().compressPlayer(event.getPlayer(), false);
    }
}
