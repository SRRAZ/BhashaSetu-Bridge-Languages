package com.bhashasetu.app.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Custom view that displays an animated starburst effect.
 * Used for celebratory animations like level-up.
 */
public class StarburstView extends View {

    private static final int DEFAULT_RAY_COUNT = 12;
    private static final int DEFAULT_COLOR = Color.parseColor("#FFC107"); // Amber
    
    private List<Ray> rays = new ArrayList<>();
    private Paint rayPaint;
    private Path rayPath;
    
    private ValueAnimator animator;
    private float animationProgress = 0f;
    private Random random = new Random();
    
    private int rayCount = DEFAULT_RAY_COUNT;
    private int rayColor = DEFAULT_COLOR;
    private boolean isAnimating = false;

    public StarburstView(Context context) {
        super(context);
        init(null);
    }

    public StarburstView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StarburstView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    
    private void init(@Nullable AttributeSet attrs) {
        // Initialize paint for rays
        rayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rayPaint.setColor(rayColor);
        rayPaint.setStyle(Paint.Style.FILL);
        
        // Initialize path for drawing rays
        rayPath = new Path();
        
        // Initialize animator
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(animation -> {
            animationProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        generateRays();
    }
    
    /**
     * Generate the rays for the starburst effect
     */
    private void generateRays() {
        rays.clear();
        
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float maxRadius = Math.min(centerX, centerY) * 0.8f;
        
        // Create rays in a circular pattern
        for (int i = 0; i < rayCount; i++) {
            // Random angle within this ray's segment
            float angleStep = 360f / rayCount;
            float baseAngle = i * angleStep;
            float angle = baseAngle + random.nextFloat() * (angleStep * 0.5f);
            
            // Random length
            float length = maxRadius * (0.7f + random.nextFloat() * 0.3f);
            
            // Random width
            float width = 5 + random.nextFloat() * 15;
            
            // Random color variation
            int color = adjustColor(rayColor, 0.8f + random.nextFloat() * 0.4f);
            
            // Random animation phase offset
            float phaseOffset = random.nextFloat();
            
            rays.add(new Ray(centerX, centerY, angle, length, width, color, phaseOffset));
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw each ray
        for (Ray ray : rays) {
            ray.draw(canvas, animationProgress);
        }
    }
    
    /**
     * Start the starburst animation
     */
    public void startAnimation() {
        if (!isAnimating) {
            animator.start();
            isAnimating = true;
        }
    }
    
    /**
     * Stop the starburst animation
     */
    public void stopAnimation() {
        if (isAnimating) {
            animator.cancel();
            isAnimating = false;
        }
    }
    
    /**
     * Set the number of rays in the starburst
     * @param rayCount Number of rays
     */
    public void setRayCount(int rayCount) {
        this.rayCount = rayCount;
        if (getWidth() > 0 && getHeight() > 0) {
            generateRays();
        }
    }
    
    /**
     * Set the color of the rays
     * @param rayColor Color for rays
     */
    public void setRayColor(int rayColor) {
        this.rayColor = rayColor;
        rayPaint.setColor(rayColor);
        if (getWidth() > 0 && getHeight() > 0) {
            generateRays();
        }
    }
    
    /**
     * Adjust a color's brightness
     * @param color Original color
     * @param factor Brightness factor (0.0-2.0, 1.0 means no change)
     * @return Adjusted color
     */
    private int adjustColor(int color, float factor) {
        int r = (int) (Color.red(color) * factor);
        int g = (int) (Color.green(color) * factor);
        int b = (int) (Color.blue(color) * factor);
        
        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));
        
        return Color.rgb(r, g, b);
    }
    
    @Override
    protected void onDetachedFromWindow() {
        stopAnimation();
        super.onDetachedFromWindow();
    }
    
    /**
     * Class representing a single ray in the starburst
     */
    private class Ray {
        private float centerX;
        private float centerY;
        private float angle;
        private float length;
        private float width;
        private int color;
        private float phaseOffset;
        
        public Ray(float centerX, float centerY, float angle, float length, 
                   float width, int color, float phaseOffset) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.angle = angle;
            this.length = length;
            this.width = width;
            this.color = color;
            this.phaseOffset = phaseOffset;
        }
        
        /**
         * Draw the ray on the canvas
         * @param canvas Canvas to draw on
         * @param progress Animation progress (0.0-1.0)
         */
        public void draw(Canvas canvas, float progress) {
            // Adjust progress with phase offset
            float adjustedProgress = (progress + phaseOffset) % 1.0f;
            
            // Calculate ray length based on animation
            float animatedLength = length * (0.5f + adjustedProgress * 0.5f);
            
            // Calculate ray end point
            double angleRadians = Math.toRadians(angle);
            float endX = centerX + (float) (Math.cos(angleRadians) * animatedLength);
            float endY = centerY + (float) (Math.sin(angleRadians) * animatedLength);
            
            // Calculate ray width points (perpendicular to ray)
            float widthAngleRadians = (float) Math.toRadians(angle + 90);
            float halfWidth = width / 2.0f;
            
            float x1 = centerX + (float) (Math.cos(widthAngleRadians) * halfWidth);
            float y1 = centerY + (float) (Math.sin(widthAngleRadians) * halfWidth);
            
            float x2 = centerX - (float) (Math.cos(widthAngleRadians) * halfWidth);
            float y2 = centerY - (float) (Math.sin(widthAngleRadians) * halfWidth);
            
            // Draw the ray as a triangle
            rayPath.reset();
            rayPath.moveTo(endX, endY);
            rayPath.lineTo(x1, y1);
            rayPath.lineTo(x2, y2);
            rayPath.close();
            
            // Use this ray's color
            rayPaint.setColor(color);
            
            // Apply alpha based on animation
            rayPaint.setAlpha((int) (255 * (1.0f - adjustedProgress * 0.5f)));
            
            canvas.drawPath(rayPath, rayPaint);
        }
    }
}