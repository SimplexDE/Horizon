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

public class Enderchest implements TabExecutor {

	MessageSender ms = new MessageSender();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		Player targetPlayer;
		Inventory targetEnderChest;

		if (args.length > 1) {
			ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT_LENGTH.getMessage());
			return true;
		}

		if (!(sender instanceof Player player)) {
			ms.sendToSender(sender, AlertMessage.ONLY_PLAYER.getMessage());
			return true;
		}

		if (args.length == 1 && Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]) != sender) {
			targetPlayer = Bukkit.getPlayer(args[0]);
			targetEnderChest = targetPlayer.getEnderChest();
			player.openInventory(targetEnderChest);
			ms.sendToPlayer(player,
				ResponseMessage.INFO.getNotification() + "Enderchest of " + targetPlayer.getName() + "opened");
			return true;
		}

		targetEnderChest = player.getEnderChest();
		player.openInventory(targetEnderChest);
		ms.sendToPlayer(player, ResponseMessage.INFO.getNotification() + "Enderchest opened");
		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label
		, @NotNull String[] args) {
		return null;
	}
}
