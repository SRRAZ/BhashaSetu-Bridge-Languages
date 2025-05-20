package com.example.englishhindi.games;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.englishhindi.R;
import com.example.englishhindi.audio.AudioFeedbackManager;
import com.example.englishhindi.audio.AudioManager;
import com.example.englishhindi.model.PracticeSession;
import com.example.englishhindi.model.UserProgress;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.view.AudioPronunciationView;
import com.example.englishhindi.view.BilingualTextView;
import com.example.englishhindi.viewmodel.PracticeSessionViewModel;
import com.example.englishhindi.viewmodel.UserProgressViewModel;
import com.example.englishhindi.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity for the Flashcard Game.
 * Players test their memory by flipping cards and answering questions about words.
 */
public class FlashcardGameActivity extends AppCompatActivity {

    private static final int MAX_CARDS = 10;
    private static final int POINTS_CORRECT = 10;
    private static final int POINTS_INCORRECT = -5;

    // Game modes
    public enum GameMode {
        STUDY,      // Just flip and study
        QUIZ,       // Show one side, ask to recall the other
        SPELLING    // Show one side, ask to spell the other
    }

    // UI components
    private TextView textViewTitle;
    private TextView textViewScore;
    private TextView textViewProgress;
    private CardView cardViewFront;
    private CardView cardViewBack;
    private TextView textViewFrontTitle;
    private TextView textViewBackTitle;
    private TextView textViewFrontWord;
    private TextView textViewBackWord;
    private AudioPronunciationView audioPronunciation;
    private ConstraintLayout layoutControls;
    private Button buttonFlip;
    private Button buttonKnow;
    private Button buttonDontKnow;
    private Button buttonNext;
    private ConstraintLayout layoutQuiz;
    private TextView textViewQuestion;
    private Button[] answerButtons;
    private ConstraintLayout layoutSpelling;
    private TextView textViewSpellingQuestion;
    private TextView textViewSpellingAnswer;
    private Button buttonCheck;
    private CardView cardViewGameOver;
    private TextView textViewFinalScore;
    private TextView textViewCorrectAnswers;
    private Button buttonPlayAgain;
    private Button buttonExit;

    // Game state
    private List<Word> gameWords;
    private int currentCardIndex = 0;
    private int score = 0;
    private int correctAnswers = 0;
    private int totalAnswers = 0;
    private GameMode gameMode = GameMode.STUDY;
    private boolean cardFlipped = false;
    private boolean gameOver = false;
    private boolean answerShown = false;

    // Card animation
    private AnimatorSet frontAnim;
    private AnimatorSet backAnim;
    private float scale;
    private boolean isFlipping = false;

    // ViewModels
    private WordViewModel wordViewModel;
    private UserProgressViewModel userProgressViewModel;
    private PracticeSessionViewModel sessionViewModel;

    // Game session
    private PracticeSession gameSession;

