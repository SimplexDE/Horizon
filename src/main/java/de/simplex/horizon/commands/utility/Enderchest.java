package de.simplex.horizon.commands.utility;

import de.simplex.horizon.Main;
import de.simplex.horizon.systems.PlayerConfig;
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

import java.util.Random;

public class Enderchest implements CommandExecutor, Listener {

	public static final String EC_INV_NAME = "§9Enderchest";

	public final String TRY = " Versuche \"/Enderchest <help; upgrade>\".";

    /**
     * Noch in Arbeit - TODO: MessageEngine
     */
	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {
		if (sen instanceof Player p) {
            PlayerConfig pC = PlayerConfig.loadConfig(p);
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
				if (pC.isSet("Player.enderChest.content.0"))
					for (String s : pC.getConfigurationSection("Player.enderChest.content").getKeys(false)) {
						i.setItem(Integer.parseInt(s), pC.getItemStack("Player.enderChest.content." + s));
					}

				p.openInventory(i);

				Random r = new Random();
				if (r.nextInt(9) >= 8)
					p.sendMessage(Main.PREFIX + "Tipp: Mit \"/Enderchest help\" kannst du deine Enderchest anpassen.");
			} else if (args.length == 1) {
				int size = pC.isSet("Player.enderChest.getSize") ? pC.getInt("Player.enderChest.getSize") : 1;

				if (args[0].equalsIgnoreCase("upgradeInfo")) {
					int tokens = pC.getTokens(),
							cost = (int) Math.round(20 * size * 1.2);
					p.sendMessage(Main.PREFIX + String.format("Das Upgrade kostet §e%s.", cost));
					if (cost > tokens) {
						p.sendMessage(Main.PREFIX + String.format("Dir fehlen §e%s §7Tokens.", (cost - tokens)));
					}

				} else if (args[0].equalsIgnoreCase("upgrade")) {
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
				} else if (args[0].equalsIgnoreCase("help")) {
					p.sendMessage(Main.PREFIX + "Nutzung: /enderchest [<upgradeInfo; upgrade; help>]");
				} else {
					p.sendMessage(Main.PREFIX + "Dieses Argument konnte nicht gefunden werden.");
				}
			} else {
                p.sendMessage(Main.PREFIX + Main.ARGS_TOO_LONG);
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
                PlayerConfig pC = PlayerConfig.loadConfig(p);

				ItemStack[] content = oi.getTopInventory().getContents();
				int i = 0;
				for (ItemStack is : content) {
					pC.set("Player.enderChest.content." + i, is);
					i++;
				}
				pC.save();
			}
		}
	}
}
