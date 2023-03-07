package de.simplex.horizon.command;

import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.GameModeName;
import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.util.MessageSender;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Gamemode implements TabExecutor {

    MessageSender ms = new MessageSender();

    private void setGameMode(Player player, GameModeName gameMode) {
        player.setGameMode(gameMode.getGameMode());
        ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Gamemode changed to "
              + Color.LIGHT_GREEN.getColorMiniMessage() + StringUtils.capitalize(gameMode.getGameModeName()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final String commandPermission = command.getPermission();

        Player targetPlayer;

        GameModeName gameMode;

        if (args.length > 2) {
            ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT_LENGTH.getMessage());
            return true;
        }

        if (GameModeName.fromString(args[0]) == null) {
            ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT.getMessage());
            return true;
        }
        gameMode = GameModeName.fromString(args[0]);

        if (args.length == 2 && Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]) != sender) {
            if (!sender.hasPermission(commandPermission + ".others")) {
                ms.sendToSender(sender, AlertMessage.NO_PERMISSION.getMessage());
                return true;
            }
            targetPlayer = Bukkit.getPlayer(args[1]);

            setGameMode(targetPlayer, gameMode);
            ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "Gamemode of "
                  + targetPlayer.getName() + " changed to " + Color.LIGHT_GREEN.getColorMiniMessage()
                  + StringUtils.capitalize(StringUtils.capitalize(gameMode.getGameModeName())));
            return true;
        }

        if (!(sender instanceof Player player)) {
            ms.sendToSender(sender, AlertMessage.ONLY_PLAYER.getMessage());
            return true;
        }

        setGameMode(player, gameMode);
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                                      @NotNull String[] args) {
        if (args.length == 1) {
            List<String> gameModes = new ArrayList<>();
            for (GameMode gamemode : GameMode.values()) {
                gameModes.add(gamemode.toString().toLowerCase());
            }
            return gameModes;
        } else if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
        } else {
            return null;
        }
    }
}
