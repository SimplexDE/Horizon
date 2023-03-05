package de.simplex.horizon.commands;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.Messages;
import de.simplex.horizon.enums.Notification;
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

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class Vanish implements TabExecutor {

    private MessageSender ms = new MessageSender();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player p) {
                PlayerConfig pC = new PlayerConfig(p);
                Boolean vanished = pC.getBoolean("staff.vanish");

                if (vanished) {
                    pC.setFalse("staff.vanish");
                    lpapi.getUserManager().getUser(p.getUniqueId()).data().remove(Node.builder("suffix.100." + Color.LIGHT_PURPLE.getColorMiniMessage() + "[V]").build());
                    ms.sendToPlayer(p, Notification.INFO.getNotification() + Color.LIGHT_RED.getColorMiniMessage() + "Disabled vanish");
                } else {
                    pC.setTrue("staff.vanish");
                    lpapi.getUserManager().getUser(p.getUniqueId()).data().add(Node.builder("suffix.100." + Color.LIGHT_PURPLE.getColorMiniMessage() + "[V]").build());
                    ms.sendToPlayer(p, Notification.INFO.getNotification() + Color.LIGHT_GREEN.getColorMiniMessage() + "Enabled vanish");
                }
                pC.save();
                return true;
            } else {
                ms.sendToSender(sender, Messages.ONLY_PLAYER.getMsg());
                return true;
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("server.vanish.others")) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player target = Bukkit.getPlayer(args[0]);
                    PlayerConfig pC = new PlayerConfig(target);
                    Boolean vanished = pC.getBoolean("staff.vanish");

                    if (vanished) {
                        pC.setFalse("staff.vanish");
                        lpapi.getUserManager().getUser(target.getUniqueId()).data().remove(Node.builder("suffix.100." + Color.LIGHT_PURPLE.getColorMiniMessage() + "[V]").build());
                        ms.sendToPlayer(target, Notification.INFO.getNotification() + Color.LIGHT_RED.getColorMiniMessage() + "Disabled vanish");
                        ms.sendToSender(sender, Notification.SYSTEM.getNotification() + Color.LIGHT_RED.getColorMiniMessage() + "Disabled vanish for " + target.getName());
                    } else {
                        pC.setTrue("staff.vanish");
                        lpapi.getUserManager().getUser(target.getUniqueId()).data().add(Node.builder("suffix.100." + Color.LIGHT_PURPLE.getColorMiniMessage() + "[V]").build());
                        ms.sendToPlayer(target, Notification.INFO.getNotification() + Color.LIGHT_GREEN.getColorMiniMessage() + "Enabled vanish");
                        ms.sendToSender(sender, Notification.SYSTEM.getNotification() + Color.LIGHT_GREEN.getColorMiniMessage() + "Enabled vanish for " + target.getName());
                    }
                    pC.save();
                    return true;
                } else {
                    ms.sendToSender(sender, Messages.PLAYER_NOT_ONLINE.getMsg());
                    return true;
                }
            } else {
                ms.sendToSender(sender, Messages.NO_PERMISSION.getMsg());
                return false;
            }
        } else {
            ms.sendToSender(sender, Messages.INVALID_ARGUMENT_LENGTH.getMsg());
            return false;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
