package com.example.englishhindi.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.englishhindi.R;

/**
 * Custom view that handles different loading states with smooth transitions.
 * Supports loading, error, empty, and content states.
 */
public class LoadingStateView extends FrameLayout {

    public enum State {
        LOADING,    // Show loading indicator
        ERROR,      // Show error message with retry option
        EMPTY,      // Show empty state message
        CONTENT     // Show actual content
    }

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View contentContainer;
    
    private ProgressBar progressBar;
    private TextView errorTextView;
    private TextView emptyTextView;
    private TextView retryButton;
    private ImageView errorImageView;
    private ImageView emptyImageView;
    
    private State currentState = State.LOADING;
    private OnRetryClickListener retryClickListener;
    
    // Default messages
    private String loadingMessage;
    private String errorMessage;
    private String emptyMessage;

    public LoadingStateView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public LoadingStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadingStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Inflate the main layout
        LayoutInflater.from(context).inflate(R.layout.view_loading_state, this, true);
        
        // Get default messages from resources
        loadingMessage = context.getString(R.string.loading_message);
        errorMessage = context.getString(R.string.error_message);
        emptyMessage = context.getString(R.string.empty_message);
        
        // Parse custom attributes if available
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingStateView);
            
            loadingMessage = a.getString(R.styleable.LoadingStateView_loadingMessage) != null ?
                            a.getString(R.styleable.LoadingStateView_loadingMessage) : loadingMessage;
                            
            errorMessage = a.getString(R.styleable.LoadingStateView_errorMessage) != null ?
                          a.getString(R.styleable.LoadingStateView_errorMessage) : errorMessage;
                          
            emptyMessage = a.getString(R.styleable.LoadingStateView_emptyMessage) != null ?
                          a.getString(R.styleable.LoadingStateView_emptyMessage) : emptyMessage;
            
            a.recycle();
        }
        
        // Find views
        loadingView = findViewById(R.id.loading_view);
        errorView = findViewById(R.id.error_view);
        emptyView = findViewById(R.id.empty_view);
        contentContainer = findViewById(R.id.content_container);
        
        progressBar = findViewById(R.id.progress_bar);
        errorTextView = findViewById(R.id.error_text);
        emptyTextView = findViewById(R.id.empty_text);
        retryButton = findViewById(R.id.retry_button);
        errorImageView = findViewById(R.id.error_image);
        emptyImageView = findViewById(R.id.empty_image);
        
        // Set default messages
        errorTextView.setText(errorMessage);
        emptyTextView.setText(emptyMessage);
        
        // Set up retry button
        retryButton.setOnClickListener(v -> {
            if (retryClickListener != null) {
                setState(State.LOADING);
                retryClickListener.onRetryClick();
            }
        });
        
        // Set initial state
        setState(State.LOADING);
    }

    /**
     * Set the current state and update the UI accordingly.
     * @param state The new state
     */
    public void setState(State state) {
        if (state == currentState) {
            return;
        }
        
        currentState = state;
        
        // Hide all views first
        loadingView.setVisibility(GONE);
        errorView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        contentContainer.setVisibility(GONE);
        
        // Show the appropriate view
        switch (state) {
            case LOADING:
                loadingView.setVisibility(VISIBLE);
                startLoadingAnimation();
                break;
            case ERROR:
                errorView.setVisibility(VISIBLE);
                startErrorAnimation();
                break;
            case EMPTY:
                emptyView.setVisibility(VISIBLE);
                startEmptyAnimation();
                break;
            case CONTENT:
                contentContainer.setVisibility(VISIBLE);
                startContentAnimation();
                break;
        }
    }
    
    /**
     * Get the current state.
     * @return The current state
     */
    public State getState() {
        return currentState;
    }
    
    /**
     * Set a custom message for the loading state.
     * @param message The loading message
     */
    public void setLoadingMessage(String message) {
        loadingMessage = message;
        // Update the UI if needed
    }
    
    /**
     * Set a custom message for the error state.
     * @param message The error message
     */
    public void setErrorMessage(String message) {
        errorMessage = message;
        errorTextView.setText(message);
    }
    
    /**
     * Set a custom message for the empty state.
     * @param message The empty message
     */
    public void setEmptyMessage(String message) {
        emptyMessage = message;
        emptyTextView.setText(message);
    }
    
    /**
     * Set a listener for retry button clicks.
     * @param listener The retry click listener
     */
    public void setOnRetryClickListener(OnRetryClickListener listener) {
        retryClickListener = listener;
    }
    
    /**
     * Add a view to the content container.
     * @param view The content view
     */
    public void addContentView(View view) {
        contentContainer.removeAllViews();
        contentContainer.addView(view);
    }
    
    /**
     * Start the loading animation.
     */
    private void startLoadingAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        loadingView.startAnimation(animation);
    }
    
    /**
     * Start the error animation.
     */
    private void startErrorAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        errorView.startAnimation(animation);
    }
    
    /**
     * Start the empty animation.
     */
    private void startEmptyAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        emptyView.startAnimation(animation);
    }
    
    /**
     * Start the content animation.
     */
    private void startContentAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        contentContainer.startAnimation(animation);
    }
    
    /**
     * Interface for retry button click events.
     */
    public interface OnRetryClickListener {
        void onRetryClick();
    }
}