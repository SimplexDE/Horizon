package de.simplex.horizoncore.systems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class StatsListener implements Listener {

	@EventHandler
	public void handle(PlayerDeathEvent e) {
		Player p = e.getEntity();
		PConfig pc = PConfig.loadConfig(p);

		pc.addOne("Player.stats.deaths");
		pc.save();
	}

	@EventHandler
	public void handle(EntityDeathEvent e) {
		Player p = e.getEntity().getKiller();
		if (p == null) return;
		PConfig pc = PConfig.loadConfig(p);

		if (e.getEntity() instanceof Player) {
			pc.addOne("Player.stats.playerKills");
		} else {
			if (Utils.isMonster(e.getEntity()))
				pc.addOne("Player.stats.mobKills");
		}
		pc.save();
	}
}
