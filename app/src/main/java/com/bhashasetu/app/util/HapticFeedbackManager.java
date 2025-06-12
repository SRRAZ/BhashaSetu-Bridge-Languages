package com.bhashasetu.app.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Utility class to manage haptic feedback throughout the app.
 * Provides methods for different types of haptic feedback based on actions.
 */
public class HapticFeedbackManager {

    private static final Object LOCK = new Object();
    private static HapticFeedbackManager instance;

    private final Context context;
    private final Vibrator vibrator;
    private boolean hapticFeedbackEnabled = true;

    private HapticFeedbackManager(Context context) {
        this.context = context.getApplicationContext();
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static HapticFeedbackManager getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new HapticFeedbackManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * Set whether haptic feedback is enabled.
     *
     * @param enabled True to enable, false to disable
     */
    public void setHapticFeedbackEnabled(boolean enabled) {
        this.hapticFeedbackEnabled = enabled;
    }

    /**
     * Check if haptic feedback is enabled.
     *
     * @return True if enabled, false otherwise
     */
    public boolean isHapticFeedbackEnabled() {
        return hapticFeedbackEnabled;
    }

    /**
     * Perform a light click feedback.
     * Use for regular button clicks, selection changes, etc.
     */
    public void performLightClick() {
        if (!hapticFeedbackEnabled || vibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(20);
        }
    }

    /**
     * Perform a medium click feedback.
     * Use for confirming selections, submitting forms, etc.
     */
    public void performMediumClick() {
        if (!hapticFeedbackEnabled || vibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(40);
        }
    }

    /**
     * Perform a heavy click feedback.
     * Use for important actions, errors, etc.
     */
    public void performHeavyClick() {
        if (!hapticFeedbackEnabled || vibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(60);
        }
    }

    /**
     * Perform a double click feedback.
     * Use for double tap actions.
     */
    public void performDoubleClick() {
        if (!hapticFeedbackEnabled || vibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            long[] pattern = {0, 30, 100, 30};
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
        } else {
            long[] pattern = {0, 30, 100, 30};
            vibrator.vibrate(pattern, -1);
        }
    }

    /**
     * Perform an error feedback.
     * Use when user makes a mistake, invalid inputs, etc.
     */
    public void performError() {
        if (!hapticFeedbackEnabled || vibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            long[] pattern = {0, 50, 100, 30, 100, 50};
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
        } else {
            long[] pattern = {0, 50, 100, 30, 100, 50};
            vibrator.vibrate(pattern, -1);
        }
    }

    /**
     * Perform a success feedback.
     * Use when a task is successfully completed.
     */
    public void performSuccess() {
        if (!hapticFeedbackEnabled || vibrator == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            long[] pattern = {0, 30, 80, 30, 80, 30};
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
        } else {
            long[] pattern = {0, 30, 80, 30, 80, 30};
            vibrator.vibrate(pattern, -1);
        }
    }

    /**
     * Perform a view-based haptic feedback.
     * This uses Android's built-in haptic feedback constants.
     *
     * @param view The view to perform feedback on
     * @param feedbackConstant The feedback constant (from HapticFeedbackConstants)
     */
    public void performViewFeedback(@NonNull View view, int feedbackConstant) {
        if (!hapticFeedbackEnabled) return;

        // Enable feedback temporarily regardless of system settings
        view.setHapticFeedbackEnabled(true);
        view.performHapticFeedback(feedbackConstant, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
    }

    /**
     * Perform a long press feedback on a view.
     *
     * @param view The view to perform feedback on
     */
    public void performLongPress(@NonNull View view) {
        performViewFeedback(view, HapticFeedbackConstants.LONG_PRESS);
    }

    /**
     * Perform virtual key feedback on a view.
     *
     * @param view The view to perform feedback on
     */
    public void performVirtualKey(@NonNull View view) {
        performViewFeedback(view, HapticFeedbackConstants.VIRTUAL_KEY);
    }

    /**
     * Perform keyboard tap feedback on a view.
     *
     * @param view The view to perform feedback on
     */
    public void performKeyboardTap(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            performViewFeedback(view, HapticFeedbackConstants.KEYBOARD_TAP);
        } else {
            performViewFeedback(view, HapticFeedbackConstants.VIRTUAL_KEY);
        }
    }

    /**
     * Cancel ongoing vibration.
     */
    public void cancel() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}