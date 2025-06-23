package com.bhashasetu.app.ui.viewmodel;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0010\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u001eJ\u0016\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020\u001e2\u0006\u0010/\u001a\u00020\u001fJ\u0006\u00100\u001a\u000201J\u0006\u00102\u001a\u000201J\u0006\u00103\u001a\u00020+J\u0018\u00104\u001a\u0002012\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u001fH\u0002J\u0017\u00108\u001a\u00020\u000f2\b\u00109\u001a\u0004\u0018\u00010\u000fH\u0002\u00a2\u0006\u0002\u0010:J\u0014\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00160<2\u0006\u0010,\u001a\u00020\u001eJ\u001a\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0<2\u0006\u0010>\u001a\u00020\u001fJ\u001a\u0010?\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0<2\u0006\u0010@\u001a\u00020\u000fJ\u001a\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0<2\u0006\u0010B\u001a\u00020\u001eJ\u001a\u0010C\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0<2\u0006\u0010D\u001a\u00020\u001eJ$\u0010E\u001a\u00020\u001e2\u0006\u0010F\u001a\u00020\u000b2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002060\nH\u0086@\u00a2\u0006\u0002\u0010HR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0016\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\rR\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000f0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\rR \u0010\u001c\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\u001d0\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0!0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\rR\u0016\u0010#\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\rR\u0016\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\'0\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\'0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\r\u00a8\u0006I"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/QuizViewModel;", "Landroidx/lifecycle/ViewModel;", "quizRepository", "Lcom/bhashasetu/app/data/repository/QuizRepository;", "userProgressRepository", "Lcom/bhashasetu/app/data/repository/UserProgressRepository;", "<init>", "(Lcom/bhashasetu/app/data/repository/QuizRepository;Lcom/bhashasetu/app/data/repository/UserProgressRepository;)V", "allQuizzes", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/bhashasetu/app/data/model/Quiz;", "getAllQuizzes", "()Lkotlinx/coroutines/flow/StateFlow;", "completedQuizCount", "", "getCompletedQuizCount", "averageQuizScore", "", "getAverageQuizScore", "_activeQuiz", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bhashasetu/app/data/relation/QuizWithQuestions;", "activeQuiz", "getActiveQuiz", "_currentQuestionIndex", "currentQuestionIndex", "getCurrentQuestionIndex", "_userAnswers", "", "", "", "userAnswers", "", "getUserAnswers", "_remainingTimeSeconds", "remainingTimeSeconds", "getRemainingTimeSeconds", "_quizResult", "Lcom/bhashasetu/app/ui/viewmodel/QuizResult;", "quizResult", "getQuizResult", "startQuiz", "", "quizId", "selectAnswer", "questionId", "answer", "nextQuestion", "", "previousQuestion", "submitQuiz", "isAnswerCorrect", "question", "Lcom/bhashasetu/app/data/model/QuizQuestion;", "userAnswer", "calculateCompletionTime", "timeLimit", "(Ljava/lang/Integer;)I", "getQuizById", "Lkotlinx/coroutines/flow/Flow;", "getQuizzesByType", "quizType", "getQuizzesByDifficulty", "difficulty", "getQuizzesByLessonId", "lessonId", "getQuizzesByCategoryId", "categoryId", "createQuiz", "quiz", "questions", "(Lcom/bhashasetu/app/data/model/Quiz;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QuizViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.QuizRepository quizRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.UserProgressRepository userProgressRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Quiz>> allQuizzes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> completedQuizCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Float> averageQuizScore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bhashasetu.app.data.relation.QuizWithQuestions> _activeQuiz = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.relation.QuizWithQuestions> activeQuiz = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _currentQuestionIndex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> currentQuestionIndex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.Long, java.lang.String>> _userAnswers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.Long, java.lang.String>> userAnswers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _remainingTimeSeconds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> remainingTimeSeconds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bhashasetu.app.ui.viewmodel.QuizResult> _quizResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.ui.viewmodel.QuizResult> quizResult = null;
    
    @javax.inject.Inject()
    public QuizViewModel(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.QuizRepository quizRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.UserProgressRepository userProgressRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getAllQuizzes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getCompletedQuizCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Float> getAverageQuizScore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.relation.QuizWithQuestions> getActiveQuiz() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getCurrentQuestionIndex() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.Long, java.lang.String>> getUserAnswers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getRemainingTimeSeconds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.ui.viewmodel.QuizResult> getQuizResult() {
        return null;
    }
    
    /**
     * Starts a quiz
     * @param quizId The ID of the quiz to start
     */
    public final void startQuiz(long quizId) {
    }
    
    /**
     * Selects an answer for the current question
     * @param questionId The ID of the question
     * @param answer The user's answer
     */
    public final void selectAnswer(long questionId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer) {
    }
    
    /**
     * Moves to the next question
     * @return True if there is a next question, false otherwise
     */
    public final boolean nextQuestion() {
        return false;
    }
    
    /**
     * Moves to the previous question
     * @return True if there is a previous question, false otherwise
     */
    public final boolean previousQuestion() {
        return false;
    }
    
    /**
     * Submits the quiz for grading
     */
    public final void submitQuiz() {
    }
    
    /**
     * Checks if an answer is correct
     * @param question The question
     * @param userAnswer The user's answer
     * @return True if the answer is correct, false otherwise
     */
    private final boolean isAnswerCorrect(com.bhashasetu.app.data.model.QuizQuestion question, java.lang.String userAnswer) {
        return false;
    }
    
    /**
     * Calculates the completion time for a quiz
     * @param timeLimit The time limit of the quiz in seconds
     * @return The completion time in seconds
     */
    private final int calculateCompletionTime(java.lang.Integer timeLimit) {
        return 0;
    }
    
    /**
     * Gets a specific quiz by ID with its questions
     * @param quizId The ID of the quiz to retrieve
     * @return Flow of QuizWithQuestions for the specified quiz
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.QuizWithQuestions> getQuizById(long quizId) {
        return null;
    }
    
    /**
     * Gets quizzes by type
     * @param quizType The type of quizzes to retrieve
     * @return Flow of Quiz list for the specified type
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByType(@org.jetbrains.annotations.NotNull()
    java.lang.String quizType) {
        return null;
    }
    
    /**
     * Gets quizzes by difficulty
     * @param difficulty The difficulty level of quizzes to retrieve
     * @return Flow of Quiz list for the specified difficulty
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByDifficulty(int difficulty) {
        return null;
    }
    
    /**
     * Gets quizzes by lesson ID
     * @param lessonId The ID of the lesson
     * @return Flow of Quiz list for the specified lesson
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByLessonId(long lessonId) {
        return null;
    }
    
    /**
     * Gets quizzes by category ID
     * @param categoryId The ID of the category
     * @return Flow of Quiz list for the specified category
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByCategoryId(long categoryId) {
        return null;
    }
    
    /**
     * Creates a new quiz
     * @param quiz The quiz to create
     * @param questions List of questions for the quiz
     * @return The ID of the newly created quiz
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createQuiz(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.QuizQuestion> questions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
}