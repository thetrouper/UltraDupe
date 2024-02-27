package me.trouper.ultradupe.data.GUIs;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiItems implements Global {
    static Global g = Global.instance;

    public static final ItemStack nextArrow = ItemBuilder.create()
            .material(Material.ARROW)
            .name(g.color("Next Page"))
            .build();

    public static final ItemStack backArrow = ItemBuilder.create()
            .material(Material.ARROW)
            .name(g.color("Previous Page"))
            .build();
}
