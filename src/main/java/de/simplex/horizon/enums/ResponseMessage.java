package de.simplex.horizon.enums;

public enum ResponseMessage {

	SYSTEM(Color.DARK_AQUA.getColorMiniMessage() + "sʏsᴛᴇᴍ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.AQUA.getColorMiniMessage()),
	HORIZON("<rainbow>ʜᴏʀɪᴢᴏɴ</rainbow> " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage()),
	INFO(Color.BLUE.getColorMiniMessage() + "ɪɴғᴏ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_BLUE.getColorMiniMessage()),
	WARN(Color.DARK_ORANGE.getColorMiniMessage() + "ᴡᴀʀɴɪɴɢ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.ORANGE.getColorMiniMessage()),
	ERROR(Color.RED.getColorMiniMessage() + "ᴇʀʀᴏʀ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_RED.getColorMiniMessage());

	private String c;

	ResponseMessage(String c) {
		this.c = c;
	}

	public String getNotification() {
		return c;
	}
}
