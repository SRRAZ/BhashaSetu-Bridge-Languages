package com.bhashasetu.app.ui.viewmodel;

@error.NonExistentClass()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\rJ\u000e\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0007J\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0016"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/AppSettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "appSettingsRepository", "Lcom/bhashasetu/app/data/repository/AppSettingsRepository;", "(Lcom/bhashasetu/app/data/repository/AppSettingsRepository;)V", "settings", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/bhashasetu/app/data/model/AppSettings;", "getSettings", "()Lkotlinx/coroutines/flow/StateFlow;", "updateDailyReminderTime", "", "time", "", "updateLanguage", "language", "updateNotificationsEnabled", "enabled", "", "updateSettings", "updateSoundEnabled", "updateVibrationEnabled", "app_freeDebug"})
public final class AppSettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.AppSettingsRepository appSettingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.model.AppSettings> settings = null;
    
    @error.NonExistentClass()
    public AppSettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.AppSettingsRepository appSettingsRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.model.AppSettings> getSettings() {
        return null;
    }
    
    /**
     * Updates the application language setting
     * @param language The language code to set ("en" or "hi")
     */
    public final void updateLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    /**
     * Updates the notifications enabled setting
     * @param enabled Whether notifications should be enabled
     */
    public final void updateNotificationsEnabled(boolean enabled) {
    }
    
    /**
     * Updates the daily reminder time setting
     * @param time The time for daily reminders (format: "HH:MM")
     */
    public final void updateDailyReminderTime(@org.jetbrains.annotations.NotNull()
    java.lang.String time) {
    }
    
    /**
     * Updates the sound enabled setting
     * @param enabled Whether sound should be enabled
     */
    public final void updateSoundEnabled(boolean enabled) {
    }
    
    /**
     * Updates the vibration enabled setting
     * @param enabled Whether vibration should be enabled
     */
    public final void updateVibrationEnabled(boolean enabled) {
    }
    
    /**
     * Updates all app settings at once
     * @param settings The settings object with new values
     */
    public final void updateSettings(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.AppSettings settings) {
    }
}