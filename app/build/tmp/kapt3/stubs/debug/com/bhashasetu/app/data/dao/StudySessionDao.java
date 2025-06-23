package com.bhashasetu.app.data.dao;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000bH\'J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\u000e\u001a\u00020\u0003H\'J$\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\'J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\'J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\'J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\'J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\'\u00a8\u0006\u0018"}, d2 = {"Lcom/bhashasetu/app/data/dao/StudySessionDao;", "", "insert", "", "studySession", "Lcom/bhashasetu/app/data/model/StudySession;", "(Lcom/bhashasetu/app/data/model/StudySession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "getAllStudySessions", "Lkotlinx/coroutines/flow/Flow;", "", "getStudySessionById", "id", "getStudySessionsByDateRange", "startDate", "Ljava/util/Date;", "endDate", "getTotalStudyTime", "", "getStudyTimeInRange", "getStudySessionCountToday", "getTodayStudyTime", "app_debug"})
@androidx.room.Dao()
public abstract interface StudySessionDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.StudySession studySession, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.StudySession studySession, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.StudySession studySession, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_sessions ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.StudySession>> getAllStudySessions();
    
    @androidx.room.Query(value = "SELECT * FROM study_sessions WHERE id = :id")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.StudySession> getStudySessionById(long id);
    
    @androidx.room.Query(value = "SELECT * FROM study_sessions WHERE startTime >= :startDate AND startTime <= :endDate ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.StudySession>> getStudySessionsByDateRange(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate);
    
    @androidx.room.Query(value = "SELECT SUM(durationMinutes) FROM study_sessions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalStudyTime();
    
    @androidx.room.Query(value = "SELECT SUM(durationMinutes) FROM study_sessions WHERE startTime >= :startDate AND startTime <= :endDate")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getStudyTimeInRange(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM study_sessions WHERE DATE(startTime / 1000, \'unixepoch\') = DATE(\'now\')")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getStudySessionCountToday();
    
    @androidx.room.Query(value = "SELECT SUM(durationMinutes) FROM study_sessions WHERE DATE(startTime / 1000, \'unixepoch\') = DATE(\'now\')")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTodayStudyTime();
}