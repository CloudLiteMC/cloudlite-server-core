package com.burchard36.cloudlite.gui;

import com.burchard36.cloudlite.gui.buttons.InventoryButton;
import com.burchard36.cloudlite.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class UpgradeItemGui extends InventoryGui {


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
        // = item in hand
        this.addButton(12, backGround);
        this.addButton(13, this.createUpgradeToButton());
        this.addButton(14, backGround);
        // = item in upgrade
        this.addButton(16, backGround);
        this.addButton(17, backGround);
        this.addButton(18, backGround);
        this.addButton(19, backGround);
        this.addButton(20, backGround);
        this.addButton(21, backGround);
        this.addButton(22, backGround);
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
}
