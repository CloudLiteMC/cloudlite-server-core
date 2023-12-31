package com.burchard36.cloudlite.config;

import com.burchard36.cloudlite.CloudLiteCore;
import com.burchard36.cloudlite.CompressorPlayer;
import com.burchard36.cloudlite.utils.ItemUtils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static java.util.Objects.requireNonNull;

public class AutoCompressorMaterial {

    @Getter
    protected final AutoCompressorCosts autoCompressorCosts;
    @Getter
    protected final AutoCompressorCosts autoSuperCompressorCosts;
    @Getter
    protected final AutoCompressorCosts autoMegaCompressorCosts;
    private final ItemStack compressedItem;
    private final ItemStack superCompressedItem;
    private final ItemStack megaCompressedItem;

    @Getter
    private final Material thisMaterial;
    @Getter
    private final Material guiMaterial;
    @Getter
    private final String guiName;


    public AutoCompressorMaterial(final ConfigurationSection config, final Material material) {
        this.thisMaterial = material;
        this.compressedItem = this.getItemStack(requireNonNull(config.getConfigurationSection("Compressor")), this.getCompressedKey());
        this.superCompressedItem = this.getItemStack(requireNonNull(config.getConfigurationSection("SuperCompressor")), this.getSuperCompressedKey());
        this.megaCompressedItem = this.getItemStack(requireNonNull(config.getConfigurationSection("MegaCompressor")), this.getMegaCompressedKey());

        this.autoCompressorCosts = new AutoCompressorCosts(requireNonNull(config.getConfigurationSection("Compressor.BuyPrice")));
        this.autoSuperCompressorCosts = new AutoCompressorCosts(requireNonNull(config.getConfigurationSection("SuperCompressor.BuyPrice")));
        this.autoMegaCompressorCosts = new AutoCompressorCosts(requireNonNull(config.getConfigurationSection("MegaCompressor.BuyPrice")));
        this.guiMaterial = Material.getMaterial(requireNonNull(config.getString("Gui.Material")));
        this.guiName = config.getString("Gui.Name");
    }

