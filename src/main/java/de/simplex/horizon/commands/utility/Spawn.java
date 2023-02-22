package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

	Main main = Main.getPlugin();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		MessageEngine ME = new MessageEngine(main);

		if (sender instanceof Player p) {

			if (Bukkit.getServer().getWorld("LOBBY") == null) {
				ME.sendTo(Main.PREFIX + "Die Lobby-Welt konnte nicht gefunden werden. Bitte wende dich an ein Teammitglied.", (Audience) p);
				return false;
			}

			FileConfiguration c = Main.getPlugin().getConfig();
			Location loc;

			if (!c.isSet("Lobby.spawn") || c.getLocation("Lobby.spawn") == null) {
				c.set("Lobby.spawn", new Location(Bukkit.getWorld("LOBBY") != null ?
						Bukkit.getWorld("LOBBY") : Bukkit.getWorld("world"), 780, 4, 105));
				Main.getPlugin().saveConfig();
			}
			loc = c.getLocation("Lobby.spawn");

			p.teleport(loc);
			ME.sendTo(Main.PREFIX + "Du wurdest zur Lobby teleportiert!", (Audience) p);
		} else {
			ME.sendTo(Main.PREFIX + Main.NOT_A_PLAYER, this.main.adventure().console());
			return false;
		}
		return true;
	}
}
