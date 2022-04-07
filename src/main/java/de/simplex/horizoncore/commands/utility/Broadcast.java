package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Der Broadcast Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Broadcast implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		/**
		 * Hat man op, so hat man automatisch jede Permission.
		 */
		if (!sender.hasPermission("core.alert") || !sender.isOp()) {
			sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
			return false;
		}

		if (args.length <= 0) {
			sender.sendMessage(Main.PREFIX + "§cNutzung: /alert <Nachricht>");
			return false;
		}

		String msg = null;

		for (String s : args) {
			msg += s + " ";
		}

		msg = ChatColor.translateAlternateColorCodes('&', msg);
		Bukkit.broadcastMessage("§8» §bRundfunk §8┃ §7" + msg);

		return false;
	}
}
