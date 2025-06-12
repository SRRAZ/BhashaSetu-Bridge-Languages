package com.bhashasetu.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bhashasetu.app.R;
import com.bhashasetu.app.adapter.RecommendedSkillAdapter;
import com.bhashasetu.app.adapter.RecommendedWordAdapter;
import com.bhashasetu.app.manager.UserProgressManager;
import com.bhashasetu.app.model.DifficultyManager;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.ui.DifficultySettingsDialog;
import com.bhashasetu.app.ui.LevelUpDialog;
import com.bhashasetu.app.ui.SkillProgressView;
import com.bhashasetu.app.ui.XpTrackerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fragment for displaying user progress, skill levels, and personalized recommendations.
 * Also provides access to difficulty customization.
 */
public class UserProgressFragment extends Fragment {

    private UserProgressManager progressManager;
    
    // UI Components
    private XpTrackerView xpTracker;
    private TextView tvDaysStreak;
    private TextView tvWordsMastered;
    private TextView tvWeeklyGoal;
    
    private SkillProgressView skillVocabulary;
    private SkillProgressView skillPronunciation;
    private SkillProgressView skillGrammar;
    private SkillProgressView skillListening;
    private SkillProgressView skillReading;
    private SkillProgressView skillWriting;
    
    private RecyclerView recyclerRecommendedSkills;
    private RecyclerView recyclerRecommendedWords;
    
    private Button btnMoreWords;
    private Button btnCustomizeDifficulty;
    
    private RecommendedSkillAdapter skillAdapter;
    private RecommendedWordAdapter wordAdapter;

    public UserProgressFragment() {
        // Required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressManager = UserProgressManager.getInstance(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize UI components
        initializeViews(view);
        
        // Set up recycler views
        setupRecyclerViews();
        
        // Observe user progress
        observeUserProgress();
        
        // Set up button listeners
        setupButtonListeners();
    }
    
    private void initializeViews(View view) {
        // XP and general progress
        xpTracker = view.findViewById(R.id.xp_tracker);
        tvDaysStreak = view.findViewById(R.id.tv_days_streak);
        tvWordsMastered = view.findViewById(R.id.tv_words_mastered);
        tvWeeklyGoal = view.findViewById(R.id.tv_weekly_goal);
        
        // Skill progress views
        skillVocabulary = view.findViewById(R.id.skill_vocabulary);
        skillPronunciation = view.findViewById(R.id.skill_pronunciation);
        skillGrammar = view.findViewById(R.id.skill_grammar);
        skillListening = view.findViewById(R.id.skill_listening);
        skillReading = view.findViewById(R.id.skill_reading);
        skillWriting = view.findViewById(R.id.skill_writing);
        
        // Recycler views
        recyclerRecommendedSkills = view.findViewById(R.id.recycler_recommended_skills);
        recyclerRecommendedWords = view.findViewById(R.id.recycler_recommended_words);
        
        // Buttons
        btnMoreWords = view.findViewById(R.id.btn_more_words);
        btnCustomizeDifficulty = view.findViewById(R.id.btn_customize_difficulty);
        
        // Set up XP tracker
        xpTracker.setOnLevelUpListener((oldLevel, newLevel) -> {
            showLevelUpDialog(newLevel);
        });
    }
    
    private void setupRecyclerViews() {
        // Set up recommended skills recycler
        recyclerRecommendedSkills.setLayoutManager(new LinearLayoutManager(requireContext()));
        skillAdapter = new RecommendedSkillAdapter(requireContext(), new ArrayList<>());
        skillAdapter.setOnSkillClickListener(skillId -> {
            // Navigate to the skill activity
            navigateToSkillActivity(skillId);
        });
        recyclerRecommendedSkills.setAdapter(skillAdapter);
        
        // Set up recommended words recycler
        recyclerRecommendedWords.setLayoutManager(new LinearLayoutManager(requireContext()));
        wordAdapter = new RecommendedWordAdapter(requireContext(), new ArrayList<>());
        wordAdapter.setOnWordClickListener(word -> {
            // Navigate to practice word
            startWordPractice(word);
        });
        recyclerRecommendedWords.setAdapter(wordAdapter);
    }
    
