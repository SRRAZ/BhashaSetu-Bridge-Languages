package com.bhashasetu.app.data.model;

/**
 * Entity representing a learning lesson containing vocabulary words and content.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b3\b\u0087\b\u0018\u00002\u00020\u0001B\u00b9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\r\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0017J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\rH\u00c6\u0003J\t\u0010/\u001a\u00020\rH\u00c6\u0003J\t\u00100\u001a\u00020\u0011H\u00c6\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0011H\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010<\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u0010=\u001a\u00020\rH\u00c6\u0003J\u00c6\u0001\u0010>\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u0011H\u00c6\u0001\u00a2\u0006\u0002\u0010?J\u0013\u0010@\u001a\u00020\u00112\b\u0010A\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010B\u001a\u00020\rH\u00d6\u0001J\t\u0010C\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001b\u0010\u0019R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u000f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010$R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010 R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\'R\u0011\u0010\u0016\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\'R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b(\u0010\u0019R\u0011\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u0011\u0010\u000e\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001dR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001d\u00a8\u0006D"}, d2 = {"Lcom/bhashasetu/app/data/model/Lesson;", "", "id", "", "titleEnglish", "", "titleHindi", "descriptionEnglish", "descriptionHindi", "contentEnglish", "contentHindi", "categoryId", "difficulty", "", "orderInCategory", "estimatedDurationMinutes", "isCompleted", "", "completedAt", "lastAccessedAt", "createdAt", "lastModifiedAt", "isSystemLesson", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IIIZLjava/lang/Long;Ljava/lang/Long;JJZ)V", "getCategoryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getCompletedAt", "getContentEnglish", "()Ljava/lang/String;", "getContentHindi", "getCreatedAt", "()J", "getDescriptionEnglish", "getDescriptionHindi", "getDifficulty", "()I", "getEstimatedDurationMinutes", "getId", "()Z", "getLastAccessedAt", "getLastModifiedAt", "getOrderInCategory", "getTitleEnglish", "getTitleHindi", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IIIZLjava/lang/Long;Ljava/lang/Long;JJZ)Lcom/bhashasetu/app/data/model/Lesson;", "equals", "other", "hashCode", "toString", "app_freeDebug"})
@androidx.room.Entity(tableName = "lessons", foreignKeys = {@androidx.room.ForeignKey(entity = com.bhashasetu.app.data.model.Category.class, parentColumns = {"id"}, childColumns = {"categoryId"}, onDelete = 3)})
public final class Lesson {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String titleEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String titleHindi = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String descriptionEnglish = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String descriptionHindi = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String contentEnglish = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String contentHindi = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long categoryId = null;
    private final int difficulty = 0;
    private final int orderInCategory = 0;
    private final int estimatedDurationMinutes = 0;
    private final boolean isCompleted = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long completedAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastAccessedAt = null;
    private final long createdAt = 0L;
    private final long lastModifiedAt = 0L;
    private final boolean isSystemLesson = false;
    
    public Lesson(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String titleEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String titleHindi, @org.jetbrains.annotations.Nullable()
    java.lang.String descriptionEnglish, @org.jetbrains.annotations.Nullable()
    java.lang.String descriptionHindi, @org.jetbrains.annotations.Nullable()
    java.lang.String contentEnglish, @org.jetbrains.annotations.Nullable()
    java.lang.String contentHindi, @org.jetbrains.annotations.Nullable()
    java.lang.Long categoryId, int difficulty, int orderInCategory, int estimatedDurationMinutes, boolean isCompleted, @org.jetbrains.annotations.Nullable()
    java.lang.Long completedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastAccessedAt, long createdAt, long lastModifiedAt, boolean isSystemLesson) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitleEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitleHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDescriptionEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDescriptionHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getContentEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getContentHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCategoryId() {
        return null;
    }
    
    public final int getDifficulty() {
        return 0;
    }
    
    public final int getOrderInCategory() {
        return 0;
    }
    
    public final int getEstimatedDurationMinutes() {
        return 0;
    }
    
    public final boolean isCompleted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCompletedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastAccessedAt() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getLastModifiedAt() {
        return 0L;
    }
    
    public final boolean isSystemLesson() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int component11() {
        return 0;
    }
    
    public final boolean component12() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component14() {
        return null;
    }
    
    public final long component15() {
        return 0L;
    }
    
    public final long component16() {
        return 0L;
    }
    
    public final boolean component17() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component8() {
        return null;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Lesson copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String titleEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String titleHindi, @org.jetbrains.annotations.Nullable()
    java.lang.String descriptionEnglish, @org.jetbrains.annotations.Nullable()
    java.lang.String descriptionHindi, @org.jetbrains.annotations.Nullable()
    java.lang.String contentEnglish, @org.jetbrains.annotations.Nullable()
    java.lang.String contentHindi, @org.jetbrains.annotations.Nullable()
    java.lang.Long categoryId, int difficulty, int orderInCategory, int estimatedDurationMinutes, boolean isCompleted, @org.jetbrains.annotations.Nullable()
    java.lang.Long completedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastAccessedAt, long createdAt, long lastModifiedAt, boolean isSystemLesson) {
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