package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for Word entity using Kotlin Flow
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000bH\'J\u0018\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\'J\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\'J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000bH\'J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\u0006\u0010\u0017\u001a\u00020\u0014H\'J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\u0006\u0010\u0019\u001a\u00020\u000fH\'J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\u0006\u0010\u0019\u001a\u00020\u000fH\'J\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\u0006\u0010\u001c\u001a\u00020\u001dH\'J\u000e\u0010\u001e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010!J\u001e\u0010\"\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010%J\u001e\u0010&\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020(H\u00a7@\u00a2\u0006\u0002\u0010)J\u001e\u0010*\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010,J\u0014\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f0\u000bH\'J\u0018\u0010.\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010/0\u000b2\u0006\u0010#\u001a\u00020\u000fH\'\u00a8\u00060"}, d2 = {"Lcom/bhashasetu/app/data/dao/WordDaoFlow;", "", "insert", "", "word", "Lcom/bhashasetu/app/data/model/Word;", "(Lcom/bhashasetu/app/data/model/Word;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "getAllWords", "Lkotlinx/coroutines/flow/Flow;", "", "getWordById", "id", "", "getWordByIdSync", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordsByCategory", "category", "", "getFavoriteWords", "searchWords", "query", "getRecentlyAdded", "limit", "getLeastMasteredWords", "getWordsDueForReview", "currentDate", "Ljava/util/Date;", "getWordCount", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordCountByCategory", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMasteryLevel", "wordId", "level", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFavoriteStatus", "isFavorite", "", "(IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLastPracticed", "date", "(ILjava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategories", "getWordWithCategories", "Lcom/bhashasetu/app/data/relation/WordWithCategories;", "app_debug"})
@androidx.room.Dao()
public abstract interface WordDaoFlow {
    
    /**
     * Insert a new word
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Update an existing word
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Delete a word
     */
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get all words
     */
    @androidx.room.Query(value = "SELECT * FROM words ORDER BY englishWord")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getAllWords();
    
    /**
     * Get word by ID
     */
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :id")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Word> getWordById(int id);
    
    /**
     * Get word by ID (synchronous for internal use)
     */
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordByIdSync(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.Word> $completion);
    
    /**
     * Get words by category
     */
    @androidx.room.Query(value = "SELECT * FROM words WHERE category = :category ORDER BY englishWord")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    /**
     * Get favorite words
     */
    @androidx.room.Query(value = "SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishWord")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getFavoriteWords();
    
    /**
     * Search words by English or Hindi text
     */
    @androidx.room.Query(value = "SELECT * FROM words WHERE englishWord LIKE :query OR hindiWord LIKE :query ORDER BY englishWord")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> searchWords(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    /**
     * Get recently added words
     */
    @androidx.room.Query(value = "SELECT * FROM words ORDER BY timeAdded DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getRecentlyAdded(int limit);
    
    /**
     * Get least mastered words
     */
    @androidx.room.Query(value = "SELECT * FROM words ORDER BY masteryLevel ASC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getLeastMasteredWords(int limit);
    
    /**
     * Get words that are due for review
     */
    @androidx.room.Query(value = "SELECT * FROM words WHERE lastPracticed IS NULL OR lastPracticed < :currentDate")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsDueForReview(@org.jetbrains.annotations.NotNull()
    java.util.Date currentDate);
    
    /**
     * Get total word count
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM words")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Get word count by category
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM words WHERE category = :category")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWordCountByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Update mastery level
     */
    @androidx.room.Query(value = "UPDATE words SET masteryLevel = :level WHERE id = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMasteryLevel(int wordId, int level, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Update favorite status
     */
    @androidx.room.Query(value = "UPDATE words SET isFavorite = :isFavorite WHERE id = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateFavoriteStatus(int wordId, boolean isFavorite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Update last practiced timestamp
     */
    @androidx.room.Query(value = "UPDATE words SET lastPracticed = :date WHERE id = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLastPracticed(int wordId, @org.jetbrains.annotations.NotNull()
    java.util.Date date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get all word categories
     */
    @androidx.room.Query(value = "SELECT DISTINCT category FROM words ORDER BY category")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getCategories();
    
    /**
     * Get word with its categories (relation)
     */
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM words WHERE id = :wordId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.WordWithCategories> getWordWithCategories(int wordId);
}