    // Audio
    private AudioManager audioManager;
    private AudioFeedbackManager audioFeedbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_game);

        // Set up action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.flashcards);
        }

        // Initialize ViewModels
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);

        // Initialize audio managers
        audioManager = AudioManager.getInstance(this);
        audioFeedbackManager = AudioFeedbackManager.getInstance(this);

        // Find views
        findViews();

        // Initialize card animations
        initializeCardAnimations();

        // Get game parameters from Intent
        int difficulty = getIntent().getIntExtra("difficulty", 1);
        String category = getIntent().getStringExtra("category");
        
        // Get game mode
        String modeStr = getIntent().getStringExtra("mode");
        if (modeStr != null) {
            try {
                gameMode = GameMode.valueOf(modeStr);
            } catch (IllegalArgumentException e) {
                gameMode = GameMode.STUDY;
            }
        }

        // Create game session
        gameSession = sessionViewModel.createGameSession(1, "flashcard_" + gameMode.name().toLowerCase());
        gameSession.setDifficulty(difficulty);
        sessionViewModel.insert(gameSession);

        // Set title based on game mode
        switch (gameMode) {
            case STUDY:
                textViewTitle.setText(R.string.study_flashcards);
                break;
            case QUIZ:
                textViewTitle.setText(R.string.flashcard_quiz);
                break;
            case SPELLING:
                textViewTitle.setText(R.string.spelling_challenge);
                break;
        }

        // Load game words
        loadGameWords(difficulty, category);

        // Set up click listeners
        setupClickListeners();

        // Update UI
        updateScoreDisplay();
    }

    /**
     * Find all views from the layout.
     */
    private void findViews() {
        textViewTitle = findViewById(R.id.text_view_title);
        textViewScore = findViewById(R.id.text_view_score);
        textViewProgress = findViewById(R.id.text_view_progress);
        cardViewFront = findViewById(R.id.card_view_front);
        cardViewBack = findViewById(R.id.card_view_back);
        textViewFrontTitle = findViewById(R.id.text_view_front_title);
        textViewBackTitle = findViewById(R.id.text_view_back_title);
        textViewFrontWord = findViewById(R.id.text_view_front_word);
        textViewBackWord = findViewById(R.id.text_view_back_word);
        audioPronunciation = findViewById(R.id.audio_pronunciation);
        layoutControls = findViewById(R.id.layout_controls);
        buttonFlip = findViewById(R.id.button_flip);
        buttonKnow = findViewById(R.id.button_know);
        buttonDontKnow = findViewById(R.id.button_dont_know);
        buttonNext = findViewById(R.id.button_next);
        layoutQuiz = findViewById(R.id.layout_quiz);
        textViewQuestion = findViewById(R.id.text_view_question);
        layoutSpelling = findViewById(R.id.layout_spelling);
        textViewSpellingQuestion = findViewById(R.id.text_view_spelling_question);
        textViewSpellingAnswer = findViewById(R.id.text_view_spelling_answer);
        buttonCheck = findViewById(R.id.button_check);
        cardViewGameOver = findViewById(R.id.card_view_game_over);
        textViewFinalScore = findViewById(R.id.text_view_final_score);
        textViewCorrectAnswers = findViewById(R.id.text_view_correct_answers);
        buttonPlayAgain = findViewById(R.id.button_play_again);
        buttonExit = findViewById(R.id.button_exit);

        // Initialize answer buttons
        answerButtons = new Button[4];
        answerButtons[0] = findViewById(R.id.button_answer_1);
        answerButtons[1] = findViewById(R.id.button_answer_2);
        answerButtons[2] = findViewById(R.id.button_answer_3);
        answerButtons[3] = findViewById(R.id.button_answer_4);

        // Initially hide quiz and spelling layouts
        layoutQuiz.setVisibility(View.GONE);
        layoutSpelling.setVisibility(View.GONE);
        cardViewGameOver.setVisibility(View.GONE);
    }

    /**
     * Initialize card flip animations.
     */
    private void initializeCardAnimations() {
        scale = getResources().getDisplayMetrics().density;
        cardViewFront.setCameraDistance(8000 * scale);
        cardViewBack.setCameraDistance(8000 * scale);

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_card_flip);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_card_flip);
    }

    /**
     * Set up click listeners for buttons.
     */
    private void setupClickListeners() {
        // Flip button
        buttonFlip.setOnClickListener(v -> flipCard());
        
        // Answer buttons
        buttonKnow.setOnClickListener(v -> handleKnowAnswer(true));
        buttonDontKnow.setOnClickListener(v -> handleKnowAnswer(false));
        buttonNext.setOnClickListener(v -> showNextCard());
        
        // Quiz answer buttons
        for (int i = 0; i < answerButtons.length; i++) {
            final int answerIndex = i;
            answerButtons[i].setOnClickListener(v -> checkQuizAnswer(answerIndex));
        }
        
        // Spelling check button
        buttonCheck.setOnClickListener(v -> checkSpellingAnswer());
        
        // Game over buttons
        buttonPlayAgain.setOnClickListener(v -> {
            // Restart the game
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        
        buttonExit.setOnClickListener(v -> finish());
    }

    /**
     * Load game words based on difficulty and category.
     *
     * @param difficulty The difficulty level
     * @param category   The word category
     */
    private void loadGameWords(int difficulty, String category) {
        if (category != null && !category.isEmpty()) {
            // Load words from specific category
            wordViewModel.getWordsByCategory(category).observe(this, words -> {
                filterAndPrepareWords(words, difficulty);
            });
        } else {
            // Load words by difficulty
            wordViewModel.getWordsByDifficulty(difficulty).observe(this, words -> {
                filterAndPrepareWords(words, difficulty);
            });
        }
    }

    /**
     * Filter and prepare words for the game.
     *
     * @param words      The list of words
     * @param difficulty The difficulty level
     */
    private void filterAndPrepareWords(List<Word> words, int difficulty) {
        if (words == null || words.isEmpty()) {
            Toast.makeText(this, R.string.no_words_criteria, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Filter by difficulty
        List<Word> filteredWords = new ArrayList<>();
        for (Word word : words) {
            if (word.getDifficulty() <= difficulty) {
                filteredWords.add(word);
            }
        }

        if (filteredWords.isEmpty()) {
            Toast.makeText(this, R.string.no_words_criteria, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Shuffle and limit
        Collections.shuffle(filteredWords);
        gameWords = filteredWords.subList(0, Math.min(MAX_CARDS, filteredWords.size()));

        // Set up the session
        gameSession.setTotalItems(gameWords.size());
        sessionViewModel.update(gameSession);

        // Show the first card
        showCurrentCard();
    }

    /**
     * Show the current flashcard.
     */
    private void showCurrentCard() {
        if (currentCardIndex >= gameWords.size()) {
            endGame();
            return;
        }

        // Get current word
        Word currentWord = gameWords.get(currentCardIndex);

        // Update progress
        String progress = (currentCardIndex + 1) + "/" + gameWords.size();
        textViewProgress.setText(progress);

        // Update session state
        gameSession.setCurrentIndex(currentCardIndex);
        sessionViewModel.update(gameSession);

        // Reset card state
        cardFlipped = false;
        answerShown = false;

        // Set card content
        textViewFrontWord.setText(currentWord.getEnglishWord());
        textViewBackWord.setText(currentWord.getHindiWord());

        // Set audio pronunciation
        audioPronunciation.setWordText(currentWord.getEnglishWord());
        audioPronunciation.setWordId(currentWord.getId());

        // Reset card visibility
        cardViewFront.setVisibility(View.VISIBLE);
        cardViewBack.setVisibility(View.GONE);

        // Show appropriate controls based on game mode
        showControls();
    }

    /**
     * Show the appropriate controls based on game mode.
     */
    private void showControls() {
        // Hide all control layouts first
        layoutControls.setVisibility(View.GONE);
        layoutQuiz.setVisibility(View.GONE);
        layoutSpelling.setVisibility(View.GONE);

        switch (gameMode) {
            case STUDY:
                layoutControls.setVisibility(View.VISIBLE);
                buttonFlip.setVisibility(View.VISIBLE);
                buttonKnow.setVisibility(View.GONE);
                buttonDontKnow.setVisibility(View.GONE);
                buttonNext.setVisibility(View.VISIBLE);
                break;

            case QUIZ:
                setupQuiz();
                layoutQuiz.setVisibility(View.VISIBLE);
                break;

            case SPELLING:
                setupSpelling();
                layoutSpelling.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Set up a quiz question with multiple-choice answers.
     */
    private void setupQuiz() {
        Word currentWord = gameWords.get(currentCardIndex);
        
        // Decide whether to quiz English->Hindi or Hindi->English
        boolean quizHindi = Math.random() > 0.5;
        
        if (quizHindi) {
            // Question: What is the Hindi word for X?
            textViewQuestion.setText(getString(R.string.what_is_hindi_for, currentWord.getEnglishWord()));
            textViewFrontTitle.setText(R.string.english);
            textViewBackTitle.setText(R.string.hindi);
        } else {
            // Question: What is the English word for X?
            textViewQuestion.setText(getString(R.string.what_is_english_for, currentWord.getHindiWord()));
            textViewFrontTitle.setText(R.string.hindi);
            textViewBackTitle.setText(R.string.english);
        }
        
        // Generate answer options (1 correct, 3 wrong)
        List<String> options = new ArrayList<>();
        String correctAnswer = quizHindi ? currentWord.getHindiWord() : currentWord.getEnglishWord();
        options.add(correctAnswer);
        
        // Add incorrect options
        List<String> incorrectOptions = generateIncorrectOptions(currentWord, quizHindi);
        options.addAll(incorrectOptions);
        
        // Shuffle options
        Collections.shuffle(options);
        
        // Set button text
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(options.get(i));
            answerButtons[i].setBackgroundResource(R.drawable.button_option_normal);
            answerButtons[i].setEnabled(true);
        }
    }

    /**
     * Generate incorrect options for a quiz question.
     *
     * @param currentWord The current word
     * @param forHindi    Whether to generate Hindi options
     * @return List of incorrect options
     */
    private List<String> generateIncorrectOptions(Word currentWord, boolean forHindi) {
        List<String> options = new ArrayList<>();
        
        // Get some random words from the game words to use as incorrect options
        List<Word> possibleOptions = new ArrayList<>(gameWords);
        possibleOptions.remove(currentWord);
        Collections.shuffle(possibleOptions);
        
        // Add incorrect options
        for (Word word : possibleOptions) {
            String option = forHindi ? word.getHindiWord() : word.getEnglishWord();
            if (!options.contains(option)) {
                options.add(option);
                if (options.size() >= 3) { // We need 3 wrong answers
                    break;
                }
            }
        }
        
        // If we don't have enough options, add some dummy ones
        while (options.size() < 3) {
            String dummyOption = forHindi ? "विकल्प " + options.size() : "Option " + options.size();
            options.add(dummyOption);
        }
        
        return options;
    }

    /**
     * Set up a spelling challenge.
     */
    private void setupSpelling() {
        Word currentWord = gameWords.get(currentCardIndex);
        
        // Decide whether to quiz English->Hindi spelling or Hindi->English spelling
        boolean spellInHindi = Math.random() > 0.5;
        
        if (spellInHindi) {
            // English word shown, spell in Hindi
            textViewFrontWord.setText(currentWord.getEnglishWord());
            textViewSpellingQuestion.setText(getString(R.string.spell_hindi_for, currentWord.getEnglishWord()));
            textViewFrontTitle.setText(R.string.english);
            textViewBackTitle.setText(R.string.hindi);
        } else {
            // Hindi word shown, spell in English
            textViewFrontWord.setText(currentWord.getHindiWord());
            textViewSpellingQuestion.setText(getString(R.string.spell_english_for, currentWord.getHindiWord()));
            textViewFrontTitle.setText(R.string.hindi);
            textViewBackTitle.setText(R.string.english);
        }
        
        // Clear previous answer
        textViewSpellingAnswer.setText("");
        buttonCheck.setEnabled(true);
    }

    /**
     * Flip the card to show the other side.
     */
    private void flipCard() {
        if (isFlipping) {
            return;
        }
        
        isFlipping = true;
        audioFeedbackManager.playTapSound();
        
        if (!cardFlipped) {
            // Flip to back
            frontAnim.setTarget(cardViewFront);
            backAnim.setTarget(cardViewBack);
            frontAnim.start();
            backAnim.start();
            
            cardViewBack.setVisibility(View.VISIBLE);
            
            frontAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}
                
                @Override
                public void onAnimationEnd(Animator animation) {
                    cardViewFront.setVisibility(View.GONE);
                    isFlipping = false;
                    cardFlipped = true;
                    
                    // Update UI based on game mode
                    if (gameMode == GameMode.STUDY) {
                        // Just show the next button
                        buttonFlip.setVisibility(View.VISIBLE);
                    } else {
                        // Show self-assessment buttons
                        buttonFlip.setVisibility(View.GONE);
                        buttonKnow.setVisibility(View.VISIBLE);
                        buttonDontKnow.setVisibility(View.VISIBLE);
                        buttonNext.setVisibility(View.GONE);
                    }
                }
                
                @Override
                public void onAnimationCancel(Animator animation) {}
                
                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
        } else {
            // Flip to front
            frontAnim.setTarget(cardViewBack);
            backAnim.setTarget(cardViewFront);
            backAnim.start();
            frontAnim.start();
            
            cardViewFront.setVisibility(View.VISIBLE);
            
            frontAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}
                
                @Override
                public void onAnimationEnd(Animator animation) {
                    cardViewBack.setVisibility(View.GONE);
                    isFlipping = false;
                    cardFlipped = false;
                }
                
                @Override
                public void onAnimationCancel(Animator animation) {}
                
                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
        }
    }

    /**
     * Handle "I know"/"I don't know" self-assessment buttons.
     *
     * @param knows Whether the user knows the answer
     */
    private void handleKnowAnswer(boolean knows) {
        totalAnswers++;
        
        if (knows) {
            // User knows the answer
            score += POINTS_CORRECT;
            correctAnswers++;
            audioFeedbackManager.playCorrectSound();
            
            // Update session
            gameSession.recordAnswer(true);
            
            // Update user progress
            updateUserProgress(gameWords.get(currentCardIndex).getId(), true);
        } else {
            // User doesn't know the answer
            audioFeedbackManager.playIncorrectSound();
            
            // Update session
            gameSession.recordAnswer(false);
            
            // Update user progress
            updateUserProgress(gameWords.get(currentCardIndex).getId(), false);
        }
        
        // Update score display
        updateScoreDisplay();
        
        // Update session
        sessionViewModel.update(gameSession);
        
        // Show next button
        buttonKnow.setVisibility(View.GONE);
        buttonDontKnow.setVisibility(View.GONE);
        buttonNext.setVisibility(View.VISIBLE);
    }

    /**
     * Check the selected quiz answer.
     *
     * @param selectedIndex The index of the selected answer
     */
    private void checkQuizAnswer(int selectedIndex) {
        if (answerShown) {
            return;
        }
        
        answerShown = true;
        totalAnswers++;
        
        // Determine if the answer is correct
        Word currentWord = gameWords.get(currentCardIndex);
        String selectedAnswer = answerButtons[selectedIndex].getText().toString();
        
        // Get the correct answer based on the question type
        boolean quizHindi = textViewQuestion.getText().toString().contains("Hindi");
        String correctAnswer = quizHindi ? currentWord.getHindiWord() : currentWord.getEnglishWord();
        
        boolean isCorrect = selectedAnswer.equals(correctAnswer);
        
        // Update score and record result
        if (isCorrect) {
            score += POINTS_CORRECT;
            correctAnswers++;
            audioFeedbackManager.playCorrectSound();
            
            // Update session
            gameSession.recordAnswer(true);
            
            // Highlight correct answer
            answerButtons[selectedIndex].setBackgroundResource(R.drawable.button_option_correct);
            
            // Update user progress
            updateUserProgress(currentWord.getId(), true);
        } else {
            score = Math.max(0, score + POINTS_INCORRECT);
            audioFeedbackManager.playIncorrectSound();
            
            // Update session
            gameSession.recordAnswer(false);
            
            // Highlight incorrect and correct answers
            answerButtons[selectedIndex].setBackgroundResource(R.drawable.button_option_incorrect);
            
            // Find and highlight the correct answer
            for (int i = 0; i < answerButtons.length; i++) {
                if (answerButtons[i].getText().toString().equals(correctAnswer)) {
                    answerButtons[i].setBackgroundResource(R.drawable.button_option_correct);
                    break;
                }
            }
            
            // Update user progress
            updateUserProgress(currentWord.getId(), false);
        }
        
        // Update score display
        updateScoreDisplay();
        
        // Update session
        sessionViewModel.update(gameSession);
        
        // Disable all answer buttons
        for (Button button : answerButtons) {
            button.setEnabled(false);
        }
        
        // Show next button after a short delay
        new Handler().postDelayed(() -> {
            showNextCard();
        }, 1500);
    }

    /**
     * Check the spelling answer.
     */
    private void checkSpellingAnswer() {
        if (answerShown) {
            return;
        }
        
        answerShown = true;
        totalAnswers++;
        
        // Get the user's answer
        String userAnswer = textViewSpellingAnswer.getText().toString().trim();
        
        // Determine if the answer is correct
        Word currentWord = gameWords.get(currentCardIndex);
        
        // Get the correct answer based on the question type
        boolean spellInHindi = textViewSpellingQuestion.getText().toString().contains("Hindi");
        String correctAnswer = spellInHindi ? currentWord.getHindiWord() : currentWord.getEnglishWord();
        
        boolean isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
        
        // Update score and record result
        if (isCorrect) {
            score += POINTS_CORRECT;
            correctAnswers++;
            audioFeedbackManager.playCorrectSound();
            
            // Update session
            gameSession.recordAnswer(true);
            
            // Show success feedback
            textViewSpellingAnswer.setTextColor(getResources().getColor(R.color.colorCorrect));
            
            // Update user progress
            updateUserProgress(currentWord.getId(), true);
        } else {
            score = Math.max(0, score + POINTS_INCORRECT);
            audioFeedbackManager.playIncorrectSound();
            
            // Update session
            gameSession.recordAnswer(false);
            
            // Show error feedback
            textViewSpellingAnswer.setTextColor(getResources().getColor(R.color.colorIncorrect));
            
            // Show correct answer
            textViewSpellingAnswer.setText(userAnswer + " ✗\nCorrect: " + correctAnswer);
            
            // Update user progress
            updateUserProgress(currentWord.getId(), false);
        }
        
        // Update score display
        updateScoreDisplay();
        
        // Update session
        sessionViewModel.update(gameSession);
        
        // Disable check button
        buttonCheck.setEnabled(false);
        
        // Show next card after a delay
        new Handler().postDelayed(() -> {
            showNextCard();
        }, 2000);
    }

    /**
     * Update the user progress for the current word.
     *
     * @param wordId  The word ID
     * @param correct Whether the answer was correct
     */
    private void updateUserProgress(int wordId, boolean correct) {
        userProgressViewModel.getWordProgress(1, wordId).observe(this, progress -> {
            if (progress != null) {
                userProgressViewModel.recordAttemptResult(progress, correct);
            } else {
                // Create a new progress entry if none exists
                UserProgress newProgress = new UserProgress(1, "word", wordId);
                newProgress.updateSpacedRepetition(correct);
                userProgressViewModel.insert(newProgress);
            }
        });
    }

    /**
     * Update the score display.
     */
    private void updateScoreDisplay() {
        textViewScore.setText(String.valueOf(score));
    }

    /**
     * Show the next card.
     */
    private void showNextCard() {
        currentCardIndex++;
        
        // If we're at the end of the cards, show game over
        if (currentCardIndex >= gameWords.size()) {
            endGame();
        } else {
            // Reset the card state and show the next card
            cardFlipped = false;
            answerShown = false;
            
            // Reset UI components
            textViewSpellingAnswer.setText("");
            textViewSpellingAnswer.setTextColor(getResources().getColor(android.R.color.black));
            
            // Show the next card
            showCurrentCard();
        }
    }

    /**
     * End the game and show results.
     */
    private void endGame() {
        gameOver = true;
        
        // Complete the session
        gameSession.setCompleted(true);
        gameSession.setScore(score);
        sessionViewModel.completeSession(gameSession);
        
        // Show game over card
        cardViewGameOver.setVisibility(View.VISIBLE);
        
        // Set final score and stats
        textViewFinalScore.setText(String.valueOf(score));
        
        String correctAnswersText = getString(R.string.correct_answers_format, 
                correctAnswers, totalAnswers, 
                totalAnswers > 0 ? (correctAnswers * 100 / totalAnswers) : 0);
        textViewCorrectAnswers.setText(correctAnswersText);
        
        // Animate card appearance
        cardViewGameOver.setAlpha(0f);
        cardViewGameOver.setScaleX(0.8f);
        cardViewGameOver.setScaleY(0.8f);
        
        cardViewGameOver.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!gameOver && currentCardIndex > 0) {
            // Show confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.exit_game_title);
            builder.setMessage(R.string.exit_game_message);
            builder.setPositiveButton(R.string.exit, (dialog, which) -> finish());
            builder.setNegativeButton(R.string.cancel, null);
            builder.show();
        } else {
            super.onBackPressed();
        }
    }
}