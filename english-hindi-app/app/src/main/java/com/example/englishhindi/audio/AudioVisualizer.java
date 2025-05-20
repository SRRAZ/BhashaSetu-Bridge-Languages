package com.example.englishhindi.audio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.englishhindi.R;

import java.util.Random;

/**
 * A customizable audio visualizer view that can be used to display audio waveforms,
 * or to animate a visualization during audio playback and recording.
 */
public class AudioVisualizer extends View {
    
    private static final int MIN_BARS = 3;
    private static final int MAX_BARS = 32;
    private static final int DEFAULT_BARS = 7;
    
    private static final int MIN_BAR_HEIGHT = 5;
    private static final int MAX_BAR_HEIGHT = 350;
    private static final int DEFAULT_SPACING = 4;
    
    // Paint and drawing configuration
    private Paint barPaint;
    private RectF barRect;
    private int barColor;
    private int barCornerRadius = 8;
    
    // Bar configuration
    private int numberOfBars = DEFAULT_BARS;
    private int barWidth = 20;
    private int barSpacing = DEFAULT_SPACING;
    private int[] barHeights;
    private Random random = new Random();
    
    // Animation control
    private boolean isAnimating = false;
    private Handler animationHandler = new Handler(Looper.getMainLooper());
    private Runnable animationRunnable;
    private int animationIntervalMs = 100;
    
    // Visualization mode
    private VisualizationMode mode = VisualizationMode.BARS;
    
    // Waveform path (for waveform mode)
    private Path waveformPath;
    
    // Current amplitude value (0-1)
    private float currentAmplitude = 0;
    
    // Audio data for visualization
    private short[] audioData;
    
    /**
     * Visualization modes
     */
    public enum VisualizationMode {
        BARS,     // Bar visualization
        WAVEFORM  // Waveform line visualization
    }
    
    public AudioVisualizer(Context context) {
        super(context);
        init(context, null);
    }
    
    public AudioVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public AudioVisualizer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    /**
     * Initialize the visualizer.
     */
    private void init(Context context, AttributeSet attrs) {
        // Initialize paint for bars
        barPaint = new Paint();
        barPaint.setAntiAlias(true);
        barRect = new RectF();
        
        // Get color from theme
        barColor = ContextCompat.getColor(context, R.color.colorAccent);
        barPaint.setColor(barColor);
        
        // Initialize waveform path
        waveformPath = new Path();
        
        // Initialize bar heights
        barHeights = new int[numberOfBars];
        resetBarHeights();
        
        // Initialize animation runnable
        animationRunnable = new Runnable() {
            @Override
            public void run() {
                if (isAnimating) {
                    updateVisualization();
                    invalidate();
                    animationHandler.postDelayed(this, animationIntervalMs);
                }
            }
        };
    }
    
    /**
     * Set the visualization mode.
     * 
     * @param mode The visualization mode
     */
    public void setMode(VisualizationMode mode) {
        this.mode = mode;
        invalidate();
    }
    
    /**
     * Set the bar color.
     * 
     * @param color The color for bars/waveform
     */
    public void setBarColor(int color) {
        barColor = color;
        barPaint.setColor(barColor);
        invalidate();
    }
    
    /**
     * Set the number of bars to display.
     * 
     * @param count The number of bars (MIN_BARS to MAX_BARS)
     */
    public void setBarCount(int count) {
        numberOfBars = Math.max(MIN_BARS, Math.min(MAX_BARS, count));
        barHeights = new int[numberOfBars];
        resetBarHeights();
        calculateBarWidth();
        invalidate();
    }
    
    /**
     * Set the spacing between bars.
     * 
     * @param spacing The spacing in pixels
     */
    public void setBarSpacing(int spacing) {
        barSpacing = Math.max(0, spacing);
        calculateBarWidth();
        invalidate();
    }
    
    /**
     * Set the corner radius for bars.
     * 
     * @param radius The corner radius
     */
    public void setBarCornerRadius(int radius) {
        barCornerRadius = radius;
        invalidate();
    }
    
    /**
     * Set the animation interval.
     * 
     * @param intervalMs The interval in milliseconds
     */
    public void setAnimationInterval(int intervalMs) {
        animationIntervalMs = Math.max(16, intervalMs); // Minimum 16ms for ~60fps
    }
    
    /**
     * Start the animation.
     */
    public void startAnimation() {
        if (!isAnimating) {
            isAnimating = true;
            animationHandler.post(animationRunnable);
        }
    }
    
    /**
     * Stop the animation.
     */
    public void stopAnimation() {
        isAnimating = false;
        animationHandler.removeCallbacks(animationRunnable);
    }
    
    /**
     * Set the current amplitude for visualization (0-1).
     * 
     * @param amplitude The amplitude value (0-1)
     */
    public void setAmplitude(float amplitude) {
        currentAmplitude = Math.max(0, Math.min(1, amplitude));
        if (!isAnimating) {
            updateVisualization();
            invalidate();
        }
    }
    
    /**
     * Set audio data for visualization.
     * 
     * @param data The audio sample data
     */
    public void setAudioData(short[] data) {
        audioData = data;
        if (!isAnimating) {
            updateVisualizationFromData();
            invalidate();
        }
    }
    
