package de.simplex.horizon.horizon;

import de.simplex.horizon.commands.*;
import de.simplex.horizon.enums.Notification;
import de.simplex.horizon.listener.ChatListener;
import de.simplex.horizon.listener.ConnectionListener;
import de.simplex.horizon.listener.LuckPermsListener;
import de.simplex.horizon.listener.MOTD;
import de.simplex.horizon.method.ServerConfig;
import de.simplex.horizon.util.MessageSender;
import de.simplex.horizon.util.RankManager;
import de.simplex.horizon.util.UpdateVisibility;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class Horizon extends JavaPlugin {

    private static Horizon horizon;

    public static String VERSION = "",
            PREFIX = "",
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

        this.adventure = BukkitAudiences.create(Horizon.getHorizon());

        MessageSender ms = new MessageSender();
        new LuckPermsListener(LuckPermsProvider.get());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            new UpdateVisibility().updateVisibility(this);
        }, 0L, 5L);


        getCommand("enderchest").setExecutor(new Enderchest());
        getCommand("chatspy").setExecutor(new ChatSpy());
        getCommand("gamemode").setExecutor(new Gamemode());
        getCommand("kick").setExecutor(new Kick());
        getCommand("maintenance").setExecutor(new Maintenance());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("teamchat").setExecutor(new TeamChat());

        final PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new ConnectionListener(), getHorizon());
        pM.registerEvents(new ChatListener(), getHorizon());
        pM.registerEvents(new MOTD(), getHorizon());

        for (Player player : Bukkit.getOnlinePlayers()) {
            RankManager.assignRank(player);
        }

        ServerConfig.createConfig();
        ServerConfig.loadConfig();

        ms.sendToConsole(Notification.HORIZON.getNotification() + "Loaded " + getDescription().getVersion() + " / " + getDescription().getAPIVersion());
    }

    @Override
    public void onDisable() {
        MessageSender ms = new MessageSender();

        ms.sendToConsole(Notification.HORIZON.getNotification() + "Unloaded " + getDescription().getVersion() + " / " + getDescription().getAPIVersion());

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
