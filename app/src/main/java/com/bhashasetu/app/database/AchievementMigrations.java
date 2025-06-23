package com.bhashasetu.app.database;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Database migrations for Achievement System Consolidation
 * Handles transition from 3-table conflicting system to 2-table clean system
 */
public class AchievementMigrations {

    /**
     * Migration from version 3 to 4: Achievement System Consolidation
     * - Ensures legacy_achievements table exists and is properly configured
     * - Creates new achievements table for modern bilingual system
     * - Migrates data from user_achievements to achievements (optional)
     * - Drops user_achievements table to eliminate conflicts
     */
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            // Step 1: Ensure legacy_achievements table has correct structure
            database.execSQL("CREATE TABLE IF NOT EXISTS `legacy_achievements_new` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`title` TEXT, " +
                    "`description` TEXT, " +
                    "`type` TEXT, " +
                    "`progressTarget` INTEGER NOT NULL, " +
                    "`currentProgress` INTEGER NOT NULL, " +
                    "`unlocked` INTEGER NOT NULL, " +
                    "`dateUnlocked` INTEGER, " +
                    "`pointsValue` INTEGER NOT NULL, " +
                    "`iconResId` INTEGER NOT NULL" +
                    ")");

            // Copy existing data if legacy_achievements exists
            database.execSQL("INSERT INTO legacy_achievements_new " +
                    "SELECT * FROM legacy_achievements WHERE EXISTS(SELECT name FROM sqlite_master WHERE type='table' AND name='legacy_achievements')");

            // Drop old table and rename new one
            database.execSQL("DROP TABLE IF EXISTS legacy_achievements");
            database.execSQL("ALTER TABLE legacy_achievements_new RENAME TO legacy_achievements");

            // Step 2: Create new modern achievements table
            database.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (" +
                    "`id` TEXT NOT NULL, " +
                    "`titleEnglish` TEXT NOT NULL, " +
                    "`titleHindi` TEXT NOT NULL, " +
                    "`descriptionEnglish` TEXT NOT NULL, " +
                    "`descriptionHindi` TEXT NOT NULL, " +
                    "`iconResId` INTEGER NOT NULL, " +
                    "`badgeImageResId` INTEGER, " +
                    "`colorHex` TEXT, " +
                    "`category` TEXT NOT NULL, " +
                    "`type` TEXT NOT NULL, " +
                    "`maxProgress` INTEGER NOT NULL, " +
                    "`currentProgress` INTEGER NOT NULL, " +
                    "`pointsRewarded` INTEGER NOT NULL, " +
                    "`isUnlocked` INTEGER NOT NULL, " +
                    "`unlockedAt` INTEGER, " +
                    "`isHidden` INTEGER NOT NULL, " +
                    "`unlockMessage` TEXT, " +
                    "`triggerConditions` TEXT, " +
                    "PRIMARY KEY(`id`)" +
                    ")");

            // Step 3: Optional - Migrate data from user_achievements to achievements
            // This preserves any existing gamification achievement data
            database.execSQL("INSERT OR IGNORE INTO achievements (" +
                    "id, titleEnglish, titleHindi, descriptionEnglish, descriptionHindi, " +
                    "iconResId, category, type, maxProgress, currentProgress, pointsRewarded, " +
                    "isUnlocked, unlockedAt, isHidden, unlockMessage" +
                    ") " +
                    "SELECT " +
                    "'migrated_' || id as id, " +
                    "title as titleEnglish, " +
                    "title as titleHindi, " +
                    "description as descriptionEnglish, " +
                    "description as descriptionHindi, " +
                    "CASE WHEN badgeImagePath IS NOT NULL THEN 1 ELSE android.R.drawable.star_on END as iconResId, " +
                    "'Migrated' as category, " +
                    "CASE WHEN type = 0 THEN 'vocabulary' WHEN type = 1 THEN 'streak' ELSE 'general' END as type, " +
                    "threshold as maxProgress, " +
                    "currentProgress, " +
                    "pointsAwarded as pointsRewarded, " +
                    "unlocked as isUnlocked, " +
                    "CASE WHEN unlockedAt IS NOT NULL THEN strftime('%s', unlockedAt) * 1000 ELSE NULL END as unlockedAt, " +
                    "0 as isHidden, " +
                    "'Achievement migrated from previous system' as unlockMessage " +
                    "FROM user_achievements WHERE EXISTS(SELECT name FROM sqlite_master WHERE type='table' AND name='user_achievements')");

            // Step 4: Drop conflicting user_achievements table
            database.execSQL("DROP TABLE IF EXISTS user_achievements");

            // Step 5: Create performance indexes
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_legacy_achievements_type` ON `legacy_achievements` (`type`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_legacy_achievements_unlocked` ON `legacy_achievements` (`unlocked`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_achievements_category` ON `achievements` (`category`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_achievements_type` ON `achievements` (`type`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_achievements_unlocked` ON `achievements` (`isUnlocked`)");
        }
    };

    /**
     * Migration from version 2 to 4: Direct migration for older databases
     * For databases that might be on version 2 and need to jump to 4
     */
    public static final Migration MIGRATION_2_4 = new Migration(2, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // This essentially does the same as MIGRATION_3_4 but from version 2
            MIGRATION_3_4.migrate(database);
        }
    };
}