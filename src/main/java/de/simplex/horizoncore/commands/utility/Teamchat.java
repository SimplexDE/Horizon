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

		if (!(sender instanceof Player operator)) {
			sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen verfügbar.");
			return true;
		}

		if (!operator.hasPermission("core.teamchat")) {
			sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
			return true;
		}

		if (args.length <= 0) {
			sender.sendMessage(Main.PREFIX + "§cNutzung: /teamchat <Nachricht>");
			return true;
		}

		String playerRank = "§7Spieler:in",
				playerColor = "§7",
				messageColor = "§7";

        /**
         * Biite die Permission manager API nutzen!!
         */
		if (operator.hasPermission("rank.admin")) {
			playerRank = "§4Administrator";
			playerColor = "§4";
			messageColor = "§c";
		} else if (operator.hasPermission("rank.con")) {
			playerRank = "§bContent";
			playerColor = "§b";
		} else if (operator.hasPermission("rank.dev")) {
			playerRank = "§3Developer";
			playerColor = "§3";
		} else if (operator.hasPermission("rank.mod")) {
			playerRank = "§2Moderator";
			playerColor = "§2";
		} else if (operator.hasPermission("rank.sup")) {
			playerRank = "§aSupporter";
			playerColor = "§a";
		}

		String msg = "";

		for (String s : args) {
			msg += s + " ";
		}

		String chatFormat = ("§b@Teamchat §8┃ " + playerRank + " §8┃ " + playerColor + operator.getName() + " §8» " + messageColor + msg);

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer.hasPermission("core.teamchat")) {
				onlinePlayer.sendMessage(chatFormat);
			}
		}
		return false;
	}
}
