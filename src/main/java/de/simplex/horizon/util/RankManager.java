package de.simplex.horizon.util;

import de.simplex.horizon.method.Sidebar;
import de.simplex.horizon.method.Tablist;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import static de.simplex.horizon.commands.api.LuckPermsAPI.lpapi;

public class RankManager {


    public static void assignRank(Player p) {

        User u = lpapi.getUserManager().getUser(p.getUniqueId());

        assert u != null;
        new Tablist().setNameTag(p, u);
        Sidebar.updateRank(p);

        /*User u = lpapi.getUserManager().getUser(player.getUniqueId());
        assert u != null;
        String primary = u.getPrimaryGroup();

        Horizon.getHorizon().playerRanks.remove(player.getUniqueId());

        Rank rank;
        try {
            rank = Rank.valueOf(primary.toUpperCase());
        } catch (IllegalArgumentException e) {
            rank = Rank.getDefault();
        }



        new Tablist().setNameTag(player, rank);
        Sidebar.updateRank(player);*/
    }
}
