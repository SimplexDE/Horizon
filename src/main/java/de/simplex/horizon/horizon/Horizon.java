package de.simplex.horizon.horizon;

import de.simplex.horizon.commands.Maintenance;
import de.simplex.horizon.commands.utility.MessageSender;
import de.simplex.horizon.listeners.Welcomer;
import de.simplex.horizon.methods.ServerConfig;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class Horizon extends JavaPlugin {

    public static Horizon horizon;

    public static String VERSION = "",
            PREFIX = "",
            PREFIXCOLOR = "",
            NO_PERMS = "<red>You have no permission to use this command";

    public static Horizon getHorizon() {
        return horizon;
    }

    private BukkitAudiences adventure;

    public static @NonNull BukkitAudiences adventure() {
        if (Horizon.getHorizon().adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return Horizon.getHorizon().adventure;
    }

    @Override
    public void onEnable() {
        horizon = this;
        VERSION = getDescription().getVersion();
        PREFIX = getDescription().getPrefix();
        PREFIXCOLOR = "<rainbow>Horizon</rainbow> <dark_gray>| <gray>";

        this.adventure = BukkitAudiences.create(Horizon.getHorizon());

        MessageSender ms = new MessageSender();

        getCommand("maintenance").setExecutor(new Maintenance());

        final PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new Welcomer(), getHorizon());

        ServerConfig.createConfig();
        ServerConfig.loadConfig();

        ms.sendToConsole(PREFIXCOLOR + "Horizon loaded successfully");
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        MessageSender ms = new MessageSender();

        ms.sendToConsole(PREFIXCOLOR + "Horizon unloaded successfully");
    }
}
