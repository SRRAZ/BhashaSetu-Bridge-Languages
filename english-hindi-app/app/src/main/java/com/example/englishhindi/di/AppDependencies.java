package com.example.englishhindi.di;

import android.content.Context;

import com.example.englishhindi.cache.CacheManager;
import com.example.englishhindi.database.RepositoryFactory;
import com.example.englishhindi.database.WordRepository;
import com.example.englishhindi.manager.SyncManager;
import com.example.englishhindi.repository.OfflineQueueHelper;
import com.example.englishhindi.util.AppExecutors;
import com.example.englishhindi.util.ImageLoader;
import com.example.englishhindi.util.NetworkUtils;

/**
 * A lightweight dependency container for application-wide components.
 * This class provides centralized access to app dependencies and helps
 * with more efficient object creation and resource management.
 */
public class AppDependencies {
    
    private final Context appContext;
    
    // Lazily instantiated dependencies
    private NetworkUtils networkUtils;
    private CacheManager cacheManager;
    private OfflineQueueHelper offlineQueueHelper;
    private RepositoryFactory repositoryFactory;
    private SyncManager syncManager;
    private ImageLoader imageLoader;
    private AppExecutors appExecutors;
    
    public AppDependencies(Context context) {
        this.appContext = context.getApplicationContext();
    }
    
    /**
     * Get the NetworkUtils instance
     * @return NetworkUtils instance
     */
    public synchronized NetworkUtils getNetworkUtils() {
        if (networkUtils == null) {
            networkUtils = NetworkUtils.getInstance(appContext);
        }
        return networkUtils;
    }
    
    /**
     * Get the CacheManager instance
     * @return CacheManager instance
     */
    public synchronized CacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = CacheManager.getInstance(appContext);
        }
        return cacheManager;
    }
    
    /**
     * Get the OfflineQueueHelper instance
     * @return OfflineQueueHelper instance
     */
    public synchronized OfflineQueueHelper getOfflineQueueHelper() {
        if (offlineQueueHelper == null) {
            offlineQueueHelper = OfflineQueueHelper.getInstance(appContext);
        }
        return offlineQueueHelper;
    }
    
    /**
     * Get the RepositoryFactory instance
     * @return RepositoryFactory instance
     */
    public synchronized RepositoryFactory getRepositoryFactory() {
        if (repositoryFactory == null) {
            repositoryFactory = RepositoryFactory.getInstance(appContext);
        }
        return repositoryFactory;
    }
    
    /**
     * Get the SyncManager instance
     * @return SyncManager instance
     */
    public synchronized SyncManager getSyncManager() {
        if (syncManager == null) {
            syncManager = SyncManager.getInstance(appContext);
        }
        return syncManager;
    }
    
    /**
     * Get the WordRepository instance
     * @return WordRepository instance
     */
    public WordRepository getWordRepository() {
        return getRepositoryFactory().getWordRepository();
    }
    
    /**
     * Get the ImageLoader instance
     * @return ImageLoader instance
     */
    public synchronized ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(appContext, getCacheManager());
        }
        return imageLoader;
    }
    
    /**
     * Get the AppExecutors instance
     * @return AppExecutors instance
     */
    public synchronized AppExecutors getAppExecutors() {
        if (appExecutors == null) {
            appExecutors = AppExecutors.getInstance();
        }
        return appExecutors;
    }
}