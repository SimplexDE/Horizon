package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Difficulty Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Difficulty implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler zugänglich.");
            return false;
        }

        if (!(sender.hasPermission("core.difficulty"))) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        Player p = (Player) sender;
        World w = p.getLocation().getWorld();


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("peaceful")) {
                w.setDifficulty(org.bukkit.Difficulty.PEACEFUL);
                sender.sendMessage(Main.PREFIX + "Der Schwierigkeitsgrad ist nun §aFriedlich§7.");

            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("easy")) {
                w.setDifficulty(org.bukkit.Difficulty.EASY);
                sender.sendMessage(Main.PREFIX + "Der Schwierigkeitsgrad ist nun §aEinfach§7.");

            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("normal")) {
                w.setDifficulty(org.bukkit.Difficulty.NORMAL);
                sender.sendMessage(Main.PREFIX + "Der Schwierigkeitsgrad ist nun §aNormal§7.");

            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("hard")) {
                w.setDifficulty(org.bukkit.Difficulty.HARD);
                sender.sendMessage(Main.PREFIX + "Der Schwierigkeitsgrad ist nun §aHart§7.");
            } else {
                sender.sendMessage(Main.PREFIX + "Es gibt diesen Schwierigkeitsgrad nicht!");
            }
        }

        return false;
    }
}
