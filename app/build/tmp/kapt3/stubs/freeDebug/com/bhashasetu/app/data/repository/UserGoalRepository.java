package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00062\u0006\u0010\u0014\u001a\u00020\u0015J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u0017\u001a\u00020\u0018J\u0016\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001e\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010\"R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/bhashasetu/app/data/repository/UserGoalRepository;", "", "userGoalDao", "Lcom/bhashasetu/app/data/dao/UserGoalDao;", "(Lcom/bhashasetu/app/data/dao/UserGoalDao;)V", "activeUserGoals", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bhashasetu/app/data/model/UserGoal;", "getActiveUserGoals", "()Lkotlinx/coroutines/flow/Flow;", "allUserGoals", "getAllUserGoals", "completedUserGoals", "getCompletedUserGoals", "delete", "", "userGoal", "(Lcom/bhashasetu/app/data/model/UserGoal;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveGoalByType", "goalType", "", "getUserGoalById", "id", "", "insert", "markGoalAsCompleted", "goalId", "timestamp", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateGoalProgress", "progress", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_freeDebug"})
public final class UserGoalRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.UserGoalDao userGoalDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> allUserGoals = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> activeUserGoals = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> completedUserGoals = null;
    
    public UserGoalRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.UserGoalDao userGoalDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> getAllUserGoals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> getActiveUserGoals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> getCompletedUserGoals() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserGoal userGoal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserGoal userGoal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserGoal userGoal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.UserGoal> getUserGoalById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markGoalAsCompleted(long goalId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateGoalProgress(long goalId, int progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.UserGoal> getActiveGoalByType(@org.jetbrains.annotations.NotNull()
    java.lang.String goalType) {
        return null;
    }
}