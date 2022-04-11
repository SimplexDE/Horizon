package de.simplex.horizoncore.systems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class IB {

	public static ItemStack getFiller(final Material m, final boolean def, final boolean glint, final String name, final String lore) {
		if (!m.toString().contains("_PANE"))
			System.out.println("The material type ''GLASS_PANE'' is suggested; You will need to change it to avoid this message.");
		final ItemStack i = new ItemStack(m);
		if (!def) lore(name(i, name), lore);
		else lore(name(i, " §0 "), " §0 ");
		if (glint) flag(ench(i, Enchantment.DURABILITY, 0), ItemFlag.HIDE_ENCHANTS);
		return i;
	}

	public static ItemStack lore(final ItemStack item, final String... lore) {
		final ItemMeta itemM = item.getItemMeta();
		itemM.setLore(Arrays.asList(lore));
		item.setItemMeta(itemM);
		return item;
	}

	public static List<String> getLore(final ItemStack item) {
		return item.getItemMeta().getLore();
	}

	public static boolean loreContains(final ItemStack item, final String s) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()
				&& item.getItemMeta().getLore().toString().contains(s)) return true;
		return false;
	}

	public static ItemStack lore(final ItemStack item, final List<String> lore) {
		final ItemMeta itemM = item.getItemMeta();
		itemM.setLore(lore);
		item.setItemMeta(itemM);
		return item;
	}

	public static ItemStack name(final ItemStack item, final String name) {
		final ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		item.setItemMeta(itemM);
		return item;
	}

	public static ItemStack addLore(final ItemStack item, final List<String> addedLore, List<String> loreBefore) {
		final ItemMeta itemM = item.getItemMeta();
		if (loreBefore == null) {
			loreBefore = getLore(item);
		}
		loreBefore.addAll(addedLore);
		itemM.setLore(addedLore);
		item.setItemMeta(itemM);
		return item;
	}

	public static ItemStack ench(final ItemStack item, final Enchantment ench, final int level) {
		final ItemMeta itemM = item.getItemMeta();
		itemM.addEnchant(ench, level, true);
		item.setItemMeta(itemM);
		return item;
	}

	public static ItemStack flag(final ItemStack item, final ItemFlag flag) {
		final ItemMeta itemM = item.getItemMeta();
		itemM.addItemFlags(flag);
		item.setItemMeta(itemM);
		return item;
	}

	public static void invFiller(final Inventory in, final ItemStack filler) {
		for (int inventorySize = in.getSize() - 1, times = 0; times <= inventorySize; ++times) {
			in.setItem(times, filler);
		}
	}
}
