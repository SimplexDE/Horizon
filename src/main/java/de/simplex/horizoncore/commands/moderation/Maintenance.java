package de.simplex.horizoncore.commands.moderation;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Der Maintenance Befehl,
 * Um den Server zu schließen/öffnen
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Maintenance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Main pl = JavaPlugin.getPlugin(Main.class);

        if (!(sender.hasPermission("core.maintenance") || sender.isOp())) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        boolean maintenance = pl.getConfig().get("MAINTENANCE") != null && pl.getConfig().getBoolean("MAINTENANCE");

        if (!maintenance) {
            maintenance = true;
            Bukkit.broadcastMessage(Main.PREFIX + "Der Server hat den §cWartungsmodus §7betreten.");
        } else {
            maintenance = false;
            Bukkit.broadcastMessage(Main.PREFIX + "Der Server hat den §cWartungsmodus §7verlassen.");
        }

        pl.getConfig().set("MAINTENANCE", maintenance);
        pl.saveConfig();
        return false;
    }
}
