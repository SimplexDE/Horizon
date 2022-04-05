package de.simplex.horizoncore;

import de.simplex.horizoncore.commands.fun.Friede;
import de.simplex.horizoncore.commands.moderation.*;
import de.simplex.horizoncore.commands.utility.*;
import de.simplex.horizoncore.systems.Chat;
import de.simplex.horizoncore.systems.Connection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Die Main-Klasse des Horizon-Plugins.
 *
 * @author Simplex
 * @version 1.0-Beta
 */
public final class Main extends JavaPlugin {

    public static Main INSTANCE;

    /**
     * Der Standard Prefix
     */
    public static String PREFIX;

    /**
     * Die Standard NP Nachricht
     */
    public static String NO_PERMISSION;

    /**
     * Die Standard CNF Nachricht
     */
    public static String COMMAND_NOT_FOUND;

    /**
     * Aktivierungslogik
     */
    @Override
    public void onEnable() {

        INSTANCE = this;

        PREFIX = "§8» §bSystem §8┃ §7";
        NO_PERMISSION = "Du hast §4keinen Zugriff §7auf diesen Befehl.";
        COMMAND_NOT_FOUND = "Dieser Befehl existiert nicht.";

        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("whisper").setExecutor(new Whisper());
        getCommand("maintenance").setExecutor(new Maintenance());
        getCommand("gamemode").setExecutor(new Gamemode());
        getCommand("globalmute").setExecutor(new ChatGlobalMute());
        getCommand("teamchat").setExecutor(new Teamchat());
        getCommand("flight").setExecutor(new Flight());
        getCommand("difficulty").setExecutor(new Difficulty());
        getCommand("achievements").setExecutor(new Achievements());
        getCommand("spawn").setExecutor(new Spawn());

        getCommand("eban").setExecutor(new Ban());
        getCommand("eunban").setExecutor(new Unban());
        getCommand("ekick").setExecutor(new Kick());

        getCommand("friede").setExecutor(new Friede());

        final PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new Connection(), this);
        pM.registerEvents(new Chat(), this);

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);


        Bukkit.getConsoleSender().sendMessage(PREFIX + "§cCore aktiviert!");
    }

    /**
     * Deaktivierungslogik
     */
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§cCore deaktiviert!");
    }
}
