package me.trouper.ultradupe.events;

import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.ultradupe.data.GUIs.DupeBanGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiEvents implements CustomListener {
    @EventHandler
    private void onGuiClose(InventoryCloseEvent e) {
        DupeBanGUI.isInGUI.remove(e.getPlayer().getUniqueId());
    }
}