    /**
     * Reset all bar heights to minimum.
     */
    private void resetBarHeights() {
        for (int i = 0; i < barHeights.length; i++) {
            barHeights[i] = MIN_BAR_HEIGHT;
        }
    }
    
    /**
     * Calculate the bar width based on view width and number of bars.
     */
    private void calculateBarWidth() {
        if (getWidth() > 0) {
            int availableWidth = getWidth() - (barSpacing * (numberOfBars - 1));
            barWidth = availableWidth / numberOfBars;
        }
    }
    
    /**
     * Update the visualization (called during animation).
     */
    private void updateVisualization() {
        if (audioData != null && audioData.length > 0) {
            updateVisualizationFromData();
            return;
        }
        
        // Calculate max height based on view height and amplitude
        int maxHeight = (int) (getHeight() * 0.8f * currentAmplitude);
        maxHeight = Math.max(MIN_BAR_HEIGHT, Math.min(MAX_BAR_HEIGHT, maxHeight));
        
        // Update bar heights with randomness
        for (int i = 0; i < barHeights.length; i++) {
            if (currentAmplitude > 0) {
                // Random height with amplitude factored in
                barHeights[i] = MIN_BAR_HEIGHT + random.nextInt(maxHeight - MIN_BAR_HEIGHT + 1);
            } else {
                // When amplitude is 0, show minimal bars
                barHeights[i] = MIN_BAR_HEIGHT;
            }
        }
    }
    
    /**
     * Update visualization based on actual audio data.
     */
    private void updateVisualizationFromData() {
        if (audioData == null || audioData.length == 0) {
            return;
        }
        
        // Calculate bar heights from audio data samples
        int samplesPerBar = audioData.length / numberOfBars;
        if (samplesPerBar <= 0) samplesPerBar = 1;
        
        for (int i = 0; i < numberOfBars; i++) {
            int startSample = i * samplesPerBar;
            int endSample = startSample + samplesPerBar - 1;
            if (endSample >= audioData.length) {
                endSample = audioData.length - 1;
            }
            
            // Calculate average amplitude for this bar
            int sum = 0;
            int count = 0;
            for (int j = startSample; j <= endSample; j++) {
                sum += Math.abs(audioData[j]);
                count++;
            }
            
            float average = count > 0 ? (float) sum / count : 0;
            
            // Scale to view height (0 to 32767 for 16-bit samples)
            float scaledHeight = (average / 32767.0f) * getHeight() * 0.8f;
            
            // Ensure minimum height
            barHeights[i] = Math.max(MIN_BAR_HEIGHT, (int) scaledHeight);
        }
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateBarWidth();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        if (mode == VisualizationMode.BARS) {
            drawBars(canvas);
        } else {
            drawWaveform(canvas);
        }
    }
    
    /**
     * Draw the bar visualization.
     * 
     * @param canvas The canvas to draw on
     */
    private void drawBars(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        
        // Calculate total width needed
        int totalBarWidth = numberOfBars * barWidth;
        int totalSpacingWidth = (numberOfBars - 1) * barSpacing;
        int startX = (width - (totalBarWidth + totalSpacingWidth)) / 2;
        
        for (int i = 0; i < numberOfBars; i++) {
            int left = startX + (i * (barWidth + barSpacing));
            int top = height - barHeights[i];
            
            barRect.set(left, top, left + barWidth, height);
            canvas.drawRoundRect(barRect, barCornerRadius, barCornerRadius, barPaint);
        }
    }
    
    /**
     * Draw the waveform visualization.
     * 
     * @param canvas The canvas to draw on
     */
    private void drawWaveform(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int centerY = height / 2;
        
        waveformPath.reset();
        waveformPath.moveTo(0, centerY);
        
        // Draw the waveform line
        if (audioData != null && audioData.length > 0) {
            // Use actual audio data if available
            float xStep = (float) width / audioData.length;
            for (int i = 0; i < audioData.length; i++) {
                float x = i * xStep;
                float scaledY = audioData[i] / 32767.0f * height * 0.4f;
                float y = centerY + scaledY;
                
                if (i == 0) {
                    waveformPath.moveTo(x, y);
                } else {
                    waveformPath.lineTo(x, y);
                }
            }
        } else {
            // Use bar heights to create a waveform-like appearance
            float xStep = (float) width / barHeights.length;
            for (int i = 0; i < barHeights.length; i++) {
                float x = i * xStep;
                float scaledHeight = (float) barHeights[i] / getHeight() * height * 0.4f;
                float y = centerY - scaledHeight;
                
                if (i == 0) {
                    waveformPath.moveTo(x, y);
                } else {
                    waveformPath.lineTo(x, y);
                }
            }
            
            // Complete the waveform with a mirror image on the bottom
            for (int i = barHeights.length - 1; i >= 0; i--) {
                float x = i * xStep;
                float scaledHeight = (float) barHeights[i] / getHeight() * height * 0.4f;
                float y = centerY + scaledHeight;
                waveformPath.lineTo(x, y);
            }
            
            waveformPath.close();
        }
        
        // Draw the waveform
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setStrokeWidth(3);
        canvas.drawPath(waveformPath, barPaint);
        barPaint.setStyle(Paint.Style.FILL);
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }
}