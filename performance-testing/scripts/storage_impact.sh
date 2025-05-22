#!/bin/bash
# Storage Impact Analysis Script for English-Hindi Learning App
# Measures app size, database growth, and cache size

# Configuration
PACKAGE_NAME="com.example.englishhindi"
MAIN_ACTIVITY="com.example.englishhindi.ui.MainActivity"
DAYS=5  # Simulate 5 days of usage
OUTPUT_FILE="../results/storage_impact_$(date +%Y%m%d_%H%M%S).csv"

# Create results directory if it doesn't exist
mkdir -p ../results

# CSV header
echo "Day,Total App Size (KB),APK Size (KB),Data Size (KB),Cache Size (KB),Database Size (KB)" > $OUTPUT_FILE

# Function to get storage stats
get_storage_stats() {
    # Get package stats
    PACKAGE_STATS=$(adb shell "dumpsys package $PACKAGE_NAME" | grep -E "dataDir|codePath")
    DATA_DIR=$(echo "$PACKAGE_STATS" | grep "dataDir" | cut -d= -f2)
    CODE_PATH=$(echo "$PACKAGE_STATS" | grep "codePath" | cut -d= -f2)
    
    # Get APK size
    APK_SIZE=$(adb shell "du -k $CODE_PATH" | cut -f1)
    
    # Get data size
    DATA_SIZE=$(adb shell "du -k $DATA_DIR" | cut -f1)
    
    # Get database size
    DB_SIZE=$(adb shell "du -k $DATA_DIR/databases" 2>/dev/null | cut -f1)
    if [ -z "$DB_SIZE" ]; then
        DB_SIZE=0
    fi
    
    # Get cache size
    CACHE_SIZE=$(adb shell "du -k $DATA_DIR/cache" 2>/dev/null | cut -f1)
    if [ -z "$CACHE_SIZE" ]; then
        CACHE_SIZE=0
    fi
    
    # Calculate total app size
    TOTAL_SIZE=$((APK_SIZE + DATA_SIZE))
    
    echo "$APK_SIZE,$DATA_SIZE,$CACHE_SIZE,$DB_SIZE,$TOTAL_SIZE"
}

# Start app and clear data for initial state
echo "Preparing app for storage analysis..."
adb shell "pm clear $PACKAGE_NAME"
adb shell "am start -n $PACKAGE_NAME/$MAIN_ACTIVITY"
sleep 5  # Wait for app to initialize

# Get initial storage stats
echo "Getting initial storage stats..."
INITIAL_STATS=$(get_storage_stats)
APK_SIZE=$(echo "$INITIAL_STATS" | cut -d, -f1)
DATA_SIZE=$(echo "$INITIAL_STATS" | cut -d, -f2)
CACHE_SIZE=$(echo "$INITIAL_STATS" | cut -d, -f3)
DB_SIZE=$(echo "$INITIAL_STATS" | cut -d, -f4)
TOTAL_SIZE=$(echo "$INITIAL_STATS" | cut -d, -f5)

echo "0,$TOTAL_SIZE,$APK_SIZE,$DATA_SIZE,$CACHE_SIZE,$DB_SIZE" >> $OUTPUT_FILE

echo "Starting storage impact analysis for $DAYS simulated days..."

