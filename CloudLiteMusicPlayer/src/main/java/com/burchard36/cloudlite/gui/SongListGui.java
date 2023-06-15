package com.burchard36.cloudlite.gui;

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
import org.bukkit.inventory.ItemStack;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class SongListGui extends PaginatedInventory {
    protected final CloudLiteMusicPlayer moduleInstance;
    protected final HeadDatabaseAPI headDatabaseAPI;
    protected final GuiManager guiManager;
    protected final MusicPlayer musicPlayer;

    public SongListGui(CloudLiteMusicPlayer moduleInstance) {
        this.moduleInstance = moduleInstance;
        this.musicPlayer = this.moduleInstance.getMusicPlayer();
        this.guiManager = moduleInstance.getPluginInstance().getGuiManager();
        this.headDatabaseAPI = this.moduleInstance.getHeadDatabaseAPI();
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
                    super.onInventoryClick(event);
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

                    this.addButton(46, backgroundItem());
                    this.addButton(47, backgroundItem());
                    this.addButton(48, backgroundItem());
                    this.addButton(50, backgroundItem());
                    this.addButton(51, backgroundItem());
                    this.addButton(52, backgroundItem());
                    this.addButton(53, new InventoryButton(getNextButton()) {
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
                            guiManager.openPaginatedTo(player, nextPage, SongListGui.this);
                        }
                    });
                    this.addButton(45, new InventoryButton(getPreviousButton()) {
                        @Override
                        public void onClick(InventoryClickEvent clickEvent) {
                            final Player player = (Player) clickEvent.getWhoClicked();
                            int previousPage = finalCurrentPage - 1;
                            if (finalTotalPages == 1 || previousPage < 0) {
                                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                                player.sendMessage(convert("&cThis is the first page"));
                                return;
                            }

                            player.closeInventory();
                            guiManager.openPaginatedTo(player, previousPage, SongListGui.this);
                        }
                    });

                    this.addButton(49, new InventoryButton(getNextSongButton()) {
                        @Override
                        public void onClick(InventoryClickEvent clickEvent) {
                            musicPlayer.playFor((Player) clickEvent.getWhoClicked());
                        }
                    });

                    super.fillButtons(); // actually sets them in the inventory
                }

            });
        }
    }

    private InventoryButton backgroundItem() {
        return new InventoryButton(ItemUtils.createItemStack(Material.CYAN_STAINED_GLASS_PANE, "&f ", null)) {
            @Override
            public void onClick(InventoryClickEvent clickEvent) {

            }
        };
    }

    private ItemStack getNextButton() {
        return ItemUtils.modify(headDatabaseAPI.getItemHead("60723"), "&a&lNEXT PAGE");
    }
    private ItemStack getPreviousButton() {
        return ItemUtils.modify(headDatabaseAPI.getItemHead("60721"), "&a&lPREVIOUS PAGE");
    }

    private ItemStack getNextSongButton() {
        return ItemUtils.modify(headDatabaseAPI.getItemHead("32818"), "&b&lPLAY NEXT SONG");
    }
}
