package com.example.englishhindi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.englishhindi.R;

/**
 * A custom TextView that applies Hindi text styling and proper handling of Devanagari script.
 * This can be used throughout the app for consistent Hindi text display.
 */
public class HindiTextView extends AppCompatTextView {

    public HindiTextView(Context context) {
        super(context);
        init(context, null);
    }

    public HindiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HindiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Apply the default Hindi text appearance
        setTextAppearance(R.style.HindiTextAppearance);
        
        // Set text direction to match locale (important for RTL/LTR mixing)
        setTextDirection(TEXT_DIRECTION_LOCALE);
        
        // Set text alignment to start for proper Hindi text alignment
        setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
        
        // Add some line spacing for better readability of Hindi text
        setLineSpacing(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, 
                getResources().getDisplayMetrics()), 1.0f);
        
        // Handle custom attributes if specified
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HindiTextView);
            
            int sizeStyle = a.getInt(R.styleable.HindiTextView_hindiTextSize, 1);
            switch (sizeStyle) {
                case 0: // Small
                    setTextAppearance(R.style.HindiTextAppearance_Small);
                    break;
                case 1: // Medium (default)
                    setTextAppearance(R.style.HindiTextAppearance_Medium);
                    break;
                case 2: // Large
                    setTextAppearance(R.style.HindiTextAppearance_Large);
                    break;
                case 3: // Header
                    setTextAppearance(R.style.HindiTextAppearance_Header);
                    break;
                case 4: // Title
                    setTextAppearance(R.style.HindiTextAppearance_Title);
                    break;
            }
            
            a.recycle();
        }
    }
}