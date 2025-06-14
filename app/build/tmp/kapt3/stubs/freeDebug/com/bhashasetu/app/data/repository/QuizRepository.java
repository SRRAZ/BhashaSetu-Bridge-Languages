package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00070\u0006J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0006J\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00070\u00062\u0006\u0010\u001a\u001a\u00020\u001bJ\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u001d\u001a\u00020\u001bJ\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00062\u0006\u0010\u001a\u001a\u00020\u001bJ\u001a\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010 \u001a\u00020\u001bJ\u001a\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\"\u001a\u00020\u0018J\u001a\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010$\u001a\u00020\u001bJ\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010&\u001a\u00020\'J\u0016\u0010(\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010+\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010,\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000eJ&\u0010-\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u00101J\u0016\u00102\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2 = {"Lcom/bhashasetu/app/data/repository/QuizRepository;", "", "quizDao", "Lcom/bhashasetu/app/data/dao/QuizDao;", "(Lcom/bhashasetu/app/data/dao/QuizDao;)V", "allQuizzes", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bhashasetu/app/data/model/Quiz;", "getAllQuizzes", "()Lkotlinx/coroutines/flow/Flow;", "delete", "", "quiz", "(Lcom/bhashasetu/app/data/model/Quiz;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteQuestion", "question", "Lcom/bhashasetu/app/data/model/QuizQuestion;", "(Lcom/bhashasetu/app/data/model/QuizQuestion;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllQuizzesWithQuestions", "Lcom/bhashasetu/app/data/relation/QuizWithQuestions;", "getAverageQuizScore", "", "getCompletedQuizCount", "", "getQuestionsByQuizId", "quizId", "", "getQuizById", "id", "getQuizWithQuestions", "getQuizzesByCategoryId", "categoryId", "getQuizzesByDifficulty", "difficulty", "getQuizzesByLessonId", "lessonId", "getQuizzesByType", "quizType", "", "incrementCompletionCount", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "insertQuestion", "update", "updateLastAttemptAndScore", "lastAttemptDate", "Ljava/util/Date;", "score", "(JLjava/util/Date;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateQuestion", "app_freeDebug"})
public final class QuizRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.QuizDao quizDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> allQuizzes = null;
    
    public QuizRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.QuizDao quizDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getAllQuizzes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Quiz> getQuizById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByType(@org.jetbrains.annotations.NotNull()
    java.lang.String quizType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByDifficulty(int difficulty) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.QuizWithQuestions> getQuizWithQuestions(long quizId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.QuizWithQuestions>> getAllQuizzesWithQuestions() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLastAttemptAndScore(long quizId, @org.jetbrains.annotations.NotNull()
    java.util.Date lastAttemptDate, int score, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object incrementCompletionCount(long quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByLessonId(long lessonId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByCategoryId(long categoryId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.QuizQuestion>> getQuestionsByQuizId(long quizId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedQuizCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Float> getAverageQuizScore() {
        return null;
    }
}