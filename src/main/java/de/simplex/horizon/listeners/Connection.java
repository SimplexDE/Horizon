package de.simplex.horizon.listeners;

import de.simplex.horizon.commands.utility.MessageSender;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.methods.ServerConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Connection implements Listener {

    ServerConfig c = ServerConfig.loadConfig();
    MessageSender ms = new MessageSender();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage("");
        ms.sendToAll("<green>+ <dark_gray>┃ <yellow>" + p.getName() + " <gray>hat den Server betreten.");

    }

    @EventHandler
    public void onPlayerConnect(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (c.isSet("server.maintenance") && c.getBoolean("server.maintenance")) {
            if (!(p.hasPermission("server.maintenance.bypass"))) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§cWartungsarbeiten" + " \n§7Wir bitten um Geduld." +
                        "\nMehr Informationen: §9" + Horizon.getHorizon().getDescription().getWebsite());
                p.setWhitelisted(false);
            } else {
                p.setWhitelisted(true);
                e.allow();
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("");
        ms.sendToAll("<red>- <dark_gray>┃ <yellow>" + p.getName() + " <gray>hat den Server verlassen.");
    }


}
