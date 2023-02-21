package de.simplex.horizon.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

    public static void defaultSb(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor", "§9§lH§9orizon");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        PlayerConfig pC = PlayerConfig.loadConfig(p);

        Score s1 = o.getScore(" §0 "),
                playerName = o.getScore("§7Dein Name§8: "),

				s2 = o.getScore(" §1 "),
				tokens = o.getScore("§6Tokens§8: "),

				s3 = o.getScore(" §2 "),
				world = o.getScore("§3Welt§8: "),
				sl = o.getScore(" §3 ");

		s1.setScore(20);
		playerName.setScore(19);

		s2.setScore(17);
		tokens.setScore(16);

		s3.setScore(14);
		world.setScore(13);

		sl.setScore(11);

		Team pName = sb.registerNewTeam("pName"),
				tTeam = sb.registerNewTeam("tokens"),
				dWorld = sb.registerNewTeam("dWorld");

		pName.addEntry("" + ChatColor.GREEN);
		tTeam.addEntry("" + ChatColor.GOLD);
		dWorld.addEntry("" + ChatColor.DARK_AQUA);

		pName.setPrefix("   §8» §7" + p.getName());
		tTeam.setPrefix("   §8» §6" + pC.getTokens());
		dWorld.setPrefix("   §8» §b" + p.getWorld().getName());

		o.getScore("" + ChatColor.GREEN).setScore(18);
		o.getScore("" + ChatColor.GOLD).setScore(15);
		o.getScore("" + ChatColor.DARK_AQUA).setScore(12);

		p.setScoreboard(sb);
	}

	public static void updateAll(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
        sb.getTeam("tokens").setPrefix("   §8» §6" + PlayerConfig.loadConfig(p).getTokens());
        sb.getTeam("dWorld").setPrefix("   §8» §b" + p.getWorld().getName());
        sb.getTeam("pName").setPrefix("   §8» §b" + p.getName());
    }

	public static void updateTokens(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
        sb.getTeam("tokens").setPrefix("   §8» §6" + PlayerConfig.loadConfig(p).getTokens());
    }

	public static void updateWorld(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
		sb.getTeam("dWorld").setPrefix("   §8» §b" + p.getWorld().getName());
	}

	public static void unsetScoreboard(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor", "§9§lH§9orizon");
		p.setScoreboard(sb);
	}
}
