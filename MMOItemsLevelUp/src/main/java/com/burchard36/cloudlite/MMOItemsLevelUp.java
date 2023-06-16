package com.burchard36.cloudlite;

import com.burchard36.cloudlite.commands.UpgradeCommand;
import com.burchard36.cloudlite.config.MMOItemLevelUpConfig;
import com.burchard36.cloudlite.module.PluginModule;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class MMOItemsLevelUp implements PluginModule {
    private CloudLiteCore pluginInstance;
    @Getter
    private MMOItemLevelUpConfig levelUpConfig;

    @Override
    public void loadModule(CloudLiteCore coreInstance) {
        this.pluginInstance = coreInstance;
        this.levelUpConfig = this.pluginInstance.getConfigManager().getConfig(new MMOItemLevelUpConfig());
    }

    @Override
    public void enableModule() {
        CloudLiteCore.registerCommand("upgradeitem", new UpgradeCommand(this));
    }

    @Override
    public void disableModule() {
        Bukkit.getOnlinePlayers().forEach(Player::closeInventory);
    }

    @Override
    public void reload() {
        this.levelUpConfig = this.pluginInstance.getConfigManager().getConfig(new MMOItemLevelUpConfig());
    }
}
