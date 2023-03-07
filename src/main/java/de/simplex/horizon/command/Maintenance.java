package de.simplex.horizon.command;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.ResponseMessage;
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

    String MAINTENANCE_ANNOUNCE =
          "<newline>" + ResponseMessage.SYSTEM.getNotification() + "Der Server hat den " + Color.RED.getColorMiniMessage() + "Wartungsmodus " + Color.LIGHT_GRAY.getColorMiniMessage() + "%s.<newline>";
    private MessageSender ms = new MessageSender();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             String[] args) {
        ServerConfig c = ServerConfig.loadConfig();
        boolean currentMaintenanceState = c.isSet("server.maintenance") && c.getBoolean("server.maintenance");
        boolean newMaintenanceState;


        if (!currentMaintenanceState) {
            newMaintenanceState = true;

            for (Player ap : Bukkit.getOnlinePlayers()) {
                if (!ap.hasPermission("server.maintenance.bypass")) {
                    ap.kickPlayer(String.format(MAINTENANCE_ANNOUNCE, "betreten") + " \nWir bitten um Geduld." +
                          "\nMehr Informationen: " + Horizon.getHorizon().getDescription().getWebsite());
                }
            }
            ms.sendToConsole(ResponseMessage.HORIZON.getNotification() + "Alle Spieler wurden gekickt aufgrund des " +
                  "aktiven Wartungsmodus.");
            Bukkit.setWhitelist(true);
            ms.sendToAll(String.format(MAINTENANCE_ANNOUNCE, "betreten"));
        } else {
            newMaintenanceState = false;
            Bukkit.setWhitelist(false);
            ms.sendToAll(String.format(MAINTENANCE_ANNOUNCE, "verlassen"));
        }

        c.set("server.maintenance", newMaintenanceState);
        c.save();
        return true;
    }
}
