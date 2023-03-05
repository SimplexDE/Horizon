package de.simplex.horizon.commands;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.util.MessageSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class TeamChat implements TabExecutor {

    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
            .useUnusualXRepeatedCharacterHexFormat()
            .hexColors()
            .build();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        MessageSender ms = new MessageSender();
        Player p = (Player) sender;

        User u = lpapi.getUserManager().getUser(p.getUniqueId());
        assert u != null;
        Group g = lpapi.getGroupManager().getGroup(u.getPrimaryGroup());

        assert g != null;
        String prefix = u.getCachedData().getMetaData().getPrefix();

        String msg = "";

        for (String s : args) {
            msg += s + " ";
        }


        Component emsg = MiniMessage.miniMessage().deserialize(Color.AQUA.getColorMiniMessage() + "Teamchat " + Color.DARK_GRAY.getColorMiniMessage() + "┃ " + prefix + sender.getName()
                + " " + Color.DARK_GRAY.getColorMiniMessage() + "» " + Color.LIGHT_GRAY.getColorMiniMessage() + msg
                .replace("<", "⏴")
                .replace(">", "⏵"));

        Audience ChatAudience = MessageSender.aapi.getPermissionSender("server.chat.team");
        ChatAudience.sendMessage(emsg);

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
