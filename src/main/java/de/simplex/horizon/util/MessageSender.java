package de.simplex.horizon.util;


import de.simplex.horizon.command.api.AdventureAPI;
import net.kyori.adventure.key.Key;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageSender {

    public static AdventureAPI aapi = new AdventureAPI();
    public static MessageCompiler mc = new MessageCompiler();


    public void sendToAll(String message) {
        mc.compileMessage(aapi.getAllSender(), message);
    }

    public void sendToConsole(String message) {
        mc.compileMessage(aapi.getConsoleSender(), message);
    }

    public void sendToPlayers(String message) {
        mc.compileMessage(aapi.getPlayersSender(), message);
    }

    public void sendToPlayer(Player player, String message) {
        mc.compileMessage(aapi.getPlayerSender(player), message);
    }

    public void sendToSender(CommandSender sender, String message) {
        mc.compileMessage(aapi.getSenderSender(sender), message);
    }

    public void sendToPermission(Key permission, String message) {
        mc.compileMessage(aapi.getPermissionSender(permission), message);
    }

    public void sendToPermission(String permission, String message) {
        mc.compileMessage(aapi.getPermissionSender(permission), message);
    }

    public void sendToWorld(Key world, String message) {
        mc.compileMessage(aapi.getWorldSender(world), message);
    }

}
