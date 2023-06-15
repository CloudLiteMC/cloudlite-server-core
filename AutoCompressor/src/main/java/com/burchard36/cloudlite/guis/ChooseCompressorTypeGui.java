package com.burchard36.cloudlite.guis;

import com.burchard36.cloudlite.AutoCompressorModule;
import com.burchard36.cloudlite.gui.InventoryGui;
import com.burchard36.cloudlite.gui.buttons.InventoryButton;
import com.burchard36.cloudlite.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class ChooseCompressorTypeGui extends InventoryGui {

    protected final AutoCompressorModule moduleInstance;

    public ChooseCompressorTypeGui(final AutoCompressorModule moduleInstance) {
        this.moduleInstance = moduleInstance;
    }

    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(null, 27, convert("&e&lCompressors"));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
        super.onInventoryClick(event);
    }

    @Override
    public void fillButtons() {
        final InventoryButton backgroundButton = this.backgroundButton();

        this.addButton(0, backgroundButton);
        this.addButton(1, backgroundButton);
        this.addButton(2, backgroundButton);
        this.addButton(3, backgroundButton);
        this.addButton(4, backgroundButton);
        this.addButton(5, backgroundButton);
        this.addButton(6, backgroundButton);
        this.addButton(7, backgroundButton);
        this.addButton(8, backgroundButton);
        this.addButton(9, backgroundButton);
        this.addButton(10, backgroundButton);
        this.addButton(11, this.compressedItemsButton());
        this.addButton(12, backgroundButton);
        this.addButton(13, this.superCompressedItemsButton());
        this.addButton(14, backgroundButton);
        this.addButton(15, this.megaCompressedItemsButton());
        this.addButton(16, backgroundButton);
        this.addButton(17, backgroundButton);
        this.addButton(18, backgroundButton);
        this.addButton(19, backgroundButton);
        this.addButton(20, backgroundButton);
        this.addButton(21, backgroundButton);
        this.addButton(22, this.compressInventoryButton());
        this.addButton(23, backgroundButton);
        this.addButton(24, backgroundButton);
        this.addButton(25, backgroundButton);
        this.addButton(26, backgroundButton);

        super.fillButtons();
    }

    public InventoryButton backgroundButton() {
        return new InventoryButton(ItemUtils.createItemStack(Material.CYAN_STAINED_GLASS_PANE, "&f ", null)) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    public InventoryButton compressedItemsButton() {
        return new InventoryButton(ItemUtils.createItemStack(Material.ANVIL, "&b&lCompressor",
                "&f",
                "&7Compressors for compressing items into they're &eCompressed&7 form!")) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {
                final Player player = (Player) clickEvent.getWhoClicked();
                player.closeInventory();
                moduleInstance.getPluginInstance().getGuiManager()
                        .openPaginatedTo((Player) clickEvent.getWhoClicked(), 0, new CompressorGui(moduleInstance, player));
            }
        };
    }

    public InventoryButton superCompressedItemsButton() {
        return new InventoryButton(ItemUtils.createItemStack(Material.ANVIL, "&b&lSuper Compressor",
                "&f",
                "&7Compressors for compressing items into they're &eSuper Compressed&7 form!")) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    public InventoryButton megaCompressedItemsButton() {
        return new InventoryButton(ItemUtils.createItemStack(Material.ANVIL, "&b&lMega Compressor",
                "&f",
                "&7Compressors for compressing items into they're &eMega Compressed&7 form!")) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    public InventoryButton compressInventoryButton() {
        final ItemStack itemStack = ItemUtils.createSkull("b69d0d4711153a089c5567a749b27879c769d3bdcea6fda9d6f66e93dd8c4512",
                "&a&lCompress All Now!",
                "&f",
                "&eClick&7 to compress all available items now!");
        return new InventoryButton(itemStack) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {
                final Player player = (Player) clickEvent.getWhoClicked();
                player.sendMessage(convert("&cCompressed all items!"));
                player.playSound(player, Sound.ENTITY_IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
                moduleInstance.getAutoCompressor().compressPlayer(player, true);
            }
        };
    }
}
