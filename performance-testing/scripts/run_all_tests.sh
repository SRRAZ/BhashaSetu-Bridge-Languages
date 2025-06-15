#!/bin/bash
# Run All Performance Tests Script for English-Hindi Learning App
# This script runs all performance tests and collects results

# Configuration
APP_VARIANT=$1  # free or premium
BUILD_TYPE=$2   # debug or release
DEVICE_TIER=$3  # low, mid, or high

# Default values if not provided
APP_VARIANT=${APP_VARIANT:-"free"}
BUILD_TYPE=${BUILD_TYPE:-"debug"}
DEVICE_TIER=${DEVICE_TIER:-"mid"}

# Output directory
OUTPUT_DIR="../results/$BUILD_TYPE/$APP_VARIANT"
mkdir -p "$OUTPUT_DIR"

# Log file
LOG_FILE="$OUTPUT_DIR/test_run_$(date +%Y%m%d_%H%M%S).log"

# Function to log messages
log() {
    echo "[$(date +"%Y-%m-%d %H:%M:%S")] $1" | tee -a "$LOG_FILE"
}

# Start test run
log "Starting performance test run for $APP_VARIANT $BUILD_TYPE on $DEVICE_TIER tier device"

# Check for connected device
DEVICE_COUNT=$(adb devices | grep -c "device$")
if [ "$DEVICE_COUNT" -eq 0 ]; then
    log "ERROR: No devices connected. Please connect a device and try again."
    exit 1
fi

# Install the appropriate APK
APK_PATH=""
if [ "$BUILD_TYPE" = "debug" ]; then
    APK_PATH="../../app/build/outputs/apk/$APP_VARIANT/debug/app-$APP_VARIANT-debug.apk"
else
    APK_PATH="../../release-package/dummy-release-apk.apk"  # Using dummy APK for this example
fi

log "Installing $APK_PATH"
if [ -f "$APK_PATH" ]; then
    adb install -r "$APK_PATH"
    log "APK installed successfully"
else
    log "WARNING: APK not found at $APK_PATH. Assuming app is already installed."
fi

# Run device preparation script
log "Preparing device for testing..."
adb shell "settings put global stay_on_while_plugged_in 3"  # Keep screen on when charging
adb shell "settings put system screen_brightness 128"  # Set brightness to 50%
adb shell "cmd notification set_dnd priority-only"  # Set Do Not Disturb mode

# Run startup time tests
log "Running startup time tests..."
./startup_time_test.sh
if [ $? -eq 0 ]; then
    log "Startup time tests completed successfully"
else
    log "ERROR: Startup time tests failed"
fi

# Run memory profile tests
log "Running memory profile tests..."
./memory_profile.sh
if [ $? -eq 0 ]; then
    log "Memory profile tests completed successfully"
else
    log "ERROR: Memory profile tests failed"
fi

# Run frame stats tests
log "Running frame statistics tests..."
./frame_stats.sh
if [ $? -eq 0 ]; then
    log "Frame statistics tests completed successfully"
else
    log "ERROR: Frame statistics tests failed"
fi

# Run battery usage tests
log "Running battery usage tests..."
./battery_usage.sh
if [ $? -eq 0 ]; then
    log "Battery usage tests completed successfully"
else
    log "ERROR: Battery usage tests failed"
fi

# Run storage impact tests
log "Running storage impact tests..."
./storage_impact.sh
if [ $? -eq 0 ]; then
    log "Storage impact tests completed successfully"
else
    log "ERROR: Storage impact tests failed"
fi

# Run AndroidJUnit benchmark tests if connected to Android Studio
log "Checking for ability to run AndroidJUnit benchmark tests..."
if [ -f "../../gradlew" ]; then
    log "Running benchmark tests..."
    cd ../../
    
    # Run startup benchmark
    ./gradlew cAT -P android.testInstrumentationRunnerArguments.class=com.bhashasetu.app.benchmark.StartupBenchmark
    
    # Run rendering benchmark
    ./gradlew cAT -P android.testInstrumentationRunnerArguments.class=com.bhashasetu.app.benchmark.RenderingBenchmark
    
    # Run memory benchmark
    ./gradlew cAT -P android.testInstrumentationRunnerArguments.class=com.bhashasetu.app.benchmark.MemoryBenchmark
    
    cd - > /dev/null
    log "Benchmark tests completed"
else
    log "WARNING: Gradle wrapper not found, skipping benchmark tests"
fi

# Generate report
log "Generating performance report..."
./generate_report.py --variant "$APP_VARIANT"
if [ $? -eq 0 ]; then
    log "Performance report generated successfully"
else
    log "ERROR: Performance report generation failed"
fi

# Clean up
log "Restoring device settings..."
adb shell "settings put global stay_on_while_plugged_in 0"  # Reset stay on setting
adb shell "cmd notification set_dnd none"  # Disable Do Not Disturb mode

log "Test run completed. Results stored in $OUTPUT_DIR"

# Print summary
echo ""
echo "===== TEST RUN SUMMARY ====="
echo "App Variant: $APP_VARIANT"
echo "Build Type: $BUILD_TYPE"
echo "Device Tier: $DEVICE_TIER"
echo "Results Directory: $OUTPUT_DIR"
echo "Log File: $LOG_FILE"
echo "==========================="