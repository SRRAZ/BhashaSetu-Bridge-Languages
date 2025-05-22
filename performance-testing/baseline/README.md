# Performance Baseline Directory

This directory contains baseline performance measurements for the English-Hindi Learning App. These baseline measurements serve as a reference point for comparing performance optimizations and detecting regressions.

## Directory Structure

```
baseline/
  ├── debug/
  │   ├── free/
  │   │   ├── startup_times_20250521.csv
  │   │   ├── memory_profile_20250521.csv
  │   │   ├── frame_stats_20250521.csv
  │   │   ├── battery_usage_20250521.csv
  │   │   └── storage_impact_20250521.csv
  │   └── premium/
  │       ├── startup_times_20250521.csv
  │       ├── memory_profile_20250521.csv
  │       ├── frame_stats_20250521.csv
  │       ├── battery_usage_20250521.csv
  │       └── storage_impact_20250521.csv
  └── release/
      ├── free/
      │   ├── startup_times_20250521.csv
      │   ├── memory_profile_20250521.csv
      │   ├── frame_stats_20250521.csv
      │   ├── battery_usage_20250521.csv
      │   └── storage_impact_20250521.csv
      └── premium/
          ├── startup_times_20250521.csv
          ├── memory_profile_20250521.csv
          ├── frame_stats_20250521.csv
          ├── battery_usage_20250521.csv
          └── storage_impact_20250521.csv
```

## Baseline Collection Process

1. **Fresh Installation**: Baselines are collected after fresh installation of the app, not upgrades
2. **Controlled Environment**: All devices are in airplane mode with consistent brightness
3. **Multi-Device**: Each baseline is an average across low, mid, and high-end devices
4. **Repeatable**: Each test runs 5 times and the results are averaged

## Baseline Metrics Summary

### App Startup Times
- Cold Start: Time from app launch to first interactive frame when not in memory
- Warm Start: Time from app launch when app was recently in memory
- Hot Start: Time to restore app from background

### Memory Usage
- Base Memory: Initial memory consumption after app launch
- Peak Memory: Maximum memory observed during typical usage
- Memory Growth: Increase in memory usage over time (potential leak indicator)

### Rendering Performance
- Frame Rate: Average frames per second during scrolling and animations
- Jank Score: Percentage of frames that took longer than 16ms (60fps threshold)
- UI Thread Utilization: Time UI thread is blocked vs. free

### Battery Usage
- Active Usage: Battery consumption during active app usage
- Background Usage: Battery consumption when app is in background
- Wakelock Usage: Frequency and duration of wakelocks

### Storage Impact
- App Size: APK/App Bundle size and installed size
- Data Growth: Rate of data size increase with usage
- Cache Growth: Rate of cache size increase with usage

## Using Baselines

1. **Performance Comparison**: Compare optimized versions against these baselines
2. **Regression Detection**: Ensure new features don't negatively impact performance
3. **Target Setting**: Use baselines to set realistic performance improvement targets
4. **Device Impact Analysis**: Understand performance differences across device tiers