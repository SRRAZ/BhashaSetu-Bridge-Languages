package com.example.englishhindi.pronunciation;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.R;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.util.ChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying detailed pronunciation progress statistics.
 * Shows trends, recent practice sessions, and provides improvement suggestions.
 */
public class PronunciationProgressDashboardActivity extends AppCompatActivity {

    private PronunciationViewModel viewModel;
    
    // UI Elements
    private TextView tvTotalSessions;
    private TextView tvTotalWords;
    private TextView tvUniqueWords;
    private TextView tvAverageAccuracy;
    private TextView tvBestScore;
    private TextView tvProficiencyLevel;
    private ProgressBar progressOverall;
    private ChartView chartProgress;
    private RecyclerView recyclerRecentSessions;
    private RecyclerView recyclerImprovementSuggestions;
    private View loadingView;
    private View contentView;
    private View emptyView;
    
    private RecentSessionsAdapter sessionsAdapter;
    private ImprovementSuggestionsAdapter suggestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciation_progress_dashboard);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PronunciationViewModel.class);
        
        // Initialize UI
        initializeViews();
        setupAdapters();
        observeViewModel();
        
        // Load data
        viewModel.loadPronunciationProgress();
    }
    
    private void initializeViews() {
        tvTotalSessions = findViewById(R.id.tv_total_sessions);
        tvTotalWords = findViewById(R.id.tv_total_words);
        tvUniqueWords = findViewById(R.id.tv_unique_words);
        tvAverageAccuracy = findViewById(R.id.tv_average_accuracy);
        tvBestScore = findViewById(R.id.tv_best_score);
        tvProficiencyLevel = findViewById(R.id.tv_proficiency_level);
        progressOverall = findViewById(R.id.progress_overall);
        chartProgress = findViewById(R.id.chart_progress);
        recyclerRecentSessions = findViewById(R.id.recycler_recent_sessions);
        recyclerImprovementSuggestions = findViewById(R.id.recycler_improvement_suggestions);
        loadingView = findViewById(R.id.loading_view);
        contentView = findViewById(R.id.content_view);
        emptyView = findViewById(R.id.empty_view);
        
        // Set up RecyclerViews
        recyclerRecentSessions.setLayoutManager(new LinearLayoutManager(this));
        recyclerImprovementSuggestions.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupAdapters() {
        sessionsAdapter = new RecentSessionsAdapter(this, new ArrayList<>());
        recyclerRecentSessions.setAdapter(sessionsAdapter);
        
        suggestionsAdapter = new ImprovementSuggestionsAdapter(this, new ArrayList<>());
        recyclerImprovementSuggestions.setAdapter(suggestionsAdapter);
    }
    
    private void observeViewModel() {
        // Observe progress stats
        viewModel.getPronunciationProgressStats().observe(this, stats -> {
            if (stats != null) {
                updateProgressUI(stats);
            }
        });
        
        // Observe recent sessions
        viewModel.getRecentSessions().observe(this, sessions -> {
            loadingView.setVisibility(View.GONE);
            
            if (sessions != null && !sessions.isEmpty()) {
                contentView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                sessionsAdapter.updateSessions(sessions);
                
                // Update the chart with session data
                updateChart(sessions);
            } else {
                contentView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        });
        
        // Observe improvement suggestions
        viewModel.getImprovementSuggestions().observe(this, suggestions -> {
            if (suggestions != null && !suggestions.isEmpty()) {
                suggestionsAdapter.updateSuggestions(suggestions);
                findViewById(R.id.suggestions_container).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.suggestions_container).setVisibility(View.GONE);
            }
        });
    }
    
    private void updateProgressUI(PronunciationProgressStats stats) {
        // Set text for the statistics
        tvTotalSessions.setText(String.valueOf(stats.getSessionCount()));
        tvTotalWords.setText(String.valueOf(stats.getWordAttempts()));
        tvUniqueWords.setText(String.valueOf(stats.getUniqueWordsPracticed()));
        tvAverageAccuracy.setText(String.format("%.1f%%", stats.getAverageAccuracyScore()));
        tvBestScore.setText(String.format("%.1f%%", stats.getBestAccuracyScore()));
        tvProficiencyLevel.setText(stats.getProficiencyLevel());
        
        // Update progress bar
        progressOverall.setProgress(stats.getOverallProgressPercentage());
    }
    
    private void updateChart(List<PronunciationSession> sessions) {
        // Extract data for chart
        List<Float> accuracyData = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        
        for (int i = 0; i < Math.min(sessions.size(), 7); i++) {
            PronunciationSession session = sessions.get(i);
            accuracyData.add((float) session.getAverageAccuracyScore());
            
            // Format date for label (e.g., "May 5")
            String label = formatDate(session.getStartTime());
            labels.add(label);
        }
        
        // Update chart with data
        chartProgress.setData(accuracyData, labels);
    }
    
    private String formatDate(java.util.Date date) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MMM d", java.util.Locale.getDefault());
        return format.format(date);
    }
    
    /**
     * Adapter for displaying recent pronunciation sessions
     */
    private class RecentSessionsAdapter extends RecyclerView.Adapter<RecentSessionsAdapter.SessionViewHolder> {
        private Context context;
        private List<PronunciationSession> sessions;
        
        // Implementation details omitted for brevity
        // Would include view holder, binding logic, etc.
    }
    
    /**
     * Adapter for displaying improvement suggestions
     */
    private class ImprovementSuggestionsAdapter extends RecyclerView.Adapter<ImprovementSuggestionsAdapter.SuggestionViewHolder> {
        private Context context;
        private List<Word> suggestedWords;
        
        // Implementation details omitted for brevity
        // Would include view holder, binding logic, etc.
    }
}