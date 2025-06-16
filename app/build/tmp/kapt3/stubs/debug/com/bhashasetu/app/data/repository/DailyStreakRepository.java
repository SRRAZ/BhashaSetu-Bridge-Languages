package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n\u00a8\u0006\u001d"}, d2 = {"Lcom/bhashasetu/app/data/repository/DailyStreakRepository;", "", "dailyStreakDao", "Lcom/bhashasetu/app/data/dao/DailyStreakDao;", "<init>", "(Lcom/bhashasetu/app/data/dao/DailyStreakDao;)V", "dailyStreak", "Lkotlinx/coroutines/flow/Flow;", "Lcom/bhashasetu/app/data/model/DailyStreak;", "getDailyStreak", "()Lkotlinx/coroutines/flow/Flow;", "hasCheckedInToday", "", "getHasCheckedInToday", "insert", "", "(Lcom/bhashasetu/app/data/model/DailyStreak;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateStreak", "newStreak", "", "checkInDate", "Ljava/util/Date;", "(ILjava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLongestStreak", "longestStreak", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetStreak", "(Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DailyStreakRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.DailyStreakDao dailyStreakDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.DailyStreak> dailyStreak = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasCheckedInToday = null;
    
    public DailyStreakRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.DailyStreakDao dailyStreakDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.DailyStreak> getDailyStreak() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHasCheckedInToday() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.DailyStreak dailyStreak, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.DailyStreak dailyStreak, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateStreak(int newStreak, @org.jetbrains.annotations.NotNull()
    java.util.Date checkInDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLongestStreak(int longestStreak, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object resetStreak(@org.jetbrains.annotations.NotNull()
    java.util.Date checkInDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}