package me.trouper.ultradupe.data.GUIs;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import io.github.itzispyder.pdk.plugin.gui.CustomGui;
import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import me.trouper.ultradupe.UltraDupe;
import me.trouper.ultradupe.data.DupeBanStorage;
import me.trouper.ultradupe.server.util.ServerUtils;
import me.trouper.ultradupe.server.util.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DupeBanGUI implements Global {
    static Global g = Global.instance;

    public static List<UUID> isInGUI = new ArrayList<>();

    public void refreshGUI(Player p) {
        p.openInventory(home.getInventory());
    }

    public final CustomGui home = CustomGui.create()
            .title(g.color("&#5A88FF&lU&#698CFF&ll&#7890FF&lt&#8794FF&lr&#9699FF&la&#A59DFF&lD&#B4A1FF&lu&#C3A5FF&lp&#D2A9FF&le &7&l| &#D589FFD&#CB99FFu&#C2A9FFp&#B8B9FFe &#AFCAFFB&#A5DAFFa&#9CEAFFn&#96F3FBs &#93F5F2E&#91F7EAd&#8EF9E1i&#8CFBD9t&#89FDD0o&#87FFC8r"))
            .size(54)
            .defineMain(this::handleMainClick)
            .onDefine(inv -> setupPage(inv,1))
            .define(45, GuiItems.BACK_ARROW, event -> decrementPage(event.getClickedInventory()))
            .define(53, GuiItems.NEXT_ARROW, event -> incrementPage(event.getClickedInventory()))
            .build();

    private void handleMainClick(InventoryClickEvent e) {
        Player who = (Player) e.getWhoClicked();

        SoundPlayer deny = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
        SoundPlayer allow = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        e.setCancelled(true);

        if (!isInGUI.contains(e.getWhoClicked().getUniqueId())) {
            deny.play(who);
            who.closeInventory();
            e.getWhoClicked().setHealth(0);
            return;
        }

        int where = e.getSlot();
        ItemStack what = e.getClickedInventory().getItem(where);
        ItemStack with = e.getCursor();
        ItemStack current = e.getCurrentItem();

        //who.sendMessage(Text.prefix("You clicked the item %s while holding %s at the slot %s").formatted(current,with,where));

        if (!with.isEmpty()) {
            if (DupeBanStorage.addMaterial(with.getType())) {
                allow.play(who);
                who.sendMessage(Text.prefix("You have &cadded&7 the material &e%s&7 from the dupe bans.".formatted(Text.cleanName(with.getType().toString()))));
            } else {
                deny.play(who);
                who.sendMessage(Text.prefix("&cUnable to add the material &e%s&c to the dupe bans. It may not be an item, or is already in the list".formatted(Text.cleanName(with.getType().toString()))));
            }

            refreshGUI(who);
            isInGUI.add(who.getUniqueId());
            UltraDupe.dupeBanStorage.save();
            return;
        }

        if (e.getClickedInventory().getItem(where) == null || e.getClickedInventory().getItem(where).isEmpty()) {
            deny.play(who);
            return;
        }

        if ((e.getSlot() > 44 && e.getSlot() < 54) && what.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
            deny.play(who);
            return;
        }

        if (where >= 0 && where < 45) {
            boolean correctLore = false;
            if (what.hasItemMeta() && what.getItemMeta().hasLore()) {
                for (Component component : what.getItemMeta().lore()) {
                    String line = component.toString();
                    if (!line.contains("Left")) continue;
                    correctLore = true;
                }
            }
            if (!correctLore) {
                deny.play(who);
                who.sendMessage(Text.prefix("&cSlow Down! You're preforming incomplete actions on the GUI.".formatted(Text.cleanName(what.getType().toString()))));
            }
            if (correctLore && DupeBanStorage.removeMaterial(what.getType())) {
                allow.play(who);
                who.sendMessage(Text.prefix("You have &aremoved&7 the material &e%s&7 from the dupe bans.".formatted(Text.cleanName(what.getType().toString()))));
            } else {
                deny.play(who);
                if (correctLore) who.sendMessage(Text.prefix("&cThe material &e%s&c is not on the dupe bans.".formatted(Text.cleanName(what.getType().toString()))));
            }

            refreshGUI(who);
            isInGUI.add(who.getUniqueId());
            UltraDupe.dupeBanStorage.save();
        }
    }

    public void incrementPage(Inventory inv) {
        ItemStack info = inv.getItem(49);
        ItemMeta meta = info.getItemMeta();
        int pageNumber = meta.getCustomModelData() + 1;
        ServerUtils.verbose("Incrementing to Page: %s".formatted(pageNumber));

        setupPage(inv,pageNumber);
    }

    public void decrementPage(Inventory inv) {
        ItemStack info = inv.getItem(49);
        ItemMeta meta = info.getItemMeta();
        int pageNumber = meta.getCustomModelData() - 1;
        ServerUtils.verbose("Decrementing to Page: %s".formatted(pageNumber));

        setupPage(inv,pageNumber);
    }

    private void setupPage(Inventory inv, int pageNumber) {
        int maxPages = getMaxPages();
        if (pageNumber < 1) pageNumber = 1;
        if (pageNumber > maxPages) pageNumber = maxPages;
        inv.clear();
        int[] indecies = getListRange(pageNumber);

        int pointer = 0;

        for (int i = indecies[0]; i < indecies[1]; i++) {
            Material bannedMaterial = UltraDupe.dupeBanStorage.bannedMaterials.get(i);
            inv.setItem(pointer, ItemBuilder.create()
                    .material(bannedMaterial)
                    .lore("")
                    .lore(Global.instance.color("&7(Left click to remove)"))
                    .build());
            pointer++;
        }

        for (int i = 45; i < 53; i++) {
            inv.setItem(i,GuiItems.BLANK);
        }

        ItemStack info = ItemBuilder.create()
                .material(Material.NAME_TAG)
                .name(g.color("&b&lInfo"))
                .lore(g.color("&3➥ &7(Drag-n-drop) &fAdds the item"))
                .lore(g.color("&3➥ &7(Left-Click) &fRemoves the item"))
                .lore(g.color(""))
                .lore(g.color("&7Page &2%s&7/&a%s".formatted(pageNumber,maxPages)))
                .customModelData(pageNumber)
                .build();

        inv.setItem(45,GuiItems.BACK_ARROW);
        inv.setItem(49,info);
        inv.setItem(53,GuiItems.NEXT_ARROW);
    }

    private int getMaxPages() {
        int totalMaterials = UltraDupe.dupeBanStorage.bannedMaterials.size();
        int maxPages = (int) Math.ceil((double) totalMaterials / 45);
        ServerUtils.verbose("Calculated max pages: %s".formatted(maxPages));
        return maxPages;
    }

    private int[] getListRange(int page) {
        int total = UltraDupe.dupeBanStorage.bannedMaterials.size();
        int totalPages = (int) Math.ceil((double) total / 45);

        page = Math.min(page,totalPages);
        page = Math.max(page,1);

        int start = (page - 1) * 45;
        int end = Math.min(start + 45, total);
        ServerUtils.verbose("List has %s items. A total of %s pages. The page %s was requested. It starts at %s and ends at %s.".formatted(total,totalPages,page,start,end));

        return new int[]{start,end};
    }


}
