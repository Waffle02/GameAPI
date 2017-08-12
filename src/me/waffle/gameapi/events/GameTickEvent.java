package me.waffle.gameapi.events;

import lombok.Getter;
import me.waffle.gameapi.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameTickEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    @Getter
    private Game game;

    public GameTickEvent(Game game) {
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
