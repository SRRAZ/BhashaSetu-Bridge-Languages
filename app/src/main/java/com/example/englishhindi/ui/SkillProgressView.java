package com.example.englishhindi.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.englishhindi.R;

/**
 * Custom view for displaying skill progress with animated circular progress bars.
 */
public class SkillProgressView extends View {

    private static final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#4CAF50");
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#E0E0E0");
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#212121");
    
    private Paint progressPaint;
    private Paint backgroundPaint;
    private Paint textPaint;
    private Paint skillNamePaint;
    
    private RectF circleRect;
    private float centerX;
    private float centerY;
    private float radius;
    
    private int progress = 0;
    private int animatedProgress = 0;
    private String skillName = "";
    private int strokeWidth = 20;
    
    private ValueAnimator progressAnimator;

    public SkillProgressView(Context context) {
        super(context);
        init(null);
    }

    public SkillProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SkillProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    
    private void init(@Nullable AttributeSet attrs) {
        // Initialize default values
        int progressColor = DEFAULT_PROGRESS_COLOR;
        int backgroundColor = DEFAULT_BACKGROUND_COLOR;
        int textColor = DEFAULT_TEXT_COLOR;
        
        // Read attributes if available
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SkillProgressView);
            progressColor = a.getColor(R.styleable.SkillProgressView_skillProgressColor, DEFAULT_PROGRESS_COLOR);
            backgroundColor = a.getColor(R.styleable.SkillProgressView_skillBackgroundColor, DEFAULT_BACKGROUND_COLOR);
            textColor = a.getColor(R.styleable.SkillProgressView_skillTextColor, DEFAULT_TEXT_COLOR);
            skillName = a.getString(R.styleable.SkillProgressView_skillName);
            strokeWidth = a.getDimensionPixelSize(R.styleable.SkillProgressView_strokeWidth, strokeWidth);
            a.recycle();
        }
        
        if (skillName == null) {
            skillName = "";
        }
        
        // Initialize paints
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(40f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        
        skillNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        skillNamePaint.setColor(textColor);
        skillNamePaint.setTextSize(32f);
        skillNamePaint.setTextAlign(Paint.Align.CENTER);
        skillNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        
        // Initialize geometry objects
        circleRect = new RectF();
        
        // Initialize animator
        progressAnimator = ValueAnimator.ofInt(0, 0);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.addUpdateListener(animation -> {
            animatedProgress = (int) animation.getAnimatedValue();
            invalidate();
        });
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        // Calculate dimensions for the circle
        centerX = w / 2f;
        centerY = h / 2f;
        
        // Leave room for the stroke width
        radius = Math.min(w, h) / 2f - strokeWidth;
        
        // Set up circle rectangle
        circleRect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);
        
        // Draw progress arc
        float sweepAngle = (animatedProgress / 100f) * 360f;
        canvas.drawArc(circleRect, -90, sweepAngle, false, progressPaint);
        
        // Draw progress text
        String progressText = animatedProgress + "%";
        float textY = centerY - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(progressText, centerX, textY, textPaint);
        
        // Draw skill name below progress
        float skillNameY = centerY + radius + strokeWidth + 40;
        canvas.drawText(skillName, centerX, skillNameY, skillNamePaint);
    }
    
    /**
     * Set the progress percentage with animation
     * @param progress Progress percentage (0-100)
     */
    public void setProgress(int progress) {
        this.progress = Math.max(0, Math.min(100, progress));
        
        // Animate from current progress to new value
        progressAnimator.cancel();
        progressAnimator.setIntValues(animatedProgress, this.progress);
        progressAnimator.start();
    }
    
    /**
     * Set the progress percentage without animation
     * @param progress Progress percentage (0-100)
     */
    public void setProgressWithoutAnimation(int progress) {
        this.progress = Math.max(0, Math.min(100, progress));
        this.animatedProgress = this.progress;
        invalidate();
    }
    
    /**
     * Set the skill name
     * @param skillName Name of the skill
     */
    public void setSkillName(String skillName) {
        this.skillName = skillName != null ? skillName : "";
        invalidate();
    }
    
    /**
     * Set the progress color
     * @param color Color value
     */
    public void setProgressColor(int color) {
        progressPaint.setColor(color);
        invalidate();
    }
    
    /**
     * Get the current progress percentage
     * @return Progress (0-100)
     */
    public int getProgress() {
        return progress;
    }
}