package com.example.englishhindi.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.englishhindi.R;
import com.example.englishhindi.model.DifficultyManager;

/**
 * Custom view for tracking and displaying user XP and level progress.
 * Features animated progress and level-up effects.
 */
public class XpTrackerView extends View {

    private static final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#4CAF50");
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#E0E0E0");
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#212121");
    private static final int DEFAULT_LEVEL_BACKGROUND_COLOR = Color.parseColor("#2196F3");
    
    private Paint progressPaint;
    private Paint backgroundPaint;
    private Paint textPaint;
    private Paint levelBackgroundPaint;
    private Paint levelTextPaint;
    
    private RectF progressRect;
    private RectF backgroundRect;
    private RectF levelCircleRect;
    
    private int currentXp = 0;
    private int targetXp = 0;
    private int animatedXp = 0;
    private int level = 1;
    private int nextLevelXp = 500;
    
    private boolean isAnimating = false;
    private ValueAnimator progressAnimator;
    private ValueAnimator levelUpAnimator;
    private float levelUpScale = 1.0f;
    
    private OnLevelUpListener onLevelUpListener;

    public XpTrackerView(Context context) {
        super(context);
        init(null);
    }

    public XpTrackerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public XpTrackerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    
    private void init(@Nullable AttributeSet attrs) {
        // Initialize default values
        int progressColor = DEFAULT_PROGRESS_COLOR;
        int backgroundColor = DEFAULT_BACKGROUND_COLOR;
        int textColor = DEFAULT_TEXT_COLOR;
        int levelBackgroundColor = DEFAULT_LEVEL_BACKGROUND_COLOR;
        
        // Read attributes if available
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.XpTrackerView);
            progressColor = a.getColor(R.styleable.XpTrackerView_progressColor, DEFAULT_PROGRESS_COLOR);
            backgroundColor = a.getColor(R.styleable.XpTrackerView_backgroundColor, DEFAULT_BACKGROUND_COLOR);
            textColor = a.getColor(R.styleable.XpTrackerView_textColor, DEFAULT_TEXT_COLOR);
            levelBackgroundColor = a.getColor(R.styleable.XpTrackerView_levelBackgroundColor, DEFAULT_LEVEL_BACKGROUND_COLOR);
            a.recycle();
        }
        
        // Initialize paints
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.FILL);
        
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);
        
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(36f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        
        levelBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        levelBackgroundPaint.setColor(levelBackgroundColor);
        levelBackgroundPaint.setStyle(Paint.Style.FILL);
        
        levelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        levelTextPaint.setColor(Color.WHITE);
        levelTextPaint.setTextSize(40f);
        levelTextPaint.setTextAlign(Paint.Align.CENTER);
        levelTextPaint.setFakeBoldText(true);
        
        // Initialize geometry objects
        progressRect = new RectF();
        backgroundRect = new RectF();
        levelCircleRect = new RectF();
        
        // Initialize animators
        progressAnimator = ValueAnimator.ofInt(0, 0);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.addUpdateListener(animation -> {
            animatedXp = (int) animation.getAnimatedValue();
            invalidate();
        });
        
        levelUpAnimator = ValueAnimator.ofFloat(1.0f, 1.5f, 1.0f);
        levelUpAnimator.setDuration(500);
        levelUpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        levelUpAnimator.addUpdateListener(animation -> {
            levelUpScale = (float) animation.getAnimatedValue();
            invalidate();
        });
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        int levelCircleSize = h;
        int progressBarHeight = h / 3;
        int progressBarTop = (h - progressBarHeight) / 2;
        
        // Set up level circle on the left
        levelCircleRect.set(0, 0, levelCircleSize, levelCircleSize);
        
        // Set up progress bar to the right of the level circle
        backgroundRect.set(levelCircleSize + dpToPx(8), progressBarTop, 
                           w, progressBarTop + progressBarHeight);
        progressRect.set(backgroundRect);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw level circle with current scale (for animation)
        float cx = levelCircleRect.centerX();
        float cy = levelCircleRect.centerY();
        float radius = levelCircleRect.width() / 2 * levelUpScale;
        canvas.drawCircle(cx, cy, radius, levelBackgroundPaint);
        
        // Draw level text
        float textY = cy - ((levelTextPaint.descent() + levelTextPaint.ascent()) / 2);
        canvas.drawText(String.valueOf(level), cx, textY, levelTextPaint);
        
        // Draw progress bar background
        canvas.drawRoundRect(backgroundRect, backgroundRect.height() / 2, 
                            backgroundRect.height() / 2, backgroundPaint);
        
        // Calculate and draw progress portion
        float progress = (animatedXp - DifficultyManager.LEVEL_XP_THRESHOLDS[level - 1]) / 
                        (float)(nextLevelXp - DifficultyManager.LEVEL_XP_THRESHOLDS[level - 1]);
        progress = Math.max(0, Math.min(1, progress));
        
        progressRect.right = backgroundRect.left + (backgroundRect.width() * progress);
        canvas.drawRoundRect(progressRect, progressRect.height() / 2, 
                            progressRect.height() / 2, progressPaint);
        
        // Draw XP text
        String xpText = animatedXp + " / " + nextLevelXp + " XP";
        float xpTextX = backgroundRect.centerX();
        float xpTextY = backgroundRect.centerY() - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(xpText, xpTextX, xpTextY, textPaint);
    }
    
    /**
     * Set the current XP and level
     * @param xp Current XP
     * @param level Current level
     * @param animate Whether to animate the change
     */
    public void setXpAndLevel(int xp, int level, boolean animate) {
        this.level = level;
        this.currentXp = xp;
        this.targetXp = xp;
        
        // Determine XP required for next level
        if (level < 5) {
            this.nextLevelXp = DifficultyManager.LEVEL_XP_THRESHOLDS[level];
        } else {
            // Max level reached, show progress as 100%
            this.nextLevelXp = xp;
        }
        
        if (animate && animatedXp != xp) {
            // Animate from current displayed XP to new value
            progressAnimator.cancel();
            progressAnimator.setIntValues(animatedXp, xp);
            progressAnimator.start();
        } else {
            // No animation, just set the value
            animatedXp = xp;
            invalidate();
        }
    }
    
    /**
     * Add XP with animation and handle level-up
     * @param xpToAdd Amount of XP to add
     * @return true if leveled up
     */
    public boolean addXp(int xpToAdd) {
        if (xpToAdd <= 0) {
            return false;
        }
        
        int oldLevel = DifficultyManager.getLevelForXp(currentXp);
        currentXp += xpToAdd;
        targetXp = currentXp;
        int newLevel = DifficultyManager.getLevelForXp(currentXp);
        
        boolean leveledUp = newLevel > oldLevel;
        
        if (leveledUp) {
            // Level up occurred
            level = newLevel;
            
            // Determine XP for next level
            if (level < 5) {
                nextLevelXp = DifficultyManager.LEVEL_XP_THRESHOLDS[level];
            } else {
                // Max level reached
                nextLevelXp = currentXp;
            }
            
            // First animate to 100% of previous level
            progressAnimator.cancel();
            progressAnimator.setIntValues(animatedXp, DifficultyManager.LEVEL_XP_THRESHOLDS[oldLevel - 1]);
            progressAnimator.setDuration(500);
            progressAnimator.start();
            
            // Then animate level up and update XP
            levelUpAnimator.start();
            levelUpAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    // After level up animation, show progress in new level
                    progressAnimator.setIntValues(DifficultyManager.LEVEL_XP_THRESHOLDS[oldLevel - 1], currentXp);
                    progressAnimator.setDuration(500);
                    progressAnimator.start();
                    
                    // Notify listener
                    if (onLevelUpListener != null) {
                        onLevelUpListener.onLevelUp(oldLevel, newLevel);
                    }
                }
            });
        } else {
            // No level up, just animate XP increase
            progressAnimator.cancel();
            progressAnimator.setIntValues(animatedXp, currentXp);
            progressAnimator.setDuration(1000);
            progressAnimator.start();
        }
        
        return leveledUp;
    }
    
    /**
     * Set listener for level up events
     * @param listener The listener to set
     */
    public void setOnLevelUpListener(OnLevelUpListener listener) {
        this.onLevelUpListener = listener;
    }
    
    /**
     * Utility method to convert dp to pixels
     */
    private int dpToPx(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
    
    /**
     * Interface for level up event callbacks
     */
    public interface OnLevelUpListener {
        void onLevelUp(int oldLevel, int newLevel);
    }
}