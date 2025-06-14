package com.bhashasetu.app.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\bH\'J\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\bH\'J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\bH\'J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\u0010\u001a\u00020\u0011H\'J\u0016\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001b\u00a8\u0006\u001c"}, d2 = {"Lcom/bhashasetu/app/data/dao/UserGoalDao;", "", "delete", "", "userGoal", "Lcom/bhashasetu/app/data/model/UserGoal;", "(Lcom/bhashasetu/app/data/model/UserGoal;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveGoalByType", "Lkotlinx/coroutines/flow/Flow;", "goalType", "", "getActiveUserGoals", "", "getAllUserGoals", "getCompletedUserGoals", "getUserGoalById", "id", "", "insert", "markGoalAsCompleted", "goalId", "timestamp", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateGoalProgress", "progress", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_freeDebug"})
@androidx.room.Dao()
public abstract interface UserGoalDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserGoal userGoal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserGoal userGoal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserGoal userGoal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM user_goals ORDER BY targetDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> getAllUserGoals();
    
    @androidx.room.Query(value = "SELECT * FROM user_goals WHERE id = :id")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.UserGoal> getUserGoalById(long id);
    
    @androidx.room.Query(value = "SELECT * FROM user_goals WHERE completed = 0 ORDER BY targetDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> getActiveUserGoals();
    
    @androidx.room.Query(value = "SELECT * FROM user_goals WHERE completed = 1 ORDER BY completedDate DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserGoal>> getCompletedUserGoals();
    
    @androidx.room.Query(value = "UPDATE user_goals SET completed = 1, completedDate = :timestamp, progress = targetValue WHERE id = :goalId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markGoalAsCompleted(long goalId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE user_goals SET progress = :progress WHERE id = :goalId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateGoalProgress(long goalId, int progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM user_goals WHERE goalType = :goalType AND completed = 0 LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.UserGoal> getActiveGoalByType(@org.jetbrains.annotations.NotNull()
    java.lang.String goalType);
}