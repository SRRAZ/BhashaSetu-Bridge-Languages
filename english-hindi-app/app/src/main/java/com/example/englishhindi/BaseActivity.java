package com.example.englishhindi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.englishhindi.util.LanguageUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Base activity class that handles language configuration and provides common functionality
 * for all activities in the app.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected boolean useHindiInterface = false;
    private Map<Integer, TextView> hindiTextViews = new HashMap<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        // Apply the saved language preference before creating the activity
        LanguageUtils.applyLanguage(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check if Hindi interface is enabled
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Apply language configuration on config changes (like orientation change)
        LanguageUtils.applyLanguage(this);
    }
    
    /**
     * Set up a TextView to use the Hindi text appearance style.
     * 
     * @param textViewId The ID of the TextView
     */
    protected void setupHindiTextView(int textViewId) {
        TextView textView = findViewById(textViewId);
        if (textView != null) {
            textView.setTextAppearance(R.style.HindiTextAppearance);
            hindiTextViews.put(textViewId, textView);
        }
    }
    
    /**
     * Apply Hindi text appearance to all registered Hindi TextViews.
     */
    protected void applyHindiStyles() {
        for (Map.Entry<Integer, TextView> entry : hindiTextViews.entrySet()) {
            entry.getValue().setTextAppearance(R.style.HindiTextAppearance);
        }
    }
    
    /**
     * Utility method to toggle visibility of Hindi/English text based on interface language.
     * 
     * @param englishViewId ID of the English text view
     * @param hindiViewId ID of the Hindi text view
     */
    protected void toggleLanguageViews(int englishViewId, int hindiViewId) {
        View englishView = findViewById(englishViewId);
        View hindiView = findViewById(hindiViewId);
        
        if (englishView != null && hindiView != null) {
            if (useHindiInterface) {
                englishView.setVisibility(View.GONE);
                hindiView.setVisibility(View.VISIBLE);
            } else {
                englishView.setVisibility(View.VISIBLE);
                hindiView.setVisibility(View.GONE);
            }
        }
    }
    
    /**
     * Helper method to handle the back button in the action bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}