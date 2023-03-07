package de.simplex.horizon.enums;

public enum AlertMessage {
    INVALID_ARGUMENT_LENGTH(ResponseMessage.ERROR.getNotification() + "Invalid argument length!"),
    INVALID_ARGUMENT(ResponseMessage.WARN.getNotification() + "Invalid argument passed!"),
    ONLY_PLAYER(ResponseMessage.ERROR.getNotification() + "Only runnable as player!"),
    PLAYER_NOT_FOUND(ResponseMessage.WARN.getNotification() + "This player doesn't exist or isn't online!"),
    NO_PERMISSION(ResponseMessage.INFO.getNotification() + "You are lacking the required permission!");

    private String msg;

    AlertMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}