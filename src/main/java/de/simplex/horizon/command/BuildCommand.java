package de.simplex.horizon.command;

import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.method.PlayerConfig;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCommand implements CommandExecutor {

	private final MessageSender ms = new MessageSender();

	private void toggleBuildModeFor(Player player) {

		PlayerConfig targetPlayerConfig = PlayerConfig.loadConfig(player);

		if (isBuilding(player)) {
			targetPlayerConfig.set("staff.build", false);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Build-Mode"
				+ Color.LIGHT_RED.getColorMiniMessage() + " deactivated");
		} else {
			targetPlayerConfig.set("staff.build", true);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Build-Mode"
				+ Color.LIGHT_GREEN.getColorMiniMessage() + " activated");
		}
		targetPlayerConfig.save();
	}

	private boolean isBuilding(Player player) {
		PlayerConfig targetConfig = PlayerConfig.loadConfig(player);
		return targetConfig.isSet("staff.build") && targetConfig.getBoolean("staff.build");
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		Player targetPlayer;
		String commandPermission;

		commandPermission = command.getPermission();

		if (args.length > 1) {
			ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT_LENGTH.getMessage());
			return true;
		}

		if (args.length == 1 && Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]) != sender) {
			if (!sender.hasPermission(commandPermission + ".others")) {
				ms.sendToSender(sender, AlertMessage.NO_PERMISSION.getMessage());
				return true;
			}
			targetPlayer = Bukkit.getPlayer(args[0]);

			if (isBuilding(targetPlayer)) {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "Build-Mode"
					+ Color.LIGHT_RED.getColorMiniMessage() + " deactivated" + Color.LIGHT_BLUE.getColorMiniMessage() + " for "
					+ targetPlayer.getName());
			} else {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "Build-Mode"
					+ Color.LIGHT_GREEN.getColorMiniMessage() + " activated" + Color.LIGHT_BLUE.getColorMiniMessage() + " for "
					+ targetPlayer.getName());
			}

			toggleBuildModeFor(targetPlayer);
			return true;
		}

		if (!(sender instanceof Player player)) {
			ms.sendToSender(sender, AlertMessage.ONLY_PLAYER.getMessage());
			return true;
		}

		if (!player.hasPermission(commandPermission)) {
			ms.sendToSender(sender, AlertMessage.NO_PERMISSION.getMessage());
			return true;
		}
		toggleBuildModeFor(player);
		return true;
	}
}
