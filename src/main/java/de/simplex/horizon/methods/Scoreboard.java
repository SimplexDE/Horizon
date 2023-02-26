package de.simplex.horizon.methods;

import de.simplex.horizon.horizon.Horizon;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

    public static void defaultSb(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor", HexConverter.getColString("<#43adf2>   ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ   "));
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        PlayerConfig pC = PlayerConfig.loadConfig(p);

        Score s1 = o.getScore(" §0 "),
                s2 = o.getScore(" §1 "),
                s3 = o.getScore(" §2 "),
                s4 = o.getScore(" §3 ");

        Score Welt = o.getScore(HexConverter.getColString("§8» §7Welt")),
                Rang = o.getScore(HexConverter.getColString("§8» §7Rang")),
                Spieler = o.getScore(HexConverter.getColString("§8» §7Spieler"));
        //Title = o.getScore(HexConverter.getColString("<#43adf2>     ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ"));

        Team TWelt = sb.registerNewTeam("TWelt"),
                TRang = sb.registerNewTeam("TRang"),
                TSpieler = sb.registerNewTeam("TSpieler");

        TWelt.addEntry("§0");
        TRang.addEntry("§1");
        TSpieler.addEntry("§2");

        String rank = Horizon.getHorizon().PlayerRanks.get(p.getUniqueId());
        String rankColor = Horizon.getHorizon().RankColors.get(rank);

        TWelt.setPrefix(HexConverter.getColString("<#4ffe41>" + StringUtils.capitalize(p.getWorld().getName())));
        TRang.setPrefix(HexConverter.getColString(rankColor + StringUtils.capitalize(rank)));
        TSpieler.setPrefix(HexConverter.getColString("<#4ffe41>" + Bukkit.getOnlinePlayers().size() + "<#AAAAAA> / <#3abd30>" + Bukkit.getMaxPlayers()));

        //Title.setScore(10);
        s4.setScore(9);
        Rang.setScore(8);
        o.getScore("§1").setScore(7);
        s3.setScore(6);
        Spieler.setScore(5);
        o.getScore("§2").setScore(4);
        s2.setScore(3);
        Welt.setScore(2);
        o.getScore("§0").setScore(1);
        s1.setScore(0);


        p.setScoreboard(sb);
    }

    public static void updateAll(Player p) {

        String rank = Horizon.getHorizon().PlayerRanks.get(p.getUniqueId());
        String rankColor = Horizon.getHorizon().RankColors.get(rank);

        org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
        sb.getTeam("TWelt").setPrefix(HexConverter.getColString("<#4ffe41>" + StringUtils.capitalize(p.getWorld().getName())));
        sb.getTeam("TRang").setPrefix(HexConverter.getColString(rankColor + StringUtils.capitalize(rank)));
        sb.getTeam("TSpieler").setPrefix("<#9aa548>" + Bukkit.getOnlinePlayers().size());
    }

    public static void updateWorld(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
        sb.getTeam("TWelt").setPrefix(HexConverter.getColString("<#4ffe41>" + StringUtils.capitalize(p.getWorld().getName())));
    }

    public static void updateRank(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
        String rank = Horizon.getHorizon().PlayerRanks.get(p.getUniqueId());
        String rankColor = Horizon.getHorizon().RankColors.get(rank);
        sb.getTeam("TRang").setPrefix(HexConverter.getColString(rankColor + StringUtils.capitalize(rank)));
    }

    public static void updatePlayers(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
        sb.getTeam("TSpieler").setPrefix(HexConverter.getColString("<#4ffe41>" + Bukkit.getOnlinePlayers().size() + "<#AAAAAA> / <#3abd30>" + Bukkit.getMaxPlayers()));
    }

    public static void unsetScoreboard(Player p) {
        org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor", HexConverter.getColString("<#43adf2>ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ"));
        p.setScoreboard(sb);
    }
}
