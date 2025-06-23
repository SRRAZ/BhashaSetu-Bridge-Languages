package com.bhashasetu.app.data.dao;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\tH\'J\u001e\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\tH\'\u00a8\u0006\u0017"}, d2 = {"Lcom/bhashasetu/app/data/dao/DailyStreakDao;", "", "insert", "", "dailyStreak", "Lcom/bhashasetu/app/data/model/DailyStreak;", "(Lcom/bhashasetu/app/data/model/DailyStreak;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "getDailyStreak", "Lkotlinx/coroutines/flow/Flow;", "updateStreak", "newStreak", "", "checkInDate", "Ljava/util/Date;", "(ILjava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLongestStreak", "longestStreak", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetStreak", "(Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasCheckedInToday", "", "app_debug"})
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
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET currentStreak = :newStreak, lastActiveDate = :checkInDate WHERE id = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateStreak(int newStreak, @org.jetbrains.annotations.NotNull()
    java.util.Date checkInDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET longestStreak = :longestStreak WHERE id = 1 AND longestStreak < :longestStreak")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLongestStreak(int longestStreak, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET currentStreak = 1, lastActiveDate = :checkInDate WHERE id = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object resetStreak(@org.jetbrains.annotations.NotNull()
    java.util.Date checkInDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM daily_streaks WHERE id = 1 AND date(lastActiveDate/1000, \'unixepoch\') = date(\'now\'))")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasCheckedInToday();
}