package me.trouper.ultradupe.data.config;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;

import java.io.File;

public class Config implements JsonSerializable<Config> {

    @Override
    public File getFile() {
        File file = new File("plugins/UltraDupe/main-config.json");
        file.getParentFile().mkdirs();
        return file;
    }

    public String prefix = "&9UltraDupe> &7";
    public boolean debugMode = false;
    public Plugin plugin = new Plugin();

    public class Plugin {
        public int dupeCooldown = 0;
        public int multCooldown = 0;
        public int maxDupe = 128;
        public int maxMult = 10;
    }
}
