package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the Category entity.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0016\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\t0\u0015H\'J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\t0\u0017H\'J\u0014\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\t0\u0015H\'J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\t0\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\'J\u0018\u0010\u001d\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001e\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u0018\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00152\u0006\u0010\u001e\u001a\u00020\u001cH\'J\u0018\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00172\u0006\u0010\u001e\u001a\u00020\u001cH\'J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u0015H\'J\u0018\u0010$\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001e\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u0018\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00152\u0006\u0010\u001e\u001a\u00020\u001cH\'J\u0014\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\t0\u0015H\'J\u000e\u0010\'\u001a\b\u0012\u0004\u0012\u00020#0\u0015H\'J\u000e\u0010(\u001a\u00020#H\u00a7@\u00a2\u0006\u0002\u0010)J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00020#0\u0015H\'J\u0016\u0010+\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\"\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001c0\t2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00120\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010.\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010/J\u0016\u00100\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u00101\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u00102\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010/J\u001c\u00103\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\t0\u00152\u0006\u00104\u001a\u00020\u000eH\'J\u0016\u00105\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001e\u00106\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u00107\u001a\u00020#H\u00a7@\u00a2\u0006\u0002\u00108\u00a8\u00069"}, d2 = {"Lcom/bhashasetu/app/data/dao/CategoryDao;", "", "addWordToCategory", "", "wordCategoryCrossRef", "Lcom/bhashasetu/app/data/model/WordCategoryCrossRef;", "(Lcom/bhashasetu/app/data/model/WordCategoryCrossRef;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addWordsToCategory", "wordCategoryCrossRefs", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "categoryExistsByName", "", "nameEnglish", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "category", "Lcom/bhashasetu/app/data/model/Category;", "(Lcom/bhashasetu/app/data/model/Category;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllCategories", "Lkotlinx/coroutines/flow/Flow;", "getAllCategoriesLiveData", "Landroidx/lifecycle/LiveData;", "getAllCategoriesWithWords", "Lcom/bhashasetu/app/data/relation/CategoryWithWords;", "getCategoriesForWord", "wordId", "", "getCategoryById", "categoryId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategoryByIdFlow", "getCategoryByIdLiveData", "getCategoryCount", "", "getCategoryWithWords", "getCategoryWithWordsFlow", "getDefaultCategories", "getDefaultCategoryCount", "getNextOrderIndex", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStartedCategoryCount", "insert", "insertAll", "categories", "isWordInCategory", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeAllWordsFromCategory", "removeWordFromCategory", "removeWordFromCategoryById", "searchCategories", "query", "update", "updateCategoryOrder", "newIndex", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_freeDebug"})
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