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
import com.bhashasetu.app.data.dao.LessonDao;
import com.bhashasetu.app.data.dao.LessonDao_AppDatabase_0_Impl;
import com.bhashasetu.app.data.dao.UserGoalDao;
import com.bhashasetu.app.data.dao.UserGoalDao_AppDatabase_0_Impl;
import com.bhashasetu.app.pronunciation.PronunciationScoreDao;
import com.bhashasetu.app.pronunciation.PronunciationScoreDao_Impl;
import com.bhashasetu.app.pronunciation.PronunciationSessionDao;
import com.bhashasetu.app.pronunciation.PronunciationSessionDao_Impl;
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
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AchievementDao _achievementDao;

  private volatile ModernAchievementDao _modernAchievementDao;

  private volatile WordDao _wordDao;

  private volatile WordCategoryDao _wordCategoryDao;

  private volatile UserDao _userDao;

  private volatile UserProgressDao _userProgressDao;

  private volatile GameScoreDao _gameScoreDao;

  private volatile BadgeDao _badgeDao;

  private volatile UserLevelDao _userLevelDao;

  private volatile UserPointsDao _userPointsDao;

  private volatile UserStatsDao _userStatsDao;

  private volatile PronunciationSessionDao _pronunciationSessionDao;

  private volatile PronunciationScoreDao _pronunciationScoreDao;

  private volatile UserGoalDao _userGoalDao;

  private volatile LessonDao _lessonDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(6, "e5a03206d725cbd7e0d7e287bee2cea0", "630a1e29b2ed50b6c2e9ab0e16489b45") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `words` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `englishWord` TEXT, `hindiWord` TEXT, `englishPronunciation` TEXT, `hindiPronunciation` TEXT, `category` TEXT, `difficulty` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `timeAdded` INTEGER NOT NULL, `masteryLevel` INTEGER NOT NULL, `exampleSentenceEnglish` TEXT, `exampleSentenceHindi` TEXT, `notes` TEXT, `usageContext` TEXT, `partOfSpeech` TEXT, `imageUrl` TEXT, `englishAudioFileName` TEXT, `hindiAudioFileName` TEXT, `hasImage` INTEGER NOT NULL, `hasEnglishAudio` INTEGER NOT NULL, `hasHindiAudio` INTEGER NOT NULL, `correctAttempts` INTEGER NOT NULL, `totalAttempts` INTEGER NOT NULL, `lastPracticed` INTEGER NOT NULL, `nextReviewDue` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `word_categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `description` TEXT, `color` TEXT, `totalWords` INTEGER NOT NULL, `isActive` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT, `email` TEXT, `fullName` TEXT, `profileImageUrl` TEXT, `preferredLanguage` TEXT, `totalPoints` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `maxStreak` INTEGER NOT NULL, `dateJoined` INTEGER, `lastActiveDate` INTEGER, `learningGoal` TEXT, `isActive` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_progress` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `itemType` TEXT, `itemId` INTEGER NOT NULL, `completionLevel` INTEGER NOT NULL, `lastPracticed` INTEGER NOT NULL, `reviewDue` INTEGER NOT NULL, `attemptCount` INTEGER NOT NULL, `correctCount` INTEGER NOT NULL, `srLevel` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE UNIQUE INDEX IF NOT EXISTS `index_user_progress_userId_itemType_itemId` ON `user_progress` (`userId`, `itemType`, `itemId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_user_progress_reviewDue` ON `user_progress` (`reviewDue`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `game_scores` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `gameType` TEXT, `category` TEXT, `difficulty` TEXT, `score` INTEGER NOT NULL, `maxScore` INTEGER NOT NULL, `correctAnswers` INTEGER NOT NULL, `totalQuestions` INTEGER NOT NULL, `timeTaken` INTEGER NOT NULL, `datePlayed` INTEGER, `isCompleted` INTEGER NOT NULL, `gameMode` TEXT, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `legacy_achievements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `type` TEXT, `progressTarget` INTEGER NOT NULL, `currentProgress` INTEGER NOT NULL, `unlocked` INTEGER NOT NULL, `dateUnlocked` INTEGER, `pointsValue` INTEGER NOT NULL, `iconResId` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `achievements` (`id` TEXT NOT NULL, `titleEnglish` TEXT NOT NULL, `titleHindi` TEXT NOT NULL, `descriptionEnglish` TEXT NOT NULL, `descriptionHindi` TEXT NOT NULL, `iconResId` INTEGER NOT NULL, `badgeImageResId` INTEGER, `colorHex` TEXT, `category` TEXT NOT NULL, `type` TEXT NOT NULL, `maxProgress` INTEGER NOT NULL, `currentProgress` INTEGER NOT NULL, `pointsRewarded` INTEGER NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockedAt` INTEGER, `isHidden` INTEGER NOT NULL, `unlockMessage` TEXT, `triggerConditions` TEXT, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `badges` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `description` TEXT, `tier` TEXT, `imagePath` TEXT, `earned` INTEGER NOT NULL, `earnedAt` INTEGER, `pointsRequired` INTEGER NOT NULL, `achievementId` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_levels` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `level` INTEGER NOT NULL, `currentExp` INTEGER NOT NULL, `expToNextLevel` INTEGER NOT NULL, `totalExp` INTEGER NOT NULL, `title` TEXT)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_points` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `points` INTEGER NOT NULL, `description` TEXT, `source` TEXT, `earnedAt` INTEGER, `sourceId` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_stats` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `wordsLearned` INTEGER NOT NULL, `exercisesCompleted` INTEGER NOT NULL, `dailyStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `perfectScores` INTEGER NOT NULL, `totalTimeSpent` INTEGER NOT NULL, `registeredAt` INTEGER, `lastActive` INTEGER, `categoriesUnlocked` INTEGER NOT NULL, `achievementsUnlocked` INTEGER NOT NULL, `badgesEarned` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `pronunciation_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `startTime` INTEGER, `endTime` INTEGER, `words` TEXT, `scores` TEXT, `averageAccuracyScore` INTEGER NOT NULL, `averageRhythmScore` INTEGER NOT NULL, `averageIntonationScore` INTEGER NOT NULL, `userId` INTEGER NOT NULL, `difficultyLevel` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `pronunciation_scores` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `word` TEXT, `accuracyScore` INTEGER NOT NULL, `rhythmScore` INTEGER NOT NULL, `intonationScore` INTEGER NOT NULL, `phoneticFeedback` TEXT, `recordingPath` TEXT, `timestamp` INTEGER, `sessionId` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_goals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `goalType` TEXT NOT NULL, `targetValue` INTEGER NOT NULL, `currentValue` INTEGER NOT NULL, `categoryId` INTEGER, `periodType` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER, `repeatDaily` INTEGER NOT NULL, `reminderEnabled` INTEGER NOT NULL, `reminderTime` TEXT, `completed` INTEGER NOT NULL, `completedAt` INTEGER, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `lastUpdatedAt` INTEGER NOT NULL, FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_user_goals_categoryId` ON `user_goals` (`categoryId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `lessons` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titleEnglish` TEXT NOT NULL, `titleHindi` TEXT NOT NULL, `descriptionEnglish` TEXT, `descriptionHindi` TEXT, `contentEnglish` TEXT, `contentHindi` TEXT, `categoryId` INTEGER, `difficulty` INTEGER NOT NULL, `orderInCategory` INTEGER NOT NULL, `estimatedDurationMinutes` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `completedAt` INTEGER, `lastAccessedAt` INTEGER, `createdAt` INTEGER NOT NULL, `lastModifiedAt` INTEGER NOT NULL, `isSystemLesson` INTEGER NOT NULL, FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_lessons_categoryId` ON `lessons` (`categoryId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `lesson_words` (`lessonId` INTEGER NOT NULL, `wordId` INTEGER NOT NULL, `orderInLesson` INTEGER NOT NULL, `isKeyword` INTEGER NOT NULL, `notes` TEXT, `includeInQuiz` INTEGER NOT NULL, `highlightInContent` INTEGER NOT NULL, PRIMARY KEY(`lessonId`, `wordId`), FOREIGN KEY(`lessonId`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`wordId`) REFERENCES `words`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_lesson_words_lessonId` ON `lesson_words` (`lessonId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_lesson_words_wordId` ON `lesson_words` (`wordId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nameEnglish` TEXT NOT NULL, `nameHindi` TEXT NOT NULL, `descriptionEnglish` TEXT, `descriptionHindi` TEXT, `iconResId` INTEGER, `colorHex` TEXT, `orderIndex` INTEGER NOT NULL, `isDefault` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `quizzes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titleEnglish` TEXT NOT NULL, `titleHindi` TEXT NOT NULL, `instructionsEnglish` TEXT, `instructionsHindi` TEXT, `lessonId` INTEGER, `categoryId` INTEGER, `difficulty` INTEGER NOT NULL, `questionCount` INTEGER NOT NULL, `timeLimit` INTEGER, `passingScorePercent` INTEGER NOT NULL, `quizType` INTEGER NOT NULL, `directionType` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `lastAttemptScore` INTEGER, `bestScore` INTEGER, `attemptCount` INTEGER NOT NULL, `lastAttemptAt` INTEGER, `completedAt` INTEGER, `createdAt` INTEGER NOT NULL, `isSystemQuiz` INTEGER NOT NULL, FOREIGN KEY(`lessonId`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_quizzes_lessonId` ON `quizzes` (`lessonId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_quizzes_categoryId` ON `quizzes` (`categoryId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `quiz_questions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quizId` INTEGER NOT NULL, `wordId` INTEGER, `questionTextEnglish` TEXT NOT NULL, `questionTextHindi` TEXT NOT NULL, `questionType` INTEGER NOT NULL, `correctAnswer` TEXT NOT NULL, `options` TEXT, `imageUrl` TEXT, `audioUrl` TEXT, `explanationEnglish` TEXT, `explanationHindi` TEXT, `difficulty` INTEGER NOT NULL, `points` INTEGER NOT NULL, `orderInQuiz` INTEGER NOT NULL, `correctAttempts` INTEGER NOT NULL, `totalAttempts` INTEGER NOT NULL, FOREIGN KEY(`quizId`) REFERENCES `quizzes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`wordId`) REFERENCES `words`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_quiz_questions_quizId` ON `quiz_questions` (`quizId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_quiz_questions_wordId` ON `quiz_questions` (`wordId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `study_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionType` TEXT NOT NULL, `categoryId` INTEGER, `lessonId` INTEGER, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `durationMs` INTEGER, `durationMinutes` INTEGER, `itemCount` INTEGER NOT NULL, `correctCount` INTEGER NOT NULL, `score` INTEGER, `deviceInfo` TEXT, `interfaceLanguage` TEXT, `isCompleted` INTEGER NOT NULL, `wasPaused` INTEGER NOT NULL, FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`lessonId`) REFERENCES `lessons`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_study_sessions_categoryId` ON `study_sessions` (`categoryId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_study_sessions_lessonId` ON `study_sessions` (`lessonId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `daily_streaks` (`id` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `totalDaysActive` INTEGER NOT NULL, `lastActiveDate` INTEGER, `isTodayCompleted` INTEGER NOT NULL, `streakFreezeAvailable` INTEGER NOT NULL, `streakFreezeUsed` INTEGER NOT NULL, `lastStreakFreezeDate` INTEGER, `nextMilestone` INTEGER NOT NULL, `milestoneReached` INTEGER NOT NULL, `weeklyActivity` TEXT NOT NULL, `monthlyActivity` TEXT NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `app_settings` (`id` INTEGER NOT NULL, `interfaceLanguage` TEXT NOT NULL, `primaryLanguage` TEXT NOT NULL, `secondaryLanguage` TEXT NOT NULL, `useDevanagariDigits` INTEGER NOT NULL, `theme` TEXT NOT NULL, `fontSizeMultiplier` REAL NOT NULL, `highContrastMode` INTEGER NOT NULL, `dailyWordGoal` INTEGER NOT NULL, `dailyTimeGoalMinutes` INTEGER NOT NULL, `reminderTime` TEXT, `reminderDays` TEXT NOT NULL, `autoPlayPronunciation` INTEGER NOT NULL, `pronunciationSpeed` REAL NOT NULL, `autoRecordEnabled` INTEGER NOT NULL, `soundEnabled` INTEGER NOT NULL, `vibrationEnabled` INTEGER NOT NULL, `flashcardSessionSize` INTEGER NOT NULL, `quizTimeLimit` INTEGER, `showTranslationHints` INTEGER NOT NULL, `dailyReminderEnabled` INTEGER NOT NULL, `streakReminderEnabled` INTEGER NOT NULL, `achievementNotificationsEnabled` INTEGER NOT NULL, `quizResultsNotificationsEnabled` INTEGER NOT NULL, `lastSelectedCategoryId` INTEGER, `lastSelectedLessonId` INTEGER, `installDate` INTEGER NOT NULL, `lastUpdatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `user_progress` (`wordId` INTEGER NOT NULL, `proficiencyLevel` INTEGER NOT NULL, `correctAttempts` INTEGER NOT NULL, `totalAttempts` INTEGER NOT NULL, `easeFactor` REAL NOT NULL, `intervalDays` INTEGER NOT NULL, `repetitions` INTEGER NOT NULL, `lastAttemptAt` INTEGER, `lastCorrectAt` INTEGER, `nextReviewDue` INTEGER, `timeSpentMs` INTEGER NOT NULL, `lastConfidenceRating` INTEGER NOT NULL, PRIMARY KEY(`wordId`), FOREIGN KEY(`wordId`) REFERENCES `words`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `words` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `englishText` TEXT NOT NULL, `hindiText` TEXT NOT NULL, `englishPronunciation` TEXT, `hindiPronunciation` TEXT, `englishExample` TEXT, `hindiExample` TEXT, `difficulty` INTEGER NOT NULL, `partOfSpeech` TEXT, `isFavorite` INTEGER NOT NULL, `englishAudioPath` TEXT, `hindiAudioPath` TEXT, `createdAt` INTEGER NOT NULL, `lastModifiedAt` INTEGER NOT NULL, `lastReviewedAt` INTEGER, `nextReviewDate` INTEGER)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `word_category_cross_refs` (`wordId` INTEGER NOT NULL, `categoryId` INTEGER NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`wordId`, `categoryId`), FOREIGN KEY(`wordId`) REFERENCES `words`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_word_category_cross_refs_wordId` ON `word_category_cross_refs` (`wordId`)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_word_category_cross_refs_categoryId` ON `word_category_cross_refs` (`categoryId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e5a03206d725cbd7e0d7e287bee2cea0')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `words`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `word_categories`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `users`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_progress`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `game_scores`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `legacy_achievements`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `achievements`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `badges`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_levels`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_points`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_stats`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `pronunciation_sessions`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `pronunciation_scores`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_goals`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `lessons`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `lesson_words`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `categories`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `quizzes`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `quiz_questions`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `study_sessions`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `daily_streaks`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `app_settings`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `user_progress`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `words`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `word_category_cross_refs`");
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
        final Map<String, TableInfo.Column> _columnsWordCategories = new HashMap<String, TableInfo.Column>(6);
        _columnsWordCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordCategories.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordCategories.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordCategories.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordCategories.put("totalWords", new TableInfo.Column("totalWords", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordCategories.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysWordCategories = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesWordCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWordCategories = new TableInfo("word_categories", _columnsWordCategories, _foreignKeysWordCategories, _indicesWordCategories);
        final TableInfo _existingWordCategories = TableInfo.read(connection, "word_categories");
        if (!_infoWordCategories.equals(_existingWordCategories)) {
          return new RoomOpenDelegate.ValidationResult(false, "word_categories(com.bhashasetu.app.model.WordCategory).\n"
                  + " Expected:\n" + _infoWordCategories + "\n"
                  + " Found:\n" + _existingWordCategories);
        }
        final Map<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(13);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("fullName", new TableInfo.Column("fullName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profileImageUrl", new TableInfo.Column("profileImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("preferredLanguage", new TableInfo.Column("preferredLanguage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("totalPoints", new TableInfo.Column("totalPoints", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("maxStreak", new TableInfo.Column("maxStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("dateJoined", new TableInfo.Column("dateJoined", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("lastActiveDate", new TableInfo.Column("lastActiveDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("learningGoal", new TableInfo.Column("learningGoal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(connection, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenDelegate.ValidationResult(false, "users(com.bhashasetu.app.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
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
        final Map<String, TableInfo.Column> _columnsGameScores = new HashMap<String, TableInfo.Column>(13);
        _columnsGameScores.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("gameType", new TableInfo.Column("gameType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("difficulty", new TableInfo.Column("difficulty", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("maxScore", new TableInfo.Column("maxScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("correctAnswers", new TableInfo.Column("correctAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("totalQuestions", new TableInfo.Column("totalQuestions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("timeTaken", new TableInfo.Column("timeTaken", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("datePlayed", new TableInfo.Column("datePlayed", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameScores.put("gameMode", new TableInfo.Column("gameMode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysGameScores = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysGameScores.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesGameScores = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGameScores = new TableInfo("game_scores", _columnsGameScores, _foreignKeysGameScores, _indicesGameScores);
        final TableInfo _existingGameScores = TableInfo.read(connection, "game_scores");
        if (!_infoGameScores.equals(_existingGameScores)) {
          return new RoomOpenDelegate.ValidationResult(false, "game_scores(com.bhashasetu.app.model.GameScore).\n"
                  + " Expected:\n" + _infoGameScores + "\n"
                  + " Found:\n" + _existingGameScores);
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
        final Map<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(18);
        _columnsAchievements.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("titleEnglish", new TableInfo.Column("titleEnglish", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("titleHindi", new TableInfo.Column("titleHindi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("descriptionEnglish", new TableInfo.Column("descriptionEnglish", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("descriptionHindi", new TableInfo.Column("descriptionHindi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("iconResId", new TableInfo.Column("iconResId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("badgeImageResId", new TableInfo.Column("badgeImageResId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("colorHex", new TableInfo.Column("colorHex", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("maxProgress", new TableInfo.Column("maxProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("currentProgress", new TableInfo.Column("currentProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("pointsRewarded", new TableInfo.Column("pointsRewarded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockedAt", new TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isHidden", new TableInfo.Column("isHidden", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockMessage", new TableInfo.Column("unlockMessage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("triggerConditions", new TableInfo.Column("triggerConditions", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(connection, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenDelegate.ValidationResult(false, "achievements(com.bhashasetu.app.data.model.Achievement).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
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
        final Map<String, TableInfo.Column> _columnsPronunciationSessions = new HashMap<String, TableInfo.Column>(10);
        _columnsPronunciationSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("startTime", new TableInfo.Column("startTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("words", new TableInfo.Column("words", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("scores", new TableInfo.Column("scores", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("averageAccuracyScore", new TableInfo.Column("averageAccuracyScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("averageRhythmScore", new TableInfo.Column("averageRhythmScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("averageIntonationScore", new TableInfo.Column("averageIntonationScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationSessions.put("difficultyLevel", new TableInfo.Column("difficultyLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysPronunciationSessions = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesPronunciationSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPronunciationSessions = new TableInfo("pronunciation_sessions", _columnsPronunciationSessions, _foreignKeysPronunciationSessions, _indicesPronunciationSessions);
        final TableInfo _existingPronunciationSessions = TableInfo.read(connection, "pronunciation_sessions");
        if (!_infoPronunciationSessions.equals(_existingPronunciationSessions)) {
          return new RoomOpenDelegate.ValidationResult(false, "pronunciation_sessions(com.bhashasetu.app.model.PronunciationSession).\n"
                  + " Expected:\n" + _infoPronunciationSessions + "\n"
                  + " Found:\n" + _existingPronunciationSessions);
        }
        final Map<String, TableInfo.Column> _columnsPronunciationScores = new HashMap<String, TableInfo.Column>(9);
        _columnsPronunciationScores.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("word", new TableInfo.Column("word", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("accuracyScore", new TableInfo.Column("accuracyScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("rhythmScore", new TableInfo.Column("rhythmScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("intonationScore", new TableInfo.Column("intonationScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("phoneticFeedback", new TableInfo.Column("phoneticFeedback", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("recordingPath", new TableInfo.Column("recordingPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPronunciationScores.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysPronunciationScores = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesPronunciationScores = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPronunciationScores = new TableInfo("pronunciation_scores", _columnsPronunciationScores, _foreignKeysPronunciationScores, _indicesPronunciationScores);
        final TableInfo _existingPronunciationScores = TableInfo.read(connection, "pronunciation_scores");
        if (!_infoPronunciationScores.equals(_existingPronunciationScores)) {
          return new RoomOpenDelegate.ValidationResult(false, "pronunciation_scores(com.bhashasetu.app.pronunciation.PronunciationScore).\n"
                  + " Expected:\n" + _infoPronunciationScores + "\n"
                  + " Found:\n" + _existingPronunciationScores);
        }
        final Map<String, TableInfo.Column> _columnsUserGoals = new HashMap<String, TableInfo.Column>(18);
        _columnsUserGoals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("goalType", new TableInfo.Column("goalType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("targetValue", new TableInfo.Column("targetValue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("currentValue", new TableInfo.Column("currentValue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("periodType", new TableInfo.Column("periodType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("repeatDaily", new TableInfo.Column("repeatDaily", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("reminderEnabled", new TableInfo.Column("reminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("reminderTime", new TableInfo.Column("reminderTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("completed", new TableInfo.Column("completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserGoals.put("lastUpdatedAt", new TableInfo.Column("lastUpdatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUserGoals = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysUserGoals.add(new TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesUserGoals = new HashSet<TableInfo.Index>(1);
        _indicesUserGoals.add(new TableInfo.Index("index_user_goals_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoUserGoals = new TableInfo("user_goals", _columnsUserGoals, _foreignKeysUserGoals, _indicesUserGoals);
        final TableInfo _existingUserGoals = TableInfo.read(connection, "user_goals");
        if (!_infoUserGoals.equals(_existingUserGoals)) {
          return new RoomOpenDelegate.ValidationResult(false, "user_goals(com.bhashasetu.app.data.model.UserGoal).\n"
                  + " Expected:\n" + _infoUserGoals + "\n"
                  + " Found:\n" + _existingUserGoals);
        }
        final Map<String, TableInfo.Column> _columnsLessons = new HashMap<String, TableInfo.Column>(17);
        _columnsLessons.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("titleEnglish", new TableInfo.Column("titleEnglish", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("titleHindi", new TableInfo.Column("titleHindi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("descriptionEnglish", new TableInfo.Column("descriptionEnglish", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("descriptionHindi", new TableInfo.Column("descriptionHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("contentEnglish", new TableInfo.Column("contentEnglish", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("contentHindi", new TableInfo.Column("contentHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("orderInCategory", new TableInfo.Column("orderInCategory", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("estimatedDurationMinutes", new TableInfo.Column("estimatedDurationMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("lastAccessedAt", new TableInfo.Column("lastAccessedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("lastModifiedAt", new TableInfo.Column("lastModifiedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessons.put("isSystemLesson", new TableInfo.Column("isSystemLesson", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysLessons = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLessons.add(new TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesLessons = new HashSet<TableInfo.Index>(1);
        _indicesLessons.add(new TableInfo.Index("index_lessons_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoLessons = new TableInfo("lessons", _columnsLessons, _foreignKeysLessons, _indicesLessons);
        final TableInfo _existingLessons = TableInfo.read(connection, "lessons");
        if (!_infoLessons.equals(_existingLessons)) {
          return new RoomOpenDelegate.ValidationResult(false, "lessons(com.bhashasetu.app.data.model.Lesson).\n"
                  + " Expected:\n" + _infoLessons + "\n"
                  + " Found:\n" + _existingLessons);
        }
        final Map<String, TableInfo.Column> _columnsLessonWords = new HashMap<String, TableInfo.Column>(7);
        _columnsLessonWords.put("lessonId", new TableInfo.Column("lessonId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("wordId", new TableInfo.Column("wordId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("orderInLesson", new TableInfo.Column("orderInLesson", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("isKeyword", new TableInfo.Column("isKeyword", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("includeInQuiz", new TableInfo.Column("includeInQuiz", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLessonWords.put("highlightInContent", new TableInfo.Column("highlightInContent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysLessonWords = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysLessonWords.add(new TableInfo.ForeignKey("lessons", "CASCADE", "NO ACTION", Arrays.asList("lessonId"), Arrays.asList("id")));
        _foreignKeysLessonWords.add(new TableInfo.ForeignKey("words", "CASCADE", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesLessonWords = new HashSet<TableInfo.Index>(2);
        _indicesLessonWords.add(new TableInfo.Index("index_lesson_words_lessonId", false, Arrays.asList("lessonId"), Arrays.asList("ASC")));
        _indicesLessonWords.add(new TableInfo.Index("index_lesson_words_wordId", false, Arrays.asList("wordId"), Arrays.asList("ASC")));
        final TableInfo _infoLessonWords = new TableInfo("lesson_words", _columnsLessonWords, _foreignKeysLessonWords, _indicesLessonWords);
        final TableInfo _existingLessonWords = TableInfo.read(connection, "lesson_words");
        if (!_infoLessonWords.equals(_existingLessonWords)) {
          return new RoomOpenDelegate.ValidationResult(false, "lesson_words(com.bhashasetu.app.data.model.LessonWord).\n"
                  + " Expected:\n" + _infoLessonWords + "\n"
                  + " Found:\n" + _existingLessonWords);
        }
        final Map<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(10);
        _columnsCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("nameEnglish", new TableInfo.Column("nameEnglish", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("nameHindi", new TableInfo.Column("nameHindi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("descriptionEnglish", new TableInfo.Column("descriptionEnglish", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("descriptionHindi", new TableInfo.Column("descriptionHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("iconResId", new TableInfo.Column("iconResId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("colorHex", new TableInfo.Column("colorHex", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("orderIndex", new TableInfo.Column("orderIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("isDefault", new TableInfo.Column("isDefault", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(connection, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenDelegate.ValidationResult(false, "categories(com.bhashasetu.app.data.model.Category).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final Map<String, TableInfo.Column> _columnsQuizzes = new HashMap<String, TableInfo.Column>(21);
        _columnsQuizzes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("titleEnglish", new TableInfo.Column("titleEnglish", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("titleHindi", new TableInfo.Column("titleHindi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("instructionsEnglish", new TableInfo.Column("instructionsEnglish", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("instructionsHindi", new TableInfo.Column("instructionsHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("lessonId", new TableInfo.Column("lessonId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("questionCount", new TableInfo.Column("questionCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("timeLimit", new TableInfo.Column("timeLimit", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("passingScorePercent", new TableInfo.Column("passingScorePercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("quizType", new TableInfo.Column("quizType", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("directionType", new TableInfo.Column("directionType", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("lastAttemptScore", new TableInfo.Column("lastAttemptScore", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("bestScore", new TableInfo.Column("bestScore", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("attemptCount", new TableInfo.Column("attemptCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("lastAttemptAt", new TableInfo.Column("lastAttemptAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizzes.put("isSystemQuiz", new TableInfo.Column("isSystemQuiz", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysQuizzes = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysQuizzes.add(new TableInfo.ForeignKey("lessons", "SET NULL", "NO ACTION", Arrays.asList("lessonId"), Arrays.asList("id")));
        _foreignKeysQuizzes.add(new TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesQuizzes = new HashSet<TableInfo.Index>(2);
        _indicesQuizzes.add(new TableInfo.Index("index_quizzes_lessonId", false, Arrays.asList("lessonId"), Arrays.asList("ASC")));
        _indicesQuizzes.add(new TableInfo.Index("index_quizzes_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoQuizzes = new TableInfo("quizzes", _columnsQuizzes, _foreignKeysQuizzes, _indicesQuizzes);
        final TableInfo _existingQuizzes = TableInfo.read(connection, "quizzes");
        if (!_infoQuizzes.equals(_existingQuizzes)) {
          return new RoomOpenDelegate.ValidationResult(false, "quizzes(com.bhashasetu.app.data.model.Quiz).\n"
                  + " Expected:\n" + _infoQuizzes + "\n"
                  + " Found:\n" + _existingQuizzes);
        }
        final Map<String, TableInfo.Column> _columnsQuizQuestions = new HashMap<String, TableInfo.Column>(17);
        _columnsQuizQuestions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("quizId", new TableInfo.Column("quizId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("wordId", new TableInfo.Column("wordId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("questionTextEnglish", new TableInfo.Column("questionTextEnglish", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("questionTextHindi", new TableInfo.Column("questionTextHindi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("questionType", new TableInfo.Column("questionType", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("correctAnswer", new TableInfo.Column("correctAnswer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("options", new TableInfo.Column("options", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("audioUrl", new TableInfo.Column("audioUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("explanationEnglish", new TableInfo.Column("explanationEnglish", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("explanationHindi", new TableInfo.Column("explanationHindi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("orderInQuiz", new TableInfo.Column("orderInQuiz", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("correctAttempts", new TableInfo.Column("correctAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuizQuestions.put("totalAttempts", new TableInfo.Column("totalAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysQuizQuestions = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysQuizQuestions.add(new TableInfo.ForeignKey("quizzes", "CASCADE", "NO ACTION", Arrays.asList("quizId"), Arrays.asList("id")));
        _foreignKeysQuizQuestions.add(new TableInfo.ForeignKey("words", "SET NULL", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesQuizQuestions = new HashSet<TableInfo.Index>(2);
        _indicesQuizQuestions.add(new TableInfo.Index("index_quiz_questions_quizId", false, Arrays.asList("quizId"), Arrays.asList("ASC")));
        _indicesQuizQuestions.add(new TableInfo.Index("index_quiz_questions_wordId", false, Arrays.asList("wordId"), Arrays.asList("ASC")));
        final TableInfo _infoQuizQuestions = new TableInfo("quiz_questions", _columnsQuizQuestions, _foreignKeysQuizQuestions, _indicesQuizQuestions);
        final TableInfo _existingQuizQuestions = TableInfo.read(connection, "quiz_questions");
        if (!_infoQuizQuestions.equals(_existingQuizQuestions)) {
          return new RoomOpenDelegate.ValidationResult(false, "quiz_questions(com.bhashasetu.app.data.model.QuizQuestion).\n"
                  + " Expected:\n" + _infoQuizQuestions + "\n"
                  + " Found:\n" + _existingQuizQuestions);
        }
        final Map<String, TableInfo.Column> _columnsStudySessions = new HashMap<String, TableInfo.Column>(15);
        _columnsStudySessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("sessionType", new TableInfo.Column("sessionType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("lessonId", new TableInfo.Column("lessonId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("durationMinutes", new TableInfo.Column("durationMinutes", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("itemCount", new TableInfo.Column("itemCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("correctCount", new TableInfo.Column("correctCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("score", new TableInfo.Column("score", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("deviceInfo", new TableInfo.Column("deviceInfo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("interfaceLanguage", new TableInfo.Column("interfaceLanguage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("wasPaused", new TableInfo.Column("wasPaused", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysStudySessions = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysStudySessions.add(new TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        _foreignKeysStudySessions.add(new TableInfo.ForeignKey("lessons", "SET NULL", "NO ACTION", Arrays.asList("lessonId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesStudySessions = new HashSet<TableInfo.Index>(2);
        _indicesStudySessions.add(new TableInfo.Index("index_study_sessions_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        _indicesStudySessions.add(new TableInfo.Index("index_study_sessions_lessonId", false, Arrays.asList("lessonId"), Arrays.asList("ASC")));
        final TableInfo _infoStudySessions = new TableInfo("study_sessions", _columnsStudySessions, _foreignKeysStudySessions, _indicesStudySessions);
        final TableInfo _existingStudySessions = TableInfo.read(connection, "study_sessions");
        if (!_infoStudySessions.equals(_existingStudySessions)) {
          return new RoomOpenDelegate.ValidationResult(false, "study_sessions(com.bhashasetu.app.data.model.StudySession).\n"
                  + " Expected:\n" + _infoStudySessions + "\n"
                  + " Found:\n" + _existingStudySessions);
        }
        final Map<String, TableInfo.Column> _columnsDailyStreaks = new HashMap<String, TableInfo.Column>(13);
        _columnsDailyStreaks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("longestStreak", new TableInfo.Column("longestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("totalDaysActive", new TableInfo.Column("totalDaysActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("lastActiveDate", new TableInfo.Column("lastActiveDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("isTodayCompleted", new TableInfo.Column("isTodayCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("streakFreezeAvailable", new TableInfo.Column("streakFreezeAvailable", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("streakFreezeUsed", new TableInfo.Column("streakFreezeUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("lastStreakFreezeDate", new TableInfo.Column("lastStreakFreezeDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("nextMilestone", new TableInfo.Column("nextMilestone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("milestoneReached", new TableInfo.Column("milestoneReached", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("weeklyActivity", new TableInfo.Column("weeklyActivity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyStreaks.put("monthlyActivity", new TableInfo.Column("monthlyActivity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysDailyStreaks = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesDailyStreaks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyStreaks = new TableInfo("daily_streaks", _columnsDailyStreaks, _foreignKeysDailyStreaks, _indicesDailyStreaks);
        final TableInfo _existingDailyStreaks = TableInfo.read(connection, "daily_streaks");
        if (!_infoDailyStreaks.equals(_existingDailyStreaks)) {
          return new RoomOpenDelegate.ValidationResult(false, "daily_streaks(com.bhashasetu.app.data.model.DailyStreak).\n"
                  + " Expected:\n" + _infoDailyStreaks + "\n"
                  + " Found:\n" + _existingDailyStreaks);
        }
        final Map<String, TableInfo.Column> _columnsAppSettings = new HashMap<String, TableInfo.Column>(28);
        _columnsAppSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("interfaceLanguage", new TableInfo.Column("interfaceLanguage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("primaryLanguage", new TableInfo.Column("primaryLanguage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("secondaryLanguage", new TableInfo.Column("secondaryLanguage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("useDevanagariDigits", new TableInfo.Column("useDevanagariDigits", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("theme", new TableInfo.Column("theme", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("fontSizeMultiplier", new TableInfo.Column("fontSizeMultiplier", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("highContrastMode", new TableInfo.Column("highContrastMode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("dailyWordGoal", new TableInfo.Column("dailyWordGoal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("dailyTimeGoalMinutes", new TableInfo.Column("dailyTimeGoalMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("reminderTime", new TableInfo.Column("reminderTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("reminderDays", new TableInfo.Column("reminderDays", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("autoPlayPronunciation", new TableInfo.Column("autoPlayPronunciation", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("pronunciationSpeed", new TableInfo.Column("pronunciationSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("autoRecordEnabled", new TableInfo.Column("autoRecordEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("soundEnabled", new TableInfo.Column("soundEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("vibrationEnabled", new TableInfo.Column("vibrationEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("flashcardSessionSize", new TableInfo.Column("flashcardSessionSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("quizTimeLimit", new TableInfo.Column("quizTimeLimit", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("showTranslationHints", new TableInfo.Column("showTranslationHints", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("dailyReminderEnabled", new TableInfo.Column("dailyReminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("streakReminderEnabled", new TableInfo.Column("streakReminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("achievementNotificationsEnabled", new TableInfo.Column("achievementNotificationsEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("quizResultsNotificationsEnabled", new TableInfo.Column("quizResultsNotificationsEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("lastSelectedCategoryId", new TableInfo.Column("lastSelectedCategoryId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("lastSelectedLessonId", new TableInfo.Column("lastSelectedLessonId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("installDate", new TableInfo.Column("installDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("lastUpdatedAt", new TableInfo.Column("lastUpdatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysAppSettings = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesAppSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppSettings = new TableInfo("app_settings", _columnsAppSettings, _foreignKeysAppSettings, _indicesAppSettings);
        final TableInfo _existingAppSettings = TableInfo.read(connection, "app_settings");
        if (!_infoAppSettings.equals(_existingAppSettings)) {
          return new RoomOpenDelegate.ValidationResult(false, "app_settings(com.bhashasetu.app.data.model.AppSettings).\n"
                  + " Expected:\n" + _infoAppSettings + "\n"
                  + " Found:\n" + _existingAppSettings);
        }
        final Map<String, TableInfo.Column> _columnsUserProgress = new HashMap<String, TableInfo.Column>(12);
        _columnsUserProgress.put("wordId", new TableInfo.Column("wordId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("proficiencyLevel", new TableInfo.Column("proficiencyLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("correctAttempts", new TableInfo.Column("correctAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("totalAttempts", new TableInfo.Column("totalAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("easeFactor", new TableInfo.Column("easeFactor", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("intervalDays", new TableInfo.Column("intervalDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("repetitions", new TableInfo.Column("repetitions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("lastAttemptAt", new TableInfo.Column("lastAttemptAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("lastCorrectAt", new TableInfo.Column("lastCorrectAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("nextReviewDue", new TableInfo.Column("nextReviewDue", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("timeSpentMs", new TableInfo.Column("timeSpentMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("lastConfidenceRating", new TableInfo.Column("lastConfidenceRating", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUserProgress = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysUserProgress.add(new TableInfo.ForeignKey("words", "CASCADE", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesUserProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProgress = new TableInfo("user_progress", _columnsUserProgress, _foreignKeysUserProgress, _indicesUserProgress);
        final TableInfo _existingUserProgress = TableInfo.read(connection, "user_progress");
        if (!_infoUserProgress.equals(_existingUserProgress)) {
          return new RoomOpenDelegate.ValidationResult(false, "user_progress(com.bhashasetu.app.data.model.UserProgress).\n"
                  + " Expected:\n" + _infoUserProgress + "\n"
                  + " Found:\n" + _existingUserProgress);
        }
        final Map<String, TableInfo.Column> _columnsWords = new HashMap<String, TableInfo.Column>(16);
        _columnsWords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("englishText", new TableInfo.Column("englishText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hindiText", new TableInfo.Column("hindiText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("englishPronunciation", new TableInfo.Column("englishPronunciation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hindiPronunciation", new TableInfo.Column("hindiPronunciation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("englishExample", new TableInfo.Column("englishExample", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hindiExample", new TableInfo.Column("hindiExample", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("partOfSpeech", new TableInfo.Column("partOfSpeech", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("englishAudioPath", new TableInfo.Column("englishAudioPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("hindiAudioPath", new TableInfo.Column("hindiAudioPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("lastModifiedAt", new TableInfo.Column("lastModifiedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("lastReviewedAt", new TableInfo.Column("lastReviewedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("nextReviewDate", new TableInfo.Column("nextReviewDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysWords = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesWords = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWords = new TableInfo("words", _columnsWords, _foreignKeysWords, _indicesWords);
        final TableInfo _existingWords = TableInfo.read(connection, "words");
        if (!_infoWords.equals(_existingWords)) {
          return new RoomOpenDelegate.ValidationResult(false, "words(com.bhashasetu.app.data.model.Word).\n"
                  + " Expected:\n" + _infoWords + "\n"
                  + " Found:\n" + _existingWords);
        }
        final Map<String, TableInfo.Column> _columnsWordCategoryCrossRefs = new HashMap<String, TableInfo.Column>(3);
        _columnsWordCategoryCrossRefs.put("wordId", new TableInfo.Column("wordId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordCategoryCrossRefs.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordCategoryCrossRefs.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysWordCategoryCrossRefs = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysWordCategoryCrossRefs.add(new TableInfo.ForeignKey("words", "CASCADE", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        _foreignKeysWordCategoryCrossRefs.add(new TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        final Set<TableInfo.Index> _indicesWordCategoryCrossRefs = new HashSet<TableInfo.Index>(2);
        _indicesWordCategoryCrossRefs.add(new TableInfo.Index("index_word_category_cross_refs_wordId", false, Arrays.asList("wordId"), Arrays.asList("ASC")));
        _indicesWordCategoryCrossRefs.add(new TableInfo.Index("index_word_category_cross_refs_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoWordCategoryCrossRefs = new TableInfo("word_category_cross_refs", _columnsWordCategoryCrossRefs, _foreignKeysWordCategoryCrossRefs, _indicesWordCategoryCrossRefs);
        final TableInfo _existingWordCategoryCrossRefs = TableInfo.read(connection, "word_category_cross_refs");
        if (!_infoWordCategoryCrossRefs.equals(_existingWordCategoryCrossRefs)) {
          return new RoomOpenDelegate.ValidationResult(false, "word_category_cross_refs(com.bhashasetu.app.data.model.WordCategoryCrossRef).\n"
                  + " Expected:\n" + _infoWordCategoryCrossRefs + "\n"
                  + " Found:\n" + _existingWordCategoryCrossRefs);
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "words", "word_categories", "users", "user_progress", "game_scores", "legacy_achievements", "achievements", "badges", "user_levels", "user_points", "user_stats", "pronunciation_sessions", "pronunciation_scores", "user_goals", "lessons", "lesson_words", "categories", "quizzes", "quiz_questions", "study_sessions", "daily_streaks", "app_settings", "user_progress", "words", "word_category_cross_refs");
  }

  @Override
  public void clearAllTables() {
    super.performClear(true, "words", "word_categories", "users", "user_progress", "game_scores", "legacy_achievements", "achievements", "badges", "user_levels", "user_points", "user_stats", "pronunciation_sessions", "pronunciation_scores", "user_goals", "lessons", "lesson_words", "categories", "quizzes", "quiz_questions", "study_sessions", "daily_streaks", "app_settings", "user_progress", "words", "word_category_cross_refs");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_AppDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(ModernAchievementDao.class, ModernAchievementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WordDao.class, WordDao_AppDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(WordCategoryDao.class, WordCategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProgressDao.class, UserProgressDao_AppDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(GameScoreDao.class, GameScoreDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BadgeDao.class, BadgeDao_AppDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserLevelDao.class, UserLevelDao_AppDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserPointsDao.class, UserPointsDao_AppDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserStatsDao.class, UserStatsDao_AppDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(PronunciationSessionDao.class, PronunciationSessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PronunciationScoreDao.class, PronunciationScoreDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserGoalDao.class, UserGoalDao_AppDatabase_0_Impl.getRequiredConverters());
    _typeConvertersMap.put(LessonDao.class, LessonDao_AppDatabase_0_Impl.getRequiredConverters());
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
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_AppDatabase_Impl(this);
        }
        return _achievementDao;
      }
    }
  }

  @Override
  public ModernAchievementDao modernAchievementDao() {
    if (_modernAchievementDao != null) {
      return _modernAchievementDao;
    } else {
      synchronized(this) {
        if(_modernAchievementDao == null) {
          _modernAchievementDao = new ModernAchievementDao_Impl(this);
        }
        return _modernAchievementDao;
      }
    }
  }

  @Override
  public WordDao wordDao() {
    if (_wordDao != null) {
      return _wordDao;
    } else {
      synchronized(this) {
        if(_wordDao == null) {
          _wordDao = new WordDao_AppDatabase_Impl(this);
        }
        return _wordDao;
      }
    }
  }

  @Override
  public WordCategoryDao wordCategoryDao() {
    if (_wordCategoryDao != null) {
      return _wordCategoryDao;
    } else {
      synchronized(this) {
        if(_wordCategoryDao == null) {
          _wordCategoryDao = new WordCategoryDao_Impl(this);
        }
        return _wordCategoryDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
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
          _userProgressDao = new UserProgressDao_AppDatabase_Impl(this);
        }
        return _userProgressDao;
      }
    }
  }

  @Override
  public GameScoreDao gameScoreDao() {
    if (_gameScoreDao != null) {
      return _gameScoreDao;
    } else {
      synchronized(this) {
        if(_gameScoreDao == null) {
          _gameScoreDao = new GameScoreDao_Impl(this);
        }
        return _gameScoreDao;
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
          _badgeDao = new BadgeDao_AppDatabase_Impl(this);
        }
        return _badgeDao;
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
          _userLevelDao = new UserLevelDao_AppDatabase_Impl(this);
        }
        return _userLevelDao;
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
          _userPointsDao = new UserPointsDao_AppDatabase_Impl(this);
        }
        return _userPointsDao;
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
          _userStatsDao = new UserStatsDao_AppDatabase_Impl(this);
        }
        return _userStatsDao;
      }
    }
  }

  @Override
  public PronunciationSessionDao pronunciationSessionDao() {
    if (_pronunciationSessionDao != null) {
      return _pronunciationSessionDao;
    } else {
      synchronized(this) {
        if(_pronunciationSessionDao == null) {
          _pronunciationSessionDao = new PronunciationSessionDao_Impl(this);
        }
        return _pronunciationSessionDao;
      }
    }
  }

  @Override
  public PronunciationScoreDao pronunciationScoreDao() {
    if (_pronunciationScoreDao != null) {
      return _pronunciationScoreDao;
    } else {
      synchronized(this) {
        if(_pronunciationScoreDao == null) {
          _pronunciationScoreDao = new PronunciationScoreDao_Impl(this);
        }
        return _pronunciationScoreDao;
      }
    }
  }

  @Override
  public UserGoalDao userGoalDao() {
    if (_userGoalDao != null) {
      return _userGoalDao;
    } else {
      synchronized(this) {
        if(_userGoalDao == null) {
          _userGoalDao = new UserGoalDao_AppDatabase_0_Impl(this);
        }
        return _userGoalDao;
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
          _lessonDao = new LessonDao_AppDatabase_0_Impl(this);
        }
        return _lessonDao;
      }
    }
  }
}
