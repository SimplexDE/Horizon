package de.simplex.horizoncore.commands.moderation;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.ModerationAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Der Unban Befehl
 *
 * @author Simplex
 * @see ModerationAPI
 * @since 1.0-Beta
 */
public class Unban implements CommandExecutor {

    /*
     * Aktuell nicht funktionsfähig. Siehe ModerationAPI.
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("core.ban")) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(Main.PREFIX + "§cNutzung: /eunban <Spieler:in>");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        ModerationAPI.UnbanPlayer(sender, target);
        return true;
    }
}
