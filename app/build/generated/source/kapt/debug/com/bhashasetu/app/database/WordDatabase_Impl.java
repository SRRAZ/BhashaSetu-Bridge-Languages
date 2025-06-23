package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class WordDatabase_Impl extends WordDatabase {
  private volatile WordDao _wordDao;

  private volatile LessonDao _lessonDao;

  private volatile UserProgressDao _userProgressDao;

  private volatile QuizDao _quizDao;

  private volatile FlashcardDeckDao _flashcardDeckDao;

  private volatile PracticeSessionDao _practiceSessionDao;

  private volatile ExerciseDao _exerciseDao;

  private volatile AchievementDao _achievementDao;

  private volatile BadgeDao _badgeDao;

  private volatile UserPointsDao _userPointsDao;

  private volatile UserLevelDao _userLevelDao;

  private volatile UserStatsDao _userStatsDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(1, "b1969c7a36e1fbc0e5c0eee53d3fc107", "63a7ce21af359747512c8430861e7b29") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `words` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `englishWord` TEXT, `hindiWord` TEXT, `englishPronunciation` TEXT, `hindiPronunciation` TEXT, `category` TEXT, `difficulty` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `timeAdded` INTEGER NOT NULL, `masteryLevel` INTEGER NOT NULL, `exampleSentenceEnglish` TEXT, `exampleSentenceHindi` TEXT, `notes` TEXT, `usageContext` TEXT, `partOfSpeech` TEXT, `imageUrl` TEXT, `englishAudioFileName` TEXT, `hindiAudioFileName` TEXT, `hasImage` INTEGER NOT NULL, `hasEnglishAudio` INTEGER NOT NULL, `hasHindiAudio` INTEGER NOT NULL, `correctAttempts` INTEGER NOT NULL, `totalAttempts` INTEGER NOT NULL, `lastPracticed` INTEGER NOT NULL, `nextReviewDue` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `lessons` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `titleHindi` TEXT, `description` TEXT, `descriptionHindi` TEXT, `category` TEXT, `level` TEXT, `orderInCategory` INTEGER NOT NULL, `content` TEXT, `contentHindi` TEXT, `imageUrl` TEXT, `hasCompleted` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `lesson_words` (`lessonId` INTEGER NOT NULL, `wordId` INTEGER NOT NULL, `order` INTEGER NOT NULL, `notes` TEXT, PRIMARY KEY(`lessonId`, `wordId`), FOREIGN KEY(`lessonId`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`wordId`) REFERENCES `words`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_lesson_words_lessonId` ON `lesson_words` (`lessonId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_lesson_words_wordId` ON `lesson_words` (`wordId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_progress` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `itemType` TEXT, `itemId` INTEGER NOT NULL, `completionLevel` INTEGER NOT NULL, `lastPracticed` INTEGER NOT NULL, `reviewDue` INTEGER NOT NULL, `attemptCount` INTEGER NOT NULL, `correctCount` INTEGER NOT NULL, `srLevel` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE UNIQUE INDEX IF NOT EXISTS `index_user_progress_userId_itemType_itemId` ON `user_progress` (`userId`, `itemType`, `itemId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_user_progress_reviewDue` ON `user_progress` (`reviewDue`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `quizzes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `lessonId` INTEGER NOT NULL, `question` TEXT, `questionHindi` TEXT, `correctAnswer` TEXT, `wrongAnswer1` TEXT, `wrongAnswer2` TEXT, `wrongAnswer3` TEXT, `explanation` TEXT, `explanationHindi` TEXT, `type` TEXT, `difficulty` INTEGER NOT NULL, `mediaUrl` TEXT, FOREIGN KEY(`lessonId`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_quizzes_lessonId` ON `quizzes` (`lessonId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `flashcard_decks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `nameHindi` TEXT, `description` TEXT, `descriptionHindi` TEXT, `category` TEXT, `difficulty` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `createdDate` INTEGER NOT NULL, `lastPracticed` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `deck_words` (`deckId` INTEGER NOT NULL, `wordId` INTEGER NOT NULL, `order` INTEGER NOT NULL, `notes` TEXT, PRIMARY KEY(`deckId`, `wordId`), FOREIGN KEY(`wordId`) REFERENCES `flashcard_decks`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`wordId`) REFERENCES `words`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_deck_words_deckId` ON `deck_words` (`deckId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_deck_words_wordId` ON `deck_words` (`wordId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `practice_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `sessionType` TEXT, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `totalItems` INTEGER NOT NULL, `correctAnswers` INTEGER NOT NULL, `deckId` INTEGER NOT NULL, `lessonId` INTEGER NOT NULL, `score` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `exercises` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `type` TEXT, `difficulty` INTEGER NOT NULL, `wordCount` INTEGER NOT NULL, `points` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `createdAt` INTEGER, `completedAt` INTEGER, `correctAnswers` INTEGER NOT NULL, `totalQuestions` INTEGER NOT NULL, `lessonId` TEXT, `category` TEXT)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `multiple_choice_exercises` (`questions` TEXT, `currentQuestionIndex` INTEGER NOT NULL, `showHindiFirst` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `type` TEXT, `difficulty` INTEGER NOT NULL, `wordCount` INTEGER NOT NULL, `points` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `createdAt` INTEGER, `completedAt` INTEGER, `correctAnswers` INTEGER NOT NULL, `totalQuestions` INTEGER NOT NULL, `lessonId` TEXT, `category` TEXT)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `matching_exercises` (`englishItems` TEXT, `hindiItems` TEXT, `userMatches` TEXT, `correctMatches` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `type` TEXT, `difficulty` INTEGER NOT NULL, `wordCount` INTEGER NOT NULL, `points` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `createdAt` INTEGER, `completedAt` INTEGER, `correctAnswers` INTEGER NOT NULL, `totalQuestions` INTEGER NOT NULL, `lessonId` TEXT, `category` TEXT)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `fill_blank_exercises` (`questions` TEXT, `currentQuestionIndex` INTEGER NOT NULL, `showHindiSentence` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `type` TEXT, `difficulty` INTEGER NOT NULL, `wordCount` INTEGER NOT NULL, `points` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `createdAt` INTEGER, `completedAt` INTEGER, `correctAnswers` INTEGER NOT NULL, `totalQuestions` INTEGER NOT NULL, `lessonId` TEXT, `category` TEXT)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `legacy_achievements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `type` TEXT, `progressTarget` INTEGER NOT NULL, `currentProgress` INTEGER NOT NULL, `unlocked` INTEGER NOT NULL, `dateUnlocked` INTEGER, `pointsValue` INTEGER NOT NULL, `iconResId` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `badges` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `description` TEXT, `tier` TEXT, `imagePath` TEXT, `earned` INTEGER NOT NULL, `earnedAt` INTEGER, `pointsRequired` INTEGER NOT NULL, `achievementId` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_points` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `points` INTEGER NOT NULL, `description` TEXT, `source` TEXT, `earnedAt` INTEGER, `sourceId` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_levels` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `level` INTEGER NOT NULL, `currentExp` INTEGER NOT NULL, `expToNextLevel` INTEGER NOT NULL, `totalExp` INTEGER NOT NULL, `title` TEXT)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_stats` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `wordsLearned` INTEGER NOT NULL, `exercisesCompleted` INTEGER NOT NULL, `dailyStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `perfectScores` INTEGER NOT NULL, `totalTimeSpent` INTEGER NOT NULL, `registeredAt` INTEGER, `lastActive` INTEGER, `categoriesUnlocked` INTEGER NOT NULL, `achievementsUnlocked` INTEGER NOT NULL, `badgesEarned` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b1969c7a36e1fbc0e5c0eee53d3fc107')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `words`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `lessons`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `lesson_words`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_progress`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `quizzes`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `flashcard_decks`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `deck_words`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `practice_sessions`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `exercises`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `multiple_choice_exercises`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `matching_exercises`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `fill_blank_exercises`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `legacy_achievements`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `badges`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_points`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_levels`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_stats`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsWords = new HashMap<String, TableInfo.Column>(25);
        _columnsWords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("englishWord", new TableInfo.Column("englishWord", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hindiWord", new TableInfo.Column("hindiWord", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("englishPronunciation", new TableInfo.Column("englishPronunciation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hindiPronunciation", new TableInfo.Column("hindiPronunciation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("timeAdded", new TableInfo.Column("timeAdded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("masteryLevel", new TableInfo.Column("masteryLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("exampleSentenceEnglish", new TableInfo.Column("exampleSentenceEnglish", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("exampleSentenceHindi", new TableInfo.Column("exampleSentenceHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("usageContext", new TableInfo.Column("usageContext", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("partOfSpeech", new TableInfo.Column("partOfSpeech", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("englishAudioFileName", new TableInfo.Column("englishAudioFileName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hindiAudioFileName", new TableInfo.Column("hindiAudioFileName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hasImage", new TableInfo.Column("hasImage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hasEnglishAudio", new TableInfo.Column("hasEnglishAudio", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hasHindiAudio", new TableInfo.Column("hasHindiAudio", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("correctAttempts", new TableInfo.Column("correctAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("totalAttempts", new TableInfo.Column("totalAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("lastPracticed", new TableInfo.Column("lastPracticed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("nextReviewDue", new TableInfo.Column("nextReviewDue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysWords = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesWords = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWords = new TableInfo("words", _columnsWords, _foreignKeysWords, _indicesWords);
        final TableInfo _existingWords = TableInfo.read(connection, "words");
        if (!_infoWords.equals(_existingWords)) {
          return new RoomOpenDelegate.ValidationResult(false, "words(com.bhashasetu.app.model.Word).\n"
                  + " Expected:\n" + _infoWords + "\n"
                  + " Found:\n" + _existingWords);
        }
        final Map<String, TableInfo.Column> _columnsLessons = new HashMap<String, TableInfo.Column>(12);
        _columnsLessons.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("titleHindi", new TableInfo.Column("titleHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("descriptionHindi", new TableInfo.Column("descriptionHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("level", new TableInfo.Column("level", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("orderInCategory", new TableInfo.Column("orderInCategory", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("contentHindi", new TableInfo.Column("contentHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("hasCompleted", new TableInfo.Column("hasCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysLessons = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesLessons = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLessons = new TableInfo("lessons", _columnsLessons, _foreignKeysLessons, _indicesLessons);
        final TableInfo _existingLessons = TableInfo.read(connection, "lessons");
        if (!_infoLessons.equals(_existingLessons)) {
          return new RoomOpenDelegate.ValidationResult(false, "lessons(com.bhashasetu.app.model.Lesson).\n"
                  + " Expected:\n" + _infoLessons + "\n"
                  + " Found:\n" + _existingLessons);
        }
        final Map<String, TableInfo.Column> _columnsLessonWords = new HashMap<String, TableInfo.Column>(4);
        _columnsLessonWords.put("lessonId", new TableInfo.Column("lessonId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("wordId", new TableInfo.Column("wordId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("order", new TableInfo.Column("order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysLessonWords = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysLessonWords.add(new TableInfo.ForeignKey("lessons", "CASCADE", "NO ACTION", Arrays.asList("lessonId"), Arrays.asList("id")));
        _foreignKeysLessonWords.add(new TableInfo.ForeignKey("words", "CASCADE", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesLessonWords = new HashSet<TableInfo.Index>(2);
        _indicesLessonWords.add(new TableInfo.Index("index_lesson_words_lessonId", false, Arrays.asList("lessonId"), Arrays.asList("ASC")));
        _indicesLessonWords.add(new TableInfo.Index("index_lesson_words_wordId", false, Arrays.asList("wordId"), Arrays.asList("ASC")));
        final TableInfo _infoLessonWords = new TableInfo("lesson_words", _columnsLessonWords, _foreignKeysLessonWords, _indicesLessonWords);
        final TableInfo _existingLessonWords = TableInfo.read(connection, "lesson_words");
        if (!_infoLessonWords.equals(_existingLessonWords)) {
          return new RoomOpenDelegate.ValidationResult(false, "lesson_words(com.bhashasetu.app.model.LessonWord).\n"
                  + " Expected:\n" + _infoLessonWords + "\n"
                  + " Found:\n" + _existingLessonWords);
        }
        final Map<String, TableInfo.Column> _columnsUserProgress = new HashMap<String, TableInfo.Column>(10);
        _columnsUserProgress.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("itemType", new TableInfo.Column("itemType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("itemId", new TableInfo.Column("itemId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("completionLevel", new TableInfo.Column("completionLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("lastPracticed", new TableInfo.Column("lastPracticed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("reviewDue", new TableInfo.Column("reviewDue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("attemptCount", new TableInfo.Column("attemptCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("correctCount", new TableInfo.Column("correctCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("srLevel", new TableInfo.Column("srLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUserProgress = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesUserProgress = new HashSet<TableInfo.Index>(2);
        _indicesUserProgress.add(new TableInfo.Index("index_user_progress_userId_itemType_itemId", true, Arrays.asList("userId", "itemType", "itemId"), Arrays.asList("ASC", "ASC", "ASC")));
        _indicesUserProgress.add(new TableInfo.Index("index_user_progress_reviewDue", false, Arrays.asList("reviewDue"), Arrays.asList("ASC")));
        final TableInfo _infoUserProgress = new TableInfo("user_progress", _columnsUserProgress, _foreignKeysUserProgress, _indicesUserProgress);
        final TableInfo _existingUserProgress = TableInfo.read(connection, "user_progress");
        if (!_infoUserProgress.equals(_existingUserProgress)) {
          return new RoomOpenDelegate.ValidationResult(false, "user_progress(com.bhashasetu.app.model.UserProgress).\n"
                  + " Expected:\n" + _infoUserProgress + "\n"
                  + " Found:\n" + _existingUserProgress);
        }
        final Map<String, TableInfo.Column> _columnsQuizzes = new HashMap<String, TableInfo.Column>(13);
        _columnsQuizzes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("lessonId", new TableInfo.Column("lessonId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("question", new TableInfo.Column("question", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("questionHindi", new TableInfo.Column("questionHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("correctAnswer", new TableInfo.Column("correctAnswer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("wrongAnswer1", new TableInfo.Column("wrongAnswer1", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("wrongAnswer2", new TableInfo.Column("wrongAnswer2", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("wrongAnswer3", new TableInfo.Column("wrongAnswer3", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("explanation", new TableInfo.Column("explanation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("explanationHindi", new TableInfo.Column("explanationHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("mediaUrl", new TableInfo.Column("mediaUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysQuizzes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysQuizzes.add(new TableInfo.ForeignKey("lessons", "CASCADE", "NO ACTION", Arrays.asList("lessonId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesQuizzes = new HashSet<TableInfo.Index>(1);
        _indicesQuizzes.add(new TableInfo.Index("index_quizzes_lessonId", false, Arrays.asList("lessonId"), Arrays.asList("ASC")));
        final TableInfo _infoQuizzes = new TableInfo("quizzes", _columnsQuizzes, _foreignKeysQuizzes, _indicesQuizzes);
        final TableInfo _existingQuizzes = TableInfo.read(connection, "quizzes");
        if (!_infoQuizzes.equals(_existingQuizzes)) {
          return new RoomOpenDelegate.ValidationResult(false, "quizzes(com.bhashasetu.app.model.Quiz).\n"
                  + " Expected:\n" + _infoQuizzes + "\n"
                  + " Found:\n" + _existingQuizzes);
        }
        final Map<String, TableInfo.Column> _columnsFlashcardDecks = new HashMap<String, TableInfo.Column>(10);
        _columnsFlashcardDecks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("nameHindi", new TableInfo.Column("nameHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("descriptionHindi", new TableInfo.Column("descriptionHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("createdDate", new TableInfo.Column("createdDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlashcardDecks.put("lastPracticed", new TableInfo.Column("lastPracticed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysFlashcardDecks = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesFlashcardDecks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFlashcardDecks = new TableInfo("flashcard_decks", _columnsFlashcardDecks, _foreignKeysFlashcardDecks, _indicesFlashcardDecks);
        final TableInfo _existingFlashcardDecks = TableInfo.read(connection, "flashcard_decks");
        if (!_infoFlashcardDecks.equals(_existingFlashcardDecks)) {
          return new RoomOpenDelegate.ValidationResult(false, "flashcard_decks(com.bhashasetu.app.model.FlashcardDeck).\n"
                  + " Expected:\n" + _infoFlashcardDecks + "\n"
                  + " Found:\n" + _existingFlashcardDecks);
        }
        final Map<String, TableInfo.Column> _columnsDeckWords = new HashMap<String, TableInfo.Column>(4);
        _columnsDeckWords.put("deckId", new TableInfo.Column("deckId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeckWords.put("wordId", new TableInfo.Column("wordId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeckWords.put("order", new TableInfo.Column("order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeckWords.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysDeckWords = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysDeckWords.add(new TableInfo.ForeignKey("flashcard_decks", "CASCADE", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        _foreignKeysDeckWords.add(new TableInfo.ForeignKey("words", "CASCADE", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesDeckWords = new HashSet<TableInfo.Index>(2);
        _indicesDeckWords.add(new TableInfo.Index("index_deck_words_deckId", false, Arrays.asList("deckId"), Arrays.asList("ASC")));
        _indicesDeckWords.add(new TableInfo.Index("index_deck_words_wordId", false, Arrays.asList("wordId"), Arrays.asList("ASC")));
        final TableInfo _infoDeckWords = new TableInfo("deck_words", _columnsDeckWords, _foreignKeysDeckWords, _indicesDeckWords);
        final TableInfo _existingDeckWords = TableInfo.read(connection, "deck_words");
        if (!_infoDeckWords.equals(_existingDeckWords)) {
          return new RoomOpenDelegate.ValidationResult(false, "deck_words(com.bhashasetu.app.model.DeckWord).\n"
                  + " Expected:\n" + _infoDeckWords + "\n"
                  + " Found:\n" + _existingDeckWords);
        }
        final Map<String, TableInfo.Column> _columnsPracticeSessions = new HashMap<String, TableInfo.Column>(11);
        _columnsPracticeSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("sessionType", new TableInfo.Column("sessionType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("endTime", new TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("totalItems", new TableInfo.Column("totalItems", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("correctAnswers", new TableInfo.Column("correctAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("deckId", new TableInfo.Column("deckId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("lessonId", new TableInfo.Column("lessonId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysPracticeSessions = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesPracticeSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPracticeSessions = new TableInfo("practice_sessions", _columnsPracticeSessions, _foreignKeysPracticeSessions, _indicesPracticeSessions);
        final TableInfo _existingPracticeSessions = TableInfo.read(connection, "practice_sessions");
        if (!_infoPracticeSessions.equals(_existingPracticeSessions)) {
          return new RoomOpenDelegate.ValidationResult(false, "practice_sessions(com.bhashasetu.app.model.PracticeSession).\n"
                  + " Expected:\n" + _infoPracticeSessions + "\n"
                  + " Found:\n" + _existingPracticeSessions);
        }
        final Map<String, TableInfo.Column> _columnsExercises = new HashMap<String, TableInfo.Column>(14);
        _columnsExercises.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("wordCount", new TableInfo.Column("wordCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("correctAnswers", new TableInfo.Column("correctAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("totalQuestions", new TableInfo.Column("totalQuestions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("lessonId", new TableInfo.Column("lessonId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysExercises = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExercises = new TableInfo("exercises", _columnsExercises, _foreignKeysExercises, _indicesExercises);
        final TableInfo _existingExercises = TableInfo.read(connection, "exercises");
        if (!_infoExercises.equals(_existingExercises)) {
          return new RoomOpenDelegate.ValidationResult(false, "exercises(com.bhashasetu.app.model.exercise.Exercise).\n"
                  + " Expected:\n" + _infoExercises + "\n"
                  + " Found:\n" + _existingExercises);
        }
        final Map<String, TableInfo.Column> _columnsMultipleChoiceExercises = new HashMap<String, TableInfo.Column>(17);
        _columnsMultipleChoiceExercises.put("questions", new TableInfo.Column("questions", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("currentQuestionIndex", new TableInfo.Column("currentQuestionIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("showHindiFirst", new TableInfo.Column("showHindiFirst", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("wordCount", new TableInfo.Column("wordCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("correctAnswers", new TableInfo.Column("correctAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("totalQuestions", new TableInfo.Column("totalQuestions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("lessonId", new TableInfo.Column("lessonId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMultipleChoiceExercises.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysMultipleChoiceExercises = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesMultipleChoiceExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMultipleChoiceExercises = new TableInfo("multiple_choice_exercises", _columnsMultipleChoiceExercises, _foreignKeysMultipleChoiceExercises, _indicesMultipleChoiceExercises);
        final TableInfo _existingMultipleChoiceExercises = TableInfo.read(connection, "multiple_choice_exercises");
        if (!_infoMultipleChoiceExercises.equals(_existingMultipleChoiceExercises)) {
          return new RoomOpenDelegate.ValidationResult(false, "multiple_choice_exercises(com.bhashasetu.app.model.exercise.MultipleChoiceExercise).\n"
                  + " Expected:\n" + _infoMultipleChoiceExercises + "\n"
                  + " Found:\n" + _existingMultipleChoiceExercises);
        }
        final Map<String, TableInfo.Column> _columnsMatchingExercises = new HashMap<String, TableInfo.Column>(18);
        _columnsMatchingExercises.put("englishItems", new TableInfo.Column("englishItems", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("hindiItems", new TableInfo.Column("hindiItems", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("userMatches", new TableInfo.Column("userMatches", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("correctMatches", new TableInfo.Column("correctMatches", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("wordCount", new TableInfo.Column("wordCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("correctAnswers", new TableInfo.Column("correctAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("totalQuestions", new TableInfo.Column("totalQuestions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("lessonId", new TableInfo.Column("lessonId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMatchingExercises.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysMatchingExercises = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesMatchingExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMatchingExercises = new TableInfo("matching_exercises", _columnsMatchingExercises, _foreignKeysMatchingExercises, _indicesMatchingExercises);
        final TableInfo _existingMatchingExercises = TableInfo.read(connection, "matching_exercises");
        if (!_infoMatchingExercises.equals(_existingMatchingExercises)) {
          return new RoomOpenDelegate.ValidationResult(false, "matching_exercises(com.bhashasetu.app.model.exercise.MatchingExercise).\n"
                  + " Expected:\n" + _infoMatchingExercises + "\n"
                  + " Found:\n" + _existingMatchingExercises);
        }
        final Map<String, TableInfo.Column> _columnsFillBlankExercises = new HashMap<String, TableInfo.Column>(17);
        _columnsFillBlankExercises.put("questions", new TableInfo.Column("questions", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("currentQuestionIndex", new TableInfo.Column("currentQuestionIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("showHindiSentence", new TableInfo.Column("showHindiSentence", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("wordCount", new TableInfo.Column("wordCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("correctAnswers", new TableInfo.Column("correctAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("totalQuestions", new TableInfo.Column("totalQuestions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("lessonId", new TableInfo.Column("lessonId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFillBlankExercises.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysFillBlankExercises = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesFillBlankExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFillBlankExercises = new TableInfo("fill_blank_exercises", _columnsFillBlankExercises, _foreignKeysFillBlankExercises, _indicesFillBlankExercises);
        final TableInfo _existingFillBlankExercises = TableInfo.read(connection, "fill_blank_exercises");
        if (!_infoFillBlankExercises.equals(_existingFillBlankExercises)) {
          return new RoomOpenDelegate.ValidationResult(false, "fill_blank_exercises(com.bhashasetu.app.model.exercise.FillBlankExercise).\n"
                  + " Expected:\n" + _infoFillBlankExercises + "\n"
                  + " Found:\n" + _existingFillBlankExercises);
        }
        final Map<String, TableInfo.Column> _columnsLegacyAchievements = new HashMap<String, TableInfo.Column>(10);
        _columnsLegacyAchievements.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("progressTarget", new TableInfo.Column("progressTarget", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("currentProgress", new TableInfo.Column("currentProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("unlocked", new TableInfo.Column("unlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("dateUnlocked", new TableInfo.Column("dateUnlocked", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("pointsValue", new TableInfo.Column("pointsValue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLegacyAchievements.put("iconResId", new TableInfo.Column("iconResId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysLegacyAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesLegacyAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLegacyAchievements = new TableInfo("legacy_achievements", _columnsLegacyAchievements, _foreignKeysLegacyAchievements, _indicesLegacyAchievements);
        final TableInfo _existingLegacyAchievements = TableInfo.read(connection, "legacy_achievements");
        if (!_infoLegacyAchievements.equals(_existingLegacyAchievements)) {
          return new RoomOpenDelegate.ValidationResult(false, "legacy_achievements(com.bhashasetu.app.model.Achievement).\n"
                  + " Expected:\n" + _infoLegacyAchievements + "\n"
                  + " Found:\n" + _existingLegacyAchievements);
        }
        final Map<String, TableInfo.Column> _columnsBadges = new HashMap<String, TableInfo.Column>(9);
        _columnsBadges.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("tier", new TableInfo.Column("tier", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("imagePath", new TableInfo.Column("imagePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("earned", new TableInfo.Column("earned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("earnedAt", new TableInfo.Column("earnedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("pointsRequired", new TableInfo.Column("pointsRequired", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("achievementId", new TableInfo.Column("achievementId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysBadges = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesBadges = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBadges = new TableInfo("badges", _columnsBadges, _foreignKeysBadges, _indicesBadges);
        final TableInfo _existingBadges = TableInfo.read(connection, "badges");
        if (!_infoBadges.equals(_existingBadges)) {
          return new RoomOpenDelegate.ValidationResult(false, "badges(com.bhashasetu.app.model.gamification.Badge).\n"
                  + " Expected:\n" + _infoBadges + "\n"
                  + " Found:\n" + _existingBadges);
        }
        final Map<String, TableInfo.Column> _columnsUserPoints = new HashMap<String, TableInfo.Column>(7);
        _columnsUserPoints.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserPoints.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserPoints.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserPoints.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserPoints.put("source", new TableInfo.Column("source", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserPoints.put("earnedAt", new TableInfo.Column("earnedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserPoints.put("sourceId", new TableInfo.Column("sourceId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUserPoints = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesUserPoints = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserPoints = new TableInfo("user_points", _columnsUserPoints, _foreignKeysUserPoints, _indicesUserPoints);
        final TableInfo _existingUserPoints = TableInfo.read(connection, "user_points");
        if (!_infoUserPoints.equals(_existingUserPoints)) {
          return new RoomOpenDelegate.ValidationResult(false, "user_points(com.bhashasetu.app.model.gamification.UserPoints).\n"
                  + " Expected:\n" + _infoUserPoints + "\n"
                  + " Found:\n" + _existingUserPoints);
        }
        final Map<String, TableInfo.Column> _columnsUserLevels = new HashMap<String, TableInfo.Column>(7);
        _columnsUserLevels.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserLevels.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserLevels.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserLevels.put("currentExp", new TableInfo.Column("currentExp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserLevels.put("expToNextLevel", new TableInfo.Column("expToNextLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserLevels.put("totalExp", new TableInfo.Column("totalExp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserLevels.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUserLevels = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesUserLevels = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserLevels = new TableInfo("user_levels", _columnsUserLevels, _foreignKeysUserLevels, _indicesUserLevels);
        final TableInfo _existingUserLevels = TableInfo.read(connection, "user_levels");
        if (!_infoUserLevels.equals(_existingUserLevels)) {
          return new RoomOpenDelegate.ValidationResult(false, "user_levels(com.bhashasetu.app.model.gamification.UserLevel).\n"
                  + " Expected:\n" + _infoUserLevels + "\n"
                  + " Found:\n" + _existingUserLevels);
        }
        final Map<String, TableInfo.Column> _columnsUserStats = new HashMap<String, TableInfo.Column>(13);
        _columnsUserStats.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("wordsLearned", new TableInfo.Column("wordsLearned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("exercisesCompleted", new TableInfo.Column("exercisesCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("dailyStreak", new TableInfo.Column("dailyStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("longestStreak", new TableInfo.Column("longestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("perfectScores", new TableInfo.Column("perfectScores", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("totalTimeSpent", new TableInfo.Column("totalTimeSpent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("registeredAt", new TableInfo.Column("registeredAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("lastActive", new TableInfo.Column("lastActive", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("categoriesUnlocked", new TableInfo.Column("categoriesUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("achievementsUnlocked", new TableInfo.Column("achievementsUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserStats.put("badgesEarned", new TableInfo.Column("badgesEarned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUserStats = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesUserStats = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserStats = new TableInfo("user_stats", _columnsUserStats, _foreignKeysUserStats, _indicesUserStats);
        final TableInfo _existingUserStats = TableInfo.read(connection, "user_stats");
        if (!_infoUserStats.equals(_existingUserStats)) {
          return new RoomOpenDelegate.ValidationResult(false, "user_stats(com.bhashasetu.app.model.gamification.UserStats).\n"
                  + " Expected:\n" + _infoUserStats + "\n"
                  + " Found:\n" + _existingUserStats);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "words", "lessons", "lesson_words", "user_progress", "quizzes", "flashcard_decks", "deck_words", "practice_sessions", "exercises", "multiple_choice_exercises", "matching_exercises", "fill_blank_exercises", "legacy_achievements", "badges", "user_points", "user_levels", "user_stats");
  }

  @Override
  public void clearAllTables() {
    super.performClear(true, "words", "lessons", "lesson_words", "user_progress", "quizzes", "flashcard_decks", "deck_words", "practice_sessions", "exercises", "multiple_choice_exercises", "matching_exercises", "fill_blank_exercises", "legacy_achievements", "badges", "user_points", "user_levels", "user_stats");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WordDao.class, WordDao_WordDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(LessonDao.class, LessonDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProgressDao.class, UserProgressDao_WordDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuizDao.class, QuizDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FlashcardDeckDao.class, FlashcardDeckDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PracticeSessionDao.class, PracticeSessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExerciseDao.class, ExerciseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_WordDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(BadgeDao.class, BadgeDao_WordDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserPointsDao.class, UserPointsDao_WordDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserLevelDao.class, UserLevelDao_WordDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserStatsDao.class, UserStatsDao_WordDatabase_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WordDao wordDao() {
    if (_wordDao != null) {
      return _wordDao;
    } else {
      synchronized(this) {
        if(_wordDao == null) {
          _wordDao = new WordDao_WordDatabase_Impl(this);
        }
        return _wordDao;
      }
    }
  }

  @Override
  public LessonDao lessonDao() {
    if (_lessonDao != null) {
      return _lessonDao;
    } else {
      synchronized(this) {
        if(_lessonDao == null) {
          _lessonDao = new LessonDao_Impl(this);
        }
        return _lessonDao;
      }
    }
  }

  @Override
  public UserProgressDao userProgressDao() {
    if (_userProgressDao != null) {
      return _userProgressDao;
    } else {
      synchronized(this) {
        if(_userProgressDao == null) {
          _userProgressDao = new UserProgressDao_WordDatabase_Impl(this);
        }
        return _userProgressDao;
      }
    }
  }

  @Override
  public QuizDao quizDao() {
    if (_quizDao != null) {
      return _quizDao;
    } else {
      synchronized(this) {
        if(_quizDao == null) {
          _quizDao = new QuizDao_Impl(this);
        }
        return _quizDao;
      }
    }
  }

  @Override
  public FlashcardDeckDao flashcardDeckDao() {
    if (_flashcardDeckDao != null) {
      return _flashcardDeckDao;
    } else {
      synchronized(this) {
        if(_flashcardDeckDao == null) {
          _flashcardDeckDao = new FlashcardDeckDao_Impl(this);
        }
        return _flashcardDeckDao;
      }
    }
  }

  @Override
  public PracticeSessionDao practiceSessionDao() {
    if (_practiceSessionDao != null) {
      return _practiceSessionDao;
    } else {
      synchronized(this) {
        if(_practiceSessionDao == null) {
          _practiceSessionDao = new PracticeSessionDao_Impl(this);
        }
        return _practiceSessionDao;
      }
    }
  }

  @Override
  public ExerciseDao exerciseDao() {
    if (_exerciseDao != null) {
      return _exerciseDao;
    } else {
      synchronized(this) {
        if(_exerciseDao == null) {
          _exerciseDao = new ExerciseDao_Impl(this);
        }
        return _exerciseDao;
      }
    }
  }

  @Override
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_WordDatabase_Impl(this);
        }
        return _achievementDao;
      }
    }
  }

  @Override
  public BadgeDao badgeDao() {
    if (_badgeDao != null) {
      return _badgeDao;
    } else {
      synchronized(this) {
        if(_badgeDao == null) {
          _badgeDao = new BadgeDao_WordDatabase_Impl(this);
        }
        return _badgeDao;
      }
    }
  }

  @Override
  public UserPointsDao userPointsDao() {
    if (_userPointsDao != null) {
      return _userPointsDao;
    } else {
      synchronized(this) {
        if(_userPointsDao == null) {
          _userPointsDao = new UserPointsDao_WordDatabase_Impl(this);
        }
        return _userPointsDao;
      }
    }
  }

  @Override
  public UserLevelDao userLevelDao() {
    if (_userLevelDao != null) {
      return _userLevelDao;
    } else {
      synchronized(this) {
        if(_userLevelDao == null) {
          _userLevelDao = new UserLevelDao_WordDatabase_Impl(this);
        }
        return _userLevelDao;
      }
    }
  }

  @Override
  public UserStatsDao userStatsDao() {
    if (_userStatsDao != null) {
      return _userStatsDao;
    } else {
      synchronized(this) {
        if(_userStatsDao == null) {
          _userStatsDao = new UserStatsDao_WordDatabase_Impl(this);
        }
        return _userStatsDao;
      }
    }
  }
}
