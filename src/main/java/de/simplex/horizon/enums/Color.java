package de.simplex.horizon.enums;

public enum Color {
    BLACK("#404040"),
    DARK_GRAY("#666666"),
    GRAY("#8c8c8c"),
    LIGHT_GRAY("#b3b3b3"),
    DARK_BLUE("#1c32b0"),
    BLUE("#3852e0"),
    LIGHT_BLUE("#7b8bea"),
    DARK_AQUA("#00b3b3"),
    AQUA("#00ffff"),
    DARK_PURPLE("#9016b6"),
    PURPLE("#bc33e6"),
    LIGHT_PURPLE("#d277ee"),
    DARK_PINK("#b300b3"),
    PINK("#ff00ff"),
    DARK_RED("#a60c0c"),
    RED("#ed1212"),
    LIGHT_RED("#f35959"),
    ORANGE("#f29026"),
    YELLOW("#f4e818"),
    LIGHT_YELLOW("#f7ef55"),
    DARK_GREEN("#13bf0d"),
    GREEN("#2ff028"),
    LIGHT_GREEN("#74f570"),
    WHITE("#ffffff");

    private String c;

    Color(String c) {
        this.c = c;
    }

    public String getColorMiniMessage() {
        return "<" + c + ">";
    }

    public String getColor() {
        return c;
    }

}