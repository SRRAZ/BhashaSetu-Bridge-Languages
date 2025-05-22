package com.example.englishhindi.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.englishhindi.model.Category;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.util.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Manager for handling offline data storage and retrieval.
 * Ensures that essential data is available even without a network connection.
 */
public class OfflineDataManager {
    
    private static final String TAG = "OfflineDataManager";
    
    // File names for cached data
    private static final String WORDS_CACHE_FILE = "words_cache.json";
    private static final String CATEGORIES_CACHE_FILE = "categories_cache.json";
    private static final String USER_DATA_CACHE_FILE = "user_data_cache.json";
    
    // Preference keys
    private static final String PREF_NAME = "offline_data_prefs";
    private static final String KEY_WORDS_LAST_UPDATED = "words_last_updated";
    private static final String KEY_CATEGORIES_LAST_UPDATED = "categories_last_updated";
    private static final String KEY_USER_DATA_LAST_UPDATED = "user_data_last_updated";
    
    private static OfflineDataManager instance;
    
    private Context context;
    private SharedPreferences prefs;
    private NetworkUtils networkUtils;
    private Executor executor;
    private Gson gson;
    
    // Repositories for data access
    private WordRepository wordRepository;
    private CategoryRepository categoryRepository;
    
    private OfflineDataManager(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.networkUtils = new NetworkUtils(context);
        this.executor = Executors.newSingleThreadExecutor();
        this.gson = new Gson();
        
        // Initialize repositories
        this.wordRepository = new WordRepository(context);
        this.categoryRepository = new CategoryRepository(context);
    }
    
    /**
     * Get the singleton instance of the offline data manager
     * @param context Application context
     * @return OfflineDataManager instance
     */
    public static synchronized OfflineDataManager getInstance(Context context) {
        if (instance == null) {
            instance = new OfflineDataManager(context);
        }
        return instance;
    }
    
