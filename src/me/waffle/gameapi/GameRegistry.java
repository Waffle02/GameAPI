package me.waffle.gameapi;

import lombok.Getter;
import me.waffle.gameapi.game.Game;
import me.waffle.gameapi.player.GamePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameRegistry {

    @Getter
    private List<Game> games = new ArrayList<>();

    public void registerGame(Game game) {
        List<Game> games = getAllOfType(game.getRawName());
        int id = games == null ? 1 : games.size() + 1;
        game.setId(id);
        game.setName(game.getName() + "" + id);
        this.games.add(game);

        System.out.println(this.games.toString());
    }

    public void unregisterGame(Game game) {
        for (GamePlayer player : game.getPlayers().keySet()) {
            game.onLeave(player);
        }

        this.games.remove(game);
    }

    public Game getGame(String name) {
        for (Game game : games) {

            if (game.getName().equalsIgnoreCase(name)) {
                return game;
            }
        }
        return null;
    }

    public boolean isInGame(GamePlayer player) {
        for (Game game : games) {
            if (game.getPlayers().containsKey(player)) {
                return true;
            }
        }
        return false;
    }

    public Game getGame(GamePlayer player) {
        for (Game game : games) {
            if (game.getPlayers().containsKey(player)) {
                return game;
            }
        }
        return null;
    }

    public List<Game> getAllOfType(String name) {
        return this.games.stream().filter(game -> game.getRawName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }
}

