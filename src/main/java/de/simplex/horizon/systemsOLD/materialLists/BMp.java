package de.simplex.horizon.systemsOLD.materialLists;

import de.simplex.horizon.systemsOLD.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BMp {
	static YamlConfiguration con;
	File file;

	public BMp() {
		this.file = new File("plugins/Horizoncore/Materials_Backup.yml");
		BMp.con = YamlConfiguration.loadConfiguration(this.file);
	}

	public YamlConfiguration getBMp() {
		return BMp.con;
	}

	public File getFile() {
		return this.file;
	}

	public static BMp loadBMp() {
		if (!BMpExists()) {
			createBMp();
		}
		return new BMp();
	}

	public static boolean BMpExists() {
		return new File("plugins/Horizoncore/Materials_Backup.yml").exists();
	}

	public static BMp createBMp() {
		final File file = new File("plugins/Horizoncore/Materials_Backup.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				if (!file.exists()) {
					Bukkit.getConsoleSender().sendMessage("§c[Horizon] Beim Erstellen der Backup Material-List ist ein Fehler aufgetreten. [0]");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new BMp();
	}

	public void setDefaults() {
		final ArrayList<Material> mp = new ArrayList<Material>();
		for (final Material mat : Material.values()) {
			if (mat.isBlock()) {
				final String s = mat.toString();
				if (!s.contains("SLAB") && !s.contains("STRIPPED") && !s.contains("PRESSURE_PLATE") && !s.contains("FENCE") && !s.contains("DOOR") && !s.contains("COMMAND") && !s.contains("WALL") && !s.contains("PANE") && !s.contains("BANNER") && !s.contains("AIR") && !s.contains("BUTTON") && !s.contains("STAIR") && !s.contains("INFESTED") && !s.contains("CONCRETE") && !s.contains("CARPET")) {
					mp.add(mat);
				}
			}
		}
		int i = 0;
		for (Material s : mp) {
			con.set("Materials." + s.toString().toLowerCase() + ".Mat", s.toString().toUpperCase());
			con.set("Materials." + s.toString().toLowerCase() + ".price", 250.0D);
			con.set("Materials." + s.toString().toLowerCase() + ".dName", "\u00A7" + Utils.genSetColor(i) + s.toString().toLowerCase());
			i++;
		}
		Bukkit.getConsoleSender().sendMessage("§e[Horizon] Die standard Werte für die Backup Material-List wurden generiert.");
	}

	public void saveBMp() {
		try {
			BMp.con.save(this.file);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§c[Horizon] Ein Fehler ist beim Speichern der Backup Material-List aufgetreten. " +
					"Überprüfe die Datei, um dich zu vergewissern, dass diese Nachricht nicht fehlerhaft ist. [2]");
		}
	}

	public void saveBMpErrorFree() {
		try {
			BMp.con.save(this.file);
		} catch (IOException ex) {
		}
	}

	public boolean isSet(Material m) {
		return con.isSet("Materials." + m.toString().toLowerCase());
	}

	public int getMatPrice(Material m) {
		return con.getInt("Materials." + m.toString().toLowerCase() + ".price");
	}

	public void setMatPrice(Material m, int p) {
		con.set("Materials." + m.toString().toLowerCase() + ".price", p);
	}

	public String getMatName(Material m) {
		return con.getString("Materials." + m.toString().toLowerCase() + ".dName");
	}

	public void setMatName(Material m, String s) {
		con.set("Materials." + m.toString().toLowerCase() + ".dName", s);
	}

	public ConfigurationSection getConfigurationSection(final String s) {
		return BMp.con.getConfigurationSection(s);
	}

	public void setTrue(final String string) {
		BMp.con.set(string, (Object) true);
	}

	public void setFalse(final String string) {
		BMp.con.set(string, (Object) false);
	}

	public void setNull(final String string) {
		BMp.con.set(string, (Object) null);
	}

	public boolean isSet(final String string) {
		return BMp.con.isSet(string);
	}

	public boolean getBoolean(final String string) {
		return BMp.con.getBoolean(string);
	}

	public void set(final String string, final Object arg1) {
		BMp.con.set(string, arg1);
	}

	public String getString(final String string) {
		return BMp.con.getString(string);
	}

	public List<String> getStringList(final String string) {
		return (List<String>) BMp.con.getStringList(string);
	}

	public float getFloat(final String string) {
		return (float) BMp.con.getDouble(string);
	}

	public int getInt(final String string) {
		return BMp.con.getInt(string);
	}

	public long getLong(final String string) {
		return BMp.con.getLong(string);
	}

	public Set<String> getKeys(final boolean deep) {
		return (Set<String>) BMp.con.getKeys(deep);
	}

	public double getDouble(final String string) {
		return BMp.con.getDouble(string);
	}
}
