package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Whisper Befehl
 *
 * @author Simplex
 * @since 1.0-Betas
 */
public class Whisper implements CommandExecutor {

	Main main = Main.getPlugin();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		MessageEngine ME = new MessageEngine(main);

		String msg = "";

		if (!(sender.hasPermission("core.msg") || sender.isOp())) {
			ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) sender);
			return false;
		}

		if (args.length <= 1) {
			ME.sendTo(Main.PREFIX + "<red>Nutzung: /whisper <Spieler:in> <Nachricht>", (Audience) sender);
			return false;
		}

		if (!(sender instanceof Player P1)) {
			ME.sendTo(Main.PREFIX + Main.NOT_A_PLAYER, (Audience) sender);
			return false;
		}

		if (Bukkit.getPlayer(args[0]) == null) {
			ME.sendTo(Main.PREFIX + "Bitte gib eine/n Spieler:in an der/die Online ist.", (Audience) sender);
			return false;
		}
		Player P2 = Bukkit.getPlayer(args[0]);

		if (P1 == P2) {
			ME.sendTo(Main.PREFIX + "Man kann sich selbst keine Nachricht senden.", (Audience) sender);
			//return false;
		}

		String[] arg = args.toString().split(args[0]);

		for (String s : arg) {
			msg += s + " ";
		}

		ME.sendTo("<dark_gray>» <gray>Du <green>➡ <light_purple>" + P2.getName() + " <dark_gray>┃ <italic><gray>" + msg, (Audience) P1);
		ME.sendTo("<dark_gray>» <light_purple>" + P1.getName() + " <green>➡ <gray>Dir <dark_gray>┃ <italic><gray>" + msg, (Audience) P2);

		return false;
	}
}
