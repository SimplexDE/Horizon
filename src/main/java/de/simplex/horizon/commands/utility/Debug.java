package de.simplex.horizon.commands.utility;

import de.simplex.horizon.horizon.Horizon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Debug implements TabExecutor {

    MessageSender ms = new MessageSender();
    TabListCompiler tc = new TabListCompiler();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            ms.sendToSender(sender, "<red>Current debug commands: updatevisuals");
            return false;
        }

        if (args[0].equals("updatescoreboard")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                final Component header = MiniMessage.miniMessage().deserialize("<newline>  <rainbow>ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ</rainbow>  <newline> <grey>Du befindest dich auf <yellow>" + p.getWorld().getName() + "<grey>.<newline>");
                final Component footer = MiniMessage.miniMessage().deserialize("<newline><green>" + Bukkit.getOnlinePlayers().size() + "<grey>/<red>" + Bukkit.getMaxPlayers());
                tc.setTabListHeaderAndFooter(Horizon.adventure().players(), header, footer);
            }
            ms.sendToSender(sender, "<red>Reloaded Visuals...");
            return true;
        } else {
            ms.sendToSender(sender, "<red>Current debug commands: updatescoreboard");
            return false;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()) {
            return null;
        }
        if (args.length == 1) {
            List<String> subcommands = new ArrayList<>();
            subcommands.add("updatescoreboard");
            return subcommands;
        }
        return null;
    }
}
