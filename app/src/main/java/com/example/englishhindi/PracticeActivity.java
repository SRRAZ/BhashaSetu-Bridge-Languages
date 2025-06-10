package com.bhashasetu.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.bhashasetu.app.adapter.PracticePagerAdapter;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.viewmodel.UserProgressViewModel;
import com.bhashasetu.app.viewmodel.WordViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class PracticeActivity extends AppCompatActivity {
    
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PracticePagerAdapter pagerAdapter;
    
    private TextView textViewWordsMastered;
    private TextView textViewDailyStreak;
    private TextView textViewDueReviews;
    private ProgressBar progressBarOverall;
    private Button buttonReviewDue;
    
    private UserProgressViewModel userProgressViewModel;
    private WordViewModel wordViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        
        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.practice_title);
        }
        
        // Initialize UI components
        tabLayout = findViewById(R.id.tab_layout_practice);
        viewPager = findViewById(R.id.view_pager_practice);
        textViewWordsMastered = findViewById(R.id.text_view_words_mastered);
        textViewDailyStreak = findViewById(R.id.text_view_daily_streak);
        textViewDueReviews = findViewById(R.id.text_view_due_reviews);
        progressBarOverall = findViewById(R.id.progress_bar_overall);
        buttonReviewDue = findViewById(R.id.button_review_due);
        
        // Set up ViewModels
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        
        // Set up ViewPager and TabLayout
        pagerAdapter = new PracticePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        
        // Get shared preferences for Hindi interface
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
        
        // Set the Hindi title if needed
        if (useHindiInterface) {
            TextView titleTextView = findViewById(R.id.text_view_practice_title);
            TextView hindiTitleTextView = findViewById(R.id.text_view_practice_title_hindi);
            
            titleTextView.setText(R.string.practice_title_hindi);
            hindiTitleTextView.setText(R.string.practice_title);
        }
        
        // Load progress data
        userProgressViewModel.getAllProgressForUser(1).observe(this, new Observer<List<UserProgress>>() {
            @Override
            public void onChanged(List<UserProgress> progressList) {
                updateProgressUI(progressList);
            }
        });
        
        // Set up click listener for review button
        buttonReviewDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get words that are due for review
                userProgressViewModel.getDueWordReviews(1, 10).observe(PracticeActivity.this, new Observer<List<UserProgress>>() {
                    @Override
                    public void onChanged(List<UserProgress> reviewItems) {
                        if (reviewItems != null && !reviewItems.isEmpty()) {
                            Intent intent = new Intent(PracticeActivity.this, FlashcardActivity.class);
                            intent.putExtra("review_mode", true);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
    
    private void updateProgressUI(List<UserProgress> progressList) {
        if (progressList == null || progressList.isEmpty()) {
            return;
        }
        
        // Count words with high mastery level
        int masteredWords = 0;
        int totalWords = 0;
        
        for (UserProgress progress : progressList) {
            if (progress.getItemType().equals("word")) {
                totalWords++;
                if (progress.getCompletionLevel() >= 80) {
                    masteredWords++;
                }
            }
        }
        
        // Update words mastered text
        textViewWordsMastered.setText("Words Mastered: " + masteredWords + "/" + totalWords);
        
        // Calculate and update overall progress
        userProgressViewModel.getOverallProgress(1).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float overallProgress) {
                if (overallProgress != null) {
                    progressBarOverall.setProgress(Math.round(overallProgress));
                }
            }
        });
        
        // Get daily streak (in a real app, this would come from a user stats table)
        // For now, we'll just set a placeholder value
        textViewDailyStreak.setText("Daily Streak: 3");
        
        // Get due reviews count
        userProgressViewModel.getDueWordReviews(1, 100).observe(this, new Observer<List<UserProgress>>() {
            @Override
            public void onChanged(List<UserProgress> reviewItems) {
                int dueCount = reviewItems != null ? reviewItems.size() : 0;
                textViewDueReviews.setText("Words Due for Review: " + dueCount);
                
                // Show/hide the review button based on whether there are due reviews
                if (dueCount > 0) {
                    buttonReviewDue.setVisibility(View.VISIBLE);
                } else {
                    buttonReviewDue.setVisibility(View.GONE);
                }
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
}