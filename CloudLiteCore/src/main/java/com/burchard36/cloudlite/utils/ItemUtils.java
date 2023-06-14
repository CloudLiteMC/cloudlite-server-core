package com.burchard36.cloudlite.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class ItemUtils {

    public static ItemStack createItemStack(
            final String materialName,
            @Nullable final String displayName,
            final String... lore) {
        return createItemStack(Material.valueOf(materialName), displayName, lore);
    }

    public static ItemStack createItemStack(
            final Material material,
            final String displayName,
            final String... lore) {
        final ItemStack itemStack = new ItemStack(material);
        if (displayName == null && lore == null) return itemStack;
        final ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        if (displayName != null) itemMeta.setDisplayName(convert(displayName));
        if (lore != null) {
            final List<String> itemLore = new ArrayList<>();
            for (String loreLine : lore) {
                itemLore.add(convert(loreLine));
            }
            itemMeta.setLore(itemLore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
