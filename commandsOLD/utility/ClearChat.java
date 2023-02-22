package de.simplex.horizon.commandsOLD.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commandsOLD.api.MessageEngine;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {

    Main main = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

        MessageEngine ME = new MessageEngine(main);

        if (args.length == 0) {
            if (!sen.hasPermission("core.ignoreWlClearChat")) {
                sen.sendMessage(Main.NO_PERMISSION);
                return true;
            }

            for (int i = 0; i <= 400; i++)
                ME.sendTo("  <newline> \n <newline> ", this.main.adventure().players());
            ME.broadcast(Main.PREFIX + "Der Chat wurde von <yellow>" + sen.getName() + "<grey> geleert.");


            /**
             * Alternativ, sollte die Console von dem Clear ausgeschlossen sein (vmtl. weniger performant):
             *
             * for(Player ap : Bukkit.getOnlinePlayers()){
             *      for(int i =0; i <= 400; i++)
             * 			ap.sendMessage(" §0» \n §0» ");
             * 		ap.sendMessage(Main.PREFIX + "Der Chat wurde von " + sen.getName() + " geleert.");
             *  }
			 *  Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Der Chat wurde von " + sen.getName() + " geleert.");
			 */

		} else if (args.length == 1) {
			boolean b = false;
			int i = 100;
			try {
				i = Integer.parseInt(args[0]);
			} catch (NumberFormatException e1) {
				try {
					b = Boolean.parseBoolean(args[0]);
				} catch (Exception e) {
                    ME.sendTo("Die angegebene Variable ist kein erwarteter Boolean/Integer-Wert.", (Audience) sen);
					return true;
				}
			}

			if (b) {
				for (Player ap : Bukkit.getOnlinePlayers()) {
					if (!ap.hasPermission("core.ignoreWlClearChat"))
						for (int c = 0; c <= 400; c++)
                            ME.sendTo("  <newline> \n <newline> ", this.main.adventure().players());
					// Clear msg trotzdem ausgeben, damit auch alle Spieler:innen / Teammitglieder bescheid wissen
                    ME.broadcast(Main.PREFIX + "Der Chat wurde von <yellow>" + sen.getName() + "<grey> geleert.");
				}
			} else {
                for (int c1 = 0; c1 <= i; c1++)
                    ME.sendTo("  <newline> \n <newline> ", this.main.adventure().players());
                ME.broadcast(Main.PREFIX + "Der Chat wurde von <yellow>" + sen.getName() + "<grey> geleert.");
            }
		} else
            ME.sendTo(Main.PREFIX + Main.ARGS_TOO_LONG, (Audience) sen);
		return false;
	}
}
