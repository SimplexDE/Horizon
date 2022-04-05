package de.simplex.horizoncore.systems;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Das Chat-System
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Chat implements Listener {

    private static final File file = new File("plugins/Horizon/playerData.yml");
    private static final YamlConfiguration pD = YamlConfiguration.loadConfiguration(file);

    private static int getInt(String path) {
        int out = 0;
        try {
            pD.load(file);
            out = pD.getInt(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setCancelled(true);

        Main pl = JavaPlugin.getPlugin(Main.class);

        /*
        MESSAGE CREATION
         */
        boolean chatEnabled = !pl.getConfig().getBoolean("GLOBALMUTE");
        String playerRank = "§7Spieler";
        String playerColor = "§7";
        String messageColor = "§a";

        /*
        RANK ASSIGNING
         */
        if (player.hasPermission("rank.admin")) {
            playerRank = "§4Administrator";
            playerColor = "§4";
            messageColor = "§c";
        } else if (player.hasPermission("rank.con")) {
            playerRank = "§bContent";
            playerColor = "§b";
            messageColor = "§a";
        } else if (player.hasPermission("rank.dev")) {
            playerRank = "§3Developer";
            playerColor = "§3";
            messageColor = "§a";
        } else if (player.hasPermission("rank.mod")) {
            playerRank = "§cModerator";
            playerColor = "§c";
            messageColor = "§a";
        } else if (player.hasPermission("rank.friend")) {
            playerRank = "§5Freund";
            playerColor = "§5";
            messageColor = "§a";
        }

        String chatFormat = (playerRank + " §8┃ " + playerColor + player.getName() + " §8» " + messageColor + message);

        /*
        MESSAGE SENDING
         */
        if (chatEnabled) {
            AchievementAPI.activateAchievement(player, "ERSTE_NACHRICHT");
            Bukkit.broadcastMessage(chatFormat);
        } else {
            if (player.hasPermission("core.globalmute.bypass")) {
                Bukkit.broadcastMessage(chatFormat);
            } else {
                player.sendMessage(Main.PREFIX + "Der Chat ist aktuell §cStummgeschaltet§7.");
            }
        }
    }
}

