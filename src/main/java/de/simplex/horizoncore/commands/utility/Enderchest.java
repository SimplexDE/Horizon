package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class Enderchest implements CommandExecutor, Listener {

	public static final String EC_INV_NAME = "§9Enderchest";


	/**
	 * UNFERTIG!
	 * -> Komponenten fehlen
	 */
	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {
		if (sen instanceof Player p) {

			if (args.length <= 0) {
				int size = 1; //pC.isSet("Player.enderChest.getSize") ? pC.getInt("Player.enderChest.getSize") : 1 ;

				Inventory i = Bukkit.createInventory(null, 9 * size, EC_INV_NAME);

				/**
				 * TEMPORÄR
				 * Wird in Config, statt pConfig gespeichert
				 */
				FileConfiguration c = Main.getPlugin().getConfig();

				/**
				 * ! der Rückgabewert von pC.get(<String>) könnte unpassend sein !
				 * Mögliche Änderung nach Testung
				 *  -> Abwägung / Suche nach besseren möglichkeiten folgt.
				 */
				ItemStack[] content = (ItemStack[]) c.get("Player.enderChest.content");

				if (content != null) {
					i.setContents(content);
				}

				p.openInventory(i);
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("upgrade")) {
					int size = 1; //pC.isSet("Player.enderChest.getSize") ? pC.getInt("Player.enderChest.getSize") : 1 ;

					if (size < 6) {

						int tokens = 10, //pC.getTokens();
								cost = (int) Math.round(20 * size * 1.2);

						if (tokens >= cost) {
							tokens = -cost;
//							pC.setTokens(tokens);

						} else {
							p.sendMessage(Main.PREFIX + "Du hast nicht genügend Tokens.");
						}
					} else {
						p.sendMessage(Main.PREFIX + "Deine Enderchest ist bereits auf dem höchsten Level!");
					}
				} else {
					p.sendMessage(Main.PREFIX + "Dieses Argument konnte nicht gefunden werden. Versuche \"/Enderchest <help; upgrade>\".");
				}
			}
		} else {
			sen.sendMessage(Main.NOT_A_PLAYER);
		}
		return false;
	}

	@EventHandler
	public void handle(InventoryCloseEvent e) {
		HumanEntity p = e.getPlayer();
		InventoryView oi = p.getOpenInventory();
		if (oi.getTitle().equalsIgnoreCase(EC_INV_NAME)) {

			/**
			 * TEMPORÄR
			 * Wird in Config, statt pConfig gespeichert
			 */
			FileConfiguration c = Main.getPlugin().getConfig();

			ItemStack[] content = oi.getTopInventory().getContents();
			c.set("Player.enderChest.content", content);
			Main.getPlugin().saveConfig();
		}
	}
}
