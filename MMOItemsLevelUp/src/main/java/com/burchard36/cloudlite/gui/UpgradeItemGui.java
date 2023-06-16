package com.burchard36.cloudlite.gui;

import com.burchard36.cloudlite.config.MMOItemLevelUpData;
import com.burchard36.cloudlite.gui.buttons.InventoryButton;
import com.burchard36.cloudlite.utils.ItemUtils;
import com.burchard36.cloudlite.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class UpgradeItemGui extends InventoryGui {

    protected final ItemStack itemInHand;
    protected final MMOItemLevelUpData levelUpData;
    protected boolean didBuyItem = false;

    public UpgradeItemGui(final ItemStack itemInHand, final MMOItemLevelUpData levelUpData) {
        this.itemInHand = itemInHand;
        this.levelUpData = levelUpData;
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        if (this.didBuyItem) return;
        event.getPlayer().getInventory().addItem(this.itemInHand);
        super.onInventoryClose(event);
    }

    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(null, 27, convert("&3&lUpgrade Item?"));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
        super.onInventoryClick(event);
    }

    @Override
    public void fillButtons() {
        final InventoryButton backGround = this.createBackGroundButton();

        this.addButton(0, backGround);
        this.addButton(1, backGround);
        this.addButton(2, backGround);
        this.addButton(3, backGround);
        this.addButton(4, backGround);
        this.addButton(5, backGround);
        this.addButton(6, backGround);
        this.addButton(7, backGround);
        this.addButton(8, backGround);
        this.addButton(9, backGround);
        this.addButton(10, backGround);
        this.addButton(11, this.currentItemButton());
        this.addButton(12, backGround);
        this.addButton(13, this.createUpgradeToButton());
        this.addButton(14, backGround);
        this.addButton(15, this.nextItemButton());
        this.addButton(16, backGround);
        this.addButton(17, backGround);
        this.addButton(18, backGround);
        this.addButton(19, backGround);
        this.addButton(20, backGround);
        this.addButton(21, backGround);
        this.addButton(22, this.getBuyButton());
        this.addButton(23, backGround);
        this.addButton(24, backGround);
        this.addButton(25, backGround);
        this.addButton(26, backGround);

        super.fillButtons();
    }

    protected ItemStack upgradeToItem() {
        String texture = "ff9e19e5f2ce3488c29582b6d2601500626e8db2a88cd18164432fef2e34de6b";
        return ItemUtils.createSkull(texture, "&a&lUPGRADES TO", null);
    }
    protected final InventoryButton createUpgradeToButton() {
        return new InventoryButton(this.upgradeToItem()) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    protected ItemStack backGroundItem() {
        return ItemUtils.createItemStack(Material.WHITE_STAINED_GLASS_PANE, "&f ", null);
    }

    protected final InventoryButton createBackGroundButton() {
        return new InventoryButton(this.backGroundItem()) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    protected final InventoryButton currentItemButton() {
        return new InventoryButton(this.itemInHand) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    protected final InventoryButton nextItemButton() {
        return new InventoryButton(this.levelUpData.getUpgradeItem()) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    protected final InventoryButton getBuyButton() {
        String costDisplayName;
        final ItemStack costItem = this.levelUpData.getCostItem();
        if (costItem.hasItemMeta()) {
            assert costItem.getItemMeta() != null;
            costDisplayName = costItem.getItemMeta().getDisplayName();
        } else costDisplayName = StringUtils.getPrettyMaterialName(costItem.getType());

        final ItemStack itemStack = ItemUtils.createSkull(
                "ac5c7e53695d88f4d31f52f43fac609ad9e62bc97d49fc504174dfdb84150c39",
                "&a&lUPGRADE ITEM",
                "&f",
                "&3Buy Price",
                "&7x&b%s&r &f%s".formatted(this.levelUpData.getUpgradeMaterialAmount(), costDisplayName),
                "&7x&b%s &eExperience Levels".formatted(this.levelUpData.getExperienceLevelCost()),
                "&f",
                "&eClick&7 to purchase!",
                "&f",
                "&cWARNING! Upgrading &c&l&nWILL&c&l remove applied gem stones!"
        );
        return new InventoryButton(itemStack) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {
                final Player player = (Player) clickEvent.getWhoClicked();
                if (levelUpData.canAfford(player)) {
                    didBuyItem = true;
                    levelUpData.removeCosts(player);
                    player.getInventory().addItem(levelUpData.getUpgradeItem());
                } else {
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                    player.sendMessage(convert("&cYou cannot afford this!"));
                }


                player.closeInventory();
            }
        };
    }

}
