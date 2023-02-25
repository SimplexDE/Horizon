package de.simplex.horizon.listeners;

import de.simplex.horizon.commands.utility.MessageSender;
import de.simplex.horizon.horizon.Horizon;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

        e.setCancelled(true);

        Audience everyone = Horizon.adventure().all();
        Component pre_msg = Component.text(msg, TextColor.color(170, 170, 170));
        Component final_msg = MiniMessage.miniMessage().deserialize("rank" + " <dark_gray>┃ " + "<gray>" + "rankColor" + e.getPlayer().getName() + " <dark_gray>» <gray>").append(pre_msg);
        everyone.sendMessage(final_msg);

    }
}
