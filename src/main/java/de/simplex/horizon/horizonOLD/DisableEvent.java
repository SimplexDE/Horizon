package de.simplex.horizon;

import de.simplex.horizon.systemsOLD.materialLists.BMp;
import de.simplex.horizon.systemsOLD.materialLists.Mp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.ArrayList;

public class DisableEvent implements Listener {

	@EventHandler
	public void handle(PluginDisableEvent e) {
		if (e.getPlugin() == Main.getPlugin()) {
			for (Player ap : Bukkit.getOnlinePlayers())
				ap.closeInventory();
			if (Mp.mpExists() && BMp.BMpExists()) {
				BMp bp = new BMp();
				Mp mp = new Mp();
				if (bp.getMatPrice(Material.STONE) > 0.0D) {
					ArrayList<Material> l = new ArrayList<>();
					for (Material mat : Material.values()) {
						if (mat.isBlock()) {
							String s = mat.toString();
							if (!s.contains("SLAB") && !s.contains("STRIPPED") && !s.contains("PRESSURE_PLATE") && !s.contains("FENCE") &&
									!s.contains("DOOR") && !s.contains("COMMAND") && !s.contains("WALL") && !s.contains("PANE") &&
									!s.contains("BANNER") && !s.contains("AIR") && !s.contains("BUTTON") && !s.contains("STAIR") &&
									!s.contains("INFESTED") && !s.contains("CONCRETE") && !s.contains("CARPET"))
								l.add(mat);
						}
					}
					for (Material s : l) {
						mp.set("Materials." + s.toString().toLowerCase() + ".Mat", s.toString().toUpperCase());
						mp.set("Materials." + s.toString().toLowerCase() + ".price", Double.valueOf(bp.getMatPrice(s)));
						mp.set("Materials." + s.toString().toLowerCase() + ".dName", bp.getMatName(s));
					}
					mp.saveMp();
				}
			}
		}
	}
}
