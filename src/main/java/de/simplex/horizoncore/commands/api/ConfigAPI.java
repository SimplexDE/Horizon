package de.simplex.horizoncore.commands.api;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

// TODO -> Auch für die DEFAULT_CONFIG erstellen

/**
 * ConfigAPI für einfacheres Verwalten von den YML Dateien.
 *
 * @author Simplex
 * @version 0.1
 * @since 1.0-Beta
 */
public class ConfigAPI {

    private static final File playerData = new File("plugins/Horizon/playerData.yml");
    private static final YamlConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

    private static final File bannedData = new File("plugins/Horizon/bannedData.yml");
    private static final YamlConfiguration bD = YamlConfiguration.loadConfiguration(bannedData);

    /**
     * Die Config speichern
     *
     * @param file Die Anzusprechende Config (bannedData || playerData)
     */
    private static void saveConfig(File file) {
        try {
            pD.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
