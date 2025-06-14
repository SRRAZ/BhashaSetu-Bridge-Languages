package com.bhashasetu.app.data.model;

/**
 * Entity representing a study session with tracking for analytics.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b,\b\u0087\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0014J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010+\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u000b\u0010,\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0012H\u00c6\u0003J\t\u0010/\u001a\u00020\u0012H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u00106\u001a\u00020\fH\u00c6\u0003J\t\u00107\u001a\u00020\fH\u00c6\u0003J\u00a8\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0012H\u00c6\u0001\u00a2\u0006\u0002\u00109J\u0013\u0010:\u001a\u00020\u00122\b\u0010;\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010<\u001a\u00020\fH\u00d6\u0001J\t\u0010=\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001c\u0010\u0016R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001d\u0010\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010!R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b#\u0010\u0016R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b$\u0010%R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001fR\u0011\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010!\u00a8\u0006>"}, d2 = {"Lcom/bhashasetu/app/data/model/StudySession;", "", "id", "", "sessionType", "", "categoryId", "lessonId", "startTime", "endTime", "durationMs", "itemCount", "", "correctCount", "score", "deviceInfo", "interfaceLanguage", "isCompleted", "", "wasPaused", "(JLjava/lang/String;Ljava/lang/Long;Ljava/lang/Long;JLjava/lang/Long;Ljava/lang/Long;IILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;ZZ)V", "getCategoryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getCorrectCount", "()I", "getDeviceInfo", "()Ljava/lang/String;", "getDurationMs", "getEndTime", "getId", "()J", "getInterfaceLanguage", "()Z", "getItemCount", "getLessonId", "getScore", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getSessionType", "getStartTime", "getWasPaused", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/Long;Ljava/lang/Long;JLjava/lang/Long;Ljava/lang/Long;IILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;ZZ)Lcom/bhashasetu/app/data/model/StudySession;", "equals", "other", "hashCode", "toString", "app_freeDebug"})
@androidx.room.Entity(tableName = "study_sessions", foreignKeys = {@androidx.room.ForeignKey(entity = com.bhashasetu.app.data.model.Category.class, parentColumns = {"id"}, childColumns = {"categoryId"}, onDelete = 3), @androidx.room.ForeignKey(entity = com.bhashasetu.app.data.model.Lesson.class, parentColumns = {"id"}, childColumns = {"lessonId"}, onDelete = 3)})
public final class StudySession {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sessionType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long categoryId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lessonId = null;
    private final long startTime = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long endTime = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long durationMs = null;
    private final int itemCount = 0;
    private final int correctCount = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer score = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String deviceInfo = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String interfaceLanguage = null;
    private final boolean isCompleted = false;
    private final boolean wasPaused = false;
    
    public StudySession(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionType, @org.jetbrains.annotations.Nullable()
    java.lang.Long categoryId, @org.jetbrains.annotations.Nullable()
    java.lang.Long lessonId, long startTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long endTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long durationMs, int itemCount, int correctCount, @org.jetbrains.annotations.Nullable()
    java.lang.Integer score, @org.jetbrains.annotations.Nullable()
    java.lang.String deviceInfo, @org.jetbrains.annotations.Nullable()
    java.lang.String interfaceLanguage, boolean isCompleted, boolean wasPaused) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCategoryId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLessonId() {
        return null;
    }
    
    public final long getStartTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getEndTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getDurationMs() {
        return null;
    }
    
    public final int getItemCount() {
        return 0;
    }
    
    public final int getCorrectCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getScore() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDeviceInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getInterfaceLanguage() {
        return null;
    }
    
    public final boolean isCompleted() {
        return false;
    }
    
    public final boolean getWasPaused() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component4() {
        return null;
    }
    
    public final long component5() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.StudySession copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionType, @org.jetbrains.annotations.Nullable()
    java.lang.Long categoryId, @org.jetbrains.annotations.Nullable()
    java.lang.Long lessonId, long startTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long endTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long durationMs, int itemCount, int correctCount, @org.jetbrains.annotations.Nullable()
    java.lang.Integer score, @org.jetbrains.annotations.Nullable()
    java.lang.String deviceInfo, @org.jetbrains.annotations.Nullable()
    java.lang.String interfaceLanguage, boolean isCompleted, boolean wasPaused) {
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