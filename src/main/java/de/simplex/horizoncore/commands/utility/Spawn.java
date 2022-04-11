package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player p) {

			if (Bukkit.getServer().getWorld("LOBBY") == null) {
				sender.sendMessage(Main.PREFIX + "Die Lobby-Welt konnte nicht gefunden werden. Bitte wende dich an ein Teammitglied.");
				return false;
			}

			FileConfiguration c = Main.getPlugin().getConfig();

			Location loc;
			if (!c.isSet("Lobby.spawn") || c.getLocation("Lobby.spawn") == null) {
				c.set("Lobby.spawn", new Location(Bukkit.getWorld("LOBBY"), 780, 4, 105));
				Main.getPlugin().saveConfig();
				loc = c.getLocation("Lobby.spawn");
			} else {
				loc = c.getLocation("Lobby.spawn");
			}

			p.teleport(loc);
			p.sendMessage(Main.PREFIX + "Du wurdest zur Lobby teleportiert!");
		} else {
			sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.");
		}
		return true;
	}
}
