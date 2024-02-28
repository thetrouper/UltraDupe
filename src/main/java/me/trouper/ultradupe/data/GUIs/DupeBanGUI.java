package me.trouper.ultradupe.data.GUIs;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import io.github.itzispyder.pdk.plugin.gui.CustomGui;
import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import me.trouper.ultradupe.UltraDupe;
import me.trouper.ultradupe.server.util.Text;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DupeBanGUI implements Global {
    static Global g = Global.instance;

    public static List<UUID> isInGUI = new ArrayList<>();

    public static final CustomGui home = CustomGui.create()
            .title(g.color("&#5A88FF&lU&#698CFF&ll&#7890FF&lt&#8794FF&lr&#9699FF&la&#A59DFF&lD&#B4A1FF&lu&#C3A5FF&lp&#D2A9FF&le &7&l | &#D589FFD&#CB99FFu&#C2A9FFp&#B8B9FFe &#AFCAFFB&#A5DAFFa&#9CEAFFn&#96F3FBs &#93F5F2E&#91F7EAd&#8EF9E1i&#8CFBD9t&#89FDD0o&#87FFC8r"))
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
            .onDefine(i -> {
                int pointer = 0;
                for (Material bannedMaterial : UltraDupe.dupeBanStorage.bannedMaterials) {
                    if (pointer > 44) return;
                    i.setItem(pointer, ItemBuilder.create()
                                    .material(bannedMaterial)
                                    .lore("")
                                    .lore(Global.instance.color("&7(Light click to remove)"))
                            .build());
                    pointer++;
                }
            })
            .define(45,GuiItems.backArrow,event-> {
                event.getWhoClicked().sendMessage(Text.prefix("You clicked back"));
            })
            .define(53,GuiItems.nextArrow,event -> {
                event.getWhoClicked().sendMessage(Text.prefix("You clicked next"));
            })
            .build();

}
