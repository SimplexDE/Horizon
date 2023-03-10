package de.simplex.horizon.util;

import de.simplex.horizon.method.Sidebar;
import de.simplex.horizon.method.Tablist;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import static de.simplex.horizon.command.api.LuckPermsAPI.lpapi;

public class RankManager {


	public static void assignRank(Player p) {

		User u = lpapi.getUserManager().getUser(p.getUniqueId());

		assert u != null;
		new Tablist().setNameTag(p, u);
		Sidebar.updateRank(p);
	}
}
