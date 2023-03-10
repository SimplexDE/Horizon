package de.simplex.horizon.command;

import de.simplex.horizon.enums.AlertMessage;
import de.simplex.horizon.enums.Color;
import de.simplex.horizon.util.MessageSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.model.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.simplex.horizon.command.api.LuckPermsAPI.lpapi;

public class TeamChat implements CommandExecutor {

	private final MessageSender ms = new MessageSender();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                         @NotNull String[] args) {
		Player p = (Player) sender;

		User u = lpapi.getUserManager().getUser(p.getUniqueId());
		String prefix = u.getCachedData().getMetaData().getPrefix();

		String msg = "";

		for (String s : args) {
			msg += s + " ";
		}

		Component emsg =
			  MiniMessage.miniMessage().deserialize(Color.AQUA.getColorMiniMessage() + "Teamchat " + Color.DARK_GRAY.getColorMiniMessage() + "┃ " + prefix + sender.getName()
					+ " " + Color.DARK_GRAY.getColorMiniMessage() + "» " + Color.LIGHT_GRAY.getColorMiniMessage() + msg
					.replace("<", "⏴")
					.replace(">", "⏵"));
		if (msg.length() < 1) {
			ms.sendToSender(sender, AlertMessage.MISSING_ARGUMENT.getMessage());
			return true;
		}

		Audience ChatAudience = MessageSender.aapi.getPermissionSender("server.chat.team");
		ChatAudience.sendMessage(emsg);

		return true;
	}
}
