package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (args.length == 0) {
			if (!sen.hasPermission("core.ignoreWlClearChat")) {
				sen.sendMessage(Main.NO_PERMISSION);
				return true;
			}

			for (int i = 0; i <= 400; i++) {
				Bukkit.broadcastMessage(" §0» \n §0» ");
			}
			Bukkit.broadcastMessage(Main.PREFIX + "Der Chat wurde von " + sen.getName() + " geleert.");

			/**
			 * Alternativ, sollte die Console von dem Clear ausgeschlossen sein (vmtl. weniger performant):
			 *
			 * for(Player ap : Bukkit.getOnlinePlayers()){
			 *      for(int i =0; i <= 400; i++) {
			 * 			ap.sendMessage(" §0» \n §0» ");
			 *      }
			 * 		ap.sendMessage(Main.PREFIX + "Der Chat wurde von " + sen.getName() + " geleert.");
			 *  }
			 *  Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Der Chat wurde von " + sen.getName() + " geleert.");
			 */

		} else if (args.length == 1) {
			boolean b = false;
			int i = 100;
			try {
				b = Boolean.parseBoolean(args[0]);
			} catch (Exception e) {
				try {
					i = Integer.parseInt(args[0]);
				} catch (NumberFormatException e1) {
					sen.sendMessage(Main.PREFIX + "Die angegebene Variable ist kein erwarteter Boolean/Integer-Wert.");
					return true;
				}
			}

			if (b) {
				for (Player ap : Bukkit.getOnlinePlayers()) {
					if (!ap.hasPermission("core.ignoreWlClearChat"))
						for (int c = 0; c <= 400; c++) {
							ap.sendMessage(" §0» \n §0» ");
						}
					// Clear msg trotzdem ausgeben, damit auch alle Spieler:innen / Teammitglieder bescheid wissen
					ap.sendMessage(Main.PREFIX + "Der Chat wurde von " + sen.getName() + " geleert.");
				}
			} else {
				for (int c1 = 0; c1 <= i; c1++) {
					Bukkit.broadcastMessage(" §0» \n §0» ");
				}
				Bukkit.broadcastMessage(Main.PREFIX + "Der Chat wurde von " + sen.getName() + " geleert.");
			}
		} else {
			sen.sendMessage("Diese Argumenten Länge ist ungültig.");
		}
		return false;
	}
}
