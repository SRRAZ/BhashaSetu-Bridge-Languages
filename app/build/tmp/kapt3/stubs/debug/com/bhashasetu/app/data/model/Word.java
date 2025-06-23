package com.bhashasetu.app.data.model;

/**
 * Entity representing a vocabulary word with translations in both English and Hindi.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b4\b\u0087\b\u0018\u00002\u00020\u0001B\u00b5\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0004\b\u0016\u0010\u0017J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J\t\u0010/\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00104\u001a\u00020\fH\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u000fH\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010;\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u0010\u0010<\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u00c0\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010>J\u0013\u0010?\u001a\u00020\u000f2\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020\fH\u00d6\u0001J\t\u0010B\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010$R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0019R\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*R\u0015\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b,\u0010*\u00a8\u0006C"}, d2 = {"Lcom/bhashasetu/app/data/model/Word;", "", "id", "", "englishText", "", "hindiText", "englishPronunciation", "hindiPronunciation", "englishExample", "hindiExample", "difficulty", "", "partOfSpeech", "isFavorite", "", "englishAudioPath", "hindiAudioPath", "createdAt", "lastModifiedAt", "lastReviewedAt", "nextReviewDate", "<init>", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;ZLjava/lang/String;Ljava/lang/String;JJLjava/lang/Long;Ljava/lang/Long;)V", "getId", "()J", "getEnglishText", "()Ljava/lang/String;", "getHindiText", "getEnglishPronunciation", "getHindiPronunciation", "getEnglishExample", "getHindiExample", "getDifficulty", "()I", "getPartOfSpeech", "()Z", "getEnglishAudioPath", "getHindiAudioPath", "getCreatedAt", "getLastModifiedAt", "getLastReviewedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getNextReviewDate", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;ZLjava/lang/String;Ljava/lang/String;JJLjava/lang/Long;Ljava/lang/Long;)Lcom/bhashasetu/app/data/model/Word;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "words")
public final class Word {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String englishText = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String hindiText = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String englishPronunciation = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String hindiPronunciation = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String englishExample = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String hindiExample = null;
    private final int difficulty = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String partOfSpeech = null;
    private final boolean isFavorite = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String englishAudioPath = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String hindiAudioPath = null;
    private final long createdAt = 0L;
    private final long lastModifiedAt = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastReviewedAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long nextReviewDate = null;
    
    public Word(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String englishText, @org.jetbrains.annotations.NotNull()
    java.lang.String hindiText, @org.jetbrains.annotations.Nullable()
    java.lang.String englishPronunciation, @org.jetbrains.annotations.Nullable()
    java.lang.String hindiPronunciation, @org.jetbrains.annotations.Nullable()
    java.lang.String englishExample, @org.jetbrains.annotations.Nullable()
    java.lang.String hindiExample, int difficulty, @org.jetbrains.annotations.Nullable()
    java.lang.String partOfSpeech, boolean isFavorite, @org.jetbrains.annotations.Nullable()
    java.lang.String englishAudioPath, @org.jetbrains.annotations.Nullable()
    java.lang.String hindiAudioPath, long createdAt, long lastModifiedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastReviewedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long nextReviewDate) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEnglishText() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHindiText() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEnglishPronunciation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHindiPronunciation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEnglishExample() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHindiExample() {
        return null;
    }
    
    public final int getDifficulty() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPartOfSpeech() {
        return null;
    }
    
    public final boolean isFavorite() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEnglishAudioPath() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHindiAudioPath() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getLastModifiedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastReviewedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getNextReviewDate() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final boolean component10() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component16() {
        return null;
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
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Word copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String englishText, @org.jetbrains.annotations.NotNull()
    java.lang.String hindiText, @org.jetbrains.annotations.Nullable()
    java.lang.String englishPronunciation, @org.jetbrains.annotations.Nullable()
    java.lang.String hindiPronunciation, @org.jetbrains.annotations.Nullable()
    java.lang.String englishExample, @org.jetbrains.annotations.Nullable()
    java.lang.String hindiExample, int difficulty, @org.jetbrains.annotations.Nullable()
    java.lang.String partOfSpeech, boolean isFavorite, @org.jetbrains.annotations.Nullable()
    java.lang.String englishAudioPath, @org.jetbrains.annotations.Nullable()
    java.lang.String hindiAudioPath, long createdAt, long lastModifiedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastReviewedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long nextReviewDate) {
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