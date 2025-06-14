package com.bhashasetu.app.data.repository;

/**
 * Implementation of WordRepository using Kotlin Flow for reactive data streams
 */
@error.NonExistentClass()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000bH\u0016J\u001c\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000bH\u0016J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000b2\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\f0\u000bH\u0016J\u000e\u0010\u0017\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000bH\u0016J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000b2\u0006\u0010\u001f\u001a\u00020\u000eH\u0016J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010#J\u001e\u0010%\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u00122\u0006\u0010&\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\'J\u0016\u0010(\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/bhashasetu/app/data/repository/WordRepositoryImpl;", "Lcom/bhashasetu/app/data/repository/WordRepository;", "wordDao", "Lcom/bhashasetu/app/data/dao/WordDao;", "(Lcom/bhashasetu/app/data/dao/WordDao;)V", "deleteWord", "", "word", "Lcom/bhashasetu/app/data/model/Word;", "(Lcom/bhashasetu/app/data/model/Word;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllWords", "Lkotlinx/coroutines/flow/Flow;", "", "category", "", "getFavoriteWords", "getLeastMasteredWords", "limit", "", "getRecentlyAdded", "getWordById", "id", "getWordCategories", "getWordCount", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordCountByCategory", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordsDueForReview", "insertWord", "", "searchWords", "query", "toggleFavorite", "error/NonExistentClass", "wordId", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLastPracticed", "updateMasteryLevel", "newLevel", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateWord", "app_freeDebug"})
public final class WordRepositoryImpl extends com.bhashasetu.app.data.repository.WordRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.WordDao wordDao = null;
    
    @error.NonExistentClass()
    public WordRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.WordDao wordDao) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getAllWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getAllWords(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getFavoriteWords() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> searchWords(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getRecentlyAdded(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Word> getWordById(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getLeastMasteredWords(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsDueForReview() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertWord(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateWord(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteWord(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateMasteryLevel(int wordId, int newLevel, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object toggleFavorite(int wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super error.NonExistentClass> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateLastPracticed(int wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getWordCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getWordCountByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getWordCategories() {
        return null;
    }
}