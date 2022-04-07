package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Gamemode Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Gamemode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player p) {

			if (!sender.hasPermission("core.gamemode")) {
				sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
				return false;
			}

			org.bukkit.GameMode gm;
			if (args.length == 1 || args.length == 2) {
				String gms;
				switch (args[0]) {
					case "1":
						gms = "CREATIVE";
						break;
					case "2":
						gms = "ADVENTURE";
						break;
					case "3":
						gms = "SPECTATOR";
						break;
					case "0":
						gms = "SURVIVAL";
						break;

				} // Geht bestimmt kürzer, aber ich löse das erstmal so. ~Simplex
				gm = org.bukkit.GameMode.valueOf(args[0]);
				if (gm == null)
					gm = org.bukkit.GameMode.getByValue(Integer.parseInt(args[0]));
				if (gm == null) {
					p.sendMessage(Main.PREFIX + "Dieser Spielmodus existiert nicht!");
					return true;
				}
			} else {
				p.sendMessage(Main.PREFIX + "Diese Argumentenlänge ist ungültig.");
				return true;
			}

			String s = Main.PREFIX + String.format("Dein Spielmodus ist nun §a%s§8.", gm.toString().toLowerCase());
			if (args.length == 1) {
				p.sendMessage(s);
				p.setGameMode(gm);

			} else {
				if (!p.hasPermission("core.gamemode.others")) {
					sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
					return false;
				}

				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					p.sendMessage(Main.PREFIX + "Diese/r Spieler:in ist nicht online!");
					return true;
				}

				target.setGameMode(gm);
				target.sendMessage(s);
				p.sendMessage(Main.PREFIX + String.format("Der Spielmodus von §e%s§7 ist nun §a%s§8.", target.getName(), gm.toString().toLowerCase()));
			}
		} else {
			sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.");
		}
		return false;
	}

}