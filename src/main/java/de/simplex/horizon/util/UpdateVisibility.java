package de.simplex.horizon.util;

import de.simplex.horizon.method.PlayerConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class UpdateVisibility {

    public void updateVisibility(Plugin plugin) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateVisibility(plugin, player);
        }
    }

    public void updateVisibility(Plugin plugin, Player player) {
        for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
            if (player == targetPlayer) {
                continue;
            }
            PlayerConfig pC = new PlayerConfig(targetPlayer);
            if (pC.isSet("staff.vanish") && pC.getBoolean("staff.vanish") && !player.hasPermission("server.vanish.see")) {
                targetPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cHidden from other " +
                      "players"));
                player.hidePlayer(plugin, targetPlayer);
                continue;
            }
            player.showPlayer(plugin, targetPlayer);
        }
    }
}
