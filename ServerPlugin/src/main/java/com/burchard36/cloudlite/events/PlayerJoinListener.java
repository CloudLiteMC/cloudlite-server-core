package com.burchard36.cloudlite.events;

import com.burchard36.cloudlite.ServerPlugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {

    protected final NamespacedKey joinItemsReceived;
    protected final ServerPlugin pluginInstance;

    public PlayerJoinListener(final ServerPlugin pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.joinItemsReceived = new NamespacedKey(this.pluginInstance, "join_items_received");
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent joinEvent) {
        final Player player = joinEvent.getPlayer();
        final PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        if (dataContainer.has(this.joinItemsReceived, PersistentDataType.BYTE)) return;

        final PlayerInventory inventory = player.getInventory();
        inventory.addItem(MMOItems.plugin.getItem(Type.TOOL, "BEGINNER_SHOVEL_1"));
        inventory.addItem(MMOItems.plugin.getItem(Type.TOOL, "BEGINNER_PICKAXE_1"));
        inventory.addItem(MMOItems.plugin.getItem(Type.TOOL, "BEGINNER_AXE_1"));
        inventory.addItem(MMOItems.plugin.getItem(Type.SWORD, "BEGINNER_SWORD_1"));
        if (inventory.getHelmet() == null)
            inventory.setItem(EquipmentSlot.HEAD, MMOItems.plugin.getItem(Type.ARMOR, "BEGINNER_HELMET_1"));
        if (inventory.getChestplate() == null)
            inventory.setItem(EquipmentSlot.CHEST, MMOItems.plugin.getItem(Type.ARMOR, "BEGINNER_CHESTPLATE_1"));
        if (inventory.getLeggings() == null)
            inventory.setItem(EquipmentSlot.LEGS, MMOItems.plugin.getItem(Type.ARMOR, "BEGINNER_LEGGINGS_1"));
        if (inventory.getBoots() == null)
            inventory.setItem(EquipmentSlot.FEET, MMOItems.plugin.getItem(Type.ARMOR, "BEGINNER_BOOTS_1"));

        dataContainer.set(this.joinItemsReceived, PersistentDataType.BYTE, (byte) 0x00);
    }
}
