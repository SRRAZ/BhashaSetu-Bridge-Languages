# Enhanced Database Schema for English-Hindi Learning App

## Overview

This document provides a comprehensive database schema for the English-Hindi Learning App, designed to support all core functionality including vocabulary storage, lesson management, user progress tracking, quiz features, and spaced repetition learning.

## Entity Relationship Diagram

```
┌────────────────┐          ┌────────────────┐          ┌────────────────┐
│     Word       │          │    Lesson      │          │     Quiz       │
├────────────────┤          ├────────────────┤          ├────────────────┤
│ id (PK)        │          │ id (PK)        │          │ id (PK)        │
│ englishWord    │          │ title          │          │ lessonId (FK)  │
│ hindiWord      │          │ titleHindi     │          │ question       │
│ engPronun      │          │ description    │          │ questionHindi  │
│ hindiPronun    │          │ descriptionHindi│         │ correctAnswer  │
│ category       │          │ category       │          │ wrongAnswer1   │
│ difficulty     │          │ level          │          │ wrongAnswer2   │
│ isFavorite     │          │ orderInCategory│          │ wrongAnswer3   │
│ timeAdded      │          │ content        │          │ explanation    │
│ masteryLevel   │          │ contentHindi   │          │ explanationHindi│
│ exampleEnglish │          │ imageUrl       │          │ type           │
│ exampleHindi   │          │ prerequisites  │          │ difficulty     │
│ notes          │          │ estimatedTime  │          │ mediaUrl       │
│ usageContext   │          │ hasCompleted   │          └────────────────┘
└───────┬────────┘          └───────┬────────┘                 ▲
        │                           │                          │
        │                           │                          │
        ▼                           ▼                          │
┌────────────────┐          ┌────────────────┐                 │
│  LessonWord    │          │  UserProgress  │                 │
├────────────────┤          ├────────────────┤                 │
│ lessonId (FK)  │◄────────►│ id (PK)        │                 │
│ wordId (FK)    │          │ userId         │                 │
│ order          │          │ itemType       │                 │
│ notes          │          │ itemId         │─────────────────┘
└────────────────┘          │ completionLevel│
                            │ lastPracticed  │
                            │ reviewDue      │          ┌────────────────┐
                            │ attemptCount   │          │ UserStatistics │
                            │ correctCount   │          ├────────────────┤
                            │ srLevel        │◄─────────│ userId (PK)    │
                            └───────┬────────┘          │ totalWords     │
                                    │                   │ masteredWords  │
                                    │                   │ lessonsCompleted│
                                    ▼                   │ quizzesTaken   │
                            ┌────────────────┐          │ quizzesPassed  │
                            │    User        │          │ dailyStreak    │
                            ├────────────────┤          │ totalStudyTime │
                            │ id (PK)        │◄─────────│ lastActive     │
                            │ name           │          │ createdDate    │
                            │ email          │          │ achievements   │
                            │ prefLang       │          └────────────────┘
                            │ createdDate    │
                            │ lastLogin      │          ┌────────────────┐
                            │ prefSettings   │          │ StudySession   │
                            └────────────────┘          ├────────────────┤
                                                        │ id (PK)        │
                                                        │ userId (FK)    │
                                                        │ startTime      │
                                                        │ endTime        │
                                                        │ sessionType    │
                                                        │ wordsPracticed │
                                                        │ lessonId       │
                                                        │ quizId         │
                                                        │ performance    │
                                                        └────────────────┘
```

## Tables Detail

