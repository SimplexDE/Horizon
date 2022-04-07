package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

/**
 * Der Difficulty Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Difficulty implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player p) {

			if (!(sender.hasPermission("core.difficulty"))) {
				sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
				return false;
			}

			World w = p.getLocation().getWorld();

			if (args.length == 1) {
				org.bukkit.Difficulty d = org.bukkit.Difficulty.valueOf(args[0]);
				if (d == null)
					d = org.bukkit.Difficulty.getByValue(Integer.parseInt(args[0]));
				if (d == null) {
					p.sendMessage(Main.PREFIX + "Dieser Schwierigkeitsgrad existiert nicht!");
					return true;
				}
				if (w == null) {
					p.sendMessage(Main.PREFIX + "Die Welt in der du dich befindest ist ungültig.");
					return true;
				}
				w.setDifficulty(d);
				p.sendMessage(Main.PREFIX + String.format("Der Schwierigkeitsgrad ist nun §a%s§8.", d.toString().toLowerCase()));

			} else {
				p.sendMessage(Main.PREFIX + "Diese Argumentenlänge ist ungültig.");
			}
		} else {
			sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.");
		}
		return false;
	}
}
