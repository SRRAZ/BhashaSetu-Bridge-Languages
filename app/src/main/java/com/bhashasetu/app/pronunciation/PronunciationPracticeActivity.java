package com.bhashasetu.app.pronunciation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.audio.AudioAnalyzer;
import com.bhashasetu.app.audio.AudioFeedbackManager;
import com.bhashasetu.app.audio.AudioManager;
import com.bhashasetu.app.audio.TtsRecorder;
import com.bhashasetu.app.manager.AchievementManager;
import com.bhashasetu.app.model.PronunciationSession;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.view.EnhancedAudioPronunciationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PronunciationPracticeActivity extends AppCompatActivity implements PronunciationWordAdapter.OnPronunciationItemClickListener {

    private PronunciationViewModel viewModel;
    private RecyclerView recyclerWords;
    private PronunciationWordAdapter adapter;
    private EnhancedAudioPronunciationView pronunciationView;
    private TextView tvInstructions;
    private TextView tvScore;
    private ProgressBar progressBar;
    private Button btnStartSession;
    private Button btnNextWord;
    private View feedbackPanel;
    private TextView tvFeedback;
    
    private AudioManager audioManager;
    private AudioAnalyzer audioAnalyzer;
    private TtsRecorder ttsRecorder;
    private AudioFeedbackManager audioFeedbackManager;
    private AchievementManager achievementManager;
    
    private int currentWordIndex = 0;
    private PronunciationSession currentSession;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciation_practice);
        
        // Initialize view model
        viewModel = new ViewModelProvider(this).get(PronunciationViewModel.class);
        
        // Initialize audio components
        audioManager = new AudioManager(this);
        audioAnalyzer = new AudioAnalyzer();
        ttsRecorder = new TtsRecorder(this);
        audioFeedbackManager = new AudioFeedbackManager(this);
        achievementManager = AchievementManager.getInstance(this);
        
        // Initialize UI elements
        initializeViews();
        setupListeners();
        observeViewModel();
        
        // Load pronunciation session data
        viewModel.loadPronunciationWords();
    }
    
    private void initializeViews() {
        recyclerWords = findViewById(R.id.recycler_pronunciation_words);
        pronunciationView = findViewById(R.id.pronunciation_view);
        tvInstructions = findViewById(R.id.tv_instructions);
        tvScore = findViewById(R.id.tv_score);
        progressBar = findViewById(R.id.progress_pronunciation);
        btnStartSession = findViewById(R.id.btn_start_session);
        btnNextWord = findViewById(R.id.btn_next_word);
        feedbackPanel = findViewById(R.id.panel_feedback);
        tvFeedback = findViewById(R.id.tv_feedback);
        
        recyclerWords.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PronunciationWordAdapter(this, new ArrayList<>(), this);
        recyclerWords.setAdapter(adapter);
        
        // Initially hide some views
        feedbackPanel.setVisibility(View.GONE);
        btnNextWord.setVisibility(View.GONE);
    }
    
    private void setupListeners() {
        btnStartSession.setOnClickListener(v -> startPronunciationSession());
        btnNextWord.setOnClickListener(v -> moveToNextWord());
        
        // Setup pronunciation view listeners
        pronunciationView.setOnRecordCompleteListener((filePath, durationMs) -> {
            isRecording = false;
            analyzePronunciation(filePath);
        });
        
        pronunciationView.setOnPlayCompleteListener(() -> {
            // Update UI after reference pronunciation playback
        });
    }
    
    private void observeViewModel() {
        viewModel.getPronunciationWords().observe(this, words -> {
            adapter.updateWords(words);
            
            if (words.size() > 0) {
                btnStartSession.setEnabled(true);
            }
        });
        
        viewModel.getCurrentSessionLiveData().observe(this, session -> {
            currentSession = session;
            updateSessionProgress();
        });
        
        viewModel.getCurrentWordScoreLiveData().observe(this, score -> {
            if (score != null) {
                displayPronunciationFeedback(score);
            }
        });
    }
    
    private void startPronunciationSession() {
        List<Word> selectedWords = adapter.getSelectedWords();
        
        if (selectedWords.size() == 0) {
            Toast.makeText(this, R.string.select_words_for_practice, Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Create new session
        viewModel.startNewSession(selectedWords);
        
        // Update UI for session
        btnStartSession.setVisibility(View.GONE);
        recyclerWords.setVisibility(View.GONE);
        pronunciationView.setVisibility(View.VISIBLE);
        
        // Start with first word
        currentWordIndex = 0;
        setupCurrentWord();
    }
    
    private void setupCurrentWord() {
        if (currentSession == null || currentWordIndex >= currentSession.getWords().size()) {
            finishSession();
            return;
        }
        
        Word currentWord = currentSession.getWords().get(currentWordIndex);
        
        // Update UI with current word
        tvInstructions.setText(getString(R.string.pronunciation_instruction, currentWord.getEnglishWord()));
        
        // Prepare reference audio
        String referenceAudioPath = prepareReferenceAudio(currentWord);
        pronunciationView.setReferenceAudioPath(referenceAudioPath);
        
        // Reset UI elements for new word
        feedbackPanel.setVisibility(View.GONE);
        btnNextWord.setVisibility(View.GONE);
        pronunciationView.resetView();
    }
    
    private String prepareReferenceAudio(Word word) {
        // Check if reference audio exists, otherwise create it
        File audioDir = new File(getFilesDir(), "reference_audio");
        if (!audioDir.exists()) {
            audioDir.mkdirs();
        }
        
        String audioFilename = "ref_" + word.getId() + ".mp3";
        File audioFile = new File(audioDir, audioFilename);
        
        if (!audioFile.exists()) {
            // Generate TTS audio file
            ttsRecorder.recordTTSToFile(word.getEnglishWord(), audioFile.getAbsolutePath());
        }
        
        return audioFile.getAbsolutePath();
    }
    
    private void analyzePronunciation(String recordingPath) {
        if (currentSession == null || currentWordIndex >= currentSession.getWords().size()) {
            return;
        }
        
        Word currentWord = currentSession.getWords().get(currentWordIndex);
        String referenceAudioPath = pronunciationView.getReferenceAudioPath();
        
        // Analyze and score the pronunciation
        viewModel.analyzePronunciation(currentWord, referenceAudioPath, recordingPath);
    }
    
    private void displayPronunciationFeedback(PronunciationScore score) {
        feedbackPanel.setVisibility(View.VISIBLE);
        btnNextWord.setVisibility(View.VISIBLE);
        
        // Set score text based on accuracy
        String feedbackText;
        if (score.getAccuracyScore() >= 80) {
            feedbackText = getString(R.string.feedback_excellent);
            audioFeedbackManager.playPositiveFeedback();
        } else if (score.getAccuracyScore() >= 60) {
            feedbackText = getString(R.string.feedback_good);
            audioFeedbackManager.playPositiveFeedback();
        } else {
            feedbackText = getString(R.string.feedback_needs_practice);
            audioFeedbackManager.playNeutralFeedback();
        }
        
        // Show detailed feedback
        tvFeedback.setText(getString(R.string.pronunciation_feedback, 
                score.getAccuracyScore(),
                score.getPhoneticFeedback(),
                feedbackText));
        
        // Save score in the session
        viewModel.saveWordScore(currentWordIndex, score);
        
        // Check for achievements
        if (score.getAccuracyScore() >= 90) {
            achievementManager.trackPerfectPronunciation();
        }
        
        achievementManager.trackPronunciationPractice();
    }
    
    private void moveToNextWord() {
        currentWordIndex++;
        setupCurrentWord();
        updateSessionProgress();
    }
    
    private void updateSessionProgress() {
        if (currentSession == null) {
            return;
        }
        
        int totalWords = currentSession.getWords().size();
        int progress = Math.min(currentWordIndex, totalWords);
        
        progressBar.setMax(totalWords);
        progressBar.setProgress(progress);
        
        tvScore.setText(getString(R.string.pronunciation_progress, progress, totalWords));
    }
    
    private void finishSession() {
        // Calculate overall session statistics
        PronunciationSessionStats stats = viewModel.calculateSessionStats();
        
        // Save final session data
        viewModel.saveSession();
        
        // Show session summary dialog
        PronunciationSessionSummaryDialog summaryDialog = new PronunciationSessionSummaryDialog(this, stats);
        summaryDialog.setOnDismissListener(dialog -> {
            // Return to main screen
            finish();
        });
        summaryDialog.show();
        
        // Check achievements
        if (stats.getCompletedWords() >= 10) {
            achievementManager.trackPronunciationMilestone();
        }
    }
    
    @Override
    public void onPronunciationItemClicked(Word word, boolean isSelected) {
        // Update the selected words count
        int selectedCount = adapter.getSelectedWords().size();
        btnStartSession.setEnabled(selectedCount > 0);
        
        // Play a quick preview of the word when selected
        if (isSelected) {
            audioManager.speakText(word.getEnglishWord());
        }
    }
    
    @Override
    public void onPronunciationItemPlayClicked(Word word) {
        // Play pronunciation of the word for preview
        audioManager.speakText(word.getEnglishWord());
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioManager.release();
        if (ttsRecorder != null) {
            ttsRecorder.shutdown();
        }
    }
}