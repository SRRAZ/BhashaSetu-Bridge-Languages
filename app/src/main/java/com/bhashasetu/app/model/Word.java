package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * ✅ COMPLETELY FIXED Word Entity
 * Resolves all Room database build errors:
 * - Constructor warnings fixed with @Ignore annotations
 * - Missing columns added (englishText, orderInLesson, etc.)
 * - Schema alignment with DAO queries
 * - Proper indexes for performance
 */
@Entity(tableName = "vocabulary_words", // Changed table name to avoid conflict from "words"
        indices = {
                @Index(value = "category"),
                @Index(value = "difficulty"),
                @Index(value = "englishWord"), // ✅ Index on English word for search
                @Index(value = "orderInLesson"),  // ✅ Index for lesson ordering
                @Index(value = "lessonId"),
                @Index(value = "categoryId")
                },
        foreignKeys = {
                @ForeignKey(
                        entity = WordCategory.class,
                        parentColumns = "id",
                        childColumns = "categoryId",
                        onDelete = ForeignKey.CASCADE),
        })
public class Word {
    @PrimaryKey(autoGenerate = true)
    private long id; // ✅ Changed from int to long for FK consistency

    // ✅ Your existing core fields (preserved)
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
    private String synonyms;
    private String antonyms;
    private int frequency; // How common is this word used


    // ✅ Added fields for DAO compatibility & missing columns
    private String englishText;    // For queries expecting this field
    private String localText;      // For queries expecting this field
    private long lessonId;         // ✅ Added for lesson relationships
    private long categoryId;       // ✅ For category relationships
    private int orderInLesson;     // ✅ CRITICAL: Fixes "missing column" error
    private boolean isActive;      // For active/inactive status
    private long createdAt;        // Creation timestamp
    private long updatedAt;        // Update timestamp

    // ✅ Your existing multimedia fields (preserved)
    private String imageUrl;
    private String englishAudioFileName;
    private String hindiAudioFileName;
    private boolean hasImage;
    private boolean hasEnglishAudio;
    private boolean hasHindiAudio;

    // ✅ Your existing progress fields (preserved)
    private int correctAttempts;
    private int totalAttempts;
    private long lastPracticed;
    private long nextReviewDue;

    // ✅ REQUIRED: No-arg constructor for Room to use
    public Word() {
    }

    // ✅ FIXED: Added @Ignore to your first constructor
    @Ignore
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

