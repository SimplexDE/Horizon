package de.simplex.horizon.command;


import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class Fly implements TabExecutor {

	private MessageSender ms = new MessageSender();

	private void toggleFlyFor(Player player) {
		boolean isAllowedToFly = player.getAllowFlight();
		if (isAllowedToFly) {
			player.setAllowFlight(false);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Fly "
				  + Color.LIGHT_RED.getColorMiniMessage() + "deactivated");
		} else {
			player.setAllowFlight(true);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Fly "
				  + Color.LIGHT_GREEN.getColorMiniMessage() + "activated");
		}
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		final String commandPermission = command.getPermission();

		Player targetPlayer;
		boolean isAllowedToFly;

		if (args.length > 1) {
			ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT_LENGTH.getMessage());
			return true;
		}

		if (args.length == 1 && Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]) != sender) {
			if (! sender.hasPermission(commandPermission + ".others")) {
				ms.sendToSender(sender, AlertMessage.NO_PERMISSION.getMessage());
				return true;
			}
			targetPlayer = Bukkit.getPlayer(args[0]);
			isAllowedToFly = targetPlayer.getAllowFlight();
			if (isAllowedToFly) {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "Fly was "
					  + Color.LIGHT_RED.getColorMiniMessage() + "deactivated" + Color.LIGHT_BLUE.getColorMiniMessage()
					  + " for " + Color.LIGHT_GREEN.getColorMiniMessage() + targetPlayer.getName());
			} else {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "Fly was "
					  + Color.LIGHT_GREEN.getColorMiniMessage() + "activated" + Color.LIGHT_BLUE.getColorMiniMessage()
					  + " for " + Color.LIGHT_GREEN.getColorMiniMessage() + targetPlayer.getName());
			}
			toggleFlyFor(targetPlayer);
			return true;
		}

		if (! (sender instanceof Player player)) {
			ms.sendToSender(sender, AlertMessage.ONLY_PLAYER.getMessage());
			return false;
		}

		toggleFlyFor(player);
		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                                  @NotNull String[] args) {
		return null;
	}
}
