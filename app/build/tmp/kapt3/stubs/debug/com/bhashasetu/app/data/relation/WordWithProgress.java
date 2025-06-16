package com.bhashasetu.app.data.relation;

/**
 * Represents a Word with its associated UserProgress.
 * This is a Room relationship helper class for retrieving a word with its learning progress.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\u0012J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\rH\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0012H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001a"}, d2 = {"Lcom/bhashasetu/app/data/relation/WordWithProgress;", "", "word", "Lcom/bhashasetu/app/data/model/Word;", "progress", "Lcom/bhashasetu/app/data/model/UserProgress;", "<init>", "(Lcom/bhashasetu/app/data/model/Word;Lcom/bhashasetu/app/data/model/UserProgress;)V", "getWord", "()Lcom/bhashasetu/app/data/model/Word;", "getProgress", "()Lcom/bhashasetu/app/data/model/UserProgress;", "getProficiencyLevel", "", "isDueForReview", "", "hasBeenStarted", "getMasteryStatus", "", "component1", "component2", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class WordWithProgress {
    @androidx.room.Embedded()
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.model.Word word = null;
    @androidx.room.Relation(parentColumn = "id", entityColumn = "wordId")
    @org.jetbrains.annotations.Nullable()
    private final com.bhashasetu.app.data.model.UserProgress progress = null;
    
    public WordWithProgress(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.Nullable()
    com.bhashasetu.app.data.model.UserProgress progress) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Word getWord() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.bhashasetu.app.data.model.UserProgress getProgress() {
        return null;
    }
    
    /**
     * Returns the word's proficiency level (0-100) or 0 if no progress exists.
     */
    public final int getProficiencyLevel() {
        return 0;
    }
    
    /**
     * Returns whether the word is due for review based on next review date.
     */
    public final boolean isDueForReview() {
        return false;
    }
    
    /**
     * Returns whether the word has been started (at least one attempt).
     */
    public final boolean hasBeenStarted() {
        return false;
    }
    
    /**
     * Returns the mastery status of the word.
     * @return One of: "new", "learning", "reviewing", "mastered"
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMasteryStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Word component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.bhashasetu.app.data.model.UserProgress component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.relation.WordWithProgress copy(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Word word, @org.jetbrains.annotations.Nullable()
    com.bhashasetu.app.data.model.UserProgress progress) {
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