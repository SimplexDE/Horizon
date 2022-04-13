package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.systems.IB;
import de.simplex.horizoncore.systems.materialLists.Mp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopNpc implements CommandExecutor, Listener {

	public static final String s = "§5Material §eShop";

	public static Inventory i = Bukkit.createInventory(null, 5 * 9, s);

	/**
	 * UNGETESTET
	 */

	@Override

	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (sen instanceof Player p) {

			if (!p.hasPermission("core.shopNpc.spawn")) {
				p.sendMessage(Main.PREFIX + "Du hast nicht die nötigen Berechtigungen zum Ausführen des Commands.");
				return true;
			}

			Location l = p.getLocation();
			World w = l.getWorld();

			l = new Location(w, l.getBlockX(), l.getBlockY(), l.getBlockZ());
			l.setPitch((float) Math.round(l.getPitch()));
			l.add(0.5, 0.0, 0.5);

			Villager v = (Villager) w.spawnEntity(l, EntityType.VILLAGER);
			v.setInvulnerable(true);
			v.setCustomName(s);
			v.setCustomNameVisible(true);
			v.setAI(false);
			v.setGravity(false);
			v.setVillagerType(Villager.Type.TAIGA);
			v.setCanPickupItems(false);
			v.setAdult();

		} else {
			sen.sendMessage(Main.NOT_A_PLAYER);
		}
		return false;
	}

	/**
	 * Fehlend:
	 * Kauf Logik
	 */

	@EventHandler
	public void handle(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		Entity en = e.getRightClicked();
		if (en.getType() == EntityType.VILLAGER) {
			Villager v = (Villager) en;

			Mp mp = Mp.loadMp();

			if (v.getName().equals(s)) {
				int size = i.getSize();
				int z = 0;
				for (int c = 0; c <= size - 1; c++) {

					if (allowedSlot(c, size)) {
						Material m = Mp.materials.get(z);
						i.setItem(c, IB.lore(IB.name(new ItemStack(m), mp.getMatName(m)), "§6Preis§8: §6" + mp.getMatPrice(m)));
						z++;
					}

				}

				p.openInventory(i);
			}
		}
	}

	public static void fillSHopNpcInv() {
		IB.invFiller(i, IB.getFiller(Material.GRAY_STAINED_GLASS_PANE, true, false, " §0 ", " §0 "));

		i.setItem(9, IB.name(new ItemStack(Material.RED_CANDLE), "§cInventar schließen"));
		i.setItem(18, IB.name(new ItemStack(Material.YELLOW_DYE), "§eVorherige Reihe"));
		i.setItem(27, IB.name(new ItemStack(Material.LIME_DYE), "§aNächste Reihe"));
	}

	public static boolean allowedSlot(final int i, final int size) {
		return i < size && ((i >= 10 && i < 17) || (i >= 19 && i < 26) || (i >= 28 && i < 35));
	}
}
