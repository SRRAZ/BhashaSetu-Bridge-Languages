package com.example.englishhindi;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.adapter.AchievementAdapter;
import com.example.englishhindi.model.Achievement;
import com.example.englishhindi.ui.AchievementDetailDialog;
import com.example.englishhindi.viewmodel.AchievementViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying all achievements and the user's progress.
 */
public class AchievementsActivity extends BaseActivity implements AchievementAdapter.OnAchievementClickListener {

    private AchievementViewModel achievementViewModel;
    private AchievementAdapter achievementAdapter;
    private List<Achievement> currentAchievements = new ArrayList<>();
    
    private TextView textViewTotalPoints;
    private TextView textViewProgress;
    private ProgressBar progressBarAchievements;
    private Spinner spinnerFilter;
    private Switch switchShowSecret;
    private RecyclerView recyclerViewAchievements;
    
    private String currentFilter = "all";
    private boolean showSecret = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.achievements);
        }

        // Initialize ViewModel
        achievementViewModel = new ViewModelProvider(this).get(AchievementViewModel.class);

        // Find views
        textViewTotalPoints = findViewById(R.id.text_view_total_points);
        textViewProgress = findViewById(R.id.text_view_achievement_progress);
        progressBarAchievements = findViewById(R.id.progress_bar_achievements);
        spinnerFilter = findViewById(R.id.spinner_filter);
        switchShowSecret = findViewById(R.id.switch_show_secret);
        recyclerViewAchievements = findViewById(R.id.recycler_view_achievements);

        // Set up RecyclerView
        recyclerViewAchievements.setLayoutManager(new LinearLayoutManager(this));
        achievementAdapter = new AchievementAdapter(this, currentAchievements, this);
        recyclerViewAchievements.setAdapter(achievementAdapter);

        // Set up filter spinner
        setupFilterSpinner();

        // Set up show secret switch
        switchShowSecret.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showSecret = isChecked;
            achievementAdapter.setShowSecret(showSecret);
        });

        // Observe achievements
        observeAchievements();

        // Observe achievement stats
        observeAchievementStats();
    }

    /**
     * Set up the filter spinner.
     */
    private void setupFilterSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.achievement_filters, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapter);
        
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        currentFilter = "all";
                        break;
                    case 1:
                        currentFilter = Achievement.TYPE_VOCABULARY;
                        break;
                    case 2:
                        currentFilter = Achievement.TYPE_PRONUNCIATION;
                        break;
                    case 3:
                        currentFilter = Achievement.TYPE_GAME;
                        break;
                    case 4:
                        currentFilter = Achievement.TYPE_STREAK;
                        break;
                    case 5:
                        currentFilter = Achievement.TYPE_MASTERY;
                        break;
                    default:
                        currentFilter = "all";
                        break;
                }
                
                loadAchievements();
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Not used
            }
        });
    }

    /**
     * Observe the achievements list.
     */
    private void observeAchievements() {
        // Initially load all achievements
        achievementViewModel.getAllAchievements().observe(this, achievements -> {
            if (achievements != null) {
                // Store for filtering
                loadAchievements();
            }
        });
    }

    /**
     * Load achievements based on the current filter.
     */
    private void loadAchievements() {
        if (currentFilter.equals("all")) {
            achievementViewModel.getAllAchievements().observe(this, achievements -> {
                if (achievements != null) {
                    currentAchievements = achievements;
                    achievementAdapter.setAchievements(achievements);
                }
            });
        } else {
            achievementViewModel.getAchievementsByType(currentFilter).observe(this, achievements -> {
                if (achievements != null) {
                    currentAchievements = achievements;
                    achievementAdapter.setAchievements(achievements);
                }
            });
        }
    }

    /**
     * Observe achievement statistics.
     */
    private void observeAchievementStats() {
        // Observe total points
        achievementViewModel.getTotalPoints().observe(this, totalPoints -> {
            if (totalPoints != null) {
                textViewTotalPoints.setText(getString(R.string.total_points, totalPoints));
            } else {
                textViewTotalPoints.setText(getString(R.string.total_points, 0));
            }
        });
        
        // Observe achievement progress
        achievementViewModel.getUnlockedCount().observe(this, unlockedCount -> {
            achievementViewModel.getTotalCount().observe(this, totalCount -> {
                if (unlockedCount != null && totalCount != null && totalCount > 0) {
                    int progress = (unlockedCount * 100) / totalCount;
                    textViewProgress.setText(getString(
                            R.string.achievements_progress_format, 
                            unlockedCount, totalCount, progress));
                    progressBarAchievements.setProgress(progress);
                } else {
                    textViewProgress.setText(getString(
                            R.string.achievements_progress_format, 0, 0, 0));
                    progressBarAchievements.setProgress(0);
                }
            });
        });
    }

    /**
     * Handle achievement click.
     */
    @Override
    public void onAchievementClick(Achievement achievement) {
        if (achievement == null) return;
        
        // Show achievement detail dialog
        AchievementDetailDialog dialog = new AchievementDetailDialog(this, achievement);
        dialog.show();
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