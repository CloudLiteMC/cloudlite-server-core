package com.burchard36.cloudlite.cropregrower;

import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.cropregrower.events.BreakBlockEvent;
import com.burchard36.cloudlite.module.PluginModule;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import lombok.Getter;

public final class CropRegrower implements PluginModule {

    @Getter
    private final StateFlag breakCropsFlag = new StateFlag("break-crops", false);
    @Override
    public void loadModule(CloudLiteCore coreInstance) {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        registry.register(this.breakCropsFlag);
    }

    @Override
    public void enableModule() {
        CloudLiteCore.registerEvent(new BreakBlockEvent(this));
    }

    @Override
    public void disableModule() {

    }

    @Override
    public void reload() {

    }
}
