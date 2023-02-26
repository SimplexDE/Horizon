package de.simplex.horizon.commands;

import de.simplex.horizon.commands.utility.MessageSender;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.methods.PlayerConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Vanish implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        MessageSender ms = new MessageSender();

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                ms.sendToSender(sender, Horizon.PREFIXCOLOR + "Nur für Spieler ausführbar");
                return false;
            }
            Player p = (Player) sender;

            PlayerConfig pc = new PlayerConfig(p);

            boolean vanished = pc.isSet("staff.vanish") && pc.getBoolean("staff.vanish");

            if (!vanished) {
                p.hidePlayer(Horizon.getHorizon(), p);
                p.setSilent(true);
                pc.set("staff.vanish", true);
                ms.sendToPlayer(p, Horizon.PREFIXCOLOR + "Du bist nun Unsichtbar.");
            } else {
                p.showPlayer(Horizon.getHorizon(), p);
                p.setSilent(false);
                pc.set("staff.vanish", false);
                ms.sendToPlayer(p, Horizon.PREFIXCOLOR + "Du bist nun Sichtbar.");
            }
            pc.save();
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
