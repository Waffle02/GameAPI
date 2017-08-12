package me.waffle.gameapi.team;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.waffle.gameapi.game.Winnable;
import me.waffle.gameapi.player.GamePlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Team implements Winnable {

    private final String name;
    private final int maxPlayers;
    private List<UUID> members = new ArrayList<>();

    private boolean friendlyFire = false;

    private ChatColor color = ChatColor.WHITE;

    public void addPlayer(GamePlayer player) {
        if (maxPlayers == -1) {
            members.add(player.getUUID());
            return;
        }
        if (members.size() >= maxPlayers) {
            player.getBukkitPlayer().sendMessage("Cannot join a team.... is it full?");
            return;
        }

        members.add(player.getUUID());
        player.getBukkitPlayer().sendMessage("Joined team: " + name);
    }

    public final void setChatColor(ChatColor color) {
        this.color = color;
    }

    public void removePlayer(GamePlayer player) {
        members.remove(player.getUUID());
    }

    @Override
    public String getWinnableName() {
        return name;
    }
}
