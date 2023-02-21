package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Flight Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Flight implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (sender instanceof Player p) {

            if (!sender.hasPermission("core.flight")) {
                ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) p);
                return false;
            }

            if (!sender.hasPermission("core.flight.other")) {
                if (args.length == 1) {
                    ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) p);
                    return false;
                }
            }

            if (args.length == 0) {
                if (!p.getAllowFlight()) {
                    p.setAllowFlight(true);
                    ME.sendTo(Main.PREFIX + "Du kannst nun <green>Fliegen<grey>.", (Audience) p);
                } else {
                    p.setAllowFlight(false);
                    ME.sendTo(Main.PREFIX + "Du kannst nun nicht mehr <green>Fliegen<grey>.", (Audience) p);
                }
            } else if (args.length == 1) {
                Player tar = Bukkit.getPlayer(args[0]);
                if (tar != null) {
                    if (!tar.getAllowFlight()) {
                        tar.setAllowFlight(true);
                        ME.sendTo(Main.PREFIX + "Du kannst nun <green>Fliegen<grey>.", (Audience) tar);
                        ME.sendTo(Main.PREFIX + "<yellow>" + tar.getName() + " <grey>kann nun <green>Fliegen<grey>.", (Audience) p);
                    } else {
                        tar.setAllowFlight(false);
                        ME.sendTo(Main.PREFIX + "Du kannst nun nicht mehr <green>Fliegen<grey>.", (Audience) tar);
                        ME.sendTo(Main.PREFIX + "<yellow>" + tar.getName() + " <grey>kann nun nicht mehr <green>Fliegen<grey>.", (Audience) p);
                    }
                } else {
                    ME.sendTo(Main.PREFIX + "Bitte gebe eine/n Spieler:in an, der/die Online ist.", (Audience) p);
                }
            } else {
                ME.sendTo(Main.PREFIX + "<red>Nutzung: /flight [<Spieler:in>]", (Audience) p);
            }
        } else {
            ME.sendTo(Main.PREFIX + "Dieser Befehl ist nur für Spieler:innen zugänglich.", (Audience) sender);
        }
        return false;
    }
}
