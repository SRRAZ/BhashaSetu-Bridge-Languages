package com.example.englishhindi.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.englishhindi.data.model.Lesson;
import com.example.englishhindi.data.model.Word;
import com.example.englishhindi.util.AppExecutors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Specialized caching system for lessons and vocabulary data.
 * Provides efficient access to both online and offline content.
 */
public class LessonsCache {
    private static final String TAG = "LessonsCache";
    
    // Singleton instance
    private static LessonsCache instance;
    
    // Context reference
    private final Context context;
    
    // Underlying caches
    private final DataCache dataCache;
    
    // Preferences
    private final SharedPreferences preferences;
    private static final String PREFS_NAME = "lessons_cache_prefs";
    private static final String KEY_DOWNLOADED_LESSONS = "downloaded_lessons";
    private static final String KEY_FAVORITED_WORDS = "favorited_words";
    private static final String KEY_LAST_UPDATE = "last_update_time";
    
    // Cache keys
    private static final String KEY_ALL_LESSONS = "all_lessons";
    private static final String KEY_LESSON_PREFIX = "lesson_";
    private static final String KEY_WORDS_FOR_LESSON_PREFIX = "words_for_lesson_";
    
    // Default expiry: 7 days for lessons
    private static final long DEFAULT_LESSONS_EXPIRY = TimeUnit.DAYS.toMillis(7);
    
    // LiveData for observing changes
    private final MutableLiveData<List<Lesson>> allLessonsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Set<Long>> downloadedLessonsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Set<Long>> favoritedWordsLiveData = new MutableLiveData<>();
    private final Map<Long, MutableLiveData<Lesson>> lessonLiveDataMap = new HashMap<>();
    private final Map<Long, MutableLiveData<List<Word>>> wordsForLessonLiveDataMap = new HashMap<>();
    
