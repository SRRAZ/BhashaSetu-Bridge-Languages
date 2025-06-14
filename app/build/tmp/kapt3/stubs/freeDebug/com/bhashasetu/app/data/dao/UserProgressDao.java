package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the UserProgress entity.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\n\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0010\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\fH\'J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\'J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\'J \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u0014\u001a\u00020\t2\b\b\u0002\u0010\u0015\u001a\u00020\u0011H\'J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\f2\u0006\u0010\b\u001a\u00020\tH\'J\u0018\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00192\u0006\u0010\b\u001a\u00020\tH\'J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\r2\u0006\u0010\u001c\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\'J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\'J\u0010\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\fH\'J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u0014\u001a\u00020\tH\'J$\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001b0\r2\u0006\u0010#\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010$J,\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0\r2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010&J\u0014\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\r0\fH\'J\u0016\u0010(\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010)\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J^\u0010-\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00112\u0006\u00101\u001a\u00020\u00112\u0006\u00102\u001a\u00020\u000f2\u0006\u00103\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u00112\u0006\u00105\u001a\u00020\t2\u0006\u00106\u001a\u00020\t2\u0006\u00107\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u00108\u00a8\u00069"}, d2 = {"Lcom/bhashasetu/app/data/dao/UserProgressDao;", "", "delete", "", "progress", "Lcom/bhashasetu/app/data/model/UserProgress;", "(Lcom/bhashasetu/app/data/model/UserProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProgressForWord", "wordId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllProgress", "Lkotlinx/coroutines/flow/Flow;", "", "getAverageAccuracy", "", "getLearningWordCount", "", "getMasteredWordCount", "getMasteredWordCountByCategory", "categoryId", "targetProficiency", "getProgressForWord", "getProgressForWordFlow", "getProgressForWordLiveData", "Landroidx/lifecycle/LiveData;", "getRecentlyStudiedWords", "Lcom/bhashasetu/app/data/relation/WordWithProgress;", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStartedWordCount", "getTotalProgressCount", "getTotalStudyTime", "getTotalWordCountByCategory", "getWordsForReview", "currentTimeMs", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordsForReviewByCategory", "(JJILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordsWithProgressByProficiency", "insert", "insertAll", "progressList", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateProgressAfterReview", "isCorrect", "", "proficiencyLevel", "confidenceRating", "newEaseFactor", "newIntervalDays", "newRepetitions", "attemptTimeMs", "nextReviewDueMs", "timeSpentMs", "(JZIIFIIJJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_freeDebug"})
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
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}