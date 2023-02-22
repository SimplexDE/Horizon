package de.simplex.horizon.commandsOLD.api;

import de.simplex.horizon.Main;
import de.simplex.horizon.systemsOLD.PlayerConfig;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Die AchievementAPI für die Achievement Verwaltung.
 *
 * @author Simplex
 * @version 0.1
 * @since 1.0-Beta
 */
public class AchievementEngine {

	private static final File AchievementConfig = new File("plugins/Horizon/achievementData.yml");
	/**
	 * pConfig anwenden!
	 * Beispiel für einen möglichen String:
	 * "Player.stats.achievementData.<Name>.<Wert>"
	 */

	private static Main Main;

	public AchievementEngine(de.simplex.horizon.Main main) {
		Main = main;
	}
	private static final YamlConfiguration AConfig = YamlConfiguration.loadConfiguration(AchievementConfig);


	/**
	 * AchievementConfig speichern
	 */
	private static void save() {
		try {
			AConfig.save(AchievementConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * AchievementConfig Wert setzen
	 *
	 * @param path  Der Path zu dem Wert
	 * @param value Der neue Wert
	 */
	private static void set(String path, Object value) {
		AConfig.set(path, value);
	}

	/**
	 * AchievementConfig einen Boolean abfragen.
	 *
	 * @param path Der Path zu dem Wert
	 * @return true / false
	 */
	private static boolean getBoolean(String path) {
		boolean out = false;
		try {
			AConfig.load(AchievementConfig);
			out = AConfig.getBoolean(path);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
		return out;
	}


	public static String[] allowedAchievements =
			{
					"JOIN_HORIZON",
					"FRIEDE_FREUDE_EIERKUCHEN",
					"ERSTE_NACHRICHT",
			};

	public static void announceAchievement(Player target, String achievement) {

		MessageEngine ME = new MessageEngine(Main);

		String achievementName = "",
				formatMsg = "<dark_grey>» <light_purple>✯ <dark_grey>┃ <yellow>%s <grey>hat die Errungenschaft <gold>[<rainbow>%s</rainbow><gold>] <gray>freigeschaltet!";

		switch (achievement) {
			case "JOIN_HORIZON" -> {
				achievementName = "Betritt Horizon";
			}
			case "FRIEDE_FREUDE_EIERKUCHEN" -> {
				achievementName = "Friede, Freude, Eierkuchen!";
			}
			case "ERSTE_NACHRICHT" -> {
				achievementName = "Schreibe deine erste Nachricht";
			}
		}
		ME.broadcast(String.format(formatMsg, target.getName(), achievementName));
	}

	public static void activateAchievement(Player target, String achievement) {

		PlayerConfig pC = PlayerConfig.loadConfig(target);

		for (String allowedAchievement : allowedAchievements) {
			if (achievement.equals(allowedAchievement)) {
				if (!(pC.getBoolean(String.format("achievementData.%s", achievement)))) {
					pC.set(String.format("achievementData.%s", achievement), true);
					pC.save();
					announceAchievement(target, achievement);
					target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				}
			}
		}
	}

	public static List<String> getAchievements(Player target) {

		PlayerConfig pC = PlayerConfig.loadConfig(target);

		List<String> ach = new ArrayList<>();
		for (String allowedAchievement : allowedAchievements) {
			if ((pC.getBoolean(String.format("achievementData.%s", allowedAchievement)))) {
				ach.add(allowedAchievement);
			}
		}
		return ach;
	}

	public static void sendAchieved(Player target) {

		List<String> ach = getAchievements(target);
		if (ach == null || ach.isEmpty()) {
			target.sendMessage("Du hast noch keine Achievements.");
			return;
		}
		target.sendMessage(String.format("Du hast folgende Achievements §8[<red>%s</red>§8]: ", ach.size()));
		for (String s : ach) {
			target.sendMessage(String.format("  §a%s", s));
		}

	}
}
