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
            if (p.getWorld() != null) {
                if (args.length == 1) {
                    World w = p.getLocation().getWorld();
                    Location loc = p.getLocation();
                    int homes = pC.getInt("homes");
                    String name = args[0];

                    pC.set("homes", homes += 1);
                    pC.set("homelist." + homes + ".name", name);
                    pC.set("homelist." + homes + ".X", loc.getX());
                    pC.set("homelist." + homes + ".Y", loc.getY());
                    pC.set("homelist." + homes + ".Z", loc.getZ());

                    p.sendMessage(Main.PREFIX + "Home §e\"%s\" §7wurde gesetzt");
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
