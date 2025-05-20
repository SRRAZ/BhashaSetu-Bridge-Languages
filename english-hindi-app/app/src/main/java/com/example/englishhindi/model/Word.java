package com.example.englishhindi.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a vocabulary word with translations,
 * pronunciations, examples, and multimedia elements.
 */
@Entity(tableName = "words")
public class Word {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String englishWord;
    private String hindiWord;
    private String englishPronunciation;
    private String hindiPronunciation;
    private String category;
    private int difficulty;
    private boolean isFavorite;
    private long timeAdded;
    private int masteryLevel;
    private String exampleSentenceEnglish;
    private String exampleSentenceHindi;
    private String notes;
    private String usageContext;
    private String partOfSpeech;
    
    // Multimedia fields
    private String imageUrl;
    private String englishAudioFileName;
    private String hindiAudioFileName;
    private boolean hasImage;
    private boolean hasEnglishAudio;
    private boolean hasHindiAudio;
    
    // Progress and game-related fields
    private int correctAttempts;
    private int totalAttempts;
    private long lastPracticed;
    private long nextReviewDue;
    
    public Word(String englishWord, String hindiWord, String englishPronunciation, 
               String hindiPronunciation, String category) {
        this.englishWord = englishWord;
        this.hindiWord = hindiWord;
        this.englishPronunciation = englishPronunciation;
        this.hindiPronunciation = hindiPronunciation;
        this.category = category;
        this.difficulty = 1;
        this.isFavorite = false;
        this.timeAdded = System.currentTimeMillis();
        this.masteryLevel = 0;
        this.hasImage = false;
        this.hasEnglishAudio = false;
        this.hasHindiAudio = false;
        this.correctAttempts = 0;
        this.totalAttempts = 0;
        this.lastPracticed = 0;
        this.nextReviewDue = System.currentTimeMillis();
    }
    
    // Constructor with more parameters
    public Word(String englishWord, String hindiWord, String englishPronunciation, 
               String hindiPronunciation, String category, int difficulty,
               String exampleSentenceEnglish, String exampleSentenceHindi,
               String partOfSpeech, String usageContext) {
        this.englishWord = englishWord;
        this.hindiWord = hindiWord;
        this.englishPronunciation = englishPronunciation;
        this.hindiPronunciation = hindiPronunciation;
        this.category = category;
        this.difficulty = difficulty;
        this.isFavorite = false;
        this.timeAdded = System.currentTimeMillis();
        this.masteryLevel = 0;
        this.exampleSentenceEnglish = exampleSentenceEnglish;
        this.exampleSentenceHindi = exampleSentenceHindi;
        this.partOfSpeech = partOfSpeech;
        this.usageContext = usageContext;
        this.hasImage = false;
        this.hasEnglishAudio = false;
        this.hasHindiAudio = false;
        this.correctAttempts = 0;
        this.totalAttempts = 0;
        this.lastPracticed = 0;
        this.nextReviewDue = System.currentTimeMillis();
    }
    
