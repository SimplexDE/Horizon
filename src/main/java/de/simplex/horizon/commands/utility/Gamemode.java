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
 * Der Gamemode Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Gamemode implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (sender instanceof Player p) {

            if (!sender.hasPermission("core.gamemode")) {
                ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) p);
                return false;
            }

            org.bukkit.GameMode gm;
            if (args.length == 1 || args.length == 2) {
                String gms = null;
                switch (args[0]) {
                    case "1" -> gms = "CREATIVE";
                    case "2" -> gms = "ADVENTURE";
                    case "3" -> gms = "SPECTATOR";
                    case "0" -> gms = "SURVIVAL";
                }
                gm = org.bukkit.GameMode.valueOf(gms);
            } else {
                ME.sendTo(Main.PREFIX + Main.ARGS_TOO_LONG, (Audience) p);
                return true;
            }

            String s = Main.PREFIX + String.format("Dein Spielmodus ist nun <green>%s<dark_grey>.", gm.toString().toLowerCase());
            if (args.length == 1) {
                ME.sendTo(s, (Audience) p);
                p.setGameMode(gm);

            } else {
                if (!p.hasPermission("core.gamemode.others")) {
                    ME.sendTo(Main.PREFIX + Main.NO_PERMISSION, (Audience) p);
                    return false;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    ME.sendTo(Main.PREFIX + "Diese/r Spieler:in ist nicht online!", (Audience) p);
                    return true;
                }

                target.setGameMode(gm);
                ME.sendTo(s, (Audience) target);
                ME.sendTo(Main.PREFIX + String.format("Der Spielmodus von <yellow>%s<grey> ist nun <green>%s<dark_grey>.", target.getName(), gm.toString().toLowerCase()), (Audience) p);
            }
        } else {
            ME.sendTo(Main.PREFIX + Main.NOT_A_PLAYER, (Audience) sender);
        }
        return false;
    }

}