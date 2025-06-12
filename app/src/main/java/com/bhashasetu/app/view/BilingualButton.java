package com.bhashasetu.app.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;

import com.bhashasetu.app.R;

/**
 * A custom button that displays text in either English or Hindi based on the user's preference.
 * This makes it easy to create bilingual UI elements throughout the app.
 */
public class BilingualButton extends AppCompatButton {

    private String englishText;
    private String hindiText;
    private boolean useHindiInterface = false;

    public BilingualButton(Context context) {
        super(context);
        init(context, null);
    }

    public BilingualButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BilingualButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Get the language preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        useHindiInterface = preferences.getBoolean("use_hindi_interface", false);

        // Apply default Hindi button styling
        if (useHindiInterface) {
            setTextAppearance(R.style.HindiButtonStyle);
        }

        // Process custom attributes
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BilingualTextView);

            englishText = a.getString(R.styleable.BilingualTextView_englishText);
            hindiText = a.getString(R.styleable.BilingualTextView_hindiText);

            a.recycle();
        }

        // Set the initial text based on the language preference
        updateText();
    }

    /**
     * Set the English text for the button.
     * 
     * @param text The English text to display
     */
    public void setEnglishText(String text) {
        this.englishText = text;
        updateText();
    }

    /**
     * Set the Hindi text for the button.
     * 
     * @param text The Hindi text to display
     */
    public void setHindiText(String text) {
        this.hindiText = text;
        updateText();
    }

    /**
     * Set both English and Hindi text for the button.
     * 
     * @param englishText The English text to display
     * @param hindiText The Hindi text to display
     */
    public void setBilingualText(String englishText, String hindiText) {
        this.englishText = englishText;
        this.hindiText = hindiText;
        updateText();
    }

    /**
     * Update the interface language preference and refresh the view.
     * 
     * @param useHindi Whether to use Hindi as the primary interface language
     */
    public void setUseHindiInterface(boolean useHindi) {
        this.useHindiInterface = useHindi;
        updateText();
        
        // Apply Hindi styling if using Hindi interface
        if (useHindi) {
            setTextAppearance(R.style.HindiButtonStyle);
        }
    }

    /**
     * Update the button text based on the interface language.
     */
    private void updateText() {
        if (useHindiInterface && hindiText != null && !hindiText.isEmpty()) {
            setText(hindiText);
        } else if (englishText != null && !englishText.isEmpty()) {
            setText(englishText);
        }
    }

    /**
     * Force the button to refresh its language display.
     */
    public void refreshLanguage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        useHindiInterface = preferences.getBoolean("use_hindi_interface", false);
        updateText();
        
        // Apply Hindi styling if using Hindi interface
        if (useHindiInterface) {
            setTextAppearance(R.style.HindiButtonStyle);
        }
    }
}