package de.simplex.horizon.systemsOLD;

import de.simplex.horizon.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Das Tab-List-System
 *
 * @author Simplex
 * @since 1.0-Beta
 */
public class Tablist {

    private static final File file = new File("plugins/Horizon/playerData.yml");
    private static final YamlConfiguration pD = YamlConfiguration.loadConfiguration(file);
    private static Tablist Tl;
    private static Main main;
    public Tablist() {
        Tablist.main = main;
    }

    public void setTl(Player player) {

        Audience audience = (Audience) player;
        final Component header = MiniMessage.miniMessage().deserialize("<newline>  <rainbow>ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ</rainbow>  <newline>");
        final Component footer = MiniMessage.miniMessage().deserialize("<newline><#ec270b>MS-22");
        audience.sendPlayerListHeaderAndFooter(header, footer);

    }

}