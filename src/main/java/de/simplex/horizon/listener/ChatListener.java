package de.simplex.horizon.listener;

import de.simplex.horizon.horizon.Horizon;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        Player p = e.getPlayer();

        User u = lpapi.getUserManager().getUser(e.getPlayer().getUniqueId());
        assert u != null;
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        assert g != null;
        String prefix = u.getCachedData().getMetaData().getPrefix();

        e.setCancelled(true);

        Audience everyone = Horizon.adventure().all();
        Component pre_msg = Component.text("§7" + msg);
        Component final_msg = MiniMessage.miniMessage().deserialize(prefix + p.getName()
                + " <dark_gray>» <gray>").append(pre_msg);
        everyone.sendMessage(final_msg);

    }
}