package de.simplex.horizon.commandsOLD.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commandsOLD.api.AchievementEngine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Achievements implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {
		if (sen instanceof Player p)
            AchievementEngine.sendAchieved(p);
		else
			sen.sendMessage(Main.NOT_A_PLAYER);
		return false;
	}
}
