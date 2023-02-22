package de.simplex.horizon;

import de.simplex.horizon.commandsOLD.api.MessageEngine;
import de.simplex.horizon.commandsOLD.fun.Friede;
import de.simplex.horizon.commandsOLD.moderation.*;
import de.simplex.horizon.commandsOLD.utility.*;
import de.simplex.horizon.systemsOLD.*;
import de.simplex.horizon.systemsOLD.materialLists.BMp;
import de.simplex.horizon.systemsOLD.materialLists.Mp;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {

	public static Main INSTANCE;

	public static final String PREFIX = "<dark_grey>» <#158BCE>System <dark_grey>┃ <grey>",
			NO_PERMISSION = "Du hast <dark_red>keinen Zugriff <grey>auf diesen Befehl.",
			COMMAND_NOT_FOUND = "Dieser Befehl existiert nicht.",
			NOT_A_PLAYER = "Dieser Befehl ist nur für Spieler:innen zulässig.",
			ARGS_TOO_LONG = "Diese Argumentenlänge ist Ungültig";
	public final MessageEngine ME = new MessageEngine(this);

	public static HashMap<String, Integer> balanceTop = new HashMap<String, Integer>();
	private BukkitAudiences adventure;

	public @NonNull BukkitAudiences adventure() {
		if (this.adventure == null) {
			throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
		}
		return this.adventure;
	}

	@Override
	public void onEnable() {

		this.adventure = BukkitAudiences.create(this);
		INSTANCE = this;

		getCommand("friede").setExecutor(new Friede(this));

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
		getCommand("clearChat").setExecutor(new ClearChat());

		getCommand("Profil").setExecutor(new Profil());
		getCommand("UpdateVisuals").setExecutor(new UpdateVisuals());
		getCommand("ShopNpc").setExecutor(new ShopNpc());

		final PluginManager pM = Bukkit.getPluginManager();
		pM.registerEvents(new Connection(this), this);
		pM.registerEvents(new Chat(this), this);
		pM.registerEvents(new WorldChanged(), this);
		pM.registerEvents(new Enderchest(), this);
		pM.registerEvents(new Profil(), this);
		pM.registerEvents(new StatsListener(), this);
		pM.registerEvents(new ShopNpc(), this);
		pM.registerEvents(new DisableEvent(), this);

		saveDefaultConfig();
		getConfig().options().copyDefaults(true);

		Profil.fillProfil();
		genCurrentBalance();

		ShopNpc.fillSHopNpcInv();

		Tablist Tablist = new Tablist();

		for (Player ap : Bukkit.getOnlinePlayers()) {
			Scoreboard.defaultSb(ap);
			Tablist.setTl(ap);
		}

		if (!BMp.BMpExists()) {
			BMp.createBMp();
			BMp bp = new BMp();
			bp.setDefaults();
			bp.saveBMp();
		}
		if (!Mp.mpExists()) {
			Mp.createMp();
			Mp mp = new Mp();
			mp.setDefaults();
			mp.saveMp();
		}
		Mp.loadMp();
		Mp.genMaterialList();

		ME.sendTo(PREFIX + "<green>Core aktiviert!", this.adventure().console());
	}

	/**
	 * Deaktivierungslogik
	 */
	@Override
	public void onDisable() {
		if (this.adventure != null) {
			this.adventure.close();
			this.adventure = null;
		}

		ME.sendTo(PREFIX + "<dark_red>Core deaktiviert!", this.adventure().console());
		for (Player ap : Bukkit.getOnlinePlayers())
			Scoreboard.unsetScoreboard(ap);
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
			if (op.hasPlayedBefore() && PlayerConfig.hasConfig(op)) {
				PlayerConfig opC = PlayerConfig.loadConfig(op);
				cTokens = opC.getTokens();
			} else
				continue;
			balanceTop.put(op.getUniqueId().toString(), cTokens);
		}
		List<String> sort1 = new ArrayList<>(balanceTop.keySet());
		Collections.sort(sort1);

		if (sort1.size() < 5) {
			ME.sendTo(PREFIX + "<yellow>Bal-Top konnte nicht ermittelt werden. <red>Zu wenig Daten!", this.adventure().console());
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