### 1. Word Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier for each word |
| englishWord | TEXT | NOT NULL | The English word |
| hindiWord | TEXT | NOT NULL | Hindi translation of the word |
| englishPronunciation | TEXT | | Phonetic pronunciation guide for English |
| hindiPronunciation | TEXT | | Phonetic pronunciation guide for Hindi |
| category | TEXT | NOT NULL | Category the word belongs to (e.g., Greetings, Food) |
| difficulty | INTEGER | NOT NULL, DEFAULT 1 | Difficulty level from 1-5 |
| isFavorite | INTEGER | NOT NULL, DEFAULT 0 | Boolean flag for favorite words (0=false, 1=true) |
| timeAdded | INTEGER | NOT NULL | Timestamp when word was added |
| masteryLevel | INTEGER | NOT NULL, DEFAULT 0 | User's mastery level of this word (0-100%) |
| exampleSentenceEnglish | TEXT | | Example usage in English |
| exampleSentenceHindi | TEXT | | Example usage in Hindi |
| notes | TEXT | | Additional notes about the word |
| usageContext | TEXT | | Contexts where the word is commonly used |
| partOfSpeech | TEXT | | Noun, verb, adjective, etc. |
| synonyms | TEXT | | Comma-separated list of synonyms |
| antonyms | TEXT | | Comma-separated list of antonyms |
| imageUrl | TEXT | | URL to an image representing the word |
| audioUrl | TEXT | | URL to pronunciation audio file |

#### Indexes:
- Index on `englishWord` for fast searches
- Index on `category` for filtering
- Index on `isFavorite` for quick access to favorites
- Index on `masteryLevel` for learning algorithms
- Index on `difficulty` for level-appropriate content

### 2. Lesson Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier for each lesson |
| title | TEXT | NOT NULL | Lesson title in English |
| titleHindi | TEXT | NOT NULL | Lesson title in Hindi |
| description | TEXT | | Brief description in English |
| descriptionHindi | TEXT | | Brief description in Hindi |
| category | TEXT | NOT NULL | Category the lesson belongs to |
| level | TEXT | NOT NULL | Difficulty level (beginner, intermediate, advanced) |
| orderInCategory | INTEGER | NOT NULL | Sequence number within category |
| content | TEXT | | Lesson content in English (HTML/Markdown) |
| contentHindi | TEXT | | Lesson content in Hindi (HTML/Markdown) |
| imageUrl | TEXT | | URL or resource path to lesson image |
| prerequisites | TEXT | | Comma-separated IDs of prerequisite lessons |
| estimatedTimeMinutes | INTEGER | DEFAULT 10 | Estimated completion time |
| hasAudio | INTEGER | DEFAULT 0 | Whether lesson has audio narration |
| hasCompleted | INTEGER | DEFAULT 0 | Whether user has completed this lesson |
| lastUpdated | INTEGER | | Timestamp of last content update |

#### Indexes:
- Index on `category` for filtering
- Index on `level` for difficulty filtering
- Composite index on `category` and `orderInCategory` for sequence retrieval

### 3. LessonWord Junction Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| lessonId | INTEGER | NOT NULL, FOREIGN KEY | Reference to Lesson table |
| wordId | INTEGER | NOT NULL, FOREIGN KEY | Reference to Word table |
| order | INTEGER | NOT NULL, DEFAULT 0 | Order of word appearance in lesson |
| notes | TEXT | | Context-specific notes for this word in this lesson |

#### Constraints:
- Primary key is composite of `lessonId` and `wordId`
- Foreign key constraint on `lessonId` referencing `Lesson(id)`
- Foreign key constraint on `wordId` referencing `Word(id)`

### 4. UserProgress Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier |
| userId | INTEGER | NOT NULL, DEFAULT 1 | User identifier |
| itemType | TEXT | NOT NULL | Type of item ('word', 'lesson', 'quiz') |
| itemId | INTEGER | NOT NULL | ID of the word, lesson, or quiz |
| completionLevel | INTEGER | NOT NULL, DEFAULT 0 | Progress level (0-100%) |
| lastPracticed | INTEGER | | Timestamp of last practice |
| reviewDue | INTEGER | | Timestamp when review is due |
| attemptCount | INTEGER | DEFAULT 0 | Number of practice attempts |
| correctCount | INTEGER | DEFAULT 0 | Number of correct answers |
| srLevel | INTEGER | DEFAULT 0 | Spaced repetition level (0-7) |
| streak | INTEGER | DEFAULT 0 | Consecutive correct answers |
| notes | TEXT | | User-specific notes on this item |

