package com.example.englishhindi;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.preference.PreferenceManager;
import androidx.room.Room;

import com.example.englishhindi.database.WordDatabase;
import com.example.englishhindi.util.LanguageUtils;

import java.util.Locale;

/**
 * Application class for the English-Hindi Learning app.
 * Handles initialization of database, preferences, and language configuration.
 */
public class EnglishHindiApplication extends Application {

    private WordDatabase database;
    private static EnglishHindiApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Initialize the database
        database = Room.databaseBuilder(getApplicationContext(), WordDatabase.class, "word_database")
                .fallbackToDestructiveMigration()
                .build();

        // Apply the saved language preference
        LanguageUtils.applyLanguage(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        // Apply the language configuration before attaching the base context
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    /**
     * Update the base context locale based on the saved language preference.
     * 
     * @param context The context to update
     * @return The updated context with the correct locale
     */
    private Context updateBaseContextLocale(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean useHindiInterface = preferences.getBoolean("use_hindi_interface", false);
        String language = useHindiInterface ? "hi" : "en";
        
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        
        return context.createConfigurationContext(config);
    }

    /**
     * Get the database instance.
     * 
     * @return The WordDatabase instance
     */
    public WordDatabase getDatabase() {
        return database;
    }

    /**
     * Get the application instance.
     * 
     * @return The EnglishHindiApplication instance
     */
    public static EnglishHindiApplication getInstance() {
        return instance;
    }
}