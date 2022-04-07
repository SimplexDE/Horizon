package de.simplex.horizoncore;

import de.simplex.horizoncore.commands.fun.Friede;
import de.simplex.horizoncore.commands.moderation.*;
import de.simplex.horizoncore.commands.utility.*;
import de.simplex.horizoncore.systems.Chat;
import de.simplex.horizoncore.systems.Connection;
import de.simplex.horizoncore.systems.PConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {

	public static Main INSTANCE;

	public static HashMap<String, Integer> balanceTop = new HashMap<String, Integer>();

	public static final String PREFIX = "§8» §bSystem §8┃ §7",
			NO_PERMISSION = "Du hast §4keinen Zugriff §7auf diesen Befehl.",
			COMMAND_NOT_FOUND = "Dieser Befehl existiert nicht.",
			NOT_A_PLAYER = "Dieser Befehl ist nur für Spieler:innen zulässig.";

	@Override
	public void onEnable() {

		INSTANCE = this;

		getCommand("friede").setExecutor(new Friede());

		getCommand("broadcast").setExecutor(new Broadcast());
		getCommand("whisper").setExecutor(new Whisper());
		getCommand("maintenance").setExecutor(new Maintenance());
		getCommand("gamemode").setExecutor(new Gamemode());
		getCommand("globalmute").setExecutor(new ChatGlobalMute());
		getCommand("teamchat").setExecutor(new Teamchat());
		getCommand("flight").setExecutor(new Flight());
		getCommand("difficulty").setExecutor(new Difficulty());
		getCommand("achievements").setExecutor(new Achievements());
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("Enderchest").setExecutor(new Enderchest());
		getCommand("Tokens").setExecutor(new Tokens());
		getCommand("BalanceTop").setExecutor(new BalanceTop());
		getCommand("sethome").setExecutor(new SetHome());

		getCommand("eban").setExecutor(new Ban());
		getCommand("eunban").setExecutor(new Unban());
		getCommand("ekick").setExecutor(new Kick());

		final PluginManager pM = Bukkit.getPluginManager();
		pM.registerEvents(new Connection(), this);
		pM.registerEvents(new Chat(), this);
		pM.registerEvents(new Enderchest(), this);

		saveDefaultConfig();
		getConfig().options().copyDefaults(true);

		Bukkit.getConsoleSender().sendMessage(PREFIX + "§cCore aktiviert!");

		genCurrentBalance();
	}

	/**
	 * Deaktivierungslogik
	 */
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(PREFIX + "§cCore deaktiviert!");
	}

	public static Main getPlugin() {
		return INSTANCE;
	}

	/**
	 * Ungetestet!
	 */
	public void genCurrentBalance() {
		FileConfiguration c = INSTANCE.getConfig();

		for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
			int cTokens;
			if (op.hasPlayedBefore() && PConfig.hasConfig(op)) {
				PConfig opC = PConfig.loadConfig(op);
				cTokens = opC.getTokens();
			} else
				continue;
			balanceTop.put(op.getUniqueId().toString(), cTokens);
		}
		List<String> sort1 = new ArrayList<>(balanceTop.keySet());
		Collections.sort(sort1);

		if (sort1.size() < 5) {
			Bukkit.getConsoleSender().sendMessage(PREFIX + "§6BalTop konnte nicht ermittelt werden!" +
					" Zu wenig Daten.!");
			return;
		}

		for (int i = 0; i <= 5; i++) {
			c.set("Balance.balanceTop." + i + ".uuid", balanceTop.get(sort1.get(i)));
			c.set("Balance.balanceTop." + i + ".name", Bukkit.getOfflinePlayer(balanceTop.get(sort1.get(i)).toString()));
			c.set("Balance.balanceTop." + i + ".tokens", sort1.get(i));
		}
		c.set("Balance.lastCalculated", System.currentTimeMillis());

		saveConfig();
	}
}
