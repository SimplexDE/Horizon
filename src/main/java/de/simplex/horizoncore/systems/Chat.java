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


        /*
        MESSAGE CREATION
         */
        boolean chatEnabled = !Main.getPlugin().getConfig().getBoolean("GLOBALMUTE");

        if (!chatEnabled && !player.hasPermission("core.globalmute.bypass")) {
            player.sendMessage(Main.PREFIX + "Der Chat ist aktuell §cStummgeschaltet§7.");
            return;
        }

        String playerRank = "§7Spieler:in";
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
        } else if (player.hasPermission("rank.dev")) {
            playerRank = "§3Developer";
            playerColor = "§3";
        } else if (player.hasPermission("rank.mod")) {
            playerRank = "§cModerator";
            playerColor = "§c";
        } else if (player.hasPermission("rank.friend")) {
            playerRank = "§5Freund";
            playerColor = "§5";
            messageColor = "§d";
        }

        String chatFormat = (playerRank + " §8┃ " + playerColor + player.getName() + " §8» " + messageColor + message);

        /*
        MESSAGE SENDING
         */
        AchievementAPI.activateAchievement(player, "ERSTE_NACHRICHT");
        // Nicht broadcasten! Funktion:    event.setFormat(); inkl. .replace("(vorher)", "(nachher)"); nutzen!
        Bukkit.broadcastMessage(chatFormat);
    }
}

