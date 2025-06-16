package com.bhashasetu.app.data.model;

/**
 * Junction entity to create many-to-many relationship between Word and Category.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t\u00a8\u0006\u0017"}, d2 = {"Lcom/bhashasetu/app/data/model/WordCategoryCrossRef;", "", "wordId", "", "categoryId", "addedAt", "<init>", "(JJJ)V", "getWordId", "()J", "getCategoryId", "getAddedAt", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
@androidx.room.Entity(tableName = "word_category_cross_refs", primaryKeys = {"wordId", "categoryId"}, indices = {@androidx.room.Index(value = {"wordId"}), @androidx.room.Index(value = {"categoryId"})}, foreignKeys = {@androidx.room.ForeignKey(entity = com.bhashasetu.app.data.model.Word.class, parentColumns = {"id"}, childColumns = {"wordId"}, onDelete = 5), @androidx.room.ForeignKey(entity = com.bhashasetu.app.data.model.Category.class, parentColumns = {"id"}, childColumns = {"categoryId"}, onDelete = 5)})
public final class WordCategoryCrossRef {
    private final long wordId = 0L;
    private final long categoryId = 0L;
    private final long addedAt = 0L;
    
    public WordCategoryCrossRef(long wordId, long categoryId, long addedAt) {
        super();
    }
    
    public final long getWordId() {
        return 0L;
    }
    
    public final long getCategoryId() {
        return 0L;
    }
    
    public final long getAddedAt() {
        return 0L;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.WordCategoryCrossRef copy(long wordId, long categoryId, long addedAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}