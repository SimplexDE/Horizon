package de.simplex.horizoncore.systems;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

        /*
        MESSAGE CREATION
         */
		boolean chatEnabled = Main.getPlugin().getConfig().getBoolean("GLOBALMUTE");

		if (chatEnabled && !player.hasPermission("core.globalmute.bypass")) {
			player.sendMessage(Main.PREFIX + "Der Chat ist aktuell §cStummgeschaltet§7.");
			e.setCancelled(true);
			Bukkit.getConsoleSender().sendMessage(player.getName()
					+ " hat versucht: \"" + e.getMessage() + "\" zu schreiben.");
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
			playerRank = "§2Moderator";
			playerColor = "§2";
		} else if (player.hasPermission("rank.sup")) {
			playerRank = "§a";
			playerColor = "§a";
		} else if (player.hasPermission("rank.friend")) {
			playerRank = "§5Freund";
			playerColor = "§5";
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

