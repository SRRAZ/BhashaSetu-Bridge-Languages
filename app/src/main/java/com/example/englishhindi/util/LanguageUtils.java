package com.bhashasetu.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Utility class for managing the application language and localization.
 */
public class LanguageUtils {

    private static final String PREFERENCE_LANGUAGE = "app_language";
    
    /**
     * Set the application language.
     * 
     * @param context The application context
     * @param language The language code (e.g., "en" or "hi")
     */
    public static void setLanguage(Context context, String language) {
        // Save the language preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(PREFERENCE_LANGUAGE, language).apply();
        
        // Update the locale configuration
        updateLocale(context, language);
    }
    
    /**
     * Get the current application language code.
     * 
     * @param context The application context
     * @return The language code (e.g., "en" or "hi")
     */
    public static String getLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREFERENCE_LANGUAGE, "en");
    }
    
    /**
     * Check if the current language is Hindi.
     * 
     * @param context The application context
     * @return True if the current language is Hindi, false otherwise
     */
    public static boolean isHindiLanguage(Context context) {
        return getLanguage(context).equals("hi");
    }
    
    /**
     * Apply the current language configuration to the given context.
     * 
     * @param context The context to update
     */
    public static void applyLanguage(Context context) {
        String language = getLanguage(context);
        updateLocale(context, language);
    }
    
    /**
     * Update the locale configuration for the given context.
     * 
     * @param context The context to update
     * @param languageCode The language code to apply
     */
    private static void updateLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config);
        } else {
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }
}