package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class sethome implements CommandExecutor {

    private static final File file = new File("plugins/Horizon/homeData.yml");
    private static final YamlConfiguration hD = YamlConfiguration.loadConfiguration(file);

    private void saveConfig() {
        try {
            hD.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean getBoolean(String path) {
        boolean out = false;
        try {
            hD.load(file);
            out = hD.getBoolean(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    private String getString(String path) {
        String out = "";
        try {
            hD.load(file);
            out = hD.getString(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }

    private int getInt(String path) {
        int out = 0;
        try {
            hD.load(file);
            out = hD.getInt(path);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return out;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für Spieler zugänglich");
            return false;
        }
        Player p = (Player) sender;

        /* A player can only set 3 homes maximum. */
        if (getInt(p.getUniqueId() + ".homes") <= 2) {
            p.sendMessage(Main.PREFIX + "MAX. Grenze an Homes erreicht."); // Todo beautify this message
            return false;
        }

        int currentHomes = getInt(p.getUniqueId() + ".homes");

        hD.set(p.getUniqueId() + ".homes", currentHomes += 1);

        Location loc = p.getLocation();

        p.sendMessage(loc.toString());


        return false;
    }
}
