package com.example.englishhindi.ui;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.englishhindi.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented tests for the LoadingButton component.
 */
@RunWith(AndroidJUnit4.class)
public class LoadingButtonTest {

    private Context context;
    private LoadingButton loadingButton;

    // Test constants
    private static final String BUTTON_TEXT = "Test Button";
    private static final String LOADING_TEXT = "Loading...";

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        loadingButton = new LoadingButton(context);
        
        // Set initial text
        loadingButton.setText(BUTTON_TEXT);
        loadingButton.setLoadingText(LOADING_TEXT);
    }

    @Test
    public void initialization_shouldSetupCorrectly() {
        // Verify initial state is not loading
        assertFalse(loadingButton.isLoading());
        
        // Get text view and progress bar
        TextView textView = loadingButton.findViewById(R.id.button_text);
        ProgressBar progressBar = loadingButton.findViewById(R.id.button_progress);
        
        // Verify text is set correctly
        assertNotNull(textView);
        assertEquals(BUTTON_TEXT, textView.getText().toString());
        
        // Verify progress bar is hidden
        assertNotNull(progressBar);
        assertEquals(View.GONE, progressBar.getVisibility());
    }

    @Test
    public void setLoading_true_shouldShowLoadingState() {
        // Set loading state
        loadingButton.setLoading(true);
        
        // Get text view and progress bar
        TextView textView = loadingButton.findViewById(R.id.button_text);
        ProgressBar progressBar = loadingButton.findViewById(R.id.button_progress);
        
        // Verify loading state
        assertTrue(loadingButton.isLoading());
        assertEquals(LOADING_TEXT, textView.getText().toString());
        assertEquals(View.VISIBLE, progressBar.getVisibility());
        assertFalse(loadingButton.isEnabled());
    }

    @Test
    public void setLoading_false_shouldShowNormalState() {
        // First set to loading
        loadingButton.setLoading(true);
        
        // Then set back to normal
        loadingButton.setLoading(false);
        
        // Get text view and progress bar
        TextView textView = loadingButton.findViewById(R.id.button_text);
        ProgressBar progressBar = loadingButton.findViewById(R.id.button_progress);
        
        // Verify normal state
        assertFalse(loadingButton.isLoading());
        assertEquals(BUTTON_TEXT, textView.getText().toString());
        assertEquals(View.GONE, progressBar.getVisibility());
        assertTrue(loadingButton.isEnabled());
    }

    @Test
    public void setText_shouldUpdateText() {
        // New text
        String newText = "New Button Text";
        
        // Set text
        loadingButton.setText(newText);
        
        // Get text view
        TextView textView = loadingButton.findViewById(R.id.button_text);
        
        // Verify text is updated
        assertEquals(newText, textView.getText().toString());
    }

    @Test
    public void setLoadingText_shouldUpdateLoadingText() {
        // New loading text
        String newLoadingText = "New Loading Text...";
        
        // Set loading text
        loadingButton.setLoadingText(newLoadingText);
        
        // Set to loading state
        loadingButton.setLoading(true);
        
        // Get text view
        TextView textView = loadingButton.findViewById(R.id.button_text);
        
        // Verify loading text is updated
        assertEquals(newLoadingText, textView.getText().toString());
    }

    @Test
    public void clickListener_shouldBeCalledWhenClicked() {
        // Flag to verify listener called
        final boolean[] listenerCalled = {false};
        
        // Set click listener
        loadingButton.setOnClickListener(v -> listenerCalled[0] = true);
        
        // Click the button
        loadingButton.performClick();
        
        // Verify listener was called
        assertTrue(listenerCalled[0]);
    }

    @Test
    public void clickListener_whenLoading_shouldNotBeCalled() {
        // Flag to verify listener called
        final boolean[] listenerCalled = {false};
        
        // Set click listener
        loadingButton.setOnClickListener(v -> listenerCalled[0] = true);
        
        // Set to loading state
        loadingButton.setLoading(true);
        
        // Click the button
        loadingButton.performClick();
        
        // Verify listener was not called (button disabled)
        assertFalse(listenerCalled[0]);
    }

    @Test
    public void setEnabled_false_shouldDisableButton() {
        // Disable the button
        loadingButton.setEnabled(false);
        
        // Verify button is disabled
        assertFalse(loadingButton.isEnabled());
    }

    @Test
    public void setEnabled_true_whenLoading_shouldStillBeDisabled() {
        // Set to loading state
        loadingButton.setLoading(true);
        
        // Try to enable the button
        loadingButton.setEnabled(true);
        
        // Verify button is still disabled due to loading state
        assertFalse(loadingButton.isEnabled());
    }

    @Test
    public void setEnabled_true_afterLoadingFinished_shouldEnableButton() {
        // Set to loading state
        loadingButton.setLoading(true);
        
        // Finish loading
        loadingButton.setLoading(false);
        
        // Enable the button
        loadingButton.setEnabled(true);
        
        // Verify button is enabled
        assertTrue(loadingButton.isEnabled());
    }
}