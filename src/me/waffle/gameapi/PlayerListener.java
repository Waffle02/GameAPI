package me.waffle.gameapi;

import me.waffle.gameapi.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    PlayerManager playerManager = (PlayerManager) GameAPI.getApi().getManager(PlayerManager.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerManager.onJoin(event);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerManager.onQuit(event);
    }
}

