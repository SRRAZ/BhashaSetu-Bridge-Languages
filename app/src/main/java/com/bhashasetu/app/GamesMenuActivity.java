package com.bhashasetu.app;

import android.content.Intent;
import android.os.Bundle;import android.view.MenuItem;import android.view.View;import android.widget.Button;import android.widget.TextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bhashasetu.app.games.PictureMatchActivity;import com.bhashasetu.app.games.WordScrambleActivity;import com.bhashasetu.app.model.PracticeSession;
import com.bhashasetu.app.games.WordScrambleActivity;
import com.bhashasetu.app.model.PracticeSession;
import com.bhashasetu.app.viewmodel.PracticeSessionViewModel;

import java.util.List;

/**
 * Activity showing the menu of all available games.
 */
public class GamesMenuActivity extends AppCompatActivity {

    private PracticeSessionViewModel sessionViewModel;

    // Recent game stats
    private TextView textViewWordScrambleStats;
    private TextView textViewPictureMatchStats;
    private TextView textViewMatchingGameStats;
    private TextView textViewWordsLearned;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu);
        
        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.games);
        }
        
        // Initialize view model
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);
        
        // Initialize UI components
        initializeUI();
        
        // Load stats
        loadStats();
    }
    
    /**
     * Initialize UI components.
     */
    private void initializeUI() {
        // Game cards
        CardView cardWordScramble = findViewById(R.id.card_word_scramble);
        CardView cardPictureMatch = findViewById(R.id.card_picture_match);
        CardView cardMatching = findViewById(R.id.card_matching);
        
        // Stats TextViews
        textViewWordScrambleStats = findViewById(R.id.text_view_word_scramble_stats);
        textViewPictureMatchStats = findViewById(R.id.text_view_picture_match_stats);
        textViewMatchingGameStats = findViewById(R.id.text_view_matching_game_stats);
        textViewWordsLearned = findViewById(R.id.text_view_words_learned);
        
        // Set up click listeners
        cardWordScramble.setOnClickListener(v -> showDifficultyDialog("word_scramble"));
        cardPictureMatch.setOnClickListener(v -> showDifficultyDialog("picture_match"));
        cardMatching.setOnClickListener(v -> showDifficultyDialog("matching"));
        
        // Buttons for each game
        Button buttonWordScramblePlay = findViewById(R.id.button_word_scramble_play);
        Button buttonPictureMatchPlay = findViewById(R.id.button_picture_match_play);
        Button buttonMatchingPlay = findViewById(R.id.button_matching_play);
        
        buttonWordScramblePlay.setOnClickListener(v -> showDifficultyDialog("word_scramble"));
        buttonPictureMatchPlay.setOnClickListener(v -> showDifficultyDialog("picture_match"));
        buttonMatchingPlay.setOnClickListener(v -> showDifficultyDialog("matching"));
    }
    
    /**
     * Load game statistics.
     */
    private void loadStats() {
        // Load Word Scramble stats
        sessionViewModel.getSessionsByType(1, "word_scramble").observe(this, sessions -> {
            if (sessions != null && !sessions.isEmpty()) {
                int totalSessions = sessions.size();
                int totalScore = 0;
                for (PracticeSession session : sessions) {
                    totalScore += session.getScore();
                }
                int avgScore = totalSessions > 0 ? totalScore / totalSessions : 0;
                
                String stats = getString(R.string.sessions_played, totalSessions) +
                        "\n" + getString(R.string.avg_score, avgScore);
                textViewWordScrambleStats.setText(stats);
            } else {
                textViewWordScrambleStats.setText(R.string.no_sessions_yet);
            }
        });
        
        // Load Picture Match stats
        sessionViewModel.getSessionsByType(1, "picture_match").observe(this, sessions -> {
            if (sessions != null && !sessions.isEmpty()) {
                int totalSessions = sessions.size();
                int totalScore = 0;
                for (PracticeSession session : sessions) {
                    totalScore += session.getScore();
                }
                int avgScore = totalSessions > 0 ? totalScore / totalSessions : 0;
                
                String stats = getString(R.string.sessions_played, totalSessions) +
                        "\n" + getString(R.string.avg_score, avgScore);
                textViewPictureMatchStats.setText(stats);
            } else {
                textViewPictureMatchStats.setText(R.string.no_sessions_yet);
            }
        });
        
        // Load Matching Game stats
        sessionViewModel.getSessionsByType(1, "matching").observe(this, sessions -> {
            if (sessions != null && !sessions.isEmpty()) {
                int totalSessions = sessions.size();
                int totalScore = 0;
                for (PracticeSession session : sessions) {
                    totalScore += session.getScore();
                }
                int avgScore = totalSessions > 0 ? totalScore / totalSessions : 0;
                
                String stats = getString(R.string.sessions_played, totalSessions) +
                        "\n" + getString(R.string.avg_score, avgScore);
                textViewMatchingGameStats.setText(stats);
            } else {
                textViewMatchingGameStats.setText(R.string.no_sessions_yet);
            }
        });
        
        // Load total words learned from games
        sessionViewModel.getAllSessions(1).observe(this, sessions -> {
            if (sessions != null && !sessions.isEmpty()) {
                int totalWordsLearned = 0;
                for (PracticeSession session : sessions) {
                    if (session.isCompleted() && 
                        (session.getType().equals("word_scramble") || 
                         session.getType().equals("picture_match") || 
                         session.getType().equals("matching"))) {
                        totalWordsLearned += countCorrectAnswers(session.getResults());
                    }
                }
                
                textViewWordsLearned.setText(getString(R.string.words_learned_count, totalWordsLearned));
            } else {
                textViewWordsLearned.setText(getString(R.string.words_learned_count, 0));
            }
        });
    }
    
    /**
     * Count the number of correct answers in a session.
     * 
     * @param results The session results
     * @return The number of correct answers
     */
    private int countCorrectAnswers(List<Boolean> results) {
        int count = 0;
        for (Boolean result : results) {
            if (result) count++;
        }
        return count;
    }
    
    /**
     * Show a dialog to select difficulty level.
     * 
     * @param gameType The type of game
     */
    private void showDifficultyDialog(String gameType) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.select_difficulty);
        
        // Inflate custom layout
        View difficultyView = getLayoutInflater().inflate(R.layout.dialog_game_difficulty, null);
        builder.setView(difficultyView);
        
        // Get difficulty buttons
        Button buttonEasy = difficultyView.findViewById(R.id.button_easy);
        Button buttonMedium = difficultyView.findViewById(R.id.button_medium);
        Button buttonHard = difficultyView.findViewById(R.id.button_hard);
        
        // Create dialog
        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        
        // Set click listeners
        buttonEasy.setOnClickListener(v -> {
            dialog.dismiss();
            startGame(gameType, 1);
        });
        
        buttonMedium.setOnClickListener(v -> {
            dialog.dismiss();
            startGame(gameType, 3);
        });
        
        buttonHard.setOnClickListener(v -> {
            dialog.dismiss();
            startGame(gameType, 5);
        });
        
        dialog.show();
    }
    
    /**
     * Start a game with the specified type and difficulty.
     * 
     * @param gameType The type of game
     * @param difficulty The difficulty level
     */
    private void startGame(String gameType, int difficulty) {
        Intent intent = null;
        
        switch (gameType) {
            case "word_scramble":
                intent = new Intent(this, WordScrambleActivity.class);
                break;
            case "picture_match":
                intent = new Intent(this, PictureMatchActivity.class);
                break;
            case "matching":
                // TODO: Create MatchingGameActivity
                // intent = new Intent(this, MatchingGameActivity.class);
                break;
        }
        
        if (intent != null) {
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        }
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