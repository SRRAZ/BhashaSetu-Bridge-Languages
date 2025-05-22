package com.example.englishhindi.games;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.R;
import com.example.englishhindi.audio.AudioFeedbackManager;
import com.example.englishhindi.audio.AudioManager;
import com.example.englishhindi.model.PracticeSession;
import com.example.englishhindi.model.UserProgress;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.view.AudioPronunciationView;
import com.example.englishhindi.viewmodel.PracticeSessionViewModel;
import com.example.englishhindi.viewmodel.UserProgressViewModel;
import com.example.englishhindi.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Activity for the Word Association game.
 * Players connect related words in a grid to score points.
 */
public class WordAssociationActivity extends AppCompatActivity implements WordCardAdapter.OnWordCardClickListener {

    private static final int DEFAULT_GRID_SIZE = 4;  // 4x4 grid
    private static final int MAX_WORDS_PER_GAME = 8; // 8 pairs of related words
    private static final int POINTS_FOR_MATCH = 10;
    private static final int MAX_LIVES = 3;
    private static final int TIME_PER_GAME = 120; // 2 minutes

    // UI Components
    private TextView textViewScore;
    private TextView textViewLives;
    private TextView textViewTimer;
    private ProgressBar progressBarTimer;
    private RecyclerView recyclerViewGrid;
    private CardView cardViewSelectedWord;
    private TextView textViewSelectedWord;
    private AudioPronunciationView audioPronunciation;
    private CardView cardViewGameOver;
    private TextView textViewGameOverTitle;
    private TextView textViewFinalScore;
    private TextView textViewMatchesMade;
    private Button buttonPlayAgain;
    private Button buttonExit;

    // Game state
    private List<WordCard> wordCards;
    private WordCardAdapter cardAdapter;
    private WordCard selectedCard;
    private int score = 0;
    private int lives = MAX_LIVES;
    private int secondsRemaining = TIME_PER_GAME;
    private boolean gameOver = false;
    private int matchesMade = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;

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
        setContentView(R.layout.activity_word_association);

