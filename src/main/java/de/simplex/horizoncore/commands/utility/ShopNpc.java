package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.systems.IB;
import de.simplex.horizoncore.systems.Utils;
import de.simplex.horizoncore.systems.materialLists.Mp;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShopNpc implements CommandExecutor, Listener {

	public static final String s = "§5Material §eShop";

	public static Inventory i = Bukkit.createInventory(null, 5 * 9, s);

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {
		if (sen instanceof Player p) {

			if (!p.hasPermission("core.shopNpc.spawn")) {
				p.sendMessage(Main.PREFIX + "Du hast nicht die nötigen Berechtigungen zum Ausführen des Commands.");
				return true;
			}

			Location l = p.getLocation();
			World w = l.getWorld();

			l = new Location(w, l.getBlockX(), l.getBlockY(), l.getBlockZ(), l.getYaw(), l.getPitch());
			l.setPitch((float) Math.round(l.getPitch()));
			l.add(0.5, 0.0, 0.5);

			Villager v = (Villager) w.spawnEntity(l, EntityType.VILLAGER);
			// Invulnerable scheint nicht zu funktionieren
			// Ggf. das Damage event später canceln
			v.setInvulnerable(true);
			v.setCustomName(s);
			v.setMaxHealth(2048);
			v.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 200, false, false, false));
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

	@EventHandler
	public void handle(PlayerInteractAtEntityEvent e) {

		Player p = e.getPlayer();
		Entity en = e.getRightClicked();

		if (en.getType() == EntityType.VILLAGER) {
			Villager v = (Villager) en;

			if (v.getName().equals(s)) {

				Mp mp = Mp.loadMp();

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

	@EventHandler
	public void handle(InventoryClickEvent e) {

		if (e.isCancelled()) return;
		if (e.getWhoClicked() instanceof Player p) {

			if (e.getClickedInventory() != p.getOpenInventory().getTopInventory()) return;
			if (!p.getOpenInventory().getTitle().equals(s)) return;

			e.setCancelled(true);
			if (i == null) return;

			ItemStack i = e.getCurrentItem();
			Material mat = i.getType();

			Mp mp = Mp.loadMp();
			Inventory in = p.getOpenInventory().getTopInventory();

			int size = in.getSize();
			int z = 0;

			switch (mat) {

				case RED_CANDLE:
					p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 0.4f, 0.8f);
					p.closeInventory();
					break;

				case LIME_DYE:
					if (!in.getItem(27).hasItemMeta() || !in.getItem(27).getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))
						return;

					if (in.getItem(in.getSize() - 10).getType().toString().contains("PANE")) {
						p.sendMessage("§7Letzte Reihe§8.");
						return;
					}

					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.4f, 1.4f);
					in.setItem(e.getSlot(), IB.flag(IB.ench(i, Enchantment.SILK_TOUCH,
							i.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1), ItemFlag.HIDE_ENCHANTS));

					for (int c = 0; c <= size - 1; c++) {
						if (allowedSlot(c, size)) {
							Material m = Mp.materials.get(z);
							in.setItem(c, IB.lore(IB.name(new ItemStack(m), mp.getMatName(m)), "§6Preis§8: §6" + mp.getMatPrice(m)));
							z++;
						}
					}
					break;
				case YELLOW_DYE:
					if (!in.getItem(27).hasItemMeta() || !in.getItem(27).getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))
						return;

					if (in.getItem(18).getEnchantmentLevel(Enchantment.SILK_TOUCH) <= 0) {
						p.sendMessage("§7Letzte Reihe§8.");
						return;
					}

					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.4f, 0.6f);
					in.setItem(e.getSlot() + 9, IB.flag(IB.ench(in.getItem(e.getSlot() + 9), Enchantment.SILK_TOUCH,
							in.getItem(e.getSlot() + 9).getEnchantmentLevel(Enchantment.SILK_TOUCH) - 1), ItemFlag.HIDE_ENCHANTS));

					z = 9 * (in.getItem(18).getItemMeta().hasEnchant(Enchantment.SILK_TOUCH) ?
							in.getItem(18).getEnchantmentLevel(Enchantment.SILK_TOUCH) : 1);

					for (int c = 0; c <= size - 1; c++) {
						if (allowedSlot(c, size)) {
							Material m = Mp.materials.get(z);
							in.setItem(c, IB.lore(IB.name(new ItemStack(m), mp.getMatName(m)), "§6Preis§8: §6" + mp.getMatPrice(m)));
							z++;
						}
					}
					break;

				case GRAY_STAINED_GLASS_PANE:
					break;
				default:
					if (i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().toString().contains("Preis"))
						Utils.buyMat(p, mp, mat, e.getClick());
					break;

			}
		}
	}

	public static void fillSHopNpcInv() {
		IB.invFiller(i, IB.getFiller(Material.GRAY_STAINED_GLASS_PANE, true, false, " §0 ", " §0 "));

		i.setItem(9, IB.name(new ItemStack(Material.RED_CANDLE), "§cInventar schließen"));
		i.setItem(18, IB.name(new ItemStack(Material.YELLOW_DYE), "§eVorherige Reihe"));
		i.setItem(27, IB.name(new ItemStack(Material.LIME_DYE), "§aNächste Reihe"));

		i.setItem(i.getSize() - 4, IB.lore(IB.name(new ItemStack(Material.SPYGLASS), "§9§oTipp§8: "),
				"§7Linksklick§8: §a§oKaufe x1", "§7Rechtsklick§8: §a§oKaufe x8",
				"§7Mausradklick§8: §a§oKaufe x64", "§7Drop§8: §a§oKaufe x128"));
	}

	public static boolean allowedSlot(final int i, final int size) {
		return i < size && ((i >= 11 && i < 17) || (i >= 20 && i < 26) || (i >= 29 && i < 35));
	}
}
