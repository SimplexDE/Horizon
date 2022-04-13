package de.simplex.horizoncore.systems.materialLists;

import de.simplex.horizoncore.systems.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mp {
	static YamlConfiguration con;
	File file;

	public static List<Material> materials = new ArrayList<>();

	public static void genMaterialList() {
		if (mpExists())
			for (String s : con.getConfigurationSection("Materials").getKeys(false)) {
				materials.add(Material.getMaterial(s.toUpperCase()));
			}
		else {
			Mp.createMp();
			System.out.println("§6[Horizon] Die Material-List wurde generiert. Bitte starte das Plugin neu!");
		}
	}


	public Mp() {
		this.file = new File("plugins/Horizon/Materials.yml");
		Mp.con = YamlConfiguration.loadConfiguration(this.file);
	}

	public YamlConfiguration getMp() {
		return Mp.con;
	}

	public File getFile() {
		return this.file;
	}

	public static Mp loadMp() {
		if (!mpExists()) {
			createMp();
		}
		final Mp Mpc = new Mp();
		return Mpc;
	}

	public static boolean mpExists() {
		if (!BMp.BMpExists())
			BMp.createBMp();
		return new File("plugins/Horizon/Materials.yml").exists();
	}

	public static Mp createMp() {
		final File file = new File("plugins/Horizon/Materials.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				if (!file.exists()) {
					System.out.println("§c[Horizon] Beim Erstellen der Material-List ist ein Fehler aufgetreten. [0]");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		final Mp Mpc = new Mp();
		return Mpc;
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
		System.out.println("§e[Horizon] Die standard Werte für die Material-List wurden generiert.");
	}

	public void saveMp() {
		try {
			Mp.con.save(this.file);
		} catch (IOException e) {
			System.out.println("§c[Horizon] Ein Fehler ist beim Speichern der Material-List aufgetreten. " +
					"Überprüfe die Datei, um dich zu vergewissern, dass diese Nachricht nicht fehlerhaft ist. [2]");
		}
	}

	public void saveMpErrorFree() {
		try {
			Mp.con.save(this.file);
		} catch (IOException ignored) {
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
		return Mp.con.getConfigurationSection(s);
	}

	public void setTrue(final String string) {
		Mp.con.set(string, (Object) true);
	}

	public void setFalse(final String string) {
		Mp.con.set(string, (Object) false);
	}

	public void setNull(final String string) {
		Mp.con.set(string, (Object) null);
	}

	public boolean isSet(final String string) {
		return Mp.con.isSet(string);
	}

	public boolean getBoolean(final String string) {
		return Mp.con.getBoolean(string);
	}

	public void set(final String string, final Object arg1) {
		Mp.con.set(string, arg1);
	}

	public String getString(final String string) {
		return Mp.con.getString(string);
	}

	public List<String> getStringList(final String string) {
		return (List<String>) Mp.con.getStringList(string);
	}

	public float getFloat(final String string) {
		return (float) Mp.con.getDouble(string);
	}

	public int getInt(final String string) {
		return Mp.con.getInt(string);
	}

	public long getLong(final String string) {
		return Mp.con.getLong(string);
	}

	public Set<String> getKeys(final boolean deep) {
		return (Set<String>) Mp.con.getKeys(deep);
	}

	public double getDouble(final String string) {
		return Mp.con.getDouble(string);
	}
}
