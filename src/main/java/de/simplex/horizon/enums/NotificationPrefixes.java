package de.simplex.horizon.enums;

public enum NotificationPrefixes {
    // sʏsᴛᴇᴍ ʜᴏʀɪᴢᴏɴ ɪɴғᴏ ᴡᴀʀɴᴜɴɢ ғᴇʜʟᴇʀ

    SYSTEM(Color.AQUA.getColor() + "sʏsᴛᴇᴍ " + Color.DARK_GRAY.getColor() + "| " + Color.LIGHT_GRAY.getColor()),
    HORIZON("<rainbow>ʜᴏʀɪᴢᴏɴ</rainbow> " + Color.DARK_GRAY.getColor() + "| " + Color.LIGHT_GRAY.getColor()),
    INFO(Color.LIGHT_BLUE.getColor() + "ɪɴғᴏ " + Color.DARK_GRAY.getColor() + "| " + Color.LIGHT_GRAY.getColor()),
    WARN(Color.ORANGE.getColor() + "ᴡᴀʀɴᴜɴɢ " + Color.DARK_GRAY.getColor() + "| " + Color.LIGHT_GRAY.getColor()),
    ERROR(Color.RED.getColor() + "ғᴇʜʟᴇʀ " + Color.DARK_GRAY.getColor() + "| " + Color.LIGHT_GRAY.getColor());

    private String c;

    NotificationPrefixes(String c) {
        this.c = c;
    }

    public String getNotification() {
        return c;
    }
}
