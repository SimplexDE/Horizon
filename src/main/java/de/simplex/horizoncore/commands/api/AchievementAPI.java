package de.simplex.horizoncore.commands.api;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Die AchievementAPI für die Achievement Verwaltung.
 *
 * @author Simplex
 * @version 0.1
 * @since 1.0-Beta
 */
public class AchievementAPI {

	/**
	 * pConfig anwenden!
	 * Beispiel für einen möglichen String:
	 * "Player.stats.achievementData.<Name>.<Wert>"
	 */

    private static final File AchievementConfig = new File("plugins/Horizon/achievementData.yml");
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

        String achievementName = "";
        String formatMsg = "§8» §d✯ §8┃ §e%s §7hat die Errungenschaft §a\"%s\"§7 freigeschaltet!";

        switch (achievement) {
            case "JOIN_HORIZON":
                achievementName = "Betrete Horzion";
                Bukkit.broadcastMessage(String.format(formatMsg, target.getName(), achievementName));
                break;
            case "FRIEDE_FREUDE_EIERKUCHEN":
                achievementName = "Friede, Freude, Eierkuchen!";
                Bukkit.broadcastMessage(String.format(formatMsg, target.getName(), achievementName));
                break;
            case "ERSTE_NACHRICHT":
                achievementName = "Schreibe deine erste Nachricht";
                Bukkit.broadcastMessage(String.format(formatMsg, target.getName(), achievementName));
                break;
        }
    }

    public static void activateAchievement(Player target, String achievement) {
        for (String allowedAchievement : allowedAchievements) {
            if (achievement.equals(allowedAchievement)) {
                if (!(getBoolean(target.getUniqueId() + "." + achievement))) {
                    set(target.getUniqueId() + "." + achievement, true);
                    save();
                    announceAchievement(target, achievement);
                    target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                }
            }
        }
    }

    public static void getAchievements(Player target) {
        List<String> List = null;
        for (String allowedAchievement : allowedAchievements) {
            if (getBoolean(target.getUniqueId() + "." + allowedAchievement)) {
                target.sendMessage(allowedAchievement);
            }
        }
    }


}
