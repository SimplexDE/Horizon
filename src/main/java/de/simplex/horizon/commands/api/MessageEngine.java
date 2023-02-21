package de.simplex.horizon.commands.api;

import de.simplex.horizon.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MessageEngine {

    Main main = Main.getPlugin();

    public MessageEngine(Main main) {
    }

    public Component parseMessage(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public void sendTo(String message, Audience sendTo) {

        Component parsedMessage = MiniMessage.miniMessage().deserialize(message);

        sendTo.sendMessage(parsedMessage);
    }

    public String broadcast(String message) {
        Audience everyone = main.adventure().all();
        Component parsed = parseMessage(message);
        everyone.sendMessage(parsed);
        return null;
    }

    public Component sendChatMessage(String playerName, String playerRank, String rankColour, String message) {
        Audience everyone = main.adventure().all();
        Component pre_msg = Component.text(message, TextColor.color(170, 170, 170));
        Component msg = MiniMessage.miniMessage().deserialize(playerRank + " <dark_gray>┃ " + rankColour + playerName + " <dark_gray>» <gray>").append(pre_msg);
        everyone.sendMessage(msg);
        return msg;
    }


}
