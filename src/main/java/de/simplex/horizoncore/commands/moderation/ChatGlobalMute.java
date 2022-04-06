package de.simplex.horizoncore.commands.moderation;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Der Globalmute Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class ChatGlobalMute implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Main pl = JavaPlugin.getPlugin(Main.class);
        FileConfiguration con = pl.getConfig();

        if (!(sender.hasPermission("core.globalmute"))) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        if (!pl.getConfig().getBoolean("GLOBALMUTE")) {
            con.set("GLOBALMUTE", true);
            Bukkit.broadcastMessage(Main.PREFIX + "Der §cGlobal-Mute §7wurde aktiviert.");
        } else {
            con.set("GLOBALMUTE", false);
            Bukkit.broadcastMessage(Main.PREFIX + "Der §cGlobal-Mute §7wurde deaktiviert.");
        }
        pl.saveDefaultConfig();

        return false;
    }
}
