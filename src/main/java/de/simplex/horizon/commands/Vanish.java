package de.simplex.horizon.commands;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.NotificationPrefixes;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.method.PlayerConfig;
import de.simplex.horizon.util.MessageSender;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class Vanish implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        MessageSender ms = new MessageSender();

        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                ms.sendToSender(sender, NotificationPrefixes.WARN.getNotification() + "Nur für Spieler ausführbar");
                return true;
            }

            PlayerConfig pc = new PlayerConfig(p);

            boolean vanished = pc.isSet("staff.vanish") && pc.getBoolean("staff.vanish");

            if (!vanished) {
                for (Player ap : Bukkit.getOnlinePlayers()) {
                    if (ap.canSee(p)) {
                        if (ap.hasPermission("server.vanish.see")) {
                            break;
                        }
                        ap.hidePlayer(Horizon.getHorizon(), p);
                    }
                }
                Objects.requireNonNull(lpapi.getUserManager().getUser(p.getUniqueId())).data().add(Node.builder("suffix.100." + Color.LIGHT_PURPLE.getColorMiniMessage() + "[V]").build());
                p.setSilent(true);
                pc.set("staff.vanish", true);
                ms.sendToPlayer(p, NotificationPrefixes.INFO.getNotification() + "Du bist nun " + Color.AQUA.getColorMiniMessage() + "versteckt" + Color.LIGHT_GRAY.getColorMiniMessage() + ".");
            } else {
                for (Player ap : Bukkit.getOnlinePlayers()) {
                    if (!ap.canSee(p)) {
                        ap.showPlayer(Horizon.getHorizon(), p);
                    }
                }
                Objects.requireNonNull(lpapi.getUserManager().getUser(p.getUniqueId())).data().remove(Node.builder("suffix.100." + Color.LIGHT_PURPLE.getColorMiniMessage() + "[V]").build());
                p.setSilent(false);
                pc.set("staff.vanish", false);
                ms.sendToPlayer(p, NotificationPrefixes.INFO.getNotification() + "Du bist nun nicht mehr " + Color.AQUA.getColorMiniMessage() + "versteckt" + Color.LIGHT_GRAY.getColorMiniMessage() + ".");
            }
            pc.save();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
