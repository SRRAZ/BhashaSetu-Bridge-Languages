package com.example.englishhindi.model.exercise;

/**
 * Enumeration of different exercise types available in the app
 */
public enum ExerciseType {
    FLASHCARD("Flashcards", "Learn words using flashcards"),
    MULTIPLE_CHOICE("Multiple Choice", "Choose the correct answer from options"),
    MATCHING("Matching", "Match words with their translations"),
    FILL_BLANK("Fill in the Blanks", "Complete sentences with the correct words"),
    SPELLING("Spelling", "Type the correct spelling of words"),
    SENTENCE_FORMATION("Sentence Formation", "Arrange words to form correct sentences"),
    AUDIO_RECOGNITION("Audio Recognition", "Identify words from their pronunciation"),
    PICTURE_WORD_ASSOCIATION("Picture Association", "Match pictures with correct words");
    
    private final String displayName;
    private final String description;
    
    ExerciseType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}