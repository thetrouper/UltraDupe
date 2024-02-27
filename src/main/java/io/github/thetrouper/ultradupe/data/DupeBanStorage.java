package io.github.thetrouper.ultradupe.data;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import org.bukkit.Material;

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
    public List<Material> dupeBans = Arrays.asList(
            Material.AZALEA,
            Material.BIG_DRIPLEAF
    );
}
