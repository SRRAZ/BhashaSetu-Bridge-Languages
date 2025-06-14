package com.bhashasetu.app.data.relation;

/**
 * Represents a Quiz with all its associated Questions.
 * This is a Room relationship helper class for retrieving a quiz with all its questions.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010\u0012\u001a\u00020\u0013J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0006\u0010\u0015\u001a\u00020\u0013J\u0006\u0010\u0016\u001a\u00020\u0013J\t\u0010\u0017\u001a\u00020\u0013H\u00d6\u0001J\u0006\u0010\u0018\u001a\u00020\u0010J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/bhashasetu/app/data/relation/QuizWithQuestions;", "", "quiz", "Lcom/bhashasetu/app/data/model/Quiz;", "questions", "", "Lcom/bhashasetu/app/data/model/QuizQuestion;", "(Lcom/bhashasetu/app/data/model/Quiz;Ljava/util/List;)V", "getQuestions", "()Ljava/util/List;", "getQuiz", "()Lcom/bhashasetu/app/data/model/Quiz;", "component1", "component2", "copy", "equals", "", "other", "getEstimatedDurationSeconds", "", "getOrderedQuestions", "getQuestionCount", "getTotalPossibleScore", "hashCode", "isPassing", "toString", "", "app_freeDebug"})
public final class QuizWithQuestions {
    @androidx.room.Embedded()
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.model.Quiz quiz = null;
    @androidx.room.Relation(parentColumn = "id", entityColumn = "quizId")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.bhashasetu.app.data.model.QuizQuestion> questions = null;
    
    public QuizWithQuestions(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.QuizQuestion> questions) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Quiz getQuiz() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.QuizQuestion> getQuestions() {
        return null;
    }
    
    /**
     * Returns the questions in the order specified in the quiz.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.QuizQuestion> getOrderedQuestions() {
        return null;
    }
    
    /**
     * Returns the number of questions in the quiz.
     */
    public final int getQuestionCount() {
        return 0;
    }
    
    /**
     * Returns the total possible score for the quiz.
     */
    public final int getTotalPossibleScore() {
        return 0;
    }
    
    /**
     * Returns the expected duration of the quiz in seconds.
     * Uses estimated time per question based on question type.
     */
    public final int getEstimatedDurationSeconds() {
        return 0;
    }
    
    /**
     * Returns whether the quiz is passing based on the last attempt score.
     */
    public final boolean isPassing() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Quiz component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bhashasetu.app.data.model.QuizQuestion> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.relation.QuizWithQuestions copy(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.QuizQuestion> questions) {
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