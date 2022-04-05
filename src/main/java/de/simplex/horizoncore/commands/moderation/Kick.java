package de.simplex.horizoncore.commands.moderation;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.ModerationAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Kick Befehl
 *
 * @author Simplex
 * @see ModerationAPI
 * @since 1.0-Beta
 */
public class Kick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender.hasPermission("core.kick"))) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        if (args.length <= 1) {
            sender.sendMessage(Main.PREFIX + "§cNutzung: /ekick <Spieler> <Grund>");
            return false;
        }

        String msg = "";

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Main.PREFIX + "§e" + args[0] + "§7 ist nicht online.");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);

        for (int i = 1; i < args.length; i++) {
            msg += (args[i] + " ");
        }

        ModerationAPI.KickPlayer(sender, target, msg);
        return false;
    }
}
