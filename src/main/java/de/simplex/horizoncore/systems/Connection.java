package de.simplex.horizoncore.systems;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Das Connection-System
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Connection implements Listener {

    private static final File file = new File("plugins/Horizon/playerData.yml");
    private static final YamlConfiguration pD = YamlConfiguration.loadConfiguration(file);
    private static final File file2 = new File("plugins/Horizon/bannedData.yml");
    private static final YamlConfiguration bD = YamlConfiguration.loadConfiguration(file2);

    private void saveConfig() {
        try {
            pD.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean getBoolean(String path) {
        boolean out = false;
        try {
            pD.load(file);
            out = pD.getBoolean(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    private String getString(String path) {
        String out = "";
        try {
            pD.load(file);
            out = pD.getString(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    private boolean getBooleanbD(String path) {
        boolean out = false;
        try {
            pD.load(file);
            out = pD.getBoolean(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    private String getStringbD(String path) {
        String out = "";
        try {
            pD.load(file);
            out = pD.getString(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    private int getInt(String path) {
        int out = 0;
        try {
            pD.load(file);
            out = pD.getInt(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Tablist.setSb();
        event.setJoinMessage("§8» §a+ §8┃ §e" + event.getPlayer().getName() + "§7 ist Beigetreten.");

        Player player = event.getPlayer();

        pD.set(player.getUniqueId() + ".ONLINE", true);
        pD.set(player.getUniqueId() + ".KICKED", null);
        pD.set(player.getUniqueId() + ".IP-ADDRESS", player.getAddress().getHostName());
        saveConfig();
        AchievementAPI.activateAchievement(player, "JOIN_HORIZON");
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("§8» §c- §8┃ §e" + event.getPlayer().getName() + "§7 ist Gegangen.");

        Player player = event.getPlayer();

        Date now = new Date();
        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);


        pD.set(player.getUniqueId() + ".ONLINE", false);
        pD.set(player.getUniqueId() + ".LAST_KNOWN_NAME", player.getName());
        pD.set(player.getUniqueId() + ".LAST_SEEN", (date.format(now)));
        saveConfig();

    }

    @EventHandler(ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent event) {
        String message = "\n§9Horizon §7befindet sich aktuell in §cWartungsarbeiten§7.\n\n§7Mehr §6Informationen \n§7auf unserem §9Discord§7:\n§ahttps://discord.gg/meCh4S2MJy";

        if (JavaPlugin.getPlugin(Main.class).getConfig().getBoolean("MAINTENANCE")) {
            if (!(event.getPlayer().hasPermission("core.maintenance.bypass"))) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
                Bukkit.broadcastMessage("§8» §4✕ §8┃ §e" + event.getPlayer().getName() + "§7 versuchte Beizutreten.");
            } else {
                AchievementAPI.activateAchievement(event.getPlayer(), "I_WAS_HERE_BEFORE_EVERYBODY");
            }
        }
    }


}
