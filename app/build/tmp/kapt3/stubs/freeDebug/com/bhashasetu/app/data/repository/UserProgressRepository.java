package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0012\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000bJ\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\f0\u000bJ\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u000bJ\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b2\u0006\u0010\u0013\u001a\u00020\u0012J\u001a\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000b2\u0006\u0010\u0015\u001a\u00020\u0012J\u0016\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000b2\u0006\u0010\u0017\u001a\u00020\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b2\u0006\u0010\u0017\u001a\u00020\u0018J\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000b2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010 \u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010!\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\"\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010#\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010%J\u001e\u0010&\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\'\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010(J\u001e\u0010)\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010%J\u001e\u0010+\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010,\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010(J\u001e\u0010-\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010.\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010(R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/bhashasetu/app/data/repository/UserProgressRepository;", "", "userProgressDao", "Lcom/bhashasetu/app/data/dao/UserProgressDao;", "(Lcom/bhashasetu/app/data/dao/UserProgressDao;)V", "delete", "", "userProgress", "Lcom/bhashasetu/app/data/model/UserProgress;", "(Lcom/bhashasetu/app/data/model/UserProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllUserProgress", "Lkotlinx/coroutines/flow/Flow;", "", "getAllWordsWithProgress", "Lcom/bhashasetu/app/data/relation/WordWithProgress;", "getAverageProficiency", "", "getLearnedWordsCount", "", "minProficiency", "getMostDifficultWords", "limit", "getUserProgressByWordId", "wordId", "", "getWordWithProgress", "getWordsForReview", "currentDate", "Ljava/util/Date;", "incrementCorrectAnswers", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementExposureCount", "incrementIncorrectAnswers", "insert", "update", "updateLastReviewed", "lastReviewed", "(JLjava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMemorizationStatus", "memorizationStatus", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateNextReviewDate", "nextReviewDate", "updateProficiencyLevel", "proficiencyLevel", "updateQuizScore", "score", "app_freeDebug"})
public final class UserProgressRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.UserProgressDao userProgressDao = null;
    
    public UserProgressRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.UserProgressDao userProgressDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserProgress>> getAllUserProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserProgress userProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserProgress userProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserProgress userProgress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.UserProgress> getUserProgressByWordId(long wordId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.WordWithProgress> getWordWithProgress(long wordId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.WordWithProgress>> getAllWordsWithProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object incrementCorrectAnswers(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object incrementIncorrectAnswers(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateProficiencyLevel(long wordId, int proficiencyLevel, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLastReviewed(long wordId, @org.jetbrains.annotations.NotNull()
    java.util.Date lastReviewed, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateNextReviewDate(long wordId, @org.jetbrains.annotations.NotNull()
    java.util.Date nextReviewDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object incrementExposureCount(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateMemorizationStatus(long wordId, int memorizationStatus, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateQuizScore(long wordId, int score, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserProgress>> getWordsForReview(@org.jetbrains.annotations.NotNull()
    java.util.Date currentDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getLearnedWordsCount(int minProficiency) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserProgress>> getMostDifficultWords(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Float> getAverageProficiency() {
        return null;
    }
}