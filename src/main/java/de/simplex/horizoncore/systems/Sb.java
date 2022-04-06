package de.simplex.horizoncore.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Sb {

    public static void defaultSb(Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor", "§9§lH§9orizon");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        pConfig pC = pConfig.loadConfig(p);

        Score s1 = o.getScore(" §0 "),
                playerName = o.getScore("§7Spielername§8: "),

                s2 = o.getScore(" §1 "),
                tokens = o.getScore("§6Tokens§8: ");

        s1.setScore(20);
        playerName.setScore(19);

        s2.setScore(17);
        tokens.setScore(16);

        Team pName = sb.registerNewTeam("pName");
        Team tTeam = sb.registerNewTeam("tokens");

        pName.addEntry("" + ChatColor.GREEN);
        tTeam.addEntry("" + ChatColor.GOLD);

        pName.setPrefix("   > " + p.getName());
        tTeam.setPrefix("   > " + pC.getTokens());

        o.getScore("" + ChatColor.GREEN).setScore(18);
        o.getScore("" + ChatColor.GOLD).setScore(15);

        p.setScoreboard(sb);
    }

    public static void updateTokens(Player p) {
        Scoreboard sb = p.getScoreboard();
        sb.getTeam("tokens").setPrefix("   > " + pConfig.loadConfig(p).getTokens());
    }

    public static void unsetScoreboard(Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor", "§9§lH§9orizon");
        p.setScoreboard(sb);
    }
}
