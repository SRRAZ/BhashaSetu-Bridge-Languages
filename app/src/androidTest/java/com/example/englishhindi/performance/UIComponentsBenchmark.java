package com.bhashasetu.app.performance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bhashasetu.app.ui.LoadingButton;
import com.bhashasetu.app.ui.LoadingStateView;
import com.bhashasetu.app.ui.ProgressAnimationView;
import com.bhashasetu.app.util.HapticFeedbackManager;
import com.bhashasetu.app.util.ViewEffectUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Performance benchmark tests for UI components.
 */
@RunWith(AndroidJUnit4.class)
public class UIComponentsBenchmark {

    @Rule
    public final BenchmarkRule benchmarkRule = new BenchmarkRule();

    @Test
    public void benchmark_loadingStateViewStateChanges() {
        Context context = ApplicationProvider.getApplicationContext();
        LoadingStateView loadingStateView = new LoadingStateView(context);
        
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark state transitions
            loadingStateView.setState(LoadingStateView.State.LOADING);
            loadingStateView.setState(LoadingStateView.State.ERROR);
            loadingStateView.setState(LoadingStateView.State.EMPTY);
            loadingStateView.setState(LoadingStateView.State.CONTENT);
        }
    }

    @Test
    public void benchmark_loadingButtonStateChanges() {
        Context context = ApplicationProvider.getApplicationContext();
        LoadingButton loadingButton = new LoadingButton(context);
        
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark loading state transitions
            loadingButton.setLoading(true);
            loadingButton.setLoading(false);
        }
    }

    @Test
    public void benchmark_progressAnimationViewSetProgress() {
        Context context = ApplicationProvider.getApplicationContext();
        ProgressAnimationView progressView = new ProgressAnimationView(context);
        
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark immediate progress updates
            progressView.setProgressImmediate(0.0f);
            progressView.setProgressImmediate(0.25f);
            progressView.setProgressImmediate(0.5f);
            progressView.setProgressImmediate(0.75f);
            progressView.setProgressImmediate(1.0f);
        }
    }

    @Test
    public void benchmark_addContentToLoadingStateView() {
        Context context = ApplicationProvider.getApplicationContext();
        LoadingStateView loadingStateView = new LoadingStateView(context);
        
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark adding content views
            TextView textView = new TextView(context);
            textView.setText("Benchmark content");
            loadingStateView.addContentView(textView);
            
            FrameLayout frameLayout = new FrameLayout(context);
            loadingStateView.addContentView(frameLayout);
        }
    }

    @Test
    public void benchmark_hapticFeedback() {
        Context context = ApplicationProvider.getApplicationContext();
        HapticFeedbackManager hapticManager = HapticFeedbackManager.getInstance(context);
        
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark haptic feedback methods (vibration will be mocked in test environment)
            hapticManager.performLightClick();
            hapticManager.performMediumClick();
            hapticManager.performHeavyClick();
            hapticManager.performSuccess();
            hapticManager.performError();
        }
    }

    @Test
    public void benchmark_viewEffects() {
        Context context = ApplicationProvider.getApplicationContext();
        View testView = new View(context);
        
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark view effect utility methods
            ViewEffectUtil.addFadeIn(testView);
            ViewEffectUtil.addFadeOut(testView);
            ViewEffectUtil.addBounceAnimation(testView);
            ViewEffectUtil.addPulseAnimation(testView);
        }
    }

    @Test
    public void benchmark_progressAnimationViewIndeterminateMode() {
        Context context = ApplicationProvider.getApplicationContext();
        ProgressAnimationView progressView = new ProgressAnimationView(context);
        
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark toggling indeterminate mode
            progressView.setIndeterminate(true);
            progressView.setIndeterminate(false);
        }
    }
}