package com.example.englishhindi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.englishhindi.R;
import com.example.englishhindi.model.AppSettings;

/**
 * Custom view that displays text in both English and Hindi,
 * with appropriate styling and emphasis based on the selected language.
 */
public class BilingualTextView extends LinearLayout {

    private TextView primaryTextView;
    private TextView secondaryTextView;
    
    private String englishText;
    private String hindiText;
    
    private float primaryTextSize;
    private float secondaryTextSize;
    
    private boolean isHindiMode;

    public BilingualTextView(Context context) {
        super(context);
        init(context, null);
    }

    public BilingualTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BilingualTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs) {
        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.view_bilingual_text, this, true);
        
        // Get view references
        primaryTextView = findViewById(R.id.text_view_primary);
        secondaryTextView = findViewById(R.id.text_view_secondary);
        
        // Default text sizes
        primaryTextSize = getResources().getDimension(R.dimen.text_size_primary);
        secondaryTextSize = getResources().getDimension(R.dimen.text_size_secondary);
        
        // Get current language setting
        isHindiMode = AppSettings.getInstance(context).useHindiInterface();
        
        // Parse attributes if available
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BilingualTextView);
            
            englishText = a.getString(R.styleable.BilingualTextView_englishText);
            hindiText = a.getString(R.styleable.BilingualTextView_hindiText);
            
            primaryTextSize = a.getDimension(R.styleable.BilingualTextView_primaryTextSize, primaryTextSize);
            secondaryTextSize = a.getDimension(R.styleable.BilingualTextView_secondaryTextSize, secondaryTextSize);
            
            a.recycle();
        }
        
        updateTextDisplay();
    }
    
    /**
     * Update the display based on current language settings
     */
    private void updateTextDisplay() {
        if (isHindiMode) {
            primaryTextView.setText(hindiText);
            secondaryTextView.setText(englishText);
            
            // Adjust text size and line spacing for Hindi
            primaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, primaryTextSize);
            primaryTextView.setLineSpacing(0, 1.4f);
            
            secondaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondaryTextSize);
            secondaryTextView.setLineSpacing(0, 1.2f);
        } else {
            primaryTextView.setText(englishText);
            secondaryTextView.setText(hindiText);
            
            // Adjust text size and line spacing for English
            primaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, primaryTextSize);
            primaryTextView.setLineSpacing(0, 1.2f);
            
            secondaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondaryTextSize);
            secondaryTextView.setLineSpacing(0, 1.4f);
        }
    }
    
    /**
     * Set the English text for this view
     */
    public void setEnglishText(String text) {
        this.englishText = text;
        updateTextDisplay();
    }
    
    /**
     * Set the Hindi text for this view
     */
    public void setHindiText(String text) {
        this.hindiText = text;
        updateTextDisplay();
    }
    
    /**
     * Set both English and Hindi text
     */
    public void setBilingualText(String englishText, String hindiText) {
        this.englishText = englishText;
        this.hindiText = hindiText;
        updateTextDisplay();
    }
    
    /**
     * Toggle which language has emphasis
     */
    public void setLanguageEmphasis(boolean emphasizeHindi) {
        if (this.isHindiMode != emphasizeHindi) {
            this.isHindiMode = emphasizeHindi;
            updateTextDisplay();
        }
    }
    
    /**
     * Refresh the view when language settings change
     */
    public void refreshLanguageSettings() {
        isHindiMode = AppSettings.getInstance(getContext()).useHindiInterface();
        updateTextDisplay();
    }
}