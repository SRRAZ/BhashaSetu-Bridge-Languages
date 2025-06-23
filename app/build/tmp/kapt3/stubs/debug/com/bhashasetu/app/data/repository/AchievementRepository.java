package com.bhashasetu.app.data.repository;

/**
 * Repository for managing both Legacy and Modern Achievement systems.
 *
 * DUAL ACHIEVEMENT SYSTEM:
 * - Legacy System: Uses legacy_achievements table for backward compatibility
 * - Modern System: Uses achievements table for new bilingual features
 *
 * USAGE GUIDELINES:
 * - Use Legacy methods for existing achievement functionality
 * - Use Modern methods for new features (bilingual, categories, analytics)
 * - Use Unified methods when you need to work with both systems together
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u00002\u00020\u0001:\u0001nB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0004\b\u0004\u0010\nJ\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001f\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000e0\f2\u0006\u0010!\u001a\u00020\"J \u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\"2\b\b\u0002\u0010%\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010&J\u001e\u0010\'\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010)J\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010+\u001a\u00020,J\u0016\u0010?\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020.H\u0086@\u00a2\u0006\u0002\u0010@J\u0016\u0010A\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020.H\u0086@\u00a2\u0006\u0002\u0010@J\u0016\u0010B\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020.H\u0086@\u00a2\u0006\u0002\u0010@J\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00020.0\f2\u0006\u0010!\u001a\u00020,J \u0010D\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020,2\b\b\u0002\u0010%\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010EJ\u001e\u0010F\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020,2\u0006\u0010G\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010HJ\u001e\u0010I\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020,2\u0006\u0010(\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010HJ\u001a\u0010J\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f2\u0006\u0010K\u001a\u00020,J\u001a\u0010L\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f2\u0006\u0010+\u001a\u00020,J\u001a\u0010M\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f2\u0006\u0010N\u001a\u00020,J\u001c\u0010O\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f2\b\b\u0002\u0010P\u001a\u00020;J\u0014\u0010Q\u001a\b\u0012\u0004\u0012\u00020.0\rH\u0086@\u00a2\u0006\u0002\u0010RJ\f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00160\fJ\f\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00160\fJ\f\u0010U\u001a\b\u0012\u0004\u0012\u00020V0\fJ\u0016\u0010W\u001a\b\u0012\u0004\u0012\u00020X0\f2\b\b\u0002\u0010Y\u001a\u00020\u0016J\u000e\u0010Z\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010RJ\u000e\u0010[\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010RJ\u0016\u0010i\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020.H\u0087@\u00a2\u0006\u0002\u0010@J\u0016\u0010j\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020.H\u0087@\u00a2\u0006\u0002\u0010@J\u0016\u0010k\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020.H\u0087@\u00a2\u0006\u0002\u0010@J\u0014\u0010l\u001a\b\u0012\u0004\u0012\u00020.0\f2\u0006\u0010!\u001a\u00020,J \u0010m\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020,2\b\b\u0002\u0010%\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010ER\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0010R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00160\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0010R\u001d\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b/\u0010\u0010R\u001d\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b1\u0010\u0010R\u001d\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b3\u0010\u0010R\u001d\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b5\u0010\u0010R\u0017\u00106\u001a\b\u0012\u0004\u0012\u00020\u00160\f8F\u00a2\u0006\u0006\u001a\u0004\b7\u0010\u0010R\u0017\u00108\u001a\b\u0012\u0004\u0012\u00020\u00160\f8F\u00a2\u0006\u0006\u001a\u0004\b9\u0010\u0010R\u0017\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\f8F\u00a2\u0006\u0006\u001a\u0004\b<\u0010\u0010R\u001d\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0\r0\f8F\u00a2\u0006\u0006\u001a\u0004\b>\u0010\u0010R&\u0010\\\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b]\u0010^\u001a\u0004\b_\u0010\u0010R&\u0010`\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\ba\u0010^\u001a\u0004\bb\u0010\u0010R&\u0010c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\r0\f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bd\u0010^\u001a\u0004\be\u0010\u0010R \u0010f\u001a\b\u0012\u0004\u0012\u00020\u00160\f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bg\u0010^\u001a\u0004\bh\u0010\u0010\u00a8\u0006o"}, d2 = {"Lcom/bhashasetu/app/data/repository/AchievementRepository;", "", "application", "Landroid/app/Application;", "<init>", "(Landroid/app/Application;)V", "legacyDao", "Lcom/bhashasetu/app/database/AchievementDao;", "modernDao", "Lcom/bhashasetu/app/database/ModernAchievementDao;", "(Lcom/bhashasetu/app/database/AchievementDao;Lcom/bhashasetu/app/database/ModernAchievementDao;)V", "allLegacyAchievements", "Landroidx/lifecycle/LiveData;", "", "Lcom/bhashasetu/app/model/Achievement;", "getAllLegacyAchievements", "()Landroidx/lifecycle/LiveData;", "unlockedLegacyAchievements", "getUnlockedLegacyAchievements", "lockedLegacyAchievements", "getLockedLegacyAchievements", "unlockedLegacyAchievementCount", "", "getUnlockedLegacyAchievementCount", "totalLegacyPoints", "getTotalLegacyPoints", "insertLegacyAchievement", "", "achievement", "(Lcom/bhashasetu/app/model/Achievement;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLegacyAchievement", "deleteLegacyAchievement", "getLegacyAchievementById", "id", "", "unlockLegacyAchievement", "achievementId", "timestamp", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLegacyAchievementProgress", "progress", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLegacyAchievementsByType", "type", "", "allModernAchievements", "Lcom/bhashasetu/app/data/model/Achievement;", "getAllModernAchievements", "unlockedModernAchievements", "getUnlockedModernAchievements", "lockedModernAchievements", "getLockedModernAchievements", "visibleModernAchievements", "getVisibleModernAchievements", "unlockedModernAchievementCount", "getUnlockedModernAchievementCount", "totalModernPoints", "getTotalModernPoints", "modernCompletionPercentage", "", "getModernCompletionPercentage", "allModernCategories", "getAllModernCategories", "insertModernAchievement", "(Lcom/bhashasetu/app/data/model/Achievement;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateModernAchievement", "deleteModernAchievement", "getModernAchievementById", "unlockModernAchievement", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementModernAchievementProgress", "increment", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setModernAchievementProgress", "getModernAchievementsByCategory", "category", "getModernAchievementsByType", "searchModernAchievements", "query", "getModernAchievementsNearCompletion", "percentage", "getModernAchievementsReadyToUnlock", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalCombinedPoints", "getTotalUnlockedCount", "hasAnyAchievements", "", "getRecentAchievements", "Lcom/bhashasetu/app/data/repository/AchievementRepository$RecentAchievementsResult;", "limit", "clearAllAchievements", "resetAllProgress", "allAchievements", "getAllAchievements$annotations", "()V", "getAllAchievements", "unlockedAchievements", "getUnlockedAchievements$annotations", "getUnlockedAchievements", "lockedAchievements", "getLockedAchievements$annotations", "getLockedAchievements", "unlockedAchievementCount", "getUnlockedAchievementCount$annotations", "getUnlockedAchievementCount", "insert", "update", "delete", "getAchievementById", "unlockAchievement", "RecentAchievementsResult", "app_debug"})
public final class AchievementRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.database.AchievementDao legacyDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.database.ModernAchievementDao modernDao = null;
    
    /**
     * Primary constructor - takes Application context
     */
    public AchievementRepository(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
    
    /**
     * Alternative constructor - takes both DAOs directly (for testing)
     */
    public AchievementRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.database.AchievementDao legacyDao, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.database.ModernAchievementDao modernDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.model.Achievement>> getAllLegacyAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.model.Achievement>> getUnlockedLegacyAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.model.Achievement>> getLockedLegacyAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getUnlockedLegacyAchievementCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTotalLegacyPoints() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertLegacyAchievement(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLegacyAchievement(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteLegacyAchievement(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.bhashasetu.app.model.Achievement> getLegacyAchievementById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unlockLegacyAchievement(long achievementId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLegacyAchievementProgress(long achievementId, int progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.model.Achievement>> getLegacyAchievementsByType(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getAllModernAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getUnlockedModernAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getLockedModernAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getVisibleModernAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getUnlockedModernAchievementCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTotalModernPoints() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Double> getModernCompletionPercentage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getAllModernCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertModernAchievement(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateModernAchievement(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteModernAchievement(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.Achievement> getModernAchievementById(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unlockModernAchievement(@org.jetbrains.annotations.NotNull()
    java.lang.String achievementId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object incrementModernAchievementProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String achievementId, int increment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setModernAchievementProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String achievementId, int progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getModernAchievementsByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getModernAchievementsByType(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> searchModernAchievements(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getModernAchievementsNearCompletion(double percentage) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getModernAchievementsReadyToUnlock(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.model.Achievement>> $completion) {
        return null;
    }
    
    /**
     * Get total points from both achievement systems combined
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTotalCombinedPoints() {
        return null;
    }
    
    /**
     * Get total unlocked achievements from both systems
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTotalUnlockedCount() {
        return null;
    }
    
    /**
     * Check if user has any achievements unlocked (either system)
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> hasAnyAchievements() {
        return null;
    }
    
    /**
     * Get recent achievements from both systems (combined)
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.bhashasetu.app.data.repository.AchievementRepository.RecentAchievementsResult> getRecentAchievements(int limit) {
        return null;
    }
    
    /**
     * Clear all achievement data (both systems) - for testing only
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAllAchievements(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Reset all achievement progress (both systems) - for testing only
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object resetAllProgress(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getAllAchievements() {
        return null;
    }
    
    /**
     * Maintain backward compatibility with your existing code
     * These methods work with the modern system but keep the same interface
     */
    @java.lang.Deprecated()
    public static void getAllAchievements$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getUnlockedAchievements() {
        return null;
    }
    
    @java.lang.Deprecated()
    public static void getUnlockedAchievements$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public final androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Achievement>> getLockedAchievements() {
        return null;
    }
    
    @java.lang.Deprecated()
    public static void getLockedAchievements$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getUnlockedAchievementCount() {
        return null;
    }
    
    @java.lang.Deprecated()
    public static void getUnlockedAchievementCount$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Deprecated()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Deprecated()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Deprecated()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.Achievement> getAchievementById(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unlockAchievement(@org.jetbrains.annotations.NotNull()
    java.lang.String achievementId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Result class for combined recent achievements
     */
    @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J)\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lcom/bhashasetu/app/data/repository/AchievementRepository$RecentAchievementsResult;", "", "legacyAchievements", "", "Lcom/bhashasetu/app/model/Achievement;", "modernAchievements", "Lcom/bhashasetu/app/data/model/Achievement;", "<init>", "(Ljava/util/List;Ljava/util/List;)V", "getLegacyAchievements", "()Ljava/util/List;", "getModernAchievements", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class RecentAchievementsResult {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bhashasetu.app.model.Achievement> legacyAchievements = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bhashasetu.app.data.model.Achievement> modernAchievements = null;
        
        public RecentAchievementsResult(@org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.bhashasetu.app.model.Achievement> legacyAchievements, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bhashasetu.app.data.model.Achievement> modernAchievements) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bhashasetu.app.model.Achievement> getLegacyAchievements() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bhashasetu.app.data.model.Achievement> getModernAchievements() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bhashasetu.app.model.Achievement> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bhashasetu.app.data.model.Achievement> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bhashasetu.app.data.repository.AchievementRepository.RecentAchievementsResult copy(@org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.bhashasetu.app.model.Achievement> legacyAchievements, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bhashasetu.app.data.model.Achievement> modernAchievements) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}