package de.simplex.horizon.commands;

import de.simplex.horizon.enums.NotificationPrefixes;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Kick implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        MessageSender ms = new MessageSender();

        if (Bukkit.getPlayer(args[0]) == null) {
            ms.sendToSender(sender, NotificationPrefixes.INFO.getNotification() + "Dieser Spieler ist nicht online.");
            return false;
        }

        String msg = "";

        for (int i = 1; i < args.length; i++) {
            msg += (args[i] + " ");
        }

        Player target = Bukkit.getPlayer(args[0]);

        assert target != null;
        target.kickPlayer("§c" + msg);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
        }
        return null;
    }
}
