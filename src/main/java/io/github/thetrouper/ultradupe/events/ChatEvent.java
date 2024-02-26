package io.github.thetrouper.ultradupe.events;

import io.github.itzispyder.pdk.events.CustomListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements CustomListener {
    @EventHandler
    private void onChat(AsyncPlayerChatEvent e) {
        handleChatEvent(e);
    }
    public static void handleChatEvent(AsyncPlayerChatEvent e) {

    }


}
