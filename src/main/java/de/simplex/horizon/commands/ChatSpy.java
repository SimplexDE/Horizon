package de.simplex.horizon.commands;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.Messages;
import de.simplex.horizon.enums.Notification;
import de.simplex.horizon.method.PlayerConfig;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChatSpy implements TabExecutor {

    private MessageSender ms = new MessageSender();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player p) {
                PlayerConfig pC = new PlayerConfig(p);
                Boolean chatspying = pC.getBoolean("staff.chatspy");

                if (chatspying) {
                    pC.setFalse("staff.chatspy");
                    ms.sendToPlayer(p, Notification.INFO.getNotification() + Color.LIGHT_RED.getColorMiniMessage() + "Disabled chatspy");
                } else {
                    pC.setTrue("staff.chatspy");
                    ms.sendToPlayer(p, Notification.INFO.getNotification() + Color.LIGHT_GREEN.getColorMiniMessage() + "Enabled chatspy");
                }
                pC.save();
                return true;
            } else {
                ms.sendToSender(sender, Messages.ONLY_PLAYER.getMsg());
                return true;
            }
        } else if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[0]);
                PlayerConfig pC = new PlayerConfig(target);
                Boolean chatspying = pC.getBoolean("staff.chatspy");

                if (chatspying) {
                    pC.setFalse("staff.chatspy");
                    ms.sendToPlayer(target, Notification.INFO.getNotification() + Color.LIGHT_RED.getColorMiniMessage() + "Disabled chatspy");
                    ms.sendToSender(sender, Notification.SYSTEM.getNotification() + Color.LIGHT_RED.getColorMiniMessage() + "Disabled chatspy for " + target.getName());
                } else {
                    pC.setTrue("staff.chatspy");
                    ms.sendToPlayer(target, Notification.INFO.getNotification() + Color.LIGHT_GREEN.getColorMiniMessage() + "Enabled chatspy");
                    ms.sendToSender(sender, Notification.SYSTEM.getNotification() + Color.LIGHT_GREEN.getColorMiniMessage() + "Enabled chatspy for " + target.getName());
                }
                pC.save();
                return true;
            } else {
                ms.sendToSender(sender, Messages.PLAYER_NOT_ONLINE.getMsg());
                return true;
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
