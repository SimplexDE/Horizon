package de.simplex.horizon.method;

import de.simplex.horizon.util.MessageSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
            header = MiniMessage.miniMessage().deserialize("<newline> <grey>▬▬▬▬▬ <rainbow>ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ</rainbow> <grey>▬▬▬▬▬ <newline>");
            footer = MiniMessage.miniMessage().deserialize("<newline><green>" + Bukkit.getOnlinePlayers().size() + "<gray> Spieler<newline><newline> <grey>▬▬▬▬▬ <rainbow>ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ</rainbow> <grey>▬▬▬▬▬ <newline>");
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

        Component prefixComponent = MiniMessage.miniMessage().deserialize(prefix + player.getName() + suffix);
        String prefixString = legacySerializer.serialize(prefixComponent);

        String playerListName = HexConverter.getColString(prefixString.replace("null", ""));
        player.setPlayerListName(playerListName);
    }
}
