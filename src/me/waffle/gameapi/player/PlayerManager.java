package me.waffle.gameapi.player;

import me.waffle.gameapi.managers.ListenerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements ListenerManager {

    private List<GamePlayer> players = new ArrayList<>();

    public GamePlayer getPlayer(Player player) {
        for (GamePlayer player1 : players) {
            if (player1.getBukkitPlayer().getName().equalsIgnoreCase(player.getName())) {
                return player1;
            }
        }
        return null;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        GamePlayer player = new GamePlayer(event.getPlayer());
        players.add(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        players.remove(getPlayer(event.getPlayer()));
    }
}

