package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the Category entity.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\u00020\f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0015\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0018\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u001d2\u0006\u0010\u0015\u001a\u00020\u0003H\'J\u0018\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u001f2\u0006\u0010\u0015\u001a\u00020\u0003H\'J\u0014\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001fH\'J\u0014\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001dH\'J\u0014\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001fH\'J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001f2\u0006\u0010$\u001a\u00020%H\'J\u0018\u0010&\u001a\u0004\u0018\u00010\'2\u0006\u0010\u0015\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0018\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\'0\u001f2\u0006\u0010\u0015\u001a\u00020\u0003H\'J\u0014\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\b0\u001fH\'J\u001c\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001f2\u0006\u0010\u0014\u001a\u00020\u0003H\'J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u001fH\'J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020,0\u001fH\'J\u000e\u0010.\u001a\b\u0012\u0004\u0012\u00020,0\u001fH\'J\u0016\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020%H\u00a7@\u00a2\u0006\u0002\u00102J\u000e\u00103\u001a\u00020,H\u00a7@\u00a2\u0006\u0002\u00104J\u001e\u00105\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u00106\u001a\u00020,H\u00a7@\u00a2\u0006\u0002\u00107J\u001e\u00108\u001a\u0002002\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0016\u00a8\u00069"}, d2 = {"Lcom/bhashasetu/app/data/dao/CategoryDao;", "", "insert", "", "category", "Lcom/bhashasetu/app/data/model/Category;", "(Lcom/bhashasetu/app/data/model/Category;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "", "categories", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "addWordToCategory", "wordCategoryCrossRef", "Lcom/bhashasetu/app/data/model/WordCategoryCrossRef;", "(Lcom/bhashasetu/app/data/model/WordCategoryCrossRef;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeWordFromCategory", "removeWordFromCategoryById", "wordId", "categoryId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeAllWordsFromCategory", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addWordsToCategory", "wordCategoryCrossRefs", "getCategoryById", "getCategoryByIdLiveData", "Landroidx/lifecycle/LiveData;", "getCategoryByIdFlow", "Lkotlinx/coroutines/flow/Flow;", "getAllCategories", "getAllCategoriesLiveData", "getDefaultCategories", "searchCategories", "query", "", "getCategoryWithWords", "Lcom/bhashasetu/app/data/relation/CategoryWithWords;", "getCategoryWithWordsFlow", "getAllCategoriesWithWords", "getCategoriesForWord", "getCategoryCount", "", "getDefaultCategoryCount", "getStartedCategoryCount", "categoryExistsByName", "", "nameEnglish", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNextOrderIndex", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCategoryOrder", "newIndex", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isWordInCategory", "app_debug"})
@androidx.room.Dao()
public abstract interface CategoryDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Category> categories, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Category category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addWordToCategory(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.WordCategoryCrossRef wordCategoryCrossRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeWordFromCategory(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.WordCategoryCrossRef wordCategoryCrossRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM word_category_cross_refs WHERE wordId = :wordId AND categoryId = :categoryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeWordFromCategoryById(long wordId, long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM word_category_cross_refs WHERE categoryId = :categoryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeAllWordsFromCategory(long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addWordsToCategory(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.WordCategoryCrossRef> wordCategoryCrossRefs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM categories WHERE id = :categoryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCategoryById(long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.Category> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM categories WHERE id = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.Category> getCategoryByIdLiveData(long categoryId);
    
    @androidx.room.Query(value = "SELECT * FROM categories WHERE id = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Category> getCategoryByIdFlow(long categoryId);
    
    @androidx.room.Query(value = "SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> getAllCategories();
    
    @androidx.room.Query(value = "SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Category>> getAllCategoriesLiveData();
    
    @androidx.room.Query(value = "SELECT * FROM categories WHERE isDefault = 1 ORDER BY orderIndex ASC, nameEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> getDefaultCategories();
    
    @androidx.room.Query(value = "SELECT * FROM categories WHERE nameEnglish LIKE \'%\' || :query || \'%\' OR nameHindi LIKE \'%\' || :query || \'%\' ORDER BY nameEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> searchCategories(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM categories WHERE id = :categoryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCategoryWithWords(long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.relation.CategoryWithWords> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM categories WHERE id = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.CategoryWithWords> getCategoryWithWordsFlow(long categoryId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.CategoryWithWords>> getAllCategoriesWithWords();
    
    @androidx.room.Query(value = "\n        SELECT c.* FROM categories c\n        INNER JOIN word_category_cross_refs wc ON c.id = wc.categoryId\n        WHERE wc.wordId = :wordId\n        ORDER BY c.orderIndex ASC, c.nameEnglish ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Category>> getCategoriesForWord(long wordId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM categories")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCategoryCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM categories WHERE isDefault = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getDefaultCategoryCount();
    
    @androidx.room.Query(value = "\n        SELECT COUNT(DISTINCT c.id) FROM categories c\n        INNER JOIN word_category_cross_refs wc ON c.id = wc.categoryId\n        INNER JOIN user_progress up ON wc.wordId = up.wordId\n        WHERE up.totalAttempts > 0\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getStartedCategoryCount();
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM categories WHERE nameEnglish = :nameEnglish LIMIT 1)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object categoryExistsByName(@org.jetbrains.annotations.NotNull()
    java.lang.String nameEnglish, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @androidx.room.Query(value = "SELECT MAX(orderIndex) + 1 FROM categories")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNextOrderIndex(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "UPDATE categories SET orderIndex = :newIndex WHERE id = :categoryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateCategoryOrder(long categoryId, int newIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM word_category_cross_refs WHERE wordId = :wordId AND categoryId = :categoryId LIMIT 1)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isWordInCategory(long wordId, long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
}