package com.bhashasetu.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.bhashasetu.app.util.LanguageUtils;
import com.bhashasetu.app.view.HindiTextView;

/**
 * Activity for language settings that allows users to choose between English and Hindi interfaces.
 */
public class LanguageSettingsActivity extends AppCompatActivity {

    private RadioGroup radioGroupLanguage;
    private RadioButton radioEnglish;
    private RadioButton radioHindi;
    private Button buttonApply;
    private Button buttonCancel;
    private TextView titleTextView;
    private HindiTextView hindiTitleTextView;
    private TextView descriptionTextView;
    private HindiTextView hindiDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_settings);

        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.language_preference);
        }

        // Initialize UI components
        titleTextView = findViewById(R.id.text_view_title);
        hindiTitleTextView = findViewById(R.id.text_view_title_hindi);
        descriptionTextView = findViewById(R.id.text_view_description);
        hindiDescriptionTextView = findViewById(R.id.text_view_description_hindi);
        radioGroupLanguage = findViewById(R.id.radio_group_language);
        radioEnglish = findViewById(R.id.radio_english);
        radioHindi = findViewById(R.id.radio_hindi);
        buttonApply = findViewById(R.id.button_apply);
        buttonCancel = findViewById(R.id.button_cancel);

        // Get the current language preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useHindiInterface = preferences.getBoolean("use_hindi_interface", false);

        // Set the initial radio button selection
        if (useHindiInterface) {
            radioHindi.setChecked(true);
            // Show Hindi texts
            titleTextView.setVisibility(View.GONE);
            hindiTitleTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setVisibility(View.GONE);
            hindiDescriptionTextView.setVisibility(View.VISIBLE);
        } else {
            radioEnglish.setChecked(true);
            // Show English texts
            titleTextView.setVisibility(View.VISIBLE);
            hindiTitleTextView.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.VISIBLE);
            hindiDescriptionTextView.setVisibility(View.GONE);
        }

        // Set up click listeners
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLanguagePreference();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Save the selected language preference and restart the app.
     */
    private void saveLanguagePreference() {
        // Get the selected language
        boolean useHindiInterface = radioHindi.isChecked();

        // Save the preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("use_hindi_interface", useHindiInterface).apply();

        // Set the app language
        LanguageUtils.setLanguage(this, useHindiInterface ? "hi" : "en");

        // Restart the app to apply the new language
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }
}