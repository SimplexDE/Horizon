package de.simplex.horizoncore.systems;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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

}
