package com.burchard36.cloudlite.events;

import com.burchard36.cloudlite.BlockDropsEditor;
import com.burchard36.cloudlite.config.BlockDropData;
import com.burchard36.cloudlite.config.BlockDropsConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class BlockDropEvent implements Listener {

    protected final BlockDropsEditor moduleInstance;
    protected final BlockDropsConfig config;

    public BlockDropEvent(final BlockDropsEditor moduleInstance) {
        this.moduleInstance = moduleInstance;
        this.config = this.moduleInstance.getBlockDropsConfig();
    }

    @EventHandler
    public void onBlockDropItem(final BlockDropItemEvent dropEvent) {
        final Player player = dropEvent.getPlayer();
        final ItemStack itemInHand = player.getInventory().getItemInMainHand();
        final Material brokenType = dropEvent.getBlockState().getType();
        final BlockDropData brokenData = this.config.getDropData(brokenType);

        /* For my servers purpose, block drops will always have
            experience, so we can just cancel the event & clear its items now
         */
        if (brokenData == null) return;
        if (brokenData.hasExperienceDrops()) {
            player.giveExp(brokenData.getDropExperience());
            dropEvent.setCancelled(true);
            dropEvent.getItems().clear();
        }

        final int amount = brokenData.getDropAmountWithFortune(itemInHand);
        player.getInventory().addItem(new ItemStack(brokenData.getDropMaterial(), amount));
    }
}
