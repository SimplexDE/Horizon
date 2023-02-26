package de.simplex.horizon.commands.utility;

import de.simplex.horizon.methods.HexConverter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TabListCompiler {

    public void setTabListHeader(Audience player, Component header) {
        player.sendPlayerListHeader(header);
    }

    public void setTabListFooter(Audience player, Component footer) {
        player.sendPlayerListFooter(footer);
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

    public void setNameTag(Player player, String rank) {
        String coloredTag;
        if (rank.equals("admin")) {
            coloredTag = HexConverter.getColString("<#e81a1f>Admin <#AAAAAA>┃ <#e81a1f>" + player.getName());
        } else if (rank.equals("manager")) {
            coloredTag = HexConverter.getColString("<#d81f43>Man <#AAAAAA>┃ <#d81f43>" + player.getName());
        } else if (rank.equals("developer")) {
            coloredTag = HexConverter.getColString("<#2ad19d>Dev <#AAAAAA>┃ <#2ad19d>" + player.getName());
        } else if (rank.equals("content")) {
            coloredTag = HexConverter.getColString("<#2390da>Con <#AAAAAA>┃ <#2390da>" + player.getName());
        } else if (rank.equals("moderator")) {
            coloredTag = HexConverter.getColString("<#d3c334>Mod <#AAAAAA>┃ <#d3c334>" + player.getName());
        } else if (rank.equals("supporter")) {
            coloredTag = HexConverter.getColString("<#ede54c>Sup <#AAAAAA>┃ <#ede54c>" + player.getName());
        } else {
            coloredTag = HexConverter.getColString("<#8c8c8c>" + player.getName());
        }

        player.setPlayerListName(coloredTag);
    }
}
