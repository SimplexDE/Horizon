package de.simplex.horizoncore.systems;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.systems.materialLists.Mp;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;

public class Utils {

	public static String genSetColor(int i) {
		if (i > 13)
			if (i % 2 == 1)
				i = i / 2 + 1;
			else
				i /= 2;
		if (i >= 0 || i <= 13) {
			switch (i) {
				case 0:
					return "2";
				case 1:
					return "a";
				case 2:
					return "e";
				case 3:
					return "6";
				case 4:
					return "4";
				case 5:
					return "c";
				case 6:
					return "d";
				case 7:
					return "5";
				case 8:
					return "1";
				case 9:
					return "9";
				case 10:
					return "3";
				case 11:
					return "b";
				case 12:
					return "7";
			}
			return "f";
		}
		return null;
	}

	public static void bcToWorld(String msg, World world) {
		for (Player ap : Bukkit.getOnlinePlayers()) {
			if (ap.getWorld().getName().equals(world.getName()))
				ap.sendMessage(msg);
		}
	}

	public static void buyMat(Player p, Mp mp, Material m, ClickType ct) {

		int amount = amountByClickType(ct);
		if (amount <= 0) return;

		PConfig pc = PConfig.loadConfig(p);
		int c = mp.getMatPrice(m) * amount, tokens = pc.getTokens();

		if (tokens < c) {
			actionBar(p, String.format("Zum Kauf fehlen dir §e%s §7Tokens§8!", (c - tokens)), 0.5f, 0.5f, Sound.BLOCK_NOTE_BLOCK_GUITAR);
			p.closeInventory();
			return;
		}

		pc.removeTokens(c);
		Sb.updateTokens(p);

		p.getInventory().addItem(new ItemStack(m, amount));
		actionBar(p, String.format("Du hast dir §a%s %s für §e%s §7Tokens gekauft§8!", amount, mp.getMatName(m), c), 0.4f, 1.2f, Sound.BLOCK_NOTE_BLOCK_CHIME);
	}

	public static int amountByClickType(ClickType ct) {
		return switch (ct) {
			case LEFT -> 1;
			case RIGHT -> 8;
			case MIDDLE -> 64;
			case DROP -> 128;
			default -> 0;
		};
	}

	public static int getMatAmount(final Material mat, final Inventory inv) {
		int gesMat = 0;
		for (int t = 0; t < inv.getSize(); ++t)
			if (inv.getItem(t) != null && inv.getItem(t).getType() == mat)
				gesMat += inv.getItem(t).getAmount();
		return gesMat;
	}

	public static void removeMaterial(final Material mat, int amount, final Inventory inv) {
		final ItemStack item = new ItemStack(mat), air = new ItemStack(Material.AIR);
		for (int times = 0; times <= inv.getSize(); ++times) {
			if (inv.getItem(times) != null && inv.getItem(times).getType() == mat) {
				if (inv.getItem(times).getAmount() >= amount) {
					final int amo = inv.getItem(times).getAmount() - amount;
					item.setAmount(amo);
					inv.setItem(times, item);
					return;
				}
				if (inv.getItem(times).getAmount() <= amount) {
					amount -= inv.getItem(times).getAmount();
					inv.setItem(times, air);
				}
			}
		}
	}

	public static void sellMat(final InventoryClickEvent e, final Player p, final Material m, Mp mp, ClickType ct) {
		PConfig pc = PConfig.loadConfig(p);
		int i = amountByClickType(ct), cost = mp.getMatPrice(m);

		if (getMatAmount(m, p.getInventory()) >= i) {
			removeMaterial(m, i, p.getInventory());

			pc.setTokens(pc.getTokens() + cost * i);
			Sb.updateTokens(p);

			actionBar(p, String.format("Du hast §2%s %s für §e%s §7Tokens verkauft§8.", i, mp.getMatName(m), cost),
					0.5f, 1.1f, Sound.BLOCK_NOTE_BLOCK_BELL);
		} else
			actionBar(p, "You do §cnot§7 have enough §b" + m.toString().toLowerCase() + "§7 to continue§8.",
					0.5f, 0.6f, Sound.BLOCK_NOTE_BLOCK_GUITAR);
	}

	public static void createFolder(String path) {
		if (path == null)
			path = "plugins/Horizoncore/players";
		File f1 = new File(path);
		if (f1.isDirectory())
			Bukkit.getConsoleSender().sendMessage("Der Ordner existiert bereits!");
		if (!f1.mkdir())
			Bukkit.getConsoleSender().sendMessage("Der Ordner konnte nicht erstellt werden.");
	}

	public static boolean isMonster(final Entity e) {
		return (e instanceof Creature || e.getType() == EntityType.SLIME) && (e instanceof Monster || e.getType() == EntityType.SLIME);
	}

	public static void actionBar(final Player p, final String msg, final float vol, final float pit, final Sound s) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Main.PREFIX + msg));
		if (s != null) p.playSound(p.getLocation(), s, vol, pit);
	}

	public static TextComponent getClickable(String msg, ClickEvent.Action a, String clickMsg, @Nullable String hover) {
		TextComponent message = new TextComponent(msg);
		message.setClickEvent(new ClickEvent(a, clickMsg));
		if (hover != null)
			message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
		return message;
	}
}
