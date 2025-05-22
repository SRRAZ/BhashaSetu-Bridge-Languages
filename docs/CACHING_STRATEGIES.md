# Caching Strategies in English-Hindi Learning App

This document provides detailed information about the caching strategies implemented in the English-Hindi Learning app to optimize performance, reduce network usage, and enable offline functionality.

## Table of Contents

1. [Introduction](#introduction)
2. [Caching Architecture](#caching-architecture)
3. [Data Caching](#data-caching)
4. [Image Caching](#image-caching)
5. [Audio Caching](#audio-caching)
6. [Lessons Caching](#lessons-caching)
7. [Cache Management](#cache-management)
8. [Implementation Examples](#implementation-examples)
9. [Testing and Validation](#testing-and-validation)
10. [Future Improvements](#future-improvements)

## Introduction

Caching is a critical aspect of the English-Hindi Learning app, enabling:

- **Improved Performance**: Reducing load times by storing frequently accessed data
- **Offline Functionality**: Allowing users to access content without an internet connection
- **Reduced Network Usage**: Minimizing data consumption and battery drain
- **Smoother User Experience**: Eliminating loading delays and network dependencies

The app implements a multi-tiered caching system with specialized components for different types of data.

## Caching Architecture

The caching architecture follows these principles:

1. **Layered Approach**: Memory caching for fast access, disk caching for persistence
2. **Specialized Caches**: Different caching strategies for different data types
3. **Centralized Management**: A unified CacheManager to coordinate all caching operations
4. **Adaptive Behavior**: Adjusting caching behavior based on device capabilities and user patterns
5. **Data Integrity**: Versioning and validation mechanisms to ensure cached data accuracy

### Core Components

The caching system consists of these key components:

```
en-hi-learning-park/english-hindi-app/app/src/main/java/com/example/englishhindi/cache/
├── CacheManagerImpl.java    # Centralized cache management
├── DataCache.java           # Generic data caching
├── ImageCache.java          # Image-specific caching
├── AudioCache.java          # Audio file caching
├── LessonsCache.java        # Domain-specific lesson caching
└── DiskLruCache.java        # Low-level disk caching implementation
```

## Data Caching

The DataCache component provides generic data caching capabilities for structured data objects.

### Key Features

1. **Multi-tiered Caching**
   - Memory cache using LruCache for fast access to frequently used data
   - Disk cache for persistence across app restarts
   - Serialization/deserialization of complex objects

2. **TTL and Expiration**
   - Time-to-live settings configurable by data type
   - Automatic expiration of stale data
   - Version checking for data model changes

3. **Priority-based Caching**
   - High-priority data kept longer in memory
   - Low-priority data evicted first under memory pressure

### Implementation

```java
public class DataCache {
    // Memory cache with LRU eviction policy
    private final LruCache<String, CacheEntry> memoryCache;
    
    // Disk cache for persistence
    private final DiskLruCache diskCache;
    
    // Constructor initializes caches with appropriate sizes
    public DataCache(Context context, int memoryCacheSizeBytes, long diskCacheSizeBytes) {
        // Initialize memory cache
        memoryCache = new LruCache<String, CacheEntry>(memoryCacheSizeBytes) {
            @Override
            protected int sizeOf(String key, CacheEntry entry) {
                return entry.getSize();
            }
        };
        
        // Initialize disk cache
        File cacheDir = new File(context.getCacheDir(), "data_cache");
        diskCache = DiskLruCache.open(cacheDir, BuildConfig.VERSION_CODE, 1, diskCacheSizeBytes);
    }
    
    // Get data from cache
    public <T> T get(String key, Class<T> type) {
        // Try memory cache first
        CacheEntry entry = memoryCache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getData(type);
        }
        
        // Try disk cache
        try {
            DiskLruCache.Snapshot snapshot = diskCache.get(key);
            if (snapshot != null) {
                try (InputStream in = snapshot.getInputStream(0)) {
                    // Deserialize data
                    T data = deserialize(in, type);
                    
                    // Put in memory cache for faster access next time
                    CacheEntry diskEntry = new CacheEntry(data);
                    memoryCache.put(key, diskEntry);
                    
                    return data;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading from disk cache", e);
        }
        
        return null; // Not found in any cache
    }
    
    // Store data in cache
    public <T> void put(String key, T data, long ttlMillis, int priority) {
        // Create cache entry
        CacheEntry entry = new CacheEntry(data, ttlMillis, priority);
        
        // Store in memory cache
        memoryCache.put(key, entry);
        
        // Store in disk cache
        try {
            DiskLruCache.Editor editor = diskCache.edit(key);
            if (editor != null) {
                try (OutputStream out = editor.newOutputStream(0)) {
                    // Serialize data
                    serialize(data, out);
                    editor.commit();
                } catch (IOException e) {
                    editor.abort();
                    Log.e(TAG, "Error writing to disk cache", e);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error opening disk cache editor", e);
        }
    }
    
    // Additional methods for cache management, invalidation, etc.
}
```

## Image Caching

The ImageCache component provides specialized caching for images with adaptive quality.

### Key Features

1. **Three-tier Caching**
   - Memory cache for fast access to recently used images
   - Disk cache for persistence across app restarts
   - Weak reference cache for recyclable bitmaps

2. **Adaptive Quality**
   - Resolution adjustment based on display capabilities
   - Quality reduction under low memory conditions
   - Format selection (JPEG vs PNG) based on content type

3. **Smart Preloading**
   - Preloading of images based on user navigation patterns
   - Background decoding to avoid UI thread blocking
   - Cancelable operations for better resource management

### Implementation

```java
public class ImageCache {
    // Memory cache for fast access
    private final LruCache<String, Bitmap> memoryCache;
    
    // Disk cache for persistence
    private final DiskLruCache diskCache;
    
    // Weak reference cache for bitmap recycling
    private final Map<String, WeakReference<Bitmap>> recycleCache;
    
    // Background executor for disk operations
    private final ExecutorService diskExecutor;
    
    // Current memory state
    private boolean isLowMemory = false;
    
    // Constructor initializes caches with appropriate sizes
    public ImageCache(Context context) {
        // Calculate memory cache size (1/8 of available memory)
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);
        
        // Initialize memory cache
        memoryCache = new LruCache<String, Bitmap>(maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getAllocationByteCount();
            }
        };
        
        // Initialize disk cache
        File cacheDir = new File(context.getCacheDir(), "image_cache");
        try {
            diskCache = DiskLruCache.open(cacheDir, BuildConfig.VERSION_CODE, 1, 50 * 1024 * 1024); // 50MB
        } catch (IOException e) {
            throw new RuntimeException("Failed to open disk cache", e);
        }
        
        // Initialize recycle cache
        recycleCache = new ConcurrentHashMap<>();
        
        // Initialize executor
        diskExecutor = Executors.newSingleThreadExecutor();
    }
    
    // Get image from cache
    public Bitmap get(String url, int reqWidth, int reqHeight) {
        // Generate cache key
        String key = generateKey(url, reqWidth, reqHeight);
        
        // Try memory cache first
        Bitmap bitmap = memoryCache.get(key);
        if (bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        }
        
        // Try recycle cache
        WeakReference<Bitmap> weakBitmap = recycleCache.get(key);
        if (weakBitmap != null) {
            bitmap = weakBitmap.get();
            if (bitmap != null && !bitmap.isRecycled()) {
                // Move back to memory cache
                memoryCache.put(key, bitmap);
                return bitmap;
            }
        }
        
        // Try disk cache
        try {
            DiskLruCache.Snapshot snapshot = diskCache.get(key);
            if (snapshot != null) {
                try (InputStream in = snapshot.getInputStream(0)) {
                    // Decode image with requested dimensions
                    bitmap = BitmapFactory.decodeStream(in);
                    if (bitmap != null) {
                        // Put in memory cache
                        memoryCache.put(key, bitmap);
                        return bitmap;
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading image from disk cache", e);
        }
        
        return null; // Not found in any cache
    }
    
    // Store image in cache
    public void put(String url, Bitmap bitmap, int reqWidth, int reqHeight) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        
        // Generate cache key
        String key = generateKey(url, reqWidth, reqHeight);
        
        // Apply quality adjustment if in low memory
        if (isLowMemory && bitmap.getByteCount() > 1024 * 1024) { // > 1MB
            bitmap = compressBitmap(bitmap, 80);
        }
        
        // Store in memory cache
        memoryCache.put(key, bitmap);
        
        // Store in disk cache
        diskExecutor.execute(() -> {
            try {
                DiskLruCache.Editor editor = diskCache.edit(key);
                if (editor != null) {
                    try (OutputStream out = editor.newOutputStream(0)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        editor.commit();
                    } catch (IOException e) {
                        editor.abort();
                        Log.e(TAG, "Error writing image to disk cache", e);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Error opening disk cache editor", e);
            }
        });
    }
    
    // Handle memory pressure
    public void setLowMemory(boolean lowMemory) {
        this.isLowMemory = lowMemory;
        
        if (lowMemory) {
            // Move items from memory cache to recycle cache
            for (Map.Entry<String, Bitmap> entry : memoryCache.snapshot().entrySet()) {
                recycleCache.put(entry.getKey(), new WeakReference<>(entry.getValue()));
            }
            
            // Clear memory cache
            memoryCache.evictAll();
        }
    }
    
    // Generate cache key based on URL and dimensions
    private String generateKey(String url, int width, int height) {
        return url + "_" + width + "x" + height;
    }
    
    // Compress bitmap to reduce memory usage
    private Bitmap compressBitmap(Bitmap original, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.JPEG, quality, out);
        byte[] data = out.toByteArray();
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
    
    // Additional methods for cache management, preloading, etc.
}
```

## Audio Caching

The AudioCache component provides specialized caching for audio files with streaming capabilities.

### Key Features

1. **Efficient Storage**
   - Disk-based storage for audio files
   - Background downloading for minimal UI impact
   - File integrity verification

2. **Streaming Support**
   - Progressive downloading for immediate playback
   - Buffer management for smooth playback
   - Caching of streamed content

3. **Preloading Intelligence**
   - Predictive preloading of likely-to-be-used audio
   - Prioritization based on lesson progress
   - Cleanup of unused audio files

### Implementation

```java
public class AudioCache {
    // Disk storage for audio files
    private final File cacheDir;
    
    // Tracking of cached files
    private final Map<String, AudioFile> cachedFiles;
    
    // Background downloading executor
    private final ExecutorService downloadExecutor;
    
    // Network client
    private final OkHttpClient client;
    
    // Constructor initializes cache directory
    public AudioCache(Context context) {
        cacheDir = new File(context.getFilesDir(), "audio_cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        
        cachedFiles = new ConcurrentHashMap<>();
        downloadExecutor = Executors.newFixedThreadPool(2); // Allow 2 concurrent downloads
        
        // Initialize OkHttp client
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        
        // Load existing cached files
        loadExistingFiles();
    }
    
    // Load existing files from cache directory
    private void loadExistingFiles() {
        File[] files = cacheDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String url = getUrlFromFilename(file.getName());
                if (url != null) {
                    AudioFile audioFile = new AudioFile(url, file);
                    cachedFiles.put(url, audioFile);
                }
            }
        }
    }
    
    // Get audio file (returns immediately if cached, otherwise downloads)
    public void getAudio(String url, AudioCallback callback) {
        // Check if already cached
        AudioFile audioFile = cachedFiles.get(url);
        if (audioFile != null && audioFile.file.exists()) {
            // Verify file integrity
            if (audioFile.isValid()) {
                callback.onAudioReady(audioFile.file);
                return;
            } else {
                // File is corrupt, remove it
                audioFile.file.delete();
                cachedFiles.remove(url);
            }
        }
        
        // Need to download
        downloadExecutor.execute(() -> {
            try {
                File file = downloadAudio(url);
                if (file != null) {
                    AudioFile newAudioFile = new AudioFile(url, file);
                    cachedFiles.put(url, newAudioFile);
                    callback.onAudioReady(file);
                } else {
                    callback.onError("Failed to download audio");
                }
            } catch (IOException e) {
                callback.onError("Network error: " + e.getMessage());
            }
        });
    }
    
    // Create audio stream for progressive playback
    public AudioStream createStream(String url) {
        // Check if already cached
        AudioFile audioFile = cachedFiles.get(url);
        if (audioFile != null && audioFile.file.exists() && audioFile.isValid()) {
            return new AudioStream(audioFile.file);
        }
        
        // Create streaming download
        return new ProgressiveAudioStream(url, client, cacheDir);
    }
    
    // Download audio file
    private File downloadAudio(String url) throws IOException {
        // Create temp file
        String filename = getFilenameFromUrl(url);
        File tempFile = new File(cacheDir, filename + ".tmp");
        File finalFile = new File(cacheDir, filename);
        
        // Create request
        Request request = new Request.Builder().url(url).build();
        
        // Execute request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            
            // Save to temp file
            try (ResponseBody body = response.body()) {
                if (body == null) {
                    throw new IOException("Empty response body");
                }
                
                try (InputStream in = body.byteStream();
                     FileOutputStream out = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[8192];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    out.flush();
                }
            }
            
            // Rename to final file
            if (tempFile.renameTo(finalFile)) {
                return finalFile;
            } else {
                throw new IOException("Failed to rename temp file");
            }
        }
    }
    
    // Preload multiple audio files
    public void preloadAudio(List<String> urls) {
        for (String url : urls) {
            if (!cachedFiles.containsKey(url)) {
                downloadExecutor.execute(() -> {
                    try {
                        File file = downloadAudio(url);
                        if (file != null) {
                            AudioFile audioFile = new AudioFile(url, file);
                            cachedFiles.put(url, audioFile);
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Failed to preload audio: " + url, e);
                    }
                });
            }
        }
    }
    
    // Clear unused audio files
    public void clearUnused(List<String> activeUrls) {
        List<String> urlsToRemove = new ArrayList<>();
        
        for (String url : cachedFiles.keySet()) {
            if (!activeUrls.contains(url)) {
                urlsToRemove.add(url);
            }
        }
        
        for (String url : urlsToRemove) {
            AudioFile audioFile = cachedFiles.remove(url);
            if (audioFile != null && audioFile.file.exists()) {
                audioFile.file.delete();
            }
        }
    }
    
    // File representation
    private static class AudioFile {
        final String url;
        final File file;
        
        AudioFile(String url, File file) {
            this.url = url;
            this.file = file;
        }
        
        boolean isValid() {
            return file.exists() && file.length() > 0;
        }
    }
    
    // Callback interface
    public interface AudioCallback {
        void onAudioReady(File audioFile);
        void onError(String errorMessage);
    }
    
    // Helper methods for filename handling
    private String getFilenameFromUrl(String url) {
        String hash = String.valueOf(url.hashCode());
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        return hash + (extension.isEmpty() ? ".mp3" : "." + extension);
    }
    
    private String getUrlFromFilename(String filename) {
        // This is a simplification; in practice, we'd need a mapping table
        // to recover URLs from filenames
        return null;
    }
}
```

## Lessons Caching

The LessonsCache component provides domain-specific caching for lesson content.

### Key Features

1. **Hierarchical Caching**
   - Lesson metadata cached separately from content
   - Vocabulary lists cached with relationships preserved
   - Progressive loading of lesson components

2. **Offline Support**
   - Complete lessons available offline
   - Progress tracking in offline mode
   - Synchronization when back online

3. **Reactive Updates**
   - Observable data for reactive UI updates
   - Change propagation to ensure consistency
   - Event-based notifications for UI updates

### Implementation

```java
public class LessonsCache {
    // Database access
    private final LessonDao lessonDao;
    private final VocabularyDao vocabularyDao;
    
    // Memory cache
    private final Map<Integer, Lesson> lessonCache;
    private final Map<Integer, List<Vocabulary>> vocabularyCache;
    
    // Current lesson tracking
    private int currentLessonId = -1;
    
    // Reactive components for UI updates
    private final PublishSubject<Lesson> lessonSubject;
    
    // Background executor
    private final ExecutorService executor;
    
    // Network client
    private final ApiService apiService;
    
    // Constructor initializes components
    public LessonsCache(LessonDao lessonDao, VocabularyDao vocabularyDao, ApiService apiService) {
        this.lessonDao = lessonDao;
        this.vocabularyDao = vocabularyDao;
        this.apiService = apiService;
        
        lessonCache = new HashMap<>();
        vocabularyCache = new HashMap<>();
        lessonSubject = PublishSubject.create();
        executor = Executors.newSingleThreadExecutor();
    }
    
    // Get lesson with vocabulary
    public Observable<LessonWithVocabulary> getLessonWithVocabulary(int lessonId) {
        // Update current lesson
        currentLessonId = lessonId;
        
        // Try memory cache first
        Lesson lesson = lessonCache.get(lessonId);
        List<Vocabulary> vocabulary = vocabularyCache.get(lessonId);
        
        if (lesson != null && vocabulary != null) {
            // Return cached data as observable
            LessonWithVocabulary data = new LessonWithVocabulary(lesson, vocabulary);
            return Observable.just(data);
        }
        
        // Try database
        return Observable.fromCallable(() -> {
            Lesson dbLesson = lessonDao.getLessonById(lessonId);
            if (dbLesson != null) {
                List<Vocabulary> dbVocabulary = vocabularyDao.getVocabularyForLesson(lessonId);
                
                // Update memory cache
                lessonCache.put(lessonId, dbLesson);
                vocabularyCache.put(lessonId, dbVocabulary);
                
                return new LessonWithVocabulary(dbLesson, dbVocabulary);
            }
            
            // Not in database, need to fetch from network
            return null;
        })
        .subscribeOn(Schedulers.io())
        .flatMap(data -> {
            if (data != null) {
                return Observable.just(data);
            } else {
                // Fetch from network
                return fetchFromNetwork(lessonId);
            }
        })
        .doOnNext(data -> {
            // Notify observers
            lessonSubject.onNext(data.lesson);
        });
    }
    
    // Fetch lesson from network
    private Observable<LessonWithVocabulary> fetchFromNetwork(int lessonId) {
        return apiService.getLesson(lessonId)
            .flatMap(lesson -> {
                // Fetch vocabulary
                return apiService.getVocabulary(lessonId)
                    .map(vocabulary -> {
                        // Save to database
                        executor.execute(() -> {
                            lessonDao.insert(lesson);
                            vocabularyDao.insertAll(vocabulary);
                        });
                        
                        // Update memory cache
                        lessonCache.put(lessonId, lesson);
                        vocabularyCache.put(lessonId, vocabulary);
                        
                        return new LessonWithVocabulary(lesson, vocabulary);
                    });
            });
    }
    
    // Save progress
    public void saveProgress(int lessonId, LessonProgress progress) {
        // Update in database
        executor.execute(() -> {
            // Update lesson progress
            Lesson lesson = lessonDao.getLessonById(lessonId);
            if (lesson != null) {
                lesson.setProgress(progress.getProgressPercent());
                lesson.setLastAccessTime(System.currentTimeMillis());
                lessonDao.update(lesson);
                
                // Update in memory cache
                lessonCache.put(lessonId, lesson);
                
                // Notify observers
                lessonSubject.onNext(lesson);
            }
        });
    }
    
    // Preload next lessons
    public void preloadNextLessons(int currentLessonId, int count) {
        executor.execute(() -> {
            List<Lesson> nextLessons = lessonDao.getNextLessons(currentLessonId, count);
            
            // If we have fewer than requested, fetch more from network
            if (nextLessons.size() < count) {
                try {
                    List<Lesson> networkLessons = apiService.getNextLessons(currentLessonId, count)
                        .blockingGet();
                    
                    for (Lesson lesson : networkLessons) {
                        // Only process if not already cached
                        if (!lessonCache.containsKey(lesson.getId())) {
                            // Save to database
                            lessonDao.insert(lesson);
                            
                            // Fetch and save vocabulary
                            List<Vocabulary> vocabulary = apiService.getVocabulary(lesson.getId())
                                .blockingGet();
                            vocabularyDao.insertAll(vocabulary);
                            
                            // Update memory cache
                            lessonCache.put(lesson.getId(), lesson);
                            vocabularyCache.put(lesson.getId(), vocabulary);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error preloading lessons", e);
                }
            }
        });
    }
    
    // Observe lesson updates
    public Observable<Lesson> observeLessonUpdates() {
        return lessonSubject;
    }
    
    // Clear old lessons from cache
    public void clearOldLessons(int maxLessons) {
        executor.execute(() -> {
            // Get oldest lessons exceeding the limit
            List<Lesson> oldLessons = lessonDao.getOldestLessons(maxLessons);
            
            for (Lesson lesson : oldLessons) {
                // Skip current lesson
                if (lesson.getId() == currentLessonId) {
                    continue;
                }
                
                // Skip lessons with user progress
                if (lesson.getProgress() > 0) {
                    continue;
                }
                
                // Remove from database
                lessonDao.delete(lesson);
                vocabularyDao.deleteByLessonId(lesson.getId());
                
                // Remove from memory cache
                lessonCache.remove(lesson.getId());
                vocabularyCache.remove(lesson.getId());
            }
        });
    }
    
    // Data class for lesson with vocabulary
    public static class LessonWithVocabulary {
        private final Lesson lesson;
        private final List<Vocabulary> vocabulary;
        
        LessonWithVocabulary(Lesson lesson, List<Vocabulary> vocabulary) {
            this.lesson = lesson;
            this.vocabulary = vocabulary;
        }
        
        public Lesson getLesson() {
            return lesson;
        }
        
        public List<Vocabulary> getVocabulary() {
            return vocabulary;
        }
    }
}
```

## Cache Management

The CacheManagerImpl provides centralized control over all caching components.

### Key Features

1. **Unified Interface**
   - Single entry point for all caching operations
   - Coordination between different cache systems
   - Simplified API for application components

2. **Adaptive Behavior**
   - Memory pressure handling
   - Cache size adjustment based on device capabilities
   - Priority-based eviction under constrained resources

3. **Maintenance Operations**
   - Periodic cleanup of unused cached items
   - Verification of cache integrity
   - Cache invalidation when data models change

### Implementation

```java
public class CacheManagerImpl implements CacheManager {
    // Cache components
    private final DataCache dataCache;
    private final ImageCache imageCache;
    private final AudioCache audioCache;
    private final LessonsCache lessonsCache;
    
    // Memory monitoring
    private final MemoryMonitor memoryMonitor;
    
    // Maintenance scheduler
    private final ScheduledExecutorService maintenanceExecutor;
    
    // Context
    private final Context context;
    
    // Constructor initializes components
    public CacheManagerImpl(Context context, MemoryMonitor memoryMonitor) {
        this.context = context.getApplicationContext();
        this.memoryMonitor = memoryMonitor;
        
        // Calculate cache sizes based on device
        int memoryCacheSize = calculateMemoryCacheSize();
        long diskCacheSize = calculateDiskCacheSize();
        
        // Initialize cache components
        dataCache = new DataCache(context, memoryCacheSize / 4, diskCacheSize / 4);
        imageCache = new ImageCache(context, memoryCacheSize / 2, diskCacheSize / 4);
        audioCache = new AudioCache(context, diskCacheSize / 4);
        
        // Initialize lessons cache
        AppDatabase database = AppDatabase.getInstance(context);
        LessonDao lessonDao = database.lessonDao();
        VocabularyDao vocabularyDao = database.vocabularyDao();
        ApiService apiService = ApiServiceFactory.create();
        lessonsCache = new LessonsCache(lessonDao, vocabularyDao, apiService);
        
        // Setup memory pressure handler
        setupMemoryPressureHandler();
        
        // Setup maintenance scheduler
        maintenanceExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduleMaintenance();
    }
    
    // Calculate appropriate memory cache size
    private int calculateMemoryCacheSize() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = activityManager.getMemoryClass();
        
        // Use 1/8 of available memory for caching
        return (memoryClass * 1024 * 1024) / 8;
    }
    
    // Calculate appropriate disk cache size
    private long calculateDiskCacheSize() {
        File cacheDir = context.getCacheDir();
        long usableSpace = cacheDir.getUsableSpace();
        
        // Use up to 10% of available space, max 250MB
        return Math.min(usableSpace / 10, 250 * 1024 * 1024);
    }
    
    // Setup memory pressure handler
    private void setupMemoryPressureHandler() {
        memoryMonitor.addListener(new MemoryMonitor.MemoryListener() {
            @Override
            public void onMemoryWarning(int level, Map<String, Object> memoryInfo) {
                // Adjust caching behavior based on memory warning level
                if (level >= ActivityManager.OnTrimMemoryListener.TRIM_MEMORY_MODERATE) {
                    imageCache.setLowMemory(true);
                    clearLowPriorityCache(50, 80); // More aggressive clearing
                } else if (level >= ActivityManager.OnTrimMemoryListener.TRIM_MEMORY_BACKGROUND) {
                    imageCache.setLowMemory(true);
                    clearLowPriorityCache(30, 50); // Moderate clearing
                }
            }
            
            @Override
            public void onPotentialLeak(String source, float growthRate) {
                // Handle potential leak by clearing more cache
                clearLowPriorityCache(70, 90);
            }
        });
    }
    
    // Schedule periodic maintenance
    private void scheduleMaintenance() {
        // Schedule cleanup every hour
        maintenanceExecutor.scheduleAtFixedRate(this::performMaintenance, 1, 1, TimeUnit.HOURS);
    }
    
    // Perform maintenance tasks
    private void performMaintenance() {
        try {
            // Check available space
            File cacheDir = context.getCacheDir();
            long usableSpace = cacheDir.getUsableSpace();
            long totalSpace = cacheDir.getTotalSpace();
            
            // If less than 10% space available, clear more aggressively
            if (usableSpace < totalSpace / 10) {
                clearLowPriorityCache(60, 90);
            }
            
            // Clear old lessons
            lessonsCache.clearOldLessons(50); // Keep up to 50 lessons
            
            // Verify cache integrity
            verifyImageCacheIntegrity();
            verifyAudioCacheIntegrity();
        } catch (Exception e) {
            Log.e(TAG, "Error during cache maintenance", e);
        }
    }
    
    // Verify image cache integrity
    private void verifyImageCacheIntegrity() {
        // Implementation details
    }
    
    // Verify audio cache integrity
    private void verifyAudioCacheIntegrity() {
        // Implementation details
    }
    
    // Clear low priority cache items
    @Override
    public void clearLowPriorityCache(int memoryPercent, int diskPercent) {
        // Clear memory cache
        if (memoryPercent > 0) {
            dataCache.trimMemoryCache(memoryPercent);
            imageCache.trimMemoryCache(memoryPercent);
        }
        
        // Clear disk cache
        if (diskPercent > 0) {
            dataCache.trimDiskCache(diskPercent);
            imageCache.trimDiskCache(diskPercent);
            audioCache.trimCache(diskPercent);
        }
    }
    
    // Get data cache
    @Override
    public DataCache getDataCache() {
        return dataCache;
    }
    
    // Get image cache
    @Override
    public ImageCache getImageCache() {
        return imageCache;
    }
    
    // Get audio cache
    @Override
    public AudioCache getAudioCache() {
        return audioCache;
    }
    
    // Get lessons cache
    @Override
    public LessonsCache getLessonsCache() {
        return lessonsCache;
    }
    
    // Clear all caches
    @Override
    public void clearAllCaches() {
        dataCache.clearAll();
        imageCache.clearAll();
        audioCache.clearAll();
    }
    
    // Clear cache for specific lesson
    @Override
    public void clearLessonCache(int lessonId) {
        // Clear from memory
        dataCache.invalidateByPrefix("lesson_" + lessonId);
        
        // Clear from database
        lessonsCache.clearLesson(lessonId);
    }
    
    // Preload lesson
    @Override
    public void preloadLesson(int lessonId) {
        // Preload lesson data
        lessonsCache.preloadLesson(lessonId);
        
        // Get lesson resources for preloading
        Lesson lesson = lessonsCache.getLessonById(lessonId).blockingGet();
        if (lesson != null) {
            // Preload audio
            List<String> audioUrls = lesson.getAudioUrls();
            if (audioUrls != null && !audioUrls.isEmpty()) {
                audioCache.preloadAudio(audioUrls);
            }
            
            // Preload images
            List<String> imageUrls = lesson.getImageUrls();
            if (imageUrls != null && !imageUrls.isEmpty()) {
                for (String url : imageUrls) {
                    imageCache.preload(url);
                }
            }
        }
    }
    
    // Release resources
    @Override
    public void release() {
        maintenanceExecutor.shutdown();
        dataCache.release();
        imageCache.release();
        audioCache.release();
    }
}
```

## Implementation Examples

Here are examples of how the caching system is used in different parts of the application:

### Loading and Displaying Images

```java
// In an adapter or activity
private void displayImage(String url, ImageView imageView) {
    // Get image cache from manager
    ImageCache imageCache = cacheManager.getImageCache();
    
    // Calculate required dimensions
    int width = imageView.getWidth();
    int height = imageView.getHeight();
    
    // Fallback dimensions if view not measured yet
    if (width <= 0) width = 300;
    if (height <= 0) height = 300;
    
    // Try to get from cache first
    Bitmap cachedBitmap = imageCache.get(url, width, height);
    if (cachedBitmap != null) {
        imageView.setImageBitmap(cachedBitmap);
        return;
    }
    
    // Set placeholder
    imageView.setImageResource(R.drawable.placeholder);
    
    // Load in background
    executor.execute(() -> {
        try {
            // Download image
            Bitmap bitmap = downloadAndResizeImage(url, width, height);
            
            // Cache the bitmap
            if (bitmap != null) {
                imageCache.put(url, bitmap, width, height);
                
                // Update UI on main thread
                mainHandler.post(() -> {
                    imageView.setImageBitmap(bitmap);
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading image", e);
            
            // Show error on main thread
            mainHandler.post(() -> {
                imageView.setImageResource(R.drawable.error_placeholder);
            });
        }
    });
}
```

### Playing Audio with Caching

```java
// In a lesson activity
private void playAudio(String audioUrl) {
    // Get audio cache
    AudioCache audioCache = cacheManager.getAudioCache();
    
    // Show loading indicator
    progressBar.setVisibility(View.VISIBLE);
    
    // Try to get from cache
    audioCache.getAudio(audioUrl, new AudioCache.AudioCallback() {
        @Override
        public void onAudioReady(File audioFile) {
            // File is ready, play it
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                
                // Initialize media player
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(audioFile.getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    Log.e(TAG, "Error playing audio", e);
                    Toast.makeText(LessonActivity.this, "Error playing audio", Toast.LENGTH_SHORT).show();
                }
            });
        }
        
        @Override
        public void onError(String errorMessage) {
            // Handle error
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LessonActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            });
        }
    });
}
```

### Loading Lesson with Vocabulary

```java
// In a lesson activity
private void loadLesson(int lessonId) {
    // Get lessons cache
    LessonsCache lessonsCache = cacheManager.getLessonsCache();
    
    // Show loading indicator
    loadingView.setVisibility(View.VISIBLE);
    contentView.setVisibility(View.GONE);
    
    // Load lesson with vocabulary
    disposable.add(lessonsCache.getLessonWithVocabulary(lessonId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(data -> {
            // Update UI with lesson data
            loadingView.setVisibility(View.GONE);
            contentView.setVisibility(View.VISIBLE);
            
            // Update title and description
            titleTextView.setText(data.getLesson().getTitle());
            descriptionTextView.setText(data.getLesson().getDescription());
            
            // Update vocabulary list
            vocabularyAdapter.setItems(data.getVocabulary());
            
            // Preload next lessons
            lessonsCache.preloadNextLessons(lessonId, 3);
        }, error -> {
            // Handle error
            loadingView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorTextView.setText("Error loading lesson: " + error.getMessage());
        }));
}
```

### Handling Memory Pressure

```java
// In an activity
@Override
public void onTrimMemory(int level) {
    super.onTrimMemory(level);
    
    // Handle memory pressure
    if (level >= TRIM_MEMORY_MODERATE) {
        // Clear non-essential caches
        cacheManager.clearLowPriorityCache(50, 0); // Clear 50% of memory cache
    } else if (level >= TRIM_MEMORY_BACKGROUND) {
        // Clear some caches
        cacheManager.clearLowPriorityCache(25, 0); // Clear 25% of memory cache
    }
}
```

## Testing and Validation

The caching system is tested and validated using the following methods:

### Unit Tests

```java
@Test
public void testDataCacheOperations() {
    // Create test data
    TestData testData = new TestData("test-data", 123);
    String key = "test_key";
    
    // Put in cache
    dataCache.put(key, testData, 1000, CacheManager.PRIORITY_NORMAL);
    
    // Retrieve from cache
    TestData cachedData = dataCache.get(key, TestData.class);
    
    // Verify data integrity
    assertNotNull(cachedData);
    assertEquals(testData.getName(), cachedData.getName());
    assertEquals(testData.getValue(), cachedData.getValue());
    
    // Test expiration
    Thread.sleep(1100); // Wait for TTL to expire
    TestData expiredData = dataCache.get(key, TestData.class);
    assertNull(expiredData); // Should be null after expiration
}
```

### Integration Tests

```java
@Test
public void testImageCacheIntegration() {
    // Create test image
    Bitmap testBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(testBitmap);
    canvas.drawColor(Color.RED);
    
    String key = "test_image_url";
    
    // Put in cache
    imageCache.put(key, testBitmap, 100, 100);
    
    // Retrieve from memory cache
    Bitmap memoryBitmap = imageCache.get(key, 100, 100);
    assertNotNull(memoryBitmap);
    
    // Clear memory cache
    imageCache.trimMemoryCache(100);
    
    // Verify image still in disk cache
    Bitmap diskBitmap = imageCache.get(key, 100, 100);
    assertNotNull(diskBitmap);
    
    // Verify image dimensions
    assertEquals(100, diskBitmap.getWidth());
    assertEquals(100, diskBitmap.getHeight());
}
```

### Performance Testing

```java
@Test
public void testCachePerformance() {
    // Prepare test data
    List<TestData> testItems = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
        testItems.add(new TestData("item_" + i, i));
    }
    
    // Measure put operations
    long startTime = System.nanoTime();
    for (int i = 0; i < testItems.size(); i++) {
        dataCache.put("key_" + i, testItems.get(i), 60000, CacheManager.PRIORITY_NORMAL);
    }
    long putTime = System.nanoTime() - startTime;
    
    // Measure get operations (memory cache)
    startTime = System.nanoTime();
    for (int i = 0; i < testItems.size(); i++) {
        dataCache.get("key_" + i, TestData.class);
    }
    long getMemoryTime = System.nanoTime() - startTime;
    
    // Clear memory cache
    dataCache.trimMemoryCache(100);
    
    // Measure get operations (disk cache)
    startTime = System.nanoTime();
    for (int i = 0; i < testItems.size(); i++) {
        dataCache.get("key_" + i, TestData.class);
    }
    long getDiskTime = System.nanoTime() - startTime;
    
    // Log performance metrics
    Log.d(TAG, "Cache performance:");
    Log.d(TAG, "  Put operations: " + (putTime / 1000000) + " ms");
    Log.d(TAG, "  Get from memory: " + (getMemoryTime / 1000000) + " ms");
    Log.d(TAG, "  Get from disk: " + (getDiskTime / 1000000) + " ms");
    
    // Assert performance is within acceptable limits
    assertTrue("Memory cache too slow", getMemoryTime < 100 * 1000000); // < 100ms
    assertTrue("Disk cache too slow", getDiskTime < 1000 * 1000000); // < 1s
}
```

## Future Improvements

The caching system could be further improved in the following ways:

1. **Enhanced Cache Prediction**
   - Machine learning-based prediction of user needs
   - Adaptive preloading based on usage patterns
   - Smart cache eviction strategies

2. **Cross-Device Synchronization**
   - Sync cache state across user devices
   - Prioritize frequently accessed content
   - Cloud-based caching for shared resources

3. **Compression Optimizations**
   - Adaptive compression levels based on device capabilities
   - Format-specific optimizations (WebP for images, Opus for audio)
   - Content-aware compression algorithms

4. **Cache Analytics**
   - Detailed metrics on cache hit/miss rates
   - Performance impact analysis
   - User experience correlation with caching efficiency

5. **Security Enhancements**
   - Encrypted cache storage for sensitive content
   - Secure cache invalidation mechanisms
   - Protection against tampering with cached content

These improvements will be considered for future updates to the caching system, based on user feedback and performance metrics from the production app.