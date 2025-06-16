package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010\u0014\u001a\u00020\rJ\u001a\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0006\u0010\u0019\u001a\u00020\u001aJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00072\u0006\u0010\u001d\u001a\u00020\rJ\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\b0\u0007J\u0016\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010\"J\u0016\u0010#\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010\"J\u0016\u0010$\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010\"J&\u0010%\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010+J\u001a\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0006\u0010-\u001a\u00020\rJ\u001a\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0006\u0010/\u001a\u00020\rJ\u001a\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\b0\u00072\u0006\u0010\u001d\u001a\u00020\rJ\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0007J\f\u00102\u001a\b\u0012\u0004\u0012\u0002030\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u00064"}, d2 = {"Lcom/bhashasetu/app/data/repository/QuizRepository;", "", "quizDao", "Lcom/bhashasetu/app/data/dao/QuizDao;", "<init>", "(Lcom/bhashasetu/app/data/dao/QuizDao;)V", "allQuizzes", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bhashasetu/app/data/model/Quiz;", "getAllQuizzes", "()Lkotlinx/coroutines/flow/Flow;", "insert", "", "quiz", "(Lcom/bhashasetu/app/data/model/Quiz;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "getQuizById", "id", "getQuizzesByType", "quizType", "", "getQuizzesByDifficulty", "difficulty", "", "getQuizWithQuestions", "Lcom/bhashasetu/app/data/relation/QuizWithQuestions;", "quizId", "getAllQuizzesWithQuestions", "insertQuestion", "question", "Lcom/bhashasetu/app/data/model/QuizQuestion;", "(Lcom/bhashasetu/app/data/model/QuizQuestion;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateQuestion", "deleteQuestion", "updateLastAttemptAndScore", "lastAttemptDate", "Ljava/util/Date;", "score", "(JLjava/util/Date;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementCompletionCount", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuizzesByLessonId", "lessonId", "getQuizzesByCategoryId", "categoryId", "getQuestionsByQuizId", "getCompletedQuizCount", "getAverageQuizScore", "", "app_debug"})
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