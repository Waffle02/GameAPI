package me.waffle.gameapi.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

    public static Location deserialize(String s) {

        String[] split = s.split(",");

        World world = Bukkit.getWorld(split[0]);
        int x = Integer.valueOf(split[1]);
        int y = Integer.valueOf(split[2]);
        int z = Integer.valueOf(split[3]);

        return new Location(world, x, y, z);
    }

    public static String serialize(Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }
}

