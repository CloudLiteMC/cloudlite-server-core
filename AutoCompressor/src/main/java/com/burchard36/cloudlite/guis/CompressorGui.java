package com.burchard36.cloudlite.guis;

import com.burchard36.cloudlite.AutoCompressorModule;
import com.burchard36.cloudlite.CompressorPlayer;
import com.burchard36.cloudlite.config.AutoCompressorConfig;
import com.burchard36.cloudlite.config.AutoCompressorCosts;
import com.burchard36.cloudlite.config.AutoCompressorMaterial;
import com.burchard36.cloudlite.gui.InventoryGui;
import com.burchard36.cloudlite.gui.PaginatedInventory;
import com.burchard36.cloudlite.gui.buttons.InventoryButton;
import com.burchard36.cloudlite.utils.ItemUtils;
import com.burchard36.cloudlite.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class CompressorGui extends PaginatedInventory {

    protected final AutoCompressorModule moduleInstance;
    protected final AutoCompressorConfig config;

    public CompressorGui(final AutoCompressorModule moduleInstance) {
        this.moduleInstance = moduleInstance;
        this.config = this.moduleInstance.getAutoCompressorConfig();

        int totalPages = this.config.getCompressedMaterials().size() / 45;
        if (totalPages == 0) totalPages = 1;

        for (int currentPage = 0; currentPage < totalPages; currentPage++) {

            int finalCurrentPage = currentPage;
            this.addPage(currentPage, new InventoryGui() {

                @Override
                public void onInventoryClick(InventoryClickEvent event) {
                    event.setCancelled(true);
                    super.onInventoryClick(event);
                }

                @Override
                public void fillButtons() {
                    int startAt = finalCurrentPage * 45; // only display 45 songs per page
                    for (int x = 0; x < config.getCompressedMaterials().size(); x++) {
                        if (startAt > x) continue;

                        final AutoCompressorMaterial materialData = config.getCompressedMaterials().values().stream().collect(Collectors.toList()).get(0);

                        this.addButton(x, new InventoryButton(materialData.getGuiMaterial()) {
                            @Override
                            public void onClick(InventoryClickEvent clickEvent) {

                            }
                        });
                    }

                    super.fillButtons();
                }

                @Override
                public Inventory createInventory() {
                    return Bukkit.createInventory(null, 54, convert("&c&lCompressors &7(&bPage %s&7").formatted(finalCurrentPage + 1));
                }
            });

        }
    }

    public final ItemStack applyCompressorLoreTo(
            final ItemStack displayItem,
            final AutoCompressorMaterial materialData,
            final CompressorPlayer compressorPlayer) {
        assert displayItem.getItemMeta() != null;
        final AutoCompressorCosts costs = materialData.getAutoCompressorCosts();

        /* Player doesn't have the auto compress tag, show the buy item for them */
        if (!materialData.canCompress(compressorPlayer)) {
            final ItemStack costItem = costs.getItem(this.moduleInstance);
            String costDisplayName;
            if (costItem.hasItemMeta()) {
                costDisplayName = Objects.requireNonNull(costItem.getItemMeta()).getDisplayName();
            } else costDisplayName = StringUtils.getPrettyMaterialName(costItem.getType());
            return ItemUtils.createItemStack(
                    Material.BARRIER,
                    displayItem.getItemMeta().getDisplayName(),
                    "&f ",
                    "&cYou don't have this compressor unlocked yet!",
                    "&f",
                    "&3Buy Price",
                    "&7x&b%s&r &f%s".formatted(costs.getMaterialCost(), costDisplayName),
                    "&7x&b%s &eExperience Levels".formatted(costs.getLevelCost()),
                    "&f",
                    "&eClick&7 to purchase!"
            );
        }

        if (materialData.hasCompressorEnabled(compressorPlayer)) {

        } else {
            /* Player doesn't have the compressor enabled, show the click to enable item for them */
            
        }

    }

}
