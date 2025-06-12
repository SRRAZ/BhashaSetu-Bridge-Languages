package com.bhashasetu.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.adapter.GameCategoryAdapter;
import com.bhashasetu.app.audio.AudioFeedbackManager;
import com.bhashasetu.app.games.FlashcardGameActivity;
import com.bhashasetu.app.games.PictureMatchActivity;
import com.bhashasetu.app.games.WordAssociationActivity;
import com.bhashasetu.app.games.WordScrambleActivity;
import com.bhashasetu.app.model.GameCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying and selecting vocabulary games.
 */
public class GamesActivity extends BaseActivity implements GameCategoryAdapter.OnGameSelectedListener {

    private RecyclerView recyclerViewGames;
    private GameCategoryAdapter adapter;
    private AudioFeedbackManager audioFeedbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.vocabulary_games);
        }

        // Initialize audio feedback manager
        audioFeedbackManager = AudioFeedbackManager.getInstance(this);

        // Set up RecyclerView
        recyclerViewGames = findViewById(R.id.recycler_view_games);
        recyclerViewGames.setLayoutManager(new GridLayoutManager(this, 2));

        // Create game categories
        List<GameCategory> gameCategories = createGameCategories();

        // Set up adapter
        adapter = new GameCategoryAdapter(this, gameCategories, this);
        recyclerViewGames.setAdapter(adapter);
    }

    /**
     * Create the list of game categories.
     */
    private List<GameCategory> createGameCategories() {
        List<GameCategory> categories = new ArrayList<>();

        // Word Scramble game
        categories.add(new GameCategory(
                R.drawable.ic_game_scramble,
                getString(R.string.word_scramble),
                getString(R.string.word_scramble_description),
                GameCategory.GameType.WORD_SCRAMBLE));

        // Picture Match game
        categories.add(new GameCategory(
                R.drawable.ic_game_picture_match,
                getString(R.string.picture_word_match),
                getString(R.string.picture_match_description),
                GameCategory.GameType.PICTURE_MATCH));

        // Word Association game
        categories.add(new GameCategory(
                R.drawable.ic_game_association,
                getString(R.string.word_association),
                getString(R.string.word_association_description),
                GameCategory.GameType.WORD_ASSOCIATION));

        // Flashcards game
        categories.add(new GameCategory(
                R.drawable.ic_game_flashcards,
                getString(R.string.flashcards),
                getString(R.string.flashcards_description),
                GameCategory.GameType.FLASHCARDS));

        return categories;
    }

    /**
     * Handle game selection.
     */
    @Override
    public void onGameSelected(GameCategory gameCategory) {
        // Play tap sound
        audioFeedbackManager.playTapSound();

        // Show difficulty selection dialog
        showDifficultySelectionDialog(gameCategory);
    }

    /**
     * Show dialog to select difficulty level.
     */
    private void showDifficultySelectionDialog(GameCategory gameCategory) {
        DifficultySelectionDialogFragment dialogFragment = 
                DifficultySelectionDialogFragment.newInstance(gameCategory.getTitle());
        
        dialogFragment.setOnDifficultySelectedListener((difficulty, category) -> {
            // Start the appropriate game activity
            startGameActivity(gameCategory.getGameType(), difficulty, category);
        });
        
        dialogFragment.show(getSupportFragmentManager(), "difficulty_selection");
    }

    /**
     * Start the appropriate game activity.
     */
    private void startGameActivity(GameCategory.GameType gameType, int difficulty, String category) {
        Intent intent = null;
        
        switch (gameType) {
            case WORD_SCRAMBLE:
                intent = new Intent(this, WordScrambleActivity.class);
                break;
                
            case PICTURE_MATCH:
                intent = new Intent(this, PictureMatchActivity.class);
                break;
                
            case WORD_ASSOCIATION:
                intent = new Intent(this, WordAssociationActivity.class);
                break;
                
            case FLASHCARDS:
                // For flashcards, show mode selection dialog
                showFlashcardModeDialog(difficulty, category);
                return;
        }
        
        if (intent != null) {
            intent.putExtra("difficulty", difficulty);
            if (category != null && !category.isEmpty()) {
                intent.putExtra("category", category);
            }
            startActivity(intent);
        }
    }

    /**
     * Show dialog to select flashcard game mode.
     */
    private void showFlashcardModeDialog(int difficulty, String category) {
        String[] modes = {
                getString(R.string.study_flashcards),
                getString(R.string.flashcard_quiz),
                getString(R.string.spelling_challenge)
        };
        
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.select_game_mode);
        builder.setItems(modes, (dialog, which) -> {
            // Determine the mode based on selection
            String mode;
            switch (which) {
                case 0:
                    mode = FlashcardGameActivity.GameMode.STUDY.name();
                    break;
                case 1:
                    mode = FlashcardGameActivity.GameMode.QUIZ.name();
                    break;
                case 2:
                    mode = FlashcardGameActivity.GameMode.SPELLING.name();
                    break;
                default:
                    mode = FlashcardGameActivity.GameMode.STUDY.name();
                    break;
            }
            
            // Start Flashcard activity with selected mode
            Intent intent = new Intent(this, FlashcardGameActivity.class);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("mode", mode);
            if (category != null && !category.isEmpty()) {
                intent.putExtra("category", category);
            }
            startActivity(intent);
        });
        builder.show();
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