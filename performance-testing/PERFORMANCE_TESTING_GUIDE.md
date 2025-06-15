# Performance Testing Guide for English-Hindi Learning App

This guide outlines the performance testing approach, tools, and procedures for thoroughly evaluating the English-Hindi Learning App performance.

## Key Performance Metrics

Performance testing will focus on these key metrics as defined in `PERFORMANCE_METRICS.md`:

1. **App Startup Time** (cold, warm, and hot starts)
2. **Memory Usage** (base memory, peak memory, memory leaks)
3. **Rendering Performance** (frame rate, jank score, UI thread utilization)
4. **Network Efficiency** (if applicable)
5. **Battery Consumption** (background and active usage)
6. **Storage Impact** (app size, database growth, cache size)

## Testing Environment Setup

### Required Tools

1. **Android Device/Emulator** running a supported Android version (API 21-33)
2. **ADB (Android Debug Bridge)** for running shell commands and collecting metrics
3. **Android Studio** for running benchmark tests
4. **Performance Monitoring Tools**:
   - Android Profiler
   - Perfetto
   - Firebase Performance Monitoring (optional)
   - Android Benchmark library

### Device Configuration for Testing

- Minimum 3 test devices representing different tiers:
  - Low-end: 2GB RAM, older processor (e.g., Snapdragon 450)
  - Mid-range: 4GB RAM, mid-tier processor (e.g., Snapdragon 660)
  - High-end: 6GB+ RAM, flagship processor (e.g., Snapdragon 8+)
- All devices should be:
  - In airplane mode (unless testing network features)
  - Connected to power source
  - On stable Android versions
  - With consistent brightness settings (50%)
  - With clean state (no other apps running)

## Testing Procedure

### 1. Baseline Establishment

Before performance optimization, establish baselines for both debug and release builds:

```bash
# Run startup time baseline tests
./scripts/startup_time_test.sh

# Run memory profile baseline
./scripts/memory_profile.sh

# Run frame statistics baseline
./scripts/frame_stats.sh

# Run battery usage baseline
./scripts/battery_usage.sh

# Run storage impact baseline
./scripts/storage_impact.sh
```

Save all baseline results in the `baseline` directory.

### 2. JVM Benchmark Tests

Run the JVM-based benchmark tests for core functionality:

```bash
# From Android Studio or command line
./gradlew cAT -P android.testInstrumentationRunnerArguments.class=com.bhashasetu.app.benchmark.StartupBenchmark
./gradlew cAT -P android.testInstrumentationRunnerArguments.class=com.bhashasetu.app.benchmark.RenderingBenchmark
./gradlew cAT -P android.testInstrumentationRunnerArguments.class=com.bhashasetu.app.benchmark.MemoryBenchmark
```

### 3. Build Variant Comparison

Perform comparative testing between:
- Debug vs. Release builds
- Free vs. Premium flavors

For release builds, use the signed APKs from the release package.

```bash
# Install and test debug build
adb install -r app/build/outputs/apk/free/debug/app-free-debug.apk
./scripts/startup_time_test.sh

# Install and test release build
adb install -r release-package/dummy-release-apk.apk
./scripts/startup_time_test.sh
```

### 4. Profiling Specific Features

For each core feature, conduct focused profiling:

#### Dictionary/Vocabulary Lookup
- Test search performance with various query lengths
- Measure response times for both English and Hindi lookups

#### Audio Playback
- Measure latency between tap and audio playback
- Check memory usage during audio playback
- Test concurrent audio handling

#### Quiz Generation
- Measure time to generate and display quiz questions
- Analyze memory usage during quiz sessions
- Test with progressive difficulty levels

#### Progress Tracking
- Measure calculation time for user statistics
- Test with different amounts of user data
- Check database performance under load

## Analysis and Reporting

Generate comprehensive performance reports with:

1. Metric comparisons (debug vs. release, free vs. premium)
2. Performance improvements quantification
3. Identified bottlenecks and optimization recommendations
4. Visual charts and graphs of key metrics
5. Compliance with performance targets

Use the `reports` directory to store all analysis results.

## Continuous Performance Monitoring

Implement continuous performance monitoring:

1. **CI Integration**: Add benchmark tests to CI pipeline
2. **Regression Detection**: Compare results with previous baselines
3. **Performance Budgets**: Set thresholds for key metrics
4. **Automated Alerting**: Alert on performance regressions

## Performance Optimization Workflow

1. **Measure**: Establish current performance baseline
2. **Identify**: Locate performance bottlenecks
3. **Optimize**: Implement targeted optimizations
4. **Validate**: Confirm improvements with measurements
5. **Document**: Record optimization techniques and results

## Tool Reference

### Android Benchmark Library
```java
// Example benchmark test
@Test
public void wordListScrolling() {
    BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
        // Code to measure
    }
}
```

### ADB Commands
```bash
# Memory stats
adb shell dumpsys meminfo com.bhashasetu.app

# Battery stats
adb shell dumpsys batterystats com.bhashasetu.app

# GPU rendering stats
adb shell dumpsys gfxinfo com.bhashasetu.app
```

### Android Profiler
1. Open Android Studio
2. Run the app in profile mode
3. Use CPU, Memory, and Energy profilers
4. Export traces for further analysis