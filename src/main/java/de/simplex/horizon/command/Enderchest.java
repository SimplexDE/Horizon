package de.simplex.horizon.commands;

import de.simplex.horizon.enums.Messages;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Enderchest implements TabExecutor {

    MessageSender ms = new MessageSender();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player p) {
                p.openInventory(p.getEnderChest());
                return true;
            } else {
                ms.sendToSender(sender, Messages.ONLY_PLAYER.getMsg());
                return true;
            }
        } else if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) != null) {
                if (sender instanceof Player p) {
                    if (sender.hasPermission("server.enderchest.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        p.openInventory(target.getEnderChest());
                        return true;
                    } else {
                        ms.sendToSender(sender, Messages.NO_PERMISSION.getMsg());
                        return true;
                    }
                } else {
                    ms.sendToSender(sender, Messages.ONLY_PLAYER.getMsg());
                    return true;
                }
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
