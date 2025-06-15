package com.bhashasetu.app.games;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.bhashasetu.app.R;
import com.bhashasetu.app.audio.AppAudioManager;
import com.bhashasetu.app.audio.AudioFeedbackManager;
import com.bhashasetu.app.model.PracticeSession;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.view.AudioPronunciationView;
import com.bhashasetu.app.view.HindiTextView;
import com.bhashasetu.app.viewmodel.PracticeSessionViewModel;
import com.bhashasetu.app.viewmodel.UserProgressViewModel;
import com.bhashasetu.app.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Activity for the Word Scramble game.
 * Players unscramble letters to form English words based on Hindi clues.
 */
public class WordScrambleActivity extends AppCompatActivity {

    private static final int DEFAULT_GAME_TIME = 60; // seconds
    private static final int MAX_QUESTIONS = 10;
    private static final int POINTS_FOR_CORRECT = 10;
    private static final int POINTS_FOR_HINT = 5;
    
    // UI Components
    private TextView textViewScore;
    private TextView textViewTimer;
    private ProgressBar progressBarTimer;
    private HindiTextView textViewHindiWord;
    private AudioPronunciationView audioPronunciation;
    private LinearLayout layoutLetterTiles;
    private LinearLayout layoutAnswerTiles;
    private Button buttonCheck;
    private Button buttonHint;
    private Button buttonClear;
    private TextView textViewFeedback;
    private CardView cardViewResult;
    private ConstraintLayout layoutSuccess;
    private ConstraintLayout layoutFail;
    
    // Game state
    private List<Word> gameWords;
    private Word currentWord;
    private int currentWordIndex = 0;
    private int score = 0;
    private int hintsUsed = 0;
    private CountDownTimer timer;
    private int secondsRemaining = DEFAULT_GAME_TIME;
    private boolean gameEnded = false;
    
    // Letter tiles
    private List<LetterTile> sourceTiles = new ArrayList<>();
    private List<LetterTile> answerTiles = new ArrayList<>();
    
    // ViewModels
    private WordViewModel wordViewModel;
    private UserProgressViewModel userProgressViewModel;
    private PracticeSessionViewModel sessionViewModel;
    
    // Audio
    private AppAudioManager audioManager;
    private AudioFeedbackManager audioFeedbackManager;
    
