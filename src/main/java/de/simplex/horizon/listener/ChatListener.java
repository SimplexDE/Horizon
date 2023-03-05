package de.simplex.horizon.listener;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.method.PlayerConfig;
import de.simplex.horizon.util.MessageSender;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class ChatListener implements Listener {

    private final double ChatRange = 50.0;
    private final double ShoutRange = 100.0;

    private final String LocalMessage = Color.LIGHT_GRAY.getColorMiniMessage() + "Local " + Color.DARK_GRAY.getColorMiniMessage()
            + "▕ " + Color.LIGHT_GRAY.getColorMiniMessage() + "%s" + Color.DARK_GRAY.getColorMiniMessage() + " » "
            + Color.LIGHT_GRAY.getColorMiniMessage() + "%s";
    ;
    private final String ShoutMessage = Color.LIGHT_YELLOW.getColorMiniMessage() + "Shout " + Color.DARK_GRAY.getColorMiniMessage()
            + "▕ " + Color.LIGHT_GRAY.getColorMiniMessage() + "%s" + Color.DARK_GRAY.getColorMiniMessage() + " » "
            + Color.LIGHT_YELLOW.getColorMiniMessage() + "%s";
    private final String GlobalMessage = Color.ORANGE.getColorMiniMessage() + "Global " + Color.DARK_GRAY.getColorMiniMessage()
            + "▕ " + Color.LIGHT_GRAY.getColorMiniMessage() + "%s" + Color.DARK_GRAY.getColorMiniMessage() + " » "
            + Color.LIGHT_GREEN.getColorMiniMessage() + "%s";
    private final String SpyMessage = Color.RED.getColorMiniMessage() + "Spy " + Color.DARK_GRAY.getColorMiniMessage()
            + "▕ " + Color.LIGHT_GRAY.getColorMiniMessage() + "%s" + Color.DARK_GRAY.getColorMiniMessage() + " » "
            + Color.LIGHT_RED.getColorMiniMessage() + "%s";

    private MessageSender ms = new MessageSender();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player sender = e.getPlayer();
        String message = e.getMessage().replace("<", "⏴").replace(">", "⏵");

        User u = lpapi.getUserManager().getUser(sender.getPlayer().getUniqueId());
        assert u != null;
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        assert g != null;
        String prefix = u.getCachedData().getMetaData().getPrefix();
        if (prefix == null) {
            prefix = "<#949494>";
        }

        e.setCancelled(true);

        if (message.startsWith("!")) {
            ms.sendToSender(sender, ShoutMessage.formatted(sender.getName(), message.substring(1)));
        } else if (message.startsWith("@")) {
            ms.sendToSender(sender, GlobalMessage.formatted(prefix + sender.getName(), message.substring(1)));
        } else {
            ms.sendToSender(sender, LocalMessage.formatted(sender.getName(), message));
        }

        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if (receiver.equals(sender)) {
                continue;
            }

            PlayerConfig pC = new PlayerConfig(receiver);

            if (message.startsWith("@")) {
                ms.sendToSender(receiver, GlobalMessage.formatted(prefix + sender.getName(), message.substring(1)));
                ms.sendToConsole(GlobalMessage.formatted(prefix + sender.getName(), message.substring(1)));
                continue;
            }

            if (message.startsWith("!") && sender.getLocation().distance(receiver.getLocation()) < ShoutRange) {
                ms.sendToPlayer(receiver, ShoutMessage.formatted(sender.getName(), message.substring(1)));
                ms.sendToConsole(ShoutMessage.formatted(sender.getName(), message.substring(1)));
                continue;
            }

            if (sender.getLocation().distance(receiver.getLocation()) < ChatRange) {
                ms.sendToPlayer(receiver, LocalMessage.formatted(sender.getName(), message));
                ms.sendToConsole(LocalMessage.formatted(sender.getName(), message.substring(1)));
                continue;
            }

            if (pC.isSet("staff.chatspy") && pC.getBoolean("staff.chatspy")) {
                if (!message.startsWith("@")) {
                    ms.sendToPlayer(receiver, SpyMessage.formatted(sender.getName(), message));
                }
            }


        }
    }
}
