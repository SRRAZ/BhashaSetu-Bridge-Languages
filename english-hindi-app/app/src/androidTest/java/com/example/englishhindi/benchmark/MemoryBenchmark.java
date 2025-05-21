package com.example.englishhindi.benchmark;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Debug;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Benchmark to measure memory usage in different scenarios
 */
@RunWith(AndroidJUnit4.class)
public class MemoryBenchmark {

    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();

    private UiDevice device;
    private static final String PACKAGE_NAME = "com.example.englishhindi";
    private static final String MAIN_ACTIVITY = ".ui.MainActivity";

    @Before
    public void setUp() throws IOException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        
        // Start app and wait for it to be idle
        device.executeShellCommand("am force-stop " + PACKAGE_NAME);
        
        Intent intent = new Intent();
        intent.setClassName(PACKAGE_NAME, PACKAGE_NAME + MAIN_ACTIVITY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.startActivitySync(intent);
        
        device.waitForIdle();
    }

    @Test
    public void baseMemoryUsage() {
        BenchmarkState state = benchmarkRule.getState();
        
        // Measure memory usage on the home screen
        while (state.keepRunning()) {
            // Force GC before measurement to get stable results
            Runtime.getRuntime().gc();
            System.runFinalization();
            Runtime.getRuntime().gc();
            
            // Take memory snapshot
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            
            // Report key memory metrics
            state.addBenchmarkMetric("total_pss", memoryInfo.getTotalPss());
            state.addBenchmarkMetric("java_heap", memoryInfo.getMemoryStat("summary.java-heap"));
            state.addBenchmarkMetric("native_heap", memoryInfo.getMemoryStat("summary.native-heap"));
            state.addBenchmarkMetric("code", memoryInfo.getMemoryStat("summary.code"));
            state.addBenchmarkMetric("graphics", memoryInfo.getMemoryStat("summary.graphics"));
        }
    }

    @Test
    public void wordListMemory() throws IOException {
        // Navigate to word list screen
        device.executeShellCommand("am start -n " + PACKAGE_NAME + "/" + PACKAGE_NAME + ".ui.WordListActivity");
        device.waitForIdle();
        
        BenchmarkState state = benchmarkRule.getState();
        
        UiObject2 recyclerView = device.findObject(By.res(PACKAGE_NAME + ":id/word_list_recycler_view"));
        
        if (recyclerView != null) {
            // Scroll to load more content
            recyclerView.scroll(Direction.DOWN, 0.8f);
            device.waitForIdle();
        }
        
        while (state.keepRunning()) {
            // Force GC before measurement
            Runtime.getRuntime().gc();
            System.runFinalization();
            Runtime.getRuntime().gc();
            
            // Take memory snapshot
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            
            // Report memory metrics
            state.addBenchmarkMetric("total_pss", memoryInfo.getTotalPss());
            state.addBenchmarkMetric("java_heap", memoryInfo.getMemoryStat("summary.java-heap"));
            state.addBenchmarkMetric("native_heap", memoryInfo.getMemoryStat("summary.native-heap"));
            
            // Add word list specific metrics
            state.addBenchmarkMetric("views", getViewCount());
        }
    }

    @Test
    public void imageMemory() throws IOException {
        // Navigate to image-heavy screen (illustrated vocabulary)
        device.executeShellCommand("am start -n " + PACKAGE_NAME + "/" + PACKAGE_NAME + ".ui.IllustratedVocabularyActivity");
        device.waitForIdle();
        
        BenchmarkState state = benchmarkRule.getState();
        
        // Load images by scrolling
        UiObject2 imageGrid = device.findObject(By.res(PACKAGE_NAME + ":id/image_grid"));
        if (imageGrid != null) {
            imageGrid.scroll(Direction.DOWN, 0.9f);
            device.waitForIdle();
        }
        
        while (state.keepRunning()) {
            // Force GC before measurement
            Runtime.getRuntime().gc();
            System.runFinalization();
            Runtime.getRuntime().gc();
            
            // Take memory snapshot
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            
            // Report memory metrics with focus on graphics
            state.addBenchmarkMetric("total_pss", memoryInfo.getTotalPss());
            state.addBenchmarkMetric("java_heap", memoryInfo.getMemoryStat("summary.java-heap"));
            state.addBenchmarkMetric("graphics", memoryInfo.getMemoryStat("summary.graphics"));
            state.addBenchmarkMetric("bitmap", getBitmapMemory());
        }
    }

    @Test
    public void memoryLeakCheck() throws IOException, InterruptedException {
        BenchmarkState state = benchmarkRule.getState();
        
        while (state.keepRunning()) {
            // Starting memory
            Debug.MemoryInfo startMemInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(startMemInfo);
            long startPss = startMemInfo.getTotalPss();
            
            // Perform operations that might leak memory
            for (int i = 0; i < 10; i++) {
                // Navigate between activities multiple times
                device.executeShellCommand("am start -n " + PACKAGE_NAME + "/" + PACKAGE_NAME + ".ui.WordListActivity");
                device.waitForIdle();
                
                device.executeShellCommand("am start -n " + PACKAGE_NAME + "/" + PACKAGE_NAME + ".ui.QuizActivity");
                device.waitForIdle();
                
                device.executeShellCommand("am start -n " + PACKAGE_NAME + "/" + PACKAGE_NAME + MAIN_ACTIVITY);
                device.waitForIdle();
            }
            
            // Force GC
            Runtime.getRuntime().gc();
            System.runFinalization();
            Runtime.getRuntime().gc();
            
            // Wait for memory to stabilize
            Thread.sleep(1000);
            
            // Ending memory
            Debug.MemoryInfo endMemInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(endMemInfo);
            long endPss = endMemInfo.getTotalPss();
            
            // Report memory growth as potential leak indicator
            state.addBenchmarkMetric("memory_growth", endPss - startPss);
        }
    }
    
    // Helper method to estimate number of views in the hierarchy
    private long getViewCount() throws IOException {
        String output = device.executeShellCommand("dumpsys activity " + PACKAGE_NAME + " | grep -c 'View:'");
        try {
            return Long.parseLong(output.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // Helper method to estimate bitmap memory usage
    private long getBitmapMemory() throws IOException {
        String output = device.executeShellCommand("dumpsys meminfo " + PACKAGE_NAME + " | grep -A 1 'TOTAL' | grep -o '[0-9]\\+'");
        String[] lines = output.split("\\n");
        if (lines.length > 0) {
            try {
                return Long.parseLong(lines[0].trim());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }
}