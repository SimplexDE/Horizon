package de.simplex.horizoncore.commands.utility;

import de.simplex.horizoncore.Main;
import de.simplex.horizoncore.commands.api.FriendEngine;
import de.simplex.horizoncore.systems.IB;
import de.simplex.horizoncore.systems.PConfig;
import de.simplex.horizoncore.systems.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

import java.util.List;
import java.util.UUID;

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
		pi.setItem(8, IB.name(new ItemStack(Material.RED_CANDLE), "§cInventar schließen"));

		pi.setItem(6, IB.name(new ItemStack(Material.PLAYER_HEAD), "§3Freunde Menü"));
		pi.setItem(5, IB.name(new ItemStack(Material.SPYGLASS), "§6Stats"));
		pi.setItem(4, IB.name(new ItemStack(Material.WITHER_ROSE), "§5Roleplay Verwaltung"));
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
				if (IB.loreContains(item, "»")) {
					switch (item.getType()) {
						case CREEPER_HEAD:
							p.closeInventory();
							p.spigot().sendMessage(Utils.getClickable(Main.PREFIX +
											"Du möchtest Freunde hinzufügen§8? [§a§lKlick§8]", ClickEvent.Action.SUGGEST_COMMAND,
									"/friend add <name>", "§7§oKlicke hier, um ein Command Preview zu erhalten."));
						default:
							return;
					}
				}
				switch (item.getType()) {
					case RED_CANDLE -> p.closeInventory();
					case PLAYER_HEAD -> {
						cleanProfile(p);
						friendTab(p);
					}
					case SPYGLASS -> {
						cleanProfile(p);
						statisticsTab(p);
					}
					case WITHER_ROSE -> {
						p.sendMessage("Role-Play Menü");
						cleanProfile(p);
					}
					default -> {
					}
				}
			}
		}
	}

	public void friendTab(Player p) {
		Inventory in = p.getOpenInventory().getTopInventory();

		FriendEngine fe = FriendEngine.loadCon(p);
		List<String> friends = fe.getFriends(),
				friendsName = fe.getFriendsName();

		if (friends == null || friends.size() <= 0) {
			in.setItem(in.getSize() / 2, IB.lore(IB.name(new ItemStack(Material.CREEPER_HEAD),
					"§c§oDu hast keine Freunde"), " ", "§eFüge doch welche hinzu!", "  §8» §6/friend add <name>"));
			return;
		}

		int i = 0;
		for (int z = 0; z < in.getSize(); z++) {
			if (allowedSlot(z, in.getSize())) {
				OfflinePlayer tar = Bukkit.getOfflinePlayer(UUID.fromString(friends.get(i)));
				if (tar.getName() != null)
					in.setItem(z, IB.name(playerSkull(new ItemStack(Material.PLAYER_HEAD), tar), "§7" + tar.getName()));
				else
					in.setItem(z, IB.name(new ItemStack(Material.SKELETON_SKULL), "§8" + friendsName.get(i)));
				i++;
				if (i >= friends.size() || i >= friendsName.size()) return;
			}
		}
	}

	public boolean allowedSlot(final int i, final int size) {
		return i < size && ((i >= 10 && i < 17) || (i >= 19 && i < 26) || (i >= 28 && i < 35));
	}

	public void statisticsTab(Player p) {
		Inventory in = p.getOpenInventory().getTopInventory();
		in.setItem(10, playerSkull(IB.lore(IB.name(new ItemStack(Material.PLAYER_HEAD),
				"§7Dies ist dein Profil:"), " §0» "), p));

		PConfig pc = PConfig.loadConfig(p);
		in.setItem(20, IB.lore(IB.name(new ItemStack(Material.DIAMOND_SWORD),
				"§bKills an Spieler:innen"), String.format("§b§o%s", pc.getSafeInt("Player.stats.playerKills"))));
		in.setItem(21, IB.lore(IB.name(new ItemStack(Material.IRON_SWORD),
				"§3Mob Kills"), String.format("§3§o%s", pc.getSafeInt("Player.stats.mobKills"))));
		in.setItem(22, IB.lore(IB.name(new ItemStack(Material.SHIELD),
				"§6Deaths"), String.format("§6§o%s", pc.getSafeInt("Player.stats.deaths"))));
	}

	public void cleanProfile(Player p) {
		if (p.getOpenInventory().getTitle().equals(profileInvTitle)) {
			ItemStack[] contents = Profil.pi.getContents();
			p.getOpenInventory().getTopInventory().setContents(contents);
		}
	}

	public static ItemStack playerSkull(ItemStack i, OfflinePlayer t) {
		if (i.getType() != Material.PLAYER_HEAD) i.setType(Material.PLAYER_HEAD);
		SkullMeta sm = (SkullMeta) i.getItemMeta();
		if (sm.setOwningPlayer(t))
			if (i.setItemMeta(sm)) return i;
		return new ItemStack(Material.SKELETON_SKULL);
	}
}


