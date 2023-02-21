package de.simplex.horizon.commands.fun;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.AchievementEngine;
import de.simplex.horizon.commands.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /friede Befehl
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Friede implements CommandExecutor {

    private final Main Main;

    public Friede(de.simplex.horizon.Main main) {
        Main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MessageEngine ME = new MessageEngine(this.Main);

        ME.sendTo("<dark_grey>»</dark_grey> <aqua>Secret</aqua> <dark_grey>┃</dark_grey> <green>Friede, Freude, Eierkuchen!", (Audience) sender);

        AchievementEngine.activateAchievement((Player) sender, "FRIEDE_FREUDE_EIERKUCHEN");

        return false;
    }
}
