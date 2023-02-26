package de.simplex.horizon.method;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;

public class Utils {

    public static String genSetColor(int i) {
        if (i > 13)
            if (i % 2 == 1)
                i = i / 2 + 1;
            else
                i /= 2;
        return switch (i) {
            case 0 -> "2";
            case 1 -> "a";
            case 2 -> "e";
            case 3 -> "6";
            case 4 -> "4";
            case 5 -> "c";
            case 6 -> "d";
            case 7 -> "5";
            case 8 -> "1";
            case 9 -> "9";
            case 10 -> "3";
            case 11 -> "b";
            case 12 -> "7";
            default -> "f";
        };
    }

    public static int amountByClickType(@NotNull ClickType ct) {
        return switch (ct) {
            case LEFT -> 1;
            case RIGHT -> 8;
            case MIDDLE -> 64;
            case DROP -> 128;
            default -> 0;
        };
    }

    public static void createFolder(String path) {
        if (path == null)
            path = "plugins/Horizon/players";
        File f1 = new File(path);

        if (f1.isDirectory())
            Bukkit.getConsoleSender().sendMessage("Der Ordner existiert bereits!");
        if (!f1.mkdir())
            Bukkit.getConsoleSender().sendMessage("Der Ordner konnte nicht erstellt werden.");
    }

    public static boolean isMonster(final Entity e) {
        return (e instanceof Creature || e.getType() == EntityType.SLIME) && (e instanceof Monster || e.getType() == EntityType.SLIME);
    }

    public static void actionBar(final @NotNull Player p, final String msg, final float vol, final float pit, final Sound s) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
        if (s != null) p.playSound(p.getLocation(), s, vol, pit);
    }

    public static @NotNull TextComponent getClickable(String msg, ClickEvent.Action a, String clickMsg, @Nullable String hover) {
        TextComponent message = new TextComponent(msg);
        message.setClickEvent(new ClickEvent(a, clickMsg));

        if (hover != null)
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
        return message;
    }
}
