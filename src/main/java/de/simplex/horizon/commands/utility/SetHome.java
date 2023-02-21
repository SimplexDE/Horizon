package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import de.simplex.horizon.systems.PlayerConfig;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHome implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (sender instanceof Player p) {
            PlayerConfig pC = PlayerConfig.loadConfig(p);

            if (!p.hasPermission("core.sethome")) {
                ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) p);
                return false;
            }
            /**
             * Diese Abfrage ist im Prinzip nicht nötig
             */
            p.getWorld();
            if (args.length == 1) {
                Location loc = p.getLocation();
                World w = loc.getWorld();
                int homes = pC.getSafeInt("homelist." + w.getName() + ".homes");
                String name = args[0];
                if (!p.hasPermission("core.sethome.limitbypass"))
                    if (homes == 3) {
                        ME.sendTo(Main.PREFIX + "Du kannst nicht mehr als 3 Homes setzen!", (Audience) p);
                        return false;
                    }
                /**
                 * Die vorherige Abfrage hätte nur teilw. gemacht, was sie soll
                 */
                if (pC.isSet("homelist." + w.getName() + name)) {
                    ME.sendTo(Main.PREFIX + "Dieser Name ist bereits in Benutzung.", (Audience) p);
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
                pC.set("homelist." + w.getName() + "." + name + ".location", loc.clone().add(0, 0.02, 0));

                pC.save();
                ME.sendTo(Main.PREFIX + String.format("Home <yellow>\"%s\" <grey>wurde gesetzt.", name), (Audience) p);

            } else {
                ME.sendTo(Main.PREFIX + "<red>Nutzung: /sethome <Name>", (Audience) p);
                return false;
            }
        } else {
            ME.sendTo(Main.PREFIX + Main.NOT_A_PLAYER, (Audience) sender);
            return false;
        }
        return true;
    }

}
