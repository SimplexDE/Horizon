package de.simplex.horizon.systemsOLD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChanged implements Listener {

	@EventHandler
	public void handle(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
        Scoreboard.updateWorld(p);
	}
}
