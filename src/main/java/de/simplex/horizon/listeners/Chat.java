package de.simplex.horizon.listeners;

import de.simplex.horizon.commands.utility.MessageSender;
import de.simplex.horizon.horizon.Horizon;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    MessageSender ms = new MessageSender();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        Player p = e.getPlayer();
        String rank = Horizon.getHorizon().PlayerRanks.get(p.getUniqueId());

        e.setCancelled(true);

        String rankColor;


        if (rank.equals("admin")) {
            rankColor = "<#e81a1f>";
        } else if (rank.equals("manager")) {
            rankColor = "<#d81f43>";
        } else if (rank.equals("developer")) {
            rankColor = "<#2ad19d>";
        } else if (rank.equals("content")) {
            rankColor = "<#2390da>";
        } else if (rank.equals("moderator")) {
            rankColor = "<#d3c334>";
        } else if (rank.equals("supporter")) {
            rankColor = "<#ede54c>";
        } else {
            rankColor = "<#8c8c8c>";
        }

        String rankName = StringUtils.capitalize(rank);

        Audience everyone = Horizon.adventure().all();
        Component pre_msg = Component.text(msg, TextColor.color(170, 170, 170));
        Component final_msg = MiniMessage.miniMessage().deserialize(rankColor + rankName + " <dark_gray>┃ " + "<gray>" + rankColor + e.getPlayer().getName() + " <dark_gray>» <gray>").append(pre_msg);
        everyone.sendMessage(final_msg);

    }
}
