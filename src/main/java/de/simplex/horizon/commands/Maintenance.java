package de.simplex.horizon.commands;

import de.simplex.horizon.commands.utility.MessageSender;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.methods.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Maintenance implements CommandExecutor {
    String MAINTENANCE_ANNOUNCE = "<newline>" + Horizon.PREFIXCOLOR + "Der Server hat den <#de4040>Wartungsmodus <grey>%s.<newline>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageSender ms = new MessageSender();

        if (!sender.hasPermission("server.maintenance")) {
            ms.sendToSender(sender, Horizon.PREFIXCOLOR + Horizon.NO_PERMS);
            return false;
        }

        ServerConfig c = ServerConfig.loadConfig();
        boolean maintenance = c.isSet("server.maintenance") && c.getBoolean("server.maintenance");


        if (!maintenance) {
            maintenance = true;

            for (Player ap : Bukkit.getOnlinePlayers()) {
                if (!ap.hasPermission("server.maintenance.bypass")) {
                    ap.kickPlayer(String.format(MAINTENANCE_ANNOUNCE, "betreten") + " \nWir bitten um Geduld." +
                            "\nMehr Informationen: " + Horizon.getHorizon().getDescription().getWebsite());
                }
            }
            ms.sendToConsole(Horizon.PREFIXCOLOR + "Alle Spieler wurden gekickt aufgrund des aktiven Wartungsmodus.");
            Bukkit.setWhitelist(true);
            ms.sendToAll(String.format(MAINTENANCE_ANNOUNCE, "betreten"));
        } else {
            maintenance = false;
            Bukkit.setWhitelist(false);
            ms.sendToAll(String.format(MAINTENANCE_ANNOUNCE, "verlassen"));
        }

        c.set("server.maintenance", maintenance);
        c.save();
        return true;
    }
}
