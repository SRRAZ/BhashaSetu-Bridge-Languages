package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the Quiz entity.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0007\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\"\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0015\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0018\u0010\u0019\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0017\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0018\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u001b2\u0006\u0010\u0017\u001a\u00020\u0003H\'J\u0018\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u001d2\u0006\u0010\u0017\u001a\u00020\u0003H\'J\u0014\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001dH\'J\u0014\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001bH\'J\u001c\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001d2\u0006\u0010!\u001a\u00020\u0003H\'J\u001c\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001d2\u0006\u0010#\u001a\u00020\u0003H\'J\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001d2\u0006\u0010%\u001a\u00020&H\'J\u001c\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001d2\u0006\u0010(\u001a\u00020)H\'J\u001c\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u001d2\u0006\u0010+\u001a\u00020,H\'J\u0018\u0010-\u001a\u0004\u0018\u00010\u00102\u0006\u0010.\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\b0\u001d2\u0006\u0010\u0017\u001a\u00020\u0003H\'J$\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\b0\u001d2\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u00101\u001a\u00020&H\'J\u0018\u00102\u001a\u0004\u0018\u0001032\u0006\u0010\u0017\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0018\u00104\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001030\u001d2\u0006\u0010\u0017\u001a\u00020\u0003H\'J\u0014\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002030\b0\u001dH\'J\u001c\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002030\b0\u001d2\u0006\u0010!\u001a\u00020\u0003H\'J\u001c\u00107\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002030\b0\u001d2\u0006\u0010#\u001a\u00020\u0003H\'J.\u00108\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010(\u001a\u00020)2\u0006\u00109\u001a\u00020&2\u0006\u0010:\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010;J\u001e\u0010<\u001a\u00020\f2\u0006\u0010.\u001a\u00020\u00032\u0006\u0010=\u001a\u00020)H\u00a7@\u00a2\u0006\u0002\u0010>J\u000e\u0010?\u001a\b\u0012\u0004\u0012\u00020&0\u001dH\'J\u000e\u0010@\u001a\b\u0012\u0004\u0012\u00020&0\u001dH\'J\u0010\u0010A\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010B0\u001dH\'J\u000e\u0010C\u001a\b\u0012\u0004\u0012\u00020&0\u001dH\'J\u0010\u0010D\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010&0\u001dH\'J\u0010\u0010E\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010&0\u001dH\'\u00a8\u0006F"}, d2 = {"Lcom/bhashasetu/app/data/dao/QuizDao;", "", "insert", "", "quiz", "Lcom/bhashasetu/app/data/model/Quiz;", "(Lcom/bhashasetu/app/data/model/Quiz;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "", "quizzes", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "insertQuestion", "question", "Lcom/bhashasetu/app/data/model/QuizQuestion;", "(Lcom/bhashasetu/app/data/model/QuizQuestion;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertQuestions", "questions", "updateQuestion", "deleteQuestion", "deleteAllQuestionsForQuiz", "quizId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuizById", "getQuizByIdLiveData", "Landroidx/lifecycle/LiveData;", "getQuizByIdFlow", "Lkotlinx/coroutines/flow/Flow;", "getAllQuizzes", "getAllQuizzesLiveData", "getQuizzesByLesson", "lessonId", "getQuizzesByCategory", "categoryId", "getQuizzesByDifficulty", "difficulty", "", "getQuizzesByCompletionStatus", "isCompleted", "", "searchQuizzes", "query", "", "getQuestionById", "questionId", "getQuestionsForQuiz", "getQuestionsForQuizByType", "questionType", "getQuizWithQuestions", "Lcom/bhashasetu/app/data/relation/QuizWithQuestions;", "getQuizWithQuestionsFlow", "getAllQuizzesWithQuestions", "getQuizzesWithQuestionsByLesson", "getQuizzesWithQuestionsByCategory", "updateQuizProgress", "score", "attemptTime", "(JZIJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateQuestionStats", "isCorrect", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuizCount", "getCompletedQuizCount", "getAverageQuizScore", "", "getTotalQuestionCount", "getTotalCorrectAnswers", "getTotalQuestionAttempts", "app_debug"})
@androidx.room.Dao()
public abstract interface QuizDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Quiz> quizzes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertQuestions(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.QuizQuestion> questions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM quiz_questions WHERE quizId = :quizId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllQuestionsForQuiz(long quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuizById(long quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.Quiz> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.Quiz> getQuizByIdLiveData(long quizId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Quiz> getQuizByIdFlow(long quizId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes ORDER BY titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getAllQuizzes();
    
    @androidx.room.Query(value = "SELECT * FROM quizzes ORDER BY titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Quiz>> getAllQuizzesLiveData();
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE lessonId = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByLesson(long lessonId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE categoryId = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByCategory(long categoryId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE difficulty = :difficulty")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByDifficulty(int difficulty);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE isCompleted = :isCompleted")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByCompletionStatus(boolean isCompleted);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE titleEnglish LIKE \'%\' || :query || \'%\' OR titleHindi LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> searchQuizzes(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "SELECT * FROM quiz_questions WHERE id = :questionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestionById(long questionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.QuizQuestion> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quiz_questions WHERE quizId = :quizId ORDER BY orderInQuiz ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.QuizQuestion>> getQuestionsForQuiz(long quizId);
    
    @androidx.room.Query(value = "SELECT * FROM quiz_questions WHERE quizId = :quizId AND questionType = :questionType ORDER BY orderInQuiz ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.QuizQuestion>> getQuestionsForQuizByType(long quizId, int questionType);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuizWithQuestions(long quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.relation.QuizWithQuestions> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.QuizWithQuestions> getQuizWithQuestionsFlow(long quizId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.QuizWithQuestions>> getAllQuizzesWithQuestions();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE lessonId = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.QuizWithQuestions>> getQuizzesWithQuestionsByLesson(long lessonId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE categoryId = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.QuizWithQuestions>> getQuizzesWithQuestionsByCategory(long categoryId);
    
    @androidx.room.Query(value = "\n        UPDATE quizzes\n        SET isCompleted = :isCompleted,\n            lastAttemptScore = :score,\n            bestScore = CASE WHEN :score > IFNULL(bestScore, 0) THEN :score ELSE bestScore END,\n            attemptCount = attemptCount + 1,\n            lastAttemptAt = :attemptTime,\n            completedAt = CASE WHEN :isCompleted THEN :attemptTime ELSE completedAt END\n        WHERE id = :quizId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQuizProgress(long quizId, boolean isCompleted, int score, long attemptTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE quiz_questions\n        SET correctAttempts = correctAttempts + CASE WHEN :isCorrect THEN 1 ELSE 0 END,\n            totalAttempts = totalAttempts + 1\n        WHERE id = :questionId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQuestionStats(long questionId, boolean isCorrect, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM quizzes")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getQuizCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM quizzes WHERE isCompleted = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedQuizCount();
    
    @androidx.room.Query(value = "SELECT AVG(CAST(lastAttemptScore AS FLOAT) / (SELECT COUNT(*) FROM quiz_questions WHERE quizId = quizzes.id)) FROM quizzes WHERE lastAttemptScore IS NOT NULL")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Float> getAverageQuizScore();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM quiz_questions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalQuestionCount();
    
    @androidx.room.Query(value = "SELECT SUM(correctAttempts) FROM quiz_questions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalCorrectAnswers();
    
    @androidx.room.Query(value = "SELECT SUM(totalAttempts) FROM quiz_questions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalQuestionAttempts();
}