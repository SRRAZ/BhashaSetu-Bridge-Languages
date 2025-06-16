package com.bhashasetu.app.model.exercise;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.MatchingItemListConverter;
import com.bhashasetu.app.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exercise for matching words with their translations
 */
@Entity(tableName = "matching_exercises")
@TypeConverters({DateConverter.class, MatchingItemListConverter.class})
public class MatchingExercise extends Exercise {
    
    private List<MatchingItem> englishItems;
    private List<MatchingItem> hindiItems;
    private Map<Integer, Integer> userMatches;  // Maps english item index to hindi item index
    private Map<Integer, Integer> correctMatches;  // Maps english item index to hindi item index

    
    public MatchingExercise(String title, int difficulty, int points) {
        super(title, ExerciseType.MATCHING, difficulty, 0, points);
        this.englishItems = new ArrayList<>();
        this.hindiItems = new ArrayList<>();
        this.userMatches = new HashMap<>();
        this.correctMatches = new HashMap<>();
    }
    
    /**
     * Generate matching items from a list of words
     * @param words words to create matching items from
     */
    public void generateItemsFromWords(List<Word> words) {
        this.englishItems.clear();
        this.hindiItems.clear();
        this.userMatches.clear();
        this.correctMatches.clear();
        this.setWordCount(words.size());
        this.setTotalQuestions(words.size());
        
        // Create items for each word
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            
            // Create English item
            MatchingItem englishItem = new MatchingItem(
                    word.getId(),
                    word.getEnglishWord(),
                    i,
                    true
            );
            englishItems.add(englishItem);
            
            // Create Hindi item
            MatchingItem hindiItem = new MatchingItem(
                    word.getId(),
                    word.getHindiWord(),
                    i,
                    false
            );
            hindiItems.add(hindiItem);
            
            // Store correct match mapping
            correctMatches.put(i, i);
        }
        
        // Shuffle Hindi items for display
        Collections.shuffle(hindiItems);
        
        // Remap indices for Hindi items after shuffle
        for (int i = 0; i < hindiItems.size(); i++) {
            hindiItems.get(i).setDisplayIndex(i);
        }
    }
    
    public List<MatchingItem> getEnglishItems() {
        return englishItems;
    }
    
    public void setEnglishItems(List<MatchingItem> englishItems) {
        this.englishItems = englishItems;
    }
    
    public List<MatchingItem> getHindiItems() {
        return hindiItems;
    }
    
    public void setHindiItems(List<MatchingItem> hindiItems) {
        this.hindiItems = hindiItems;
    }
    
    public Map<Integer, Integer> getUserMatches() {
        return userMatches;
    }
    
    public void setUserMatches(Map<Integer, Integer> userMatches) {
        this.userMatches = userMatches;
    }
    
    public Map<Integer, Integer> getCorrectMatches() {
        return correctMatches;
    }
    
    public void setCorrectMatches(Map<Integer, Integer> correctMatches) {
        this.correctMatches = correctMatches;
    }
    
    /**
     * Create a match between an English item and a Hindi item
     * @param englishItemIndex index of the English item in the englishItems list
     * @param hindiItemIndex index of the Hindi item in the hindiItems list
     */
    public void createMatch(int englishItemIndex, int hindiItemIndex) {
        userMatches.put(englishItemIndex, hindiItemIndex);
    }
    
    /**
     * Remove a match for the given English item
     * @param englishItemIndex index of the English item in the englishItems list
     */
    public void removeMatch(int englishItemIndex) {
        userMatches.remove(englishItemIndex);
    }
    
    /**
     * Check if a given English item has been matched
     * @param englishItemIndex index of the English item in the englishItems list
     * @return true if matched, false otherwise
     */
    public boolean isEnglishItemMatched(int englishItemIndex) {
        return userMatches.containsKey(englishItemIndex);
    }
    
    /**
     * Check if a given Hindi item has been matched
     * @param hindiItemIndex index of the Hindi item in the hindiItems list
     * @return true if matched, false otherwise
     */
    public boolean isHindiItemMatched(int hindiItemIndex) {
        return userMatches.containsValue(hindiItemIndex);
    }
    
    /**
     * Get the index of the Hindi item matched with the given English item
     * @param englishItemIndex index of the English item in the englishItems list
     * @return index of the matched Hindi item, or -1 if not matched
     */
    public int getMatchedHindiItemIndex(int englishItemIndex) {
        Integer match = userMatches.get(englishItemIndex);
        return match != null ? match : -1;
    }
    
    /**
     * Get the index of the English item matched with the given Hindi item
     * @param hindiItemIndex index of the Hindi item in the hindiItems list
     * @return index of the matched English item, or -1 if not matched
     */
    public int getMatchedEnglishItemIndex(int hindiItemIndex) {
        for (Map.Entry<Integer, Integer> entry : userMatches.entrySet()) {
            if (entry.getValue() == hindiItemIndex) {
                return entry.getKey();
            }
        }
        return -1;
    }
    
    /**
     * Check if all items have been matched
     * @return true if all items are matched, false otherwise
     */
    public boolean areAllItemsMatched() {
        return userMatches.size() == englishItems.size();
    }
    
    /**
     * Check all matches and calculate score
     */
    public void checkMatches() {
        setCorrectAnswers(0);
        
        // Count correct matches
        for (Map.Entry<Integer, Integer> match : userMatches.entrySet()) {
            int englishIndex = match.getKey();
            int hindiIndex = match.getValue();
            
            // Find original word ID for both items
            int englishWordId = englishItems.get(englishIndex).getWordId();
            int hindiWordId = hindiItems.get(hindiIndex).getWordId();
            
            // Check if they match
            if (englishWordId == hindiWordId) {
                incrementCorrectAnswers();
            }
        }
    }
    
    private void incrementCorrectAnswers() {
        setCorrectAnswers(getCorrectAnswers() + 1);
    }
    
    /**
     * Nested class for matching items
     */
    public static class MatchingItem {
        private int wordId;
        private String text;
        private int displayIndex;
        private boolean isEnglish;
        
        @Ignore
        public MatchingItem() {
        }
        
        public MatchingItem(int wordId, String text, int displayIndex, boolean isEnglish) {
            this.wordId = wordId;
            this.text = text;
            this.displayIndex = displayIndex;
            this.isEnglish = isEnglish;
        }
        
        public int getWordId() {
            return wordId;
        }
        
        public void setWordId(int wordId) {
            this.wordId = wordId;
        }
        
        public String getText() {
            return text;
        }
        
        public void setText(String text) {
            this.text = text;
        }
        
        public int getDisplayIndex() {
            return displayIndex;
        }
        
        public void setDisplayIndex(int displayIndex) {
            this.displayIndex = displayIndex;
        }
        
        public boolean isEnglish() {
            return isEnglish;
        }
        
        public void setEnglish(boolean english) {
            isEnglish = english;
        }
    }
}