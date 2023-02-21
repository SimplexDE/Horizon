package de.simplex.horizon.systems;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class StandardAchievementReplacer implements Listener {

    Main main = Main.getPlugin();

    @EventHandler
    public void handle(@NonNull PlayerAdvancementDoneEvent e) {
        MessageEngine ME = new MessageEngine(main);

        String formatMsg = "<dark_grey>» <light_purple>✯ <dark_grey>┃ <yellow>%s <grey>hat die Errungenschaft <gold>[<rainbow><hover:show_text:'<green>%s'>%s</hover></rainbow><gold>] <gray>freigeschaltet!";


        ME.broadcast(String.format(formatMsg,
                e.getPlayer().getName(),
                Objects.requireNonNull(e.getAdvancement().getDisplay()).getDescription(),
                Objects.requireNonNull(e.getAdvancement().getDisplay()).getTitle()));
    }

}
