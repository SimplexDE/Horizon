package de.simplex.horizon.method;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.util.MessageSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class Tablist {

    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
            .useUnusualXRepeatedCharacterHexFormat()
            .hexColors()
            .build();

    public void setTabListHeader(@NotNull Audience player, Component header) {
        player.sendPlayerListHeader(header);
    }

    MessageSender ms = new MessageSender();

    public void setTabListHeaderAndFooter(Audience player) {
        Component header = null;
        Component footer = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            header = MiniMessage.miniMessage().deserialize("<newline>" + Color.GRAY.getColorMiniMessage() +
                    " ▬▬▬▬▬ <rainbow>ʜᴏʀɪᴢᴏɴ ᴍʏsᴇʀᴠᴇʀ</rainbow>" + Color.GRAY.getColorMiniMessage() + " ▬▬▬▬▬ <newline>");
            footer = MiniMessage.miniMessage().deserialize("<newline>" + Color.GREEN.getColorMiniMessage()
                    + Bukkit.getOnlinePlayers().size() + Color.ORANGE.getColorMiniMessage() + " Spieler<newline><newline>" +
                    Color.GRAY.getColorMiniMessage() + " ▬▬▬▬▬ <rainbow>ʜᴏʀɪᴢᴏɴ ᴍʏsᴇʀᴠᴇʀ</rainbow>"
                    + Color.GRAY.getColorMiniMessage() + " ▬▬▬▬▬ <newline>");
        }

        player.sendPlayerListHeader(header);
        player.sendPlayerListFooter(footer);
    }

    public void setTabListFooter(@NotNull Audience player, Component footer) {
        player.sendPlayerListFooter(footer);
    }

    public void setNameTag(@NotNull Player player, @NotNull User u) {
        String prefix = u.getCachedData().getMetaData().getPrefix();
        String suffix = u.getCachedData().getMetaData().getSuffix();
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());
        Scoreboard sb = player.getScoreboard();


        for (Player ap : Bukkit.getOnlinePlayers()) {
            User au = lpapi.getUserManager().getUser(ap.getUniqueId());
            Group gap = lpapi.getGroupManager().getGroup(au.getPrimaryGroup());
            int priority = gap.getWeight().getAsInt();
            String groupName = gap.getFriendlyName();
            String playerName = ap.getName();
            if (groupName.length() > 15) {
                groupName = groupName.substring(0, 15);
            }
            char priorityChar = (char) (19968 + priority);
            String teamName = priorityChar + groupName;
            Team team = sb.getTeam(teamName);
            if (team == null) {
                team = sb.registerNewTeam(teamName);
            }
            team.addEntry(playerName);
        }


        Component prefixComponent = MiniMessage.miniMessage().deserialize(prefix + player.getName() + suffix);
        String prefixString = legacySerializer.serialize(prefixComponent);

        String playerListName = prefixString.replace("null", "");
        player.setPlayerListName(playerListName);
    }
}
