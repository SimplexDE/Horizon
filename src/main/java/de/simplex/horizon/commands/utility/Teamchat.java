package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		if (sender.hasPermission("rank.admin")) {
			playerRank = "<#e81a1f>Administrator";
			playerColor = "<#e81a1f>";
		} else if (sender.hasPermission("rank.con")) {
			playerRank = "<#2390da>Content";
			playerColor = "<#2390da>";
		} else if (sender.hasPermission("rank.dev")) {
			playerRank = "<#2ad19d>Developer";
			playerColor = "<#2ad19d>";
		} else if (sender.hasPermission("rank.mod")) {
			playerRank = "<#d3c334>Moderator";
			playerColor = "<#d3c334>";
		} else if (sender.hasPermission("rank.sup")) {
			playerRank = "<#ede54c>Supporter";
			playerColor = "<#ede54c>";
		} else if (sender.hasPermission("rank.friend")) {
			playerRank = "<#af33c6>Freund";
			playerColor = "<#af33c6>";
		}

		String msg = "";

		for (String s : args) {
			msg += s + " ";
		}

		String chatFormat = ("§b@Teamchat §8┃ " + playerRank + " §8┃ " + playerColor + operator.getName() + " §8» " + msg);


		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer.hasPermission("core.teamchat")) {
				onlinePlayer.sendMessage(chatFormat);
			}
		}
		return false;
	}
}
