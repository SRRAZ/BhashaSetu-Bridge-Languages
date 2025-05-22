package com.example.englishhindi.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.animation.LinearInterpolator;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented tests for the ProgressAnimationView component.
 */
@RunWith(AndroidJUnit4.class)
public class ProgressAnimationViewTest {

    private Context context;
    private ProgressAnimationView progressAnimationView;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        progressAnimationView = new ProgressAnimationView(context);
    }

    @Test
    public void initialization_shouldSetupCorrectly() throws Exception {
        // Get the current progress through reflection (private field)
        Field currentProgressField = ProgressAnimationView.class.getDeclaredField("currentProgress");
        currentProgressField.setAccessible(true);
        float currentProgress = (float) currentProgressField.get(progressAnimationView);
        
        // Get indeterminate state through reflection
        Field indeterminateField = ProgressAnimationView.class.getDeclaredField("indeterminate");
        indeterminateField.setAccessible(true);
        boolean indeterminate = (boolean) indeterminateField.get(progressAnimationView);
        
        // Verify initial state
        assertEquals(0f, currentProgress, 0.001f);
        assertFalse(indeterminate);
    }

    @Test
    public void setProgress_shouldUpdateProgress() throws Exception {
        // Set progress
        float testProgress = 0.75f;
        progressAnimationView.setProgressImmediate(testProgress);
        
        // Get the current progress through reflection
        Field currentProgressField = ProgressAnimationView.class.getDeclaredField("currentProgress");
        currentProgressField.setAccessible(true);
        float currentProgress = (float) currentProgressField.get(progressAnimationView);
        
        // Verify progress is updated
        assertEquals(testProgress, currentProgress, 0.001f);
        assertEquals(testProgress, progressAnimationView.getProgress(), 0.001f);
    }

    @Test
    public void setProgress_withValueOver1_shouldClampTo1() throws Exception {
        // Set progress > 1
        progressAnimationView.setProgressImmediate(1.5f);
        
        // Verify progress is clamped to 1
        assertEquals(1.0f, progressAnimationView.getProgress(), 0.001f);
    }

    @Test
    public void setProgress_withValueUnder0_shouldClampTo0() throws Exception {
        // Set progress < 0
        progressAnimationView.setProgressImmediate(-0.5f);
        
        // Verify progress is clamped to 0
        assertEquals(0.0f, progressAnimationView.getProgress(), 0.001f);
    }

    @Test
    public void setIndeterminate_true_shouldEnableIndeterminateMode() throws Exception {
        // Enable indeterminate mode
        progressAnimationView.setIndeterminate(true);
        
        // Get indeterminate state through reflection
        Field indeterminateField = ProgressAnimationView.class.getDeclaredField("indeterminate");
        indeterminateField.setAccessible(true);
        boolean indeterminate = (boolean) indeterminateField.get(progressAnimationView);
        
        // Verify indeterminate mode is enabled
        assertTrue(indeterminate);
    }

    @Test
    public void setIndeterminate_false_shouldDisableIndeterminateMode() throws Exception {
        // First enable indeterminate mode
        progressAnimationView.setIndeterminate(true);
        
        // Then disable it
        progressAnimationView.setIndeterminate(false);
        
        // Get indeterminate state through reflection
        Field indeterminateField = ProgressAnimationView.class.getDeclaredField("indeterminate");
        indeterminateField.setAccessible(true);
        boolean indeterminate = (boolean) indeterminateField.get(progressAnimationView);
        
        // Verify indeterminate mode is disabled
        assertFalse(indeterminate);
    }

    @Test
    public void setProgressColor_shouldUpdateColor() throws Exception {
        // Set progress color
        int testColor = Color.RED;
        progressAnimationView.setProgressColor(testColor);
        
        // Get progress color through reflection
        Field progressColorField = ProgressAnimationView.class.getDeclaredField("progressColor");
        progressColorField.setAccessible(true);
        int progressColor = (int) progressColorField.get(progressAnimationView);
        
        // Verify color is updated
        assertEquals(testColor, progressColor);
    }

    @Test
    public void setBackgroundCircleColor_shouldUpdateColor() throws Exception {
        // Set background color
        int testColor = Color.BLUE;
        progressAnimationView.setBackgroundCircleColor(testColor);
        
        // Get background color through reflection
        Field backgroundColorField = ProgressAnimationView.class.getDeclaredField("backgroundColor");
        backgroundColorField.setAccessible(true);
        int backgroundColor = (int) backgroundColorField.get(progressAnimationView);
        
        // Verify color is updated
        assertEquals(testColor, backgroundColor);
    }

    @Test
    public void setStrokeWidth_shouldUpdateWidth() throws Exception {
        // Set stroke width
        float testWidth = 12f;
        progressAnimationView.setStrokeWidth(testWidth);
        
        // Get stroke width through reflection
        Field strokeWidthField = ProgressAnimationView.class.getDeclaredField("strokeWidth");
        strokeWidthField.setAccessible(true);
        float strokeWidth = (float) strokeWidthField.get(progressAnimationView);
        
        // Verify width is updated
        assertEquals(testWidth, strokeWidth, 0.001f);
    }

    @Test
    public void setAnimationDuration_shouldUpdateDuration() throws Exception {
        // Set animation duration
        long testDuration = 2000L;
        progressAnimationView.setAnimationDuration(testDuration);
        
        // Get animation duration through reflection
        Field animationDurationField = ProgressAnimationView.class.getDeclaredField("animationDurationMs");
        animationDurationField.setAccessible(true);
        long animationDuration = (long) animationDurationField.get(progressAnimationView);
        
        // Verify duration is updated
        assertEquals(testDuration, animationDuration);
    }

    @Test
    public void setInterpolator_shouldUpdateInterpolator() throws Exception {
        // Create test interpolator
        LinearInterpolator testInterpolator = new LinearInterpolator();
        
        // Set interpolator
        progressAnimationView.setInterpolator(testInterpolator);
        
        // Get interpolator through reflection
        Field interpolatorField = ProgressAnimationView.class.getDeclaredField("interpolator");
        interpolatorField.setAccessible(true);
        Object interpolator = interpolatorField.get(progressAnimationView);
        
        // Verify interpolator is updated
        assertEquals(testInterpolator, interpolator);
    }
}