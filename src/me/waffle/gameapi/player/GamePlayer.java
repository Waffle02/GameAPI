package me.waffle.gameapi.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.waffle.gameapi.game.Winnable;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class GamePlayer implements Winnable {

    @Getter
    private final Player bukkitPlayer;

    public UUID getUUID() {
        return bukkitPlayer.getUniqueId();
    }

    public void sendMessage(String msg) {
        bukkitPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public void teleport(Location location) {
        bukkitPlayer.teleport(location);
    }

    public void teleport(Entity entity) {
        teleport(entity.getLocation());
    }

    public void reset() {
        bukkitPlayer.setHealth(bukkitPlayer.getMaxHealth());
        bukkitPlayer.getInventory().clear();
    }

    @Override
    public String getWinnableName() {
        return bukkitPlayer.getDisplayName();
    }
}
