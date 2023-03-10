package de.simplex.horizon.listener;

import de.simplex.horizon.enums.Color;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.method.ServerConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTD implements Listener {

	private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
		  .useUnusualXRepeatedCharacterHexFormat()
		  .hexColors()
		  .build();

	@EventHandler
	public void setMOTD(ServerListPingEvent e) {
		ServerConfig sC = new ServerConfig();

		Component NormalComponent = MiniMessage.miniMessage().deserialize(
			  "        <rainbow>ʜᴏʀɪᴢᴏɴ ᴍʏsᴇʀᴠᴇʀ</rainbow> "
					+ Color.DARK_GRAY.getColorMiniMessage()
					+ "⏵"
					+ Color.DARK_AQUA.getColorMiniMessage()
					+ " 1.16"
					+ Color.DARK_GRAY.getColorMiniMessage()
					+ " ⇄ "
					+ Color.AQUA.getColorMiniMessage()
					+ "1.19.3\n"
					+ Color.GREEN.getColorMiniMessage()
					+ "                     Horizon SMP");
		Component MaintenanceComponent = MiniMessage.miniMessage().deserialize(
			  "        <rainbow>ʜᴏʀɪᴢᴏɴ ᴍʏsᴇʀᴠᴇʀ</rainbow> "
					+ Color.DARK_GRAY.getColorMiniMessage()
					+ "⏵"
					+ Color.DARK_AQUA.getColorMiniMessage()
					+ " 1.16"
					+ Color.DARK_GRAY.getColorMiniMessage()
					+ " ⇄ "
					+ Color.AQUA.getColorMiniMessage()
					+ "1.19.3\n"
					+ Color.RED.getColorMiniMessage()
					+ "     Maintenance"
					+ Color.ORANGE.getColorMiniMessage()
					+ " ⚝ "
					+ Color.BLUE.getColorMiniMessage()
					+ Horizon.getHorizon().getDescription().getWebsite());
		String NormalString = legacySerializer.serialize(NormalComponent);
		String MaintenanceString = legacySerializer.serialize(MaintenanceComponent);


		if (! sC.getBoolean("server.maintenance")) {
			e.setMotd(NormalString);
			e.setMaxPlayers(70);
		} else {
			e.setMotd(MaintenanceString);
			e.setMaxPlayers(0);
		}
	}
}
