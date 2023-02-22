package de.simplex.horizon.commandsOLD.moderation;

import de.simplex.horizon.Main;
import de.simplex.horizon.commandsOLD.api.ModerationAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Der Ban Befehl
 *
 * @author Simplex
 * @see ModerationAPI
 * @since 1.0-Beta
 */
public class Ban implements CommandExecutor {

    /*
     * Aktuell nicht funktionsfähig. Siehe ModerationAPI.
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender.hasPermission("core.ban"))) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        if (args.length <= 1) {
            sender.sendMessage(Main.PREFIX + "§cNutzung: /eban <Spieler> <Grund>");
            return false;
        }

        String msg = "";
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        for (int i = 1; i < args.length; i++) {
            msg += (args[i] + " ");
        }

        ModerationAPI.BanPlayer(sender, target, msg);
        return true;
    }
}
