package com.bhashasetu.app;

import android.app.Application;

import com.bhashasetu.app.database.WordDatabase;
import com.bhashasetu.app.database.WordDatabase;
import com.bhashasetu.app.util.LanguageManager;
public class EnglishHindiApplication extends Application {
    
    private static EnglishHindiApplication instance;
    private LanguageManager languageManager;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        // Initialize language manager and apply settings
        languageManager = new LanguageManager(this);
        languageManager.applyLanguageSettings();
        
        // Initialize database
        WordDatabase.getInstance(this);
    }
    
    public static EnglishHindiApplication getInstance() {
        return instance;
    }
    
    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}