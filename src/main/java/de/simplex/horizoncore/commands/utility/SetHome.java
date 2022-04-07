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
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PConfig pC = PConfig.loadConfig(p);

            if (!(p.hasPermission("core.sethome"))) {
                p.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
                return false;
            }

            if (p.getWorld() != null) {
                if (args.length == 1) {
                    World w = p.getLocation().getWorld();
                    Location loc = p.getLocation();
                    int homes = pC.getInt("homelist." + w.getName() + ".homes");
                    String name = args[0];
                    if (!(p.hasPermission("core.sethome.limitbypass")))
                        if (homes == 3) {
                            p.sendMessage(Main.PREFIX + "Du kannst nicht über mehr als 3 Homes setzen!");
                            return false;
                        }
                    if (name.equals(pC.getString("homelist." + w.getName() + name + ".name"))) {
                        p.sendMessage(Main.PREFIX + "Dieser Name ist bereits benutzt.");
                        return false;
                    }
                    pC.set("homelist." + w.getName() + ".homes", homes += 1);
                    pC.set("homelist." + w.getName() + name + ".name", name);
                    pC.set("homelist." + w.getName() + name + ".X", loc.getX());
                    pC.set("homelist." + w.getName() + name + ".Y", loc.getY());
                    pC.set("homelist." + w.getName() + name + ".Z", loc.getZ());
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
