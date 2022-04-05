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
 * @since 1.0-Beta
 */
public class Whisper implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Message = "";

        if (!(sender.hasPermission("core.msg") || sender.isOp())) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        if (args.length <= 1) {
            sender.sendMessage(Main.PREFIX + "§cNutzung: /whisper <Spieler> <Nachricht>");
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Du kannst diesen Befehl nur als Spieler ausführen!");
            return false;
        }
        Player P1 = (Player) sender;

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Main.PREFIX + "Bitte gebe einen Spieler an der Online ist.");
            return false;
        }
        Player P2 = Bukkit.getPlayer(args[0]);

        if (P1 == P2) {
            sender.sendMessage(Main.PREFIX + "Du kannst dir selber keine Nachricht senden.");
            return false;
        }

        for (int i = 1; i < args.length; i++) {
            Message += (args[i] + " ");
        }

        P1.sendMessage("§8» §bDu §a➡ §b" + P2.getName() + " §8┃ §7" + Message);
        P2.sendMessage("§8» §b" + P1.getName() + " §a➡ §bDir §8┃ §7" + Message);

        return false;
    }
}
