package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class BalanceTop implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration c = Main.getPlugin().getConfig();

        MessageEngine ME = new MessageEngine(this.main);

        if (sender instanceof Player p) {

            ME.sendTo(Main.PREFIX + "<gold>Top 5 <grey>der Reichsten Spieler: ", (Audience) sender);
            if (c.isSet("Balance.balanceTop")) {
                int i = 0;
                for (String s : c.getConfigurationSection("Balance.balanceTop").getKeys(false)) {
                    i++;
                    ME.sendTo(Main.PREFIX + String.format(" #%s: \n%s mit %s Tokens", i, c.getString(
                            "Balance.balanceTop." + s + ".name"), c.getInt("Balance.balanceTop." + s + ".tokens")), (Audience) sender);
                }

				long l = c.isSet("Balance.lastCalculated") ? c.getLong("Balance.lastCalculated") : 0;
                ME.sendTo(Main.PREFIX + String.format("Stand: %s", longToTime(l)), (Audience) sender);
			} else {
                ME.sendTo(Main.PREFIX + "Die reichsten Spieler:innen wurden noch nicht festgestellt.", (Audience) sender);
			}
		} else {
            ME.sendTo(Main.PREFIX + Main.NOT_A_PLAYER, (Audience) sender);
		}
		return false;
	}

	public static String longToTime(final long l) {
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(l);
	}
}
