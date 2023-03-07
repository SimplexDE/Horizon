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

    final String MAINTENANCE_ANNOUNCE =
          "<newline>" + ResponseMessage.SYSTEM.getNotification() + Color.RED.getColorMiniMessage() + "Maintenance" + Color.AQUA.getColorMiniMessage() + " was %s" + "<newline>";
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
                    ap.kickPlayer(String.format(MAINTENANCE_ANNOUNCE, "activated") +
                          "\nMore Information: " + Horizon.getHorizon().getDescription().getWebsite());
                }
            }
            ms.sendToConsole(ResponseMessage.HORIZON.getNotification()
                  + "All players were kicked due to the activation of maintenance.");
            Bukkit.setWhitelist(true);
            ms.sendToAll(String.format(MAINTENANCE_ANNOUNCE, "activated"));
        } else {
            newMaintenanceState = false;
            Bukkit.setWhitelist(false);
            ms.sendToAll(String.format(MAINTENANCE_ANNOUNCE, "deactivated"));
        }

        c.set("server.maintenance", newMaintenanceState);
        c.save();
        return true;
    }
}
