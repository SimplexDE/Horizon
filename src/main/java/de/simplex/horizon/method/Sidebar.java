package de.simplex.horizon.method;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class Sidebar {

    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
            .useUnusualXRepeatedCharacterHexFormat()
            .hexColors()
            .build();

    public static void defaultSb(Player p) {

        Component displayComponent = MiniMessage.miniMessage().deserialize("<#43adf2>   ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ   ");
        String displayString = legacySerializer.serialize(displayComponent);

        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor", displayString);
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        PlayerConfig pC = PlayerConfig.loadConfig(p);
        World world = p.getWorld();

        Score s1 = o.getScore(" §0 "),
                s2 = o.getScore(" §1 "),
                s3 = o.getScore(" §2 "),
                s4 = o.getScore(" §3 ");

        Score Welt = o.getScore("§8» §7Welt"),
                Rang = o.getScore("§8» §7Rang"),
                Spieler = o.getScore("§8» §7Spieler");

        Team TWelt = sb.registerNewTeam("TWelt");
        Team TRang = sb.registerNewTeam("TRang");
        Team TSpieler = sb.registerNewTeam("TSpieler");

        TWelt.addEntry("§0");
        TRang.addEntry("§1");
        TSpieler.addEntry("§2");

        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 1, 8);

        Component rangComponent = MiniMessage.miniMessage().deserialize(color + g.getDisplayName());
        Component worldComponent = MiniMessage.miniMessage()
                .deserialize("<#4ffe41>" + StringUtils.capitalize(world.getName()));
        Component playerComponent = MiniMessage.miniMessage()
                .deserialize("<#4ffe41>" + Bukkit.getOnlinePlayers().size()
                        + "<#AAAAAA> / <#3abd30>" + Bukkit.getMaxPlayers());

        String rangRow = legacySerializer.serialize(rangComponent);
        String worldRow = legacySerializer.serialize(worldComponent);
        String playerRow = legacySerializer.serialize(playerComponent);

        TWelt.setPrefix(worldRow);
        TRang.setPrefix(rangRow);
        TSpieler.setPrefix(playerRow);

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

    public static void updateAll(@NotNull Player p) {
        World world = p.getWorld();
        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 1, 8);

        Component rangComponent = MiniMessage.miniMessage().deserialize(color + g.getDisplayName());
        Component worldComponent = MiniMessage.miniMessage()
                .deserialize("<#4ffe41>" + StringUtils.capitalize(world.getName()));
        Component playerComponent = MiniMessage.miniMessage()
                .deserialize("<#4ffe41>" + Bukkit.getOnlinePlayers().size()
                        + "<#AAAAAA> / <#3abd30>" + Bukkit.getMaxPlayers());
        String rangRow = legacySerializer.serialize(rangComponent);
        String worldRow = legacySerializer.serialize(worldComponent);
        String playerRow = legacySerializer.serialize(playerComponent);

        Scoreboard sb = p.getScoreboard();
        sb.getTeam("TWelt").setPrefix(worldRow);
        sb.getTeam("TRang").setPrefix(rangRow);
        sb.getTeam("TSpieler").setPrefix(playerRow);
    }

    public static void updateWorld(@NotNull Player p) {
        Scoreboard sb = p.getScoreboard();
        sb.getTeam("TWelt")
                .setPrefix(HexConverter.getColString("<#4ffe41>" + StringUtils.capitalize(p.getWorld().getName())));
    }

    public static void updateRank(@NotNull Player p) {
        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 0, 9);

        Component rangComponent = MiniMessage.miniMessage().deserialize(color + g.getDisplayName());
        String rangRow = legacySerializer.serialize(rangComponent);

        Scoreboard sb = p.getScoreboard();
        if (sb.getTeam("TRang") == null) {
            defaultSb(p);
        }
        sb.getTeam("TRang").setPrefix(rangRow);
    }

    public static void updatePlayers(@NotNull Player p) {
        Scoreboard sb = p.getScoreboard();
        sb.getTeam("TSpieler")
                .setPrefix(HexConverter.getColString("<#4ffe41>" + Bukkit.getOnlinePlayers().size()
                        + "<#AAAAAA> / <#3abd30>" + Bukkit.getMaxPlayers()));
    }

    public static void unsetScoreboard(@NotNull Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor",
                HexConverter.getColString("<#43adf2>ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ"));
        p.setScoreboard(sb);
    }
}
