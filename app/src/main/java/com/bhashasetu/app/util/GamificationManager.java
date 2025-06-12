package com.bhashasetu.app.util;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bhashasetu.app.database.WordDatabase;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.model.exercise.Exercise;
import com.bhashasetu.app.model.gamification.Achievement;
import com.bhashasetu.app.model.gamification.AchievementType;
import com.bhashasetu.app.model.gamification.Badge;
import com.bhashasetu.app.model.gamification.BadgeTier;
import com.bhashasetu.app.model.gamification.PointsSource;
import com.bhashasetu.app.model.gamification.UserLevel;
import com.bhashasetu.app.model.gamification.UserPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manager class for handling achievements, badges, points, and levels
 */
public class GamificationManager {
    
    private static GamificationManager instance;
    private final Context context;
    private final WordDatabase database;
    private final ExecutorService executorService;
    
    private final MutableLiveData<List<Achievement>> recentAchievements = new MutableLiveData<>();
    private final MutableLiveData<Integer> totalPoints = new MutableLiveData<>();
    private final MutableLiveData<UserLevel> userLevel = new MutableLiveData<>();
    
    private GamificationManager(Context context) {
        this.context = context.getApplicationContext();
        this.database = WordDatabase.getInstance(context);
        this.executorService = Executors.newSingleThreadExecutor();
        
        // Initialize default values
        totalPoints.setValue(0);
        recentAchievements.setValue(new ArrayList<>());
        userLevel.setValue(new UserLevel(1));  // Default user ID = 1
        
        // Load user data
        loadUserData();
    }
    
