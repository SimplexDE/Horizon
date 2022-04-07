package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class BalanceTop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration c = Main.getPlugin().getConfig();

		if (sender instanceof Player p) {

			p.sendMessage("Top 5 Spieler:innen: ");
			if (c.isSet("Balance.balanceTop")) {
				int i = 0;
				for (String s : c.getConfigurationSection("Balance.balanceTop").getKeys(false)) {
					i++;
					p.sendMessage(Main.PREFIX + String.format(" #%s: \n%s mit %s Tokens", i, c.getString(
							"Balance.balanceTop." + s + ".name"), c.getInt("Balance.balanceTop." + s + ".tokens")));
				}

				long l = c.isSet("Balance.lastCalculated") ? c.getLong("Balance.lastCalculated") : 0;
				p.sendMessage(Main.PREFIX + String.format("Stand: %s", longToTime(l)));
			} else {
				p.sendMessage(Main.PREFIX + "Die reichsten Spieler:innen wurden noch nicht festgestellt.");
			}
		} else {
			sender.sendMessage(Main.PREFIX + Main.NOT_A_PLAYER);
		}
		return false;
	}

	public static String longToTime(final long l) {
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(l);
	}
}
