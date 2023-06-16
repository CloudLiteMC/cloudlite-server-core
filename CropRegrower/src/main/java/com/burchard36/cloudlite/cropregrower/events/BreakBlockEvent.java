package com.burchard36.cloudlite.cropregrower.events;

import com.burchard36.cloudlite.cropregrower.CropRegrower;
import com.burchard36.cloudlite.utils.TaskRunner;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Date;
import java.util.HashMap;

public class BreakBlockEvent implements Listener {

    protected final CropRegrower moduleInstance;

    protected final HashMap<Block, Long> blockRegenTimes;
    protected final int regenTimeTicks = 180;

    public BreakBlockEvent(final CropRegrower moduleInstance) {
        this.moduleInstance = moduleInstance;
        this.blockRegenTimes = new HashMap<>();
        TaskRunner.runSyncTaskTimer(() -> {
            if (blockRegenTimes.isEmpty()) return;
            final long now = new Date().getTime();
            blockRegenTimes.forEach((block, regenAt) -> {
                if (regenAt > now) return;
                Ageable ageable = (Ageable) block.getBlockData();
                ageable.setAge(ageable.getMaximumAge());
                TaskRunner.runSyncTaskLater(() -> {
                    block.setBlockData(ageable);
                    blockRegenTimes.remove(block);
                }, 1);
            });
        }, 4);
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent breakEvent) {
        final Block brokenBlock = breakEvent.getBlock();
        if (this.blockRegenTimes.containsKey(brokenBlock)) return;
        if (!(brokenBlock.getBlockData() instanceof Ageable ageable)) return;
        this.blockRegenTimes.putIfAbsent(brokenBlock, new Date().getTime() + (this.regenTimeTicks * 50L));
        ageable.setAge(0);
        TaskRunner.runSyncTaskLater(() -> brokenBlock.setBlockData(ageable), 1);
    }

    @EventHandler
    public void onWorldGuardBreak(final com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent breakEvent) {
        if (breakEvent.getCause().getFirstPlayer() == null) return; // only players can break crops
        final LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(breakEvent.getCause().getFirstPlayer());
        final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        if (breakEvent.getBlocks().size() > 1 || breakEvent.getBlocks().size() == 0) return; // players can only do one block at once
        final Block brokenBlock = breakEvent.getBlocks().get(0);
        if (!(brokenBlock.getBlockData() instanceof Ageable ageable)) return;
        final boolean resultValue = container.createQuery().testState(
                localPlayer.getLocation(),
                localPlayer,
                this.moduleInstance.getBreakCropsFlag()
        );

        if (resultValue && ageable.getAge() == ageable.getMaximumAge()) {
            breakEvent.setResult(Event.Result.ALLOW);
        } else if (resultValue && ageable.getAge() != ageable.getMaximumAge()) breakEvent.setResult(Event.Result.DENY);
    }
}
