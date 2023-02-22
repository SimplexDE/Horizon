package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.MessageEngine;
import de.simplex.horizon.systems.PlayerConfig;
import de.simplex.horizon.systems.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tokens implements CommandExecutor {

	Main main = Main.getPlugin();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		MessageEngine ME = new MessageEngine(main);

		if (sender instanceof Player p) {
			PlayerConfig pC = PlayerConfig.loadConfig(p);
			if (args.length == 0) {
				p.sendMessage(Main.PREFIX + String.format("Du hast §e%s §7Tokens.", pC.getTokens()));
			} else if (args.length == 1) {

				if (args[0].equalsIgnoreCase("help")) {
					String s = "/Tokens [<help%s>] [<Zahl; Name>] [<Name>]";
					if (p.hasPermission("core.manageTokens")) {
						p.sendMessage(Main.PREFIX + String.format(s, "; reset; set; take; give"));
						return true;
					}
					p.sendMessage(Main.PREFIX + String.format(s, ""));
					return true;
				}

				if (!p.hasPermission("core.manageTokens.viewOthers")) {
					p.sendMessage(Main.PREFIX + "Diese Argumenten länge ist ungültig.");
					return true;
				}

				Player t = Bukkit.getPlayer(args[0]);
				if (t == null) {
					p.sendMessage(Main.PREFIX + "Das Target existiert nicht.");
					return true;
				}
				PlayerConfig tC = PlayerConfig.loadConfig(t);
				p.sendMessage(Main.PREFIX + String.format("%s hat %s Tokens.", t.getName(), tC.getTokens()));

			} else if (args.length >= 4) {
				p.sendMessage(Main.PREFIX + "Diese Argumenten länge ist ungültig.");
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("reset")) {
					if (!p.hasPermission("core.manageTokens.reset")) {
						p.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
						return true;
					}

					Player t = Bukkit.getPlayer(args[1]);
					if (t == null) {
						OfflinePlayer ot = Bukkit.getOfflinePlayer(args[1]);
						if (!ot.hasPlayedBefore() || !PlayerConfig.hasConfig(ot)) {
							p.sendMessage(Main.PREFIX + args[1] + " war noch nie auf dem Server, oder hat keine PlayerConfig.");
							return true;
						}
						PlayerConfig otC = PlayerConfig.loadConfig(ot);
						otC.setTokens(0);
						otC.save();
						p.sendMessage(Main.PREFIX + args[1] + "'s Tokens wurden zu 0 gesetzt. (Derzeit offline)");
						return true;
					}

					PlayerConfig tC = PlayerConfig.loadConfig(t);
					tC.setTokens(0);
					tC.save();
					Scoreboard.updateTokens(t);

					t.sendMessage(Main.PREFIX + "Deine Tokens wurden resettet!");
					p.sendMessage(Main.PREFIX + t.getName() + "'s Tokens wurden zu 0 gesetzt.");
				} else
					p.sendMessage(Main.PREFIX + "Dieses Argument existiert nicht.");
			} else {
				Player t = Bukkit.getPlayer(args[2]);

				if (t == null) {
					p.sendMessage(Main.PREFIX + args[2] + " ist nicht online.");
					return true;
				}

				PlayerConfig tC = PlayerConfig.loadConfig(t);
				int i;

				try {
					i = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					p.sendMessage(Main.PREFIX + "Das ist keine gültige Zahl!");
					return true;
				}

				if (args[0].equalsIgnoreCase("set")) {
					if (!p.hasPermission("core.manageTokens.set")) {
						p.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
						return true;
					}
					tC.setTokens(i);

				} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("take")) {
					if (!p.hasPermission("core.manageTokens.take")) {
						p.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
						return true;
					}
					tC.removeTokens(i);

				} else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("give")) {
					if (!p.hasPermission("core.manageTokens.give")) {
						p.sendMessage(Main.PREFIX + Main.NO_PERMISSION);
						return true;
					}
					tC.addTokens(i);
				}

				tC.save();
				Scoreboard.updateTokens(t);
				p.sendMessage(Main.PREFIX + String.format("%s's Tokens wurden zu §e%s §7gesetzt.", t.getName(), tC.getTokens()));
				t.sendMessage(Main.PREFIX + String.format("Deine Tokens wurden zu §e%s §7gesetzt.", tC.getTokens()));
			}
		} else
			sender.sendMessage(Main.NOT_A_PLAYER);
		return false;
	}
}
