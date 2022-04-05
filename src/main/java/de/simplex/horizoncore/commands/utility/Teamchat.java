package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Der Teamchat Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Teamchat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler verfügbar.");
            return false;
        }

        Player operator = (Player) sender;

        if (!(operator.hasPermission("core.teamchat"))) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        String playerRank = "§7Spieler";
        String playerColor = "§7";
        String messageColor = "§a";

        /*
        RANK ASSIGNING
         */
        if (operator.hasPermission("rank.admin")) {
            playerRank = "§4Admin";
            playerColor = "§4";
            messageColor = "§c";
        } else if (operator.hasPermission("rank.dev")) {
            playerRank = "§3Dev";
            playerColor = "§3";
            messageColor = "§a";
        } else if (operator.hasPermission("rank.mod")) {
            playerRank = "§cMod";
            playerColor = "§c";
            messageColor = "§a";
        } else if (operator.hasPermission("rank.con")) {
            playerRank = "§bCon";
            playerColor = "§b";
            messageColor = "§a";
        }

        String msg = "";

        if (args.length <= 0) {
            sender.sendMessage(Main.PREFIX + "§cNutzung: /teamchat <Nachricht>");
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            msg = msg + args[i] + " ";
        }

        String chatFormat = ("§b@Teamchat §8┃ " + playerRank + " §8┃ " + playerColor + operator.getName() + " §8» " + messageColor + msg);

        for (org.bukkit.entity.Player onlinePlayer : onlinePlayers) {
            if (onlinePlayer.hasPermission("core.teamchat")) {
                onlinePlayer.sendMessage(chatFormat);
            }
        }


        return false;
    }
}
