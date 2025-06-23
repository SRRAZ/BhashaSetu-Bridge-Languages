package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the UserProgress entity.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0010\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00132\u0006\u0010\u000e\u001a\u00020\u000fH\'J\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\'J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\u0015H\'J\u0014\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\t0\u0015H\'J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\t2\u0006\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00180\t2\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ,\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00180\t2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\"J^\u0010#\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001b2\u0006\u0010\'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010/J\u000e\u00100\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015H\'J\u000e\u00101\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015H\'J\u000e\u00102\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015H\'J\u000e\u00103\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015H\'J\u0010\u00104\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0015H\'J\u0010\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010)0\u0015H\'J \u00106\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00152\u0006\u0010!\u001a\u00020\u000f2\b\b\u0002\u00107\u001a\u00020\u001bH\'J\u0016\u00108\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00152\u0006\u0010!\u001a\u00020\u000fH\'\u00a8\u00069"}, d2 = {"Lcom/bhashasetu/app/data/dao/UserProgressDao;", "", "insert", "", "progress", "Lcom/bhashasetu/app/data/model/UserProgress;", "(Lcom/bhashasetu/app/data/model/UserProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "progressList", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "delete", "deleteProgressForWord", "wordId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProgressForWord", "getProgressForWordLiveData", "Landroidx/lifecycle/LiveData;", "getProgressForWordFlow", "Lkotlinx/coroutines/flow/Flow;", "getAllProgress", "getWordsWithProgressByProficiency", "Lcom/bhashasetu/app/data/relation/WordWithProgress;", "getRecentlyStudiedWords", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordsForReview", "currentTimeMs", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordsForReviewByCategory", "categoryId", "(JJILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProgressAfterReview", "isCorrect", "", "proficiencyLevel", "confidenceRating", "newEaseFactor", "", "newIntervalDays", "newRepetitions", "attemptTimeMs", "nextReviewDueMs", "timeSpentMs", "(JZIIFIIJJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalProgressCount", "getMasteredWordCount", "getLearningWordCount", "getStartedWordCount", "getTotalStudyTime", "getAverageAccuracy", "getMasteredWordCountByCategory", "targetProficiency", "getTotalWordCountByCategory", "app_debug"})
@androidx.room.Dao()
public abstract interface UserProgressDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserProgress progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.UserProgress> progressList, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserProgress progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.UserProgress progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM user_progress WHERE wordId = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteProgressForWord(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM user_progress WHERE wordId = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProgressForWord(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.UserProgress> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM user_progress WHERE wordId = :wordId")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.UserProgress> getProgressForWordLiveData(long wordId);
    
    @androidx.room.Query(value = "SELECT * FROM user_progress WHERE wordId = :wordId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.UserProgress> getProgressForWordFlow(long wordId);
    
    @androidx.room.Query(value = "SELECT * FROM user_progress ORDER BY lastAttemptAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.UserProgress>> getAllProgress();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT w.* FROM words w\n        INNER JOIN user_progress up ON w.id = up.wordId\n        ORDER BY up.proficiencyLevel ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.WordWithProgress>> getWordsWithProgressByProficiency();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT w.* FROM words w\n        INNER JOIN user_progress up ON w.id = up.wordId\n        WHERE up.proficiencyLevel < 100\n        ORDER BY up.lastAttemptAt DESC\n        LIMIT :limit\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecentlyStudiedWords(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.relation.WordWithProgress>> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT w.* FROM words w\n        INNER JOIN user_progress up ON w.id = up.wordId\n        WHERE up.nextReviewDue <= :currentTimeMs\n        ORDER BY up.nextReviewDue ASC\n        LIMIT :limit\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordsForReview(long currentTimeMs, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.relation.WordWithProgress>> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT w.* FROM words w\n        INNER JOIN user_progress up ON w.id = up.wordId\n        INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId\n        WHERE wc.categoryId = :categoryId AND up.nextReviewDue <= :currentTimeMs\n        ORDER BY up.nextReviewDue ASC\n        LIMIT :limit\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordsForReviewByCategory(long categoryId, long currentTimeMs, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.relation.WordWithProgress>> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE user_progress\n        SET \n            proficiencyLevel = :proficiencyLevel,\n            correctAttempts = correctAttempts + CASE WHEN :isCorrect THEN 1 ELSE 0 END,\n            totalAttempts = totalAttempts + 1,\n            lastAttemptAt = :attemptTimeMs,\n            lastCorrectAt = CASE WHEN :isCorrect THEN :attemptTimeMs ELSE lastCorrectAt END,\n            lastConfidenceRating = :confidenceRating,\n            easeFactor = :newEaseFactor,\n            intervalDays = :newIntervalDays,\n            repetitions = :newRepetitions,\n            nextReviewDue = :nextReviewDueMs,\n            timeSpentMs = timeSpentMs + :timeSpentMs\n        WHERE wordId = :wordId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProgressAfterReview(long wordId, boolean isCorrect, int proficiencyLevel, int confidenceRating, float newEaseFactor, int newIntervalDays, int newRepetitions, long attemptTimeMs, long nextReviewDueMs, long timeSpentMs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM user_progress")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalProgressCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM user_progress WHERE proficiencyLevel >= 85")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getMasteredWordCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM user_progress WHERE proficiencyLevel > 0 AND proficiencyLevel < 85")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getLearningWordCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM user_progress WHERE totalAttempts > 0")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getStartedWordCount();
    
    @androidx.room.Query(value = "SELECT SUM(timeSpentMs) FROM user_progress")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Long> getTotalStudyTime();
    
    @androidx.room.Query(value = "\n        SELECT AVG(CAST(correctAttempts AS FLOAT) / CAST(totalAttempts AS FLOAT) * 100)\n        FROM user_progress\n        WHERE totalAttempts > 0\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Float> getAverageAccuracy();
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM user_progress\n        WHERE proficiencyLevel >= :targetProficiency\n        AND wordId IN (\n            SELECT wordId FROM word_category_cross_refs\n            WHERE categoryId = :categoryId\n        )\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getMasteredWordCountByCategory(long categoryId, int targetProficiency);
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM word_category_cross_refs\n        WHERE categoryId = :categoryId\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalWordCountByCategory(long categoryId);
    
    /**
     * Data Access Object for the UserProgress entity.
     */
    @kotlin.Metadata(mv = {2, 1, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}