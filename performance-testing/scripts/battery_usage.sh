#!/bin/bash
# Battery Usage Monitoring Script for English-Hindi Learning App
# Measures battery consumption during active and background usage

# Configuration
PACKAGE_NAME="com.bhashasetu.app"
MAIN_ACTIVITY="com.bhashasetu.app.ui.MainActivity"
ACTIVE_DURATION=1800  # 30 minutes
BACKGROUND_DURATION=3600  # 60 minutes
INTERVAL=300  # 5 minutes
OUTPUT_FILE="../results/battery_usage_$(date +%Y%m%d_%H%M%S).csv"

# Create results directory if it doesn't exist
mkdir -p ../results

# Function to get battery stats for app
get_battery_stats() {
    adb shell "dumpsys batterystats --charged $PACKAGE_NAME" | grep -E "Uid|Computed|top=|fg=|bg="
}

# Function to get current battery level
get_battery_level() {
    adb shell "dumpsys battery" | grep "level" | cut -d: -f2 | tr -d ' '
}

# CSV header
echo "Timestamp,Battery Level,State,Duration (s),mAh Used" > $OUTPUT_FILE

# Reset battery stats
echo "Resetting battery stats..."
adb shell "dumpsys batterystats --reset"
sleep 2

# Get initial battery level
INITIAL_LEVEL=$(get_battery_level)
echo "Initial battery level: $INITIAL_LEVEL%"

# Active usage test
echo "Starting active usage test for $ACTIVE_DURATION seconds..."
START_TIME=$(date +%s)
adb shell "am start -n $PACKAGE_NAME/$MAIN_ACTIVITY"
sleep 5  # Wait for app to initialize

# Record start of active test
echo "$(date +%s),$INITIAL_LEVEL,Active Start,0,0" >> $OUTPUT_FILE

# Simulate active usage
END_TIME=$((START_TIME + ACTIVE_DURATION))
LAST_SAMPLE_TIME=$START_TIME

while [ $(date +%s) -lt $END_TIME ]; do
    CURRENT_TIME=$(date +%s)
    
    # Take battery sample every INTERVAL seconds
    if [ $((CURRENT_TIME - LAST_SAMPLE_TIME)) -ge $INTERVAL ]; then
        CURRENT_LEVEL=$(get_battery_level)
        ELAPSED=$((CURRENT_TIME - START_TIME))
        
        # Get approximate mAh used (this is simplified)
        PERCENTAGE_USED=$((INITIAL_LEVEL - CURRENT_LEVEL))
        # Assuming a 3000mAh battery
        MAH_USED=$(echo "scale=2; $PERCENTAGE_USED * 30" | bc)
        
        echo "$(date +%s),$CURRENT_LEVEL,Active,$ELAPSED,$MAH_USED" >> $OUTPUT_FILE
        LAST_SAMPLE_TIME=$CURRENT_TIME
    fi
    
    # Simulate user interaction
    RANDOM_ACTION=$((RANDOM % 5))
    case $RANDOM_ACTION in
        0) adb shell "input swipe 300 800 300 300" ;;  # Scroll down
        1) adb shell "input swipe 300 300 300 800" ;;  # Scroll up
        2) adb shell "input tap 300 500" ;;  # Tap center
        3) adb shell "input tap 500 500" ;;  # Tap right
        4) adb shell "input keyevent KEYCODE_BACK" && sleep 1 && adb shell "input keyevent KEYCODE_BACK" && sleep 1 && adb shell "am start -n $PACKAGE_NAME/$MAIN_ACTIVITY" ;;  # Back then restart
    esac
    
    sleep 3
done

# Get battery level after active test
ACTIVE_END_LEVEL=$(get_battery_level)
echo "Active usage test completed. Battery level: $ACTIVE_END_LEVEL%"
echo "$(date +%s),$ACTIVE_END_LEVEL,Active End,$ACTIVE_DURATION,0" >> $OUTPUT_FILE

# Background usage test
echo "Starting background usage test for $BACKGROUND_DURATION seconds..."
adb shell "input keyevent KEYCODE_HOME"  # Send app to background
sleep 5

# Record start of background test
START_TIME=$(date +%s)
echo "$(date +%s),$ACTIVE_END_LEVEL,Background Start,0,0" >> $OUTPUT_FILE
LAST_SAMPLE_TIME=$START_TIME

# Monitor in background
END_TIME=$((START_TIME + BACKGROUND_DURATION))

while [ $(date +%s) -lt $END_TIME ]; do
    CURRENT_TIME=$(date +%s)
    
    # Take battery sample every INTERVAL seconds
    if [ $((CURRENT_TIME - LAST_SAMPLE_TIME)) -ge $INTERVAL ]; then
        CURRENT_LEVEL=$(get_battery_level)
        ELAPSED=$((CURRENT_TIME - START_TIME))
        
        # Get approximate mAh used
        PERCENTAGE_USED=$((ACTIVE_END_LEVEL - CURRENT_LEVEL))
        # Assuming a 3000mAh battery
        MAH_USED=$(echo "scale=2; $PERCENTAGE_USED * 30" | bc)
        
        echo "$(date +%s),$CURRENT_LEVEL,Background,$ELAPSED,$MAH_USED" >> $OUTPUT_FILE
        LAST_SAMPLE_TIME=$CURRENT_TIME
    fi
    
    sleep $INTERVAL
done

# Get final battery level
FINAL_LEVEL=$(get_battery_level)
echo "Background usage test completed. Final battery level: $FINAL_LEVEL%"
echo "$(date +%s),$FINAL_LEVEL,Background End,$BACKGROUND_DURATION,0" >> $OUTPUT_FILE

# Calculate battery usage statistics
echo "\nBattery Usage Statistics:" >> $OUTPUT_FILE
echo "Active Usage: $((INITIAL_LEVEL - ACTIVE_END_LEVEL))% over $ACTIVE_DURATION seconds" >> $OUTPUT_FILE
echo "Background Usage: $((ACTIVE_END_LEVEL - FINAL_LEVEL))% over $BACKGROUND_DURATION seconds" >> $OUTPUT_FILE
echo "Total Usage: $((INITIAL_LEVEL - FINAL_LEVEL))%" >> $OUTPUT_FILE

# Calculate hourly drain rates
ACTIVE_HOURLY_RATE=$(echo "scale=2; ($INITIAL_LEVEL - $ACTIVE_END_LEVEL) * 3600 / $ACTIVE_DURATION" | bc)
BG_HOURLY_RATE=$(echo "scale=2; ($ACTIVE_END_LEVEL - $FINAL_LEVEL) * 3600 / $BACKGROUND_DURATION" | bc)

echo "Active Hourly Drain Rate: $ACTIVE_HOURLY_RATE% per hour" >> $OUTPUT_FILE
echo "Background Hourly Drain Rate: $BG_HOURLY_RATE% per hour" >> $OUTPUT_FILE

echo "Battery usage test completed. Results saved to $OUTPUT_FILE"