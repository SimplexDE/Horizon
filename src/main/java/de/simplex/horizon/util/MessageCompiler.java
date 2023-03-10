package de.simplex.horizon.util;


import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MessageCompiler {

	public Component parseString(String string) {
		return MiniMessage.miniMessage().deserialize(string);
	}

	public void compileMessage(Audience audience, String string) {
		Component component = parseString(string);
		audience.sendMessage(component);
	}

}
