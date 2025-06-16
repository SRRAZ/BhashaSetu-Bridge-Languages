package com.bhashasetu.app.data.relation;

/**
 * Represents a Lesson with all its associated Words.
 * This is a Room relationship helper class for retrieving a lesson with all its vocabulary.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0004\b\t\u0010\nJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J3\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e\u00a8\u0006\u001e"}, d2 = {"Lcom/bhashasetu/app/data/relation/LessonWithWords;", "", "lesson", "Lcom/bhashasetu/app/data/model/Lesson;", "words", "", "Lcom/bhashasetu/app/data/model/Word;", "lessonWords", "Lcom/bhashasetu/app/data/model/LessonWord;", "<init>", "(Lcom/bhashasetu/app/data/model/Lesson;Ljava/util/List;Ljava/util/List;)V", "getLesson", "()Lcom/bhashasetu/app/data/model/Lesson;", "getWords", "()Ljava/util/List;", "getLessonWords", "getOrderedWords", "getKeywords", "getQuizWords", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class LessonWithWords {
    @androidx.room.Embedded()
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.model.Lesson lesson = null;
    @androidx.room.Relation(parentColumn = "id", entityColumn = "id", associateBy = @androidx.room.Junction(value = com.bhashasetu.app.data.model.LessonWord.class, parentColumn = "lessonId", entityColumn = "wordId"))
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.bhashasetu.app.data.model.Word> words = null;
    @androidx.room.Relation(entity = com.bhashasetu.app.data.model.LessonWord.class, parentColumn = "id", entityColumn = "lessonId")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.bhashasetu.app.data.model.LessonWord> lessonWords = null;
    
    public LessonWithWords(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Word> words, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.LessonWord> lessonWords) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Lesson getLesson() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.Word> getWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.LessonWord> getLessonWords() {
        return null;
    }
    
    /**
     * Returns the words in the order specified in the lesson.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.Word> getOrderedWords() {
        return null;
    }
    
    /**
     * Returns only the keywords of the lesson.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.Word> getKeywords() {
        return null;
    }
    
    /**
     * Returns the words to be included in the quiz.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.Word> getQuizWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Lesson component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.Word> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.LessonWord> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.relation.LessonWithWords copy(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Word> words, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.LessonWord> lessonWords) {
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