package com.example.englishhindi.games;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Activity for the Picture-Word matching game.
 * Players match pictures with the correct English/Hindi words.
 */
public class PictureMatchActivity extends AppCompatActivity {

    private static final int MAX_QUESTIONS = 10;
    private static final int OPTIONS_PER_QUESTION = 4;
    private static final int POINTS_FOR_CORRECT = 10;
    private static final long FEEDBACK_DELAY = 1000; // milliseconds

    // UI components
    private TextView textViewScore;
    private TextView textViewQuestion;
    private ProgressBar progressBar;
    private ImageView imageViewPicture;
    private CardView cardViewPicture;
    private AudioPronunciationView audioPronunciation;
    private ConstraintLayout layoutOptions;
    private Button[] optionButtons;
    private Button buttonNext;
    private TextView textViewFeedback;
    private CardView cardViewCompletion;
    private TextView textViewCompletionTitle;
    private TextView textViewCompletionScore;
    private ProgressBar progressBarAccuracy;
    private TextView textViewAccuracy;
    private Button buttonPlayAgain;
    private Button buttonExit;

    // Game data
    private List<Word> gameWords;
    private List<Word> allWords; // For generating incorrect options
    private Word currentWord;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean hasAnswered = false;
    private int correctAnswerIndex = -1;
    private boolean gameCompleted = false;

    // Randomizer
    private Random random = new Random();

    // ViewModels
    private WordViewModel wordViewModel;
    private UserProgressViewModel userProgressViewModel;
    private PracticeSessionViewModel sessionViewModel;

    // Session
    private PracticeSession gameSession;

