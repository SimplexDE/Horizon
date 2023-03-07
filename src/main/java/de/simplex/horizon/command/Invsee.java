package de.simplex.horizon.command;

import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Invsee implements TabExecutor {

	final MessageSender ms = new MessageSender();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		Player targetPlayer;
		Inventory targetInventory;

		if (args.length < 1) {
			ms.sendToSender(sender, AlertMessage.MISSING_ARGUMENT.getMessage());
			return true;
		}

		if (args.length > 1) {
			ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT_LENGTH.getMessage());
			return true;
		}

		if (!(sender instanceof Player player)) {
			ms.sendToSender(sender, AlertMessage.ONLY_PLAYER.getMessage());
			return true;
		}

		if (Bukkit.getPlayer(args[0]) == sender) {
			ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT.getMessage());
			return true;
		}

		if (Bukkit.getPlayer(args[0]) != null) {
			targetPlayer = Bukkit.getPlayer(args[0]);
			targetInventory = targetPlayer.getInventory();
			player.openInventory(targetInventory);
			ms.sendToPlayer(player,
				ResponseMessage.INFO.getNotification() + "Inventory of " + targetPlayer.getName() + "opened");
			return true;
		}

		ms.sendToPlayer(player, AlertMessage.PLAYER_NOT_FOUND.getMessage());
		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label
		, @NotNull String[] args) {
		return null;
	}
}
