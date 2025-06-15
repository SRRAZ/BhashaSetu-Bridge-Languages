#!/bin/bash
# Memory Profile Script for English-Hindi Learning App
# Tracks memory usage across different app states

# Configuration
PACKAGE_NAME="com.bhashasetu.app"
MAIN_ACTIVITY="com.bhashasetu.app.ui.MainActivity"
DURATION=300  # 5 minutes
INTERVAL=10   # 10 seconds
OUTPUT_FILE="../results/memory_profile_$(date +%Y%m%d_%H%M%S).csv"

# Create results directory if it doesn't exist
mkdir -p ../results

# Function to get memory stats
get_memory_stats() {
    adb shell "dumpsys meminfo $PACKAGE_NAME" | grep -E "TOTAL:|Native Heap:|Dalvik Heap:|Views:|Activities:"
}

# CSV header
echo "Timestamp,Total PSS (KB),Native Heap (KB),Dalvik Heap (KB),Views,Activities,App State" > $OUTPUT_FILE

# Start app
echo "Starting app..."
adb shell "am force-stop $PACKAGE_NAME"
adb shell "am start -n $PACKAGE_NAME/$MAIN_ACTIVITY"
sleep 5  # Wait for app to initialize

# Take initial reading (home screen)
TIMESTAMP=$(date +%s)
MEMORY_STATS=$(get_memory_stats)
TOTAL_PSS=$(echo "$MEMORY_STATS" | grep "TOTAL:" | awk '{print $2}')
NATIVE_HEAP=$(echo "$MEMORY_STATS" | grep "Native Heap:" | awk '{print $3}')
DALVIK_HEAP=$(echo "$MEMORY_STATS" | grep "Dalvik Heap:" | awk '{print $3}')
VIEWS=$(echo "$MEMORY_STATS" | grep "Views:" | awk '{print $2}')
ACTIVITIES=$(echo "$MEMORY_STATS" | grep "Activities:" | awk '{print $2}')

echo "$TIMESTAMP,$TOTAL_PSS,$NATIVE_HEAP,$DALVIK_HEAP,$VIEWS,$ACTIVITIES,Home Screen" >> $OUTPUT_FILE

echo "Starting memory profiling for $DURATION seconds, sampling every $INTERVAL seconds..."

# Simulate user navigation to capture memory in different states
STATES=("Word List" "Dictionary" "Quiz" "Learning Module" "Settings" "Profile")
STATE_COMMANDS=(
    "input tap 300 500"  # Tap to navigate to Word List
    "input tap 300 700"  # Tap to navigate to Dictionary
    "input tap 300 900"  # Tap to navigate to Quiz
    "input tap 500 500"  # Tap to navigate to Learning Module
    "input tap 500 700"  # Tap to navigate to Settings
    "input tap 500 900"  # Tap to navigate to Profile
)

# Start collecting samples
START_TIME=$(date +%s)
STATE_INDEX=0
CURRENT_STATE=${STATES[$STATE_INDEX]}

while true; do
    CURRENT_TIME=$(date +%s)
    ELAPSED=$((CURRENT_TIME - START_TIME))
    
    if [ $ELAPSED -ge $DURATION ]; then
        break
    fi
    
    # Every minute, change the app state to simulate user navigation
    if [ $((ELAPSED % 60)) -eq 0 ] && [ $ELAPSED -gt 0 ]; then
        STATE_INDEX=$(( (STATE_INDEX + 1) % ${#STATES[@]} ))
        CURRENT_STATE=${STATES[$STATE_INDEX]}
        echo "Navigating to $CURRENT_STATE..."
        adb shell "${STATE_COMMANDS[$STATE_INDEX]}"
        sleep 3  # Wait for navigation to complete
    fi
    
    # Get memory stats
    TIMESTAMP=$(date +%s)
    MEMORY_STATS=$(get_memory_stats)
    TOTAL_PSS=$(echo "$MEMORY_STATS" | grep "TOTAL:" | awk '{print $2}')
    NATIVE_HEAP=$(echo "$MEMORY_STATS" | grep "Native Heap:" | awk '{print $3}')
    DALVIK_HEAP=$(echo "$MEMORY_STATS" | grep "Dalvik Heap:" | awk '{print $3}')
    VIEWS=$(echo "$MEMORY_STATS" | grep "Views:" | awk '{print $2}')
    ACTIVITIES=$(echo "$MEMORY_STATS" | grep "Activities:" | awk '{print $2}')
    
    echo "$TIMESTAMP,$TOTAL_PSS,$NATIVE_HEAP,$DALVIK_HEAP,$VIEWS,$ACTIVITIES,$CURRENT_STATE" >> $OUTPUT_FILE
    
    sleep $INTERVAL
done

echo "Memory profiling completed. Results saved to $OUTPUT_FILE"

# Calculate statistics
echo "\nMemory Usage Statistics:" >> $OUTPUT_FILE
echo "Average Total PSS: $(awk -F',' '{sum+=$2; count++} END {print sum/count}' $OUTPUT_FILE) KB" >> $OUTPUT_FILE
echo "Max Total PSS: $(awk -F',' '{if($2>max) max=$2} END {print max}' $OUTPUT_FILE) KB" >> $OUTPUT_FILE
echo "Average Native Heap: $(awk -F',' '{sum+=$3; count++} END {print sum/count}' $OUTPUT_FILE) KB" >> $OUTPUT_FILE
echo "Average Dalvik Heap: $(awk -F',' '{sum+=$4; count++} END {print sum/count}' $OUTPUT_FILE) KB" >> $OUTPUT_FILE
echo "Max Views: $(awk -F',' '{if($5>max) max=$5} END {print max}' $OUTPUT_FILE)" >> $OUTPUT_FILE