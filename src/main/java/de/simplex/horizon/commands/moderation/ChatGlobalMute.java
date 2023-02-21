package de.simplex.horizon.commands.moderation;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Der Globalmute Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class ChatGlobalMute implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        Main pl = Main.getPlugin();
        FileConfiguration con = pl.getConfig();

        if (!(sender.hasPermission("core.globalmute"))) {
            ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) sender);
            return false;
        }

        if (!con.getBoolean("GLOBALMUTE")) {
            con.set("GLOBALMUTE", true);
            ME.broadcast(Main.PREFIX + "Der <red>Global-Mute <grey>wurde aktiviert.");
		} else {
            con.set("GLOBALMUTE", false);
            ME.broadcast(Main.PREFIX + "Der <red>Global-Mute <grey>wurde deaktiviert.");
		}
		pl.saveConfig();

		return false;
	}
}
