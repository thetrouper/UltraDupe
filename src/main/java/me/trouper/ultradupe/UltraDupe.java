package me.trouper.ultradupe;

import io.github.itzispyder.pdk.PDK;
import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.trouper.ultradupe.cmds.DupeBansCommand;
import me.trouper.ultradupe.cmds.DupeCommand;
import me.trouper.ultradupe.cmds.MultiplyCommand;
import me.trouper.ultradupe.cmds.UltraDupeCommand;
import me.trouper.ultradupe.data.DupeBanStorage;
import me.trouper.ultradupe.data.config.Config;
import me.trouper.ultradupe.events.GuiEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class UltraDupe extends JavaPlugin {

    private static UltraDupe instance;
    private static final File cfgfile = new File("plugins/UltraDupe/main-config.json");
    private static final File dupeBans = new File("plugins/UltraDupe/dupebans.json");
    public static Config config = JsonSerializable.load(cfgfile, Config.class, new Config());
    public static DupeBanStorage dupeBanStorage = JsonSerializable.load(dupeBans, DupeBanStorage.class, new DupeBanStorage());
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
        new DupeCommand().register();
        new DupeBansCommand().register();
        new MultiplyCommand().register();
        new UltraDupeCommand().register();

        // Events
        new GuiEvents().register();

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
        dupeBanStorage = JsonSerializable.load(dupeBans, DupeBanStorage.class,new DupeBanStorage());

        // Save
        config.save();
        dupeBanStorage.save();
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
