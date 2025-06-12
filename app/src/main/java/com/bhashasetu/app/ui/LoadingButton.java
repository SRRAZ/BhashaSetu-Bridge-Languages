package com.bhashasetu.app.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bhashasetu.app.R;

/**
 * Custom button component that shows loading state and handles disabling during operations.
 */
public class LoadingButton extends FrameLayout {

    private TextView textView;
    private ProgressBar progressBar;
    private boolean isLoading = false;
    private String originalText;
    private String loadingText;
    private int originalTextColor;
    private int loadingTextColor;
    private OnClickListener originalClickListener;
    
    // Debounce mechanism
    private static final long DEBOUNCE_INTERVAL_MS = 300;
    private long lastClickTime = 0;
    
    // Auto reset
    private Handler handler;
    private Runnable resetRunnable;
    private long autoResetDelayMs = 0;

    public LoadingButton(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.view_loading_button, this, true);
        
        // Find views
        textView = findViewById(R.id.button_text);
        progressBar = findViewById(R.id.button_progress);
        
        // Set up handler for auto reset
        handler = new Handler(Looper.getMainLooper());
        
        // Default values
        originalText = "";
        loadingText = context.getString(R.string.loading);
        originalTextColor = ContextCompat.getColor(context, android.R.color.white);
        loadingTextColor = ContextCompat.getColor(context, android.R.color.white);
        
        // Get attributes
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton);
            
            originalText = a.getString(R.styleable.LoadingButton_text);
            loadingText = a.getString(R.styleable.LoadingButton_loadingText) != null ?
                         a.getString(R.styleable.LoadingButton_loadingText) : loadingText;
                         
            originalTextColor = a.getColor(R.styleable.LoadingButton_textColor, originalTextColor);
            loadingTextColor = a.getColor(R.styleable.LoadingButton_loadingTextColor, loadingTextColor);
            
            // Auto reset delay
            autoResetDelayMs = a.getInt(R.styleable.LoadingButton_autoResetDelayMs, 0);
            
            // Progress bar color
            int progressColor = a.getColor(R.styleable.LoadingButton_progressColor, 
                                         ContextCompat.getColor(context, android.R.color.white));
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(progressColor));
            
            a.recycle();
        }
        
        // Set text
        if (originalText != null) {
            textView.setText(originalText);
        }
        
        // Set text color
        textView.setTextColor(originalTextColor);
        
        // Set initial state
        progressBar.setVisibility(View.GONE);
        
        // Setup click listener with debounce protection
        super.setOnClickListener(v -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > DEBOUNCE_INTERVAL_MS) {
                lastClickTime = currentTime;
                if (!isLoading && originalClickListener != null) {
                    originalClickListener.onClick(v);
                }
            }
        });
    }

    /**
     * Set the button text.
     * @param text The button text
     */
    public void setText(String text) {
        originalText = text;
        if (!isLoading) {
            textView.setText(text);
        }
    }

    /**
     * Set the loading text.
     * @param text The loading text
     */
    public void setLoadingText(String text) {
        loadingText = text;
        if (isLoading) {
            textView.setText(text);
        }
    }

    /**
     * Set the button text color.
     * @param color The text color
     */
    public void setTextColor(int color) {
        originalTextColor = color;
        if (!isLoading) {
            textView.setTextColor(color);
        }
    }

    /**
     * Set the loading text color.
     * @param color The loading text color
     */
    public void setLoadingTextColor(int color) {
        loadingTextColor = color;
        if (isLoading) {
            textView.setTextColor(color);
        }
    }

    /**
     * Set the progress bar color.
     * @param color The progress bar color
     */
    public void setProgressColor(int color) {
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(color));
    }

    /**
     * Set the auto reset delay.
     * @param delayMs The delay in milliseconds (0 to disable)
     */
    public void setAutoResetDelayMs(long delayMs) {
        this.autoResetDelayMs = delayMs;
    }

    /**
     * Set the loading state of the button.
     * @param loading True to show loading, false to show normal
     */
    public void setLoading(boolean loading) {
        if (isLoading == loading) {
            return;
        }
        
        isLoading = loading;
        
        if (loading) {
            // Show loading state
            textView.setText(loadingText);
            textView.setTextColor(loadingTextColor);
            progressBar.setVisibility(View.VISIBLE);
            setEnabled(false);
            
            // Schedule auto reset if needed
            if (autoResetDelayMs > 0) {
                cancelAutoReset();
                resetRunnable = () -> setLoading(false);
                handler.postDelayed(resetRunnable, autoResetDelayMs);
            }
        } else {
            // Show normal state
            textView.setText(originalText);
            textView.setTextColor(originalTextColor);
            progressBar.setVisibility(View.GONE);
            setEnabled(true);
            
            // Cancel any pending auto reset
            cancelAutoReset();
        }
    }

    /**
     * Check if the button is in loading state.
     * @return True if loading, false otherwise
     */
    public boolean isLoading() {
        return isLoading;
    }

    /**
     * Cancel any pending auto reset.
     */
    private void cancelAutoReset() {
        if (resetRunnable != null) {
            handler.removeCallbacks(resetRunnable);
            resetRunnable = null;
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener listener) {
        originalClickListener = listener;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        // Make sure the button is disabled during loading
        if (isLoading) {
            super.setEnabled(false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAutoReset();
    }
}