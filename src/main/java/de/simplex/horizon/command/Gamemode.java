package de.simplex.horizon.commands;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.GameModeName;
import de.simplex.horizon.enums.Messages;
import de.simplex.horizon.enums.Notification;
import de.simplex.horizon.util.MessageSender;
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player p) {
                GameModeName modeName = GameModeName.fromString(args[0]);

                if (modeName != null) {
                    GameMode gameMode = modeName.getGameMode();
                    p.setGameMode(gameMode);
                    ms.sendToPlayer(p, Notification.SYSTEM.getNotification() + "Gamemode set to "
                            + Color.GREEN.getColorMiniMessage() + modeName.toString().toUpperCase());
                } else {
                    ms.sendToSender(sender, Messages.INVALID_ARGUMENT.getMsg());
                    return false;
                }
                return true;
            } else {
                ms.sendToSender(sender, Messages.ONLY_PLAYER.getMsg());
                return false;
            }
        } else if (args.length == 2) {
            if (Bukkit.getPlayer(args[1]) != null) {
                Player target = Bukkit.getPlayer(args[1]);
                GameModeName modeName = GameModeName.fromString(args[0]);

                if (modeName != null) {
                    GameMode gameMode = modeName.getGameMode();
                    target.setGameMode(gameMode);
                    ms.sendToPlayer(target, Notification.SYSTEM.getNotification() + "Gamemode set to "
                            + Color.GREEN.getColorMiniMessage() + modeName.toString().toUpperCase());
                    ms.sendToSender(sender, Notification.SYSTEM.getNotification() + "Gamemode of " + target.getName() + " was set to "
                            + Color.GREEN.getColorMiniMessage() + modeName.toString().toUpperCase());
                } else {
                    ms.sendToSender(sender, Messages.INVALID_ARGUMENT.getMsg());
                    return false;
                }
                return true;
            } else {
                ms.sendToSender(sender, Messages.PLAYER_NOT_ONLINE.getMsg());
                return true;
            }
        } else {
            ms.sendToSender(sender, Messages.INVALID_ARGUMENT_LENGTH.getMsg());
            return false;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
        }
        if (args.length == 1) {
            List<String> gameModes = new ArrayList<>();
            for (GameMode gamemode : GameMode.values()) {
                gameModes.add(gamemode.toString());
            }
        }
        return null;
    }
}
