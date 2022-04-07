package de.simplex.horizoncore.systems;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.AchievementAPI;
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

/**
 * Das Connection-System
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Connection implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onLogin(PlayerLoginEvent event) {
		String message = "\n§9Horizon §7befindet sich aktuell in §cWartungsarbeiten§7.\n\n§7Mehr §6Informationen \n§7auf unserem §9Discord§7:\n§ahttps://discord.gg/meCh4S2MJy";

		if (Main.getPlugin().getConfig().getBoolean("MAINTENANCE")) {
			if (!(event.getPlayer().hasPermission("core.maintenance.bypass"))) {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
				Bukkit.broadcastMessage("§8» §4✕ §8┃ §e" + event.getPlayer().getName() + "§7 versuchte Beizutreten.");
			} else {
				AchievementAPI.activateAchievement(event.getPlayer(), "I_WAS_HERE_BEFORE_EVERYBODY");
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PConfig pC = PConfig.loadConfig(player);

		if (!player.hasPlayedBefore()) {
			pC.setTokens(1000);
			player.sendMessage(Main.PREFIX + "Da du das erste Mal gejoined bist, erhälst du §e1000§7 Tokens!");
		}

		Sb.defaultSb(player);
		Tablist.setSb();
		event.setJoinMessage("§8» §a+ §8┃ §e" + player.getName() + "§7 ist Beigetreten.");

		pC.set(player.getUniqueId() + ".ONLINE", true);
		pC.set(player.getUniqueId() + ".KICKED", null);
		pC.set(player.getUniqueId() + ".IP-ADDRESS", player.getAddress().getHostName());
		pC.save();
		AchievementAPI.activateAchievement(player, "JOIN_HORIZON");
	}

	@EventHandler(ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage("§8» §c- §8┃ §e" + event.getPlayer().getName() + "§7 ist Gegangen.");

		Player player = event.getPlayer();
		PConfig pC = PConfig.loadConfig(player);

		Date now = new Date();
		DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);


		pC.set(player.getUniqueId() + ".ONLINE", false);
		pC.set(player.getUniqueId() + ".LAST_KNOWN_NAME", player.getName());
		pC.set(player.getUniqueId() + ".LAST_SEEN", (date.format(now)));
		pC.save();

	}


}
