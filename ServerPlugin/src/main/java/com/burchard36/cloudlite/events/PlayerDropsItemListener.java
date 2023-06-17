package com.burchard36.cloudlite.events;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class PlayerDropsItemListener implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void onDrop(final PlayerDropItemEvent dropEvent) {
        final Player player = dropEvent.getPlayer();
        dropEvent.setCancelled(true); // stop drops accross the entire server
        player.sendMessage(convert("&cUse &e/trash&c to get rid of items!"));
        player.playNote(player.getLocation(), Instrument.PLING, new Note(0));
    }
}
