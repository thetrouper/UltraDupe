package io.github.thetrouper.ultradupe.events;

import io.github.itzispyder.pdk.events.CustomListener;
import io.github.thetrouper.ultradupe.data.GUIs.DupeBanGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GuiEvents implements CustomListener {
    @EventHandler
    private void onGuiClose(InventoryCloseEvent e) {
        DupeBanGUI.isInGUI.remove(e.getPlayer().getUniqueId());
    }
}