#### Constraints:
- Unique constraint on combination of `userId`, `itemType`, and `itemId`

#### Indexes:
- Index on `userId` for filtering
- Index on `reviewDue` for scheduling reviews
- Composite index on `userId` and `itemType` for filtering by type

### 5. Quiz Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier |
| lessonId | INTEGER | NOT NULL, FOREIGN KEY | Reference to associated lesson |
| question | TEXT | NOT NULL | Quiz question text in English |
| questionHindi | TEXT | NOT NULL | Quiz question text in Hindi |
| correctAnswer | TEXT | NOT NULL | Correct answer text |
| wrongAnswer1 | TEXT | NOT NULL | First incorrect option |
| wrongAnswer2 | TEXT | | Second incorrect option (optional) |
| wrongAnswer3 | TEXT | | Third incorrect option (optional) |
| explanation | TEXT | | Explanation shown after answering (English) |
| explanationHindi | TEXT | | Explanation shown after answering (Hindi) |
| type | TEXT | DEFAULT 'multiple_choice' | Question type (multiple_choice, fill_blank, matching) |
| difficulty | INTEGER | DEFAULT 2 | Question difficulty (1-5) |
| mediaUrl | TEXT | | URL to associated media (image, audio) |

#### Constraints:
- Foreign key constraint on `lessonId` referencing `Lesson(id)`

### 6. User Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier |
| name | TEXT | | User's name |
| email | TEXT | UNIQUE | User's email (for future login) |
| preferredLanguage | TEXT | DEFAULT 'en' | UI language preference (en/hi) |
| createdDate | INTEGER | NOT NULL | Account creation timestamp |
| lastLogin | INTEGER | | Last login timestamp |
| preferenceSettings | TEXT | | JSON string of user preferences |
| profileImageUrl | TEXT | | URL to profile image |
| deviceId | TEXT | | Device identifier |

### 7. UserStatistics Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| userId | INTEGER | PRIMARY KEY, FOREIGN KEY | Reference to User table |
| totalWords | INTEGER | DEFAULT 0 | Total words encountered |
| masteredWords | INTEGER | DEFAULT 0 | Words with mastery level > 80% |
| lessonsCompleted | INTEGER | DEFAULT 0 | Number of completed lessons |
| quizzesTaken | INTEGER | DEFAULT 0 | Number of quizzes attempted |
| quizzesPassed | INTEGER | DEFAULT 0 | Number of quizzes passed |
| dailyStreak | INTEGER | DEFAULT 0 | Consecutive days of activity |
| longestStreak | INTEGER | DEFAULT 0 | Longest daily streak |
| totalStudyTimeMinutes | INTEGER | DEFAULT 0 | Total time spent learning |
| lastActiveDate | INTEGER | | Last activity timestamp |
| createdDate | INTEGER | NOT NULL | Statistics creation timestamp |
| achievementsEarned | TEXT | | JSON array of earned achievements |

### 8. StudySession Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier |
| userId | INTEGER | NOT NULL, FOREIGN KEY | Reference to User table |
| startTime | INTEGER | NOT NULL | Session start timestamp |
| endTime | INTEGER | | Session end timestamp |
| sessionType | TEXT | NOT NULL | Type of session (lesson, word_practice, quiz) |
| wordsPracticed | INTEGER | DEFAULT 0 | Number of words practiced |
| lessonId | INTEGER | | Lesson ID if studying a lesson |
| quizId | INTEGER | | Quiz ID if taking a quiz |
| performance | INTEGER | | Performance score (0-100) |
| notes | TEXT | | Session notes or observations |

#### Constraints:
- Foreign key constraint on `userId` referencing `User(id)`

