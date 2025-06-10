package com.bhashasetu.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.bhashasetu.app.adapter.LessonPagerAdapter;
import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.viewmodel.LessonViewModel;
import com.bhashasetu.app.viewmodel.UserProgressViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class LessonDetailActivity extends AppCompatActivity {
    
    private int lessonId;
    private LessonViewModel lessonViewModel;
    private UserProgressViewModel userProgressViewModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fabMarkComplete;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView headerImageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);
        
        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Get the lesson ID from intent
        lessonId = getIntent().getIntExtra("lesson_id", -1);
        if (lessonId == -1) {
            Toast.makeText(this, "Error: Lesson not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        // Get references to views
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        headerImageView = findViewById(R.id.image_view_lesson_header);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        fabMarkComplete = findViewById(R.id.fab_mark_complete);
        
        // Initialize ViewModels
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        
        // Load lesson data
        lessonViewModel.getLessonById(lessonId).observe(this, new Observer<Lesson>() {
            @Override
            public void onChanged(Lesson lesson) {
                if (lesson != null) {
                    setupUI(lesson);
                }
            }
        });
        
        // Set up "Mark as Complete" button
        fabMarkComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markLessonAsComplete();
            }
        });
    }
    
    private void setupUI(Lesson lesson) {
        // Set toolbar title
        collapsingToolbar.setTitle(lesson.getTitle());
        
        // Load image (would use a proper image loading library in production)
        // For now, just use a placeholder
        headerImageView.setImageResource(R.drawable.ic_launcher_background);
        
        // Set up ViewPager and TabLayout
        LessonPagerAdapter pagerAdapter = new LessonPagerAdapter(getSupportFragmentManager(), lessonId);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        
        // Check if the lesson is already marked as complete
        if (lesson.isHasCompleted()) {
            fabMarkComplete.setImageResource(android.R.drawable.ic_menu_edit);
        } else {
            fabMarkComplete.setImageResource(android.R.drawable.ic_menu_send);
        }
        
        // Update the FAB based on lesson completion status
        userProgressViewModel.getLessonProgress(1, lessonId).observe(this, new Observer<UserProgress>() {
            @Override
            public void onChanged(UserProgress progress) {
                if (progress != null && progress.getCompletionLevel() >= 100) {
                    fabMarkComplete.setImageResource(android.R.drawable.ic_menu_edit);
                } else {
                    fabMarkComplete.setImageResource(android.R.drawable.ic_menu_send);
                }
            }
        });
    }
    
    private void markLessonAsComplete() {
        lessonViewModel.getLessonById(lessonId).observe(this, new Observer<Lesson>() {
            @Override
            public void onChanged(Lesson lesson) {
                if (lesson != null) {
                    // Toggle completion status
                    boolean wasCompleted = lesson.isHasCompleted();
                    lesson.setHasCompleted(!wasCompleted);
                    lessonViewModel.update(lesson);
                    
                    // Update user progress
                    userProgressViewModel.getWordProgress(1, lessonId).observe(LessonDetailActivity.this, new Observer<UserProgress>() {
                        @Override
                        public void onChanged(UserProgress progress) {
                            if (progress != null) {
                                // If not completed, mark as 100% complete
                                if (!wasCompleted) {
                                    progress.setCompletionLevel(100);
                                    userProgressViewModel.update(progress);
                                    Toast.makeText(LessonDetailActivity.this, 
                                            getString(R.string.lesson_completed), 
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    
                    // Update the FAB icon
                    if (!wasCompleted) {
                        fabMarkComplete.setImageResource(android.R.drawable.ic_menu_edit);
                    } else {
                        fabMarkComplete.setImageResource(android.R.drawable.ic_menu_send);
                    }
                }
            }
        });
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}