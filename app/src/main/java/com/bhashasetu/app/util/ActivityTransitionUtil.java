package com.bhashasetu.app.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.bhashasetu.app.R;

/**
 * Utility class for handling activity transitions and animations.
 * Provides methods for various transition types between activities.
 */
public class ActivityTransitionUtil {

    /**
     * Enum for transition types
     */
    public enum TransitionType {
        SLIDE_RIGHT,
        SLIDE_LEFT,
        SLIDE_UP,
        SLIDE_DOWN,
        FADE,
        SHARED_ELEMENT,
        NONE
    }

    private ActivityTransitionUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Start an activity with a specific transition animation.
     *
     * @param activity     The current activity
     * @param intent       The intent to start
     * @param transitionType The type of transition animation to use
     */
    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, TransitionType transitionType) {
        switch (transitionType) {
            case SLIDE_RIGHT:
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case SLIDE_LEFT:
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case SLIDE_UP:
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.fade_out);
                break;
            case SLIDE_DOWN:
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.slide_out_bottom);
                break;
            case FADE:
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case NONE:
            default:
                activity.startActivity(intent);
                break;
        }
    }

    /**
     * Start an activity with a shared element transition.
     *
     * @param activity     The current activity
     * @param intent       The intent to start
     * @param sharedElement The shared element view
     * @param transitionName The transition name for the shared element
     */
    public static void startActivityWithSharedElement(@NonNull Activity activity,
                                                    @NonNull Intent intent,
                                                    @NonNull View sharedElement,
                                                    @NonNull String transitionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    activity, sharedElement, transitionName);
            activity.startActivity(intent, options.toBundle());
        } else {
            // Fallback for older devices
            startActivity(activity, intent, TransitionType.FADE);
        }
    }

    /**
     * Start an activity with multiple shared element transitions.
     *
     * @param activity     The current activity
     * @param intent       The intent to start
     * @param sharedElements Array of pairs of views and their transition names
     */
    public static void startActivityWithMultipleSharedElements(@NonNull Activity activity,
                                                             @NonNull Intent intent,
                                                             @NonNull Pair<View, String>[] sharedElements) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    activity, sharedElements);
            activity.startActivity(intent, options.toBundle());
        } else {
            // Fallback for older devices
            startActivity(activity, intent, TransitionType.FADE);
        }
    }

    /**
     * Apply exit transition when finishing an activity.
     *
     * @param activity     The activity to finish
     * @param transitionType The type of transition animation to use
     */
    public static void finishActivity(@NonNull Activity activity, TransitionType transitionType) {
        activity.finish();
        switch (transitionType) {
            case SLIDE_RIGHT:
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case SLIDE_LEFT:
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case SLIDE_UP:
                activity.overridePendingTransition(R.anim.fade_in, R.anim.slide_out_bottom);
                break;
            case SLIDE_DOWN:
                activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.fade_out);
                break;
            case FADE:
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case NONE:
            default:
                // No animation
                break;
        }
    }

    /**
     * Create a reveal animation for circular reveals on Lollipop+ devices.
     * This can be used when a button expands to reveal a new activity.
     *
     * @param activity     The current activity
     * @param intent       The intent to start
     * @param sourceView   The view that is the source of the reveal
     */
    public static void startActivityWithCircularReveal(@NonNull Activity activity,
                                                     @NonNull Intent intent,
                                                     @NonNull View sourceView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int[] location = new int[2];
            sourceView.getLocationOnScreen(location);
            int centerX = location[0] + sourceView.getWidth() / 2;
            int centerY = location[1] + sourceView.getHeight() / 2;

            ActivityOptionsCompat options = ActivityOptionsCompat.makeClipRevealAnimation(
                    sourceView, centerX, centerY, sourceView.getWidth(), sourceView.getHeight());
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } else {
            // Fallback for older devices
            startActivity(activity, intent, TransitionType.FADE);
        }
    }
}