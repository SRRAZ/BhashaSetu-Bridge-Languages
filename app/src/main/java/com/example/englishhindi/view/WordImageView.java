package com.example.englishhindi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.englishhindi.R;

/**
 * Custom view for displaying an image with associated word
 */
public class WordImageView extends FrameLayout {

    private ImageView imageView;
    private TextView captionTextView;
    private ProgressBar progressBar;
    
    private String imageUrl;
    private String captionText;
    private OnImageClickListener clickListener;

    public WordImageView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public WordImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WordImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs) {
        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.view_word_image, this, true);
        
        // Get view references
        imageView = findViewById(R.id.image_view);
        captionTextView = findViewById(R.id.text_caption);
        progressBar = findViewById(R.id.progress_bar);
        
        // Set click listener
        imageView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onImageClick(imageUrl);
            }
        });
        
        // Parse attributes if available
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WordImageView);
            
            imageUrl = a.getString(R.styleable.WordImageView_imageUrl);
            captionText = a.getString(R.styleable.WordImageView_captionText);
            
            a.recycle();
        }
        
        // Set initial values
        if (captionText != null) {
            captionTextView.setText(captionText);
            captionTextView.setVisibility(View.VISIBLE);
        } else {
            captionTextView.setVisibility(View.GONE);
        }
        
        if (imageUrl != null && !imageUrl.isEmpty()) {
            loadImage();
        }
    }
    
    /**
     * Load the image using Glide
     */
    private void loadImage() {
        progressBar.setVisibility(View.VISIBLE);
        
        Glide.with(getContext())
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                    
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
    
    /**
     * Set the image URL
     * @param url image URL
     */
    public void setImageUrl(String url) {
        this.imageUrl = url;
        if (url != null && !url.isEmpty()) {
            loadImage();
        }
    }
    
    /**
     * Set the caption text
     * @param text caption text
     */
    public void setCaptionText(String text) {
        this.captionText = text;
        if (text != null && !text.isEmpty()) {
            captionTextView.setText(text);
            captionTextView.setVisibility(View.VISIBLE);
        } else {
            captionTextView.setVisibility(View.GONE);
        }
    }
    
    /**
     * Set a click listener for the image
     * @param listener the listener to set
     */
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.clickListener = listener;
    }
    
    /**
     * Interface for image click callbacks
     */
    public interface OnImageClickListener {
        void onImageClick(String imageUrl);
    }
}