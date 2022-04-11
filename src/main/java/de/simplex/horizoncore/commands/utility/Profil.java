package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.systems.IB;
import de.simplex.horizoncore.systems.PConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Profil implements CommandExecutor, Listener {

	public static final Inventory pi = Bukkit.createInventory(null, 5 * 9, "Profil-Copy");
	public static final String profileInvTitle = "§9Dein Profil";
	public static final ItemStack filler = IB.lore(IB.name(new ItemStack(Material.BLUE_STAINED_GLASS_PANE), " §0 "), " §0 ");

	/**
	 * WIP -> TearsDontFall
	 */

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (sen instanceof Player p) {

			ItemStack[] piClone = pi.getContents().clone();
			Inventory i = Bukkit.createInventory(null, pi.getSize(), profileInvTitle);

			i.setContents(piClone);
			p.openInventory(i);
		} else {
			sen.sendMessage(Main.NOT_A_PLAYER);
		}
		return false;
	}

	public static void fillProfil() {

		IB.invFiller(pi, filler);
		pi.setItem(8, new ItemStack(Material.RED_CANDLE));

		pi.setItem(6, new ItemStack(Material.PLAYER_HEAD));
		pi.setItem(5, new ItemStack(Material.SPYGLASS));
		pi.setItem(4, new ItemStack(Material.WITHER_ROSE));
	}

	@EventHandler
	public void handle(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player p) {

			if (e.getClickedInventory() != p.getOpenInventory().getTopInventory()) return;
			ItemStack item = e.getCurrentItem();
			if (item == null) return;

			String title = p.getOpenInventory().getTitle();
			if (title.equals(profileInvTitle)) {
				e.setCancelled(true);

				switch (item.getType()) {
					case RED_CANDLE:
						p.closeInventory();
						break;
					case PLAYER_HEAD:
						p.sendMessage("Friend System");
						break;
					case SPYGLASS:
						p.sendMessage("Statistics");
						cleanProfile(p);
						statisticsTab(p);
						break;
					case WITHER_ROSE:
						p.sendMessage("Role-Play Menü");
						break;
					default:
						break;
				}
			}
		}
	}

	public void statisticsTab(Player p) {
		Inventory in = p.getOpenInventory().getTopInventory();
		in.setItem(10, IB.name(playerSkull(new ItemStack(Material.PLAYER_HEAD), p), "§7Dies ist dein Profil:"));

		PConfig pc = PConfig.loadConfig(p);
		in.setItem(20, IB.lore(IB.name(new ItemStack(Material.DIAMOND_SWORD),
				"§bKills sn Spieler:innen"), String.format("§b§o%s", pc.getSafeInt("Player.stats.playerKills"))));
		in.setItem(21, IB.lore(IB.name(new ItemStack(Material.IRON_SWORD),
				"§3Kills"), String.format("§3§o%s", pc.getSafeInt("Player.stats.mobKills"))));
		in.setItem(22, IB.lore(IB.name(new ItemStack(Material.SHIELD),
				"§6Deaths"), String.format("§6§o%s", pc.getSafeInt("Player.stats.deaths"))));
	}

	public void cleanProfile(Player p) {
		if (p.getOpenInventory().getTitle().equals(profileInvTitle)) {
			p.getOpenInventory().getTopInventory().setContents(pi.getContents());
		}
	}

	public static ItemStack playerSkull(ItemStack i, OfflinePlayer t) {
		ItemStack ic = i.clone();
		ic.setType(Material.PLAYER_HEAD);
		SkullMeta sm = (SkullMeta) ic.getItemMeta();
		sm.setOwningPlayer(t);
		if (sm.getOwningPlayer() == null) return i;
		return ic;
	}
}