    private void observeUserProgress() {
        progressManager.getUserProgressLiveData().observe(getViewLifecycleOwner(), this::updateUI);
    }
    
    private void updateUI(UserProgress progress) {
        if (progress == null) {
            return;
        }
        
        // Update XP tracker
        int totalXp = progress.getTotalXp();
        int level = progress.getCurrentLevel();
        xpTracker.setXpAndLevel(totalXp, level, true);
        
        // Update stats
        tvDaysStreak.setText(String.valueOf(progress.getCurrentStreak()));
        tvWordsMastered.setText(String.valueOf(progress.getMasteredWordIds().size()));
        tvWeeklyGoal.setText(progress.getWeeklyTargetCompletion() + "%");
        
        // Update skill progress
        Map<String, Integer> skillProgress = progress.getSkillProgress();
        skillVocabulary.setProgress(skillProgress.getOrDefault(DifficultyManager.SKILL_VOCABULARY, 0));
        skillPronunciation.setProgress(skillProgress.getOrDefault(DifficultyManager.SKILL_PRONUNCIATION, 0));
        skillGrammar.setProgress(skillProgress.getOrDefault(DifficultyManager.SKILL_GRAMMAR, 0));
        skillListening.setProgress(skillProgress.getOrDefault(DifficultyManager.SKILL_LISTENING, 0));
        skillReading.setProgress(skillProgress.getOrDefault(DifficultyManager.SKILL_READING, 0));
        skillWriting.setProgress(skillProgress.getOrDefault(DifficultyManager.SKILL_WRITING, 0));
        
        // Get recommended skills
        progressManager.getRecommendedSkills(recommendations -> {
            if (recommendations != null) {
                skillAdapter.updateSkills(recommendations);
            }
        });
        
        // Get recommended words
        progressManager.getRecommendedWords(5, words -> {
            if (words != null) {
                wordAdapter.updateWords(words);
            }
        });
    }
    
    private void setupButtonListeners() {
        btnMoreWords.setOnClickListener(v -> {
            // Show more word recommendations
            progressManager.getRecommendedWords(15, this::showMoreWordsDialog);
        });
        
        btnCustomizeDifficulty.setOnClickListener(v -> {
            // Show difficulty settings dialog
            showDifficultySettingsDialog();
        });
    }
    
    private void showLevelUpDialog(int newLevel) {
        LevelUpDialog dialog = new LevelUpDialog(requireContext(), newLevel);
        dialog.setOnLevelUpContinueListener(level -> {
            // User acknowledged the level up
        });
        dialog.show();
    }
    
    private void showDifficultySettingsDialog() {
        DifficultySettingsDialog dialog = new DifficultySettingsDialog(requireContext());
        dialog.setOnDifficultySettingsChangedListener(settings -> {
            // Refresh UI after settings change
            UserProgress progress = progressManager.getCachedProgress();
            if (progress != null) {
                updateUI(progress);
            }
        });
        dialog.show();
    }
    
    private void showMoreWordsDialog(List<Word> words) {
        // Implementation would depend on how you want to display more words
        // Could be another activity, fragment, or dialog
    }
    
    private void navigateToSkillActivity(String skillId) {
        // Navigate to the appropriate activity based on skill
        switch (skillId) {
            case DifficultyManager.SKILL_VOCABULARY:
                // Start vocabulary activity
                break;
            case DifficultyManager.SKILL_PRONUNCIATION:
                // Start pronunciation activity
                break;
            case DifficultyManager.SKILL_GRAMMAR:
                // Start grammar activity
                break;
            case DifficultyManager.SKILL_LISTENING:
                // Start listening activity
                break;
            case DifficultyManager.SKILL_READING:
                // Start reading activity
                break;
            case DifficultyManager.SKILL_WRITING:
                // Start writing activity
                break;
        }
    }
    
    private void startWordPractice(Word word) {
        // Navigate to word practice activity
    }
}