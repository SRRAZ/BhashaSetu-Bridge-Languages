package com.bhashasetu.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bhashasetu.app.R;
import com.bhashasetu.app.model.AppSettings;
import com.bhashasetu.app.util.LanguageManagerLegacy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

/**
 * Custom view for switching between English and Hindi interfaces
 */
public class LanguageSwitcherView extends FrameLayout {

    private MaterialButtonToggleGroup toggleGroup;
    private MaterialButton englishButton;
    private MaterialButton hindiButton;
    private LanguageManagerLegacy languageManager;
    private LanguageSwitchListener listener;

    public LanguageSwitcherView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LanguageSwitcherView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LanguageSwitcherView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.view_language_switcher, this, true);
        
        // Initialize language manager
        languageManager = new LanguageManagerLegacy(context);
        
        // Get view references
        toggleGroup = findViewById(R.id.language_toggle);
        englishButton = findViewById(R.id.toggle_english);
        hindiButton = findViewById(R.id.toggle_hindi);
        
        // Set initial state based on current language setting
        boolean isHindiMode = AppSettings.getInstance(context).useHindiInterface();
        toggleGroup.check(isHindiMode ? R.id.toggle_hindi : R.id.toggle_english);
        
        // Set listener for language changes
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    boolean useHindi = checkedId == R.id.toggle_hindi;
                    
                    // Update language setting
                    languageManager.setLanguage(useHindi);
                    
                    // Notify listener if set
                    if (listener != null) {
                        listener.onLanguageChanged(useHindi);
                    }
                    
                    // Restart the activity to apply language change
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        Intent intent = activity.getIntent();
                        activity.finish();
                        activity.startActivity(intent);
                        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }
            }
        });
    }
    
    /**
     * Set a listener for language change events
     */
    public void setOnLanguageChangeListener(LanguageSwitchListener listener) {
        this.listener = listener;
    }
    
    /**
     * Interface for language change callbacks
     */
    public interface LanguageSwitchListener {
        void onLanguageChanged(boolean isHindi);
    }
}