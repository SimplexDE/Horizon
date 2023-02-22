package de.simplex.horizon.commandsOLD.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commandsOLD.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Broadcast implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (!sender.hasPermission("core.alert")) {
            ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) sender);
            return false;
        }

        if (args.length <= 0) {
            ME.sendTo(Main.PREFIX + "<red>Nutzung: /alert <Nachricht>", (Audience) sender);
            return false;
        }

		String msg = "";

		for (String s : args) {
			msg += s + " ";
		}

        msg = ChatColor.translateAlternateColorCodes('&', msg);
        ME.broadcast("<newline><dark_grey>» <rainbow>ʜᴏʀɪᴢᴏɴ</rainbow> <dark_grey>┃ <green>" + msg + "<newline>");

		return false;
	}
}
