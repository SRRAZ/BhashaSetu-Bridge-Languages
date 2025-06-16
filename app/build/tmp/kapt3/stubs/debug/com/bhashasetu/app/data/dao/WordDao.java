package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the Word entity.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00122\u0006\u0010\u000f\u001a\u00020\u0003H\'J\u0018\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00142\u0006\u0010\u000f\u001a\u00020\u0003H\'J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0014H\'J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0012H\'J\u0014\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0014H\'J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\'J\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\'J\u001c\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\u001f\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010 J\u0018\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u000f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\b0\u0014H\'J\u0018\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u000f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00142\u0006\u0010\'\u001a\u00020\u0003H\'J\u001c\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u00122\u0006\u0010\'\u001a\u00020\u0003H\'J$\u0010)\u001a\b\u0012\u0004\u0012\u00020%0\b2\u0006\u0010*\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010+J\u001c\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\u001f\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010 J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0014H\'J\u000e\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0014H\'J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0014H\'J\u000e\u00100\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0014H\'J\u0016\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u00104J\u0016\u00105\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00142\u0006\u0010\'\u001a\u00020\u0003H\'\u00a8\u00066"}, d2 = {"Lcom/bhashasetu/app/data/dao/WordDao;", "", "insert", "", "word", "Lcom/bhashasetu/app/data/model/Word;", "(Lcom/bhashasetu/app/data/model/Word;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "", "words", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "getWordById", "wordId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordByIdLiveData", "Landroidx/lifecycle/LiveData;", "getWordByIdFlow", "Lkotlinx/coroutines/flow/Flow;", "getAllWords", "getAllWordsLiveData", "getFavoriteWords", "searchWords", "query", "", "getWordsByDifficulty", "level", "", "getRandomWords", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordWithCategories", "Lcom/bhashasetu/app/data/relation/WordWithCategories;", "getAllWordsWithCategories", "getWordWithProgress", "Lcom/bhashasetu/app/data/relation/WordWithProgress;", "getWordsByCategory", "categoryId", "getWordsByCategoryLiveData", "getWordsForReview", "currentTimeMillis", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNewWordsForLearning", "getTotalWordCount", "getFavoriteWordCount", "getMasteredWordCount", "getStartedWordCount", "wordExistsByEnglishText", "", "englishText", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordCountByCategory", "app_debug"})
@androidx.room.Dao()
public abstract interface WordDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Word> words, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordById(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.Word> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :wordId")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.Word> getWordByIdLiveData(long wordId);
    
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :wordId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Word> getWordByIdFlow(long wordId);
    
    @androidx.room.Query(value = "SELECT * FROM words ORDER BY englishText ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getAllWords();
    
    @androidx.room.Query(value = "SELECT * FROM words ORDER BY englishText ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Word>> getAllWordsLiveData();
    
    @androidx.room.Query(value = "SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishText ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getFavoriteWords();
    
    @androidx.room.Query(value = "SELECT * FROM words WHERE englishText LIKE \'%\' || :query || \'%\' OR hindiText LIKE \'%\' || :query || \'%\' ORDER BY englishText ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> searchWords(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "SELECT * FROM words WHERE difficulty = :level ORDER BY englishText ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsByDifficulty(int level);
    
    @androidx.room.Query(value = "SELECT * FROM words ORDER BY RANDOM() LIMIT :limit")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRandomWords(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.model.Word>> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordWithCategories(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.relation.WordWithCategories> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM words")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.WordWithCategories>> getAllWordsWithCategories();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordWithProgress(long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.relation.WordWithProgress> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT w.* FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId ORDER BY w.englishText ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsByCategory(long categoryId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT w.* FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId ORDER BY w.englishText ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsByCategoryLiveData(long categoryId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT w.* FROM words w\n        INNER JOIN user_progress up ON w.id = up.wordId\n        WHERE up.nextReviewDue <= :currentTimeMillis\n        ORDER BY up.nextReviewDue ASC\n        LIMIT :limit\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordsForReview(long currentTimeMillis, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.relation.WordWithProgress>> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT w.* FROM words w\n        LEFT JOIN user_progress up ON w.id = up.wordId\n        WHERE up.wordId IS NULL\n        ORDER BY RANDOM()\n        LIMIT :limit\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNewWordsForLearning(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.model.Word>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM words")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalWordCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM words WHERE isFavorite = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getFavoriteWordCount();
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM words w\n        INNER JOIN user_progress up ON w.id = up.wordId\n        WHERE up.proficiencyLevel >= 85\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getMasteredWordCount();
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM words w\n        INNER JOIN user_progress up ON w.id = up.wordId\n        WHERE up.totalAttempts > 0\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getStartedWordCount();
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM words WHERE englishText = :englishText LIMIT 1)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object wordExistsByEnglishText(@org.jetbrains.annotations.NotNull()
    java.lang.String englishText, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getWordCountByCategory(long categoryId);
}