## Data Access Objects (DAOs)

### WordDao Interface
```java
@Dao
public interface WordDao {
    @Insert
    long insert(Word word);
    
    @Update
    void update(Word word);
    
    @Delete
    void delete(Word word);
    
    @Query("DELETE FROM words")
    void deleteAllWords();
    
    @Query("SELECT COUNT(*) FROM words")
    int getWordCount();
    
    @Query("SELECT * FROM words ORDER BY englishWord ASC")
    LiveData<List<Word>> getAllWords();
    
    @Query("SELECT * FROM words WHERE category = :category ORDER BY englishWord ASC")
    LiveData<List<Word>> getWordsByCategory(String category);
    
    @Query("SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishWord ASC")
    LiveData<List<Word>> getFavoriteWords();
    
    @Query("SELECT * FROM words WHERE englishWord LIKE '%' || :query || '%' OR hindiWord LIKE '%' || :query || '%'")
    LiveData<List<Word>> searchWords(String query);
    
    @Query("SELECT * FROM words ORDER BY masteryLevel ASC LIMIT :limit")
    LiveData<List<Word>> getLeastMasteredWords(int limit);
    
    @Query("SELECT * FROM words WHERE id IN (:wordIds)")
    LiveData<List<Word>> getWordsByIds(List<Integer> wordIds);
    
    @Query("SELECT * FROM words WHERE difficulty <= :maxDifficulty ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Word>> getRandomWordsByDifficulty(int maxDifficulty, int limit);
    
    @Query("SELECT * FROM words WHERE id = :wordId")
    LiveData<Word> getWordById(int wordId);
    
    @Query("SELECT DISTINCT category FROM words ORDER BY category ASC")
    LiveData<List<String>> getAllCategories();
    
    @Transaction
    @Query("SELECT w.* FROM words w " +
           "INNER JOIN lesson_words lw ON w.id = lw.wordId " +
           "WHERE lw.lessonId = :lessonId ORDER BY lw.`order` ASC")
    LiveData<List<Word>> getWordsForLesson(int lessonId);
}
```

### LessonDao Interface
```java
@Dao
public interface LessonDao {
    @Insert
    long insert(Lesson lesson);
    
    @Update
    void update(Lesson lesson);
    
    @Delete
    void delete(Lesson lesson);
    
    @Query("SELECT * FROM lessons ORDER BY category, orderInCategory ASC")
    LiveData<List<Lesson>> getAllLessons();
    
    @Query("SELECT * FROM lessons WHERE category = :category ORDER BY orderInCategory ASC")
    LiveData<List<Lesson>> getLessonsByCategory(String category);
    
    @Query("SELECT * FROM lessons WHERE level = :level ORDER BY category, orderInCategory ASC")
    LiveData<List<Lesson>> getLessonsByLevel(String level);
    
    @Query("SELECT * FROM lessons WHERE id = :id")
    LiveData<Lesson> getLessonById(int id);
    
    @Query("SELECT * FROM lessons WHERE hasCompleted = 0 ORDER BY level, orderInCategory ASC LIMIT 1")
    LiveData<Lesson> getNextIncompleteLesson();
    
    @Query("SELECT DISTINCT category FROM lessons ORDER BY category ASC")
    LiveData<List<String>> getAllCategories();
    
    @Query("SELECT * FROM lessons WHERE id IN " +
           "(SELECT lessonId FROM lesson_words WHERE wordId = :wordId)")
    LiveData<List<Lesson>> getLessonsContainingWord(int wordId);
    
    @Insert
    void insertLessonWord(LessonWord lessonWord);
    
    @Query("DELETE FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId")
    void removeLessonWord(int lessonId, int wordId);
    
    @Transaction
    @Query("SELECT EXISTS(SELECT 1 FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId)")
    boolean lessonContainsWord(int lessonId, int wordId);
}
```

