package de.simplex.horizon.enums;

public enum Notification {

    SYSTEM(Color.AQUA.getColorMiniMessage() + "sʏsᴛᴇᴍ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage()),
    HORIZON("<rainbow>ʜᴏʀɪᴢᴏɴ</rainbow> " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage()),
    INFO(Color.LIGHT_BLUE.getColorMiniMessage() + "ɪɴғᴏ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_BLUE.getColorMiniMessage()),
    WARN(Color.ORANGE.getColorMiniMessage() + "ᴡᴀʀɴɪɴɢ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.ORANGE.getColorMiniMessage()),
    ERROR(Color.RED.getColorMiniMessage() + "ᴇʀʀᴏʀ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_RED.getColorMiniMessage());

    private String c;

    Notification(String c) {
        this.c = c;
    }

    public String getNotification() {
        return c;
    }
}
