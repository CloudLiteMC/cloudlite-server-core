package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.MMOItemsLevelUp;
import lombok.NonNull;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class MMOItemLevelUpConfig {

    private final HashMap<String, MMOItemLevelUpData> mmoItemLevelUps = new HashMap<>();
    protected final MMOItemsLevelUp moduleInstance;

    public MMOItemLevelUpConfig(final MMOItemsLevelUp moduleInstance) {
        this.moduleInstance = moduleInstance;

        /* Ensure the config files always exist from the jar */
        this.moduleInstance.getPluginInstance().saveResource("levelup-items/axes/beginner_axe_upgrades.yml", false);
        this.moduleInstance.getPluginInstance().saveResource("levelup-items/pickaxes/beginner_pickaxe_upgrades.yml", false);
        this.moduleInstance.getPluginInstance().saveResource("levelup-items/shovels/beginner_shovel_upgrades.yml", false);
        this.moduleInstance.getPluginInstance().saveResource("levelup-items/swords/beginner_sword_upgrades.yml", false);

        List<MMOItemRecursiveConfig> recursiveConfigs = this.moduleInstance.getPluginInstance().getConfigManager().getRecursiveConfig(
                "levelup-items",
                MMOItemRecursiveConfig.class,
                null
        );

        Bukkit.getLogger().info(convert("Loading %s configuration files for MMOItemsLevelUpConfig".formatted(recursiveConfigs.size())));
        recursiveConfigs.forEach(recursiveConfig -> {
            this.mmoItemLevelUps.putAll(recursiveConfig.getMmoItemLevelUps());
        });
        Bukkit.getLogger().info(convert("Successfully laoded %s MMOItem upgrades!".formatted(this.mmoItemLevelUps.size())));
    }

    public final @Nullable MMOItemLevelUpData getUpgrade(final ItemStack itemStack) {
        final String mmoItemID = this.getMMOItemID(itemStack);
        if (mmoItemID == null) return null;
        return this.mmoItemLevelUps.get(mmoItemID);
    }

    public final boolean hasUpgrade(final ItemStack itemStack) {
        final String mmoItemID = this.getMMOItemID(itemStack);
        if (mmoItemID == null) return false;
        return this.mmoItemLevelUps.containsKey(mmoItemID);
    }

    public @Nullable Type getMMOItemType(final ItemStack itemStack) {
        return MMOItems.getType(itemStack);
    }

    public @Nullable String getMMOItemID(final ItemStack itemStack) {
        return MMOItems.getID(itemStack);
    }
}