        // Set up action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.word_association);
        }

        // Initialize ViewModels
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);

        // Initialize audio
        audioManager = AudioManager.getInstance(this);
        audioFeedbackManager = AudioFeedbackManager.getInstance(this);

        // Find UI components
        initializeUI();

        // Get difficulty and category from intent
        int difficulty = getIntent().getIntExtra("difficulty", 1);
        String category = getIntent().getStringExtra("category");

        // Create a new game session
        gameSession = sessionViewModel.createGameSession(1, "word_association");
        gameSession.setDifficulty(difficulty);
        sessionViewModel.insert(gameSession);

        // Load words for the game
        loadGameWords(difficulty, category);

        // Set up click listeners
        setupClickListeners();

        // Update displays
        updateScoreDisplay();
        updateLivesDisplay();
    }

    /**
     * Initialize UI components.
     */
    private void initializeUI() {
        textViewScore = findViewById(R.id.text_view_score);
        textViewLives = findViewById(R.id.text_view_lives);
        textViewTimer = findViewById(R.id.text_view_timer);
        progressBarTimer = findViewById(R.id.progress_bar_timer);
        recyclerViewGrid = findViewById(R.id.recycler_view_grid);
        cardViewSelectedWord = findViewById(R.id.card_view_selected_word);
        textViewSelectedWord = findViewById(R.id.text_view_selected_word);
        audioPronunciation = findViewById(R.id.audio_pronunciation);
        cardViewGameOver = findViewById(R.id.card_view_game_over);
        textViewGameOverTitle = findViewById(R.id.text_view_game_over_title);
        textViewFinalScore = findViewById(R.id.text_view_final_score);
        textViewMatchesMade = findViewById(R.id.text_view_matches_made);
        buttonPlayAgain = findViewById(R.id.button_play_again);
        buttonExit = findViewById(R.id.button_exit);

        // Set up RecyclerView with GridLayoutManager
        recyclerViewGrid.setLayoutManager(new GridLayoutManager(this, DEFAULT_GRID_SIZE));

        // Initially hide selected word and game over card
        cardViewSelectedWord.setVisibility(View.INVISIBLE);
        cardViewGameOver.setVisibility(View.GONE);

        // Set up progress bar
        progressBarTimer.setMax(TIME_PER_GAME);
        progressBarTimer.setProgress(TIME_PER_GAME);
        textViewTimer.setText(formatTime(TIME_PER_GAME));
    }

    /**
     * Set up click listeners.
     */
    private void setupClickListeners() {
        buttonPlayAgain.setOnClickListener(v -> {
            // Restart the game
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

        buttonExit.setOnClickListener(v -> finish());
    }

    /**
     * Load words for the game based on difficulty and category.
     *
     * @param difficulty The difficulty level
     * @param category   The category (optional)
     */
    private void loadGameWords(int difficulty, String category) {
        if (category != null && !category.isEmpty()) {
            // Load words from specific category
            wordViewModel.getWordsByCategory(category).observe(this, words -> {
                if (words != null && !words.isEmpty()) {
                    prepareGameWords(words, difficulty);
                } else {
                    Toast.makeText(this, R.string.no_words_criteria, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            // Load random words by difficulty
            wordViewModel.getWordsByDifficulty(difficulty).observe(this, words -> {
                if (words != null && !words.isEmpty()) {
                    prepareGameWords(words, difficulty);
                } else {
                    Toast.makeText(this, R.string.no_words_criteria, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    /**
     * Prepare game words by finding pairs of related words.
     *
     * @param words      The list of words to choose from
     * @param difficulty The difficulty level
     */
    private void prepareGameWords(List<Word> words, int difficulty) {
        if (words.size() < MAX_WORDS_PER_GAME) {
            Toast.makeText(this, R.string.not_enough_words, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Group words by categories for finding related pairs
        Map<String, List<Word>> wordsByCategory = new HashMap<>();
        for (Word word : words) {
            String category = word.getCategory();
            if (category != null && !category.isEmpty()) {
                if (!wordsByCategory.containsKey(category)) {
                    wordsByCategory.put(category, new ArrayList<>());
                }
                wordsByCategory.get(category).add(word);
            }
        }

        // Find categories with enough words for pairs
        List<String> eligibleCategories = new ArrayList<>();
        for (Map.Entry<String, List<Word>> entry : wordsByCategory.entrySet()) {
            if (entry.getValue().size() >= 2) {
                eligibleCategories.add(entry.getKey());
            }
        }

        if (eligibleCategories.isEmpty()) {
            Toast.makeText(this, R.string.not_enough_related_words, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Create word pairs
        List<WordPair> wordPairs = new ArrayList<>();
        Random random = new Random();
        Set<Integer> usedWordIds = new HashSet<>();

        while (wordPairs.size() < MAX_WORDS_PER_GAME && !eligibleCategories.isEmpty()) {
            // Pick a random category
            int categoryIndex = random.nextInt(eligibleCategories.size());
            String category = eligibleCategories.get(categoryIndex);
            List<Word> categoryWords = wordsByCategory.get(category);

            // Filter out already used words
            List<Word> availableWords = new ArrayList<>();
            for (Word word : categoryWords) {
                if (!usedWordIds.contains(word.getId())) {
                    availableWords.add(word);
                }
            }

            if (availableWords.size() >= 2) {
                // Pick two random words from this category
                Collections.shuffle(availableWords);
                Word word1 = availableWords.get(0);
                Word word2 = availableWords.get(1);

                // Create a pair
                WordPair pair = new WordPair(word1, word2);
                wordPairs.add(pair);

                // Mark words as used
                usedWordIds.add(word1.getId());
                usedWordIds.add(word2.getId());
            } else {
                // Not enough words in this category, remove it
                eligibleCategories.remove(categoryIndex);
            }
        }

        if (wordPairs.size() < 4) {
            Toast.makeText(this, R.string.not_enough_related_words, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Create cards from pairs
        createCardsFromPairs(wordPairs);

        // Set total items in session
        gameSession.setTotalItems(wordPairs.size());
        sessionViewModel.update(gameSession);

        // Start the game
        startGame();
    }

    /**
     * Create word cards from pairs of related words.
     *
     * @param wordPairs The list of word pairs
     */
    private void createCardsFromPairs(List<WordPair> wordPairs) {
        wordCards = new ArrayList<>();
        
        // Create two cards for each pair (one English, one Hindi)
        for (WordPair pair : wordPairs) {
            // Card for first word (English)
            WordCard card1 = new WordCard(
                    pair.word1.getId(),
                    pair.word1.getEnglishWord(),
                    pair.getPairId(),
                    WordCard.CardType.ENGLISH);
            
            // Card for second word (Hindi)
            WordCard card2 = new WordCard(
                    pair.word2.getId(),
                    pair.word2.getHindiWord(),
                    pair.getPairId(),
                    WordCard.CardType.HINDI);
            
            wordCards.add(card1);
            wordCards.add(card2);
        }
        
        // Shuffle cards
        Collections.shuffle(wordCards);
        
        // Create adapter
        cardAdapter = new WordCardAdapter(this, wordCards, this);
        recyclerViewGrid.setAdapter(cardAdapter);
    }

    /**
     * Start the game.
     */
    private void startGame() {
        // Start timer
        startTimer();
        
        // Initialize game state
        gameOver = false;
        score = 0;
        lives = MAX_LIVES;
        matchesMade = 0;
        
        // Update UI
        updateScoreDisplay();
        updateLivesDisplay();
    }

    /**
     * Start the game timer.
     */
    private void startTimer() {
        secondsRemaining = TIME_PER_GAME;
        progressBarTimer.setMax(TIME_PER_GAME);
        progressBarTimer.setProgress(TIME_PER_GAME);
        textViewTimer.setText(formatTime(secondsRemaining));
        
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                secondsRemaining--;
                if (secondsRemaining >= 0) {
                    textViewTimer.setText(formatTime(secondsRemaining));
                    progressBarTimer.setProgress(secondsRemaining);
                    timerHandler.postDelayed(this, 1000);
                } else {
                    // Time's up
                    if (!gameOver) {
                        endGame(false);
                    }
                }
            }
        };
        
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    /**
     * Format time as MM:SS.
     *
     * @param seconds The time in seconds
     * @return The formatted time string
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    /**
     * Update the score display.
     */
    private void updateScoreDisplay() {
        textViewScore.setText(String.valueOf(score));
    }

    /**
     * Update the lives display.
     */
    private void updateLivesDisplay() {
        // Create heart symbols based on lives
        StringBuilder heartsBuilder = new StringBuilder();
        for (int i = 0; i < MAX_LIVES; i++) {
            if (i < lives) {
                heartsBuilder.append("❤️ "); // Filled heart for remaining lives
            } else {
                heartsBuilder.append("🖤 "); // Empty heart for lost lives
            }
        }
        textViewLives.setText(heartsBuilder.toString());
    }

    /**
     * Handle a card click.
     *
     * @param position The position of the clicked card
     */
    @Override
    public void onWordCardClick(int position) {
        if (gameOver) {
            return;
        }
        
        WordCard clickedCard = wordCards.get(position);
        
        // Ignore already matched cards
        if (clickedCard.isMatched()) {
            return;
        }
        
        // Play tap sound
        audioFeedbackManager.playTapSound();
        
        if (selectedCard == null) {
            // First card selection
            selectedCard = clickedCard;
            clickedCard.setSelected(true);
            cardAdapter.notifyItemChanged(position);
            
            // Show selected word
            showSelectedWord(clickedCard);
            
        } else if (selectedCard.getPosition() == position) {
            // Same card clicked again - deselect it
            selectedCard.setSelected(false);
            cardAdapter.notifyItemChanged(position);
            selectedCard = null;
            
            // Hide selected word card
            hideSelectedWord();
            
        } else {
            // Second card selection - check for a match
            clickedCard.setSelected(true);
            cardAdapter.notifyItemChanged(position);
            
            // Check if the cards match (same pair ID, different types)
            boolean isMatch = selectedCard.getPairId() == clickedCard.getPairId() && 
                             selectedCard.getCardType() != clickedCard.getCardType();
            
            if (isMatch) {
                // Match found
                handleMatch(selectedCard, clickedCard);
            } else {
                // No match
                handleMismatch(selectedCard, clickedCard);
            }
            
            // Clear selection
            new Handler().postDelayed(() -> {
                selectedCard.setSelected(false);
                clickedCard.setSelected(false);
                
                if (isMatch) {
                    selectedCard.setMatched(true);
                    clickedCard.setMatched(true);
                }
                
                cardAdapter.notifyDataSetChanged();
                selectedCard = null;
                
                // Hide selected word
                hideSelectedWord();
                
                // Check if game is complete
                checkGameCompletion();
            }, 1000);
        }
    }

    /**
     * Show the selected word card.
     *
     * @param card The selected card
     */
    private void showSelectedWord(WordCard card) {
        textViewSelectedWord.setText(card.getText());
        
        // Set pronunciation if it's an English word
        if (card.getCardType() == WordCard.CardType.ENGLISH) {
            audioPronunciation.setWordText(card.getText());
            audioPronunciation.setWordId(card.getWordId());
            audioPronunciation.setVisibility(View.VISIBLE);
        } else {
            audioPronunciation.setVisibility(View.GONE);
        }
        
        // Show the card with animation
        cardViewSelectedWord.setAlpha(0f);
        cardViewSelectedWord.setScaleX(0.8f);
        cardViewSelectedWord.setScaleY(0.8f);
        cardViewSelectedWord.setVisibility(View.VISIBLE);
        
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(
                ObjectAnimator.ofFloat(cardViewSelectedWord, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(cardViewSelectedWord, "scaleX", 0.8f, 1f),
                ObjectAnimator.ofFloat(cardViewSelectedWord, "scaleY", 0.8f, 1f)
        );
        animSet.setDuration(200);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.start();
    }

    /**
     * Hide the selected word card.
     */
    private void hideSelectedWord() {
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(
                ObjectAnimator.ofFloat(cardViewSelectedWord, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(cardViewSelectedWord, "scaleX", 1f, 0.8f),
                ObjectAnimator.ofFloat(cardViewSelectedWord, "scaleY", 1f, 0.8f)
        );
        animSet.setDuration(200);
        animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            
            @Override
            public void onAnimationEnd(Animator animation) {
                cardViewSelectedWord.setVisibility(View.INVISIBLE);
            }
            
            @Override
            public void onAnimationCancel(Animator animation) {}
            
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animSet.start();
    }

    /**
     * Handle a successful match.
     *
     * @param card1 The first card
     * @param card2 The second card
     */
    private void handleMatch(WordCard card1, WordCard card2) {
        // Play success sound
        audioFeedbackManager.playCorrectSound();
        
        // Update score
        score += POINTS_FOR_MATCH;
        updateScoreDisplay();
        
        // Update match count
        matchesMade++;
        
        // Update user progress for both words
        updateUserProgress(card1.getWordId(), true);
        updateUserProgress(card2.getWordId(), true);
        
        // Record correct answer in session
        gameSession.recordAnswer(true);
        sessionViewModel.update(gameSession);
    }

    /**
     * Handle a mismatch.
     *
     * @param card1 The first card
     * @param card2 The second card
     */
    private void handleMismatch(WordCard card1, WordCard card2) {
        // Play error sound
        audioFeedbackManager.playIncorrectSound();
        
        // Lose a life
        lives--;
        updateLivesDisplay();
        
        // Record incorrect answer in session
        gameSession.recordAnswer(false);
        sessionViewModel.update(gameSession);
        
        // Check if out of lives
        if (lives <= 0) {
            new Handler().postDelayed(() -> endGame(false), 1000);
        }
    }

    /**
     * Check if the game is complete (all matches found).
     */
    private void checkGameCompletion() {
        // Count matched cards
        int matchedCount = 0;
        for (WordCard card : wordCards) {
            if (card.isMatched()) {
                matchedCount++;
            }
        }
        
        // If all cards are matched, game is complete
        if (matchedCount == wordCards.size()) {
            endGame(true);
        }
    }

    /**
     * End the game.
     *
     * @param success Whether the game was completed successfully
     */
    private void endGame(boolean success) {
        // Stop timer
        timerHandler.removeCallbacks(timerRunnable);
        
        // Set game over state
        gameOver = true;
        
        // Complete session
        gameSession.setCompleted(true);
        gameSession.setScore(score);
        sessionViewModel.completeSession(gameSession);
        
        // Set game over title based on success
        if (success) {
            textViewGameOverTitle.setText(R.string.congratulations);
            audioFeedbackManager.playAchievementSound();
        } else {
            textViewGameOverTitle.setText(R.string.game_over);
            audioFeedbackManager.playIncorrectSound();
        }
        
        // Set final score and matches
        textViewFinalScore.setText(String.valueOf(score));
        textViewMatchesMade.setText(String.valueOf(matchesMade));
        
        // Show game over card with animation
        cardViewGameOver.setVisibility(View.VISIBLE);
        cardViewGameOver.setAlpha(0f);
        cardViewGameOver.setScaleX(0.8f);
        cardViewGameOver.setScaleY(0.8f);
        
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(
                ObjectAnimator.ofFloat(cardViewGameOver, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(cardViewGameOver, "scaleX", 0.8f, 1f),
                ObjectAnimator.ofFloat(cardViewGameOver, "scaleY", 0.8f, 1f)
        );
        animSet.setDuration(300);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.start();
    }

    /**
     * Update the user progress for a word.
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
        if (!gameOver) {
            // Show confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.exit_game_title);
            builder.setMessage(R.string.exit_game_message);
            builder.setPositiveButton(R.string.exit, (dialog, which) -> {
                timerHandler.removeCallbacks(timerRunnable);
                finish();
            });
            builder.setNegativeButton(R.string.cancel, null);
            builder.show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(timerRunnable);
    }

    /**
     * Class to represent a pair of related words.
     */
    private static class WordPair {
        private Word word1;
        private Word word2;
        private int pairId;
        
        public WordPair(Word word1, Word word2) {
            this.word1 = word1;
            this.word2 = word2;
            this.pairId = generatePairId(word1, word2);
        }
        
        /**
         * Generate a unique pair ID.
         */
        private int generatePairId(Word word1, Word word2) {
            return (word1.getId() * 31) + word2.getId();
        }
        
        public int getPairId() {
            return pairId;
        }
    }

    /**
     * Class to represent a card in the game.
     */
    public static class WordCard {
        private int wordId;
        private String text;
        private int pairId;
        private CardType cardType;
        private boolean matched;
        private boolean selected;
        private int position;
        
        /**
         * Card types.
         */
        public enum CardType {
            ENGLISH,
            HINDI
        }
        
        public WordCard(int wordId, String text, int pairId, CardType cardType) {
            this.wordId = wordId;
            this.text = text;
            this.pairId = pairId;
            this.cardType = cardType;
            this.matched = false;
            this.selected = false;
        }
        
        public int getWordId() {
            return wordId;
        }
        
        public String getText() {
            return text;
        }
        
        public int getPairId() {
            return pairId;
        }
        
        public CardType getCardType() {
            return cardType;
        }
        
        public boolean isMatched() {
            return matched;
        }
        
        public void setMatched(boolean matched) {
            this.matched = matched;
        }
        
        public boolean isSelected() {
            return selected;
        }
        
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
        
        public int getPosition() {
            return position;
        }
        
        public void setPosition(int position) {
            this.position = position;
        }
    }
}