package de.simplex.horizon.commands.utility;

import de.simplex.horizon.horizon.Horizon;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class RankAssigning {

    public static void setupColors() {
        Horizon.getHorizon().RankColors.put("admin", "<#e81a1f>");
        Horizon.getHorizon().RankColors.put("manager", "<#d81f43>");
        Horizon.getHorizon().RankColors.put("developer", "<#2ad19d>");
        Horizon.getHorizon().RankColors.put("content", "<#2390da>");
        Horizon.getHorizon().RankColors.put("moderator", "<#d3c334>");
        Horizon.getHorizon().RankColors.put("supporter", "<#ede54c>");
        Horizon.getHorizon().RankColors.put("spieler", "<#8c8c8c>");
    }


    public static boolean assignRank(Player player) {

        if (Horizon.getHorizon().RankColors.isEmpty()) {
            setupColors();
        }

        User u = lpapi.getUserManager().getUser(player.getUniqueId());
        assert u != null;
        String primary = u.getPrimaryGroup();

        Horizon.getHorizon().PlayerRanks.remove(player.getUniqueId());

        if (primary.equals("admin")) {
            Horizon.getHorizon().PlayerRanks.put(player.getUniqueId(), "admin");
            Horizon.getHorizon().RankColors.put("admin", "<#e81a1f>");
        } else if (primary.equals("manager")) {
            Horizon.getHorizon().PlayerRanks.put(player.getUniqueId(), "manager");
            Horizon.getHorizon().RankColors.put("manager", "<#d81f43>");
        } else if (primary.equals("developer")) {
            Horizon.getHorizon().PlayerRanks.put(player.getUniqueId(), "developer");
            Horizon.getHorizon().RankColors.put("developer", "<#2ad19d>");
        } else if (primary.equals("content")) {
            Horizon.getHorizon().PlayerRanks.put(player.getUniqueId(), "content");
            Horizon.getHorizon().RankColors.put("content", "<#2390da>");
        } else if (primary.equals("moderator")) {
            Horizon.getHorizon().PlayerRanks.put(player.getUniqueId(), "moderator");
            Horizon.getHorizon().RankColors.put("moderator", "<#d3c334>");
        } else if (primary.equals("supporter")) {
            Horizon.getHorizon().PlayerRanks.put(player.getUniqueId(), "supporter");
            Horizon.getHorizon().RankColors.put("supporter", "<#ede54c>");
        } else {
            Horizon.getHorizon().PlayerRanks.put(player.getUniqueId(), "spieler");
            Horizon.getHorizon().RankColors.put("spieler", "<#8c8c8c>");
        }

        new TabListCompiler().setNameTag(player, Horizon.getHorizon().PlayerRanks.get(player.getUniqueId()));
        return true;
    }
}
