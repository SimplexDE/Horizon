package de.simplex.horizon.enums;

public enum NotificationPrefixes {
    // sʏsᴛᴇᴍ ʜᴏʀɪᴢᴏɴ ɪɴғᴏ ᴡᴀʀɴᴜɴɢ ғᴇʜʟᴇʀ

    SYSTEM(Color.AQUA.getColorMiniMessage() + "sʏsᴛᴇᴍ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage()),
    HORIZON("<rainbow>ʜᴏʀɪᴢᴏɴ</rainbow> " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage()),
    INFO(Color.LIGHT_BLUE.getColorMiniMessage() + "ɪɴғᴏ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage()),
    WARN(Color.ORANGE.getColorMiniMessage() + "ᴡᴀʀɴᴜɴɢ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage()),
    ERROR(Color.RED.getColorMiniMessage() + "ғᴇʜʟᴇʀ " + Color.DARK_GRAY.getColorMiniMessage() + "| " + Color.LIGHT_GRAY.getColorMiniMessage());

    private String c;

    NotificationPrefixes(String c) {
        this.c = c;
    }

    public String getNotification() {
        return c;
    }
}
