package de.simplex.horizon.commands;

import de.simplex.horizon.enums.NotificationPrefixes;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.method.ServerConfig;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class Maintenance implements CommandExecutor {
    String MAINTENANCE_ANNOUNCE = "<newline>" + NotificationPrefixes.SYSTEM.getNotification() + "Der Server hat den <#de4040>Wartungsmodus <grey>%s.<newline>";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        MessageSender ms = new MessageSender();

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
            ms.sendToConsole(NotificationPrefixes.HORIZON.getNotification() + "Alle Spieler wurden gekickt aufgrund des aktiven Wartungsmodus.");
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
