package de.simplex.horizon.command;

import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.util.MessageSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Broadcast implements CommandExecutor {

	private final MessageSender ms = new MessageSender();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		String msg = "";

		for (String s : args) {
			msg += s + " ";
		}

		Component emsg = MiniMessage.miniMessage().deserialize("<newline><rainbow>ʙʀᴏᴀᴅᴄᴀsᴛ</rainbow> "
			  + Color.DARK_GRAY.getColorMiniMessage() + "┃ "
			  + Color.AQUA.getColorMiniMessage() + msg + "<newline>");
		if (msg.length() < 1) {
			ms.sendToSender(sender, AlertMessage.MISSING_ARGUMENT.getMessage());
			return true;
		}
		Audience ChatAudience = MessageSender.aapi.getAllSender();
		ChatAudience.sendMessage(emsg);
		return true;
	}
}
