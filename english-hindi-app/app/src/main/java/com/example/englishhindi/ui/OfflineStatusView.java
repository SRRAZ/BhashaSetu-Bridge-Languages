package com.example.englishhindi.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.englishhindi.R;
import com.example.englishhindi.util.NetworkUtils;

/**
 * Custom view to display the current offline/online status.
 * Shows status indicator and provides controls for offline mode.
 */
public class OfflineStatusView extends View {
    
    private static final int ONLINE_COLOR = Color.parseColor("#4CAF50");
    private static final int OFFLINE_COLOR = Color.parseColor("#F44336");
    private static final int WIFI_ONLY_COLOR = Color.parseColor("#2196F3");
    
    private Paint backgroundPaint;
    private Paint textPaint;
    private Paint iconBackgroundPaint;
    
    private RectF backgroundRect;
    private RectF iconRect;
    
    private Drawable onlineIcon;
    private Drawable offlineIcon;
    private Drawable wifiIcon;
    
    private String statusText;
    private int statusColor;
    
    private NetworkUtils networkUtils;
    private boolean isAnimating = false;
    
    public OfflineStatusView(Context context) {
        super(context);
        init(null);
    }
    
    public OfflineStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    
    public OfflineStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    
    private void init(@Nullable AttributeSet attrs) {
        // Initialize NetworkUtils
        networkUtils = new NetworkUtils(getContext());
        
        // Initialize paints
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
        
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(spToPx(14));
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        
        iconBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iconBackgroundPaint.setStyle(Paint.Style.FILL);
        iconBackgroundPaint.setColor(Color.WHITE);
        
        // Initialize rects
        backgroundRect = new RectF();
        iconRect = new RectF();
        
        // Load drawables
        onlineIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_online);
        offlineIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_offline);
        wifiIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_wifi);
        
        // Set initial status
        updateStatus();
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        // Set background rect
        backgroundRect.set(0, 0, w, h);
        
        // Set icon rect
        float iconSize = h * 0.6f;
        float iconMargin = (h - iconSize) / 2;
        iconRect.set(iconMargin, iconMargin, iconMargin + iconSize, iconMargin + iconSize);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw background
        backgroundPaint.setColor(statusColor);
        canvas.drawRoundRect(backgroundRect, backgroundRect.height() / 2, 
                backgroundRect.height() / 2, backgroundPaint);
        
        // Draw icon background
        canvas.drawCircle(iconRect.centerX(), iconRect.centerY(), iconRect.width() / 2, 
                iconBackgroundPaint);
        
        // Draw appropriate icon
        Drawable icon;
        if (networkUtils.isOfflineModeEnabled()) {
            icon = offlineIcon;
        } else if (networkUtils.isWifiOnlyModeEnabled() && networkUtils.isWifiConnected()) {
            icon = wifiIcon;
        } else if (networkUtils.isNetworkAvailable()) {
            icon = onlineIcon;
        } else {
            icon = offlineIcon;
        }
        
        if (icon != null) {
            icon.setBounds((int) iconRect.left, (int) iconRect.top, 
                    (int) iconRect.right, (int) iconRect.bottom);
            icon.draw(canvas);
        }
        
        // Draw status text
        float textX = iconRect.right + dpToPx(12);
        float textY = backgroundRect.centerY() - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(statusText, textX, textY, textPaint);
    }
    
    /**
     * Update the status display based on current network state
     */
    public void updateStatus() {
        if (networkUtils.isOfflineModeEnabled()) {
            statusText = getContext().getString(R.string.offline_mode);
            statusColor = OFFLINE_COLOR;
        } else if (networkUtils.isWifiOnlyModeEnabled()) {
            if (networkUtils.isWifiConnected()) {
                statusText = getContext().getString(R.string.wifi_connected);
                statusColor = WIFI_ONLY_COLOR;
            } else if (networkUtils.isCellularConnected()) {
                statusText = getContext().getString(R.string.waiting_for_wifi);
                statusColor = OFFLINE_COLOR;
            } else {
                statusText = getContext().getString(R.string.no_connection);
                statusColor = OFFLINE_COLOR;
            }
        } else if (networkUtils.isNetworkAvailable()) {
            statusText = getContext().getString(R.string.online);
            statusColor = ONLINE_COLOR;
        } else {
            statusText = getContext().getString(R.string.no_connection);
            statusColor = OFFLINE_COLOR;
        }
        
        invalidate();
    }
    
    /**
     * Toggle offline mode
     * @return New offline mode state
     */
    public boolean toggleOfflineMode() {
        boolean newState = !networkUtils.isOfflineModeEnabled();
        networkUtils.setOfflineModeEnabled(newState);
        updateStatus();
        animateStatusChange();
        return newState;
    }
    
    /**
     * Toggle WiFi-only mode
     * @return New WiFi-only mode state
     */
    public boolean toggleWifiOnlyMode() {
        boolean newState = !networkUtils.isWifiOnlyModeEnabled();
        networkUtils.setWifiOnlyModeEnabled(newState);
        updateStatus();
        animateStatusChange();
        return newState;
    }
    
    /**
     * Animate the status change
     */
    private void animateStatusChange() {
        if (isAnimating) {
            return;
        }
        
        isAnimating = true;
        
        Animation pulseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
        pulseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating = false;
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        
        startAnimation(pulseAnimation);
    }
    
    /**
     * Convert SP to pixels
     * @param sp Value in SP
     * @return Value in pixels
     */
    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, 
                getResources().getDisplayMetrics());
    }
    
    /**
     * Convert DP to pixels
     * @param dp Value in DP
     * @return Value in pixels
     */
    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, 
                getResources().getDisplayMetrics());
    }
}