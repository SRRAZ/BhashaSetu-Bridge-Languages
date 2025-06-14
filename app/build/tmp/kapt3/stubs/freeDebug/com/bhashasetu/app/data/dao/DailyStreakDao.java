package com.bhashasetu.app.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003H\'J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\'J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/bhashasetu/app/data/dao/DailyStreakDao;", "", "getDailyStreak", "Lkotlinx/coroutines/flow/Flow;", "Lcom/bhashasetu/app/data/model/DailyStreak;", "hasCheckedInToday", "", "insert", "", "dailyStreak", "(Lcom/bhashasetu/app/data/model/DailyStreak;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetStreak", "checkInDate", "Ljava/util/Date;", "(Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateLongestStreak", "longestStreak", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateStreak", "newStreak", "(ILjava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_freeDebug"})
@androidx.room.Dao()
public abstract interface DailyStreakDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.DailyStreak dailyStreak, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.DailyStreak dailyStreak, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM daily_streaks WHERE id = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.DailyStreak> getDailyStreak();
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET currentStreak = :newStreak, lastCheckInDate = :checkInDate WHERE id = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateStreak(int newStreak, @org.jetbrains.annotations.NotNull()
    java.util.Date checkInDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET longestStreak = :longestStreak WHERE id = 1 AND longestStreak < :longestStreak")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLongestStreak(int longestStreak, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET currentStreak = 1, lastCheckInDate = :checkInDate WHERE id = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object resetStreak(@org.jetbrains.annotations.NotNull()
    java.util.Date checkInDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM daily_streaks WHERE id = 1 AND date(lastCheckInDate/1000, \'unixepoch\') = date(\'now\'))")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasCheckedInToday();
}