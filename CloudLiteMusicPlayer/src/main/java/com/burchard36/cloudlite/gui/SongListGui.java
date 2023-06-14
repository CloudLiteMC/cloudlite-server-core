package com.burchard36.cloudlite.gui;

import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.CloudLiteMusicPlayer;
import com.burchard36.cloudlite.MusicPlayer;
import com.burchard36.cloudlite.config.SongData;
import com.burchard36.cloudlite.gui.buttons.InventoryButton;
import com.burchard36.cloudlite.utils.ItemUtils;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class SongListGui extends PaginatedInventory {
    protected final CloudLiteMusicPlayer moduleInstance;
    protected final MusicPlayer musicPlayer;

    public SongListGui(CloudLiteMusicPlayer moduleInstance) {
        this.moduleInstance = moduleInstance;
        this.musicPlayer = this.moduleInstance.getMusicPlayer();

        int totalPages = musicPlayer.getMusicConfig().getSongDataList().size() / 45;
        if (totalPages == 0) totalPages = 1;

        for (int currentPage = 0; currentPage < totalPages; currentPage++) {
            final int finalCurrentPage = currentPage;
            int finalTotalPages = totalPages;
            this.addPage(finalCurrentPage, new InventoryGui() {
                @Override
                public Inventory createInventory() {
                    return Bukkit.createInventory(
                            null,
                            54,
                            convert("&3&lSong List Page %s").formatted(finalCurrentPage));
                }

                @Override
                public void onInventoryClick(InventoryClickEvent event) {
                    event.setCancelled(true);
                }

                @Override
                public void fillButtons() {
                    int startAt = finalCurrentPage * 45; // only display 45 songs per page

                    for (int x = 0; x < musicPlayer.getMusicConfig().getSongDataList().size(); x++) {
                        if (startAt > x) continue;
                        final SongData songData = musicPlayer.getMusicConfig().getSongDataList().get(x);

                        this.addButton(x, new InventoryButton(songData.getDisplayItem()) {
                            @Override
                            public void onClick(InventoryClickEvent clickEvent) {
                                musicPlayer.forcePlaySongFor((Player) clickEvent.getWhoClicked(), songData);
                            }
                        });
                    }

                    this.addButton(52, backgroundItem());
                    this.addButton(53, new InventoryButton(CloudLiteCore.getHeadDatabaseAPI().getItemHead("60723")) {
                        @Override
                        public void onClick(InventoryClickEvent clickEvent) {
                            final Player player = (Player) clickEvent.getWhoClicked();
                            int nextPage = finalCurrentPage + 1;
                            if (finalTotalPages == 1 || nextPage >= finalTotalPages) {
                                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                                player.sendMessage(convert("&cThis is the final page"));
                                return;
                            }

                            player.closeInventory();
                            CloudLiteCore.getGuiManager().openPaginatedTo();
                        }
                    });
                }

            });
        }
    }

    private InventoryButton backgroundItem() {
        return new InventoryButton(ItemUtils.createItemStack(Material.CYAN_STAINED_GLASS_PANE, "&f ", null) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }
}
