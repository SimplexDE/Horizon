package de.simplex.horizon.listener;

import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.util.RankManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsListener {
	public LuckPermsListener(LuckPerms luckPerms) {
		EventBus eventBus = luckPerms.getEventBus();

		eventBus.subscribe(Horizon.getHorizon(), NodeMutateEvent.class, this::onNodeMutate);
	}

	private void onNodeMutate(NodeMutateEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			RankManager.assignRank(p);
		}
	}
}
