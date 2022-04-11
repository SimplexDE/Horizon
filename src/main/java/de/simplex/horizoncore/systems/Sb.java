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

		PConfig pC = PConfig.loadConfig(p);

		Score s1 = o.getScore(" §0 "),
				playerName = o.getScore("§7Dein Name§8: "),

				s2 = o.getScore(" §1 "),
				tokens = o.getScore("§6Tokens§8: "),

				s3 = o.getScore(" §2 "),
				world = o.getScore("§3Welt§8: ");

		s1.setScore(20);
		playerName.setScore(19);

		s2.setScore(17);
		tokens.setScore(16);

		s3.setScore(14);
		world.setScore(13);

		Team pName = sb.registerNewTeam("pName"),
				tTeam = sb.registerNewTeam("tokens"),
				dWorld = sb.registerNewTeam("dWorld");

		pName.addEntry("" + ChatColor.GREEN);
		tTeam.addEntry("" + ChatColor.GOLD);
		dWorld.addEntry("" + ChatColor.DARK_AQUA);

		pName.setPrefix("   §8» §7" + p.getName());
		tTeam.setPrefix("   §8» §6" + pC.getTokens());
		tTeam.setPrefix("   §8» §b" + p.getWorld());

		o.getScore("" + ChatColor.GREEN).setScore(18);
		o.getScore("" + ChatColor.GOLD).setScore(15);
		o.getScore("" + ChatColor.DARK_AQUA).setScore(12);

		p.setScoreboard(sb);
	}

	public static void updateAll(Player p) {
		Scoreboard sb = p.getScoreboard();
		sb.getTeam("tokens").setPrefix("   §8» §6" + PConfig.loadConfig(p).getTokens());
		sb.getTeam("dWorld").setPrefix("   §8» §b" + p.getWorld());
		sb.getTeam("pName").setPrefix("   §8» §b" + p.getName());
	}

	public static void updateTokens(Player p) {
		Scoreboard sb = p.getScoreboard();
		sb.getTeam("tokens").setPrefix("   §8» §6" + PConfig.loadConfig(p).getTokens());
	}

	public static void updateWorld(Player p) {
		Scoreboard sb = p.getScoreboard();
		sb.getTeam("dWorld").setPrefix("   §8» §b" + p.getWorld());
	}

	public static void unsetScoreboard(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("hor", "hor", "§9§lH§9orizon");
		p.setScoreboard(sb);
	}
}