    /**
     * Cache all words for offline access
     * @param callback Callback when complete
     */
    public void cacheAllWords(final DataCacheCallback<List<Word>> callback) {
        executor.execute(() -> {
            try {
                // Get all words from repository
                List<Word> words = wordRepository.getAllWords();
                
                // Save to cache file
                saveWordsToCache(words);
                
                // Update last updated timestamp
                prefs.edit().putLong(KEY_WORDS_LAST_UPDATED, System.currentTimeMillis()).apply();
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onDataCached(words));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error caching words", e);
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onError(e));
                }
            }
        });
    }
    
    /**
     * Cache essential words for offline access (subset of all words)
     * @param callback Callback when complete
     */
    public void cacheEssentialWords(final DataCacheCallback<List<Word>> callback) {
        executor.execute(() -> {
            try {
                // Get essential words from repository (high frequency, beginner level)
                List<Word> words = wordRepository.getEssentialWords();
                
                // Save to cache file
                saveWordsToCache(words);
                
                // Update last updated timestamp
                prefs.edit().putLong(KEY_WORDS_LAST_UPDATED, System.currentTimeMillis()).apply();
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onDataCached(words));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error caching essential words", e);
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onError(e));
                }
            }
        });
    }
    
    /**
     * Cache categories for offline access
     * @param callback Callback when complete
     */
    public void cacheCategories(final DataCacheCallback<List<Category>> callback) {
        executor.execute(() -> {
            try {
                // Get all categories from repository
                List<Category> categories = categoryRepository.getAllCategories();
                
                // Save to cache file
                saveCategoriesToCache(categories);
                
                // Update last updated timestamp
                prefs.edit().putLong(KEY_CATEGORIES_LAST_UPDATED, System.currentTimeMillis()).apply();
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onDataCached(categories));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error caching categories", e);
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onError(e));
                }
            }
        });
    }
    
    /**
     * Cache user data for offline access
     * @param userData User data object
     * @param callback Callback when complete
     */
    public void cacheUserData(final Object userData, final DataCacheCallback<Object> callback) {
        executor.execute(() -> {
            try {
                // Save to cache file
                String json = gson.toJson(userData);
                writeToFile(USER_DATA_CACHE_FILE, json);
                
                // Update last updated timestamp
                prefs.edit().putLong(KEY_USER_DATA_LAST_UPDATED, System.currentTimeMillis()).apply();
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onDataCached(userData));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error caching user data", e);
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onError(e));
                }
            }
        });
    }
    
    /**
     * Get cached words for offline access
     * @param callback Callback with the result
     */
    public void getCachedWords(final DataCallback<List<Word>> callback) {
        executor.execute(() -> {
            try {
                // Try to read from cache file
                String json = readFromFile(WORDS_CACHE_FILE);
                
                if (json != null && !json.isEmpty()) {
                    // Parse JSON to words list
                    Type listType = new TypeToken<List<Word>>() {}.getType();
                    List<Word> words = gson.fromJson(json, listType);
                    
                    // Notify callback on main thread
                    if (callback != null) {
                        final List<Word> finalWords = words != null ? words : new ArrayList<>();
                        notifyOnMainThread(() -> callback.onDataLoaded(finalWords));
                    }
                } else {
                    // No cached data, try to get from repository if online
                    if (networkUtils.isNetworkAvailable()) {
                        List<Word> words = wordRepository.getAllWords();
                        
                        // Cache for future use
                        saveWordsToCache(words);
                        
                        // Notify callback on main thread
                        if (callback != null) {
                            final List<Word> finalWords = words != null ? words : new ArrayList<>();
                            notifyOnMainThread(() -> callback.onDataLoaded(finalWords));
                        }
                    } else {
                        // No cache and no network
                        if (callback != null) {
                            notifyOnMainThread(() -> callback.onDataLoaded(new ArrayList<>()));
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error getting cached words", e);
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onError(e));
                }
            }
        });
    }
    
    /**
     * Get cached categories for offline access
     * @param callback Callback with the result
     */
    public void getCachedCategories(final DataCallback<List<Category>> callback) {
        executor.execute(() -> {
            try {
                // Try to read from cache file
                String json = readFromFile(CATEGORIES_CACHE_FILE);
                
                if (json != null && !json.isEmpty()) {
                    // Parse JSON to categories list
                    Type listType = new TypeToken<List<Category>>() {}.getType();
                    List<Category> categories = gson.fromJson(json, listType);
                    
                    // Notify callback on main thread
                    if (callback != null) {
                        final List<Category> finalCategories = categories != null ? 
                                categories : new ArrayList<>();
                        notifyOnMainThread(() -> callback.onDataLoaded(finalCategories));
                    }
                } else {
                    // No cached data, try to get from repository if online
                    if (networkUtils.isNetworkAvailable()) {
                        List<Category> categories = categoryRepository.getAllCategories();
                        
                        // Cache for future use
                        saveCategoriesToCache(categories);
                        
                        // Notify callback on main thread
                        if (callback != null) {
                            final List<Category> finalCategories = categories != null ? 
                                    categories : new ArrayList<>();
                            notifyOnMainThread(() -> callback.onDataLoaded(finalCategories));
                        }
                    } else {
                        // No cache and no network
                        if (callback != null) {
                            notifyOnMainThread(() -> callback.onDataLoaded(new ArrayList<>()));
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error getting cached categories", e);
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onError(e));
                }
            }
        });
    }
    
    /**
     * Get cached user data for offline access
     * @param typeToken Type token for the user data class
     * @param callback Callback with the result
     * @param <T> Type of user data
     */
    public <T> void getCachedUserData(final TypeToken<T> typeToken, final DataCallback<T> callback) {
        executor.execute(() -> {
            try {
                // Try to read from cache file
                String json = readFromFile(USER_DATA_CACHE_FILE);
                
                if (json != null && !json.isEmpty()) {
                    // Parse JSON to user data object
                    T userData = gson.fromJson(json, typeToken.getType());
                    
                    // Notify callback on main thread
                    if (callback != null) {
                        final T finalUserData = userData;
                        notifyOnMainThread(() -> callback.onDataLoaded(finalUserData));
                    }
                } else {
                    // No cached data
                    if (callback != null) {
                        notifyOnMainThread(() -> callback.onDataLoaded(null));
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error getting cached user data", e);
                
                // Notify callback on main thread
                if (callback != null) {
                    notifyOnMainThread(() -> callback.onError(e));
                }
            }
        });
    }
    
    /**
     * Check if words data needs updating (cache is older than threshold)
     * @param thresholdHours Number of hours that determines if cache is stale
     * @return true if cache is stale or doesn't exist
     */
    public boolean isWordsCacheStale(int thresholdHours) {
        long lastUpdated = prefs.getLong(KEY_WORDS_LAST_UPDATED, 0);
        long currentTime = System.currentTimeMillis();
        long threshold = thresholdHours * 60 * 60 * 1000; // Convert hours to milliseconds
        
        return (currentTime - lastUpdated) > threshold;
    }
    
    /**
     * Check if categories data needs updating (cache is older than threshold)
     * @param thresholdHours Number of hours that determines if cache is stale
     * @return true if cache is stale or doesn't exist
     */
    public boolean isCategoriesCacheStale(int thresholdHours) {
        long lastUpdated = prefs.getLong(KEY_CATEGORIES_LAST_UPDATED, 0);
        long currentTime = System.currentTimeMillis();
        long threshold = thresholdHours * 60 * 60 * 1000; // Convert hours to milliseconds
        
        return (currentTime - lastUpdated) > threshold;
    }
    
    /**
     * Check if user data needs updating (cache is older than threshold)
     * @param thresholdHours Number of hours that determines if cache is stale
     * @return true if cache is stale or doesn't exist
     */
    public boolean isUserDataCacheStale(int thresholdHours) {
        long lastUpdated = prefs.getLong(KEY_USER_DATA_LAST_UPDATED, 0);
        long currentTime = System.currentTimeMillis();
        long threshold = thresholdHours * 60 * 60 * 1000; // Convert hours to milliseconds
        
        return (currentTime - lastUpdated) > threshold;
    }
    
    /**
     * Get the timestamp when words were last updated
     * @return Timestamp or 0 if never updated
     */
    public long getWordsLastUpdated() {
        return prefs.getLong(KEY_WORDS_LAST_UPDATED, 0);
    }
    
    /**
     * Get the timestamp when categories were last updated
     * @return Timestamp or 0 if never updated
     */
    public long getCategoriesLastUpdated() {
        return prefs.getLong(KEY_CATEGORIES_LAST_UPDATED, 0);
    }
    
    /**
     * Get the timestamp when user data was last updated
     * @return Timestamp or 0 if never updated
     */
    public long getUserDataLastUpdated() {
        return prefs.getLong(KEY_USER_DATA_LAST_UPDATED, 0);
    }
    
    /**
     * Save words to the cache file
     * @param words List of words to cache
     * @throws IOException If writing to file fails
     */
    private void saveWordsToCache(List<Word> words) throws IOException {
        String json = gson.toJson(words);
        writeToFile(WORDS_CACHE_FILE, json);
    }
    
    /**
     * Save categories to the cache file
     * @param categories List of categories to cache
     * @throws IOException If writing to file fails
     */
    private void saveCategoriesToCache(List<Category> categories) throws IOException {
        String json = gson.toJson(categories);
        writeToFile(CATEGORIES_CACHE_FILE, json);
    }
    
    /**
     * Write a string to a file
     * @param fileName Name of the file
     * @param content Content to write
     * @throws IOException If writing fails
     */
    private void writeToFile(String fileName, String content) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(content.getBytes());
        fos.close();
    }
    
    /**
     * Read a string from a file
     * @param fileName Name of the file
     * @return File contents or null if file doesn't exist
     */
    private String readFromFile(String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            return sb.toString();
        } catch (IOException e) {
            // File doesn't exist or can't be read
            return null;
        }
    }
    
    /**
     * Run a runnable on the main thread
     * @param runnable Runnable to execute
     */
    private void notifyOnMainThread(Runnable runnable) {
        android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
        mainHandler.post(runnable);
    }
    
    /**
     * Interface for data loading callbacks
     */
    public interface DataCallback<T> {
        void onDataLoaded(T data);
        void onError(Exception e);
    }
    
    /**
     * Interface for data caching callbacks
     */
    public interface DataCacheCallback<T> {
        void onDataCached(T data);
        void onError(Exception e);
    }
}