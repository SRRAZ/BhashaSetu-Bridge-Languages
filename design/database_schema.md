# English-Hindi Learning App Database Schema

## Overview

The database schema is designed to support all the core functionality of the English-Hindi Learning App, including vocabulary storage, lesson management, user progress tracking, and quiz features.

## Entity Relationship Diagram

```
+----------------+       +----------------+       +----------------+
|    Word        |       |    Lesson      |       |     Quiz       |
+----------------+       +----------------+       +----------------+
| id (PK)        |       | id (PK)        |       | id (PK)        |
| englishWord    |       | title          |       | lessonId (FK)  |
| hindiWord      |       | description    |       | question       |
| engPronun      |       | category       |       | correctAnswer  |
| hindiPronun    |       | level          |       | wrongAnswer1   |
| category       |       | order          |       | wrongAnswer2   |
| difficulty     |       | content        |       | wrongAnswer3   |
| isFavorite     |       | imageUrl       |       | explanationText|
| timeAdded      |       | wordIds        |       +----------------+
| masteryLevel   |       +----------------+
+----------------+               |
        |                        |
        |                        |
+----------------+       +----------------+
|  LessonWords   |       |   Progress     |
+----------------+       +----------------+
| id (PK)        |       | id (PK)        |
| lessonId (FK)  |       | userId         |
| wordId (FK)    |       | wordId (FK)    |
+----------------+       | lessonId (FK)  |
                         | completion     |
                         | lastPracticed  |
                         | reviewDue      |
                         +----------------+
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

#### Indexes:
- Index on `englishWord` for fast searches
- Index on `category` for filtering
- Index on `isFavorite` for quick access to favorites

### 2. Lesson Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier for each lesson |
| title | TEXT | NOT NULL | Lesson title |
| description | TEXT | | Brief description of the lesson |
| category | TEXT | NOT NULL | Category the lesson belongs to |
| level | TEXT | NOT NULL | Difficulty level (beginner, intermediate, advanced) |
| order | INTEGER | NOT NULL | Sequence number within category |
| content | TEXT | | Lesson content in HTML or Markdown format |
| imageUrl | TEXT | | URL or resource path to lesson image |

#### Indexes:
- Index on `category` for filtering
- Index on `level` for difficulty filtering
- Composite index on `category` and `order` for sequence retrieval

### 3. LessonWords Junction Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier |
| lessonId | INTEGER | NOT NULL, FOREIGN KEY | Reference to Lesson table |
| wordId | INTEGER | NOT NULL, FOREIGN KEY | Reference to Word table |

#### Constraints:
- Foreign key constraint on `lessonId` referencing `Lesson(id)`
- Foreign key constraint on `wordId` referencing `Word(id)`
- Unique constraint on the combination of `lessonId` and `wordId`

### 4. Progress Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier |
| userId | INTEGER | NOT NULL, DEFAULT 1 | User identifier (for future multi-user support) |
| wordId | INTEGER | FOREIGN KEY | Reference to Word table (can be NULL) |
| lessonId | INTEGER | FOREIGN KEY | Reference to Lesson table (can be NULL) |
| completionLevel | INTEGER | NOT NULL, DEFAULT 0 | Progress level (0-100%) |
| lastPracticed | INTEGER | | Timestamp of last practice |
| reviewDue | INTEGER | | Timestamp when review is due (for spaced repetition) |

#### Constraints:
- Foreign key constraint on `wordId` referencing `Word(id)`
- Foreign key constraint on `lessonId` referencing `Lesson(id)`
- One of `wordId` or `lessonId` must not be NULL

#### Indexes:
- Index on `userId` for future multi-user support
- Index on `reviewDue` for scheduling reviews

### 5. Quiz Table

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| id | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier |
| lessonId | INTEGER | NOT NULL, FOREIGN KEY | Reference to associated lesson |
| question | TEXT | NOT NULL | Quiz question text |
| correctAnswer | TEXT | NOT NULL | Correct answer text |
| wrongAnswer1 | TEXT | NOT NULL | First incorrect option |
| wrongAnswer2 | TEXT | | Second incorrect option (optional) |
| wrongAnswer3 | TEXT | | Third incorrect option (optional) |
| explanationText | TEXT | | Explanation shown after answering |

#### Constraints:
- Foreign key constraint on `lessonId` referencing `Lesson(id)`

## Data Access Objects (DAOs)

### WordDao Interface
```java
@Dao
public interface WordDao {
    @Insert
    void insert(Word word);
    
    @Update
    void update(Word word);
    
    @Delete
    void delete(Word word);
    
    @Query("DELETE FROM words")
    void deleteAllWords();
    
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
}
```

### LessonDao Interface
```java
@Dao
public interface LessonDao {
    @Insert
    void insert(Lesson lesson);
    
    @Update
    void update(Lesson lesson);
    
    @Delete
    void delete(Lesson lesson);
    
    @Query("SELECT * FROM lessons ORDER BY category, `order` ASC")
    LiveData<List<Lesson>> getAllLessons();
    
    @Query("SELECT * FROM lessons WHERE category = :category ORDER BY `order` ASC")
    LiveData<List<Lesson>> getLessonsByCategory(String category);
    
    @Query("SELECT * FROM lessons WHERE level = :level ORDER BY category, `order` ASC")
    LiveData<List<Lesson>> getLessonsByLevel(String level);
    
    @Query("SELECT * FROM lessons WHERE id = :id")
    LiveData<Lesson> getLessonById(int id);
    
    @Query("SELECT * FROM lessons WHERE id = (SELECT lessonId FROM progress WHERE userId = :userId ORDER BY completionLevel DESC LIMIT 1)")
    LiveData<Lesson> getCurrentLesson(int userId);
}
```

### ProgressDao Interface
```java
@Dao
public interface ProgressDao {
    @Insert
    void insert(Progress progress);
    
    @Update
    void update(Progress progress);
    
    @Query("SELECT * FROM progress WHERE userId = :userId")
    LiveData<List<Progress>> getAllProgressForUser(int userId);
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND wordId = :wordId")
    LiveData<Progress> getWordProgress(int userId, int wordId);
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND lessonId = :lessonId")
    LiveData<Progress> getLessonProgress(int userId, int lessonId);
    
    @Query("SELECT AVG(completionLevel) FROM progress WHERE userId = :userId")
    LiveData<Float> getOverallProgress(int userId);
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND reviewDue <= :currentTime ORDER BY reviewDue ASC")
    LiveData<List<Progress>> getDueReviews(int userId, long currentTime);
}
```

### QuizDao Interface
```java
@Dao
public interface QuizDao {
    @Insert
    void insert(Quiz quiz);
    
    @Update
    void update(Quiz quiz);
    
    @Delete
    void delete(Quiz quiz);
    
    @Query("SELECT * FROM quiz WHERE lessonId = :lessonId")
    LiveData<List<Quiz>> getQuizzesByLesson(int lessonId);
    
    @Query("SELECT * FROM quiz ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Quiz>> getRandomQuizzes(int limit);
}
```

## Database Migration Strategy

The app will include a migration strategy to handle database schema changes in future updates:

1. Each schema version will have a migration class
2. Room's Migration system will be used to handle version changes
3. Fallback to destructive migration if needed (with prior data backup)
4. Migration tests will ensure data integrity during upgrades

## Sample Data

Initial database will be pre-populated with:
- Common vocabulary words in different categories
- Basic beginner lessons with simple content
- Starter quizzes for each lesson
- Default progress entries for new users