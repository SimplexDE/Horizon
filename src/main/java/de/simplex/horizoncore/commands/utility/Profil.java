package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Profil implements CommandExecutor {

	public static final Inventory pi = Bukkit.createInventory(null, 5 * 9, "Profil-Copy");

	/**
	 * Angefangen -> TearsDontFall
	 */

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (sen instanceof Player p) {

			ItemStack[] piClone = pi.getContents().clone();
			Inventory i = Bukkit.createInventory(null, 5 * 9, "§9Profil");

		} else {
			sen.sendMessage(Main.NOT_A_PLAYER);
		}
		return false;
	}

	public static void invFiller(final Inventory in, final ItemStack filler) {
		for (int inventorySize = in.getSize() - 1, times = 0; times <= inventorySize; ++times) {
			in.setItem(times, filler);
		}
	}

	public static void fillProfil() {

		invFiller(pi, new ItemStack(Material.BLUE_STAINED_GLASS_PANE));

		/**
		 * Candle = back Item
		 * Player Head = Friend System
		 * SpyGlass = Statistics
		 * Wither Rose = Role-Play Role
		 */

		pi.setItem(8, new ItemStack(Material.RED_CANDLE));
		pi.setItem(6, new ItemStack(Material.PLAYER_HEAD));
		pi.setItem(5, new ItemStack(Material.SPYGLASS));
		pi.setItem(4, new ItemStack(Material.WITHER_ROSE));
	}
}
