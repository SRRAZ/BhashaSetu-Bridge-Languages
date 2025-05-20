package com.example.englishhindi.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.englishhindi.R;
import com.example.englishhindi.audio.AudioFeedbackManager;
import com.example.englishhindi.model.Achievement;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

/**
 * Dialog to display when an achievement is unlocked.
 * Includes animations and celebratory effects.
 */
public class AchievementUnlockedDialog extends Dialog {

    private Achievement achievement;
    private AnimatorSet mainAnimator;
    private Handler handler = new Handler(Looper.getMainLooper());
    private AudioFeedbackManager audioFeedbackManager;

    /**
     * Constructor.
     *
     * @param context The context
     * @param achievement The unlocked achievement
     */
    public AchievementUnlockedDialog(@NonNull Context context, Achievement achievement) {
        super(context);
        this.achievement = achievement;
        audioFeedbackManager = AudioFeedbackManager.getInstance(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_achievement_unlocked);
        
        // Set dialog window properties
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        
        // Find views
        KonfettiView konfettiView = findViewById(R.id.konfetti_view);
        ImageView imageViewIcon = findViewById(R.id.image_view_achievement_icon);
        TextView textViewTitle = findViewById(R.id.text_view_achievement_title);
        TextView textViewDescription = findViewById(R.id.text_view_achievement_description);
        TextView textViewPoints = findViewById(R.id.text_view_achievement_points);
        Button buttonClose = findViewById(R.id.button_close);
        
        // Set achievement details
        imageViewIcon.setImageResource(achievement.getIconResId());
        textViewTitle.setText(achievement.getTitle());
        textViewDescription.setText(achievement.getDescription());
        textViewPoints.setText(getContext().getString(
                R.string.achievement_points, achievement.getPointsValue()));
        
        // Set up button
        buttonClose.setOnClickListener(v -> dismiss());
        
        // Play achievement sound
        audioFeedbackManager.playAchievementSound();
        
        // Show dialog with animations
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        
        // Start entrance animations when dialog is shown
        setOnShowListener(dialog -> {
            startEntranceAnimations(konfettiView);
        });
    }

    /**
     * Start the entrance animations for the dialog.
     */
    private void startEntranceAnimations(KonfettiView konfettiView) {
        // Play confetti animation
        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 1500L);
        
        // Get views for animation
        View dialogContainer = findViewById(R.id.dialog_container);
        ImageView imageViewIcon = findViewById(R.id.image_view_achievement_icon);
        TextView textViewTitle = findViewById(R.id.text_view_achievement_title);
        TextView textViewDescription = findViewById(R.id.text_view_achievement_description);
        TextView textViewPoints = findViewById(R.id.text_view_achievement_points);
        Button buttonClose = findViewById(R.id.button_close);
        View shineView = findViewById(R.id.view_shine);
        
        // Initially hide all elements except the container
        imageViewIcon.setAlpha(0f);
        textViewTitle.setAlpha(0f);
        textViewDescription.setAlpha(0f);
        textViewPoints.setAlpha(0f);
        buttonClose.setAlpha(0f);
        shineView.setAlpha(0f);
        
        // Dialog container animation (scale in)
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(dialogContainer, "scaleX", 0.7f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(dialogContainer, "scaleY", 0.7f, 1.0f);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(dialogContainer, "alpha", 0f, 1f);
        
        // Create animator set for container
        AnimatorSet containerAnimSet = new AnimatorSet();
        containerAnimSet.playTogether(scaleXAnim, scaleYAnim, alphaAnim);
        containerAnimSet.setDuration(500);
        containerAnimSet.setInterpolator(new DecelerateInterpolator());
        
        // Icon animation
        ObjectAnimator iconScaleX = ObjectAnimator.ofFloat(imageViewIcon, "scaleX", 0.5f, 1.3f, 1.0f);
        ObjectAnimator iconScaleY = ObjectAnimator.ofFloat(imageViewIcon, "scaleY", 0.5f, 1.3f, 1.0f);
        ObjectAnimator iconAlpha = ObjectAnimator.ofFloat(imageViewIcon, "alpha", 0f, 1f);
        
        AnimatorSet iconAnimSet = new AnimatorSet();
        iconAnimSet.playTogether(iconScaleX, iconScaleY, iconAlpha);
        iconAnimSet.setDuration(800);
        iconAnimSet.setInterpolator(new BounceInterpolator());
        
        // Title, description, and points animations
        ObjectAnimator titleAnim = ObjectAnimator.ofFloat(textViewTitle, "translationY", 50f, 0f);
        titleAnim.setInterpolator(new DecelerateInterpolator());
        titleAnim.setDuration(500);
        
        ObjectAnimator titleAlphaAnim = ObjectAnimator.ofFloat(textViewTitle, "alpha", 0f, 1f);
        titleAlphaAnim.setDuration(500);
        
        ObjectAnimator descAnim = ObjectAnimator.ofFloat(textViewDescription, "translationY", 50f, 0f);
        descAnim.setInterpolator(new DecelerateInterpolator());
        descAnim.setDuration(500);
        
        ObjectAnimator descAlphaAnim = ObjectAnimator.ofFloat(textViewDescription, "alpha", 0f, 1f);
        descAlphaAnim.setDuration(500);
        
        ObjectAnimator pointsAnim = ObjectAnimator.ofFloat(textViewPoints, "translationY", 50f, 0f);
        pointsAnim.setInterpolator(new DecelerateInterpolator());
        pointsAnim.setDuration(500);
        
        ObjectAnimator pointsAlphaAnim = ObjectAnimator.ofFloat(textViewPoints, "alpha", 0f, 1f);
        pointsAlphaAnim.setDuration(500);
        
        // Button animation
        ObjectAnimator buttonAnim = ObjectAnimator.ofFloat(buttonClose, "alpha", 0f, 1f);
        buttonAnim.setDuration(300);
        
        // Shine effect animation
        ObjectAnimator shineRotate = ObjectAnimator.ofFloat(shineView, "rotation", 0f, 360f);
        shineRotate.setDuration(3000);
        shineRotate.setRepeatCount(ValueAnimator.INFINITE);
        shineRotate.setRepeatMode(ValueAnimator.RESTART);
        shineRotate.setInterpolator(new AccelerateDecelerateInterpolator());
        
        ObjectAnimator shineAlpha = ObjectAnimator.ofFloat(shineView, "alpha", 0f, 0.7f);
        shineAlpha.setDuration(1000);
        
        // Main animation set
        mainAnimator = new AnimatorSet();
        mainAnimator.play(containerAnimSet).before(iconAnimSet);
        mainAnimator.play(iconAnimSet).before(titleAnim);
        mainAnimator.play(titleAnim).with(titleAlphaAnim);
        mainAnimator.play(titleAnim).before(descAnim);
        mainAnimator.play(descAnim).with(descAlphaAnim);
        mainAnimator.play(descAnim).before(pointsAnim);
        mainAnimator.play(pointsAnim).with(pointsAlphaAnim);
        mainAnimator.play(pointsAnim).before(buttonAnim);
        mainAnimator.play(shineAlpha).after(iconAnimSet);
        mainAnimator.play(shineRotate).with(shineAlpha);
        
        // Start the animation sequence
        mainAnimator.start();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Clean up animations
        if (mainAnimator != null && mainAnimator.isRunning()) {
            mainAnimator.cancel();
        }
        handler.removeCallbacksAndMessages(null);
    }
}