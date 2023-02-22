package de.simplex.horizon.commandsOLD.moderation;

import de.simplex.horizon.Main;
import de.simplex.horizon.commandsOLD.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
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
    String MAINTENANCE_ANNOUNCE = Main.PREFIX + "Der Server hat den <#de4040>Wartungsmodus <grey>%s.";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (!sender.hasPermission("core.maintenance")) {
            ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) sender);
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
            ME.broadcast(String.format(MAINTENANCE_ANNOUNCE, "betreten"));
		} else {
            maintenance = false;
            ME.broadcast(String.format(MAINTENANCE_ANNOUNCE, "verlassen"));
		}

		c.set("MAINTENANCE", maintenance);
		main.saveConfig();
		return false;
	}
}
