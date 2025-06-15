package com.bhashasetu.app.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bhashasetu.app.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented tests for the LoadingStateView component.
 */
@RunWith(AndroidJUnit4.class)
public class LoadingStateViewTest {

    private Context context;
    private LoadingStateView loadingStateView;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        loadingStateView = new LoadingStateView(context);
    }

    @Test
    public void initialization_shouldSetupCorrectly() {
        // Verify initial state is LOADING
        assertEquals(LoadingStateView.State.LOADING, loadingStateView.getState());
    }

    @Test
    public void setState_toLoading_shouldShowLoadingView() {
        // Set state to LOADING
        loadingStateView.setState(LoadingStateView.State.LOADING);

        // Get loading view
        View loadingView = loadingStateView.findViewById(R.id.loading_view);

        // Verify it's visible
        assertNotNull(loadingView);
        assertEquals(View.VISIBLE, loadingView.getVisibility());
    }

    @Test
    public void setState_toError_shouldShowErrorView() {
        // Set state to ERROR
        loadingStateView.setState(LoadingStateView.State.ERROR);

        // Get error view
        View errorView = loadingStateView.findViewById(R.id.error_view);

        // Verify it's visible
        assertNotNull(errorView);
        assertEquals(View.VISIBLE, errorView.getVisibility());
    }

    @Test
    public void setState_toEmpty_shouldShowEmptyView() {
        // Set state to EMPTY
        loadingStateView.setState(LoadingStateView.State.EMPTY);

        // Get empty view
        View emptyView = loadingStateView.findViewById(R.id.empty_view);

        // Verify it's visible
        assertNotNull(emptyView);
        assertEquals(View.VISIBLE, emptyView.getVisibility());
    }

    @Test
    public void setState_toContent_shouldShowContentContainer() {
        // Set state to CONTENT
        loadingStateView.setState(LoadingStateView.State.CONTENT);

        // Get content container
        View contentContainer = loadingStateView.findViewById(R.id.content_container);

        // Verify it's visible
        assertNotNull(contentContainer);
        assertEquals(View.VISIBLE, contentContainer.getVisibility());
    }

    @Test
    public void setErrorMessage_shouldUpdateErrorTextView() {
        // Custom error message
        String customErrorMessage = "Custom error message";

        // Set error message
        loadingStateView.setErrorMessage(customErrorMessage);

        // Get error text view
        TextView errorTextView = loadingStateView.findViewById(R.id.error_text);

        // Verify message is set
        assertNotNull(errorTextView);
        assertEquals(customErrorMessage, errorTextView.getText().toString());
    }

    @Test
    public void setEmptyMessage_shouldUpdateEmptyTextView() {
        // Custom empty message
        String customEmptyMessage = "Custom empty message";

        // Set empty message
        loadingStateView.setEmptyMessage(customEmptyMessage);

        // Get empty text view
        TextView emptyTextView = loadingStateView.findViewById(R.id.empty_text);

        // Verify message is set
        assertNotNull(emptyTextView);
        assertEquals(customEmptyMessage, emptyTextView.getText().toString());
    }

    @Test
    public void setOnRetryClickListener_shouldInvokeListenerWhenClicked() {
        // Flag to verify listener was called
        final boolean[] listenerCalled = {false};

        // Set retry click listener
        loadingStateView.setOnRetryClickListener(() -> listenerCalled[0] = true);

        // Set state to ERROR to show retry button
        loadingStateView.setState(LoadingStateView.State.ERROR);

        // Get retry button
        View retryButton = loadingStateView.findViewById(R.id.retry_button);

        // Verify button is shown
        assertNotNull(retryButton);
        assertEquals(View.VISIBLE, retryButton.getVisibility());

        // Click the button
        retryButton.performClick();

        // Verify listener was called
        assertTrue(listenerCalled[0]);
    }

    @Test
    public void addContentView_shouldAddViewToContentContainer() {
        // Create a test view to add
        TextView testView = new TextView(context);
        testView.setText("Test content");
        testView.setId(View.generateViewId());

        // Add the view to content container
        loadingStateView.addContentView(testView);

        // Get content container
        FrameLayout contentContainer = loadingStateView.findViewById(R.id.content_container);

        // Verify container has the view
        assertEquals(1, contentContainer.getChildCount());
        assertEquals(testView.getId(), contentContainer.getChildAt(0).getId());
    }

    @Test
    public void stateChanges_shouldHideOtherViews() {
        // First set to LOADING
        loadingStateView.setState(LoadingStateView.State.LOADING);

        // Get all state views
        View loadingView = loadingStateView.findViewById(R.id.loading_view);
        View errorView = loadingStateView.findViewById(R.id.error_view);
        View emptyView = loadingStateView.findViewById(R.id.empty_view);
        View contentContainer = loadingStateView.findViewById(R.id.content_container);

        // Verify only loading view is visible
        assertEquals(View.VISIBLE, loadingView.getVisibility());
        assertEquals(View.GONE, errorView.getVisibility());
        assertEquals(View.GONE, emptyView.getVisibility());
        assertEquals(View.GONE, contentContainer.getVisibility());

        // Now set to ERROR
        loadingStateView.setState(LoadingStateView.State.ERROR);

        // Verify only error view is visible
        assertEquals(View.GONE, loadingView.getVisibility());
        assertEquals(View.VISIBLE, errorView.getVisibility());
        assertEquals(View.GONE, emptyView.getVisibility());
        assertEquals(View.GONE, contentContainer.getVisibility());

        // Now set to EMPTY
        loadingStateView.setState(LoadingStateView.State.EMPTY);

        // Verify only empty view is visible
        assertEquals(View.GONE, loadingView.getVisibility());
        assertEquals(View.GONE, errorView.getVisibility());
        assertEquals(View.VISIBLE, emptyView.getVisibility());
        assertEquals(View.GONE, contentContainer.getVisibility());

        // Now set to CONTENT
        loadingStateView.setState(LoadingStateView.State.CONTENT);

        // Verify only content container is visible
        assertEquals(View.GONE, loadingView.getVisibility());
        assertEquals(View.GONE, errorView.getVisibility());
        assertEquals(View.GONE, emptyView.getVisibility());
        assertEquals(View.VISIBLE, contentContainer.getVisibility());
    }
}