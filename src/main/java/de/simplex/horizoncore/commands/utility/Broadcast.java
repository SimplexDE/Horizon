package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Broadcast implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("core.alert")) {
			sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
			return false;
		}

		if (args.length <= 0) {
			sender.sendMessage(Main.PREFIX + "§cNutzung: /alert <Nachricht>");
			return false;
		}

		String msg = "";

		for (String s : args) {
			msg += s + " ";
		}

		msg = ChatColor.translateAlternateColorCodes('&', msg);
		Bukkit.broadcastMessage("§8» §bRundfunk §8┃ §7" + msg);

		return false;
	}
}
