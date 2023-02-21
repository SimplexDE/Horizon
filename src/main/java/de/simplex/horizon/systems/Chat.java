package de.simplex.horizon.systems;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.AchievementEngine;
import de.simplex.horizon.commands.api.MessageEngine;
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

	private final Main Main;

	public Chat(de.simplex.horizon.Main main) {
		Main = main;
	}

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

		String playerRank = "<#8c8c8c>Spieler",
				playerColor = "<#8c8c8c>",
				messageColor = "§a";

        /*
        RANK ASSIGNING
         */
		if (player.hasPermission("rank.admin")) {
			playerRank = "<#e81a1f>Administrator";
			playerColor = "<#e81a1f>";
			messageColor = "§c";
		} else if (player.hasPermission("rank.con")) {
			playerRank = "<#2390da>Content";
			playerColor = "<#2390da>";
		} else if (player.hasPermission("rank.dev")) {
			playerRank = "<#2ad19d>Developer";
			playerColor = "<#2ad19d>";
		} else if (player.hasPermission("rank.mod")) {
			playerRank = "<#d3c334>Moderator";
			playerColor = "<#d3c334>";
		} else if (player.hasPermission("rank.sup")) {
			playerRank = "<#ede54c>Supporter";
			playerColor = "<#ede54c>";
		} else if (player.hasPermission("rank.friend")) {
			playerRank = "<#af33c6>Freund";
			playerColor = "<#af33c6>";
		}

		MessageEngine ME = new MessageEngine(this.Main);



        /*
        MESSAGE SENDING
         */
		AchievementEngine.activateAchievement(player, "ERSTE_NACHRICHT");
		e.setCancelled(true);
		ME.sendChatMessage(e.getPlayer().getName(), playerRank, playerColor, message);
	}
}

