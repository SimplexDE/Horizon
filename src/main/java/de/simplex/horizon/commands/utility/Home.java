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

public class Home implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (sender instanceof Player p) {
            PlayerConfig pC = PlayerConfig.loadConfig(p);

            if (!p.hasPermission("core.home")) {
                ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) p);
                return false;
            }
            p.getWorld();
            if (args.length == 1) {
                Location loc = p.getLocation();
                World w = loc.getWorld();
                String name = args[0];

                if (pC.isSet("homelist." + w.getName() + name + ".used")) {
                    Location locate = pC.getLocation("homelist." + w.getName() + "." + name + ".location");
                    p.teleport(locate);
                    ME.sendTo(Main.PREFIX + "Du wurdest zu <yellow>\"%s\" <gray>teleportiert.", (Audience) p);
                    return true;
                } else {
                    ME.sendTo(Main.PREFIX + "<red>Home wurde nicht gefunden. <gray>" + pC.getLocation("homelist." + w.getName() + "." + name + ".location"), (Audience) p);
                    return false;
                }
            } else {
                ME.sendTo(Main.PREFIX + "<red>Nutzung: /home <Name>", (Audience) p);
                return false;
            }
        } else {
            ME.sendTo(Main.PREFIX + Main.NOT_A_PLAYER, (Audience) sender);
            return false;
        }
    }

}
