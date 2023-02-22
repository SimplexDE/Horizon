package de.simplex.horizon.horizon;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Horizon extends JavaPlugin {

    public static Horizon horizon;

    public static String VERSION = "";

    public static Horizon getHorizon() {
        return horizon;
    }

    @Override
    public void onEnable() {
        horizon = this;
        VERSION = getPluginMeta().getVersion();

        LanguageFramework.loadMessages();
        LanguageFramework.PRE = LanguageFramework.getMessage("en", "Prefix");

        ConsoleCommandSender cs = Bukkit.getConsoleSender();


        cs.sendMessage(LanguageFramework.getMessage(LanguageFramework.getServerLang(), "PluginStarted"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
