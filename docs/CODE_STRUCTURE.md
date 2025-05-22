# Code Structure Documentation

This document provides a comprehensive overview of the code structure for the English-Hindi Learning application, detailing the architecture, package organization, and component relationships.

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Package Structure](#package-structure)
3. [Key Components](#key-components)
   - [Activities and Fragments](#activities-and-fragments)
   - [ViewModels](#viewmodels)
   - [Data Layer](#data-layer)
   - [Performance Components](#performance-components)
4. [Dependency Injection](#dependency-injection)
5. [Threading Model](#threading-model)
6. [Testing Structure](#testing-structure)
7. [Best Practices](#best-practices)

## Architecture Overview

The English-Hindi Learning app follows the MVVM (Model-View-ViewModel) architecture pattern with clean architecture principles. The application is structured in layers:

1. **Presentation Layer**: Activities, Fragments, and ViewModels
2. **Domain Layer**: Use cases, business logic, and domain models
3. **Data Layer**: Repositories, DAOs, and data sources (local database and network)
4. **Core/Common**: Utilities, base classes, and shared components

This separation of concerns ensures:
- Better testability with clear boundaries
- Improved code organization and readability
- Easier maintenance and feature development
- Performance optimization at appropriate abstraction levels

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                      │
│                                                             │
│  ┌─────────────┐       ┌─────────────┐       ┌──────────┐  │
│  │  Activities │◄─────►│  Fragments  │◄─────►│ Adapters │  │
│  └─────────────┘       └─────────────┘       └──────────┘  │
│          ▲                     ▲                   ▲       │
│          │                     │                   │       │
│          ▼                     ▼                   │       │
│  ┌─────────────────────────────────────────┐      │       │
│  │             ViewModels                  │◄─────┘       │
│  └─────────────────────────────────────────┘              │
└────────────────────────┬────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                           │
│                                                             │
│  ┌─────────────┐       ┌─────────────┐       ┌──────────┐  │
│  │  Use Cases  │◄─────►│ Domain Model│◄─────►│ Entities │  │
│  └─────────────┘       └─────────────┘       └──────────┘  │
└────────────────────────┬────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                       Data Layer                            │
│                                                             │
│  ┌─────────────┐       ┌─────────────┐       ┌──────────┐  │
│  │ Repositories│◄─────►│    DAOs     │◄─────►│   Room   │  │
│  └─────────────┘       └─────────────┘       └──────────┘  │
│          ▲                     ▲                           │
│          │                     │                           │
│          ▼                     │                           │
│  ┌─────────────┐               │                           │
│  │ Remote API  │               │                           │
│  └─────────────┘               │                           │
└─────────────────────────────────────────────────────────────┘
```

## Package Structure

The application code is organized into the following package structure:

```
com.example.englishhindi/
├── activity/          # Activities and activity-related classes
├── adapter/           # RecyclerView and other adapters
├── audio/             # Audio playback and processing
├── base/              # Base classes (BaseActivity, BaseFragment, etc.)
├── cache/             # Caching mechanisms for data, images, audio
├── data/              # Data layer components
│   ├── dao/           # Data Access Objects for Room
│   ├── db/            # Database configuration
│   ├── model/         # Data models and entities
│   ├── relation/      # Room relationships between entities
│   ├── repository/    # Repository implementations
│   └── util/          # Data utility classes
├── database/          # Database-specific components
│   └── converters/    # Type converters for Room
├── debug/             # Debug utilities and configurations
├── di/                # Dependency injection modules
├── fragment/          # Fragments and fragment-related classes
├── games/             # Learning games implementation
├── manager/           # Manager classes for various subsystems
├── model/             # Domain models
│   ├── exercise/      # Exercise-related models
│   └── gamification/  # Gamification-related models
├── monitoring/        # Performance monitoring components
├── pronunciation/     # Pronunciation evaluation components
├── receiver/          # Broadcast receivers
├── repository/        # Domain repositories
├── service/           # Background services
├── testing/           # Test configurations and utilities
├── ui/                # UI components
│   ├── components/    # Reusable UI components
│   └── viewmodel/     # View models for UI
├── util/              # Utility classes
├── view/              # Custom views
└── viewmodel/         # Additional view models
```

## Key Components

### Activities and Fragments

Activities and fragments form the UI layer of the application. All activities extend `BaseActivity` or `PerformanceMonitoredActivity`, which provides:

- Lifecycle management with performance tracking
- Memory optimization behaviors
- UI state restoration
- Common navigation patterns

Key activities include:

1. **MainActivity**: The entry point for the application, implementing bottom navigation
2. **LessonActivity**: Displays lesson content and vocabulary
3. **ExerciseActivity**: Hosts various exercise types for practice
4. **ProfileActivity**: Shows user progress and achievements

Fragments follow a similar pattern, extending `BaseFragment` or `PerformanceMonitoredFragment`:

1. **HomeFragment**: Dashboard with recent progress and suggestions
2. **LessonListFragment**: Displays available lessons
3. **VocabularyFragment**: Shows vocabulary items with search and filtering
4. **SettingsFragment**: Application settings and preferences

### ViewModels

ViewModels serve as the connection between UI components and the domain layer. They:

- Manage UI state and expose LiveData/Observable data
- Execute business logic via use cases
- Handle data transformations for the UI
- Implement error handling and loading states

Key ViewModels include:

1. **LessonViewModel**: Manages lesson data and progression
2. **VocabularyViewModel**: Handles vocabulary lists and filtering
3. **ExerciseViewModel**: Controls exercise flow and scoring
4. **ProfileViewModel**: Manages user profile and achievement data

### Data Layer

The data layer manages data access and storage:

1. **Repositories**: Implement data access strategy
   - **LessonRepository**: Manages lesson data access
   - **VocabularyRepository**: Handles vocabulary data
   - **UserProgressRepository**: Tracks user progress
   - **SettingsRepository**: Manages app settings

2. **Room Database**:
   - **AppDatabase**: Main database configuration
   - **DAOs**: Data Access Objects for each entity
   - **Entities**: Database models with Room annotations
   - **Relations**: Cross-reference relationships

3. **Network API**:
   - **ApiService**: Retrofit interfaces for network calls
   - **NetworkResponseHandler**: Handles API responses
   - **NetworkUtils**: Network-related utilities

### Performance Components

Performance optimization is integrated throughout the codebase:

1. **Monitoring**:
   - **PerformanceMonitoringManager**: Central monitoring manager
   - **PerformanceMonitor**: Tracks frame rates and execution times
   - **MemoryMonitor**: Tracks memory usage
   - **NetworkMonitor**: Monitors network operations

2. **Caching**:
   - **CacheManager**: Centralized cache management
   - **DataCache**: Generic data caching
   - **ImageCache**: Specialized image caching
   - **AudioCache**: Audio file caching
   - **LessonsCache**: Domain-specific lesson caching

3. **Optimization Utilities**:
   - **MemoryOptimizer**: Memory usage optimization
   - **DatabaseOptimizer**: Database performance optimization
   - **UIPerformanceOptimizer**: UI rendering optimization
   - **NetworkOptimizer**: Network request optimization

## Dependency Injection

The application uses a lightweight dependency injection pattern:

```java
// Example dependency injection setup
public class EnglishHindiApplication extends Application {
    private AppDatabase database;
    private CacheManager cacheManager;
    private PerformanceMonitoringManager performanceMonitoringManager;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize components
        database = AppDatabase.getInstance(this);
        
        // Create memory monitor before cache manager
        MemoryMonitor memoryMonitor = new MemoryMonitor(this);
        cacheManager = new CacheManagerImpl(this, memoryMonitor);
        
        // Initialize performance monitoring
        performanceMonitoringManager = PerformanceMonitoringManager.getInstance(this);
        performanceMonitoringManager.enable(MonitoringLevel.STANDARD);
    }
    
    // Accessor methods for dependencies
    public AppDatabase getDatabase() {
        return database;
    }
    
    public CacheManager getCacheManager() {
        return cacheManager;
    }
    
    public PerformanceMonitoringManager getPerformanceMonitoringManager() {
        return performanceMonitoringManager;
    }
}
```

Accessing dependencies:

```java
public class MyActivity extends BaseActivity {
    private AppDatabase database;
    private LessonRepository lessonRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get dependencies
        EnglishHindiApplication app = (EnglishHindiApplication) getApplication();
        database = app.getDatabase();
        
        // Create repositories
        lessonRepository = new LessonRepositoryImpl(
            database.lessonDao(),
            app.getCacheManager().getDataCache()
        );
    }
}
```

## Threading Model

The application uses a structured threading model to ensure smooth UI and efficient background processing:

1. **Main Thread**: UI operations and short-lived work
2. **IO Thread Pool**: Network and disk operations
3. **Computation Thread Pool**: CPU-intensive tasks
4. **Database Thread**: Database operations

Threading is managed through:

```java
// Example threading in repository
public class LessonRepositoryImpl implements LessonRepository {
    private final LessonDao lessonDao;
    private final Executor diskIOExecutor;
    private final Executor networkExecutor;
    private final Executor computationExecutor;
    private final Handler mainThreadHandler;
    
    public LessonRepositoryImpl(LessonDao lessonDao, ExecutorProvider executorProvider) {
        this.lessonDao = lessonDao;
        this.diskIOExecutor = executorProvider.diskIO();
        this.networkExecutor = executorProvider.network();
        this.computationExecutor = executorProvider.computation();
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }
    
    @Override
    public void getLessons(final LessonCallback callback) {
        // Execute database operation on disk IO thread
        diskIOExecutor.execute(() -> {
            final List<Lesson> lessons = lessonDao.getAllLessons();
            
            // If empty, fetch from network
            if (lessons.isEmpty()) {
                fetchLessonsFromNetwork(callback);
                return;
            }
            
            // Deliver result on main thread
            mainThreadHandler.post(() -> callback.onLessonsLoaded(lessons));
        });
    }
    
    private void fetchLessonsFromNetwork(final LessonCallback callback) {
        // Execute network call on network thread
        networkExecutor.execute(() -> {
            try {
                // Network operation
                final List<Lesson> lessons = apiService.getLessons().execute().body();
                
                // Save to database on disk thread
                diskIOExecutor.execute(() -> {
                    lessonDao.insertAll(lessons);
                    
                    // Deliver result on main thread
                    mainThreadHandler.post(() -> callback.onLessonsLoaded(lessons));
                });
            } catch (Exception e) {
                // Handle error on main thread
                mainThreadHandler.post(() -> callback.onError(e));
            }
        });
    }
}
```

## Testing Structure

The application includes a comprehensive testing strategy:

```
test/                    # Unit tests
├── java/
│   └── com/
│       └── example/
│           └── englishhindi/
│               └── unit/       # Unit tests for business logic
│
androidTest/             # Android instrumentation tests
├── java/
│   └── com/
│       └── example/
│           └── englishhindi/
│               ├── performance/ # Performance tests
│               └── ui/          # UI tests
```

### Unit Tests

Unit tests focus on testing business logic and repositories:

```java
// Example unit test for LessonRepository
@RunWith(JUnit4.class)
public class LessonRepositoryTest {
    private LessonRepository repository;
    private LessonDao mockLessonDao;
    
    @Before
    public void setUp() {
        mockLessonDao = mock(LessonDao.class);
        repository = new LessonRepositoryImpl(mockLessonDao, TestExecutorProvider.getInstance());
    }
    
    @Test
    public void getLessons_returnsLessonsFromDao() {
        // Arrange
        List<Lesson> expectedLessons = createTestLessons();
        when(mockLessonDao.getAllLessons()).thenReturn(expectedLessons);
        
        // Act
        TestLessonCallback callback = new TestLessonCallback();
        repository.getLessons(callback);
        
        // Wait for async operation
        callback.await();
        
        // Assert
        verify(mockLessonDao).getAllLessons();
        assertEquals(expectedLessons, callback.getLessons());
    }
}
```

### Instrumentation Tests

Instrumentation tests focus on UI and integration testing:

```java
// Example UI test
@RunWith(AndroidJUnit4.class)
public class LessonActivityTest {
    @Rule
    public ActivityScenarioRule<LessonActivity> activityRule =
            new ActivityScenarioRule<>(LessonActivity.class);
    
    @Test
    public void displayLesson_showsCorrectContent() {
        // Verify title is displayed
        onView(withId(R.id.lesson_title))
                .check(matches(isDisplayed()));
        
        // Verify vocabulary list is displayed
        onView(withId(R.id.vocabulary_list))
                .check(matches(isDisplayed()));
        
        // Click on first vocabulary item
        onView(withId(R.id.vocabulary_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        
        // Verify vocabulary detail is displayed
        onView(withId(R.id.vocabulary_detail))
                .check(matches(isDisplayed()));
    }
}
```

### Performance Tests

Performance tests measure the app's performance:

```java
// Example performance test
@RunWith(AndroidJUnit4.class)
public class DatabasePerformanceTest {
    private AppDatabase db;
    private LessonDao lessonDao;
    
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        lessonDao = db.lessonDao();
    }
    
    @After
    public void closeDb() {
        db.close();
    }
    
    @Test
    public void insertAndQueryLessons_measuresPerformance() {
        // Prepare test data
        List<Lesson> lessons = TestDataGenerator.createTestLessons(100);
        
        // Measure insert performance
        long startTime = System.nanoTime();
        lessonDao.insertAll(lessons);
        long insertTime = System.nanoTime() - startTime;
        
        // Measure query performance
        startTime = System.nanoTime();
        List<Lesson> loadedLessons = lessonDao.getAllLessons();
        long queryTime = System.nanoTime() - startTime;
        
        // Log performance metrics
        Log.d("PerformanceTest", "Insert time: " + insertTime / 1_000_000 + "ms");
        Log.d("PerformanceTest", "Query time: " + queryTime / 1_000_000 + "ms");
        
        // Assert performance meets requirements
        assertTrue("Insert too slow", insertTime < 1_000_000_000); // < 1s
        assertTrue("Query too slow", queryTime < 100_000_000);    // < 100ms
    }
}
```

## Best Practices

### Code Organization

1. **Single Responsibility**: Each class has a single responsibility
2. **Explicit Dependencies**: Dependencies are explicitly passed to components
3. **Meaningful Names**: Classes, methods, and variables have descriptive names
4. **Consistent Style**: Code follows a consistent style throughout the codebase
5. **Documentation**: Critical components are well-documented

### Performance Optimizations

1. **Background Processing**: Long-running operations run on appropriate threads
2. **Memory Management**: Resources are properly released and memory leaks prevented
3. **Efficient UI**: UI components are optimized for performant rendering
4. **Database Efficiency**: Database queries and transactions are optimized
5. **Lazy Loading**: Resources are loaded only when needed

### Error Handling

1. **Graceful Degradation**: The app continues to function even when parts fail
2. **Meaningful Error Messages**: Errors are reported with actionable information
3. **Recovery Mechanisms**: The app attempts to recover from errors when possible
4. **Logging**: Errors are logged for diagnosis and improvement

### Testing Approach

1. **Test-Driven Development**: Critical components are developed with tests first
2. **Comprehensive Coverage**: Different types of tests cover the entire app
3. **Isolated Tests**: Tests are isolated and don't depend on external systems
4. **Performance Testing**: Critical paths have performance tests
5. **Continuous Integration**: Tests run automatically on code changes