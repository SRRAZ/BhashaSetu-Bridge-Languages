package com.bhashasetu.app.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bhashasetu.app.R;

/**
 * Custom progress animation view that shows a circular progress indicator
 * with smooth animations and customizable colors.
 */
public class ProgressAnimationView extends View {

    private static final int DEFAULT_STROKE_WIDTH_DP = 8;
    private static final int DEFAULT_ANIMATION_DURATION_MS = 1000;
    private static final int DEFAULT_COLOR = Color.BLUE;
    private static final float MAX_ANGLE = 360f;

    private final Paint progressPaint;
    private final RectF bounds = new RectF();
    private float currentProgress = 0f;
    private float targetProgress = 0f;
    private ValueAnimator progressAnimator;
    private int progressColor;
    private int backgroundColor;
    private float strokeWidth;
    private boolean indeterminate = false;
    private float startAngle = 0f;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private long animationDurationMs = DEFAULT_ANIMATION_DURATION_MS;

    public ProgressAnimationView(Context context) {
        this(context, null);
    }

    public ProgressAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        
        // Convert default stroke width from dp to pixels
        float density = context.getResources().getDisplayMetrics().density;
        strokeWidth = DEFAULT_STROKE_WIDTH_DP * density;
        
        // Default colors
        progressColor = ContextCompat.getColor(context, R.color.colorPrimary);
        backgroundColor = ContextCompat.getColor(context, R.color.colorDivider);
        
        // Get custom attributes if available
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressAnimationView);
            
            progressColor = a.getColor(R.styleable.ProgressAnimationView_progressColor, progressColor);
            backgroundColor = a.getColor(R.styleable.ProgressAnimationView_backgroundColor, backgroundColor);
            strokeWidth = a.getDimension(R.styleable.ProgressAnimationView_strokeWidth, strokeWidth);
            indeterminate = a.getBoolean(R.styleable.ProgressAnimationView_indeterminate, indeterminate);
            animationDurationMs = a.getInt(R.styleable.ProgressAnimationView_animationDuration, 
                                         (int) animationDurationMs);
            
            a.recycle();
        }
        
        progressPaint.setStrokeWidth(strokeWidth);
        
        if (indeterminate) {
            startIndeterminateAnimation();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateBounds();
    }

    private void updateBounds() {
        float halfStrokeWidth = strokeWidth / 2f;
        bounds.set(halfStrokeWidth, halfStrokeWidth, 
                  getWidth() - halfStrokeWidth, 
                  getHeight() - halfStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw background circle
        progressPaint.setColor(backgroundColor);
        canvas.drawArc(bounds, 0, MAX_ANGLE, false, progressPaint);
        
        // Draw progress arc
        progressPaint.setColor(progressColor);
        float sweepAngle = indeterminate ? 120f : currentProgress * MAX_ANGLE;
        canvas.drawArc(bounds, startAngle, sweepAngle, false, progressPaint);
    }

    /**
     * Set progress value (0.0 - 1.0) with animation.
     *
     * @param progress Value between 0 and 1
     */
    public void setProgress(float progress) {
        // Constrain progress to 0-1 range
        progress = Math.max(0f, Math.min(1f, progress));
        
        if (indeterminate) {
            // Cancel indeterminate animation and switch to determinate mode
            stopIndeterminateAnimation();
            indeterminate = false;
        }
        
        targetProgress = progress;
        
        // Cancel any ongoing animation
        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
        
        // Create and start new animation
        progressAnimator = ValueAnimator.ofFloat(currentProgress, targetProgress);
        progressAnimator.setDuration(animationDurationMs);
        progressAnimator.setInterpolator(interpolator);
        progressAnimator.addUpdateListener(animation -> {
            currentProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        progressAnimator.start();
    }

    /**
     * Set progress immediately without animation.
     *
     * @param progress Value between 0 and 1
     */
    public void setProgressImmediate(float progress) {
        // Constrain progress to 0-1 range
        progress = Math.max(0f, Math.min(1f, progress));
        
        if (indeterminate) {
            // Cancel indeterminate animation and switch to determinate mode
            stopIndeterminateAnimation();
            indeterminate = false;
        }
        
        currentProgress = progress;
        targetProgress = progress;
        invalidate();
    }

    /**
     * Get current progress value.
     *
     * @return Current progress (0-1)
     */
    public float getProgress() {
        return currentProgress;
    }

    /**
     * Set the progress color.
     *
     * @param color Color resource or constant
     */
    public void setProgressColor(int color) {
        this.progressColor = color;
        invalidate();
    }

    /**
     * Set the background circle color.
     *
     * @param color Color resource or constant
     */
    public void setBackgroundCircleColor(int color) {
        this.backgroundColor = color;
        invalidate();
    }

    /**
     * Set the stroke width.
     *
     * @param strokeWidthPx Stroke width in pixels
     */
    public void setStrokeWidth(float strokeWidthPx) {
        this.strokeWidth = strokeWidthPx;
        progressPaint.setStrokeWidth(strokeWidth);
        updateBounds();
        invalidate();
    }

    /**
     * Set animation duration for progress changes.
     *
     * @param durationMs Duration in milliseconds
     */
    public void setAnimationDuration(long durationMs) {
        this.animationDurationMs = durationMs;
    }

    /**
     * Set animation interpolator for smooth progress changes.
     *
     * @param interpolator Animation interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    /**
     * Switch to indeterminate mode with continuous animation.
     */
    public void setIndeterminate(boolean indeterminate) {
        if (this.indeterminate == indeterminate) {
            return;
        }
        
        this.indeterminate = indeterminate;
        
        if (indeterminate) {
            startIndeterminateAnimation();
        } else {
            stopIndeterminateAnimation();
            currentProgress = 0f;
            invalidate();
        }
    }

    /**
     * Start the indeterminate animation.
     */
    private void startIndeterminateAnimation() {
        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
        
        // Create and start rotation animation
        progressAnimator = ValueAnimator.ofFloat(0f, MAX_ANGLE);
        progressAnimator.setDuration(animationDurationMs);
        progressAnimator.setInterpolator(interpolator);
        progressAnimator.setRepeatCount(ValueAnimator.INFINITE);
        progressAnimator.setRepeatMode(ValueAnimator.RESTART);
        progressAnimator.addUpdateListener(animation -> {
            startAngle = (float) animation.getAnimatedValue();
            invalidate();
        });
        progressAnimator.start();
    }

    /**
     * Stop the indeterminate animation.
     */
    private void stopIndeterminateAnimation() {
        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
        startAngle = 0f;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Clean up animator to prevent memory leaks
        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
    }
}