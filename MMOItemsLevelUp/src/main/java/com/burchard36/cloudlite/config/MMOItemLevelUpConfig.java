package com.burchard36.cloudlite.config;

import lombok.NonNull;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

public class MMOItemLevelUpConfig implements Config {

    private final HashMap<String, MMOItemLevelUpData> mmoItemLevelUps = new HashMap<>();

    @Override
    public @NonNull String getFileName() {
        return "mmo-levelup/levels.yml";
    }

    @Override
    public void deserialize(FileConfiguration configuration) {
        this.mmoItemLevelUps.clear();
        for (String startingMMOItem : configuration.getKeys(false)) {
            final ConfigurationSection section = configuration.getConfigurationSection(startingMMOItem);
            assert section != null;
            this.mmoItemLevelUps.put(startingMMOItem, new MMOItemLevelUpData(section));
        }
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
