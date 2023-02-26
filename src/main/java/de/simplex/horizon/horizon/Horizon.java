package de.simplex.horizon.horizon;

import de.simplex.horizon.commands.Kick;
import de.simplex.horizon.commands.Maintenance;
import de.simplex.horizon.commands.Vanish;
import de.simplex.horizon.listener.ChatListener;
import de.simplex.horizon.listener.ConnectionListener;
import de.simplex.horizon.listener.LuckPermsListener;
import de.simplex.horizon.method.ServerConfig;
import de.simplex.horizon.util.MessageSender;
import de.simplex.horizon.util.RankManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.UUID;

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
        new LuckPermsListener(LuckPermsProvider.get());

        getCommand("kick").setExecutor(new Kick());
        getCommand("maintenance").setExecutor(new Maintenance());
        getCommand("vanish").setExecutor(new Vanish());

        final PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new Connection(), getHorizon());
        pM.registerEvents(new Chat(), getHorizon());

        RankAssigning.setupColors();

        for (Player player : Bukkit.getOnlinePlayers()) {
            RankManager.assignRank(player);
        }

        ServerConfig.createConfig();
        ServerConfig.loadConfig();

        ms.sendToConsole(PREFIXCOLOR + "Loaded " + getDescription().getVersion() + " / " + getDescription().getAPIVersion());
    }

    @Override
    public void onDisable() {
        MessageSender ms = new MessageSender();

        PlayerRanks.clear();
        RankColors.clear();

        ms.sendToConsole(PREFIXCOLOR + "Unloaded " + getDescription().getVersion() + " / " + getDescription().getAPIVersion());

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
