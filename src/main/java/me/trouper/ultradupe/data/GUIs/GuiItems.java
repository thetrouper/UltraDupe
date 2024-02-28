package me.trouper.ultradupe.data.GUIs;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiItems implements Global {
    static Global g = Global.instance;

    public static final ItemStack NEXT_ARROW = ItemBuilder.create()
            .material(Material.ARROW)
            .name(g.color("Next Page"))
            .build();

    public static final ItemStack BACK_ARROW = ItemBuilder.create()
            .material(Material.ARROW)
            .name(g.color("Previous Page"))
            .build();
    public static final ItemStack INFO_ICON = ItemBuilder.create()
            .material(Material.NAME_TAG)
            .name(g.color("Info"))
            .lore(g.color("Right click an item in your inventory to add it"))
            .lore(g.color("Left click any item to remove it"))
            .build();

    public static final ItemStack BLANK = ItemBuilder.create()
            .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .name("")
            .build();
}
