package com.bhashasetu.app.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bhashasetu.app.R;
import com.bhashasetu.app.util.NetworkUtils;

/**
 * Custom view that shows the offline status and allows toggling offline mode
 * with a simple tap. Provides visual feedback about the current network state.
 */
public class OfflineStatusView extends View implements NetworkUtils.NetworkStateListener {
    
    private static final int STATE_ONLINE = 0;
    private static final int STATE_OFFLINE = 1;
    private static final int STATE_ONLINE_WIFI = 2;
    private static final int STATE_ONLINE_CELLULAR = 3;
    private static final int STATE_METERED = 4;
    
    private Paint textPaint;
    private Paint backgroundPaint;
    private Paint iconBackgroundPaint;
    
    private Drawable onlineIcon;
    private Drawable offlineIcon;
    private Drawable wifiIcon;
    private Drawable cellularIcon;
    private Drawable meteredIcon;
    
    private NetworkUtils networkUtils;
    private int currentState = STATE_ONLINE;
    private boolean isPressed = false;

    private int textColor;
    private int backgroundColor;
    private int iconBackgroundColor;
    private int iconSize;
    private int iconPadding;
    private float cornerRadius;
    private String onlineText;
    private String offlineText;
    private String wifiText;
    private String cellularText;
    private String meteredText;
    
    private OnClickListener clickListener;
    
    public OfflineStatusView(Context context) {
        super(context);
        init(context, null);
    }
    
