package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Flight Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Flight implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player operator) {

			if (!sender.hasPermission("core.flight")) {
				sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
				return false;
			}

			if (!sender.hasPermission("core.flight.other")) {
				if (args.length == 1) {
					sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
					return false;
				}
			}

			if (args.length == 0) {
				if (!operator.getAllowFlight()) {
					operator.setAllowFlight(true);
					operator.sendMessage(Main.PREFIX + "Du kannst nun §aFliegen§7.");
				} else {
					operator.setAllowFlight(false);
					operator.sendMessage(Main.PREFIX + "Du kannst nun nicht mehr §aFliegen§7.");
				}
			} else if (args.length == 1) {
				Player tar = Bukkit.getPlayer(args[0]);
				if (tar != null) {
					if (!tar.getAllowFlight()) {
						tar.setAllowFlight(true);
						tar.sendMessage(Main.PREFIX + "Du kannst nun §aFliegen§7.");
						operator.sendMessage(Main.PREFIX + "§e" + tar.getName() + " §7kann nun §aFliegen§7.");
					} else {
						tar.setAllowFlight(false);
						tar.sendMessage(Main.PREFIX + "Du kannst nun nicht mehr §aFliegen§7.");
						operator.sendMessage(Main.PREFIX + "§e" + tar.getName() + " §7kann nun nicht mehr §aFliegen§7.");
					}
				} else {
					operator.sendMessage(Main.PREFIX + "Bitte gebe eine/n Spieler:in an, der/die Online ist.");
				}
			} else {
				operator.sendMessage(Main.PREFIX + "§cNutzung: /flight [<Spieler:in>]");
			}
		} else {
			sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.");
		}
		return false;
	}
}