    // Game session
    private PracticeSession gameSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_scramble);
        
        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.word_scramble);
        }
        
        // Initialize ViewModels
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);
        
        // Get AppAudioManager instance
        audioManager = AppAudioManager.getInstance(this);
        audioFeedbackManager = AudioFeedbackManager.getInstance(this);
        
        // Initialize UI components
        textViewScore = findViewById(R.id.text_view_score);
        textViewTimer = findViewById(R.id.text_view_timer);
        progressBarTimer = findViewById(R.id.progress_bar_timer);
        textViewHindiWord = findViewById(R.id.text_view_hindi_word);
        audioPronunciation = findViewById(R.id.audio_pronunciation);
        layoutLetterTiles = findViewById(R.id.layout_letter_tiles);
        layoutAnswerTiles = findViewById(R.id.layout_answer_tiles);
        buttonCheck = findViewById(R.id.button_check);
        buttonHint = findViewById(R.id.button_hint);
        buttonClear = findViewById(R.id.button_clear);
        textViewFeedback = findViewById(R.id.text_view_feedback);
        cardViewResult = findViewById(R.id.card_view_result);
        layoutSuccess = findViewById(R.id.layout_success);
        layoutFail = findViewById(R.id.layout_fail);
        
        // Get difficulty and category from Intent
        int difficulty = getIntent().getIntExtra("difficulty", 1);
        String category = getIntent().getStringExtra("category");
        
        // Create a new game session
        gameSession = sessionViewModel.createGameSession(1, "word_scramble");
        gameSession.setDifficulty(difficulty);
        sessionViewModel.insert(gameSession);
        
        // Load words for the game
        loadGameWords(difficulty, category);
        
        // Set up click listeners
        buttonCheck.setOnClickListener(v -> checkAnswer());
        buttonHint.setOnClickListener(v -> useHint());
        buttonClear.setOnClickListener(v -> clearAnswer());
        
        // Set up completion buttons
        Button buttonPlayAgain = findViewById(R.id.button_play_again);
        Button buttonExit = findViewById(R.id.button_exit);
        
        buttonPlayAgain.setOnClickListener(v -> {
            // Start a new game
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        
        buttonExit.setOnClickListener(v -> {
            // Exit the game
            finish();
        });
        
        // Update score display
        updateScoreDisplay();
    }
    
    /**
     * Load words for the game based on difficulty and category.
     * 
     * @param difficulty The difficulty level
     * @param category The category (optional)
     */
    private void loadGameWords(int difficulty, String category) {
        if (category != null && !category.isEmpty()) {
            // Load words from the specified category
            wordViewModel.getWordsByCategory(category).observe(this, words -> {
                filterAndStartGame(words, difficulty);
            });
        } else {
            // Load random words by difficulty
            wordViewModel.getRandomWordsByDifficulty(difficulty, MAX_QUESTIONS * 2).observe(this, words -> {
                filterAndStartGame(words, difficulty);
            });
        }
    }
    
    /**
     * Filter words by difficulty and start the game.
     * 
     * @param words The list of words
     * @param difficulty The difficulty level
     */
    private void filterAndStartGame(List<Word> words, int difficulty) {
        if (words == null || words.isEmpty()) {
            Toast.makeText(this, R.string.no_words_criteria, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        // Filter words by difficulty and length
        gameWords = new ArrayList<>();
        for (Word word : words) {
            if (word.getDifficulty() <= difficulty &&
                    word.getEnglishWord().length() <= 8 && // Don't use very long words
                    word.getEnglishWord().length() >= 3 && // Don't use very short words
                    gameWords.size() < MAX_QUESTIONS) {
                gameWords.add(word);
            }
        }
        
        // Shuffle the words
        Collections.shuffle(gameWords);
        
        // Limit to MAX_QUESTIONS
        if (gameWords.size() > MAX_QUESTIONS) {
            gameWords = gameWords.subList(0, MAX_QUESTIONS);
        }
        
        // Set total items in session
        gameSession.setTotalItems(gameWords.size());
        sessionViewModel.update(gameSession);
        
        // Start the first question
        if (!gameWords.isEmpty()) {
            startGame();
        } else {
            Toast.makeText(this, R.string.no_words_criteria, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    /**
     * Start the game.
     */
    private void startGame() {
        // Show first word
        currentWordIndex = 0;
        showCurrentWord();
        
        // Start timer
        startTimer();
    }
    
    /**
     * Start the countdown timer.
     */
    private void startTimer() {
        // Cancel any existing timer
        if (timer != null) {
            timer.cancel();
        }
        
        // Set initial values
        secondsRemaining = DEFAULT_GAME_TIME;
        progressBarTimer.setMax(DEFAULT_GAME_TIME);
        progressBarTimer.setProgress(DEFAULT_GAME_TIME);
        textViewTimer.setText(formatTime(secondsRemaining));
        
        // Create new timer
        timer = new CountDownTimer(DEFAULT_GAME_TIME * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsRemaining = (int) (millisUntilFinished / 1000);
                textViewTimer.setText(formatTime(secondsRemaining));
                progressBarTimer.setProgress(secondsRemaining);
            }
            
            @Override
            public void onFinish() {
                secondsRemaining = 0;
                textViewTimer.setText(formatTime(0));
                progressBarTimer.setProgress(0);
                
                // End game if not already ended
                if (!gameEnded) {
                    endGame(false);
                }
            }
        };
        
        // Start the timer
        timer.start();
    }
    
    /**
     * Format the time as MM:SS.
     * 
     * @param seconds The time in seconds
     * @return The formatted time string
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
    }
    
    /**
     * Show the current word.
     */
    private void showCurrentWord() {
        if (currentWordIndex >= gameWords.size()) {
            // End of game
            endGame(true);
            return;
        }
        
        // Get current word
        currentWord = gameWords.get(currentWordIndex);
        
        // Show Hindi word
        textViewHindiWord.setText(currentWord.getHindiWord());
        
        // Set up audio pronunciation
        audioPronunciation.setWordText(currentWord.getEnglishWord());
        audioPronunciation.setWordId(currentWord.getId());
        
        // Create letter tiles
        createLetterTiles(currentWord.getEnglishWord());
        
        // Clear answer tiles
        layoutAnswerTiles.removeAllViews();
        answerTiles.clear();
        
        // Clear feedback
        textViewFeedback.setText("");
        textViewFeedback.setVisibility(View.INVISIBLE);
        
        // Update game session
        gameSession.setCurrentIndex(currentWordIndex);
        sessionViewModel.update(gameSession);
    }
    
    /**
     * Create letter tiles for the scrambled word.
     * 
     * @param word The word to scramble
     */
    private void createLetterTiles(String word) {
        // Clear existing tiles
        layoutLetterTiles.removeAllViews();
        sourceTiles.clear();
        
        // Convert word to uppercase
        word = word.toUpperCase();
        
        // Create character array and shuffle
        char[] letters = word.toCharArray();
        List<Character> letterList = new ArrayList<>();
        for (char c : letters) {
            letterList.add(c);
        }
        Collections.shuffle(letterList);
        
        // Create tiles for each letter
        for (char letter : letterList) {
            // Skip non-letter characters
            if (!Character.isLetter(letter)) {
                continue;
            }
            
            // Create tile
            LetterTile tile = new LetterTile(this, letter);
            sourceTiles.add(tile);
            
            // Add to source layout
            layoutLetterTiles.addView(tile);
            
            // Set click listener
            tile.setOnClickListener(v -> {
                if (gameEnded) return;
                
                // Move tile from source to answer
                if (tile.getParent() == layoutLetterTiles) {
                    moveLetterToAnswer(tile);
                } else if (tile.getParent() == layoutAnswerTiles) {
                    moveLetterToSource(tile);
                }
            });
        }
    }
    
    /**
     * Move a letter tile from source to answer.
     * 
     * @param tile The letter tile to move
     */
    private void moveLetterToAnswer(LetterTile tile) {
        // Remove from source layout
        layoutLetterTiles.removeView(tile);
        
        // Add to answer layout
        layoutAnswerTiles.addView(tile);
        
        // Add to answer tiles list
        answerTiles.add(tile);
        
        // Play click sound
        playClickSound();
    }
    
    /**
     * Move a letter tile from answer to source.
     * 
     * @param tile The letter tile to move
     */
    private void moveLetterToSource(LetterTile tile) {
        // Remove from answer layout
        layoutAnswerTiles.removeView(tile);
        
        // Add to source layout
        layoutLetterTiles.addView(tile);
        
        // Remove from answer tiles list
        answerTiles.remove(tile);
        
        // Play click sound
        playClickSound();
    }
    
    /**
     * Play a click sound.
     */
    private void playClickSound() {
        AudioFeedbackManager audioFeedbackManager = AudioFeedbackManager.getInstance(this);
        audioFeedbackManager.playTapSound();
    }
    
    /**
     * Check the current answer.
     */
    private void checkAnswer() {
        if (gameEnded || answerTiles.isEmpty()) {
            return;
        }
        
        // Build answer string
        StringBuilder answerBuilder = new StringBuilder();
        for (LetterTile tile : answerTiles) {
            answerBuilder.append(tile.getLetter());
        }
        String answer = answerBuilder.toString();
        
        // Check if correct
        boolean correct = answer.equalsIgnoreCase(currentWord.getEnglishWord());
        
        // Record answer in session
        gameSession.recordAnswer(correct);
        
        if (correct) {
            // Show success feedback
            textViewFeedback.setText(R.string.correct);
            textViewFeedback.setTextColor(getResources().getColor(R.color.colorCorrect));
            
            // Update score
            score += POINTS_FOR_CORRECT;
            updateScoreDisplay();
            
            // Update user progress
            updateUserProgress(currentWord.getId(), true);
            
            // Show next word after delay
            new Handler().postDelayed(() -> {
                currentWordIndex++;
                showCurrentWord();
            }, 1500);
            
            // Play success animation
            playSuccessAnimation();
            
        } else {
            // Show error feedback
            textViewFeedback.setText(R.string.incorrect);
            textViewFeedback.setTextColor(getResources().getColor(R.color.colorIncorrect));
            
            // Update user progress
            updateUserProgress(currentWord.getId(), false);
            
            // Play error animation
            playErrorAnimation();
        }
        
        textViewFeedback.setVisibility(View.VISIBLE);
    }
    
    /**
     * Use a hint to reveal one correct letter.
     */
    private void useHint() {
        if (gameEnded || currentWord == null) {
            return;
        }
        
        // Deduct points for using hint
        score = Math.max(0, score - POINTS_FOR_HINT);
        hintsUsed++;
        updateScoreDisplay();
        
        // Get the correct word
        String correctWord = currentWord.getEnglishWord().toUpperCase();
        
        // Check if there are any letters left to be placed
        if (answerTiles.size() >= correctWord.length()) {
            Toast.makeText(this, R.string.hint_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Get the current partial answer
        StringBuilder current = new StringBuilder();
        for (LetterTile tile : answerTiles) {
            current.append(tile.getLetter());
        }
        
        // Find the next correct letter
        int position = current.length();
        if (position >= correctWord.length()) {
            return;
        }
        
        char nextCorrectLetter = correctWord.charAt(position);
        
        // Find this letter in the source tiles
        for (LetterTile tile : sourceTiles) {
            if (tile.getLetter() == nextCorrectLetter && tile.getParent() == layoutLetterTiles) {
                // Move this tile to the answer
                moveLetterToAnswer(tile);
                break;
            }
        }
    }
    
    /**
     * Clear the current answer.
     */
    private void clearAnswer() {
        if (gameEnded) {
            return;
        }
        
        // Move all answer tiles back to source
        List<LetterTile> tilesToMove = new ArrayList<>(answerTiles);
        for (LetterTile tile : tilesToMove) {
            moveLetterToSource(tile);
        }
    }
    
    /**
     * Update the user progress for a word.
     * 
     * @param wordId The word ID
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
     * Play a success animation.
     */
    private void playSuccessAnimation() {
        // Create success animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textViewFeedback, "scaleX", 1f, 1.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textViewFeedback, "scaleY", 1f, 1.5f, 1f);
        
        scaleX.setDuration(500);
        scaleY.setDuration(500);
        
        scaleX.setInterpolator(new DecelerateInterpolator());
        scaleY.setInterpolator(new DecelerateInterpolator());
        
        scaleX.start();
        scaleY.start();
    }
    
    /**
     * Play an error animation.
     */
    private void playErrorAnimation() {
        // Create shake animation
        ObjectAnimator shake = ObjectAnimator.ofFloat(textViewFeedback, "translationX", 0, 10, -10, 10, -10, 5, -5, 0);
        shake.setDuration(500);
        shake.start();
    }
    
    /**
     * End the game.
     * 
     * @param success Whether the game was completed successfully
     */
    private void endGame(boolean success) {
        gameEnded = true;
        
        // Cancel timer
        if (timer != null) {
            timer.cancel();
        }
        
        // Complete the session
        gameSession.setCompleted(true);
        gameSession.setScore(score);
        sessionViewModel.completeSession(gameSession);
        
        // Show completion card
        cardViewResult.setVisibility(View.VISIBLE);
        
        // Show appropriate completion view
        if (success) {
            layoutSuccess.setVisibility(View.VISIBLE);
            layoutFail.setVisibility(View.GONE);
            
            // Set success text
            TextView textViewSuccessScore = findViewById(R.id.text_view_success_score);
            textViewSuccessScore.setText(String.valueOf(score));
            
            // Set success animation
            TextView textViewSuccessTitle = findViewById(R.id.text_view_success_title);
            ObjectAnimator titleAnim = ObjectAnimator.ofFloat(textViewSuccessTitle, "alpha", 0f, 1f);
            titleAnim.setDuration(1000);
            titleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            titleAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator.ofFloat(textViewSuccessScore, "alpha", 0f, 1f)
                            .setDuration(500)
                            .start();
                }
            });
            titleAnim.start();
        } else {
            layoutSuccess.setVisibility(View.GONE);
            layoutFail.setVisibility(View.VISIBLE);
            
            // Set failure text
            TextView textViewFailScore = findViewById(R.id.text_view_fail_score);
            textViewFailScore.setText(String.valueOf(score));
        }
        
        // Animate card appearance
        cardViewResult.setAlpha(0f);
        cardViewResult.setScaleX(0.8f);
        cardViewResult.setScaleY(0.8f);
        
        cardViewResult.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
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
        if (gameEnded) {
            super.onBackPressed();
        } else {
            // Show confirmation dialog
            showExitConfirmationDialog();
        }
    }
    
    /**
     * Show a confirmation dialog when trying to exit during a game.
     */
    private void showExitConfirmationDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.exit_game_title);
        builder.setMessage(R.string.exit_game_message);
        builder.setPositiveButton(R.string.exit, (dialog, which) -> {
            // End game and exit
            if (timer != null) {
                timer.cancel();
            }
            finish();
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
    
    /**
     * Letter tile view for the game.
     */
    private static class LetterTile extends androidx.appcompat.widget.AppCompatTextView {
        
        private char letter;
        
        public LetterTile(Context context, char letter) {
            super(context);
            this.letter = letter;
            
            // Set text
            setText(String.valueOf(letter));
            
            // Set layout parameters
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 8, 8, 8);
            setLayoutParams(params);
            
            // Set appearance
            setTextSize(22);
            setTextColor(context.getResources().getColor(android.R.color.white));
            setBackgroundResource(R.drawable.tile_background);
            setElevation(4);
            setGravity(android.view.Gravity.CENTER);
            setPadding(24, 16, 24, 16);
        }
        
        /**
         * Get the letter.
         * 
         * @return The letter
         */
        public char getLetter() {
            return letter;
        }
    }
}