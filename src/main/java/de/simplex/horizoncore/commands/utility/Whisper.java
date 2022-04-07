package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
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

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String msg = null;

		if (!(sender.hasPermission("core.msg") || sender.isOp())) {
			sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
			return false;
		}

		if (args.length <= 1) {
			sender.sendMessage(Main.PREFIX + "§cNutzung: /whisper <Spieler:in> <Nachricht>");
			return false;
		}

		if (!(sender instanceof Player P1)) {
			sender.sendMessage(Main.PREFIX + "Du kannst diesen Befehl nur als Spieler:in ausführen!");
			return false;
		}

		if (Bukkit.getPlayer(args[0]) == null) {
			sender.sendMessage(Main.PREFIX + "Bitte gib eine/n Spieler:in an der/die Online ist.");
			return false;
		}
		Player P2 = Bukkit.getPlayer(args[0]);

		if (P1 == P2) {
			sender.sendMessage(Main.PREFIX + "Man kann sich selbst keine Nachricht senden.");
			return false;
		}

		for (String s : args) {
			msg += s + " ";
		}

		P1.sendMessage("§8» §bDu §a➡ §b" + P2.getName() + " §8┃ §7" + msg);
		P2.sendMessage("§8» §b" + P1.getName() + " §a➡ §bDir §8┃ §7" + msg);

		return false;
	}
}
