#!/bin/bash
# Startup Time Test Script for English-Hindi Learning App
# Measures cold, warm, and hot start times

# Configuration
PACKAGE_NAME="com.example.englishhindi"
MAIN_ACTIVITY="com.example.englishhindi.ui.MainActivity"
ITERATIONS=5
OUTPUT_FILE="../results/startup_times_$(date +%Y%m%d_%H%M%S).csv"

# Create results directory if it doesn't exist
mkdir -p ../results

# CSV header
echo "Test Type,Iteration,Startup Time (ms)" > $OUTPUT_FILE

# Function to measure cold start
measure_cold_start() {
    echo "Measuring cold start (iteration $1)..."
    
    # Clear app from memory
    adb shell "am force-stop $PACKAGE_NAME"
    adb shell "pm clear $PACKAGE_NAME"
    sleep 2
    
    # Start app and measure time
    START_TIME=$(date +%s%3N)
    adb shell "am start -W -n $PACKAGE_NAME/$MAIN_ACTIVITY" | grep "TotalTime" | cut -d ' ' -f 2
    STARTUP_TIME=$?
    
    echo "Cold Start,$1,$STARTUP_TIME" >> $OUTPUT_FILE
}

# Function to measure warm start
measure_warm_start() {
    echo "Measuring warm start (iteration $1)..."
    
    # Close app but keep in memory
    adb shell "input keyevent KEYCODE_HOME"
    sleep 2
    
    # Start app and measure time
    START_TIME=$(date +%s%3N)
    adb shell "am start -W -n $PACKAGE_NAME/$MAIN_ACTIVITY" | grep "TotalTime" | cut -d ' ' -f 2
    STARTUP_TIME=$?
    
    echo "Warm Start,$1,$STARTUP_TIME" >> $OUTPUT_FILE
}

# Function to measure hot start
measure_hot_start() {
    echo "Measuring hot start (iteration $1)..."
    
    # Send to background
    adb shell "input keyevent KEYCODE_HOME"
    sleep 1
    
    # Start app and measure time
    START_TIME=$(date +%s%3N)
    adb shell "am start -W -n $PACKAGE_NAME/$MAIN_ACTIVITY" | grep "TotalTime" | cut -d ' ' -f 2
    STARTUP_TIME=$?
    
    echo "Hot Start,$1,$STARTUP_TIME" >> $OUTPUT_FILE
}

# Run tests
for ((i=1; i<=$ITERATIONS; i++)); do
    measure_cold_start $i
    sleep 2
    measure_warm_start $i
    sleep 2
    measure_hot_start $i
    sleep 2
done

echo "Startup time tests completed. Results saved to $OUTPUT_FILE"

# Calculate averages
echo "\nAverage Results:" >> $OUTPUT_FILE
echo "Cold Start Average: $(awk -F',' '/Cold Start/ {sum+=$3; count++} END {print sum/count}' $OUTPUT_FILE) ms" >> $OUTPUT_FILE
echo "Warm Start Average: $(awk -F',' '/Warm Start/ {sum+=$3; count++} END {print sum/count}' $OUTPUT_FILE) ms" >> $OUTPUT_FILE
echo "Hot Start Average: $(awk -F',' '/Hot Start/ {sum+=$3; count++} END {print sum/count}' $OUTPUT_FILE) ms" >> $OUTPUT_FILE