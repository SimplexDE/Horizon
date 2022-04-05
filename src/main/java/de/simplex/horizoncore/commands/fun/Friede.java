package de.simplex.horizoncore.commands.fun;

import de.simplex.horizoncore.commands.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /friede Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Friede implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage("§aFriede, Freude, Eierkuchen!");

        if (sender instanceof Player) {
            AchievementAPI.activateAchievement((Player) sender, "FRIEDE_FREUDE_EIERKUCHEN");
        }

        return false;
    }
}
