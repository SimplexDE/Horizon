package de.simplex.horizoncore.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
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

    /**
     * Bitte überarbeiten!
     */

    public static void setSb() {
        /**
         * Ich wüsste nicht, das Scoreboards Tablists sind? Bitte anpassen, bei Fragen @TearsDontFall
         */
        sb = Bukkit.getScoreboardManager().getNewScoreboard();

        sb.registerNewTeam("000Admin").setPrefix("§4Admin §8┃ §c");
        sb.registerNewTeam("001Content").setPrefix("§bCon §8┃ §7");
        sb.registerNewTeam("002Dev").setPrefix("§3Dev §8┃ §7");
        sb.registerNewTeam("003Mod").setPrefix("§2Mod §8┃ §7");
        sb.registerNewTeam("004Sup").setPrefix("§aSup §8┃ §7");
        sb.registerNewTeam("005Friend").setPrefix("§5Freund §8┃ §7");
        sb.registerNewTeam("006Spieler").setPrefix("§7Spieler §8┃ §7");

        sb.getTeam("000Admin").setColor(ChatColor.DARK_RED);
        sb.getTeam("001Content").setColor(ChatColor.AQUA);
        sb.getTeam("002Dev").setColor(ChatColor.DARK_AQUA);
        sb.getTeam("003Mod").setColor(ChatColor.DARK_GREEN);
        sb.getTeam("004Sup").setColor(ChatColor.GREEN);
        sb.getTeam("005Friend").setColor(ChatColor.DARK_PURPLE);
        sb.getTeam("006Spieler").setColor(ChatColor.GRAY);

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
        } else if (player.hasPermission("rank.sup")) {
            team = "004Sup";
        } else if (player.hasPermission("rank.friend")) {
            team = "005Friend";
        } else {
            team = "006Spieler";
        }

        sb.getTeam(team).addPlayer(player);
        player.setScoreboard(sb);
    }

}