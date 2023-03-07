package de.simplex.horizon.method;

import de.simplex.horizon.enums.Color;
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

import static de.simplex.horizon.command.api.LuckPermsAPI.lpapi;

public class Sidebar {

    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
            .useUnusualXRepeatedCharacterHexFormat()
            .hexColors()
            .build();

    public static void defaultSb(Player p) {
        Component displayComponent = MiniMessage.miniMessage().deserialize("   <rainbow>ʜᴏʀɪᴢᴏɴ ᴍʏsᴇʀᴠᴇʀ</rainbow>   ");
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

        Component rangtitleComponent = MiniMessage.miniMessage()
                .deserialize(Color.YELLOW.getColorMiniMessage() + "» " + Color.LIGHT_GRAY.getColorMiniMessage() + "Dein Rang");
        Component worldtitleComponent = MiniMessage.miniMessage()
                .deserialize(Color.YELLOW.getColorMiniMessage() + "» " + Color.LIGHT_GRAY.getColorMiniMessage() + "Aktuelle Welt");
        Component playertitleComponent = MiniMessage.miniMessage()
                .deserialize(Color.YELLOW.getColorMiniMessage() + "» " + Color.LIGHT_GRAY.getColorMiniMessage() + "Aktuelle Spieler");

        String rangtitleRow = legacySerializer.serialize(rangtitleComponent);
        String worldtitleRow = legacySerializer.serialize(worldtitleComponent);
        String playertitleRow = legacySerializer.serialize(playertitleComponent);

        Team Welt = sb.registerNewTeam(worldtitleRow),
                Rang = sb.registerNewTeam(rangtitleRow),
                Spieler = sb.registerNewTeam(playertitleRow);

        Team TWelt = sb.registerNewTeam("TWelt");
        Team TRang = sb.registerNewTeam("TRang");
        Team TSpieler = sb.registerNewTeam("TSpieler");

        TWelt.addEntry("§0");
        TRang.addEntry("§1");
        TSpieler.addEntry("§2");
        Welt.addEntry("§3");
        Rang.addEntry("§4");
        Spieler.addEntry("§5");

        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 1, 8);

        Component rangComponent = MiniMessage.miniMessage().deserialize(color + g.getDisplayName());
        Component worldComponent = MiniMessage.miniMessage()
                .deserialize(Color.GREEN.getColorMiniMessage() + StringUtils.capitalize(p.getWorld().getName()));
        Component playerComponent = MiniMessage.miniMessage()
                .deserialize(Color.GREEN.getColorMiniMessage() +
                        Bukkit.getOnlinePlayers().size() + Color.DARK_GRAY.getColorMiniMessage() + "/" + Color.LIGHT_GREEN.getColorMiniMessage()
                        + Bukkit.getMaxPlayers());

        String rangRow = legacySerializer.serialize(rangComponent);
        String worldRow = legacySerializer.serialize(worldComponent);
        String playerRow = legacySerializer.serialize(playerComponent);

        TWelt.setPrefix(worldRow);
        TRang.setPrefix(rangRow);
        TSpieler.setPrefix(playerRow);
        Welt.setPrefix(worldtitleRow);
        Rang.setPrefix(rangtitleRow);
        Spieler.setPrefix(playertitleRow);

        //Title.setScore(10);
        s4.setScore(9);
        o.getScore("§4").setScore(8);
        o.getScore("§1").setScore(7);
        s3.setScore(6);
        o.getScore("§5").setScore(5);
        o.getScore("§2").setScore(4);
        s2.setScore(3);
        o.getScore("§3").setScore(2);
        o.getScore("§0").setScore(1);
        s1.setScore(0);

        p.setScoreboard(sb);
    }

    public static void updateAll(@NotNull Player p) {
        World world = p.getWorld();
        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());
        String DisplayName = g.getDisplayName();

        if (DisplayName == "null") {
            DisplayName = g.getName();
        }

        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 1, 8);

        Component rangComponent = MiniMessage.miniMessage().deserialize(color + DisplayName);
        Component worldComponent = MiniMessage.miniMessage()
                .deserialize(Color.GREEN.getColorMiniMessage() + StringUtils.capitalize(p.getWorld().getName()));
        Component playerComponent = MiniMessage.miniMessage()
                .deserialize(Color.GREEN.getColorMiniMessage() +
                        Bukkit.getOnlinePlayers().size() + Color.DARK_GRAY.getColorMiniMessage() + "/" + Color.LIGHT_GREEN.getColorMiniMessage()
                        + Bukkit.getMaxPlayers());
        String rangRow = legacySerializer.serialize(rangComponent);
        String worldRow = legacySerializer.serialize(worldComponent);
        String playerRow = legacySerializer.serialize(playerComponent);

        Scoreboard sb = p.getScoreboard();
        sb.getTeam("TWelt").setPrefix(worldRow);
        sb.getTeam("TRang").setPrefix(rangRow);
        sb.getTeam("TSpieler").setPrefix(playerRow);
    }

    public static void updateWorld(@NotNull Player p) {

        Component worldComponent = MiniMessage.miniMessage().deserialize(Color.GREEN.getColorMiniMessage() + StringUtils.capitalize(p.getWorld().getName()));
        String worldRow = legacySerializer.serialize(worldComponent);

        Scoreboard sb = p.getScoreboard();
        sb.getTeam("TWelt")
                .setPrefix(worldRow);
    }

    public static void updateRank(@NotNull Player p) {
        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());
        String DisplayName = g.getDisplayName();

        if (DisplayName == null) {
            DisplayName = g.getName();
        }


        String color = StringUtils.substring(u.getCachedData().getMetaData().getPrefix(), 0, 9);
        if (color == null) {
            color = "<#949494>";
        }

        Component rangComponent = MiniMessage.miniMessage().deserialize(color + DisplayName);
        String rangRow = legacySerializer.serialize(rangComponent);

        Scoreboard sb = p.getScoreboard();
        if (sb.getTeam("TRang") == null) {
            defaultSb(p);
        }
        sb.getTeam("TRang").setPrefix(rangRow);
    }

    public static void updatePlayers(@NotNull Player p) {
        Component playerComponent = MiniMessage.miniMessage().deserialize(Color.GREEN.getColorMiniMessage() +
                Bukkit.getOnlinePlayers().size() + Color.DARK_GRAY.getColorMiniMessage() + "/" + Color.LIGHT_GREEN.getColorMiniMessage()
                + Bukkit.getMaxPlayers());
        String playerRow = legacySerializer.serialize(playerComponent);

        Scoreboard sb = p.getScoreboard();
        sb.getTeam("TSpieler")
                .setPrefix(playerRow);
    }

    public static void unsetScoreboard(@NotNull Player p) {

        Component titleComponent = MiniMessage.miniMessage().deserialize(Color.LIGHT_BLUE.getColorMiniMessage() + "ʜᴏʀɪᴢᴏɴ ᴍʏsᴇʀᴠᴇʀ");
        String titleRow = legacySerializer.serialize(titleComponent);

        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("hor", "hor",
                titleRow);
        p.setScoreboard(sb);
    }
}