    public OfflineStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public OfflineStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs) {
        // Default values
        textColor = Color.WHITE;
        backgroundColor = 0xFF4CAF50; // Green
        iconBackgroundColor = 0xFF388E3C; // Darker green
        iconSize = dpToPx(24);
        iconPadding = dpToPx(8);
        cornerRadius = dpToPx(4);
        onlineText = "Online";
        offlineText = "Offline";
        wifiText = "WiFi";
        cellularText = "Cellular";
        meteredText = "Metered";
        
        // Load attributes from XML if available
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OfflineStatusView);
            
            textColor = a.getColor(R.styleable.OfflineStatusView_textColor, textColor);
            backgroundColor = a.getColor(R.styleable.OfflineStatusView_backgroundColor, backgroundColor);
            iconBackgroundColor = a.getColor(R.styleable.OfflineStatusView_iconBackgroundColor, iconBackgroundColor);
            iconSize = a.getDimensionPixelSize(R.styleable.OfflineStatusView_iconSize, iconSize);
            iconPadding = a.getDimensionPixelSize(R.styleable.OfflineStatusView_iconPadding, iconPadding);
            cornerRadius = a.getDimension(R.styleable.OfflineStatusView_cornerRadius, cornerRadius);
            
            if (a.hasValue(R.styleable.OfflineStatusView_onlineText)) {
                onlineText = a.getString(R.styleable.OfflineStatusView_onlineText);
            }
            if (a.hasValue(R.styleable.OfflineStatusView_offlineText)) {
                offlineText = a.getString(R.styleable.OfflineStatusView_offlineText);
            }
            if (a.hasValue(R.styleable.OfflineStatusView_wifiText)) {
                wifiText = a.getString(R.styleable.OfflineStatusView_wifiText);
            }
            if (a.hasValue(R.styleable.OfflineStatusView_cellularText)) {
                cellularText = a.getString(R.styleable.OfflineStatusView_cellularText);
            }
            if (a.hasValue(R.styleable.OfflineStatusView_meteredText)) {
                meteredText = a.getString(R.styleable.OfflineStatusView_meteredText);
            }
            
            a.recycle();
        }
        
        // Initialize paints
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(dpToPx(14));
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);
        
        backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setAntiAlias(true);
        
        iconBackgroundPaint = new Paint();
        iconBackgroundPaint.setColor(iconBackgroundColor);
        iconBackgroundPaint.setStyle(Paint.Style.FILL);
        iconBackgroundPaint.setAntiAlias(true);
        
        // Load icons
        onlineIcon = ContextCompat.getDrawable(context, R.drawable.ic_online);
        offlineIcon = ContextCompat.getDrawable(context, R.drawable.ic_offline);
        wifiIcon = ContextCompat.getDrawable(context, R.drawable.ic_wifi);
        cellularIcon = ContextCompat.getDrawable(context, R.drawable.ic_cellular);
        meteredIcon = ContextCompat.getDrawable(context, R.drawable.ic_data_usage);
        
        // Get the NetworkUtils instance
        networkUtils = NetworkUtils.getInstance(context);
        networkUtils.addNetworkStateListener(this);
        
        // Set initial state
        updateStatus();
    }
    
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (networkUtils != null) {
            networkUtils.addNetworkStateListener(this);
        }
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (networkUtils != null) {
            networkUtils.removeNetworkStateListener(this);
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = calculateDesiredWidth();
        int desiredHeight = dpToPx(40); // Fixed height
        
        int width = resolveSize(desiredWidth, widthMeasureSpec);
        int height = resolveSize(desiredHeight, heightMeasureSpec);
        
        setMeasuredDimension(width, height);
    }
    
    private int calculateDesiredWidth() {
        // Calculate width based on the text and icon
        String text = getTextForCurrentState();
        float textWidth = textPaint.measureText(text);
        return (int) (iconSize + textWidth + iconPadding * 4);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        int width = getWidth();
        int height = getHeight();
        
        // Draw background
        int bgColor = isPressed ? darkenColor(backgroundColor) : backgroundColor;
        backgroundPaint.setColor(bgColor);
        canvas.drawRoundRect(0, 0, width, height, cornerRadius, cornerRadius, backgroundPaint);
        
        // Draw icon background
        int iconBgColor = isPressed ? darkenColor(iconBackgroundColor) : iconBackgroundColor;
        iconBackgroundPaint.setColor(iconBgColor);
        canvas.drawRoundRect(0, 0, iconSize + iconPadding * 2, height, cornerRadius, cornerRadius, iconBackgroundPaint);
        
        // Draw icon
        Drawable icon = getIconForCurrentState();
        if (icon != null) {
            int iconLeft = iconPadding;
            int iconTop = (height - iconSize) / 2;
            icon.setBounds(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize);
            icon.draw(canvas);
        }
        
        // Draw text
        String text = getTextForCurrentState();
        float textX = iconSize + iconPadding * 3;
        float textY = (height / 2) - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(text, textX, textY, textPaint);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                isPressed = false;
                performClick();
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
                isPressed = false;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }
    
    @Override
    public boolean performClick() {
        boolean result = super.performClick();
        
        // Toggle offline mode if clicked
        toggleOfflineMode();
        
        // Call any additional click listener
        if (clickListener != null) {
            clickListener.onClick(this);
        }
        
        return result;
    }
    
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        // Store the listener separately so we can call it after our internal handling
        this.clickListener = l;
        // Always set the internal listener to ensure we handle the click
        super.setOnClickListener(v -> {
            // Internal handling is done in performClick()
        });
    }
    
    /**
     * Update the view based on current network status
     */
    public void updateStatus() {
        if (networkUtils == null) {
            return;
        }
        
        if (networkUtils.isOfflineModeEnabled()) {
            currentState = STATE_OFFLINE;
            backgroundPaint.setColor(Color.parseColor("#F44336")); // Red
            iconBackgroundPaint.setColor(Color.parseColor("#D32F2F")); // Dark red
        } else if (networkUtils.isNetworkAvailable()) {
            if (networkUtils.isWifiConnected()) {
                currentState = STATE_ONLINE_WIFI;
                backgroundPaint.setColor(Color.parseColor("#4CAF50")); // Green
                iconBackgroundPaint.setColor(Color.parseColor("#388E3C")); // Dark green
            } else if (networkUtils.isCellularConnected() && networkUtils.isMeteredConnection()) {
                currentState = STATE_METERED;
                backgroundPaint.setColor(Color.parseColor("#FF9800")); // Orange
                iconBackgroundPaint.setColor(Color.parseColor("#F57C00")); // Dark orange
            } else if (networkUtils.isCellularConnected()) {
                currentState = STATE_ONLINE_CELLULAR;
                backgroundPaint.setColor(Color.parseColor("#2196F3")); // Blue
                iconBackgroundPaint.setColor(Color.parseColor("#1976D2")); // Dark blue
            } else {
                currentState = STATE_ONLINE;
                backgroundPaint.setColor(Color.parseColor("#4CAF50")); // Green
                iconBackgroundPaint.setColor(Color.parseColor("#388E3C")); // Dark green
            }
        } else {
            currentState = STATE_OFFLINE;
            backgroundPaint.setColor(Color.parseColor("#F44336")); // Red
            iconBackgroundPaint.setColor(Color.parseColor("#D32F2F")); // Dark red
        }
        
        invalidate();
        requestLayout(); // Since the text might change width
    }
    
    /**
     * Toggle the offline mode
     * @return new offline mode state
     */
    public boolean toggleOfflineMode() {
        if (networkUtils != null) {
            boolean newState = networkUtils.toggleOfflineMode();
            updateStatus();
            return newState;
        }
        return false;
    }
    
    /**
     * Get the appropriate icon for the current state
     * @return Drawable for the current state
     */
    private Drawable getIconForCurrentState() {
        switch (currentState) {
            case STATE_OFFLINE:
                return offlineIcon;
            case STATE_ONLINE_WIFI:
                return wifiIcon;
            case STATE_ONLINE_CELLULAR:
                return cellularIcon;
            case STATE_METERED:
                return meteredIcon;
            case STATE_ONLINE:
            default:
                return onlineIcon;
        }
    }
    
    /**
     * Get the appropriate text for the current state
     * @return Text for the current state
     */
    private String getTextForCurrentState() {
        switch (currentState) {
            case STATE_OFFLINE:
                return offlineText;
            case STATE_ONLINE_WIFI:
                return wifiText;
            case STATE_ONLINE_CELLULAR:
                return cellularText;
            case STATE_METERED:
                return meteredText;
            case STATE_ONLINE:
            default:
                return onlineText;
        }
    }
    
    /**
     * Helper method to convert dp to pixels
     * @param dp DP value
     * @return Pixel value
     */
    private int dpToPx(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
    
    /**
     * Darken a color for the pressed state
     * @param color Original color
     * @return Darkened color
     */
    private int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // Reduce brightness to 80%
        return Color.HSVToColor(hsv);
    }
    
    @Override
    public void onNetworkStateChanged(boolean isConnected, boolean isWifi, boolean isMetered, int connectionQuality) {
        // Update the view when network state changes
        updateStatus();
    }
}