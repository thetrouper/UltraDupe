package me.trouper.ultradupe.data.GUIs;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiItems implements Global {
    static Global g = Global.instance;

    public static final ItemStack NEXT_ARROW = ItemBuilder.create()
            .material(Material.ARROW)
            .name(g.color("&bNext Page &7➔"))
            .build();

    public static final ItemStack BACK_ARROW = ItemBuilder.create()
            .material(Material.ARROW)
            .name(g.color("&7\uD83E\uDC78 &bPrevious Page"))
            .build();
    public static final ItemStack INFO_ICON = ItemBuilder.create()
            .material(Material.NAME_TAG)
            .name(g.color("&b&lInfo"))
            .lore(g.color("&3➥ &7(Drag-n-drop) &fAdds the item"))
            .lore(g.color("&3➥ &7(Left-Click) &fRemoves the item"))
            .lore(g.color(""))
            .lore(g.color("Page 1/1"))
            .customModelData(1)
            .build();

    public static final ItemStack BLANK = ItemBuilder.create()
            .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .name(g.color("&7"))
            .build();
}
