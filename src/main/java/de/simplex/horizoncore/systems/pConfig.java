package de.simplex.horizoncore.systems;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class pConfig {
	private static final String path = "plugins/Horizon/players/%s.yml",
			tP = "token";
	YamlConfiguration con;
	File file;


	public pConfig(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		this.file = new File(String.format(path, uuid));
		this.con = YamlConfiguration.loadConfiguration(this.file);
	}

	public static pConfig loadConfig(final OfflinePlayer p) {
		if (!hasConfig(p)) {
			createConfig(p);
		}
		return new pConfig(p);
	}

	public static pConfig createConfig(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		final File file = new File(String.format(path, uuid));
		if (!file.exists()) {
			try {
				if (!file.exists()) {
					final File folder = new File("plugins/Horizon/players");
					folder.createNewFile();
				}
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new pConfig(p);
	}

	public static boolean hasConfig(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		return new File(String.format(path, uuid)).exists();
	}

	public YamlConfiguration getCon() {
		return this.con;
	}

	public File getFile() {
		return this.file;
	}

	public boolean isSet(final String s) {
		return this.con.isSet(s);
	}

	public void set(final String s, Object o) {
		this.con.set(s, o);
	}

	public void save() {
		try {
			this.con.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getTokens() {
		return this.con.getInt(tP);
	}

	public void setTokens(int i) {
		this.con.set(tP, i);
	}

	public void addTokens(int i) {
		i += getTokens();
		this.con.set(tP, i);
	}

	public ConfigurationSection getConfigurationSection(final String string) {
		return this.con.getConfigurationSection(string);
	}

	public void setTrue(final String string) {
		this.con.set(string, true);
	}

	public void setFalse(final String string) {
		this.con.set(string, false);
	}

	public void setNull(final String string) {
		this.con.set(string, null);
	}

	public boolean getBoolean(final String string) {
		return this.con.getBoolean(string);
	}

	public boolean hasWorldID() {
		return this.con.isSet("pWorld.OwningID");
	}

	public String getString(final String string) {
		return this.con.getString(string);
	}

	public List<String> getStringList(final String string) {
		return (List<String>) this.con.getStringList(string);
	}

	public Location getLocation(final String loc) {
		return this.con.isSet(loc) ? this.con.getLocation(loc) : null;
	}

	public float getFloat(final String string) {
		return (float) this.con.getDouble(string);
	}

	public int getInt(final String string) {
		return this.con.getInt(string);
	}

	public long getLong(final String string) {
		return this.con.getLong(string);
	}

	public Set<String> getKeys(final boolean deep) {
		return (Set<String>) this.con.getKeys(deep);
	}

	public double getDouble(final String string) {
		return this.con.getDouble(string);
	}

}
