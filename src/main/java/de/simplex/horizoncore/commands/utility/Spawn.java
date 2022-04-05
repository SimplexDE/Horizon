package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler zugänglich.");
            return false;
        }

        Player p = (Player) sender;

        if (Bukkit.getServer().getWorld("LOBBY") == null) {
            sender.sendMessage(Main.PREFIX + "Die Lobby Welt konnte nicht gefunden werden. Bitte wende dich an einen Administrator.");
            return false;
        }


        World world = Bukkit.getServer().getWorld("LOBBY");
        Location loc = new Location(world, 780, 4, 105);
        p.teleport(loc);
        p.sendMessage(Main.PREFIX + "Du wurdest zur Lobby teleportiert!");

        return true;
    }
}
