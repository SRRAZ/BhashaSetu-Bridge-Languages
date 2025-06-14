package com.bhashasetu.app.data.model;

/**
 * Entity representing a user's progress with a specific vocabulary word.
 * Implements spaced repetition data model for optimized learning.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b(\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0011J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\tH\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\u008c\u0001\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0002\u00100J\u0013\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00104\u001a\u00020\u0005H\u00d6\u0001J\t\u00105\u001a\u000206H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0015\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u001b\u0010\u0018R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u001c\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0013R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010 \u00a8\u00067"}, d2 = {"Lcom/bhashasetu/app/data/model/UserProgress;", "", "wordId", "", "proficiencyLevel", "", "correctAttempts", "totalAttempts", "easeFactor", "", "intervalDays", "repetitions", "lastAttemptAt", "lastCorrectAt", "nextReviewDue", "timeSpentMs", "lastConfidenceRating", "(JIIIFIILjava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;JI)V", "getCorrectAttempts", "()I", "getEaseFactor", "()F", "getIntervalDays", "getLastAttemptAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getLastConfidenceRating", "getLastCorrectAt", "getNextReviewDue", "getProficiencyLevel", "getRepetitions", "getTimeSpentMs", "()J", "getTotalAttempts", "getWordId", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JIIIFIILjava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;JI)Lcom/bhashasetu/app/data/model/UserProgress;", "equals", "", "other", "hashCode", "toString", "", "app_freeDebug"})
@androidx.room.Entity(tableName = "user_progress", foreignKeys = {@androidx.room.ForeignKey(entity = com.bhashasetu.app.data.model.Word.class, parentColumns = {"id"}, childColumns = {"wordId"}, onDelete = 5)})
public final class UserProgress {
    @androidx.room.PrimaryKey()
    private final long wordId = 0L;
    private final int proficiencyLevel = 0;
    private final int correctAttempts = 0;
    private final int totalAttempts = 0;
    private final float easeFactor = 0.0F;
    private final int intervalDays = 0;
    private final int repetitions = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastAttemptAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastCorrectAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long nextReviewDue = null;
    private final long timeSpentMs = 0L;
    private final int lastConfidenceRating = 0;
    
    public UserProgress(long wordId, int proficiencyLevel, int correctAttempts, int totalAttempts, float easeFactor, int intervalDays, int repetitions, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastAttemptAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastCorrectAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long nextReviewDue, long timeSpentMs, int lastConfidenceRating) {
        super();
    }
    
    public final long getWordId() {
        return 0L;
    }
    
    public final int getProficiencyLevel() {
        return 0;
    }
    
    public final int getCorrectAttempts() {
        return 0;
    }
    
    public final int getTotalAttempts() {
        return 0;
    }
    
    public final float getEaseFactor() {
        return 0.0F;
    }
    
    public final int getIntervalDays() {
        return 0;
    }
    
    public final int getRepetitions() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastAttemptAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastCorrectAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getNextReviewDue() {
        return null;
    }
    
    public final long getTimeSpentMs() {
        return 0L;
    }
    
    public final int getLastConfidenceRating() {
        return 0;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component10() {
        return null;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.UserProgress copy(long wordId, int proficiencyLevel, int correctAttempts, int totalAttempts, float easeFactor, int intervalDays, int repetitions, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastAttemptAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastCorrectAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long nextReviewDue, long timeSpentMs, int lastConfidenceRating) {
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