package com.bhashasetu.app.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.bhashasetu.app.R;

/**
 * Enhanced Toast utility for showing nicely styled toast messages with icons and animations.
 */
public class ToastUtil {

    public enum ToastType {
        INFO,
        SUCCESS,
        WARNING,
        ERROR
    }

    private static final int TOAST_DURATION_SHORT = 2000; // 2 seconds
    private static final int TOAST_DURATION_LONG = 3500;  // 3.5 seconds

    private ToastUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Show a styled toast message with an icon.
     *
     * @param context Context
     * @param message Message text
     * @param type Toast type (INFO, SUCCESS, WARNING, ERROR)
     * @param isLongDuration True for long duration, false for short
     */
    public static void showToast(Context context, String message, ToastType type, boolean isLongDuration) {
        new Handler(Looper.getMainLooper()).post(() -> {
            int iconRes;
            int colorRes;
            
            // Select icon and color based on toast type
            switch (type) {
                case SUCCESS:
                    iconRes = R.drawable.ic_success;
                    colorRes = R.color.success;
                    break;
                case WARNING:
                    iconRes = R.drawable.ic_warning;
                    colorRes = R.color.warning;
                    break;
                case ERROR:
                    iconRes = R.drawable.ic_error;
                    colorRes = R.color.error;
                    break;
                case INFO:
                default:
                    iconRes = R.drawable.ic_info;
                    colorRes = R.color.info;
                    break;
            }
            
            // Create custom toast view
            View toastView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
            ImageView iconView = toastView.findViewById(R.id.toast_icon);
            TextView textView = toastView.findViewById(R.id.toast_text);
            
            // Set icon with tint
            Drawable icon = ContextCompat.getDrawable(context, iconRes);
            if (icon != null) {
                icon.setColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN);
                iconView.setImageDrawable(icon);
            }
            
            // Set text
            textView.setText(message);
            
            // Apply animation
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
            toastView.startAnimation(animation);
            
            // Create and show toast
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.setDuration(isLongDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setView(toastView);
            
            // Use a custom duration
            int duration = isLongDuration ? TOAST_DURATION_LONG : TOAST_DURATION_SHORT;
            
            // Show toast
            toast.show();
            
            // Cancel toast after custom duration (optional if you want more control)
            new Handler().postDelayed(toast::cancel, duration);
        });
    }

    /**
     * Show a styled toast message with an icon using string resource.
     *
     * @param context Context
     * @param messageResId Message text resource ID
     * @param type Toast type (INFO, SUCCESS, WARNING, ERROR)
     * @param isLongDuration True for long duration, false for short
     */
    public static void showToast(Context context, @StringRes int messageResId, 
                                ToastType type, boolean isLongDuration) {
        showToast(context, context.getString(messageResId), type, isLongDuration);
    }
    
    /**
     * Show a custom toast with a specific icon.
     *
     * @param context Context
     * @param message Message text
     * @param iconResId Icon resource ID
     * @param colorResId Color resource ID for the icon
     * @param isLongDuration True for long duration, false for short
     */
    public static void showCustomToast(Context context, String message, 
                                     @DrawableRes int iconResId, int colorResId, 
                                     boolean isLongDuration) {
        new Handler(Looper.getMainLooper()).post(() -> {
            // Create custom toast view
            View toastView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
            ImageView iconView = toastView.findViewById(R.id.toast_icon);
            TextView textView = toastView.findViewById(R.id.toast_text);
            
            // Set icon with tint
            Drawable icon = ContextCompat.getDrawable(context, iconResId);
            if (icon != null) {
                icon.setColorFilter(ContextCompat.getColor(context, colorResId), PorterDuff.Mode.SRC_IN);
                iconView.setImageDrawable(icon);
            }
            
            // Set text
            textView.setText(message);
            
            // Apply animation
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
            toastView.startAnimation(animation);
            
            // Create and show toast
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.setDuration(isLongDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setView(toastView);
            toast.show();
        });
    }
    
    /**
     * Show an info toast.
     *
     * @param context Context
     * @param message Message text
     */
    public static void showInfo(@NonNull Context context, String message) {
        showToast(context, message, ToastType.INFO, false);
    }
    
    /**
     * Show a success toast.
     *
     * @param context Context
     * @param message Message text
     */
    public static void showSuccess(@NonNull Context context, String message) {
        showToast(context, message, ToastType.SUCCESS, false);
    }
    
    /**
     * Show a warning toast.
     *
     * @param context Context
     * @param message Message text
     */
    public static void showWarning(@NonNull Context context, String message) {
        showToast(context, message, ToastType.WARNING, true);
    }
    
    /**
     * Show an error toast.
     *
     * @param context Context
     * @param message Message text
     */
    public static void showError(@NonNull Context context, String message) {
        showToast(context, message, ToastType.ERROR, true);
    }
    
    /**
     * Show an error toast for an exception.
     *
     * @param context Context
     * @param throwable The exception
     */
    public static void showError(@NonNull Context context, @NonNull Throwable throwable) {
        String errorMessage = ErrorHandler.getErrorMessage(context, throwable);
        showToast(context, errorMessage, ToastType.ERROR, true);
    }
}