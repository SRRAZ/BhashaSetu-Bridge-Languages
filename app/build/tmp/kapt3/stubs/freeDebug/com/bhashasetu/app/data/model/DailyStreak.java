package com.bhashasetu.app.data.model;

/**
 * Entity representing the user's daily streak for continuous learning.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\'\b\u0087\b\u0018\u00002\u00020\u0001B\u008b\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0013J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0011H\u00c6\u0003J\t\u0010)\u001a\u00020\u0011H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u0010.\u001a\u00020\nH\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0094\u0001\u00102\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011H\u00c6\u0001\u00a2\u0006\u0002\u00103J\u0013\u00104\u001a\u00020\n2\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u00020\u0003H\u00d6\u0001J\t\u00107\u001a\u00020\u0011H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0017R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\r\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001b\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0015R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001f\u00a8\u00068"}, d2 = {"Lcom/bhashasetu/app/data/model/DailyStreak;", "", "id", "", "currentStreak", "longestStreak", "totalDaysActive", "lastActiveDate", "", "isTodayCompleted", "", "streakFreezeAvailable", "streakFreezeUsed", "lastStreakFreezeDate", "nextMilestone", "milestoneReached", "weeklyActivity", "", "monthlyActivity", "(IIIILjava/lang/Long;ZIILjava/lang/Long;IILjava/lang/String;Ljava/lang/String;)V", "getCurrentStreak", "()I", "getId", "()Z", "getLastActiveDate", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getLastStreakFreezeDate", "getLongestStreak", "getMilestoneReached", "getMonthlyActivity", "()Ljava/lang/String;", "getNextMilestone", "getStreakFreezeAvailable", "getStreakFreezeUsed", "getTotalDaysActive", "getWeeklyActivity", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(IIIILjava/lang/Long;ZIILjava/lang/Long;IILjava/lang/String;Ljava/lang/String;)Lcom/bhashasetu/app/data/model/DailyStreak;", "equals", "other", "hashCode", "toString", "app_freeDebug"})
@androidx.room.Entity(tableName = "daily_streak")
public final class DailyStreak {
    @androidx.room.PrimaryKey()
    private final int id = 0;
    private final int currentStreak = 0;
    private final int longestStreak = 0;
    private final int totalDaysActive = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastActiveDate = null;
    private final boolean isTodayCompleted = false;
    private final int streakFreezeAvailable = 0;
    private final int streakFreezeUsed = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastStreakFreezeDate = null;
    private final int nextMilestone = 0;
    private final int milestoneReached = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String weeklyActivity = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String monthlyActivity = null;
    
    public DailyStreak(int id, int currentStreak, int longestStreak, int totalDaysActive, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastActiveDate, boolean isTodayCompleted, int streakFreezeAvailable, int streakFreezeUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastStreakFreezeDate, int nextMilestone, int milestoneReached, @org.jetbrains.annotations.NotNull()
    java.lang.String weeklyActivity, @org.jetbrains.annotations.NotNull()
    java.lang.String monthlyActivity) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    public final int getCurrentStreak() {
        return 0;
    }
    
    public final int getLongestStreak() {
        return 0;
    }
    
    public final int getTotalDaysActive() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastActiveDate() {
        return null;
    }
    
    public final boolean isTodayCompleted() {
        return false;
    }
    
    public final int getStreakFreezeAvailable() {
        return 0;
    }
    
    public final int getStreakFreezeUsed() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastStreakFreezeDate() {
        return null;
    }
    
    public final int getNextMilestone() {
        return 0;
    }
    
    public final int getMilestoneReached() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWeeklyActivity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMonthlyActivity() {
        return null;
    }
    
    public DailyStreak() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.DailyStreak copy(int id, int currentStreak, int longestStreak, int totalDaysActive, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastActiveDate, boolean isTodayCompleted, int streakFreezeAvailable, int streakFreezeUsed, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastStreakFreezeDate, int nextMilestone, int milestoneReached, @org.jetbrains.annotations.NotNull()
    java.lang.String weeklyActivity, @org.jetbrains.annotations.NotNull()
    java.lang.String monthlyActivity) {
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