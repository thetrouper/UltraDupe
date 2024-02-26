package io.github.thetrouper.ultradupe;

import io.github.itzispyder.pdk.PDK;
import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import io.github.thetrouper.ultradupe.cmds.ChatClickCallback;
import io.github.thetrouper.ultradupe.data.config.Config;
import io.github.thetrouper.ultradupe.events.ChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class UltraDupe extends JavaPlugin {

    private static UltraDupe instance;
    private static final File cfgfile = new File("plugins/UltraDupe/main-config.json");
    public static Config config = JsonSerializable.load(cfgfile, Config.class, new Config());
    public static final PluginManager manager = Bukkit.getPluginManager();

    public static final Logger log = Bukkit.getLogger();

    /**
     * Plugin startup logic
     */
    @Override
    public void onEnable() {

        log.info("\n]======------ Pre-load started! ------======[");
        PDK.init(this);
        instance = this;

        log.info("Loading Config...");

        loadConfig();

        startup();
    }

    public void startup() {
        log.info("\n]======----- Loading UltraDupe! -----======[");

        // Plugin startup logic
        log.info("Starting Up! (%s)...".formatted(getDescription().getVersion()));

        // Commands
        new ChatClickCallback().register();

        // Events
        new ChatEvent().register();

        log.info("""
                Finished!
                  _    _ _ _             _____                  \s
                 | |  | | | |           |  __ \\                 \s
                 | |  | | | |_ _ __ __ _| |  | |_   _ _ __   ___\s
                 | |  | | | __| '__/ _` | |  | | | | | '_ \\ / _ \\
                 | |__| | | |_| | | (_| | |__| | |_| | |_) |  __/
                  \\____/|_|\\__|_|  \\__,_|_____/ \\__,_| .__/ \\___|
                                                     | |        \s
                                                     |_|        \s
                                                     
                  ]====---- The only acceptable dupe plugin ----====[""");
    }

    public void loadConfig() {

        // Init
        config = JsonSerializable.load(cfgfile, Config.class,new Config());

        // Save
        config.save();
    }


    /**
     * Plugin shutdown logic
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("UltraDupe has disabled! (%s)".formatted(getDescription().getVersion()));
    }

    public static UltraDupe getInstance() {
        return instance;
    }

}
