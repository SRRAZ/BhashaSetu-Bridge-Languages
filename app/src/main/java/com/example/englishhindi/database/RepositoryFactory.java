package com.example.englishhindi.database;

import android.content.Context;

import com.example.englishhindi.util.NetworkUtils;

/**
 * Factory class to provide repository instances with appropriate online/offline behavior.
 * Handles switching between online and offline data sources based on connectivity.
 */
public class RepositoryFactory {
    
    private static RepositoryFactory instance;
    
    private Context context;
    private NetworkUtils networkUtils;
    private OfflineDataManager offlineDataManager;
    
    // Repository instances
    private WordRepository wordRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private UserProgressRepository userProgressRepository;
    private GameScoreRepository gameScoreRepository;
    private AchievementRepository achievementRepository;
    
    private RepositoryFactory(Context context) {
        this.context = context.getApplicationContext();
        this.networkUtils = new NetworkUtils(context);
        this.offlineDataManager = OfflineDataManager.getInstance(context);
    }
    
    /**
     * Get the singleton instance of the repository factory
     * @param context Application context
     * @return RepositoryFactory instance
     */
    public static synchronized RepositoryFactory getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryFactory(context);
        }
        return instance;
    }
    
    /**
     * Get a word repository with offline support
     * @return WordRepository instance
     */
    public WordRepository getWordRepository() {
        if (wordRepository == null) {
            wordRepository = new WordRepository(context);
        }
        return wordRepository;
    }
    
    /**
     * Get a category repository with offline support
     * @return CategoryRepository instance
     */
    public CategoryRepository getCategoryRepository() {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepository(context);
        }
        return categoryRepository;
    }
    
    /**
     * Get a user repository with offline support
     * @return UserRepository instance
     */
    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository(context);
        }
        return userRepository;
    }
    
    /**
     * Get a user progress repository with offline support
     * @return UserProgressRepository instance
     */
    public UserProgressRepository getUserProgressRepository() {
        if (userProgressRepository == null) {
            userProgressRepository = new UserProgressRepository(context);
        }
        return userProgressRepository;
    }
    
    /**
     * Get a game score repository with offline support
     * @return GameScoreRepository instance
     */
    public GameScoreRepository getGameScoreRepository() {
        if (gameScoreRepository == null) {
            gameScoreRepository = new GameScoreRepository(context);
        }
        return gameScoreRepository;
    }
    
    /**
     * Get an achievement repository with offline support
     * @return AchievementRepository instance
     */
    public AchievementRepository getAchievementRepository() {
        if (achievementRepository == null) {
            achievementRepository = new AchievementRepository(context);
        }
        return achievementRepository;
    }
    
    /**
     * Check if we should use offline data
     * @return true if offline mode should be used
     */
    public boolean shouldUseOfflineData() {
        // Use offline data if:
        // 1. Network is not available, or
        // 2. Offline mode is explicitly enabled
        return !networkUtils.isNetworkAvailable() || networkUtils.isOfflineModeEnabled();
    }
    
    /**
     * Pre-cache essential data for offline use
     * Called during app initialization or when switching to offline mode
     * @param callback Callback when completed
     */
    public void preCacheEssentialData(final CacheCompleteCallback callback) {
        // Only cache if we have a network connection
        if (!networkUtils.isNetworkAvailable()) {
            if (callback != null) {
                callback.onComplete(false);
            }
            return;
        }
        
        // Counter for completion tracking
        final int[] completionCounter = new int[1];
        completionCounter[0] = 0;
        
        // We have 2 essential data types to cache
        final int totalOperations = 2;
        
        // Cache essential words
        offlineDataManager.cacheEssentialWords(new OfflineDataManager.DataCacheCallback<Object>() {
            @Override
            public void onDataCached(Object data) {
                checkCompletion();
            }
            
            @Override
            public void onError(Exception e) {
                checkCompletion();
            }
        });
        
        // Cache categories
        offlineDataManager.cacheCategories(new OfflineDataManager.DataCacheCallback<Object>() {
            @Override
            public void onDataCached(Object data) {
                checkCompletion();
            }
            
            @Override
            public void onError(Exception e) {
                checkCompletion();
            }
        });
        
        // Helper method to check completion
        Runnable checkCompletion = () -> {
            completionCounter[0]++;
            if (completionCounter[0] >= totalOperations && callback != null) {
                callback.onComplete(true);
            }
        };
    }
    
    /**
     * Interface for cache completion callback
     */
    public interface CacheCompleteCallback {
        void onComplete(boolean success);
    }
}