package me.waffle.gameapi.map;

import lombok.Data;
import org.bukkit.Location;

import java.util.List;

@Data
public class Map {

    private String name;
    private List<Location> spawns;
    private int minPlayers;
    private int maxPlayers;

    private Location lobby;

    public Map(String name, List<Location> spawns, Location lobby, int minPlayers, int maxPlayers) {
        this.name = name;
        this.spawns = spawns;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.lobby = lobby;
    }
}
