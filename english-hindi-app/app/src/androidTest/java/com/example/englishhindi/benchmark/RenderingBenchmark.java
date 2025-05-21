package com.example.englishhindi.benchmark;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Benchmark to measure UI rendering performance
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RenderingBenchmark {

    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();

    private UiDevice device;

    @Before
    public void setUp() throws IOException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        
        // Start app and navigate to home screen
        device.executeShellCommand("am force-stop com.example.englishhindi");
        device.executeShellCommand("am start -n com.example.englishhindi/com.example.englishhindi.ui.MainActivity");
        device.waitForIdle();
    }

    @Test
    public void scrollWordList() {
        // Navigate to word list screen
        device.findObject(By.text("Word List")).click();
        device.waitForIdle();

        BenchmarkState state = benchmarkRule.getState();
        
        UiObject2 recyclerView = device.findObject(By.res("com.example.englishhindi:id/word_list_recycler_view"));
        
        while (state.keepRunning()) {
            // Start timing scroll performance
            recyclerView.scroll(Direction.DOWN, 0.8f);
            
            // Wait for scroll to complete
            device.waitForIdle();
            
            // Scroll back up to repeat
            recyclerView.scroll(Direction.UP, 0.8f);
            device.waitForIdle();
        }
    }

    @Test
    public void animationTransition() throws IOException {
        BenchmarkState state = benchmarkRule.getState();
        
        while (state.keepRunning()) {
            // Start timing screen transition animations
            
            // Navigate to Quiz screen
            device.findObject(By.text("Quiz")).click();
            device.waitForIdle();
            
            // Navigate to Dictionary screen
            device.findObject(By.text("Dictionary")).click();
            device.waitForIdle();
            
            // Navigate to Learning screen
            device.findObject(By.text("Learning")).click();
            device.waitForIdle();
            
            // Go back to home
            device.executeShellCommand("input keyevent KEYCODE_BACK");
            device.waitForIdle();
        }
    }

    @Test
    public void complexUIRendering() {
        BenchmarkState state = benchmarkRule.getState();
        
        // Navigate to a screen with complex UI (e.g., learning module with images)
        device.findObject(By.text("Learning")).click();
        device.waitForIdle();
        
        while (state.keepRunning()) {
            // Trigger complex UI rendering
            
            // Open a learning module that has rich content
            device.findObject(By.res("com.example.englishhindi:id/module_item")).click();
            device.waitForIdle();
            
            // Interact with complex content
            UiObject2 contentView = device.findObject(By.res("com.example.englishhindi:id/module_content"));
            if (contentView != null) {
                contentView.scroll(Direction.DOWN, 0.5f);
                device.waitForIdle();
                
                contentView.scroll(Direction.UP, 0.5f);
                device.waitForIdle();
            }
            
            // Go back
            device.pressBack();
            device.waitForIdle();
        }
    }

    @Test
    public void imageLoadingPerformance() {
        BenchmarkState state = benchmarkRule.getState();
        
        // Navigate to a screen with images (e.g., illustrated vocabulary)
        device.findObject(By.text("Vocabulary")).click();
        device.waitForIdle();
        
        device.findObject(By.text("Illustrated")).click();
        device.waitForIdle();
        
        while (state.keepRunning()) {
            // Test image loading performance
            
            // Scroll through images
            UiObject2 imageGrid = device.findObject(By.res("com.example.englishhindi:id/image_grid"));
            imageGrid.scroll(Direction.DOWN, 0.7f);
            
            // Wait for images to load (this measures image loading performance)
            device.wait(Until.findObject(By.res("com.example.englishhindi:id/image_view")), 2000);
            device.waitForIdle();
            
            // Scroll back up to reset
            imageGrid.scroll(Direction.UP, 0.7f);
            device.waitForIdle();
        }
    }
}