### UserProgressDao Interface
```java
@Dao
public interface UserProgressDao {
    @Insert
    long insert(UserProgress progress);
    
    @Update
    void update(UserProgress progress);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    LiveData<List<UserProgress>> getAllProgressForUser(int userId);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND itemType = 'word' AND itemId = :wordId")
    LiveData<UserProgress> getWordProgress(int userId, int wordId);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND itemType = 'lesson' AND itemId = :lessonId")
    LiveData<UserProgress> getLessonProgress(int userId, int lessonId);
    
    @Query("SELECT AVG(completionLevel) FROM user_progress WHERE userId = :userId")
    LiveData<Float> getOverallProgress(int userId);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND reviewDue <= :currentTime " +
           "AND itemType = 'word' ORDER BY reviewDue ASC LIMIT :limit")
    LiveData<List<UserProgress>> getDueWordReviews(int userId, long currentTime, int limit);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND itemType = 'word' " + 
           "ORDER BY completionLevel ASC LIMIT :limit")
    LiveData<List<UserProgress>> getLeastMasteredItems(int userId, int limit);
    
    @Query("UPDATE user_progress SET completionLevel = :level WHERE userId = :userId " +
           "AND itemType = :itemType AND itemId = :itemId")
    void updateCompletionLevel(int userId, String itemType, int itemId, int level);
    
    @Query("UPDATE user_progress SET reviewDue = :nextReview WHERE id = :progressId")
    void updateNextReview(int progressId, long nextReview);
    
    @Query("UPDATE user_progress SET srLevel = :newLevel WHERE id = :progressId")
    void updateSpacedRepetitionLevel(int progressId, int newLevel);
}
```

### QuizDao Interface
```java
@Dao
public interface QuizDao {
    @Insert
    long insert(Quiz quiz);
    
    @Update
    void update(Quiz quiz);
    
    @Delete
    void delete(Quiz quiz);
    
    @Query("SELECT * FROM quizzes WHERE lessonId = :lessonId")
    LiveData<List<Quiz>> getQuizzesByLesson(int lessonId);
    
    @Query("SELECT * FROM quizzes ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Quiz>> getRandomQuizzes(int limit);
    
    @Query("SELECT * FROM quizzes WHERE difficulty <= :maxDifficulty ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Quiz>> getQuizzesByDifficulty(int maxDifficulty, int limit);
    
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    LiveData<Quiz> getQuizById(int quizId);
    
    @Query("SELECT COUNT(*) FROM quizzes WHERE lessonId = :lessonId")
    int getQuizCountForLesson(int lessonId);
}
```

### UserDao Interface
```java
@Dao
public interface UserDao {
    @Insert
    long insert(User user);
    
    @Update
    void update(User user);
    
    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserById(int userId);
    
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    LiveData<User> getUserByEmail(String email);
    
    @Query("UPDATE users SET preferredLanguage = :langCode WHERE id = :userId")
    void updateLanguagePreference(int userId, String langCode);
    
    @Query("UPDATE users SET lastLogin = :timestamp WHERE id = :userId")
    void updateLastLogin(int userId, long timestamp);
}
```

### UserStatisticsDao Interface
```java
@Dao
public interface UserStatisticsDao {
    @Insert
    long insert(UserStatistics stats);
    
    @Update
    void update(UserStatistics stats);
    
    @Query("SELECT * FROM user_statistics WHERE userId = :userId")
    LiveData<UserStatistics> getStatisticsForUser(int userId);
    
    @Query("UPDATE user_statistics SET dailyStreak = dailyStreak + 1 WHERE userId = :userId")
    void incrementDailyStreak(int userId);
    
    @Query("UPDATE user_statistics SET dailyStreak = 0 WHERE userId = :userId")
    void resetDailyStreak(int userId);
    
    @Query("UPDATE user_statistics SET totalStudyTimeMinutes = totalStudyTimeMinutes + :minutes WHERE userId = :userId")
    void addStudyTime(int userId, int minutes);
    
    @Query("UPDATE user_statistics SET lessonsCompleted = lessonsCompleted + 1 WHERE userId = :userId")
    void incrementLessonsCompleted(int userId);
    
    @Query("UPDATE user_statistics SET masteredWords = " +
           "(SELECT COUNT(*) FROM user_progress WHERE userId = :userId AND itemType = 'word' " +
           "AND completionLevel >= 80) WHERE userId = :userId")
    void updateMasteredWordsCount(int userId);
}
```

