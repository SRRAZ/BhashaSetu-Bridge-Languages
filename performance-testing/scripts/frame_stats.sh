#!/bin/bash
# Frame Statistics Script for English-Hindi Learning App
# Measures rendering performance and jank

# Configuration
PACKAGE_NAME="com.bhashasetu.app"
MAIN_ACTIVITY="com.bhashasetu.app.ui.MainActivity"
DURATION=60  # 60 seconds
OUTPUT_FILE="../results/frame_stats_$(date +%Y%m%d_%H%M%S).csv"

# Create results directory if it doesn't exist
mkdir -p ../results

# Start app
echo "Starting app..."
adb shell "am force-stop $PACKAGE_NAME"
adb shell "am start -n $PACKAGE_NAME/$MAIN_ACTIVITY"
sleep 5  # Wait for app to initialize

# Reset frame stats
adb shell "dumpsys gfxinfo $PACKAGE_NAME reset"

# Simulate user interaction (scrolling)
echo "Simulating user interaction for $DURATION seconds..."
END_TIME=$(($(date +%s) + DURATION))

while [ $(date +%s) -lt $END_TIME ]; do
    # Scroll down
    adb shell "input swipe 300 800 300 300"
    sleep 1
    
    # Scroll up
    adb shell "input swipe 300 300 300 800"
    sleep 1
done

# Collect frame stats
echo "Collecting frame statistics..."
adb shell "dumpsys gfxinfo $PACKAGE_NAME framestats" > ../results/raw_frame_stats.txt

# Process and extract frame data
echo "Processing frame data..."
echo "Frame Number,Intended Vsync,Vsync,Oldest Input Event,Newest Input Event,Handle Input Start,Animation Start,Perform Traversals Start,Draw Start,Sync Queue,Command Issue,Swap Buffers,Frame Completed" > $OUTPUT_FILE

# Extract frame stats lines (starts after "---PROFILEDATA---" and ends before "---PROFILEDATA---")
awk '/---PROFILEDATA---/{flag=1;next}/---PROFILEDATA---/{flag=0}flag' ../results/raw_frame_stats.txt | \
while read -r line; do
    # Skip empty lines
    [ -z "$line" ] && continue
    
    # Convert line to CSV
    echo "$line" | tr ',[] ' ',' | sed 's/,,/,/g' >> $OUTPUT_FILE
done

# Calculate frame time statistics
echo "Calculating frame statistics..."
echo "\nFrame Timing Statistics:" >> $OUTPUT_FILE

# Calculate total frames
TOTAL_FRAMES=$(grep -v "Frame Number" $OUTPUT_FILE | wc -l)
echo "Total Frames: $TOTAL_FRAMES" >> $OUTPUT_FILE

# Calculate frames that took more than 16ms (jank)
JANK_FRAMES=$(awk -F',' 'NR>1 && ($13-$2)>16000000 {count++} END {print count}' $OUTPUT_FILE)
JANK_PERCENT=$(echo "scale=2; $JANK_FRAMES*100/$TOTAL_FRAMES" | bc)
echo "Janky Frames: $JANK_FRAMES ($JANK_PERCENT%)" >> $OUTPUT_FILE

# Calculate average frame time
AVG_FRAME_TIME=$(awk -F',' 'NR>1 {sum+=$13-$2} END {printf "%.2f", sum/NR/1000000}' $OUTPUT_FILE)
echo "Average Frame Time: $AVG_FRAME_TIME ms" >> $OUTPUT_FILE

# Calculate 90th percentile frame time
PERCENTILE_90=$(awk -F',' 'NR>1 {times[NR-1]=$13-$2} END {asort(times); printf "%.2f", times[int(NR*0.9)]/1000000}' $OUTPUT_FILE)
echo "90th Percentile Frame Time: $PERCENTILE_90 ms" >> $OUTPUT_FILE

echo "Frame statistics collection completed. Results saved to $OUTPUT_FILE"