# Simulate daily usage and measure storage impact
for day in $(seq 1 $DAYS); do
    echo "Simulating day $day usage..."
    
    # Simulate intensive app usage
    for i in $(seq 1 10); do
        # Navigate to different screens
        adb shell "input tap 300 500"  # Tap center
        sleep 2
        adb shell "input swipe 300 800 300 300"  # Scroll down
        sleep 2
        adb shell "input tap 500 500"  # Tap right
        sleep 2
        adb shell "input keyevent KEYCODE_BACK"  # Back
        sleep 1
        
        # Add simulated content (e.g., bookmarking words, completing exercises)
        adb shell "input tap 300 600"  # Tap to select item
        sleep 1
        adb shell "input tap 500 600"  # Tap to bookmark/interact
        sleep 2
        
        # Simulate search/lookup
        adb shell "input tap 100 100"  # Tap search icon
        sleep 1
        RANDOM_WORD=$((RANDOM % 5))
        case $RANDOM_WORD in
            0) adb shell "input text 'hello'" ;;
            1) adb shell "input text 'world'" ;;
            2) adb shell "input text 'learning'" ;;
            3) adb shell "input text 'language'" ;;
            4) adb shell "input text 'dictionary'" ;;
        esac
        sleep 1
        adb shell "input keyevent KEYCODE_ENTER"
        sleep 3
        adb shell "input keyevent KEYCODE_BACK"
        sleep 1
    done
    
    # Get updated storage stats
    CURRENT_STATS=$(get_storage_stats)
    APK_SIZE=$(echo "$CURRENT_STATS" | cut -d, -f1)
    DATA_SIZE=$(echo "$CURRENT_STATS" | cut -d, -f2)
    CACHE_SIZE=$(echo "$CURRENT_STATS" | cut -d, -f3)
    DB_SIZE=$(echo "$CURRENT_STATS" | cut -d, -f4)
    TOTAL_SIZE=$(echo "$CURRENT_STATS" | cut -d, -f5)
    
    echo "$day,$TOTAL_SIZE,$APK_SIZE,$DATA_SIZE,$CACHE_SIZE,$DB_SIZE" >> $OUTPUT_FILE
    
    # Keep app in background overnight
    adb shell "input keyevent KEYCODE_HOME"
    sleep 10
end

# Calculate storage growth statistics
echo "\nStorage Growth Statistics:" >> $OUTPUT_FILE

# Calculate daily growth rates
INITIAL_DATA=$(awk -F',' 'NR==2 {print $4}' $OUTPUT_FILE)
FINAL_DATA=$(awk -F',' 'END {print $4}' $OUTPUT_FILE)
DATA_GROWTH=$((FINAL_DATA - INITIAL_DATA))
DAILY_DATA_GROWTH=$(echo "scale=2; $DATA_GROWTH / $DAYS" | bc)

INITIAL_CACHE=$(awk -F',' 'NR==2 {print $5}' $OUTPUT_FILE)
FINAL_CACHE=$(awk -F',' 'END {print $5}' $OUTPUT_FILE)
CACHE_GROWTH=$((FINAL_CACHE - INITIAL_CACHE))
DAILY_CACHE_GROWTH=$(echo "scale=2; $CACHE_GROWTH / $DAYS" | bc)

INITIAL_DB=$(awk -F',' 'NR==2 {print $6}' $OUTPUT_FILE)
FINAL_DB=$(awk -F',' 'END {print $6}' $OUTPUT_FILE)
DB_GROWTH=$((FINAL_DB - INITIAL_DB))
DAILY_DB_GROWTH=$(echo "scale=2; $DB_GROWTH / $DAYS" | bc)

echo "Data Size Growth: $DATA_GROWTH KB total, $DAILY_DATA_GROWTH KB per day" >> $OUTPUT_FILE
echo "Cache Size Growth: $CACHE_GROWTH KB total, $DAILY_CACHE_GROWTH KB per day" >> $OUTPUT_FILE
echo "Database Size Growth: $DB_GROWTH KB total, $DAILY_DB_GROWTH KB per day" >> $OUTPUT_FILE

# Project monthly growth
MONTHLY_DATA_GROWTH=$(echo "scale=2; $DAILY_DATA_GROWTH * 30" | bc)
MONTHLY_CACHE_GROWTH=$(echo "scale=2; $DAILY_CACHE_GROWTH * 30" | bc)
MONTHLY_DB_GROWTH=$(echo "scale=2; $DAILY_DB_GROWTH * 30" | bc)

echo "Projected Monthly Data Growth: $MONTHLY_DATA_GROWTH KB" >> $OUTPUT_FILE
echo "Projected Monthly Cache Growth: $MONTHLY_CACHE_GROWTH KB" >> $OUTPUT_FILE
echo "Projected Monthly Database Growth: $MONTHLY_DB_GROWTH KB" >> $OUTPUT_FILE

echo "Storage impact analysis completed. Results saved to $OUTPUT_FILE"