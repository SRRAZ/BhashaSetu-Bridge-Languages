package com.bhashasetu.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.adapter.WordAdapter;
import com.bhashasetu.app.base.BaseActivity;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.view.LanguageSwitcherView;
import com.bhashasetu.app.viewmodel.LessonViewModel;
import com.bhashasetu.app.viewmodel.UserProgressViewModel;
import com.bhashasetu.app.viewmodel.WordViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    public static final int ADD_WORD_REQUEST = 1;
    
    private WordViewModel wordViewModel;
    private LessonViewModel lessonViewModel;
    private UserProgressViewModel userProgressViewModel;
    
    private RecyclerView recyclerView;
    private WordAdapter adapter;
    private BottomNavigationView bottomNavigation;
    private TextView textViewTitle;
    private TextView textViewSubtitle;
    private FloatingActionButton fabAddWord;
    private LanguageSwitcherView languageSwitcher;
    
    private String currentCategory = null;
    private boolean showingFavorites = false;
    private String searchQuery = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize UI components
        recyclerView = findViewById(R.id.recycler_view);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        textViewTitle = findViewById(R.id.text_view_title);
        textViewSubtitle = findViewById(R.id.text_view_subtitle);
        fabAddWord = findViewById(R.id.fab_add_word);
        languageSwitcher = findViewById(R.id.language_switcher);
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        
        // Set up adapter
        adapter = new WordAdapter();
        recyclerView.setAdapter(adapter);
        
        // Initialize ViewModels
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        
        // Load initial data
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.submitList(words);
            }
        });
        
        // Set up click listeners
        fabAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivityForResult(intent, ADD_WORD_REQUEST);
            }
        });
        
        adapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Word word) {
                Intent intent = new Intent(MainActivity.this, WordDetailActivity.class);
                intent.putExtra("word_id", word.getId());
                startActivity(intent);
            }
        });
        
        // Set up bottom navigation
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        
                        if (itemId == R.id.action_words) {
                            // Show all words (already implemented)
                            showAllWords();
                            return true;
                        } else if (itemId == R.id.action_lessons) {
                            // Navigate to lessons activity
                            Intent lessonsIntent = new Intent(MainActivity.this, LessonsActivity.class);
                            startActivity(lessonsIntent);
                            return true;
                        } else if (itemId == R.id.action_practice) {
                            // Navigate to practice activity
                            Intent practiceIntent = new Intent(MainActivity.this, PracticeActivity.class);
                            startActivity(practiceIntent);
                            return true;
                        } else if (itemId == R.id.action_favorites) {
                            // Show favorite words
                            showFavoriteWords();
                            return true;
                        }
                        
                        return false;
                    }
                });
        
        // Set language switcher listener
        languageSwitcher.setOnLanguageChangeListener(new LanguageSwitcherView.LanguageSwitchListener() {
            @Override
            public void onLanguageChanged(boolean isHindi) {
                // The activity will be recreated by the LanguageSwitcherView
                // so we don't need to do anything here
            }
        });
        
        // Update UI based on language
        updateUILanguage();
    }
    
    @Override
    protected void updateUILanguage() {
        // Update title and subtitle based on language
        if (isHindiActive()) {
            textViewTitle.setText(R.string.welcome_message);
            textViewSubtitle.setText(R.string.welcome_message_hindi);
        } else {
            textViewTitle.setText(R.string.welcome_message);
            textViewSubtitle.setText(R.string.welcome_message_hindi);
        }
    
        // Update bottom navigation titles
        Menu menu = bottomNavigation.getMenu();
        menu.findItem(R.id.action_words).setTitle(getString(R.string.home_tab));
        menu.findItem(R.id.action_lessons).setTitle(getString(R.string.lessons_tab));
        menu.findItem(R.id.action_practice).setTitle(getString(R.string.practice_tab));
        menu.findItem(R.id.action_favorites).setTitle(getString(R.string.favorites_tab));
    }
        
        // Check preferences for interface language
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
        
        if (useHindiInterface) {
            textViewTitle.setText(R.string.welcome_message_hindi);
            textViewSubtitle.setText(R.string.welcome_message);
        } else {
            textViewTitle.setText(R.string.welcome_message);
            textViewSubtitle.setText(R.string.welcome_message_hindi);
        }
    }
    
    private void showAllWords() {
        showingFavorites = false;
        currentCategory = null;
        searchQuery = null;
        
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.submitList(words);
            }
        });
        
        setActionBarTitle(getString(R.string.app_name));
    }
    
    private void showFavoriteWords() {
        showingFavorites = true;
        currentCategory = null;
        searchQuery = null;
        
        wordViewModel.getFavoriteWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.submitList(words);
            }
        });
        
        setActionBarTitle(getString(R.string.action_favorites));
    }
    
    private void showWordsByCategory(String category) {
        showingFavorites = false;
        currentCategory = category;
        searchQuery = null;
        
        wordViewModel.getWordsByCategory(category).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.submitList(words);
            }
        });
        
        setActionBarTitle(category);
    }
    
    private void searchWords(String query) {
        showingFavorites = false;
        currentCategory = null;
        searchQuery = query;
        
        wordViewModel.searchWords(query).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.submitList(words);
            }
        });
        
        setActionBarTitle(getString(R.string.action_search) + ": " + query);
    }
    
    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    searchWords(query);
                }
                return true;
            }
            
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    searchWords(newText);
                } else if (searchQuery != null) {
                    showAllWords();
                }
                return true;
            }
        });
        
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
            
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (searchQuery != null) {
                    showAllWords();
                }
                return true;
            }
        });
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_categories) {
            // Show categories dialog or menu
            // This would be implemented in a real app
            Toast.makeText(this, "Categories feature would be shown here", Toast.LENGTH_SHORT).show();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == ADD_WORD_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, getString(R.string.word_saved), Toast.LENGTH_SHORT).show();
        }
    }
}
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning to this activity
        if (showingFavorites) {
            showFavoriteWords();
        } else if (currentCategory != null) {
            showWordsByCategory(currentCategory);
        } else if (searchQuery != null) {
            searchWords(searchQuery);
        } else {
            showAllWords();
        }
        
        // Update UI language in case it changed in settings
        updateUILanguage();
    }
}