### StudySessionDao Interface
```java
@Dao
public interface StudySessionDao {
    @Insert
    long insert(StudySession session);
    
    @Update
    void update(StudySession session);
    
    @Query("SELECT * FROM study_sessions WHERE userId = :userId ORDER BY startTime DESC LIMIT :limit")
    LiveData<List<StudySession>> getRecentSessions(int userId, int limit);
    
    @Query("SELECT * FROM study_sessions WHERE userId = :userId AND endTime IS NULL")
    LiveData<StudySession> getActiveSessions(int userId);
    
    @Query("UPDATE study_sessions SET endTime = :endTime, performance = :performance " +
           "WHERE id = :sessionId")
    void completeSession(int sessionId, long endTime, int performance);
    
    @Query("SELECT SUM(wordsPracticed) FROM study_sessions WHERE userId = :userId " +
           "AND startTime >= :startTime")
    int getWordsPracticedSince(int userId, long startTime);
}
```

## Database Migration Strategy

The app will implement a robust migration strategy to handle database schema changes in future updates:

1. **Version Management**:
   - Each schema version will have a clear migration path
   - Version numbers will increment with schema changes

2. **Migration Classes**:
   - Room's Migration system will handle version transitions
   - Example migration from version 1 to 2:

   ```java
   static final Migration MIGRATION_1_2 = new Migration(1, 2) {
       @Override
       public void migrate(SupportSQLiteDatabase database) {
           // Add 'exampleSentenceEnglish' and 'exampleSentenceHindi' columns to Word table
           database.execSQL("ALTER TABLE words ADD COLUMN exampleSentenceEnglish TEXT");
           database.execSQL("ALTER TABLE words ADD COLUMN exampleSentenceHindi TEXT");
       }
   };
   ```

3. **Fallback Strategy**:
   - Destructive migration as a last resort with user warning
   - Data backup before destructive migrations
   - Export/import functionality for user data

4. **Migration Testing**:
   - Automated tests for each migration path
   - Verification of data integrity post-migration
   - Performance testing of migration process

## Sample Data and Initialization

The database will be pre-populated with essential content to provide immediate value:

1. **Basic Vocabulary**:
   - Common greetings (Hello, Thank you, etc.)
   - Numbers and basic counting words
   - Common nouns for everyday objects
   - Basic verbs for common actions

2. **Starter Lessons**:
   - Introduction to English pronunciation
   - Basic conversation starters
   - Common phrases for travel
   - Simple sentence structures

3. **Initial Quizzes**:
   - Matching Hindi to English words
   - Simple fill-in-the-blank exercises
   - Basic vocabulary identification

4. **System Setup**:
   - Default user creation
   - Initial statistics establishment
   - First-time user experience flags

## Performance Considerations

1. **Indexing Strategy**:
   - Strategic indexes on frequently queried columns
   - Composite indexes for complex queries
   - Avoiding over-indexing to maintain write performance

2. **Query Optimization**:
   - Use of join operations for efficient data retrieval
   - Pre-computed values for frequently accessed statistics
   - Query result caching where appropriate

3. **Batch Operations**:
   - Bulk inserts for initial data loading
   - Transaction wrapping for related operations
   - Background processing for non-critical updates

4. **Data Volume Management**:
   - Progressive content loading based on user progress
   - Lazy loading of media content
   - Archiving of inactive user data