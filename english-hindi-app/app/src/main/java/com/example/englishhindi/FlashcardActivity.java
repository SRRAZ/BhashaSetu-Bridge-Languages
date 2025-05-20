package com.example.englishhindi;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.englishhindi.model.FlashcardDeck;
import com.example.englishhindi.model.PracticeSession;
import com.example.englishhindi.model.UserProgress;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.viewmodel.FlashcardDeckViewModel;
import com.example.englishhindi.viewmodel.PracticeSessionViewModel;
import com.example.englishhindi.viewmodel.UserProgressViewModel;
import com.example.englishhindi.viewmodel.WordViewModel;

import java.util.List;
import java.util.Locale;

public class FlashcardActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    
    private TextView textViewDeckTitle;
    private TextView textViewDeckTitleHindi;
    private TextView textViewProgress;
    private CardView flashcardContainer;
    private TextView textViewFlashcardFront;
    private TextView textViewFlashcardBack;
    private TextView textViewPronunciation;
    private TextView textViewHintTap;
    private ImageButton buttonAudio;
    private Button buttonPrevious;
    private Button buttonFlip;
    private Button buttonNext;
    private Button buttonDontKnow;
    private Button buttonKnow;
    
    private FlashcardDeckViewModel deckViewModel;
    private WordViewModel wordViewModel;
    private UserProgressViewModel userProgressViewModel;
    private PracticeSessionViewModel sessionViewModel;
    
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;
    
    private Animation animFlipOutForward;
    private Animation animFlipInForward;
    private Animation animFlipOutReverse;
    private Animation animFlipInReverse;
    
    private int deckId;
    private boolean isReviewMode;
    private PracticeSession currentSession;
    private boolean cardFlipped = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check if Hindi interface is enabled
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
        
        // Set the appropriate layout based on language preference
        if (useHindiInterface) {
            setContentView(R.layout.activity_flashcard_hindi);
        } else {
            setContentView(R.layout.activity_flashcard);
        }
        
        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.flashcards);
        }
        
        // Initialize UI components
        textViewDeckTitle = findViewById(R.id.text_view_deck_title);
        textViewDeckTitleHindi = findViewById(R.id.text_view_deck_title_hindi);
        textViewProgress = findViewById(R.id.text_view_progress);
        flashcardContainer = findViewById(R.id.flashcard_container);
        textViewFlashcardFront = findViewById(R.id.text_view_flashcard_front);
        textViewFlashcardBack = findViewById(R.id.text_view_flashcard_back);
        textViewPronunciation = findViewById(R.id.text_view_pronunciation);
        textViewHintTap = findViewById(R.id.text_view_hint_tap);
        buttonAudio = findViewById(R.id.button_audio);
        buttonPrevious = findViewById(R.id.button_previous);
        buttonFlip = findViewById(R.id.button_flip);
        buttonNext = findViewById(R.id.button_next);
        buttonDontKnow = findViewById(R.id.button_dont_know);
        buttonKnow = findViewById(R.id.button_know);
        
        // Initialize ViewModels
        deckViewModel = new ViewModelProvider(this).get(FlashcardDeckViewModel.class);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);
        
        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, this);
        
        // Load animations
        animFlipOutForward = AnimationUtils.loadAnimation(this, R.anim.card_flip_left_out);
        animFlipInForward = AnimationUtils.loadAnimation(this, R.anim.card_flip_right_in);
        animFlipOutReverse = AnimationUtils.loadAnimation(this, R.anim.card_flip_right_out);
        animFlipInReverse = AnimationUtils.loadAnimation(this, R.anim.card_flip_left_in);
        
        // Set the animation interpolators for smoother animation
        animFlipOutForward.setInterpolator(new AccelerateInterpolator());
        animFlipInForward.setInterpolator(new DecelerateInterpolator());
        animFlipOutReverse.setInterpolator(new AccelerateInterpolator());
        animFlipInReverse.setInterpolator(new DecelerateInterpolator());
        
        // Get Intent extras
        deckId = getIntent().getIntExtra("deck_id", -1);
        isReviewMode = getIntent().getBooleanExtra("review_mode", false);
        
        // Create a new practice session
        if (isReviewMode) {
            // Review mode - we'll load words that are due for review
            initializeReviewSession();
        } else if (deckId != -1) {
            // Deck mode - we'll load words from the specified deck
            initializeDeckSession();
        } else {
            // Something went wrong
            Toast.makeText(this, "Error: No deck specified", Toast.LENGTH_SHORT).show();
            finish();
        }
        
        // Set up click listeners
        flashcardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
        
        buttonFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
        
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousCard();
            }
        });
        
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextCard();
            }
        });
        
        buttonDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordResponse(false);
            }
        });
        
        buttonKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordResponse(true);
            }
        });
        
        buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPronunciation();
            }
        });
    }
    
    private void initializeDeckSession() {
        // Get the deck details
        deckViewModel.getDeckById(deckId).observe(this, new Observer<FlashcardDeck>() {
            @Override
            public void onChanged(FlashcardDeck deck) {
                if (deck != null) {
                    // Update deck title
                    textViewDeckTitle.setText(deck.getName());
                    textViewDeckTitleHindi.setText(deck.getNameHindi());
                    
                    // Update deck last practiced timestamp
                    deckViewModel.updateLastPracticed(deckId);
                    
                    // Get words for this deck
                    wordViewModel.getWordsForDeck(deckId).observe(FlashcardActivity.this, new Observer<List<Word>>() {
                        @Override
                        public void onChanged(List<Word> words) {
                            if (words != null && !words.isEmpty()) {
                                // Create a new practice session
                                currentSession = sessionViewModel.createFlashcardSession(1, deckId);
                                currentSession.setWords(words);
                                sessionViewModel.insert(currentSession);
                                
                                // Show the first card
                                updateCardDisplay();
                            } else {
                                // No words in this deck
                                Toast.makeText(FlashcardActivity.this, "No words in this deck", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                } else {
                    // Deck not found
                    Toast.makeText(FlashcardActivity.this, "Error: Deck not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    
    private void initializeReviewSession() {
        // Set title for review mode
        textViewDeckTitle.setText("Review Due Cards");
        textViewDeckTitleHindi.setText("देय कार्ड समीक्षा");
        
        // Get words that are due for review
        userProgressViewModel.getDueWordReviews(1, 10).observe(this, new Observer<List<UserProgress>>() {
            @Override
            public void onChanged(List<UserProgress> reviewItems) {
                if (reviewItems != null && !reviewItems.isEmpty()) {
                    // Create list of word IDs to load
                    Integer[] wordIds = new Integer[reviewItems.size()];
                    for (int i = 0; i < reviewItems.size(); i++) {
                        wordIds[i] = reviewItems.get(i).getItemId();
                    }
                    
                    // Load the words
                    wordViewModel.getWordsByIds(java.util.Arrays.asList(wordIds)).observe(FlashcardActivity.this, new Observer<List<Word>>() {
                        @Override
                        public void onChanged(List<Word> words) {
                            if (words != null && !words.isEmpty()) {
                                // Create a new practice session
                                currentSession = sessionViewModel.createFlashcardSession(1, 0);
                                currentSession.setWords(words);
                                sessionViewModel.insert(currentSession);
                                
                                // Show the first card
                                updateCardDisplay();
                            } else {
                                // No words to review
                                Toast.makeText(FlashcardActivity.this, "No words to review", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                } else {
                    // No reviews due
                    Toast.makeText(FlashcardActivity.this, "No reviews due", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    
    private void updateCardDisplay() {
        Word currentWord = currentSession.getCurrentWord();
        
        if (currentWord != null) {
            // Update progress indicator
            textViewProgress.setText("Card " + (currentSession.getCurrentIndex() + 1) + " of " + currentSession.getTotalItems());
            
            // Get shared preferences for interface language
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
            
            // Set card content based on interface language preference
            if (useHindiInterface) {
                // Hindi interface - show Hindi on front, English on back
                textViewFlashcardFront.setText(currentWord.getHindiWord());
                textViewFlashcardBack.setText(currentWord.getEnglishWord());
                textViewPronunciation.setText("Pronunciation: " + currentWord.getEnglishPronunciation());
            } else {
                // English interface - show English on front, Hindi on back
                textViewFlashcardFront.setText(currentWord.getEnglishWord());
                textViewFlashcardBack.setText(currentWord.getHindiWord());
                textViewPronunciation.setText("Pronunciation: " + currentWord.getHindiPronunciation());
            }
            
            // Reset card to front side
            cardFlipped = false;
            textViewFlashcardFront.setVisibility(View.VISIBLE);
            textViewFlashcardBack.setVisibility(View.GONE);
            
            // Enable/disable navigation buttons
            buttonPrevious.setEnabled(currentSession.getCurrentIndex() > 0);
            buttonNext.setEnabled(currentSession.getCurrentIndex() < currentSession.getTotalItems() - 1);
        }
    }
    
    private void flipCard() {
        // Animation-based card flipping
        if (cardFlipped) {
            // Setup animations to flip from back to front
            animFlipOutReverse.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    textViewFlashcardBack.setVisibility(View.GONE);
                    textViewFlashcardFront.setVisibility(View.VISIBLE);
                    textViewFlashcardFront.startAnimation(animFlipInReverse);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            
            textViewFlashcardBack.startAnimation(animFlipOutReverse);
            cardFlipped = false;
        } else {
            // Setup animations to flip from front to back
            animFlipOutForward.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    textViewFlashcardFront.setVisibility(View.GONE);
                    textViewFlashcardBack.setVisibility(View.VISIBLE);
                    textViewFlashcardBack.startAnimation(animFlipInForward);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            
            textViewFlashcardFront.startAnimation(animFlipOutForward);
            cardFlipped = true;
        }
    }
    
    private void showPreviousCard() {
        if (currentSession.moveToPrevious()) {
            updateCardDisplay();
        }
    }
    
    private void showNextCard() {
        if (currentSession.moveToNext()) {
            updateCardDisplay();
        } else {
            // End of deck
            finishSession();
        }
    }
    
    private void recordResponse(boolean knewAnswer) {
        // Get the current word
        final Word currentWord = currentSession.getCurrentWord();
        if (currentWord == null) {
            return;
        }
        
        // Record the response
        currentSession.recordAnswer(knewAnswer);
        
        // Update user progress for this word
        userProgressViewModel.getWordProgress(1, currentWord.getId()).observe(this, new Observer<UserProgress>() {
            @Override
            public void onChanged(UserProgress progress) {
                if (progress != null) {
                    userProgressViewModel.recordAttemptResult(progress, knewAnswer);
                } else {
                    // Create a new progress entry if none exists
                    UserProgress newProgress = new UserProgress(1, "word", currentWord.getId());
                    newProgress.updateSpacedRepetition(knewAnswer);
                    userProgressViewModel.insert(newProgress);
                }
            }
        });
        
        // Move to the next card
        if (currentSession.moveToNext()) {
            updateCardDisplay();
        } else {
            // End of deck
            finishSession();
        }
    }
    
    private void finishSession() {
        // Complete the session
        currentSession.setCompleted(true);
        
        // Calculate the score (percentage)
        int score = (int) currentSession.getAccuracy();
        currentSession.setScore(score);
        
        // Update the session in the database
        sessionViewModel.completeSession(currentSession);
        
        // Show completion dialog
        showCompletionDialog(score);
    }
    
    private void showCompletionDialog(int score) {
        // Create a dialog to show the completion results
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        
        // Inflate a custom view for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_practice_complete, null);
        builder.setView(dialogView);
        
        // Set up dialog components
        TextView textViewCongratulations = dialogView.findViewById(R.id.text_view_congratulations);
        TextView textViewScore = dialogView.findViewById(R.id.text_view_score);
        TextView textViewFeedback = dialogView.findViewById(R.id.text_view_feedback);
        Button buttonClose = dialogView.findViewById(R.id.button_close);
        Button buttonRestart = dialogView.findViewById(R.id.button_restart);
        
        // Set custom text for flashcards
        textViewCongratulations.setText("फ्लैशकार्ड पूरा हुआ!\nFlashcard Complete!");
        
        // Set the score
        textViewScore.setText(score + "%");
        
        // Set feedback based on score
        String feedback;
        if (score >= 90) {
            feedback = "शानदार! आपकी याददाश्त अद्भुत है।\nAmazing! Your memory is excellent.";
        } else if (score >= 70) {
            feedback = "बहुत अच्छा! आप बहुत प्रगति कर रहे हैं।\nVery good! You're making great progress.";
        } else if (score >= 50) {
            feedback = "अच्छा प्रयास! नियमित अभ्यास जारी रखें।\nGood effort! Keep practicing regularly.";
        } else {
            feedback = "आप सीख रहे हैं! नियमित अभ्यास से मदद मिलेगी।\nYou're learning! Regular practice will help.";
        }
        textViewFeedback.setText(feedback);
        
        // Create and show the dialog
        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        
        // Set up button click listeners
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Restart with the same parameters
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
    
    private void playPronunciation() {
        if (!ttsReady) {
            Toast.makeText(this, "Text-to-speech is not ready yet", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Word currentWord = currentSession.getCurrentWord();
        if (currentWord == null) {
            return;
        }
        
        // Get shared preferences for interface language
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
        
        // Determine which pronunciation to play
        String wordToSpeak;
        
        if (useHindiInterface) {
            // If Hindi interface, speak the English word when card is flipped
            wordToSpeak = cardFlipped ? currentWord.getEnglishWord() : currentWord.getHindiWord();
        } else {
            // If English interface, speak the Hindi word when card is flipped
            wordToSpeak = cardFlipped ? currentWord.getHindiWord() : currentWord.getEnglishWord();
        }
        
        // Set speech rate (slightly slower for learning)
        textToSpeech.setSpeechRate(0.8f);
        
        // Speak the word
        textToSpeech.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null, "flashcard_word_" + currentWord.getId());
    }
    
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language for TTS
            int result = textToSpeech.setLanguage(Locale.US);
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Text-to-speech language not supported", Toast.LENGTH_SHORT).show();
            } else {
                ttsReady = true;
                buttonAudio.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "Text-to-speech initialization failed", Toast.LENGTH_SHORT).show();
            buttonAudio.setEnabled(false);
        }
    }
    
    @Override
    protected void onDestroy() {
        // Shutdown TTS when activity is destroyed
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}