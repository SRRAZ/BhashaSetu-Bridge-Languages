package com.bhashasetu.app.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhashasetu.app.util.LanguageManagerLegacy;

import java.util.Locale;

/**
 * Base activity class that handles common functionality like language settings
 * All activities should extend this class to ensure consistent behavior
 */
public abstract class BaseActivity extends AppCompatActivity {
    
    protected LanguageManagerLegacy languageManager;
    
    @Override
    protected void attachBaseContext(Context newBase) {
        // Apply language settings to the context before attaching it
        languageManager = new LanguageManagerLegacy(newBase);
        
        // Get the locale based on settings
        Locale locale = new Locale(languageManager.getCurrentLanguageCode());
        Locale.setDefault(locale);
        
        // Update configuration with the new locale
        Configuration config = new Configuration(newBase.getResources().getConfiguration());
        config.setLocale(locale);
        
        // Create and attach a new context with the updated configuration
        super.attachBaseContext(newBase.createConfigurationContext(config));
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        languageManager = new LanguageManagerLegacy(this);

    }
    
    /**
     * Updates the UI language elements
     * Override in subclasses to update specific UI elements based on language
     */
    protected void updateUILanguage() {
        // Default implementation does nothing
        // Subclasses should override to update specific UI elements
    }
    
    /**
     * Returns whether the current UI language is Hindi
     * @return true if Hindi is the active language
     */
    protected boolean isHindiActive() {
        return languageManager.isHindiMode();
    }
}