package de.simplex.horizon.listener;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.NotificationPrefixes;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.method.PlayerConfig;
import de.simplex.horizon.method.ServerConfig;
import de.simplex.horizon.method.Sidebar;
import de.simplex.horizon.method.Tablist;
import de.simplex.horizon.util.MessageSender;
import de.simplex.horizon.util.RankManager;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class ConnectionListener implements Listener {

    ServerConfig c = ServerConfig.loadConfig();
    MessageSender ms = new MessageSender();
    Tablist tc = new Tablist();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        PlayerConfig pc = PlayerConfig.loadConfig(e.getPlayer());

        Player p = e.getPlayer();
        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());
        e.setJoinMessage("");

        Sidebar.defaultSb(p);

        RankManager.assignRank(p);

        for (Player player : Bukkit.getOnlinePlayers()) {
            tc.setTabListHeaderAndFooter(Horizon.adventure().players());
            Sidebar.updatePlayers(player);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConfig apc = PlayerConfig.loadConfig(player.getPlayer());
            if (apc.isSet("staff.vanish") && apc.getBoolean("staff.vanish")) {
                if (p.hasPermission("staff.vanish.see")) {
                    break;
                }
                p.hidePlayer(Horizon.getHorizon(), player);
            }
        }

        boolean vanished = pc.isSet("staff.vanish") && pc.getBoolean("staff.vanish");

        if (vanished) {
            if (!p.hasPermission("server.vanish")) {
                pc.set("staff.vanish", false);
            } else {
                for (Player ap : Bukkit.getOnlinePlayers()) {
                    if (ap.canSee(p)) {
                        if (ap.hasPermission("server.vanish.see")) {
                            break;
                        }
                        ap.hidePlayer(Horizon.getHorizon(), p);
                    }
                }
                Objects.requireNonNull(lpapi.getUserManager().getUser(p.getUniqueId())).data().add(Node.builder("suffix.100." + Color.LIGHT_PURPLE.getColorMiniMessage() + "[V]").build());
                p.setSilent(true);
                pc.set("staff.vanish", true);
                ms.sendToPlayer(p, NotificationPrefixes.WARN.getNotification() + "Du bist " + Color.AQUA.getColorMiniMessage() + "versteckt" + Color.LIGHT_GRAY.getColorMiniMessage() + " beigetreten.");
            }
        }

        Date now = new Date();
        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);

        pc.set("online", true);
        pc.set("last_known_name", e.getPlayer().getName());
        pc.set("last_known_address", e.getPlayer().getAddress().getHostString());
        pc.set("last_seen", (date.format(now)));

        pc.save();

        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 0, 9);
        if (color == null) {
            color = "<#949494>";
        }

        if (!vanished) {
            ms.sendToAll(Color.GREEN.getColorMiniMessage() + "+" + Color.BLACK.getColorMiniMessage() + " ┃ " + color
                    + p.getName() + Color.LIGHT_GRAY.getColorMiniMessage() + " hat den Server " + Color.LIGHT_GREEN.getColorMiniMessage() + "betreten" + Color.LIGHT_GRAY.getColorMiniMessage() + ".");
        }

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

        PlayerConfig pc = PlayerConfig.loadConfig(e.getPlayer());

        Player p = e.getPlayer();
        e.setQuitMessage("");

        Bukkit.getScheduler().scheduleSyncDelayedTask(Horizon.getHorizon(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                tc.setTabListHeaderAndFooter(Horizon.adventure().players());
                Sidebar.updatePlayers(player);
            }
        }, 10L);

        Date now = new Date();
        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);

        pc.set("online", false);
        pc.set("last_seen", (date.format(now)));
        pc.save();

        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 0, 9);
        if (color == null) {
            color = "<#949494>";
        }

        boolean vanished = pc.isSet("staff.vanish") && pc.getBoolean("staff.vanish");

        if (!vanished) {
            ms.sendToAll(Color.RED.getColorMiniMessage() + "-" + Color.DARK_GRAY.getColorMiniMessage() + " ┃ " + color
                    + p.getName() + Color.LIGHT_GRAY.getColorMiniMessage() + " hat den Server " + Color.LIGHT_RED.getColorMiniMessage() + "verlassen" + Color.LIGHT_GRAY.getColorMiniMessage() + ".");
        }
    }


}
