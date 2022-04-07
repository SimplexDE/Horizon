package de.simplex.horizoncore.systems;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.AchievementAPI;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;

/**
 * Das Chat-System
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Chat implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		String message = e.getMessage();
		e.setCancelled(true);

        /*
        MESSAGE CREATION
         */
		boolean chatEnabled = !Main.getPlugin().getConfig().getBoolean("GLOBALMUTE");

		if (!chatEnabled && !player.hasPermission("core.globalmute.bypass")) {
			player.sendMessage(Main.PREFIX + "Der Chat ist aktuell §cStummgeschaltet§7.");
			return;
		}

		String playerRank = "§7Spieler:in",
				playerColor = "§7",
				messageColor = "§a";

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

		message = message.replace("%%", "%");
		String chatFormat = (playerRank + " §8┃ " + playerColor + player.getName() + " §8» " + messageColor + message);

        /*
        MESSAGE SENDING
         */
		AchievementAPI.activateAchievement(player, "ERSTE_NACHRICHT");
		e.setFormat(chatFormat);
	}
}

