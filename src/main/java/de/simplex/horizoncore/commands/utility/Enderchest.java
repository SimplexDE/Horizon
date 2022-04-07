package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.systems.PConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class Enderchest implements CommandExecutor, Listener {

	public static final String EC_INV_NAME = "§9Enderchest";

	public final String TRY = " Versuche \"/Enderchest <help; upgrade>\".";

	/**
	 * Noch in Arbeit
	 */
	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {
		if (sen instanceof Player p) {
			PConfig pC = PConfig.loadConfig(p);
			if (args.length <= 0) {
				int size = pC.isSet("Player.enderChest.getSize") ? pC.getInt("Player.enderChest.getSize") : 1;
				Inventory i = Bukkit.createInventory(null, 9 * size, EC_INV_NAME);

				/**
				 * ! der Rückgabewert von pC.get(<String>) könnte unpassend sein !
				 * Mögliche Änderung nach  ! Testung !
				 *  -> Abwägung / Suche nach besseren möglichkeiten folgt.
				 *
				 *  Zum Abgleich wird zunächst erstmal nur der Inv-Content gespeichert. (-> InventoryCloseEvent)
				 */
				ItemStack[] content = null; //(ItemStack[]) pC.get("Player.enderChest.content");

				if (content != null) {
					i.setContents(content);
				}

				p.openInventory(i);
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("upgrade")) {
					int size = pC.isSet("Player.enderChest.getSize") ? pC.getInt("Player.enderChest.getSize") : 1;

					if (size >= 6) {
						p.sendMessage(Main.PREFIX + "Deine Enderchest ist bereits auf dem höchsten Level!");
						return true;
					}
					int tokens = pC.getTokens(),
							cost = (int) Math.round(20 * size * 1.2);

					if (tokens >= cost) {
						tokens = -cost;
						pC.setTokens(tokens);
						p.sendMessage(Main.PREFIX + "Kauf erfolgreich.");
					} else {
						p.sendMessage(Main.PREFIX + "Du hast nicht genügend Tokens.");
					}
				} else {
					p.sendMessage(Main.PREFIX + "Dieses Argument konnte nicht gefunden werden.");
				}
			} else {
				p.sendMessage(Main.PREFIX + "Du hast zu viele Argumente verwendet. ");
			}
		} else {
			sen.sendMessage(Main.NOT_A_PLAYER);
		}
		return false;
	}

	@EventHandler
	public void handle(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player p) {
			InventoryView oi = p.getOpenInventory();
			if (oi.getTitle().equalsIgnoreCase(EC_INV_NAME)) {
				PConfig pC = PConfig.loadConfig(p);

				ItemStack[] content = oi.getTopInventory().getContents();
				pC.set("Player.enderChest.content", content);
				pC.save();
			}
		}
	}
}