    // Basic Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getEnglishWord() {
        return englishWord;
    }
    
    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }
    
    public String getHindiWord() {
        return hindiWord;
    }
    
    public void setHindiWord(String hindiWord) {
        this.hindiWord = hindiWord;
    }
    
    public String getEnglishPronunciation() {
        return englishPronunciation;
    }
    
    public void setEnglishPronunciation(String englishPronunciation) {
        this.englishPronunciation = englishPronunciation;
    }
    
    public String getHindiPronunciation() {
        return hindiPronunciation;
    }
    
    public void setHindiPronunciation(String hindiPronunciation) {
        this.hindiPronunciation = hindiPronunciation;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    public long getTimeAdded() {
        return timeAdded;
    }
    
    public void setTimeAdded(long timeAdded) {
        this.timeAdded = timeAdded;
    }
    
    public int getMasteryLevel() {
        return masteryLevel;
    }
    
    public void setMasteryLevel(int masteryLevel) {
        this.masteryLevel = masteryLevel;
    }
    
    public String getExampleSentenceEnglish() {
        return exampleSentenceEnglish;
    }
    
    public void setExampleSentenceEnglish(String exampleSentenceEnglish) {
        this.exampleSentenceEnglish = exampleSentenceEnglish;
    }
    
    public String getExampleSentenceHindi() {
        return exampleSentenceHindi;
    }
    
    public void setExampleSentenceHindi(String exampleSentenceHindi) {
        this.exampleSentenceHindi = exampleSentenceHindi;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getUsageContext() {
        return usageContext;
    }
    
    public void setUsageContext(String usageContext) {
        this.usageContext = usageContext;
    }
    
    public String getPartOfSpeech() {
        return partOfSpeech;
    }
    
    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
    
    // Image-related getters and setters
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.hasImage = (imageUrl != null && !imageUrl.isEmpty());
    }
    
    public boolean hasImage() {
        return hasImage;
    }
    
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
    
    // Audio-related getters and setters
    public String getEnglishAudioFileName() {
        return englishAudioFileName;
    }
    
    public void setEnglishAudioFileName(String englishAudioFileName) {
        this.englishAudioFileName = englishAudioFileName;
        this.hasEnglishAudio = (englishAudioFileName != null && !englishAudioFileName.isEmpty());
    }
    
    public String getHindiAudioFileName() {
        return hindiAudioFileName;
    }
    
    public void setHindiAudioFileName(String hindiAudioFileName) {
        this.hindiAudioFileName = hindiAudioFileName;
        this.hasHindiAudio = (hindiAudioFileName != null && !hindiAudioFileName.isEmpty());
    }
    
    public boolean hasEnglishAudio() {
        return hasEnglishAudio;
    }
    
    public void setHasEnglishAudio(boolean hasEnglishAudio) {
        this.hasEnglishAudio = hasEnglishAudio;
    }
    
    public boolean hasHindiAudio() {
        return hasHindiAudio;
    }
    
    public void setHasHindiAudio(boolean hasHindiAudio) {
        this.hasHindiAudio = hasHindiAudio;
    }
    
    // Mastery and progress tracking getters and setters
    public int getCorrectAttempts() {
        return correctAttempts;
    }
    
    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
    }
    
    public int getTotalAttempts() {
        return totalAttempts;
    }
    
    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }
    
    public long getLastPracticed() {
        return lastPracticed;
    }
    
    public void setLastPracticed(long lastPracticed) {
        this.lastPracticed = lastPracticed;
    }
    
    public long getNextReviewDue() {
        return nextReviewDue;
    }
    
    public void setNextReviewDue(long nextReviewDue) {
        this.nextReviewDue = nextReviewDue;
    }
    
    /**
     * Calculate success rate as a percentage
     * @return percentage of correct answers (0-100)
     */
    public int getSuccessRate() {
        if (totalAttempts == 0) {
            return 0;
        }
        return (int) (((float) correctAttempts / totalAttempts) * 100);
    }
    
    /**
     * Record a practice attempt
     * @param correct whether the attempt was correct
     */
    public void recordAttempt(boolean correct) {
        this.totalAttempts++;
        if (correct) {
            this.correctAttempts++;
        }
        this.lastPracticed = System.currentTimeMillis();
        
        // Update mastery level (0 to 5, where 5 is fully mastered)
        if (correct) {
            if (masteryLevel < 5) {
                masteryLevel++;
            }
        } else {
            if (masteryLevel > 0) {
                masteryLevel--;
            }
        }
        
        // Calculate next review time using spaced repetition algorithm
        calculateNextReviewTime(correct);
    }
    
    /**
     * Calculate when this word should be reviewed next using a spaced repetition algorithm
     * @param wasCorrect whether the last attempt was correct
     */
    private void calculateNextReviewTime(boolean wasCorrect) {
        long now = System.currentTimeMillis();
        
        // Base intervals (in milliseconds)
        final long HOUR = 60 * 60 * 1000;
        final long DAY = 24 * HOUR;
        
        if (wasCorrect) {
            // If answer was correct, increase interval based on mastery level
            switch (masteryLevel) {
                case 0:
                    nextReviewDue = now + 1 * HOUR;  // 1 hour
                    break;
                case 1:
                    nextReviewDue = now + 4 * HOUR;  // 4 hours
                    break;
                case 2:
                    nextReviewDue = now + 1 * DAY;   // 1 day
                    break;
                case 3:
                    nextReviewDue = now + 3 * DAY;   // 3 days
                    break;
                case 4:
                    nextReviewDue = now + 7 * DAY;   // 1 week
                    break;
                case 5:
                    nextReviewDue = now + 30 * DAY;  // 1 month
                    break;
            }
        } else {
            // If answer was incorrect, review sooner
            switch (masteryLevel) {
                case 0:
                case 1:
                    nextReviewDue = now + 30 * 60 * 1000;  // 30 minutes
                    break;
                case 2:
                case 3:
                    nextReviewDue = now + 2 * HOUR;  // 2 hours
                    break;
                case 4:
                case 5:
                    nextReviewDue = now + 6 * HOUR;  // 6 hours
                    break;
            }
        }
    }
    
    /**
     * Check if this word is due for review
     * @return true if the word should be reviewed now
     */
    public boolean isDueForReview() {
        return System.currentTimeMillis() >= nextReviewDue;
    }
}