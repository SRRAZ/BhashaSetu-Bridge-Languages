package com.bhashasetu.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.adapter.LessonAdapter;
import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.viewmodel.LessonViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class LessonsActivity extends AppCompatActivity {
    
    private LessonViewModel lessonViewModel;
    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private TabLayout tabLayout;
    private FloatingActionButton fabNextLesson;
    private SharedPreferences sharedPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        
        // Get shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
        
        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view_lessons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        
        // Set up adapter
        adapter = new LessonAdapter();
        adapter.setUseHindiTitles(useHindiInterface);
        recyclerView.setAdapter(adapter);
        
        // Set up ViewModel
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        lessonViewModel.getAllLessons().observe(this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(List<Lesson> lessons) {
                adapter.submitList(lessons);
            }
        });
        
        // Set up TabLayout for categories
        tabLayout = findViewById(R.id.tab_layout_categories);
        loadCategories();
        
        // Set up click listeners
        fabNextLesson = findViewById(R.id.fab_next_lesson);
        fabNextLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextIncompleteLesson();
            }
        });
        
        // Handle lesson item clicks
        adapter.setOnItemClickListener(new LessonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lesson lesson) {
                Intent intent = new Intent(LessonsActivity.this, com.bhashasetu.app.LessonDetailActivity.class);
                intent.putExtra("lesson_id", lesson.getId());
                startActivity(intent);
            }
        });
        
        // Set up tab selection listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String category = tab.getText().toString();
                if (category.equals(getString(R.string.all_categories))) {
                    lessonViewModel.getAllLessons().observe(LessonsActivity.this, new Observer<List<Lesson>>() {
                        @Override
                        public void onChanged(List<Lesson> lessons) {
                            adapter.submitList(lessons);
                        }
                    });
                } else {
                    lessonViewModel.getLessonsByCategory(category).observe(LessonsActivity.this, new Observer<List<Lesson>>() {
                        @Override
                        public void onChanged(List<Lesson> lessons) {
                            adapter.submitList(lessons);
                        }
                    });
                }
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Not needed
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Not needed
            }
        });
    }
    
    private void loadCategories() {
        // Add an "All" tab first
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.all_categories)));
        
        // Add tabs for each category
        lessonViewModel.getAllCategories().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                // Clear existing tabs except the first one (All)
                while (tabLayout.getTabCount() > 1) {
                    tabLayout.removeTabAt(1);
                }
                
                // Add a tab for each category
                for (String category : categories) {
                    tabLayout.addTab(tabLayout.newTab().setText(category));
                }
            }
        });
    }
    
    private void gotoNextIncompleteLesson() {
        lessonViewModel.getNextIncompleteLesson().observe(this, new Observer<Lesson>() {
            @Override
            public void onChanged(Lesson lesson) {
                if (lesson != null) {
                    Intent intent = new Intent(LessonsActivity.this, com.bhashasetu.app.LessonDetailActivity.class);
                    intent.putExtra("lesson_id", lesson.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(LessonsActivity.this, 
                            getString(R.string.no_incomplete_lessons), 
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lessons, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_toggle_language) {
            boolean currentSetting = sharedPreferences.getBoolean("use_hindi_interface", false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("use_hindi_interface", !currentSetting);
            editor.apply();
            
            // Update the adapter
            adapter.setUseHindiTitles(!currentSetting);
            
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}