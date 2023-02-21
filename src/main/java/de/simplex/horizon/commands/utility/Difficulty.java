package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Difficulty Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Difficulty implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (sender instanceof Player p) {

            if (!(sender.hasPermission("core.difficulty"))) {
                ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) sender);
                return false;
            }

            World w = p.getLocation().getWorld();

			if (args.length == 0) {
                ME.sendTo(Main.PREFIX + String.format("Derzeitiger Schwierigkeitsgrad: %s", w.getDifficulty().toString().toLowerCase()), (Audience) p);
			} else if (args.length == 1) {
				org.bukkit.Difficulty d = org.bukkit.Difficulty.valueOf(args[0].toUpperCase());
				if (d == null)
					d = org.bukkit.Difficulty.getByValue(Integer.parseInt(args[0].toUpperCase()));
				if (d == null) {
                    ME.sendTo(Main.PREFIX + "Dieser Schwierigkeitsgrad existiert nicht!", (Audience) p);
					return true;
				}
				if (w == null) {
                    ME.sendTo(Main.PREFIX + "Die Welt in der du dich befindest ist ungültig.", (Audience) p);
					return true;
				}
                w.setDifficulty(d);
                ME.sendTo(Main.PREFIX + String.format("Der Schwierigkeitsgrad ist nun <green>%s<dark_grey>.", d.toString().toLowerCase()), (Audience) p);

			} else {
                ME.sendTo(Main.PREFIX + Main.ARGS_TOO_LONG, (Audience) p);
			}
		} else {
            ME.sendTo(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.", (Audience) sender);
		}
		return false;
	}
}