    private ItemStack getItemStack(final ConfigurationSection config, final NamespacedKey compressedKey) {
        final String skullTexture = config.getString("HeadTexture");
        final String displayName = config.getString("DisplayName");
        final ItemStack itemStack = ItemUtils.createSkull(skullTexture, displayName, (String) null);
        assert itemStack.getItemMeta() != null;
        final ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(compressedKey, PersistentDataType.BYTE, (byte) 0x00);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack getCompressedItem() {
        return this.compressedItem.clone();
    }

    public ItemStack getSuperCompressedItem() {
        return this.superCompressedItem.clone();
    }

    public ItemStack getMegaCompressedItem() {
        return this.megaCompressedItem.clone();
    }

    /**
     * Simple flag NamespacedKey that is meant to be used as a flag
     *
     * if a player has this key, it means they have unlocked the auto compressor for this material type
     * @return {@link NamespacedKey}
     */
    public NamespacedKey getCompressedKey() {
        return new NamespacedKey(CloudLiteCore.INSTANCE, "COMPRESSED_%s".formatted(this.thisMaterial.name()));
    }

    /**
     * Simple flag NamespacedKey that is meant to be used as a flag
     *
     * If a play has this key, it means they have unlocked the super compressor for this material type
     * @return {@link NamespacedKey}
     */
    public NamespacedKey getSuperCompressedKey() {
        return new NamespacedKey(CloudLiteCore.INSTANCE, "SUPER_COMPRESSED_%s".formatted(this.thisMaterial.name()));
    }

    /**
     * Simple flag NamespacedKey that is meant to be used as a flag
     *
     * If a play has this key, it means they have unlocked the mega compressor for this material type
     * @return {@link NamespacedKey}
     */
    public NamespacedKey getMegaCompressedKey() {
        return new NamespacedKey(CloudLiteCore.INSTANCE, "MEGA_COMPRESSED_%s".formatted(this.thisMaterial.name()));
    }

    /**
     * Simple flag NamespacedKey that is meant to be used as a flag
     *
     * If a player has this key, it means the compressor for this type has been enabled for that player
     * @return {@link NamespacedKey}
     */
    public NamespacedKey compressorEnabledKey() {
        return new NamespacedKey(CloudLiteCore.INSTANCE, this.getCompressedKey().getKey() + "_ENABLED");
    }

    /**
     * Simple flag NamespacedKey that is meant to be used as a flag
     *
     * If a player has this key, it means the super compressor for this type has been enabled for that player
     * @return {@link NamespacedKey}
     */
    public NamespacedKey superCompressorEnabledKey() {
        return new NamespacedKey(CloudLiteCore.INSTANCE, this.getSuperCompressedKey().getKey() + "_ENABLED");
    }

    /**
     * Simple flag NamespacedKey that is meant to be used as a flag
     *
     * If a player has this key, it means the mega compressor for this type has been enabled for that player
     * @return {@link NamespacedKey}
     */
    public NamespacedKey megaCompressorEnabledKey() {
        return new NamespacedKey(CloudLiteCore.INSTANCE, this.getMegaCompressedKey().getKey() + "_ENABLED");
    }

    /**
     * Checks if a player has access to auto compress this material
     * @param compressorPlayer {@link CompressorPlayer}
     * @return true if they have access to compress the material
     */
    public final boolean canAutoCompress(final @NonNull CompressorPlayer compressorPlayer) {
        return compressorPlayer.hasKey(this.getCompressedKey(), PersistentDataType.BYTE);
    }

    /**
     * Grants a player access to this Materials Compressor
     * @param compressorPlayer {@link CompressorPlayer}
     */
    public final void giveCompressorAccess(final @NonNull CompressorPlayer compressorPlayer) {
        compressorPlayer.giveKey(this.getCompressedKey(), PersistentDataType.BYTE, (byte) 0x00);
        this.setCompressorEnabled(compressorPlayer, true);
    }

    /**
     * Grants the compressor enabled flag to the player if they pass {@link AutoCompressorMaterial#canAutoCompress(CompressorPlayer)} checks
     * @param compressorPlayer {@link CompressorPlayer}
     * @param enable true if you want the compressor to be enabled
     */
    public final void setCompressorEnabled(final @NonNull CompressorPlayer compressorPlayer, boolean enable) {
        if (!this.canAutoCompress(compressorPlayer)) return;
        if (enable && this.hasCompressorEnabled(compressorPlayer)) return;
        if (!enable && this.hasCompressorEnabled(compressorPlayer)) {
            compressorPlayer.removeKey(this.compressorEnabledKey());
            return;
        }
        compressorPlayer.giveKey(this.compressorEnabledKey(), PersistentDataType.BYTE, (byte) 0x00);
    }

    /**
     * Checks if the player has the compressor active
     * @param compressorPlayer {@link CompressorPlayer}
     * @return true if they have the active flag for this materials compressor
     */
    public final boolean hasCompressorEnabled(final @NonNull CompressorPlayer compressorPlayer) {
        return compressorPlayer.hasKey(this.compressorEnabledKey(), PersistentDataType.BYTE);
    }


    /**
     * Checks if a player has access to auto super compress this material
     * @param compressorPlayer {@link CompressorPlayer}
     * @return true if they have access to super compress the material
     */
    public final boolean canAutoSuperCompress(final @NonNull CompressorPlayer compressorPlayer) {
        return compressorPlayer.hasKey(this.getSuperCompressedKey(), PersistentDataType.BYTE);
    }

    /**
     * Grants a player access to this Materials Super Compressor
     * @param compressorPlayer {@link CompressorPlayer}
     */
    public final void giveSuperCompressorAccess(final @NonNull CompressorPlayer compressorPlayer) {
        compressorPlayer.giveKey(this.getSuperCompressedKey(), PersistentDataType.BYTE, (byte) 0x00);
    }

    /**
     * Grants the super compressor enabled flag to the player if they pass {@link AutoCompressorMaterial#canAutoSuperCompress(CompressorPlayer)} checks
     * @param compressorPlayer {@link CompressorPlayer}
     * @param enable true if you want the super compressor to be enabled
     */
    public final void setSuperCompressorEnabled(final @NonNull CompressorPlayer compressorPlayer, boolean enable) {
        if (!this.canAutoSuperCompress(compressorPlayer)) return;
        if (enable && this.hasSuperCompressorEnabled(compressorPlayer)) return;
        if (!enable && this.hasSuperCompressorEnabled(compressorPlayer)) {
            compressorPlayer.removeKey(this.superCompressorEnabledKey());
            return;
        }
        compressorPlayer.giveKey(this.superCompressorEnabledKey(), PersistentDataType.BYTE, (byte) 0x00);
    }

    /**
     * Checks if the player has the super compressor active
     * @param compressorPlayer {@link CompressorPlayer}
     * @return true if they have the active flag for this materials super compressor
     */
    public final boolean hasSuperCompressorEnabled(final @NonNull CompressorPlayer compressorPlayer) {
        return compressorPlayer.hasKey(this.superCompressorEnabledKey(), PersistentDataType.BYTE);
    }

    /**
     * Checks if a player has access to auto mega compress this material
     * @param compressorPlayer {@link CompressorPlayer}
     * @return true if they have access to mega compress the material
     */
    public final boolean canAutoMegaCompress(final @NonNull CompressorPlayer compressorPlayer) {
        return compressorPlayer.hasKey(this.getMegaCompressedKey(), PersistentDataType.BYTE);
    }

    /**
     * Grants a player access to this Materials Mega Compressor
     * @param compressorPlayer {@link CompressorPlayer}
     */
    public final void giveMegaCompressorAccess(final @NonNull CompressorPlayer compressorPlayer) {
        compressorPlayer.giveKey(this.getMegaCompressedKey(), PersistentDataType.BYTE, (byte) 0x00);
    }

    /**
     * Grants the mega compressor enabled flag to the player if they pass {@link AutoCompressorMaterial#canAutoMegaCompress(CompressorPlayer)} checks
     * @param compressorPlayer {@link CompressorPlayer}
     * @param enable true if you want the mega compressor to be enabled
     */
    public final void setMegaCompressorEnabled(final @NonNull CompressorPlayer compressorPlayer, boolean enable) {
        if (!this.canAutoMegaCompress(compressorPlayer)) return;
        if (enable && this.hasMegaCompressorEnabled(compressorPlayer)) return;
        if (!enable && this.hasMegaCompressorEnabled(compressorPlayer)) {
            compressorPlayer.removeKey(this.megaCompressorEnabledKey());
            return;
        }
        compressorPlayer.giveKey(this.megaCompressorEnabledKey(), PersistentDataType.BYTE, (byte) 0x00);
    }

    /**
     * Checks if the player has the mega compressor active
     * @param compressorPlayer {@link CompressorPlayer}
     * @return true if they have the active flag for this materials mega compressor
     */
    public final boolean hasMegaCompressorEnabled(final @NonNull CompressorPlayer compressorPlayer) {
        return compressorPlayer.hasKey(this.megaCompressorEnabledKey(), PersistentDataType.BYTE);
    }

    public final boolean isCompressed(final ItemStack itemStack) {
        return requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer()
                .has(this.getCompressedKey(), PersistentDataType.BYTE);
    }

    public final boolean isSuperCompressed(final ItemStack itemStack) {
        return requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer()
                .has(this.getSuperCompressedKey(), PersistentDataType.BYTE);
    }

    public final boolean isMegaCompressed(final ItemStack itemStack) {
        return requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer()
                .has(this.getMegaCompressedKey(), PersistentDataType.BYTE);
    }

    public ItemStack getGuiItem() {
        return ItemUtils.createItemStack(this.guiMaterial, this.guiName, null);
    }

}
