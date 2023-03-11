package de.simplex.horizon.command;


import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.ResponseMessage;
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

import static de.simplex.horizon.command.api.LuckPermsAPI.lpapi;


public class GodMode implements TabExecutor {

	private MessageSender ms = new MessageSender();

	private void toggleGodModeFor(Player player) {
		boolean isInGodMode = player.isInvulnerable();
		if (isInGodMode) {
			lpapi.getUserManager().modifyUser(player.getUniqueId(), user -> {
				user.data().remove(Node.builder("suffix.2." + Color.LIGHT_YELLOW.getColorMiniMessage() + "[G]").build());
			});
			player.setInvulnerable(false);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Godmode "
				  + Color.LIGHT_RED.getColorMiniMessage() + "deactivated");
		} else {
			lpapi.getUserManager().modifyUser(player.getUniqueId(), user -> {
				user.data().add(Node.builder("suffix.2." + Color.LIGHT_YELLOW.getColorMiniMessage() + "[G]").build());
			});
			player.setInvulnerable(true);
			ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Godmode "
				  + Color.LIGHT_GREEN.getColorMiniMessage() + "activated");
		}
		lpapi.runUpdateTask();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		final String commandPermission = command.getPermission();

		Player targetPlayer;
		boolean isInGodMode;

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
			isInGodMode = targetPlayer.isInvulnerable();
			if (isInGodMode) {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "Godmode was "
					  + Color.LIGHT_RED.getColorMiniMessage() + "disabled" + Color.LIGHT_BLUE.getColorMiniMessage()
					  + " for " + Color.LIGHT_GREEN.getColorMiniMessage() + targetPlayer.getName());
			} else {
				ms.sendToSender(sender, ResponseMessage.INFO.getNotification() + "Godmode was "
					  + Color.LIGHT_GREEN.getColorMiniMessage() + "enabled" + Color.LIGHT_BLUE.getColorMiniMessage()
					  + " for " + Color.LIGHT_GREEN.getColorMiniMessage() + targetPlayer.getName());
			}
			toggleGodModeFor(targetPlayer);
			return true;
		}

		if (! (sender instanceof Player player)) {
			ms.sendToSender(sender, AlertMessage.ONLY_PLAYER.getMessage());
			return false;
		}
		toggleGodModeFor(player);
		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                                  @NotNull String[] args) {
		return null;
	}
}
