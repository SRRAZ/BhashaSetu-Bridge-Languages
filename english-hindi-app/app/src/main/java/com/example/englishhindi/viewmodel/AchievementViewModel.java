package com.example.englishhindi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.englishhindi.database.AchievementRepository;
import com.example.englishhindi.model.Achievement;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for achievements
 */
public class AchievementViewModel extends AndroidViewModel {
    private final AchievementRepository repository;
    private final LiveData<List<Achievement>> allAchievements;
    private final LiveData<List<Achievement>> unlockedAchievements;
    private final LiveData<List<Achievement>> lockedAchievements;
    private final MutableLiveData<String> selectedCategory = new MutableLiveData<>();
    private final MediatorLiveData<List<Achievement>> filteredAchievements = new MediatorLiveData<>();
    private final MutableLiveData<Achievement> lastUnlockedAchievement = new MutableLiveData<>();
    
    public AchievementViewModel(@NonNull Application application) {
        super(application);
        repository = new AchievementRepository(application);
        
        allAchievements = repository.getAllAchievements();
        unlockedAchievements = repository.getAchievementsByStatus(Achievement.STATUS_UNLOCKED);
        lockedAchievements = repository.getAchievementsByStatus(Achievement.STATUS_LOCKED);
        
        // Set up the filtered achievements - initially show all
        filteredAchievements.addSource(allAchievements, achievements -> {
            String category = selectedCategory.getValue();
            if (category == null || category.isEmpty()) {
                filteredAchievements.setValue(achievements);
            } else {
                filterAchievementsByCategory(achievements, category);
            }
        });
        
        // Set up category filter
        selectedCategory.setValue(""); // Default to all
        selectedCategory.observeForever(category -> {
            if (allAchievements.getValue() != null) {
                if (category == null || category.isEmpty()) {
                    filteredAchievements.setValue(allAchievements.getValue());
                } else {
                    filterAchievementsByCategory(allAchievements.getValue(), category);
                }
            }
        });
    }
    
    private void filterAchievementsByCategory(List<Achievement> achievements, String category) {
        List<Achievement> filtered = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (category.equals(achievement.getCategory())) {
                filtered.add(achievement);
            }
        }
        filteredAchievements.setValue(filtered);
    }
    
    public LiveData<List<Achievement>> getAllAchievements() {
        return allAchievements;
    }
    
    public LiveData<List<Achievement>> getUnlockedAchievements() {
        return unlockedAchievements;
    }
    
    public LiveData<List<Achievement>> getLockedAchievements() {
        return lockedAchievements;
    }
    
    public LiveData<List<Achievement>> getFilteredAchievements() {
        return filteredAchievements;
    }
    
    public void setSelectedCategory(String category) {
        selectedCategory.setValue(category);
    }
    
    public String getSelectedCategory() {
        return selectedCategory.getValue();
    }
    
    public LiveData<Achievement> getAchievementById(String id) {
        return repository.getAchievementById(id);
    }
    
    public LiveData<Integer> getUnlockedCount() {
        return Transformations.map(unlockedAchievements, List::size);
    }
    
    public LiveData<Integer> getTotalCount() {
        return Transformations.map(allAchievements, List::size);
    }
    
    public void updateAchievement(Achievement achievement) {
        repository.update(achievement);
    }
    
    public void incrementAchievementProgress(String id) {
        Achievement achievement = null;
        if (allAchievements.getValue() != null) {
            for (Achievement a : allAchievements.getValue()) {
                if (a.getId().equals(id)) {
                    achievement = a;
                    break;
                }
            }
        }
        
        if (achievement != null && achievement.getStatus() == Achievement.STATUS_LOCKED) {
            // Check if this increment will unlock the achievement
            if (achievement.getProgressCurrent() + 1 >= achievement.getProgressTarget()) {
                // This will unlock the achievement
                setLastUnlockedAchievement(achievement);
            }
        }
        
        repository.incrementProgress(id);
    }
    
    public void updateProgress(String id, int progress) {
        Achievement achievement = null;
        if (allAchievements.getValue() != null) {
            for (Achievement a : allAchievements.getValue()) {
                if (a.getId().equals(id)) {
                    achievement = a;
                    break;
                }
            }
        }
        
        if (achievement != null && achievement.getStatus() == Achievement.STATUS_LOCKED) {
            // Check if this update will unlock the achievement
            if (progress >= achievement.getProgressTarget()) {
                // This will unlock the achievement
                setLastUnlockedAchievement(achievement);
            }
        }
        
        repository.updateProgress(id, progress);
    }
    
    public LiveData<Achievement> getLastUnlockedAchievement() {
        return lastUnlockedAchievement;
    }
    
    public void setLastUnlockedAchievement(Achievement achievement) {
        lastUnlockedAchievement.setValue(achievement);
    }
    
    public void clearLastUnlockedAchievement() {
        lastUnlockedAchievement.setValue(null);
    }
    
    public int getTotalPoints() {
        return repository.getTotalPoints();
    }
}