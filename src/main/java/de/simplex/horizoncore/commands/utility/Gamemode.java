package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Gamemode Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Gamemode implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {

            if (!(sender.hasPermission("core.gamemode"))) {
                sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
                return false;
            }

            if (args.length == 1) {
                org.bukkit.GameMode gm = org.bukkit.GameMode.valueOf(args[0]);
                if (gm == null)
                    gm = org.bukkit.GameMode.getByValue(Integer.parseInt(args[0]));
                if (gm == null) {
                    p.sendMessage(Main.PREFIX + "Dieser Spielmodus existiert nicht!");
                    return true;
                }
                p.sendMessage(Main.PREFIX + String.format("Dein Spielmodus ist nun §a%s§8.", gm.toString().toLowerCase()));

            } else if (args.length == 2) {

                if (!p.hasPermission("core.gamemode.other")) {
                    sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
                    return false;
                }

                org.bukkit.GameMode gm = org.bukkit.GameMode.valueOf(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                if (gm == null)
                    gm = org.bukkit.GameMode.getByValue(Integer.parseInt(args[0]));
                if (gm == null) {
                    p.sendMessage(Main.PREFIX + "Dieser Spielmodus existiert nicht!");
                    return true;
                }

                if (target == null)
                    target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(Main.PREFIX + "Dieser Spieler ist nicht online!");
                    return true;
                }
                target.sendMessage(Main.PREFIX + String.format("Dein Spielmodus ist nun §a%s§8.", gm.toString().toLowerCase()));
                p.sendMessage(Main.PREFIX + String.format("Der Spielmodus von §e%s§7 ist nun §a%s§8.", target.getName(), gm.toString().toLowerCase()));
            } else {
                p.sendMessage(Main.PREFIX + "Diese Argumentenlänge ist ungültig.");
            }
        } else {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.");
        }
        return false;
    }

}