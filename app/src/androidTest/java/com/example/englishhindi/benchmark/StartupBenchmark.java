package com.bhashasetu.app.benchmark;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Benchmark to measure app startup time
 */
@RunWith(AndroidJUnit4.class)
public class StartupBenchmark {

    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();

    @Test
    public void coldStartup() throws IOException {
        BenchmarkState state = benchmarkRule.getState();
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        while (state.keepRunning()) {
            // Force stop the app before each run
            device.executeShellCommand("am force-stop com.bhashasetu.app");
            
            // Clear app data to ensure true cold start
            device.executeShellCommand("pm clear com.bhashasetu.app");
            
            // Start timing
            state.pauseTiming();
            
            // Launch the app
            device.executeShellCommand("am start -n com.bhashasetu.app/com.bhashasetu.app.ui.MainActivity");
            
            // Resume timing - benchmark will automatically time until the app is fully loaded
            state.resumeTiming();
            
            // Wait for the app to be fully loaded (this is timed)
            device.waitForIdle();
        }
    }

    @Test
    public void warmStartup() throws IOException {
        BenchmarkState state = benchmarkRule.getState();
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // First, launch the app to load it into memory
        device.executeShellCommand("am start -n com.bhashasetu.app/com.bhashasetu.app.ui.MainActivity");
        device.waitForIdle();

        while (state.keepRunning()) {
            // Send app to background
            device.executeShellCommand("input keyevent KEYCODE_HOME");
            device.waitForIdle();
            
            // Start timing
            state.pauseTiming();
            
            // Launch the app again
            device.executeShellCommand("am start -n com.bhashasetu.app/com.bhashasetu.app.ui.MainActivity");
            
            // Resume timing
            state.resumeTiming();
            
            // Wait for the app to be fully loaded (this is timed)
            device.waitForIdle();
        }
    }

    @Test
    public void hotStartup() throws IOException {
        BenchmarkState state = benchmarkRule.getState();
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // First, launch the app to load it into memory
        device.executeShellCommand("am start -n com.bhashasetu.app/com.bhashasetu.app.ui.MainActivity");
        device.waitForIdle();

        while (state.keepRunning()) {
            // Send app to background but don't kill it
            device.executeShellCommand("input keyevent KEYCODE_HOME");
            device.waitForIdle();
            
            // Wait a brief moment 
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Start timing
            state.pauseTiming();
            
            // Launch the app again immediately
            device.executeShellCommand("am start -n com.bhashasetu.app/com.bhashasetu.app.ui.MainActivity");
            
            // Resume timing
            state.resumeTiming();
            
            // Wait for the app to be fully loaded (this is timed)
            device.waitForIdle();
        }
    }
}