    // Audio
    private AudioManager audioManager;
    private AudioFeedbackManager audioFeedbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_match);

        // Setup ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.picture_word_match);
        }

        // Initialize ViewModels
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);

        // Initialize audio manager
        audioManager = AudioManager.getInstance(this);
        audioFeedbackManager = AudioFeedbackManager.getInstance(this);

        // Initialize UI components
        initializeUI();

        // Get difficulty and category from intent
        int difficulty = getIntent().getIntExtra("difficulty", 1);
        String category = getIntent().getStringExtra("category");

        // Create a new game session
        gameSession = sessionViewModel.createGameSession(1, "picture_match");
        gameSession.setDifficulty(difficulty);
        sessionViewModel.insert(gameSession);

        // Load all words (for generating incorrect options)
        wordViewModel.getAllWords().observe(this, words -> {
            if (words != null && !words.isEmpty()) {
                allWords = new ArrayList<>(words);
                // Now load game words
                loadGameWords(difficulty, category);
            } else {
                Toast.makeText(this, R.string.no_words_available, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Set up click listeners
        setupClickListeners();
    }

    /**
     * Initialize UI components.
     */
    private void initializeUI() {
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestion = findViewById(R.id.text_view_question);
        progressBar = findViewById(R.id.progress_bar);
        imageViewPicture = findViewById(R.id.image_view_picture);
        cardViewPicture = findViewById(R.id.card_view_picture);
        audioPronunciation = findViewById(R.id.audio_pronunciation);
        layoutOptions = findViewById(R.id.layout_options);
        buttonNext = findViewById(R.id.button_next);
        textViewFeedback = findViewById(R.id.text_view_feedback);
        cardViewCompletion = findViewById(R.id.card_view_completion);
        textViewCompletionTitle = findViewById(R.id.text_view_completion_title);
        textViewCompletionScore = findViewById(R.id.text_view_completion_score);
        progressBarAccuracy = findViewById(R.id.progress_bar_accuracy);
        textViewAccuracy = findViewById(R.id.text_view_accuracy);
        buttonPlayAgain = findViewById(R.id.button_play_again);
        buttonExit = findViewById(R.id.button_exit);

        // Initialize option buttons
        optionButtons = new Button[OPTIONS_PER_QUESTION];
        optionButtons[0] = findViewById(R.id.button_option_1);
        optionButtons[1] = findViewById(R.id.button_option_2);
        optionButtons[2] = findViewById(R.id.button_option_3);
        optionButtons[3] = findViewById(R.id.button_option_4);

        // Initially hide next button and feedback
        buttonNext.setVisibility(View.INVISIBLE);
        textViewFeedback.setVisibility(View.INVISIBLE);
        cardViewCompletion.setVisibility(View.GONE);

        // Update score
        updateScoreDisplay();
    }

    /**
     * Set up click listeners.
     */
    private void setupClickListeners() {
        // Option buttons
        for (int i = 0; i < optionButtons.length; i++) {
            final int optionIndex = i;
            optionButtons[i].setOnClickListener(v -> {
                if (!hasAnswered) {
                    checkAnswer(optionIndex);
                }
            });
        }

        // Next button
        buttonNext.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < gameWords.size()) {
                showCurrentQuestion();
            } else {
                completeGame();
            }
        });

        // Completion buttons
        buttonPlayAgain.setOnClickListener(v -> {
            // Start a new game with the same parameters
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });

        buttonExit.setOnClickListener(v -> finish());
    }

    /**
     * Load game words based on difficulty and category.
     *
     * @param difficulty The difficulty level (1-5)
     * @param category   The word category (or null for all categories)
     */
    private void loadGameWords(int difficulty, String category) {
        if (category != null && !category.isEmpty()) {
            // Load words by category
            wordViewModel.getWordsByCategory(category).observe(this, words -> {
                filterAndPrepareWords(words, difficulty);
            });
        } else {
            // Load random words by difficulty
            wordViewModel.getWordsByDifficulty(difficulty).observe(this, words -> {
                filterAndPrepareWords(words, difficulty);
            });
        }
    }

    /**
     * Filter words by those that have images and prepare for the game.
     *
     * @param words      The list of words to filter
     * @param difficulty The difficulty level
     */
    private void filterAndPrepareWords(List<Word> words, int difficulty) {
        if (words == null || words.isEmpty()) {
            Toast.makeText(this, R.string.no_words_criteria, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Filter words that have images and match difficulty
        gameWords = new ArrayList<>();
        for (Word word : words) {
            if (word.getDifficulty() <= difficulty && hasImageForWord(word) && gameWords.size() < MAX_QUESTIONS) {
                gameWords.add(word);
            }
        }

        // Shuffle words
        Collections.shuffle(gameWords);

        // If we don't have enough words with images, show message and finish
        if (gameWords.size() < 5) {
            Toast.makeText(this, R.string.not_enough_words_with_images, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set total items in session
        gameSession.setTotalItems(gameWords.size());
        sessionViewModel.update(gameSession);

        // Start game
        showCurrentQuestion();
    }

    /**
     * Check if a word has an associated image.
     *
     * @param word The word to check
     * @return True if the word has an image, false otherwise
     */
    private boolean hasImageForWord(Word word) {
        // Check if image exists in app's files directory
        File imageFile = getImageFileForWord(word);
        return imageFile != null && imageFile.exists();
    }

    /**
     * Get the image file for a word.
     *
     * @param word The word
     * @return The image file
     */
    private File getImageFileForWord(Word word) {
        File imagesDir = new File(getFilesDir(), "images");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }

        // Check different possible image formats
        File jpgFile = new File(imagesDir, "word_" + word.getId() + ".jpg");
        File pngFile = new File(imagesDir, "word_" + word.getId() + ".png");
        File webpFile = new File(imagesDir, "word_" + word.getId() + ".webp");

        if (jpgFile.exists()) {
            return jpgFile;
        } else if (pngFile.exists()) {
            return pngFile;
        } else if (webpFile.exists()) {
            return webpFile;
        }

        return null;
    }

    /**
     * Show the current question.
     */
    private void showCurrentQuestion() {
        if (currentQuestionIndex >= gameWords.size()) {
            completeGame();
            return;
        }

        // Get the current word
        currentWord = gameWords.get(currentQuestionIndex);

        // Update session state
        gameSession.setCurrentIndex(currentQuestionIndex);
        sessionViewModel.update(gameSession);

        // Update progress
        progressBar.setMax(gameWords.size());
        progressBar.setProgress(currentQuestionIndex + 1);
        String questionText = getString(R.string.question_number, currentQuestionIndex + 1, gameWords.size());
        textViewQuestion.setText(questionText);

        // Load and display the image
        loadImage(currentWord);

        // Set up audio pronunciation
        audioPronunciation.setWordText(currentWord.getEnglishWord());
        audioPronunciation.setWordId(currentWord.getId());

        // Generate and display options
        generateOptions();

        // Reset answer state
        hasAnswered = false;
        buttonNext.setVisibility(View.INVISIBLE);
        textViewFeedback.setVisibility(View.INVISIBLE);

        // Enable option buttons
        for (Button button : optionButtons) {
            button.setEnabled(true);
            button.setBackgroundResource(R.drawable.button_option_normal);
        }

        // Play entrance animations
        playEntranceAnimations();
    }

    /**
     * Load and display the image for the current word.
     *
     * @param word The word whose image to load
     */
    private void loadImage(Word word) {
        File imageFile = getImageFileForWord(word);
        if (imageFile != null && imageFile.exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
                imageViewPicture.setImageBitmap(bitmap);
            } catch (Exception e) {
                imageViewPicture.setImageResource(R.drawable.placeholder_image);
            }
        } else {
            imageViewPicture.setImageResource(R.drawable.placeholder_image);
        }
    }

    /**
     * Generate options for the current question.
     */
    private void generateOptions() {
        // Clear previous options
        List<Word> options = new ArrayList<>();
        options.add(currentWord); // Add correct answer

        // Generate incorrect options
        List<Word> incorrectOptions = generateIncorrectOptions(currentWord, OPTIONS_PER_QUESTION - 1);
        options.addAll(incorrectOptions);

        // Shuffle options
        Collections.shuffle(options);

        // Find index of correct answer
        correctAnswerIndex = options.indexOf(currentWord);

        // Set option text
        for (int i = 0; i < optionButtons.length; i++) {
            // Determine if we should show English or Hindi based on image
            String optionText;
            if (random.nextBoolean()) {
                // Show English
                optionText = options.get(i).getEnglishWord();
            } else {
                // Show Hindi
                optionText = options.get(i).getHindiWord();
            }
            optionButtons[i].setText(optionText);
        }
    }

    /**
     * Generate incorrect options for a multiple-choice question.
     *
     * @param correctWord   The correct word
     * @param numberOfOptions The number of incorrect options to generate
     * @return A list of incorrect options
     */
    private List<Word> generateIncorrectOptions(Word correctWord, int numberOfOptions) {
        List<Word> incorrectOptions = new ArrayList<>();
        List<Word> candidateWords = new ArrayList<>(allWords);
        
        // Remove the correct word from candidates
        candidateWords.remove(correctWord);
        
        // Try to find words with similar difficulty first
        List<Word> similarDifficultyWords = new ArrayList<>();
        for (Word word : candidateWords) {
            if (Math.abs(word.getDifficulty() - correctWord.getDifficulty()) <= 1) {
                similarDifficultyWords.add(word);
            }
        }
        
        // If we have enough similar difficulty words, use those
        if (similarDifficultyWords.size() >= numberOfOptions) {
            Collections.shuffle(similarDifficultyWords);
            return similarDifficultyWords.subList(0, numberOfOptions);
        }
        
        // Otherwise, use random words
        Collections.shuffle(candidateWords);
        return candidateWords.subList(0, Math.min(numberOfOptions, candidateWords.size()));
    }

    /**
     * Check if the selected answer is correct.
     *
     * @param selectedIndex The index of the selected option
     */
    private void checkAnswer(int selectedIndex) {
        hasAnswered = true;
        boolean isCorrect = selectedIndex == correctAnswerIndex;
        
        // Disable all option buttons
        for (Button button : optionButtons) {
            button.setEnabled(false);
        }
        
        // Update score
        if (isCorrect) {
            score += POINTS_FOR_CORRECT;
            updateScoreDisplay();
        }
        
        // Update user progress
        updateUserProgress(currentWord.getId(), isCorrect);
        
        // Record answer in session
        gameSession.recordAnswer(isCorrect);
        sessionViewModel.update(gameSession);
        
        // Show feedback
        if (isCorrect) {
            textViewFeedback.setText(R.string.correct);
            textViewFeedback.setTextColor(getResources().getColor(R.color.colorCorrect));
            optionButtons[selectedIndex].setBackgroundResource(R.drawable.button_option_correct);
        } else {
            textViewFeedback.setText(R.string.incorrect);
            textViewFeedback.setTextColor(getResources().getColor(R.color.colorIncorrect));
            optionButtons[selectedIndex].setBackgroundResource(R.drawable.button_option_incorrect);
            optionButtons[correctAnswerIndex].setBackgroundResource(R.drawable.button_option_correct);
        }
        
        textViewFeedback.setVisibility(View.VISIBLE);
        
        // Play sound
        AudioFeedbackManager audioFeedbackManager = AudioFeedbackManager.getInstance(this);
        if (isCorrect) {
            audioFeedbackManager.playCorrectSound();
        } else {
            audioFeedbackManager.playIncorrectSound();
        }
        
        // Show next button after a short delay
        new Handler().postDelayed(() -> {
            buttonNext.setVisibility(View.VISIBLE);
        }, FEEDBACK_DELAY);
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

    /**
     * Update the score display.
     */
    private void updateScoreDisplay() {
        textViewScore.setText(String.valueOf(score));
    }

    /**
     * Play entrance animations for the question elements.
     */
    private void playEntranceAnimations() {
        // Animate card
        cardViewPicture.setAlpha(0f);
        cardViewPicture.setScaleX(0.8f);
        cardViewPicture.setScaleY(0.8f);
        
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(cardViewPicture, "alpha", 0f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(cardViewPicture, "scaleX", 0.8f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(cardViewPicture, "scaleY", 0.8f, 1f);
        
        AnimatorSet cardAnimSet = new AnimatorSet();
        cardAnimSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        cardAnimSet.setDuration(300);
        cardAnimSet.setInterpolator(new AccelerateDecelerateInterpolator());
        cardAnimSet.start();
        
        // Animate options with a staggered delay
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setTranslationX(100);
            optionButtons[i].setAlpha(0f);
            
            ObjectAnimator optionTranslateAnimator = ObjectAnimator.ofFloat(optionButtons[i], "translationX", 100f, 0f);
            ObjectAnimator optionAlphaAnimator = ObjectAnimator.ofFloat(optionButtons[i], "alpha", 0f, 1f);
            
            AnimatorSet optionAnimSet = new AnimatorSet();
            optionAnimSet.playTogether(optionTranslateAnimator, optionAlphaAnimator);
            optionAnimSet.setDuration(300);
            optionAnimSet.setStartDelay(100 + (i * 50)); // Staggered delay
            optionAnimSet.setInterpolator(new AccelerateDecelerateInterpolator());
            optionAnimSet.start();
        }
    }

    /**
     * Complete the game and show results.
     */
    private void completeGame() {
        gameCompleted = true;
        
        // Complete the session
        gameSession.setCompleted(true);
        gameSession.setScore(score);
        sessionViewModel.completeSession(gameSession);
        
        // Calculate accuracy
        int totalQuestions = gameWords.size();
        int correctAnswers = 0;
        for (boolean result : gameSession.getResults()) {
            if (result) correctAnswers++;
        }
        
        int accuracy = totalQuestions > 0 ? (correctAnswers * 100) / totalQuestions : 0;
        
        // Show completion card
        cardViewCompletion.setVisibility(View.VISIBLE);
        
        // Set completion title based on performance
        if (accuracy >= 80) {
            textViewCompletionTitle.setText(R.string.excellent_job);
        } else if (accuracy >= 60) {
            textViewCompletionTitle.setText(R.string.well_done);
        } else {
            textViewCompletionTitle.setText(R.string.good_effort);
        }
        
        // Set score and accuracy
        textViewCompletionScore.setText(String.valueOf(score));
        textViewAccuracy.setText(accuracy + "%");
        progressBarAccuracy.setProgress(accuracy);
        
        // Animate completion card
        cardViewCompletion.setAlpha(0f);
        cardViewCompletion.setScaleX(0.8f);
        cardViewCompletion.setScaleY(0.8f);
        
        AnimatorSet completionAnimSet = new AnimatorSet();
        completionAnimSet.playTogether(
                ObjectAnimator.ofFloat(cardViewCompletion, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(cardViewCompletion, "scaleX", 0.8f, 1f),
                ObjectAnimator.ofFloat(cardViewCompletion, "scaleY", 0.8f, 1f)
        );
        completionAnimSet.setDuration(300);
        completionAnimSet.setInterpolator(new BounceInterpolator());
        completionAnimSet.start();
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
        if (!gameCompleted && currentQuestionIndex > 0) {
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