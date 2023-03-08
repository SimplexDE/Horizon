package de.simplex.horizon.enums;

import org.bukkit.Difficulty;

public enum DifficultyName {
	PEACEFUL("peaceful", "0", Difficulty.PEACEFUL),
	EASY("easy", "1", Difficulty.EASY),
	NORMAL("normal", "2", Difficulty.NORMAL),
	HARD("hard", "3", Difficulty.HARD);

	private final String[] aliases;
	private final Difficulty difficulty;

	DifficultyName(String primaryAlias, String secondaryAlias, Difficulty difficulty) {
		this.aliases = new String[]{primaryAlias, secondaryAlias};
		this.difficulty = difficulty;
	}

	/**
	 * @param input the difficulty to search for
	 * @return The difficulty or null if not found
	 */
	public static DifficultyName fromString(String input) {
		for (DifficultyName difficultyName : values()) {
			for (String alias : difficultyName.aliases) {
				if (input.equalsIgnoreCase(alias)) {
					return difficultyName;
				}
			}
		}
		return null;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public String getDifficultyName() {
		return aliases[0];
	}
}