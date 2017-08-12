package me.waffle.gameapi.game;

import lombok.Data;
import me.waffle.gameapi.GameAPI;
import me.waffle.gameapi.events.GameTickEvent;
import me.waffle.gameapi.player.GamePlayer;
import me.waffle.gameapi.player.PlayerStatus;
import me.waffle.gameapi.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public abstract class Game implements Runnable {

    private String name;
    private GameState state;

    private Map map;

    private List<Team> teams;

    private GameManager gameManager;

    private int id;

    private String rawName;

    private Map<GamePlayer, PlayerStatus> players;

    private BukkitTask timer;

    private int ticks = 0;

    public BigGame(String name) {
        this.name = name;
        this.rawName = name;
        this.state = GameState.LOBBY;
        this.teams = new ArrayList<>();
        this.players = new HashMap<>();
        //TODO setup
        this.timer = Bukkit.getServer().getScheduler().runTaskTimer(GameAPI.getApi(), this, 0L, 20L);
    }

    public Team createTeam(String name, int maxPlayers) {
        Team team = new Team(name, maxPlayers);
        this.teams.add(team);
        return team;
    }

    @Override
    public void run() {
        if (ticks > 0) {
            setTicks(getTicks() - 1);
        }
        GameTickEvent event = new GameTickEvent(this);
        GameAPI.getApi().getServer().getPluginManager().callEvent(event);
    }

    public void broadcast(String msg) {
        for (GamePlayer player : players.keySet()) {
            player.sendMessage(msg);
        }
    }

    public final boolean isJoinable() {
        return state == GameState.LOBBY;
    }

    public final Team getPlayerTeam(GamePlayer player) {
        for (Team team : teams) {
            if (team.getMembers().contains(player.getUUID())) {
                return team;
            }
        }
        return null;
    }



    public abstract void onJoin(GamePlayer player);
    public abstract void onLeave(GamePlayer player);
    public abstract void start();
    public abstract void finish(Winnable winnable);

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
        GameAPI.getApi().addManager(gameManager);
    }

    public boolean hasPlayer(Player player){
        for(Map.Entry<GamePlayer, PlayerStatus> entry : players.entrySet()){
            if(entry.getKey().getBukkitPlayer() == player){
                return true;
            }
        }
        return false;
    }
}