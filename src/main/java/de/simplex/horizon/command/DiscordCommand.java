package de.simplex.horizon.command;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DiscordCommand implements CommandExecutor {

	private MessageSender ms = new MessageSender();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		ms.sendToSender(sender, ResponseMessage.INFO.getNotification()
			+ Color.GREEN.getColorMiniMessage() + "<click:open_url:https://discord.com/invite/Wry5qqX3GY>Click " +
			"here</click>"
			+ Color.LIGHT_BLUE.getColorMiniMessage() + " to join our Discord!");
		return true;
	}
}
