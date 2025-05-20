package com.example.englishhindi.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.preference.PreferenceManager;

import com.example.englishhindi.model.AppSettings;

import java.util.Locale;

/**
 * Utility class to manage language settings throughout the app
 */
public class LanguageManager {
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_HINDI = "hi";
    
    private final Context context;
    private final AppSettings appSettings;
    
    public LanguageManager(Context context) {
        this.context = context.getApplicationContext();
        this.appSettings = AppSettings.getInstance(context);
    }
    
    /**
     * Apply the current language settings to the provided context
     */
    public void applyLanguageSettings() {
        boolean useHindiInterface = appSettings.useHindiInterface();
        String languageCode = useHindiInterface ? LANGUAGE_HINDI : LANGUAGE_ENGLISH;
        updateResources(languageCode);
    }
    
    /**
     * Toggle the interface language and apply the changes
     * @return true if the language was changed, false otherwise
     */
    public boolean toggleLanguage() {
        boolean currentSetting = appSettings.useHindiInterface();
        boolean newSetting = !currentSetting;
        appSettings.setUseHindiInterface(newSetting);
        
        String languageCode = newSetting ? LANGUAGE_HINDI : LANGUAGE_ENGLISH;
        updateResources(languageCode);
        
        return true;
    }
    
    /**
     * Set a specific language and apply it
     * @param useHindi true to use Hindi, false to use English
     */
    public void setLanguage(boolean useHindi) {
        if (appSettings.useHindiInterface() != useHindi) {
            appSettings.setUseHindiInterface(useHindi);
            String languageCode = useHindi ? LANGUAGE_HINDI : LANGUAGE_ENGLISH;
            updateResources(languageCode);
        }
    }
    
    /**
     * Get the current language code
     * @return LANGUAGE_HINDI or LANGUAGE_ENGLISH
     */
    public String getCurrentLanguageCode() {
        return appSettings.useHindiInterface() ? LANGUAGE_HINDI : LANGUAGE_ENGLISH;
    }
    
    /**
     * Check if Hindi interface is currently active
     * @return true if Hindi interface is active
     */
    public boolean isHindiMode() {
        return appSettings.useHindiInterface();
    }
    
    /**
     * Update the app resources with the selected language
     * @param languageCode the language code to use
     */
    @SuppressLint("NewApi")
    private void updateResources(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }
}