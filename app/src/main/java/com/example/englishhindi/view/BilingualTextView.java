package com.bhashasetu.app.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.bhashasetu.app.R;

/**
 * A custom view that displays text in both English and Hindi based on the user's preference.
 * This makes it easy to create bilingual UI elements throughout the app.
 */
public class BilingualTextView extends LinearLayout {

    private TextView englishTextView;
    private HindiTextView hindiTextView;
    private boolean useHindiInterface = false;

    public BilingualTextView(Context context) {
        super(context);
        init(context, null);
    }

    public BilingualTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BilingualTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Set vertical orientation for the layout
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.view_bilingual_text, this, true);

        // Get the TextViews
        englishTextView = findViewById(R.id.text_view_english);
        hindiTextView = findViewById(R.id.text_view_hindi);

        // Get the language preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        useHindiInterface = preferences.getBoolean("use_hindi_interface", false);

        // Process custom attributes
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BilingualTextView);

            String englishText = a.getString(R.styleable.BilingualTextView_englishText);
            String hindiText = a.getString(R.styleable.BilingualTextView_hindiText);

            if (englishText != null) {
                englishTextView.setText(englishText);
            }

            if (hindiText != null) {
                hindiTextView.setText(hindiText);
            }

            int textStyle = a.getInt(R.styleable.BilingualTextView_textStyle, 1);
            switch (textStyle) {
                case 0: // Small
                    englishTextView.setTextAppearance(android.R.style.TextAppearance_Small);
                    hindiTextView.setTextAppearance(R.style.HindiTextAppearance_Small);
                    break;
                case 1: // Medium (default)
                    englishTextView.setTextAppearance(android.R.style.TextAppearance_Medium);
                    hindiTextView.setTextAppearance(R.style.HindiTextAppearance_Medium);
                    break;
                case 2: // Large
                    englishTextView.setTextAppearance(android.R.style.TextAppearance_Large);
                    hindiTextView.setTextAppearance(R.style.HindiTextAppearance_Large);
                    break;
                case 3: // Header
                    englishTextView.setTextSize(20);
                    englishTextView.setTypeface(englishTextView.getTypeface(), android.graphics.Typeface.BOLD);
                    hindiTextView.setTextAppearance(R.style.HindiTextAppearance_Header);
                    break;
                case 4: // Title
                    englishTextView.setTextSize(24);
                    englishTextView.setTypeface(englishTextView.getTypeface(), android.graphics.Typeface.BOLD);
                    hindiTextView.setTextAppearance(R.style.HindiTextAppearance_Title);
                    break;
            }

            a.recycle();
        }

        // Update visibility based on the interface language
        updateVisibility();
    }

    /**
     * Set the English text.
     * 
     * @param text The English text to display
     */
    public void setEnglishText(String text) {
        englishTextView.setText(text);
    }

    /**
     * Set the Hindi text.
     * 
     * @param text The Hindi text to display
     */
    public void setHindiText(String text) {
        hindiTextView.setText(text);
    }

    /**
     * Set both English and Hindi text.
     * 
     * @param englishText The English text to display
     * @param hindiText The Hindi text to display
     */
    public void setBilingualText(String englishText, String hindiText) {
        englishTextView.setText(englishText);
        hindiTextView.setText(hindiText);
    }

    /**
     * Update the interface language preference and refresh the view.
     * 
     * @param useHindi Whether to use Hindi as the primary interface language
     */
    public void setUseHindiInterface(boolean useHindi) {
        this.useHindiInterface = useHindi;
        updateVisibility();
    }

    /**
     * Update the visibility of the English and Hindi TextViews based on the interface language.
     */
    private void updateVisibility() {
        if (useHindiInterface) {
            englishTextView.setVisibility(GONE);
            hindiTextView.setVisibility(VISIBLE);
        } else {
            englishTextView.setVisibility(VISIBLE);
            hindiTextView.setVisibility(GONE);
        }
    }

    /**
     * Force the view to refresh its language display.
     */
    public void refreshLanguage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        useHindiInterface = preferences.getBoolean("use_hindi_interface", false);
        updateVisibility();
    }
}