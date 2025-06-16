package com.bhashasetu.app.ui.viewmodel;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u000eJ\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0017"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/AppSettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "appSettingsRepository", "Lcom/bhashasetu/app/data/repository/AppSettingsRepository;", "<init>", "(Lcom/bhashasetu/app/data/repository/AppSettingsRepository;)V", "settings", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/bhashasetu/app/data/model/AppSettings;", "getSettings", "()Lkotlinx/coroutines/flow/StateFlow;", "updateLanguage", "", "language", "", "updateNotificationsEnabled", "enabled", "", "updateDailyReminderTime", "time", "updateSoundEnabled", "updateVibrationEnabled", "updateSettings", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AppSettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.AppSettingsRepository appSettingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.model.AppSettings> settings = null;
    
    @javax.inject.Inject()
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