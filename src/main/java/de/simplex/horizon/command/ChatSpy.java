package de.simplex.horizon.command;

import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.method.PlayerConfig;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatSpy implements TabExecutor {

	private MessageSender ms = new MessageSender();

	private void toggleChatSpyFor(Player player) {

		PlayerConfig targetPlayerConfig = PlayerConfig.loadConfig(player);

		if (isChatSpying(player)) {
			targetPlayerConfig.set("staff.chatspy", false);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "ChatSpy"
				+ Color.LIGHT_RED.getColorMiniMessage() + " deactivated");
		} else {
			targetPlayerConfig.set("staff.chatspy", true);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "ChatSpy"
				+ Color.LIGHT_GREEN.getColorMiniMessage() + " activated");
		}
		targetPlayerConfig.save();
	}

	private boolean isChatSpying(Player player) {
		PlayerConfig targetConfig = PlayerConfig.loadConfig(player);
		return targetConfig.isSet("staff.chatspy") && targetConfig.getBoolean("staff.chatspy");
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String @NotNull [] args) {

		final String commandPermission = command.getPermission();

		Player targetPlayer;

		// Check for correct argument length
		if (args.length > 1) {
			ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT_LENGTH.getMessage());
			return true;
		}

		// Check for a Player argument
		if (args.length == 1 && Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]) != sender) {

			// Check if the Sender has the Permission to toggle ChatSpy for others
			if (!sender.hasPermission(commandPermission + ".others")) {
				ms.sendToSender(sender, AlertMessage.NO_PERMISSION.getMessage());
				return true;
			}

			targetPlayer = Bukkit.getPlayer(args[0]);

			if (isChatSpying(targetPlayer)) {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "ChatSpy"
					+ Color.LIGHT_RED.getColorMiniMessage() + " deactivated" + Color.LIGHT_BLUE.getColorMiniMessage() + " for "
					+ targetPlayer.getName());
			} else {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "ChatSpy"
					+ Color.LIGHT_GREEN.getColorMiniMessage() + " activated" + Color.LIGHT_BLUE.getColorMiniMessage() + " for "
					+ targetPlayer.getName());
			}

			toggleChatSpyFor(targetPlayer);
			return true;
		}

		if (!(sender instanceof Player player)) {
			ms.sendToSender(sender, AlertMessage.ONLY_PLAYER.getMessage());
			return true;
		}

		toggleChatSpyFor(player);
		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label
		, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> playerNames = new ArrayList<>();
			Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
			Bukkit.getServer().getOnlinePlayers().toArray(players);
			for (int i = 0; i < players.length; i++) {
				playerNames.add(players[i].getName());
			}
			return playerNames;
		} else {
			return null;
		}
	}
}
