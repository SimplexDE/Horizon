package de.simplex.horizon.commandsOLD.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.systemsOLD.Scoreboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpdateVisuals implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {
		if (sen instanceof Player p) {

			if (p.getScoreboard().getTeam("pName") == null) {
				Scoreboard.defaultSb(p);
				p.sendMessage(Main.PREFIX + "Scoreboard gesetzt!");
				return true;
			}
			Scoreboard.updateAll(p);
			p.sendMessage(Main.PREFIX + "Erfolgreich!");
		} else {
			sen.sendMessage(Main.NOT_A_PLAYER);
		}
		return false;
	}
}
