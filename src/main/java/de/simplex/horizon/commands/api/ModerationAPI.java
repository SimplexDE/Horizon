package de.simplex.horizon.commands.api;

import de.simplex.horizon.Main;
import de.simplex.horizon.systems.PlayerConfig;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ModerationAPI, zur Vereinfachung der Moderation-Verwaltung
 *
 * @author Simplex
 * @version 1.0
 * @since 1.0-Beta
 */

public class ModerationAPI {

    /**
     * Die Spieler-IP abfragen
     *
     * @param target Spieler, dessen IP abgefragt werden soll.
     * @return Spieler-ID
     */
    public static String getPlayerIP(OfflinePlayer target) {
        PlayerConfig pC = PlayerConfig.loadConfig(target);
        return pC.getString(target.getUniqueId() + ".IP-ADDRESS"); // IP-Adresse aus der playerData.yml ziehen (Der Spieler musste mind. einmal auf dem Server gewesen sein.)
    }

    /**
     * Einen Ban erstellen
     *
     * @param operator ausführender Nutzer
     * @param target   Ziel-Spieler
     * @param reason   Der Ban-Grund
     */
    public static void BanPlayer(CommandSender operator, OfflinePlayer target, String reason) {
        String IP = getPlayerIP(target);
        PlayerConfig pC = PlayerConfig.loadConfig(target);

        if (IP != null) {

            if (!target.isBanned()) {

                if (Bukkit.getPlayer(target.getUniqueId()) != null) { // Überprüfen ob, der Spieler online ist

                    Player onlinePlayer = (Bukkit.getPlayer(target.getUniqueId())); // Den Spieler zur einer Variable machen.
                    onlinePlayer.kickPlayer("§7Du wurdest §cgebannt§7.\n§7Grund: §e" + reason + "\n§7Wir wünschen dir noch viel Spaß auf MyPvP, ohne Horizon."); // Spieler kicken, und informieren darüber, das er Gebannt wurde und weswegen
                    Bukkit.getBanList(BanList.Type.IP).addBan(IP, reason, null, null); // Den IP-Ban erstellen
                    Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, null); // Den Name-Ban erstellen

                    pC.set("ban_state.banned_reason", reason); // Einen Vermerk im Player Verzeichnis erstellen
                    pC.set("ban_state.last_banned_reason", reason); // Einen Vermerk im Player Verzeichnis erstellen
                    pC.save();

                    operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7wurde gebannt."); // Spieler wurde gebannt - Rückmeldung#

                } else {
                    Bukkit.getBanList(BanList.Type.IP).addBan(IP, reason, null, null); // Den IP-Ban erstellen
                    Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, null); // Den Name-Ban erstellen

                    pC.set("ban_state.banned_reason", reason); // Einen Vermerk im Player Verzeichnis erstellen
                    pC.set("ban_state.last_banned_reason", reason); // Einen Vermerk im Player Verzeichnis erstellen
                    pC.save();

                    operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7wurde gebannt."); // Spieler wurde gebannt - Rückmeldung
                }
            } else {

                operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7ist bereits gebannt.");
            }

        } else {

            operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7konnte nicht gebannt werden.");
        }
    }

    /**
     * Einen Ban aufheben
     *
     * @param operator ausführender Nutzer
     * @param target   Ziel-Spieler
     */
    public static void UnbanPlayer(CommandSender operator, OfflinePlayer target) {
        String IP = getPlayerIP(target);
        PlayerConfig pC = PlayerConfig.loadConfig(target);

        if (IP != null) { // Überprüfen ob, die IP gefunden wurde.

            if (target.isBanned()) { // Überprüfen ob, die IP gebannt ist.

                Bukkit.getBanList(BanList.Type.IP).pardon(IP); // Den Spieler entbannen auf IP-Ebene
                Bukkit.getBanList(BanList.Type.NAME).pardon(target.getName()); // Den Spieler entbannen auf Name-Ebene

                pC.set(target.getUniqueId() + ".BANNED_REASON", null); // Den Ban-Grund Vermerk in der playerData.yml löschen
                pC.save();

                operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7wurde entbannt.");

            } else {

                operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7ist nicht gebannt.");
            }

        } else {

            operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7konnte nicht entbannt werden.");
        }

    }

    /**
     * Einen Spieler vom Server werfen
     *
     * @param operator ausführender Nutzer
     * @param target   Ziel-Spieler
     * @param reason   Kick-Grund
     */
    public static void KickPlayer(CommandSender operator, Player target, String reason) {
        PlayerConfig pC = PlayerConfig.loadConfig(target);

        pC.set("ban_state.kicked", true); // Einen Vermerk im Player Verzeichnis erstellen
        pC.set("ban_state.kicked_reason", reason);
        pC.save();

        Player onlinePlayer = (Bukkit.getPlayer(target.getName())); // Den Spieler als Variable Erstellen
        assert onlinePlayer != null;
        onlinePlayer.kickPlayer(("§7Du wurdest §cgekickt§7.\n§7Grund: §e" + reason + "")); // Den Spieler kicken mit einer Custom Nachricht, mit Reason(Grund)
        operator.sendMessage(Main.PREFIX + "§e" + target.getName() + " §7wurde gekickt."); // Dem Ausführenden die erfolgreiche Rückmeldung geben, das der Spieler gekickt wurde

    }

}
