package de.simplex.horizoncore.commands.api;

import de.simplex.horizoncore.systems.Friend;
import de.simplex.horizoncore.systems.PConfig;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FriendEngine {

	private static final String path = "plugins/Horizoncore/players/%s_friends.yml";
	YamlConfiguration con;
	File file;

	public FriendEngine(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		this.file = new File(String.format(path, uuid));
		this.con = YamlConfiguration.loadConfiguration(this.file);
	}

	public static FriendEngine loadCon(final OfflinePlayer p) {
		if (!hasCon(p)) {
			createCon(p);
		}
		return new FriendEngine(p);
	}

	public static FriendEngine createCon(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		final File file = new File(String.format(path, uuid));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new FriendEngine(p);
	}

	public static boolean hasCon(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		return new File(String.format(path, uuid)).exists();
	}

	public YamlConfiguration getCon() {
		return this.con;
	}

	public File getFile() {
		return this.file;
	}

	public void save() {
		try {
			this.con.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFriend(Player target) {
		return con.isSet("Friends.byUuid." + target.getUniqueId().toString());
	}

	public boolean setFriend(Player target) {
		if (!isFriend(target)) {
			List<String> sl = con.getStringList("Friends.byUuid");
			sl.add(target.getUniqueId().toString());
			con.set("Friends.byUuid", sl);
			return true;
		}
		return false;
	}

	public boolean hasFriends() {
		return con.isSet("Friends.byUuid") && !con.getStringList("Friends.byUuid").isEmpty();
	}

	public List<String> getFriends() {
		if (!hasFriends()) return null;
		return con.getStringList("Friends.byUuid");
	}

	public List<String> getFriendsName() {
		if (!hasFriends()) return null;
		return con.getStringList("Friends.byName");
	}

	public int getFriendAmount() {
		if (!hasFriends()) return 0;
		return getFriends().size();
	}

	/**
	 * Ob ein/e Spieler:in bei einer Person geblockt ist
	 * target = gecheckte Person
	 */
	public boolean isBlocked(Player target) {
		return con.isSet("Blocked.byUuid." + target.getUniqueId().toString());
	}

	public boolean setBlocked(Player target) {
		if (!isBlocked(target)) {
			List<String> sl = con.getStringList("Friends.byUuid");
			sl.add(target.getUniqueId().toString());
			con.set("Friends.byUuid", sl);
			return true;
		}
		return false;
	}

	public boolean hasBlocked() {
		return con.isSet("Blocked.byUuid") && !con.getStringList("Blocked.byUuid").isEmpty();
	}

	public List<String> getBlocked() {
		if (!hasBlocked()) return null;
		return con.getStringList("Blocked.byUuid");
	}


// --------------------------


	public boolean isSet(final String s) {
		return this.con.isSet(s);
	}

	public void set(final String s, Object o) {
		this.con.set(s, o);
	}

	public boolean getBoolean(final String string) {
		return this.con.getBoolean(string);
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

	public void addOne(final String string) {
		con.set(string, (con.getInt(string) + 1));
	}

	public int getSafeInt(final String string) {
		return con.isSet(string) ? con.getInt(string) : 0;
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

	public ConfigurationSection getConfigurationSection(final String string) {
		return this.con.getConfigurationSection(string);
	}
}
