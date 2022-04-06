package de.simplex.horizoncore.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Das Tab-List-System
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Tablist {

    private static Scoreboard sb;

    private static final File file = new File("plugins/Horizon/playerData.yml");
    private static final YamlConfiguration pD = YamlConfiguration.loadConfiguration(file);

    public static void setSb() {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();

        sb.registerNewTeam("000Admin").setPrefix("§4Admin §8┃ §c");
        sb.registerNewTeam("001Content").setPrefix("§bCon §8┃ §7");
        sb.registerNewTeam("002Dev").setPrefix("§3Dev §8┃ §7");
        sb.registerNewTeam("003Mod").setPrefix("§cMod §8┃ §7");
        sb.registerNewTeam("004Friend").setPrefix("§5Freund §8┃ §7");
        sb.registerNewTeam("005Spieler").setPrefix("§7Spieler §8┃ §7");

        sb.getTeam("000Admin").setColor(ChatColor.DARK_RED);
        sb.getTeam("001Content").setColor(ChatColor.AQUA);
        sb.getTeam("002Dev").setColor(ChatColor.DARK_AQUA);
        sb.getTeam("003Mod").setColor(ChatColor.RED);
        sb.getTeam("004Friend").setColor(ChatColor.DARK_PURPLE);
        sb.getTeam("005Spieler").setColor(ChatColor.GRAY);

        Collection<? extends org.bukkit.entity.Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player all : Bukkit.getOnlinePlayers()) {
            setTeams(all);
        }
    }

    public static void setTeams(Player player) {
        String team;

        if (player.hasPermission("rank.admin")) {
            team = "000Admin";
        } else if (player.hasPermission("rank.con")) {
            team = "001Content";
        } else if (player.hasPermission("rank.dev")) {
            team = "002Dev";
        } else if (player.hasPermission("rank.mod")) {
            team = "003Mod";
        } else if (player.hasPermission("rank.friend")) {
            team = "004Friend";
        } else {
            team = "005Spieler";
        }

        sb.getTeam(team).addPlayer(player);
        player.setScoreboard(sb);
    }

}