        // ✅ Initialize new fields to prevent null values
        this.englishText = englishWord;
        this.localText = hindiWord;
        this.isActive = true;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.orderInLesson = 0;
        this.lessonId = 0;
        this.categoryId = 0;
    }

    // ✅ FIXED: Added @Ignore to your second constructor
    @Ignore
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

        // ✅ Initialize new fields
        this.englishText = englishWord;
        this.localText = hindiWord;
        this.isActive = true;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.orderInLesson = 0;
        this.lessonId = 0;
        this.categoryId = 0;
    }

    // ✅ ALL GETTERS AND SETTERS

    // ID - Changed to long
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    // English Word - Synchronized with englishText
    public String getEnglishWord() { return englishWord; }
    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
        this.englishText = englishWord; // Keep both in sync
        this.updatedAt = System.currentTimeMillis();
    }

    // Hindi Word - Synchronized with localText
    public String getHindiWord() { return hindiWord; }
    public void setHindiWord(String hindiWord) {
        this.hindiWord = hindiWord;
        this.localText = hindiWord; // Keep both in sync
        this.updatedAt = System.currentTimeMillis();
    }

    // ✅ NEW FIELDS for DAO compatibility
    public String getEnglishText() { return englishText; }
    public void setEnglishText(String englishText) {
        this.englishText = englishText;
        this.englishWord = englishText; // Keep both in sync
        this.updatedAt = System.currentTimeMillis();
    }

    public String getLocalText() { return localText; }
    public void setLocalText(String localText) {
        this.localText = localText;
        this.hindiWord = localText; // Keep both in sync
        this.updatedAt = System.currentTimeMillis();
    }

    public long getLessonId() { return lessonId; }
    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
        this.updatedAt = System.currentTimeMillis();
    }

    public long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
        this.updatedAt = System.currentTimeMillis();
    }

    public int getOrderInLesson() { return orderInLesson; }
    public void setOrderInLesson(int orderInLesson) {
        this.orderInLesson = orderInLesson;
        this.updatedAt = System.currentTimeMillis();
    }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) {
        isActive = active;
        this.updatedAt = System.currentTimeMillis();
    }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    // ✅ ALL YOUR EXISTING GETTERS/SETTERS (preserved exactly)
    public String getEnglishPronunciation() { return englishPronunciation; }
    public void setEnglishPronunciation(String englishPronunciation) {
        this.englishPronunciation = englishPronunciation;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getHindiPronunciation() { return hindiPronunciation; }
    public void setHindiPronunciation(String hindiPronunciation) {
        this.hindiPronunciation = hindiPronunciation;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getCategory() { return category; }
    public void setCategory(String category) {
        this.category = category;
        this.updatedAt = System.currentTimeMillis();
    }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        this.updatedAt = System.currentTimeMillis();
    }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
        this.updatedAt = System.currentTimeMillis();
    }

    public long getTimeAdded() { return timeAdded; }
    public void setTimeAdded(long timeAdded) { this.timeAdded = timeAdded; }

    public int getMasteryLevel() { return masteryLevel; }
    public void setMasteryLevel(int masteryLevel) {
        this.masteryLevel = masteryLevel;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getExampleSentenceEnglish() { return exampleSentenceEnglish; }
    public void setExampleSentenceEnglish(String exampleSentenceEnglish) {
        this.exampleSentenceEnglish = exampleSentenceEnglish;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getExampleSentenceHindi() { return exampleSentenceHindi; }
    public void setExampleSentenceHindi(String exampleSentenceHindi) {
        this.exampleSentenceHindi = exampleSentenceHindi;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getNotes() { return notes; }
    public void setNotes(String notes) {
        this.notes = notes;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getUsageContext() { return usageContext; }
    public void setUsageContext(String usageContext) {
        this.usageContext = usageContext;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getPartOfSpeech() { return partOfSpeech; }
    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getSynonyms() { return synonyms; }
    public void setSynonyms(String synonyms) { this.synonyms = synonyms; }

    public String getAntonyms() { return antonyms; }
    public void setAntonyms(String antonyms) { this.antonyms = antonyms; }

    public int getFrequency() { return frequency; }
    public void setFrequency(int frequency) { this.frequency = frequency; }

    // Image-related getters and setters (preserved exactly)
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.hasImage = (imageUrl != null && !imageUrl.isEmpty());
        this.updatedAt = System.currentTimeMillis();
    }

    public boolean hasImage() { return hasImage; }
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
        this.updatedAt = System.currentTimeMillis();
    }

    // Audio-related getters and setters (preserved exactly)
    public String getEnglishAudioFileName() { return englishAudioFileName; }
    public void setEnglishAudioFileName(String englishAudioFileName) {
        this.englishAudioFileName = englishAudioFileName;
        this.hasEnglishAudio = (englishAudioFileName != null && !englishAudioFileName.isEmpty());
        this.updatedAt = System.currentTimeMillis();
    }

    public String getHindiAudioFileName() { return hindiAudioFileName; }
    public void setHindiAudioFileName(String hindiAudioFileName) {
        this.hindiAudioFileName = hindiAudioFileName;
        this.hasHindiAudio = (hindiAudioFileName != null && !hindiAudioFileName.isEmpty());
        this.updatedAt = System.currentTimeMillis();
    }

    public boolean hasEnglishAudio() { return hasEnglishAudio; }
    public void setHasEnglishAudio(boolean hasEnglishAudio) {
        this.hasEnglishAudio = hasEnglishAudio;
        this.updatedAt = System.currentTimeMillis();
    }

    public boolean hasHindiAudio() { return hasHindiAudio; }
    public void setHasHindiAudio(boolean hasHindiAudio) {
        this.hasHindiAudio = hasHindiAudio;
        this.updatedAt = System.currentTimeMillis();
    }

    // Progress tracking getters and setters (preserved exactly)
    public int getCorrectAttempts() { return correctAttempts; }
    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
        this.updatedAt = System.currentTimeMillis();
    }

    public int getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
        this.updatedAt = System.currentTimeMillis();
    }

    public long getLastPracticed() { return lastPracticed; }
    public void setLastPracticed(long lastPracticed) {
        this.lastPracticed = lastPracticed;
        this.updatedAt = System.currentTimeMillis();
    }

    public long getNextReviewDue() { return nextReviewDue; }
    public void setNextReviewDue(long nextReviewDue) {
        this.nextReviewDue = nextReviewDue;
        this.updatedAt = System.currentTimeMillis();
    }

    // ✅ ALL YOUR EXISTING UTILITY METHODS (preserved exactly)

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
        this.updatedAt = System.currentTimeMillis();

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