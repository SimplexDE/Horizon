package de.simplex.horizon.listener;

import de.simplex.horizon.enums.ResponseMessage;
import de.simplex.horizon.method.PlayerConfig;
import de.simplex.horizon.util.MessageSender;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener {

	private final MessageSender ms = new MessageSender();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player;
		PlayerConfig playerConfig;
		GameMode gameMode;

		player = e.getPlayer();
		playerConfig = PlayerConfig.loadConfig(player);
		gameMode = player.getGameMode();

		if (gameMode == GameMode.CREATIVE) {
			if (!playerConfig.getBoolean("staff.build")) {
				ms.sendToPlayer(player, ResponseMessage.WARN.getNotification() + "Build-Mode is disabled");
				e.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player player;
		PlayerConfig playerConfig;
		GameMode gameMode;

		player = e.getPlayer();
		playerConfig = PlayerConfig.loadConfig(player);
		gameMode = player.getGameMode();

		if (gameMode == GameMode.CREATIVE) {
			if (!playerConfig.getBoolean("staff.build")) {
				ms.sendToPlayer(player, ResponseMessage.WARN.getNotification() + "Build-Mode is disabled");
				e.setCancelled(true);
			}
		}

	}
}
