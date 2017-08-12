package me.waffle.gameapi.commands;

import me.waffle.gameapi.GameAPI;
import me.waffle.gameapi.managers.MapManager;
import me.waffle.gameapi.map.Map;
import me.waffle.gameapi.util.LocationUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetupCommand implements CommandExecutor {

    MapManager manager = (MapManager) GameAPI.getApi().getManager(MapManager.class);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (command.getName().equalsIgnoreCase("addspawn")) {

            if (!(commandSender instanceof Player)) {
                return true;
            }

            Player player = (Player) commandSender;

            if (!player.hasPermission("gameapi.setup")) {
                player.sendMessage("No permissions");
                return true;
            }

            Location loc = player.getLocation();

            if (args.length != 1) {
                player.sendMessage("/addspawn [mapID]");
                return true;
            }

            String mapName = args[0];
            ConfigurationSection section = manager.getMapsConfig().getConfigurationSection("maps." + mapName);
            if (section == null) {
                player.sendMessage("No map!");
                return true;
            }

            List<String> spawns = section.getStringList("spawns");
            spawns.add(LocationUtil.serialize(loc));
            section.set("spawns", spawns);
            manager.saveMaps();
            Map map = manager.getMap(mapName);

            List<Location> locs = new ArrayList<>();

            spawns.forEach(s1 -> locs.add(LocationUtil.deserialize(s1)));

            map.setSpawns(locs);

            map.setMaxPlayers(map.getSpawns().size());
            player.sendMessage("Added spawn to map.");
        } else if (command.getName().equalsIgnoreCase("setlobby")) {
            if (!(commandSender instanceof Player)) {
                return true;
            }

            Player player = (Player) commandSender;

            if (!player.hasPermission("gameapi.setup")) {
                player.sendMessage("No permissions");
                return true;
            }

            Location loc = player.getLocation();

            if (args.length != 1) {
                player.sendMessage("/setlobby [mapID]");
                return true;
            }

            String mapName = args[0];
            ConfigurationSection section = manager.getMapsConfig().getConfigurationSection("maps." + mapName);
            if (section == null) {
                player.sendMessage("No map!");
                return true;
            }

            section.set("lobby", LocationUtil.serialize(loc));
            manager.saveMaps();
            player.sendMessage("Added lobby to map.");
        } else if (command.getName().equalsIgnoreCase("create")) {


            if (!(commandSender instanceof Player)) {
                return true;
            }

            Player player = (Player) commandSender;

            if (!player.hasPermission("gameapi.setup")) {
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "/create [mapID]");
                return true;
            }

            String id = args[0];

            if (manager.getMap(id) != null) {
                player.sendMessage(ChatColor.RED + "Map already exists!");
                return true;
            }

            manager.getMapsConfig().set("maps." + id + ".lobby", LocationUtil.serialize(player.getLocation()));
            manager.getMapsConfig().set("maps." + id + ".minPlayers", 4);
            Map map = new Map(id, new ArrayList<>(), player.getLocation(), 4, 12);
            manager.getLoadedMaps().add(map);

            manager.saveMaps();
            player.sendMessage(ChatColor.GREEN + "Created a new Map, don't forget to set it up!");
        }
        return false;
    }
}
