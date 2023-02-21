package de.simplex.horizon.systems;

import de.simplex.horizon.Main;
import de.simplex.horizon.commands.api.AchievementEngine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Das Connection-System
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Connection implements Listener {

    private final Main Main;

    public Connection(de.simplex.horizon.Main main) {
        this.Main = main;
    }

    @EventHandler(ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent event) {
        String message = "\n§9Horizon §7befindet sich aktuell in §cWartungsarbeiten§7.\n\n§7Mehr §6Informationen" +
                " \n§7auf unserem §9Discord§7:\n§ahttps://discord.gg/";

        if (Main.getPlugin().getConfig().getBoolean("MAINTENANCE")) {
            Player p = event.getPlayer();
            if (!(p.hasPermission("core.maintenance.bypass"))) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
				Bukkit.broadcastMessage("§8» §6✕ §8┃ §e" + p.getName() + "§7 versuchte Beizutreten.");
			} else {
                AchievementEngine.activateAchievement(p, "I_WAS_HERE_BEFORE_EVERYBODY");
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerConfig pC = PlayerConfig.loadConfig(player);

		if (!player.hasPlayedBefore()) {
			pC.setTokens(1000);

			PlayerInventory i = player.getInventory();
			i.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            i.addItem(new ItemStack(Material.STONE_SWORD));
            i.addItem(new ItemStack(Material.STONE_PICKAXE));
            i.addItem(new ItemStack(Material.STONE_AXE));
            i.addItem(new ItemStack(Material.STONE_SHOVEL));
            i.addItem(new ItemStack(Material.STONE_HOE));
            i.addItem(new ItemStack(Material.COOKED_BEEF));

            player.sendMessage(Main.PREFIX + "Willkommen auf Horizon! Du hast §e1000§7 Tokens und das §eStarter-Kit§7 erhalten!");
        }

        Tablist Tablist = new Tablist();

        Scoreboard.defaultSb(player);
        Tablist.setTl(player);

        event.setJoinMessage("§8» §a+ §8┃ §e" + player.getName() + "§7 ist Beigetreten.");

        Date now = new Date();
        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);

        pC.set("online", true);
        pC.set("uuid", player.getUniqueId().toString());
        if (player.getAddress() != null)
            pC.set("ip_address", player.getAddress().getHostString());
        pC.set("last_known_name", player.getName());
        pC.set("last_seen", (date.format(now)));

        if (!pC.isSet("ban_state")) {
            pC.set("ban_state.kicked", false);
            pC.set("ban_state.kick_reason", "null");
            pC.set("ban_state.banned", false);
            pC.set("ban_state.ban_reason", "null");
            pC.set("ban_state.last_banned_reason", "null");
        }
        pC.save();
        AchievementEngine.activateAchievement(player, "JOIN_HORIZON");
    }

	@EventHandler(ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("§8» §c- §8┃ §e" + event.getPlayer().getName() + "§7 ist Gegangen.");

        Player player = event.getPlayer();
        PlayerConfig pC = PlayerConfig.loadConfig(player);

        Date now = new Date();
        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);

        pC.set("online", false);
        pC.set("last_known_name", player.getName());
        pC.set("last_seen", (date.format(now)));
        pC.save();
    }
}
