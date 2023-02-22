package de.simplex.horizon.commands.api;

import de.simplex.horizon.horizon.Horizon;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdventureAPI {

    public static AdventureAPI aapi = new AdventureAPI();

    public Audience getAllSender() {
        return Horizon.adventure().all();
    }

    public Audience getConsoleSender() {
        return Horizon.adventure().console();
    }

    public Audience getPlayersSender() {
        return Horizon.adventure().players();
    }

    public Audience getPlayerSender(Player player) {
        return Horizon.adventure().player(player);
    }

    public Audience getSenderSender(CommandSender sender) {
        return Horizon.adventure().sender(sender);
    }

    public Audience getPermissionSender(@NotNull Key permission) {
        return Horizon.adventure().permission(permission);
    }

    public Audience getPermissionSender(@NotNull java.lang.String permission) {
        return Horizon.adventure().permission(permission);
    }

    public Audience getWorldSender(@NotNull Key world) {
        return Horizon.adventure().world(world);
    }
}