    // In-memory cache for quick access
    private List<Lesson> allLessonsCache;
    private final Map<Long, Lesson> lessonCache = new HashMap<>();
    private final Map<Long, List<Word>> wordsForLessonCache = new HashMap<>();
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private LessonsCache(Context context) {
        this.context = context.getApplicationContext();
        this.dataCache = DataCache.getInstance(context);
        
        // Initialize preferences
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        
        // Initialize LiveData with saved values
        downloadedLessonsLiveData.setValue(getDownloadedLessonIds());
        favoritedWordsLiveData.setValue(getFavoritedWordIds());
        
        // Try to initialize all lessons from cache
        tryLoadAllLessonsFromCache();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return LessonsCache instance
     */
    public static synchronized LessonsCache getInstance(Context context) {
        if (instance == null) {
            instance = new LessonsCache(context);
        }
        return instance;
    }
    
    /**
     * Try to load all lessons from cache.
     */
    private void tryLoadAllLessonsFromCache() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Lesson> lessons = dataCache.get(KEY_ALL_LESSONS, null);
            if (lessons != null) {
                allLessonsCache = lessons;
                allLessonsLiveData.postValue(lessons);
                
                // Also cache individual lessons
                for (Lesson lesson : lessons) {
                    lessonCache.put(lesson.getId(), lesson);
                    MutableLiveData<Lesson> liveData = lessonLiveDataMap.get(lesson.getId());
                    if (liveData != null) {
                        liveData.postValue(lesson);
                    }
                }
            }
        });
    }
    
    /**
     * Get all lessons.
     *
     * @return LiveData with all lessons
     */
    public LiveData<List<Lesson>> getAllLessons() {
        return allLessonsLiveData;
    }
    
    /**
     * Get a specific lesson by ID.
     *
     * @param lessonId Lesson ID
     * @return LiveData with the lesson
     */
    public LiveData<Lesson> getLesson(long lessonId) {
        // Create LiveData if it doesn't exist
        MutableLiveData<Lesson> liveData = lessonLiveDataMap.get(lessonId);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            lessonLiveDataMap.put(lessonId, liveData);
            
            // Try to get from memory cache first
            Lesson cachedLesson = lessonCache.get(lessonId);
            if (cachedLesson != null) {
                liveData.setValue(cachedLesson);
            } else {
                // Try to load from disk cache
                AppExecutors.getInstance().diskIO().execute(() -> {
                    Lesson lesson = dataCache.get(KEY_LESSON_PREFIX + lessonId, null);
                    if (lesson != null) {
                        lessonCache.put(lessonId, lesson);
                        liveData.postValue(lesson);
                    }
                });
            }
        }
        
        return liveData;
    }
    
    /**
     * Get words for a specific lesson.
     *
     * @param lessonId Lesson ID
     * @return LiveData with list of words
     */
    public LiveData<List<Word>> getWordsForLesson(long lessonId) {
        // Create LiveData if it doesn't exist
        MutableLiveData<List<Word>> liveData = wordsForLessonLiveDataMap.get(lessonId);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            wordsForLessonLiveDataMap.put(lessonId, liveData);
            
            // Try to get from memory cache first
            List<Word> cachedWords = wordsForLessonCache.get(lessonId);
            if (cachedWords != null) {
                liveData.setValue(cachedWords);
            } else {
                // Try to load from disk cache
                AppExecutors.getInstance().diskIO().execute(() -> {
                    List<Word> words = dataCache.get(KEY_WORDS_FOR_LESSON_PREFIX + lessonId, null);
                    if (words != null) {
                        wordsForLessonCache.put(lessonId, words);
                        liveData.postValue(words);
                    }
                });
            }
        }
        
        return liveData;
    }
    
    /**
     * Set all lessons.
     *
     * @param lessons List of lessons
     */
    public void setAllLessons(List<Lesson> lessons) {
        if (lessons == null) {
            return;
        }
        
        // Update memory cache
        allLessonsCache = new ArrayList<>(lessons);
        
        // Update LiveData
        allLessonsLiveData.setValue(allLessonsCache);
        
        // Cache individual lessons
        for (Lesson lesson : lessons) {
            lessonCache.put(lesson.getId(), lesson);
            MutableLiveData<Lesson> liveData = lessonLiveDataMap.get(lesson.getId());
            if (liveData != null) {
                liveData.setValue(lesson);
            }
        }
        
        // Save to disk cache in background
        AppExecutors.getInstance().diskIO().execute(() -> {
            dataCache.put(KEY_ALL_LESSONS, lessons, DataCache.CachePolicy.HIGH_PRIORITY, 
                        DEFAULT_LESSONS_EXPIRY);
            
            // Update last update time
            preferences.edit().putLong(KEY_LAST_UPDATE, System.currentTimeMillis()).apply();
        });
    }
    
    /**
     * Set a specific lesson.
     *
     * @param lesson Lesson to cache
     */
    public void setLesson(Lesson lesson) {
        if (lesson == null) {
            return;
        }
        
        // Update memory cache
        lessonCache.put(lesson.getId(), lesson);
        
        // Update LiveData
        MutableLiveData<Lesson> liveData = lessonLiveDataMap.get(lesson.getId());
        if (liveData != null) {
            liveData.setValue(lesson);
        } else {
            liveData = new MutableLiveData<>(lesson);
            lessonLiveDataMap.put(lesson.getId(), liveData);
        }
        
        // Update all lessons cache if it exists
        if (allLessonsCache != null) {
            boolean found = false;
            for (int i = 0; i < allLessonsCache.size(); i++) {
                if (allLessonsCache.get(i).getId() == lesson.getId()) {
                    allLessonsCache.set(i, lesson);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                allLessonsCache.add(lesson);
            }
            
            allLessonsLiveData.setValue(allLessonsCache);
        }
        
        // Save to disk cache in background
        AppExecutors.getInstance().diskIO().execute(() -> {
            dataCache.put(KEY_LESSON_PREFIX + lesson.getId(), lesson, 
                        DataCache.CachePolicy.HIGH_PRIORITY, DEFAULT_LESSONS_EXPIRY);
        });
    }
    
    /**
     * Set words for a specific lesson.
     *
     * @param lessonId Lesson ID
     * @param words    List of words
     */
    public void setWordsForLesson(long lessonId, List<Word> words) {
        if (words == null) {
            return;
        }
        
        // Update memory cache
        wordsForLessonCache.put(lessonId, new ArrayList<>(words));
        
        // Update LiveData
        MutableLiveData<List<Word>> liveData = wordsForLessonLiveDataMap.get(lessonId);
        if (liveData != null) {
            liveData.setValue(words);
        } else {
            liveData = new MutableLiveData<>(words);
            wordsForLessonLiveDataMap.put(lessonId, liveData);
        }
        
        // Save to disk cache in background
        AppExecutors.getInstance().diskIO().execute(() -> {
            dataCache.put(KEY_WORDS_FOR_LESSON_PREFIX + lessonId, words, 
                        DataCache.CachePolicy.HIGH_PRIORITY, DEFAULT_LESSONS_EXPIRY);
        });
    }
    
    /**
     * Mark a lesson as downloaded.
     *
     * @param lessonId Lesson ID
     * @param downloaded true if downloaded, false otherwise
     */
    public void setLessonDownloaded(long lessonId, boolean downloaded) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Set<Long> downloadedLessons = getDownloadedLessonIds();
            
            // Update set
            if (downloaded) {
                downloadedLessons.add(lessonId);
            } else {
                downloadedLessons.remove(lessonId);
            }
            
            // Save to preferences
            saveDownloadedLessonIds(downloadedLessons);
            
            // Update LiveData
            downloadedLessonsLiveData.postValue(downloadedLessons);
        });
    }
    
    /**
     * Get IDs of downloaded lessons.
     *
     * @return Set of lesson IDs
     */
    public Set<Long> getDownloadedLessonIds() {
        Set<String> stringIds = preferences.getStringSet(KEY_DOWNLOADED_LESSONS, new HashSet<>());
        Set<Long> longIds = new HashSet<>();
        
        for (String id : stringIds) {
            try {
                longIds.add(Long.parseLong(id));
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid lesson ID in preferences: " + id, e);
            }
        }
        
        return longIds;
    }
    
    /**
     * Save downloaded lesson IDs to preferences.
     *
     * @param lessonIds Set of lesson IDs
     */
    private void saveDownloadedLessonIds(Set<Long> lessonIds) {
        Set<String> stringIds = new HashSet<>();
        for (Long id : lessonIds) {
            stringIds.add(String.valueOf(id));
        }
        
        preferences.edit().putStringSet(KEY_DOWNLOADED_LESSONS, stringIds).apply();
    }
    
    /**
     * Get LiveData for downloaded lesson IDs.
     *
     * @return LiveData with set of lesson IDs
     */
    public LiveData<Set<Long>> getDownloadedLessonsLiveData() {
        return downloadedLessonsLiveData;
    }
    
    /**
     * Mark a word as favorited.
     *
     * @param wordId Word ID
     * @param favorited true if favorited, false otherwise
     */
    public void setWordFavorited(long wordId, boolean favorited) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Set<Long> favoritedWords = getFavoritedWordIds();
            
            // Update set
            if (favorited) {
                favoritedWords.add(wordId);
            } else {
                favoritedWords.remove(wordId);
            }
            
            // Save to preferences
            saveFavoritedWordIds(favoritedWords);
            
            // Update LiveData
            favoritedWordsLiveData.postValue(favoritedWords);
        });
    }
    
    /**
     * Get IDs of favorited words.
     *
     * @return Set of word IDs
     */
    public Set<Long> getFavoritedWordIds() {
        Set<String> stringIds = preferences.getStringSet(KEY_FAVORITED_WORDS, new HashSet<>());
        Set<Long> longIds = new HashSet<>();
        
        for (String id : stringIds) {
            try {
                longIds.add(Long.parseLong(id));
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid word ID in preferences: " + id, e);
            }
        }
        
        return longIds;
    }
    
    /**
     * Save favorited word IDs to preferences.
     *
     * @param wordIds Set of word IDs
     */
    private void saveFavoritedWordIds(Set<Long> wordIds) {
        Set<String> stringIds = new HashSet<>();
        for (Long id : wordIds) {
            stringIds.add(String.valueOf(id));
        }
        
        preferences.edit().putStringSet(KEY_FAVORITED_WORDS, stringIds).apply();
    }
    
    /**
     * Get LiveData for favorited word IDs.
     *
     * @return LiveData with set of word IDs
     */
    public LiveData<Set<Long>> getFavoritedWordsLiveData() {
        return favoritedWordsLiveData;
    }
    
    /**
     * Get the time of last update.
     *
     * @return Timestamp of last update
     */
    public long getLastUpdateTime() {
        return preferences.getLong(KEY_LAST_UPDATE, 0);
    }
    
    /**
     * Clear all cached lessons and words.
     */
    public void clearCache() {
        // Clear memory caches
        allLessonsCache = null;
        lessonCache.clear();
        wordsForLessonCache.clear();
        
        // Clear LiveData
        allLessonsLiveData.setValue(null);
        for (MutableLiveData<Lesson> liveData : lessonLiveDataMap.values()) {
            liveData.setValue(null);
        }
        for (MutableLiveData<List<Word>> liveData : wordsForLessonLiveDataMap.values()) {
            liveData.setValue(null);
        }
        
        // Clear disk cache in background
        AppExecutors.getInstance().diskIO().execute(() -> {
            dataCache.remove(KEY_ALL_LESSONS);
            
            // We'd need to know all lesson IDs to remove them individually
            // Instead, we'll rely on DataCache's expiration mechanism
        });
    }
    
    /**
     * Get downloaded lessons.
     *
     * @return List of downloaded lessons
     */
    public List<Lesson> getDownloadedLessons() {
        if (allLessonsCache == null) {
            return Collections.emptyList();
        }
        
        Set<Long> downloadedIds = getDownloadedLessonIds();
        List<Lesson> downloadedLessons = new ArrayList<>();
        
        for (Lesson lesson : allLessonsCache) {
            if (downloadedIds.contains(lesson.getId())) {
                downloadedLessons.add(lesson);
            }
        }
        
        return downloadedLessons;
    }
    
    /**
     * Get favorited words.
     *
     * @return List of favorited words
     */
    public LiveData<List<Word>> getFavoritedWords() {
        MutableLiveData<List<Word>> liveData = new MutableLiveData<>();
        
        AppExecutors.getInstance().diskIO().execute(() -> {
            Set<Long> favoritedIds = getFavoritedWordIds();
            List<Word> favoritedWords = new ArrayList<>();
            
            // We'd need to search through all cached words for matches
            // This is just a placeholder implementation
            
            liveData.postValue(favoritedWords);
        });
        
        return liveData;
    }
    
    /**
     * Check if lessons need to be refreshed from network.
     *
     * @param maxAge Maximum age in milliseconds
     * @return true if refresh is needed
     */
    public boolean shouldRefreshLessons(long maxAge) {
        long lastUpdate = getLastUpdateTime();
        long now = System.currentTimeMillis();
        
        return now - lastUpdate > maxAge || allLessonsCache == null || allLessonsCache.isEmpty();
    }
    
    /**
     * Import data from backup.
     *
     * @param downloadedLessonIds IDs of downloaded lessons
     * @param favoritedWordIds    IDs of favorited words
     */
    public void importData(Set<Long> downloadedLessonIds, Set<Long> favoritedWordIds) {
        if (downloadedLessonIds != null) {
            saveDownloadedLessonIds(downloadedLessonIds);
            downloadedLessonsLiveData.setValue(downloadedLessonIds);
        }
        
        if (favoritedWordIds != null) {
            saveFavoritedWordIds(favoritedWordIds);
            favoritedWordsLiveData.setValue(favoritedWordIds);
        }
    }
    
    /**
     * Export backup data.
     *
     * @return Map containing exported data
     */
    public Map<String, Object> exportData() {
        Map<String, Object> data = new HashMap<>();
        data.put("downloadedLessons", getDownloadedLessonIds());
        data.put("favoritedWords", getFavoritedWordIds());
        data.put("lastUpdateTime", getLastUpdateTime());
        return data;
    }
}