    public static synchronized GamificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new GamificationManager(context);
        }
        return instance;
    }
    
    /**
     * Load user's gamification data from database
     */
    private void loadUserData() {
        executorService.execute(() -> {
            try {
                // Load user level
                UserLevel level = database.userLevelDao().getUserLevel(1);
                if (level == null) {
                    level = new UserLevel(1);
                    database.userLevelDao().insert(level);
                }
                userLevel.postValue(level);
                
                // Calculate total points
                int points = database.userPointsDao().getTotalPoints(1);
                totalPoints.postValue(points);
                
                // Load recent achievements (last 5)
                List<Achievement> achievements = database.achievementDao().getRecentAchievements(5);
                recentAchievements.postValue(achievements);
                
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Initialize default achievements in the database
     */
    public void initializeDefaultAchievements() {
        executorService.execute(() -> {
            try {
                // Check if achievements already exist
                if (database.achievementDao().getAchievementCount() > 0) {
                    return;
                }
                
                // Create and insert default achievements
                List<Achievement> defaultAchievements = createDefaultAchievements();
                for (Achievement achievement : defaultAchievements) {
                    database.achievementDao().insert(achievement);
                }
                
                // Create and insert default badges
                List<Badge> defaultBadges = createDefaultBadges();
                for (Badge badge : defaultBadges) {
                    database.badgeDao().insert(badge);
                }
                
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Create default achievements for the app
     * @return list of default achievements
     */
    private List<Achievement> createDefaultAchievements() {
        List<Achievement> achievements = new ArrayList<>();
        
        // Words learned achievements
        achievements.add(new Achievement(
                "Word Novice",
                "Learn 10 words",
                AchievementType.WORDS_LEARNED,
                10,
                50,
                "badge_words_bronze"
        ));
        
        achievements.add(new Achievement(
                "Word Apprentice",
                "Learn 50 words",
                AchievementType.WORDS_LEARNED,
                50,
                150,
                "badge_words_silver"
        ));
        
        achievements.add(new Achievement(
                "Word Scholar",
                "Learn 100 words",
                AchievementType.WORDS_LEARNED,
                100,
                300,
                "badge_words_gold"
        ));
        
        // Perfect quiz achievements
        achievements.add(new Achievement(
                "Perfect Beginner",
                "Complete 1 quiz with 100% accuracy",
                AchievementType.PERFECT_QUIZ,
                1,
                50,
                "badge_quiz_bronze"
        ));
        
        achievements.add(new Achievement(
                "Perfect Adept",
                "Complete 5 quizzes with 100% accuracy",
                AchievementType.PERFECT_QUIZ,
                5,
                150,
                "badge_quiz_silver"
        ));
        
        // Daily streak achievements
        achievements.add(new Achievement(
                "Week Warrior",
                "Practice for 7 consecutive days",
                AchievementType.DAILY_STREAK,
                7,
                100,
                "badge_streak_bronze"
        ));
        
        achievements.add(new Achievement(
                "Month Master",
                "Practice for 30 consecutive days",
                AchievementType.DAILY_STREAK,
                30,
                500,
                "badge_streak_gold"
        ));
        
        // Session completed achievements
        achievements.add(new Achievement(
                "Practice Initiate",
                "Complete 5 practice sessions",
                AchievementType.SESSION_COMPLETED,
                5,
                75,
                "badge_practice_bronze"
        ));
        
        achievements.add(new Achievement(
                "Practice Devotee",
                "Complete 25 practice sessions",
                AchievementType.SESSION_COMPLETED,
                25,
                250,
                "badge_practice_silver"
        ));
        
        return achievements;
    }
    
    /**
     * Create default badges for the app
     * @return list of default badges
     */
    private List<Badge> createDefaultBadges() {
        List<Badge> badges = new ArrayList<>();
        
        // Add badges corresponding to achievements
        // In a real app, this would include more detailed badge design
        
        badges.add(new Badge(
                "Word Novice",
                "Awarded for learning 10 words",
                BadgeTier.BRONZE,
                "badge_words_bronze",
                1  // Achievement ID
        ));
        
        badges.add(new Badge(
                "Word Apprentice",
                "Awarded for learning 50 words",
                BadgeTier.SILVER,
                "badge_words_silver",
                2  // Achievement ID
        ));
        
        badges.add(new Badge(
                "Word Scholar",
                "Awarded for learning 100 words",
                BadgeTier.GOLD,
                "badge_words_gold",
                3  // Achievement ID
        ));
        
        // Add more badges for other achievements
        
        return badges;
    }
    
    /**
     * Add points to the user
     * @param points amount of points to add
     * @param description description of the points
     * @param source source of the points
     * @param sourceId ID of the source
     */
    public void addPoints(int points, String description, PointsSource source, int sourceId) {
        if (points <= 0) {
            return;
        }
        
        executorService.execute(() -> {
            try {
                // Create points record
                UserPoints userPoints = new UserPoints(
                        1,  // Default user ID
                        points,
                        description,
                        source,
                        sourceId
                );
                
                // Insert into database
                database.userPointsDao().insert(userPoints);
                
                // Update total points
                int newTotal = (totalPoints.getValue() != null ? totalPoints.getValue() : 0) + points;
                totalPoints.postValue(newTotal);
                
                // Update user level with XP
                UserLevel level = userLevel.getValue();
                if (level != null) {
                    boolean leveledUp = level.addExp(points);
                    database.userLevelDao().update(level);
                    userLevel.postValue(level);
                    
                    // If user leveled up, award level up points
                    if (leveledUp) {
                        int levelUpBonus = level.getLevel() * 50;  // Bonus based on new level
                        addPoints(levelUpBonus, "Level Up Bonus", PointsSource.LEVEL_UP, level.getLevel());
                    }
                }
                
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Record a completed word learning
     * @param word the word that was learned
     */
    public void recordWordLearned(Word word) {
        executorService.execute(() -> {
            try {
                // Update word count achievement progress
                List<Achievement> wordAchievements = database.achievementDao()
                        .getAchievementsByType(AchievementType.WORDS_LEARNED);
                
                for (Achievement achievement : wordAchievements) {
                    if (!achievement.isUnlocked()) {
                        boolean unlocked = achievement.incrementProgress();
                        database.achievementDao().update(achievement);
                        
                        if (unlocked) {
                            // Add achievement to recent list
                            List<Achievement> recent = recentAchievements.getValue();
                            if (recent != null) {
                                recent.add(0, achievement);
                                if (recent.size() > 5) {
                                    recent.remove(recent.size() - 1);
                                }
                                recentAchievements.postValue(recent);
                            }
                            
                            // Award points for achievement
                            addPoints(achievement.getPointsAwarded(), 
                                    "Achievement: " + achievement.getTitle(), 
                                    PointsSource.ACHIEVEMENT_UNLOCK, 
                                    achievement.getId());
                            
                            // Unlock corresponding badge
                            Badge badge = database.badgeDao().getBadgeByAchievementId(achievement.getId());
                            if (badge != null && !badge.isEarned()) {
                                badge.setEarned(true);
                                database.badgeDao().update(badge);
                            }
                        }
                    }
                }
                
                // Add points for learning a word
                int wordPoints = 5 + (word.getDifficulty() * 2);  // Base points + difficulty bonus
                addPoints(wordPoints, "Learned: " + word.getEnglishWord(), 
                        PointsSource.WORD_MASTERY, word.getId());
                
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Record a completed exercise
     * @param exercise the completed exercise
     */
    public void recordExerciseCompleted(Exercise exercise) {
        executorService.execute(() -> {
            try {
                // Update exercise count achievement progress
                List<Achievement> exerciseAchievements = database.achievementDao()
                        .getAchievementsByType(AchievementType.EXERCISES_COMPLETED);
                
                for (Achievement achievement : exerciseAchievements) {
                    if (!achievement.isUnlocked()) {
                        boolean unlocked = achievement.incrementProgress();
                        database.achievementDao().update(achievement);
                        
                        if (unlocked) {
                            // Add achievement to recent list
                            List<Achievement> recent = recentAchievements.getValue();
                            if (recent != null) {
                                recent.add(0, achievement);
                                if (recent.size() > 5) {
                                    recent.remove(recent.size() - 1);
                                }
                                recentAchievements.postValue(recent);
                            }
                            
                            // Award points for achievement
                            addPoints(achievement.getPointsAwarded(), 
                                    "Achievement: " + achievement.getTitle(), 
                                    PointsSource.ACHIEVEMENT_UNLOCK, 
                                    achievement.getId());
                            
                            // Unlock corresponding badge
                            Badge badge = database.badgeDao().getBadgeByAchievementId(achievement.getId());
                            if (badge != null && !badge.isEarned()) {
                                badge.setEarned(true);
                                database.badgeDao().update(badge);
                            }
                        }
                    }
                }
                
                // Check for perfect score achievements
                if (exercise.getScorePercentage() == 100) {
                    List<Achievement> perfectAchievements = database.achievementDao()
                            .getAchievementsByType(AchievementType.PERFECT_QUIZ);
                    
                    for (Achievement achievement : perfectAchievements) {
                        if (!achievement.isUnlocked()) {
                            boolean unlocked = achievement.incrementProgress();
                            database.achievementDao().update(achievement);
                            
                            if (unlocked) {
                                // Add achievement to recent list
                                List<Achievement> recent = recentAchievements.getValue();
                                if (recent != null) {
                                    recent.add(0, achievement);
                                    if (recent.size() > 5) {
                                        recent.remove(recent.size() - 1);
                                    }
                                    recentAchievements.postValue(recent);
                                }
                                
                                // Award points for achievement
                                addPoints(achievement.getPointsAwarded(), 
                                        "Achievement: " + achievement.getTitle(), 
                                        PointsSource.ACHIEVEMENT_UNLOCK, 
                                        achievement.getId());
                                
                                // Unlock corresponding badge
                                Badge badge = database.badgeDao().getBadgeByAchievementId(achievement.getId());
                                if (badge != null && !badge.isEarned()) {
                                    badge.setEarned(true);
                                    database.badgeDao().update(badge);
                                }
                            }
                        }
                    }
                    
                    // Extra points for perfect score
                    addPoints(25, "Perfect Score Bonus", PointsSource.PERFECT_SCORE, exercise.getId());
                }
                
                // Add points for completing the exercise
                int exercisePoints = exercise.getEarnedPoints();
                addPoints(exercisePoints, "Completed: " + exercise.getTitle(), 
                        PointsSource.EXERCISE_COMPLETION, exercise.getId());
                
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Record a day of practice for daily streak
     */
    public void recordDailyPractice() {
        executorService.execute(() -> {
            try {
                // Get user streak from database
                int currentStreak = database.userStatsDao().getDailyStreak(1);
                currentStreak++;  // Increment streak
                
                // Update streak in database
                database.userStatsDao().updateDailyStreak(1, currentStreak);
                
                // Update streak achievement progress
                List<Achievement> streakAchievements = database.achievementDao()
                        .getAchievementsByType(AchievementType.DAILY_STREAK);
                
                for (Achievement achievement : streakAchievements) {
                    if (!achievement.isUnlocked() && currentStreak >= achievement.getThreshold()) {
                        achievement.setCurrentProgress(currentStreak);
                        boolean unlocked = achievement.isUnlocked();
                        database.achievementDao().update(achievement);
                        
                        if (unlocked) {
                            // Add achievement to recent list
                            List<Achievement> recent = recentAchievements.getValue();
                            if (recent != null) {
                                recent.add(0, achievement);
                                if (recent.size() > 5) {
                                    recent.remove(recent.size() - 1);
                                }
                                recentAchievements.postValue(recent);
                            }
                            
                            // Award points for achievement
                            addPoints(achievement.getPointsAwarded(), 
                                    "Achievement: " + achievement.getTitle(), 
                                    PointsSource.ACHIEVEMENT_UNLOCK, 
                                    achievement.getId());
                            
                            // Unlock corresponding badge
                            Badge badge = database.badgeDao().getBadgeByAchievementId(achievement.getId());
                            if (badge != null && !badge.isEarned()) {
                                badge.setEarned(true);
                                database.badgeDao().update(badge);
                            }
                        }
                    }
                }
                
                // Add streak bonus points
                int streakPoints = 10;  // Base points
                if (currentStreak % 7 == 0) {
                    streakPoints = 50;  // Weekly bonus
                } else if (currentStreak % 30 == 0) {
                    streakPoints = 200;  // Monthly bonus
                }
                
                addPoints(streakPoints, "Daily Streak: " + currentStreak + " days", 
                        PointsSource.DAILY_STREAK, currentStreak);
                
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Get the user's total points
     * @return LiveData containing total points
     */
    public LiveData<Integer> getTotalPoints() {
        return totalPoints;
    }
    
    /**
     * Get the user's level information
     * @return LiveData containing user level
     */
    public LiveData<UserLevel> getUserLevel() {
        return userLevel;
    }
    
    /**
     * Get recently unlocked achievements
     * @return LiveData containing recent achievements
     */
    public LiveData<List<Achievement>> getRecentAchievements() {
        return recentAchievements;
    }
    
    /**
     * Get all unlocked badges
     * @return LiveData containing unlocked badges
     */
    public LiveData<List<Badge>> getUnlockedBadges() {
        return database.badgeDao().getEarnedBadges();
    }
    
    /**
     * Get all achievements
     * @return LiveData containing all achievements
     */
    public LiveData<List<Achievement>> getAllAchievements() {
        return database.achievementDao().getAllAchievements();
    }
    
    /**
     * Get points history (recent transactions)
     * @param limit maximum number of records to return
     * @return LiveData containing points history
     */
    public LiveData<List<UserPoints>> getPointsHistory(int limit) {
        return database.userPointsDao().getRecentPoints(1, limit);
    }
}