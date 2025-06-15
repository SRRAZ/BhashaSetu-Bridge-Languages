package com.bhashasetu.app;

import android.content.Intent; // This import doesn't need changing as it's from the Android framework
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddWordActivity extends AppCompatActivity {
    
    public static final String EXTRA_ENGLISH = "com.bhashasetu.app.EXTRA_ENGLISH";
    public static final String EXTRA_HINDI = "com.bhashasetu.app.EXTRA_HINDI";
    public static final String EXTRA_ENGLISH_PRONUNCIATION = "com.bhashasetu.app.EXTRA_ENGLISH_PRONUNCIATION";
    public static final String EXTRA_HINDI_PRONUNCIATION = "com.bhashasetu.app.EXTRA_HINDI_PRONUNCIATION";
    public static final String EXTRA_CATEGORY = "com.bhashasetu.app.EXTRA_CATEGORY";
    
    private EditText editTextEnglish;
    private EditText editTextHindi;
    private EditText editTextEnglishPronunciation;
    private EditText editTextHindiPronunciation;
    private Spinner spinnerCategory;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        
        editTextEnglish = findViewById(R.id.edit_text_english);
        editTextHindi = findViewById(R.id.edit_text_hindi);
        editTextEnglishPronunciation = findViewById(R.id.edit_text_english_pronunciation);
        editTextHindiPronunciation = findViewById(R.id.edit_text_hindi_pronunciation);
        spinnerCategory = findViewById(R.id.spinner_category);
        Button buttonSave = findViewById(R.id.button_save);
        
        // Set up spinner with categories
        String[] categories = {"Greetings", "Basic", "Food", "Education", "People", "Travel", "Study"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        
        // Set up the save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWord();
            }
        });
        
        // Set title
        setTitle("Add New Word");
    }
    
    private void saveWord() {
        String english = editTextEnglish.getText().toString().trim();
        String hindi = editTextHindi.getText().toString().trim();
        String englishPronunciation = editTextEnglishPronunciation.getText().toString().trim();
        String hindiPronunciation = editTextHindiPronunciation.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        
        // Validate input
        if (english.isEmpty() || hindi.isEmpty()) {
            Toast.makeText(this, "Please enter both English and Hindi words", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Return data to MainActivity_ViewBased
        Intent data = new Intent();
        data.putExtra(EXTRA_ENGLISH, english);
        data.putExtra(EXTRA_HINDI, hindi);
        data.putExtra(EXTRA_ENGLISH_PRONUNCIATION, englishPronunciation);
        data.putExtra(EXTRA_HINDI_PRONUNCIATION, hindiPronunciation);
        data.putExtra(EXTRA_CATEGORY, category);
        
        setResult(RESULT_OK, data);
        finish();
    }
}