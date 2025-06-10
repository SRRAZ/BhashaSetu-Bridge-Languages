package com.bhashasetu.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.preference.PreferenceManager;

import java.util.Locale;

/**
 * Utility class to manage language settings throughout the app.
 * Handles language switching, locale settings, and language preference persistence.
 */
public class LanguageManager {
    
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_HINDI = "hi";
    
    private static final String PREF_LANGUAGE = "app_language";
    
    private final SharedPreferences preferences;
    private final Context context;
    
    public LanguageManager(Context context) {
        this.context = context.getApplicationContext();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    /**
     * Set the app language and update all resources to use that language
     */
    public void setLanguage(String languageCode) {
        preferences.edit().putString(PREF_LANGUAGE, languageCode).apply();
        updateResources(languageCode);
    }
    
    /**
     * Get the currently selected language
     */
    public String getCurrentLanguage() {
        return preferences.getString(PREF_LANGUAGE, LANGUAGE_ENGLISH);
    }
    
    /**
     * Check if Hindi is the current interface language
     */
    public boolean isHindiMode() {
        return LANGUAGE_HINDI.equals(getCurrentLanguage());
    }
    
    /**
     * Update app resources with the selected language
     */
    @SuppressLint("NewApi")
    private void updateResources(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }
    
    /**
     * Apply the current language to a new context (used for Activities)
     */
    public Context applyLanguageContext(Context baseContext) {
        String language = getCurrentLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        
        Context updatedContext = baseContext;
        
        Configuration config = new Configuration(baseContext.getResources().getConfiguration());
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            updatedContext = baseContext.createConfigurationContext(config);
        } else {
            config.locale = locale;
            baseContext.getResources().updateConfiguration(config, baseContext.getResources().getDisplayMetrics());
        }
        
        return updatedContext;
    }
}