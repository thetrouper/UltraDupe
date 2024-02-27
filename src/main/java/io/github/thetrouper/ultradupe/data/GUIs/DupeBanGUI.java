package io.github.thetrouper.ultradupe.data.GUIs;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import io.github.itzispyder.pdk.plugin.gui.CustomGui;
import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import io.github.thetrouper.ultradupe.UltraDupe;
import io.github.thetrouper.ultradupe.data.DupeBanStorage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DupeBanGUI implements Global {
    static Global g = Global.instance;

    public static List<UUID> isInGUI = new ArrayList<>();

    public static final CustomGui home = CustomGui.create()
            .title(g.color("&#5A88FF&lU&#698CFF&ll&#7890FF&lt&#8794FF&lr&#9699FF&la&#A59DFF&lD&#B4A1FF&lu&#C3A5FF&lp&#D2A9FF&le &7&l | &#FF5A5A&l&nP&#F25C62&l&nr&#E65E6B&l&ne&#D95F73&l&nm&#CC617B&l&ni&#BF6383&l&nu&#B3658C&l&nm &#A66794&l&nD&#99689C&l&nu&#8C6AA4&l&np&#806CAD&l&ne &#736EB5&l&nB&#6670BD&l&na&#5971C5&l&nn&#4D73CE&l&ns &#4075D6&l&nE&#3377DE&l&nd&#2679E6&l&ni&#1A7AEF&l&nt&#0D7CF7&l&no&#007EFF&l&nr"))
            .size(54)
            .defineMain(e -> {
                e.setCancelled(true);
                if (!isInGUI.contains(e.getWhoClicked().getUniqueId())) {
                    e.setCancelled(true);
                    e.getWhoClicked().setHealth(0);
                    return;
                }
                if (e.getClickedInventory().getItem(e.getSlot()).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                    SoundPlayer deny = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
                    deny.play((Player) e.getWhoClicked());
                }
            })
            .onDefine(e -> {

            })
            .define(54,GuiItems.nextArrow,event -> {

            })
            .build();

}
