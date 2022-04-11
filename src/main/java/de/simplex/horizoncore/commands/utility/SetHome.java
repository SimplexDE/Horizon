package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.systems.PConfig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHome implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player p) {
			PConfig pC = PConfig.loadConfig(p);

			if (!p.hasPermission("core.sethome")) {
				p.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
				return false;
			}
            /**
             * Diese Abfrage ist im Prinzip nicht nötig
             */
			if (p.getWorld() != null) {
				if (args.length == 1) {
                    Location loc = p.getLocation();
                    World w = loc.getWorld();
					int homes = pC.getSafeInt("homelist." + w.getName() + ".homes");
					String name = args[0];
					if (!p.hasPermission("core.sethome.limitbypass"))
						if (homes == 3) {
							p.sendMessage(Main.PREFIX + "Du kannst nicht mehr als 3 Homes setzen!");
							return false;
						}
					/**
					 * Die vorherige Abfrage hätte nur teilw. gemacht, was sie soll
					 */
					if (pC.isSet("homelist." + w.getName() + name)) {
						p.sendMessage(Main.PREFIX + "Dieser Name ist bereits in Benutzung.");
						return false;
					}
					/**
					 * Die hier vorherige Art des Speicherns wäre leicht falsch zu nutzen
					 * Anpassung durch TearsDontFall
					 */

					pC.set("homelist." + w.getName() + ".homes", homes += 1);
                    /**
                     * Hier hat ein Punkt gefehlt:
                     * Vorher wäre der String: "homelist:  <weltName><homeName>:  name: <homeName>"
                     * jetzt: "homelist:  <weltName>:  <homeName>:  name: <homeName>"
                     * Übrigens durch einen String ein home setzen, der sich selbst benötigt macht keinen Sinn...
                     */
                    pC.set("homelist." + w.getName() + "." + name + ".name", name);
                    /**
                     * Hab bei Y mal 0.02 hinzugefügt, damit man beim location laden nicht in dei Welt gebuggt wird.
                     */
                    pC.set("homelist." + w.getName() + "." + name + ".location", loc.clone().add(0,0.02,0));

					pC.save();
					p.sendMessage(Main.PREFIX + String.format("Home §e\"%s\" §7wurde gesetzt.", name));

				} else {
					p.sendMessage(Main.PREFIX + "&cNutzung: /sethome <Name>");
					return false;
				}
			} else {
				p.sendMessage(Main.PREFIX + "Die Welt in der du dich befindest ist ungültig.");
				return false;
			}
		} else {
			sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.");
			return false;
		}
		return true;
	}

}
