package com.example.englishhindi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.englishhindi.database.AchievementRepository;
import com.example.englishhindi.model.Achievement;

import java.util.List;

/**
 * ViewModel for achievements.
 */
public class AchievementViewModel extends AndroidViewModel {

    private AchievementRepository repository;
    private LiveData<List<Achievement>> allAchievements;
    private LiveData<List<Achievement>> unlockedAchievements;
    private LiveData<Integer> totalPoints;
    private LiveData<Integer> unlockedCount;
    private LiveData<Integer> totalCount;

    /**
     * Constructor.
     * 
     * @param application The application context
     */
    public AchievementViewModel(@NonNull Application application) {
        super(application);
        repository = new AchievementRepository(application);
        allAchievements = repository.getAllAchievements();
        unlockedAchievements = repository.getUnlockedAchievements();
        totalPoints = repository.getTotalPoints();
        unlockedCount = repository.getUnlockedCount();
        totalCount = repository.getTotalCount();
    }

    /**
     * Get all achievements.
     * 
     * @return LiveData list of all achievements
     */
    public LiveData<List<Achievement>> getAllAchievements() {
        return allAchievements;
    }

    /**
     * Get all unlocked achievements.
     * 
     * @return LiveData list of unlocked achievements
     */
    public LiveData<List<Achievement>> getUnlockedAchievements() {
        return unlockedAchievements;
    }

    /**
     * Get achievements by type.
     * 
     * @param type The achievement type
     * @return LiveData list of achievements of the specified type
     */
    public LiveData<List<Achievement>> getAchievementsByType(String type) {
        return repository.getAchievementsByType(type);
    }

    /**
     * Get an achievement by ID.
     * 
     * @param id The achievement ID
     * @return LiveData with the achievement
     */
    public LiveData<Achievement> getAchievementById(String id) {
        return repository.getAchievementById(id);
    }

    /**
     * Get the total achievement points.
     * 
     * @return LiveData with the total points
     */
    public LiveData<Integer> getTotalPoints() {
        return totalPoints;
    }

    /**
     * Get the count of unlocked achievements.
     * 
     * @return LiveData with the unlocked count
     */
    public LiveData<Integer> getUnlockedCount() {
        return unlockedCount;
    }

    /**
     * Get the total count of achievements.
     * 
     * @return LiveData with the total count
     */
    public LiveData<Integer> getTotalCount() {
        return totalCount;
    }

    /**
     * Update an achievement's progress.
     * 
     * @param achievementId The achievement ID
     * @param increment The amount to increment progress by
     * @param callback Callback to be notified if the achievement was unlocked
     */
    public void updateAchievementProgress(String achievementId, int increment, 
                                         AchievementRepository.OnAchievementUnlockedListener callback) {
        repository.updateAchievementProgress(achievementId, increment, callback);
    }

    /**
     * Check and update progress for all achievements of a specific type.
     * 
     * @param type The achievement type
     * @param progressToAdd The amount to add to the progress
     * @param callback Callback to be notified when an achievement is unlocked
     */
    public void checkTypeAchievements(String type, int progressToAdd, 
                                     AchievementRepository.OnAchievementUnlockedListener callback) {
        repository.checkTypeAchievements(type, progressToAdd, callback);
    }

    /**
     * Unlock an achievement.
     * 
     * @param achievementId The achievement ID
     * @param callback Callback to be notified when the achievement is unlocked
     */
    public void unlockAchievement(String achievementId, 
                                 AchievementRepository.OnAchievementUnlockedListener callback) {
        repository.unlockAchievement(achievementId, callback);
    }
}