package de.simplex.horizon.enums;

public enum Messages {
    INVALID_ARGUMENT_LENGTH(Notification.ERROR.getNotification() + "Invalid argument length"),
    INVALID_ARGUMENT(Notification.WARN.getNotification() + "Invalid argument passed"),
    ONLY_PLAYER(Notification.ERROR.getNotification() + "Only runnable as player"),
    PLAYER_NOT_ONLINE(Notification.WARN.getNotification() + "This player is not online"),
    NO_PERMISSION(Notification.INFO.getNotification() + "You dont have permission for this action");

    private String msg;

    Messages(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
