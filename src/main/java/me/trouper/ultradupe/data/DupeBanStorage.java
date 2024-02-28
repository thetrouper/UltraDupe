package me.trouper.ultradupe.data;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.trouper.ultradupe.data.GUIs.GuiItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DupeBanStorage implements JsonSerializable<DupeBanStorage> {

    @Override
    public File getFile() {
        File file = new File("plugins/UltraDupe/dupebans.json");
        file.getParentFile().mkdirs();
        return file;
    }
    public int bannedModelData = 1111;
    public List<Material> bannedMaterials = Arrays.asList(
            Material.ANCIENT_DEBRIS,
            Material.NETHERITE_INGOT,
            Material.TOTEM_OF_UNDYING
    );
}
