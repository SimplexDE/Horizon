package de.simplex.horizon.listeners;

import de.simplex.horizon.commands.utility.MessageSender;
import de.simplex.horizon.commands.utility.RankAssigning;
import de.simplex.horizon.commands.utility.TabListCompiler;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.methods.PlayerConfig;
import de.simplex.horizon.methods.Scoreboard;
import de.simplex.horizon.methods.ServerConfig;
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

public class Connection implements Listener {

    ServerConfig c = ServerConfig.loadConfig();
    MessageSender ms = new MessageSender();
    TabListCompiler tc = new TabListCompiler();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        PlayerConfig pc = PlayerConfig.loadConfig(e.getPlayer());

        Player p = e.getPlayer();
        e.setJoinMessage("");

        RankAssigning.assignRank(p);
        tc.setNameTag(e.getPlayer(), Horizon.getHorizon().PlayerRanks.get(e.getPlayer().getUniqueId()));

        Scoreboard.defaultSb(p);

        for (Player player : Bukkit.getOnlinePlayers()) {
            tc.setTabListHeaderAndFooter(Horizon.adventure().players());
            Scoreboard.updatePlayers(player);
        }

        Date now = new Date();
        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);

        pc.set("online", true);
        pc.set("last_known_name", e.getPlayer().getName());
        pc.set("last_known_address", e.getPlayer().getAddress().getHostString());
        pc.set("last_seen", (date.format(now)));


        pc.save();

        String rankColor = Horizon.getHorizon().RankColors.get(Horizon.getHorizon().PlayerRanks.get(p.getUniqueId()));

        ms.sendToAll("<green>+ <dark_gray>┃ " + rankColor + p.getName() + " <gray>hat den Server betreten.");

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

        for (Player player : Bukkit.getOnlinePlayers()) {
            tc.setTabListHeaderAndFooter(Horizon.adventure().players());
            Scoreboard.updatePlayers(player);
        }

        Date now = new Date();
        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);

        pc.set("online", false);
        pc.set("last_seen", (date.format(now)));
        pc.save();

        String rankColor = Horizon.getHorizon().RankColors.get(Horizon.getHorizon().PlayerRanks.get(p.getUniqueId()));

        ms.sendToAll("<red>- <dark_gray>┃ " + rankColor + p.getName() + " <gray>hat den Server verlassen.");

        Horizon.getHorizon().PlayerRanks.remove(p.getUniqueId());
    }


}
