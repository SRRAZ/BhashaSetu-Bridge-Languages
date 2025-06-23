package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010\u001b\u001a\u00020\u0014J\"\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u001c\u0010 \u001a\b\u0012\u0004\u0012\u00020\r0\u00072\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000b\u00a8\u0006!"}, d2 = {"Lcom/bhashasetu/app/data/repository/StudySessionRepository;", "", "studySessionDao", "Lcom/bhashasetu/app/data/dao/StudySessionDao;", "<init>", "(Lcom/bhashasetu/app/data/dao/StudySessionDao;)V", "allStudySessions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bhashasetu/app/data/model/StudySession;", "getAllStudySessions", "()Lkotlinx/coroutines/flow/Flow;", "totalStudyTime", "", "getTotalStudyTime", "todayStudySessionCount", "getTodayStudySessionCount", "todayStudyTime", "getTodayStudyTime", "insert", "", "studySession", "(Lcom/bhashasetu/app/data/model/StudySession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "getStudySessionById", "id", "getStudySessionsByDateRange", "startDate", "Ljava/util/Date;", "endDate", "getStudyTimeInRange", "app_debug"})
public final class StudySessionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.StudySessionDao studySessionDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.StudySession>> allStudySessions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> totalStudyTime = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> todayStudySessionCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> todayStudyTime = null;
    
    public StudySessionRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.StudySessionDao studySessionDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.StudySession>> getAllStudySessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalStudyTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTodayStudySessionCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTodayStudyTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.StudySession studySession, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.StudySession studySession, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.StudySession studySession, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.StudySession> getStudySessionById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.StudySession>> getStudySessionsByDateRange(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getStudyTimeInRange(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate) {
        return null;
    }
}