package de.simplex.horizoncore.commands.moderation;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Der Maintenance Befehl,
 * Um den Server zu schließen/öffnen
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Maintenance implements CommandExecutor {

	Main main = Main.getPlugin();
	String MAINTENANCE_ANNOUNCE = "Main.PREFIX + \"Der Server hat den §cWartungsmodus §7%s.";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("core.maintenance")) {
			sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
			return false;
		}

		FileConfiguration c = main.getConfig();
		boolean maintenance = c.get("MAINTENANCE") != null && c.getBoolean("MAINTENANCE");

		if (!maintenance) {
			maintenance = true;

			/**
			 * ap = allPlayers ;)
			 */
			for (Player ap : Bukkit.getOnlinePlayers()) {
				if (!ap.hasPermission("core.ignoreMaintenanceKick")) {
					ap.kickPlayer(String.format(MAINTENANCE_ANNOUNCE, "betreten") + " \nWir bitten um Geduld." +
							"\nMehr Informationen: https://discord.gg/gw8nKNxCxE");
				}
			}
			System.out.println(Main.PREFIX + "Alle Spieler:innen wurden auf Grund des Wartungsmoduses gekickt.");
			Bukkit.broadcastMessage(String.format(MAINTENANCE_ANNOUNCE, "betreten"));
		} else {
			maintenance = false;
			Bukkit.broadcastMessage(String.format(MAINTENANCE_ANNOUNCE, "verlassen"));
		}

		c.set("MAINTENANCE", maintenance);
		main.saveConfig();
		return false;
	}
}
