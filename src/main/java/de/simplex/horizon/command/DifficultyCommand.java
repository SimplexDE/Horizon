package de.simplex.horizon.command;

import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.DifficultyName;
import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.util.MessageSender;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DifficultyCommand implements TabExecutor {

	private final MessageSender ms = new MessageSender();
	private final String DifficultySet = ResponseMessage.INFO.getNotification() + "Difficulty in "
		+ Color.LIGHT_GREEN.getColorMiniMessage() + "%s" + Color.LIGHT_BLUE.getColorMiniMessage() + " was set to "
		+ Color.LIGHT_GREEN.getColorMiniMessage() + "%s";
	private final String DifficultyCurrent = ResponseMessage.INFO.getNotification() + "Difficulty in "
		+ Color.LIGHT_GREEN.getColorMiniMessage() + "%s" + Color.LIGHT_BLUE.getColorMiniMessage() + " is "
		+ Color.LIGHT_GREEN.getColorMiniMessage() + "%s";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		if (args.length > 2) {
			ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT_LENGTH.getMessage());
			return true;
		}

		Player player;

		if (!(sender instanceof Player)) {
			player = null;
		} else {
			player = (Player) sender;
		}

		World world;
		DifficultyName difficultyName;

		if (args.length == 0) {
			if (player == null) {
				ms.sendToSender(sender, AlertMessage.MISSING_ARGUMENT.getMessage());
				ms.sendToSender(sender, command.getUsage());
			} else {
				world = player.getWorld();
				difficultyName = DifficultyName.fromString(world.getDifficulty().toString());
				ms.sendToSender(sender, DifficultyCurrent.formatted(StringUtils.capitalize(world.getName()),
					StringUtils.capitalize(difficultyName.getDifficultyName())));
			}
			return true;
		}

		if (args.length == 1) {
			if (player == null) {
				if (Bukkit.getWorld(args[0]) != null) {
					world = Bukkit.getWorld(args[0]);
					difficultyName = DifficultyName.fromString(world.getDifficulty().toString());
					ms.sendToSender(sender,
						DifficultyCurrent.formatted(StringUtils.capitalize(world.getName()),
						StringUtils.capitalize(difficultyName.getDifficultyName())));
					return true;
				} else if (DifficultyName.fromString(args[0]) != null) {
					ms.sendToSender(sender, AlertMessage.MISSING_ARGUMENT.getMessage());
					ms.sendToSender(sender, command.getUsage());
					return true;
				} else {
					ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT.getMessage());
					return true;
				}
			} else {
				if (Bukkit.getWorld(args[0]) != null) {
					world = Bukkit.getWorld(args[0]);
					difficultyName = DifficultyName.fromString(world.getDifficulty().toString());
					ms.sendToSender(sender,
						DifficultyCurrent.formatted(StringUtils.capitalize(world.getName()),
						StringUtils.capitalize(difficultyName.getDifficultyName())));
					return true;
				} else if (DifficultyName.fromString(args[0]) != null) {
					world = player.getWorld();
					difficultyName = DifficultyName.fromString(args[0]);
					world.setDifficulty(difficultyName.getDifficulty());
					ms.sendToSender(sender, DifficultySet.formatted(StringUtils.capitalize(world.getName()),
						StringUtils.capitalize(difficultyName.getDifficultyName())));
					return true;
				} else {
					ms.sendToSender(sender, AlertMessage.INVALID_ARGUMENT.getMessage());
					return true;
				}
			}
		}

		if (DifficultyName.fromString(args[0]) == null) {
			ms.sendToSender(sender, AlertMessage.MISSING_ARGUMENT.getMessage());
			ms.sendToSender(sender, command.getUsage());
			return true;
		}
		difficultyName = DifficultyName.fromString(args[0]);
		if (Bukkit.getWorld(args[1]) == null) {
			ms.sendToSender(sender, AlertMessage.MISSING_ARGUMENT.getMessage());
			ms.sendToSender(sender, command.getUsage());
			return true;
		}
		world = Bukkit.getWorld(args[1]);
		world.setDifficulty(difficultyName.getDifficulty());
		ms.sendToSender(sender, DifficultySet.formatted(StringUtils.capitalize(world.getName()),
			StringUtils.capitalize(difficultyName.getDifficultyName())));
		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label
		, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> difficulties = new ArrayList<>();
			for (Difficulty difficulty : Difficulty.values()) {
				difficulties.add(difficulty.toString().toLowerCase());
			}
			return difficulties;
		} else if (args.length == 2) {
			List<String> worldsList = new ArrayList<>();
			World[] worlds = Bukkit.getServer().getWorlds().toArray(new World[0]);
			for (int i = 0; i < worlds.length; i++) {
				worldsList.add(worlds[i].getName());
			}
			return worldsList;
		} else {
			return null;
		}
	}
}
