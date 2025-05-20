package com.example.englishhindi.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.example.englishhindi.R;

/**
 * Utility class for adding visual effects to views.
 * Provides methods for ripple effects, animations, and other visual enhancements.
 */
public class ViewEffectUtil {

    private ViewEffectUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Add a ripple effect to a view.
     *
     * @param view  The view to add the ripple effect to
     * @param color The ripple color
     */
    public static void addRippleEffect(@NonNull View view, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setClickable(true);
            view.setFocusable(true);
            
            // Create ripple drawable
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            Drawable content = view.getBackground();
            RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, content, null);
            
            // Set as background
            ViewCompat.setBackground(view, rippleDrawable);
        }
    }

    /**
     * Add a circular reveal animation to a view.
     *
     * @param view     The view to animate
     * @param centerX  The center X of the reveal
     * @param centerY  The center Y of the reveal
     * @param duration The duration of the animation
     */
    public static void addCircularReveal(@NonNull View view, int centerX, int centerY, long duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setVisibility(View.INVISIBLE);
            
            // Set a listener to start the animation when the view is laid out
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                         int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    // Remove the listener to prevent repeated animations
                    view.removeOnLayoutChangeListener(this);
                    
                    // Get the final radius
                    float finalRadius = (float) Math.hypot(right - left, bottom - top);
                    
                    // Create and start the animation
                    android.animation.Animator anim = android.view.ViewAnimationUtils.createCircularReveal(
                            view, centerX, centerY, 0f, finalRadius);
                    anim.setDuration(duration);
                    view.setVisibility(View.VISIBLE);
                    anim.start();
                }
            });
        } else {
            view.setVisibility(View.VISIBLE);
            view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in));
        }
    }

    /**
     * Add a fade in animation to a view.
     *
     * @param view The view to animate
     */
    public static void addFadeIn(@NonNull View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
        view.startAnimation(animation);
    }

    /**
     * Add a fade out animation to a view.
     *
     * @param view The view to animate
     */
    public static void addFadeOut(@NonNull View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out);
        view.startAnimation(animation);
    }

    /**
     * Add a custom animation to a view.
     *
     * @param view            The view to animate
     * @param animationResId  The animation resource ID
     */
    public static void addAnimation(@NonNull View view, @AnimRes int animationResId) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), animationResId);
        view.startAnimation(animation);
    }

    /**
     * Add a bounce animation to a view.
     *
     * @param view The view to animate
     */
    public static void addBounceAnimation(@NonNull View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
        view.startAnimation(animation);
    }

    /**
     * Add a pulse animation to a view.
     *
     * @param view The view to animate
     */
    public static void addPulseAnimation(@NonNull View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.pulse);
        view.startAnimation(animation);
    }

    /**
     * Set staggered animations for children of a ViewGroup.
     *
     * @param viewGroup    The parent ViewGroup
     * @param animationRes The animation resource ID
     * @param staggerDelay The delay between each child animation (in milliseconds)
     */
    public static void setStaggeredAnimation(@NonNull ViewGroup viewGroup, 
                                           @AnimRes int animationRes, long staggerDelay) {
        int childCount = viewGroup.getChildCount();
        
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            Animation animation = AnimationUtils.loadAnimation(viewGroup.getContext(), animationRes);
            
            // Set start time with staggered delay
            animation.setStartTime(Animation.START_ON_FIRST_FRAME + (staggerDelay * i));
            
            child.startAnimation(animation);
        }
    }

    /**
     * Set elevation (shadow) for a view.
     *
     * @param view      The view to set elevation for
     * @param elevation The elevation value in pixels
     */
    public static void setElevation(@NonNull View view, float elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(elevation);
        }
    }
}