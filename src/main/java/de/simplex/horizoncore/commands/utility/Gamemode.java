package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Der Gamemode Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Gamemode implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean gm_other = true;

        // Testet, ob der Ausführende die benötigten Berechtigungen hat.
        if (!(sender.hasPermission("core.gamemode"))) {
            sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
            return false;
        }

        if (!(sender.hasPermission("core.gamemode.other"))) {
            if (args.length == 2) {
                sender.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
                return false;
            }
        }

        if (args.length == 1) {
            Player operator = (Player) sender;
            Player target = (Player) sender;
            String mode = args[0];
            setGamemode(operator, target, mode);
        } else if (args.length == 2) {
            Player operator = (Player) sender;
            if (Bukkit.getPlayer(args[1]) == null) {
                operator.sendMessage(Main.PREFIX + "§7Bitte gebe einen Spieler an, der aktuell Online ist.");
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            String mode = args[0];
            setGamemode(operator, target, mode);
        }

        return false;
    }

    /**
     * Gamemode setzen
     *
     * @param operator  ausführender Nutzer
     * @param target    Ziel
     * @param givenMode Angegebener Modus
     */
    public void setGamemode(Player operator, Player target, String givenMode) {

        String modeName = null;
        GameMode mode = null;
        switch (givenMode) {
            case "0":
                modeName = "§aÜberleben";
                mode = GameMode.SURVIVAL;
                break;
            case "1":
                modeName = "§aKreativ";
                mode = GameMode.CREATIVE;
                break;
            case "2":
                modeName = "§aAbenteuer";
                mode = GameMode.ADVENTURE;
                break;
            case "3":
                modeName = "§aZuschauer";
                mode = GameMode.SPECTATOR;
                break;
            default:
                operator.sendMessage(Main.PREFIX + "Dieser Spielmodus existiert nicht.");
                break;
        }

        if (mode != null) {
            if (operator == target) {
                target.setGameMode(mode);
                target.sendMessage(Main.PREFIX + "Dein Spielmodus ist nun " + modeName + "§7.");
            } else {
                target.setGameMode(mode);
                target.sendMessage(Main.PREFIX + "Dein Spielmodus ist nun " + modeName + "§7.");
                operator.sendMessage(Main.PREFIX + "Der Spielmodus von §e" + target.getName() + "§7 ist nun " + modeName + "§7.");
            }
        }
    }

}

