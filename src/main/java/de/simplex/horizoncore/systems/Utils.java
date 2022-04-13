package de.simplex.horizoncore.systems;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Utils {

	public static String genSetColor(int i) {
		if (i > 13)
			if (i % 2 == 1)
				i = i / 2 + 1;
			else
				i /= 2;
		if (i >= 0 || i <= 13) {
			switch (i) {
				case 0:
					return "2";
				case 1:
					return "a";
				case 2:
					return "e";
				case 3:
					return "6";
				case 4:
					return "4";
				case 5:
					return "c";
				case 6:
					return "d";
				case 7:
					return "5";
				case 8:
					return "1";
				case 9:
					return "9";
				case 10:
					return "3";
				case 11:
					return "b";
				case 12:
					return "7";
			}
			return "f";
		}
		return null;
	}

	public static void bcToWorld(String msg, World world) {
		for (Player ap : Bukkit.getOnlinePlayers()) {
			if (ap.getWorld().getName().equals(world.getName()))
				ap.sendMessage(msg);
		}
	}
}
