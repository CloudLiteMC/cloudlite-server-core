package com.burchard36.cloudlite.events;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.api.event.LootDropCause;
import com.archyx.aureliumskills.api.event.PlayerLootDropEvent;
import com.archyx.aureliumskills.data.PlayerData;
import com.burchard36.cloudlite.AureliumMultiplierData;
import com.burchard36.cloudlite.AutoCompressorModule;
import com.burchard36.cloudlite.BlockDropsEditor;
import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.config.BlockDropData;
import com.burchard36.cloudlite.config.BlockDropsConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class BlockDropEvent implements Listener {

    protected final BlockDropsEditor moduleInstance;
    protected final BlockDropsConfig config;
    protected final HashMap<UUID, AureliumMultiplierData> harvestAbilityMultipliers;

    public BlockDropEvent(final BlockDropsEditor moduleInstance) {
        this.moduleInstance = moduleInstance;
        this.harvestAbilityMultipliers = new HashMap<>();
        this.config = this.moduleInstance.getBlockDropsConfig();
    }

    /* This gets called before onBlockDropItem */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onExtraLoot(final PlayerLootDropEvent event) {
        this.addAbilityMultiplier(event.getPlayer(), event.getCause(), event.getLocation());
        event.setCancelled(true);
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
        final ItemStack droppedItem = new ItemStack(brokenData.getDropMaterial(), amount);
        this.handleAurelium(player, droppedItem);
        player.getInventory().addItem(droppedItem);
        ((AutoCompressorModule) CloudLiteCore.INSTANCE.getModuleLoader().getModule(AutoCompressorModule.class)).getAutoCompressor()
                .compressPlayer(player, false);
    }

    protected void handleAurelium(final Player player, final ItemStack pickupDrop) {
        final PlayerData aureliumPlayerData =
                AureliumAPI.getPlugin().getPlayerManager().getPlayerData(player.getUniqueId());
        assert aureliumPlayerData != null;

        if (!harvestAbilityMultipliers.containsKey(player.getUniqueId())) return;
        AureliumMultiplierData abilityMultipliers = harvestAbilityMultipliers.get(player.getUniqueId());

        switch (abilityMultipliers.getDropCause()) {
            case BOUNTIFUL_HARVEST, LUMBERJACK, LUCKY_MINER -> {
                pickupDrop.setAmount(pickupDrop.getAmount() * 2);
            }

            case BIGGER_SCOOP, TRIPLE_HARVEST -> {
                pickupDrop.setAmount(pickupDrop.getAmount() * 3);
            }
        }
        harvestAbilityMultipliers.remove(player.getUniqueId());

    }

    protected void addAbilityMultiplier(
            final Player player,
            LootDropCause theAbility,
            final Location theBlock
    ) {
        final AureliumMultiplierData multiplierData = new AureliumMultiplierData(player, theAbility, theBlock);
        harvestAbilityMultipliers.putIfAbsent(player.getUniqueId(), multiplierData);
    }
}
