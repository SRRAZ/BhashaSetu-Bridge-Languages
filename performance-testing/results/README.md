# Performance Test Results Directory

This directory contains the results of performance tests run on the English-Hindi Learning App. These results are used to evaluate performance improvements, identify bottlenecks, and generate performance reports.

## Directory Structure

```
results/
  ├── debug/
  │   ├── free/
  │   │   ├── startup_times_[TIMESTAMP].csv
  │   │   ├── memory_profile_[TIMESTAMP].csv
  │   │   ├── frame_stats_[TIMESTAMP].csv
  │   │   ├── battery_usage_[TIMESTAMP].csv
  │   │   └── storage_impact_[TIMESTAMP].csv
  │   └── premium/
  │       ├── startup_times_[TIMESTAMP].csv
  │       ├── memory_profile_[TIMESTAMP].csv
  │       ├── frame_stats_[TIMESTAMP].csv
  │       ├── battery_usage_[TIMESTAMP].csv
  │       └── storage_impact_[TIMESTAMP].csv
  └── release/
      ├── free/
      │   ├── startup_times_[TIMESTAMP].csv
      │   ├── memory_profile_[TIMESTAMP].csv
      │   ├── frame_stats_[TIMESTAMP].csv
      │   ├── battery_usage_[TIMESTAMP].csv
      │   └── storage_impact_[TIMESTAMP].csv
      └── premium/
          ├── startup_times_[TIMESTAMP].csv
          ├── memory_profile_[TIMESTAMP].csv
          ├── frame_stats_[TIMESTAMP].csv
          ├── battery_usage_[TIMESTAMP].csv
          └── storage_impact_[TIMESTAMP].csv
```

## File Format Details

### startup_times_[TIMESTAMP].csv
```
Test Type,Iteration,Startup Time (ms)
Cold Start,1,2345
Cold Start,2,2123
...
Warm Start,1,1234
...
Hot Start,1,456
...

Average Results:
Cold Start Average: 2234 ms
Warm Start Average: 1300 ms
Hot Start Average: 432 ms
```

### memory_profile_[TIMESTAMP].csv
```
Timestamp,Total PSS (KB),Native Heap (KB),Dalvik Heap (KB),Views,Activities,App State
1621234567,45678,23456,12345,123,3,Home Screen
1621234577,47890,24567,13456,145,3,Word List
...

Memory Usage Statistics:
Average Total PSS: 48123 KB
Max Total PSS: 52345 KB
Average Native Heap: 25678 KB
Average Dalvik Heap: 14567 KB
Max Views: 156
```

### frame_stats_[TIMESTAMP].csv
```
Frame Number,Intended Vsync,Vsync,...,Frame Completed
1,12345678,12345679,...,12345699
...

Frame Timing Statistics:
Total Frames: 234
Janky Frames: 12 (5.1%)
Average Frame Time: 8.3 ms
90th Percentile Frame Time: 14.2 ms
```

### battery_usage_[TIMESTAMP].csv
```
Timestamp,Battery Level,State,Duration (s),mAh Used
1621234567,100,Active Start,0,0
1621234867,99,Active,300,30
...

Battery Usage Statistics:
Active Usage: 3% over 1800 seconds
Background Usage: 1% over 3600 seconds
Total Usage: 4%
Active Hourly Drain Rate: 6.0% per hour
Background Hourly Drain Rate: 1.0% per hour
```

### storage_impact_[TIMESTAMP].csv
```
Day,Total App Size (KB),APK Size (KB),Data Size (KB),Cache Size (KB),Database Size (KB)
0,23456,15678,7890,234,345
1,23789,15678,8123,345,456
...

Storage Growth Statistics:
Data Size Growth: 567 KB total, 113.4 KB per day
Cache Size Growth: 234 KB total, 46.8 KB per day
Database Size Growth: 123 KB total, 24.6 KB per day
Projected Monthly Data Growth: 3402 KB
Projected Monthly Cache Growth: 1404 KB
Projected Monthly Database Growth: 738 KB
```

## Results Usage

The data collected in this directory is used to:

1. **Generate Performance Reports**: Detailed analysis of app performance
2. **Identify Bottlenecks**: Pinpoint performance issues for optimization
3. **Validate Optimizations**: Confirm performance improvements
4. **Track Changes Over Time**: Monitor performance changes across releases

## Analysis Tools

Custom scripts for analyzing these results are located in the `scripts` directory. These tools can:

- Generate comparative charts and visualizations
- Calculate performance improvements
- Identify performance regressions
- Generate JSON/CSV summaries for further processing