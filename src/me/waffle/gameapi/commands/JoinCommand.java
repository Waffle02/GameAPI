package me.waffle.gameapi.commands;

import me.waffle.gameapi.GameAPI;
import me.waffle.gameapi.game.Game;
import me.waffle.gameapi.player.GamePlayer;
import me.waffle.gameapi.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {

    PlayerManager playerManager = (PlayerManager) GameAPI.getApi().getManager(PlayerManager.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equalsIgnoreCase("join")) {
            return true;
        }

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        GamePlayer player1 = playerManager.getPlayer(player);

        if (args.length != 1) {
            player.sendMessage("&c/join [game]");
            return true;
        }

        String gameName = args[0];

        Game game = GameAPI.getApi().getGameByName(gameName);

        if (game == null) {
            player.sendMessage("Cant find that game!");
            return true;
        }

        game.onJoin(player1);
        return false;
    }
}

