package com.example.englishhindi.model;

/**
 * Model class for a game category.
 */
public class GameCategory {

    /**
     * Game types.
     */
    public enum GameType {
        WORD_SCRAMBLE,
        PICTURE_MATCH,
        WORD_ASSOCIATION,
        FLASHCARDS
    }

    private int iconResId;
    private String title;
    private String description;
    private GameType gameType;

    /**
     * Constructor.
     *
     * @param iconResId    Resource ID for the game icon
     * @param title        Game title
     * @param description  Game description
     * @param gameType     Game type
     */
    public GameCategory(int iconResId, String title, String description, GameType gameType) {
        this.iconResId = iconResId;
        this.title = title;
        this.description = description;
        this.gameType = gameType;
    }

    /**
     * Get the icon resource ID.
     */
    public int getIconResId() {
        return iconResId;
    }

    /**
     * Set the icon resource ID.
     */
    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    /**
     * Get the game title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the game title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the game description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the game description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the game type.
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * Set the game type.
     */
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}