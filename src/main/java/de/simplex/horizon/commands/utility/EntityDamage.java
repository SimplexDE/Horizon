package de.simplex.horizon.commands.utility;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent e) {

		if (e.getEntity() instanceof Villager v) {
			if (v.getCustomName() != null & v.getCustomName().equals(ShopNpc.s)) {

				e.setCancelled(true);
				if (e instanceof EntityDamageByEntityEvent en) {

					if (en.getDamager() instanceof Player attacker)
						attacker.playSound(attacker.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.25f, 1.1f);
				}
			}
		}
	}
}
