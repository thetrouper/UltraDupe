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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DupeBanGUI extends CustomGui implements Global {
    static Global g = Global.instance;

    public static List<UUID> isInGUI = new ArrayList<>();

    public DupeBanGUI(String title, int size, InvAction mainAction, Map<Integer, InvAction> slotActions, Map<Integer, ItemStack> slotDisplays, CreateAction createAction, CloseAction closeAction) {
        super(title, size, mainAction, slotActions, slotDisplays, createAction, closeAction);
    }

    public static void refreshGUI(Player p) {
        p.openInventory(home.getInventory());
    }

    public static final CustomGui home = CustomGui.create()
            .title(g.color("&#5A88FF&lU&#698CFF&ll&#7890FF&lt&#8794FF&lr&#9699FF&la&#A59DFF&lD&#B4A1FF&lu&#C3A5FF&lp&#D2A9FF&le &7&l| &#D589FFD&#CB99FFu&#C2A9FFp&#B8B9FFe &#AFCAFFB&#A5DAFFa&#9CEAFFn&#96F3FBs &#93F5F2E&#91F7EAd&#8EF9E1i&#8CFBD9t&#89FDD0o&#87FFC8r"))
            .size(54)
            .defineMain(DupeBanGUI::handleMainClick)
            .onDefine(DupeBanGUI::handleDefine)
            .define(45, GuiItems.BACK_ARROW, event -> event.getWhoClicked().sendMessage(Text.prefix("You clicked back")))
            .define(53, GuiItems.NEXT_ARROW, event -> event.getWhoClicked().sendMessage(Text.prefix("You clicked next")))
            .define(49, GuiItems.INFO_ICON, event -> {})
            .build();

    private static void handleMainClick(InventoryClickEvent e) {
        SoundPlayer deny = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
        SoundPlayer allow = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        e.setCancelled(true);

        Player who = (Player) e.getWhoClicked();
        int where = e.getSlot();

        if (e.getClickedInventory().getItem(where) == null || e.getClickedInventory().getItem(where).isEmpty()) {
            deny.play(who);
            return;
        }

        ItemStack what = e.getClickedInventory().getItem(where);

        if (!isInGUI.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            deny.play(who);
            e.getWhoClicked().setHealth(0);
            return;
        }

        if (!e.getCursor().isEmpty()) {
            allow.play(who);
            UltraDupe.dupeBanStorage.bannedMaterials.add(e.getCursor().getType());
            who.sendMessage(Text.prefix("You have &cadded&7 the material &e%s&7 from the dupe bans.".formatted(Text.cleanName(what.getType().toString()))));
            who.closeInventory();
            refreshGUI(who);
            isInGUI.add(who.getUniqueId());
            UltraDupe.dupeBanStorage.save();
        }

        if ((e.getSlot() > 44 && e.getSlot() < 54) && what.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
            e.setCancelled(true);
            deny.play(who);
            return;
        }

        if (e.getSlot() > 0 && e.getSlot() < 45) {
            allow.play(who);
            UltraDupe.dupeBanStorage.bannedMaterials.remove(what.getType());
            who.sendMessage(Text.prefix("You have &aremoved&7 the material &e%s&7 from the dupe bans.".formatted(Text.cleanName(what.getType().toString()))));
            who.closeInventory();
            refreshGUI(who);
            isInGUI.add(who.getUniqueId());
            UltraDupe.dupeBanStorage.save();
        }
    }

    private static void handleDefine(Inventory inv) {
        int pointer = 0;
        for (Material bannedMaterial : UltraDupe.dupeBanStorage.bannedMaterials) {
            if (pointer > 44) return;
            inv.setItem(pointer, ItemBuilder.create()
                    .material(bannedMaterial)
                    .lore("")
                    .lore(Global.instance.color("&7(Light click to remove)"))
                    .build());
            pointer++;
        }
        for (int i = 45; i < 53; i++) {
            inv.setItem(i,GuiItems.BLANK);
        }
    }




}
