package me.waffle.gameapi;

import lombok.Getter;
import me.waffle.gameapi.commands.JoinCommand;
import me.waffle.gameapi.commands.SetupCommand;
import me.waffle.gameapi.game.Game;
import me.waffle.gameapi.managers.Manager;
import me.waffle.gameapi.managers.MapManager;
import me.waffle.gameapi.player.GamePlayer;
import me.waffle.gameapi.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;


public class GameAPI extends JavaPlugin {

    /**
     * List of loaded managers.
     */
    private static HashMap<Class<? extends Manager>, Manager> loadedManagers;

    /**
     * game registry instance.
     */
    private static GameRegistry registry;

    /**
     * Static instance of this class, protected so it cannot be abused.
     */
    @Getter
    private static GameAPI api;


    /**
     * Called on enable
     */
    @Override
    public void onEnable() {
        api = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        loadManagers();

        registry = new GameRegistry();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        loadCommands();
    }

    /**
     * Load the commands
     */
    public void loadCommands() {
        SetupCommand setupCommand = new SetupCommand();
        getCommand("setlobby").setExecutor(setupCommand);
        getCommand("addspawn").setExecutor(setupCommand);
        getCommand("join").setExecutor(new JoinCommand());
        getCommand("create").setExecutor(setupCommand);
    }

    /**
     * Called on disable
     */
    @Override
    public void onDisable() {
        loadedManagers.clear();

        api = null;
    }

    /**
     * Loads the managers
     */
    public final void loadManagers() {
        loadedManagers = new HashMap<>();
        loadedManagers.put(PlayerManager.class, new PlayerManager());
        loadedManagers.put(MapManager.class, new MapManager());
    }

    /**
     *
     * @param manager The manager class you would like to find
     * @param <T> Ignore
     * @return The instance of the class that has been found, null if not registered.
     */

    public <T extends Manager> Manager getManager(Class<?> manager) {
        return (T) loadedManagers.get(manager);
    }

    /**
     * Adds a manager to the list, and starts it's duties.
     * @param manager Which is the manager class you wish to register
     */
    public void addManager(Manager manager) {
        if (loadedManagers.containsKey(manager.getClass())) {
            return;
        }

        if (manager instanceof Listener) {
            Bukkit.getServer().getPluginManager().registerEvents((Listener) manager, GameAPI.api);
        }
        loadedManagers.put(manager.getClass(), manager);
    }

    /**
     * Removes an operating manager
     * @param manager The manager class you wish to remove
     */
    public void removeManager(Class<?> manager) {
        if (!loadedManagers.containsKey(manager)) {
            return;
        }

        loadedManagers.remove(manager);
    }

    /**
     * Method to register a game, will not work if it is registered.
     * @param game The game instance to register
     */

    public void registerGame(Game game) {
        registry.registerGame(game);
    }

    /**
     * Find the game for the specified GamePlayer
     * @param player The GamePlayer you wish to find
     * @return The game that the player is in, null if not in a game.
     */
    public Game getGameForPlayer(GamePlayer player) {
        return registry.getGame(player);
    }

    /**
     * Find a game with a specified name.
     * @param name Name of the game which you wish to find.
     * @return BigGame instance matching the name.
     */
    public Game getGameByName(String name) {
        return registry.getGame(name);
    }

    /**
     * Check to see if player is in game
     * @param player Player you wish to seek
     * @return boolean value if they are in or out of a game.
     */
    public boolean isInGame(GamePlayer player) {
        return registry.isInGame(player);
    }

    public boolean mapIsInUse(String mapName) {
        for (Game game : registry.getGames()) {
            if (game.getMap().getName().equalsIgnoreCase(mapName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all games that are registered
     * @return boolean value if they are in or out of a game.
     */
    public List<Game> getGames(){
        return registry.getGames();
    }

    /**
     * Gets a
     * @return boolean value if they are in or out of a game.
     */
    public Game getGame(String gameName){
        return registry.getGame(gameName);
    }

    public boolean gameExists(String gameName){
        for(Game game : registry.getGames()){
            if(game.getName().equalsIgnoreCase(gameName)){
                return true;
            }
        }

        return false;
    }
}
