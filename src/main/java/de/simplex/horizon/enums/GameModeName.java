package de.simplex.horizon.enums;

import org.bukkit.GameMode;

public enum GameModeName {
    SURVIVAL("survival", "0", GameMode.SURVIVAL),
    CREATIVE("creative", "1", GameMode.CREATIVE),
    ADVENTURE("adventure", "2", GameMode.ADVENTURE),
    SPECTATOR("spectator", "3", GameMode.SPECTATOR);

    private final String[] aliases;
    private final GameMode gameMode;

    GameModeName(String primaryAlias, String secondaryAlias, GameMode gameMode) {
        this.aliases = new String[]{primaryAlias, secondaryAlias};
        this.gameMode = gameMode;
    }

    public static GameModeName fromString(String input) {
        for (GameModeName modeName : values()) {
            for (String alias : modeName.aliases) {
                if (input.equalsIgnoreCase(alias)) {
                    return modeName;
                }
            }
        }
        return null;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}