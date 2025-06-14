package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00070\u0006J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0014\u001a\u00020\u0015J\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0017\u001a\u00020\u0015J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u0019\u001a\u00020\u001aJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u00062\u0006\u0010\u001d\u001a\u00020\u0015J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00120\u00062\u0006\u0010\u001f\u001a\u00020\u001aJ\"\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"J\u001a\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010%\u001a\u00020\u001aJ\u001a\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u001d\u001a\u00020\u0015J\u0016\u0010\'\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001a\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010)\u001a\u00020*J\u001e\u0010+\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020-H\u0086@\u00a2\u0006\u0002\u0010.J\u0016\u0010/\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/bhashasetu/app/data/repository/WordRepository;", "", "wordDao", "Lcom/bhashasetu/app/data/dao/WordDao;", "(Lcom/bhashasetu/app/data/dao/WordDao;)V", "allWords", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bhashasetu/app/data/model/Word;", "getAllWords", "()Lkotlinx/coroutines/flow/Flow;", "favoriteWords", "getFavoriteWords", "delete", "", "word", "(Lcom/bhashasetu/app/data/model/Word;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllWordsWithCategories", "Lcom/bhashasetu/app/data/relation/WordWithCategories;", "getRandomWords", "count", "", "getRecentlyAddedWords", "limit", "getWordById", "id", "", "getWordCount", "getWordCountByDifficulty", "difficulty", "getWordWithCategories", "wordId", "getWordsAddedBetween", "startDate", "Ljava/util/Date;", "endDate", "getWordsByCategoryId", "categoryId", "getWordsByDifficulty", "insert", "searchWords", "query", "", "toggleFavorite", "isFavorite", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_freeDebug"})
public final class WordRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.WordDao wordDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> allWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> favoriteWords = null;
    
    public WordRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.WordDao wordDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getAllWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getFavoriteWords() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Word> getWordById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsByDifficulty(int difficulty) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> searchWords(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.WordWithCategories> getWordWithCategories(long wordId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.WordWithCategories>> getAllWordsWithCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object toggleFavorite(long wordId, boolean isFavorite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getRecentlyAddedWords(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsByCategoryId(long categoryId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getWordsAddedBetween(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getWordCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getWordCountByDifficulty(int difficulty) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Word>> getRandomWords(int count) {
        return null;
    }
}