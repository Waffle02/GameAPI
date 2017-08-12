package me.waffle.gameapi.managers;

import lombok.Data;
import me.waffle.gameapi.GameAPI;
import me.waffle.gameapi.map.Map;
import me.waffle.gameapi.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class MapManager implements Manager {

    private List<Map> loadedMaps = new ArrayList<>();
    private File mapsFile;
    private FileConfiguration mapsConfig;

    public MapManager() {
        this.mapsFile = new File(GameAPI.getApi().getDataFolder(), "maps.yml");

        if (!mapsFile.exists()) {
            try {
                mapsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mapsConfig = YamlConfiguration.loadConfiguration(mapsFile);

        if (mapsConfig.getConfigurationSection("maps") == null) {
            System.out.println("NULL");
            return;
        }

        for (String map : mapsConfig.getConfigurationSection("maps").getKeys(false)) {

            List<Location> spawns = new ArrayList<>();

            for (String s : mapsConfig.getStringList("maps." + map + ".spawns")) {
                Location loc = LocationUtil.deserialize(s);
                spawns.add(loc);
            }

            int minPlayers = mapsConfig.getInt("maps." + map + ".minPlayers");
            Location lobby = LocationUtil.deserialize(mapsConfig.getString("maps." + map + ".lobby"));

            this.loadedMaps.add(new Map(map, spawns, lobby, minPlayers, spawns.size()));
        }
    }

    public void saveMaps() {
        try {
            mapsConfig.save(mapsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getMap(String name) {
        for (Map map : loadedMaps) {
            if (map.getName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }
}