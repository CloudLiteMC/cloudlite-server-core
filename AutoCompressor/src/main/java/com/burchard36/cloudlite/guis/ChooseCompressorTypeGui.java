package com.burchard36.cloudlite.guis;

import com.burchard36.cloudlite.gui.InventoryGui;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import static com.burchard36.cloudlite.utils.StringUtils.convert;

public class ChooseCompressorTypeGui extends InventoryGui {
    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(null, 27, convert("&e&lCompressors"));
    }

    @Override
    public void fillButtons() {



        super.fillButtons();
    }
}
