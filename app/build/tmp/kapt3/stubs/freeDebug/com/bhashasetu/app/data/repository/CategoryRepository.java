package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0012\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00070\u0006J\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0017\u001a\u00020\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u001a\u001a\u00020\u000eJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00180\u0006J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u00062\u0006\u0010\u000f\u001a\u00020\u000eJ\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00180\u00062\u0006\u0010\u000f\u001a\u00020\u000eJ\u0016\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u001f\u001a\u00020\f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0086@\u00a2\u0006\u0002\u0010!J\u001e\u0010\"\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001a\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010$\u001a\u00020%J\u0016\u0010&\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/bhashasetu/app/data/repository/CategoryRepository;", "", "categoryDao", "Lcom/bhashasetu/app/data/dao/CategoryDao;", "(Lcom/bhashasetu/app/data/dao/CategoryDao;)V", "allCategories", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bhashasetu/app/data/model/Category;", "getAllCategories", "()Lkotlinx/coroutines/flow/Flow;", "addWordToCategory", "", "wordId", "", "categoryId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "category", "(Lcom/bhashasetu/app/data/model/Category;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllCategoriesWithWords", "Lcom/bhashasetu/app/data/relation/CategoryWithWords;", "getCategoriesByLevel", "level", "", "getCategoryById", "id", "getCategoryCount", "getCategoryWithWords", "getWordCountInCategory", "insert", "insertAll", "categories", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeWordFromCategory", "searchCategories", "query", "", "update", "app_freeDebug"})
public final class CategoryRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.CategoryDao categoryDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> allCategories = null;
    
    public CategoryRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.CategoryDao categoryDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> getAllCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Category> categories, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Category> getCategoryById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> getCategoriesByLevel(int level) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> searchCategories(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.CategoryWithWords> getCategoryWithWords(long categoryId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.CategoryWithWords>> getAllCategoriesWithWords() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addWordToCategory(long wordId, long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeWordFromCategory(long wordId, long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getCategoryCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getWordCountInCategory(long categoryId) {
        return null;
    }
}