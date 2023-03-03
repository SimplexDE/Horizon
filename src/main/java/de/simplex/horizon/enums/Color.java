package de.simplex.horizon.enums;

public enum Color {
    BLACK("<#434343>"),
    DARK_GRAY("<#6c6c6c>"),
    GRAY("<#949494>"),
    LIGHT_GRAY("<#c1c1c1>"),
    DARK_BLUE("<#182b9b>"),
    BLUE("<#213ad0>"),
    LIGHT_BLUE("<#445df1>"),
    DARK_AQUA("<#18989d>"),
    AQUA("<#23d8e0>"),
    LIGHT_AQUA("<#79e8ec>"),
    DARK_PURPLE("<#921cb6>"),
    PURPLE("<#ba29e5>"),
    LIGHT_PURPLE("<#cb64ea>"),
    DARK_PINK("<#b2189d>"),
    PINK("<#e721cc>"),
    LIGHT_PINK("<#ea62d7>"),
    DARK_RED("<#a71b1b>"),
    RED("<#ef2929>"),
    LIGHT_RED("<#f16060>"),
    ORANGE("<#f28e20>"),
    DARK_YELLOW("<#bcb318>"),
    YELLOW("<#f4e818>"),
    LIGHT_YELLOW("<#f6ed56>"),
    DARK_GREEN("<#1d9618>"),
    GREEN("<#26ef1e>"),
    LIGHT_GREEN("<#72f66d>"),
    WHITE("<#ffffff>");

    private String c;

    Color(String c) {
        this.c = c;
    }

    public String getColor() {
        return c;
    }

}

/*
public enum BaseColor {
    BLACK("<#262626>"),
    DARK_GRAY("<#404040>"),
    GRAY("<#525252>"),
    LIGHT_GRAY("<#878787>"),
    DARK_BLUE("<#182b9b>"),
    BLUE("<#213ad0>"),
    LIGHT_BLUE("<#445df1>"),
    DARK_AQUA("<#18989d>"),
    AQUA("<#23d8e0>"),
    LIGHT_AQUA("<#79e8ec>"),
    DARK_PURPLE("<#921cb6>"),
    PURPLE("<#ba29e5>"),
    LIGHT_PURPLE("<#cb64ea>"),
    DARK_PINK("<#b2189d>"),
    PINK("<#e721cc>"),
    LIGHT_PINK("<#ea62d7>"),
    DARK_RED("<#a71b1b>"),
    RED("<#ef2929>"),
    LIGHT_RED("<#f16060>"),
    ORANGE("<#f28e20>"),
    DARK_YELLOW("<#bcb318>"),
    YELLOW("<#f4e818>"),
    LIGHT_YELLOW("<#f6ed56>"),
    DARK_GREEN("<#1d9618>"),
    GREEN("<#26ef1e>"),
    LIGHT_GREEN("<#72f66d>"),
    WHITE("<#ffffff>");

    private final String COLOR;

    Color(String COLOR) {
        this.COLOR = COLOR;
   }

   public String getColor() {
        return COLOR;
   }
}


*/
