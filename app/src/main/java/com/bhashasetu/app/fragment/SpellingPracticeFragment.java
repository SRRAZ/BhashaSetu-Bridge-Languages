package com.bhashasetu.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bhashasetu.app.R;
import com.bhashasetu.app.PronunciationPracticeActivity;
import com.bhashasetu.app.SpellingPracticeActivity;
import com.bhashasetu.app.adapter.PracticeSessionAdapter;
import com.bhashasetu.app.model.PracticeSession;
import com.bhashasetu.app.viewmodel.PracticeSessionViewModel;
import com.bhashasetu.app.viewmodel.WordViewModel;

import java.util.List;

public class SpellingPracticeFragment extends Fragment {

    private WordViewModel wordViewModel;
    private PracticeSessionViewModel sessionViewModel;
    private RecyclerView recyclerView;
    private PracticeSessionAdapter adapter;
    private TextView textViewNoSessions;
    private Spinner spinnerCategory;
    private RadioGroup radioGroupDifficulty;
    private Button buttonStartSpelling;
    private int selectedDifficulty = 1; // Default to easy

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spelling_practice, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_view_sessions);
        textViewNoSessions = view.findViewById(R.id.text_view_no_sessions);
        spinnerCategory = view.findViewById(R.id.spinner_category);
        radioGroupDifficulty = view.findViewById(R.id.radio_group_difficulty);
        buttonStartSpelling = view.findViewById(R.id.button_start_spelling);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        
        // Set up adapter
        adapter = new PracticeSessionAdapter();
        recyclerView.setAdapter(adapter);
        
        // Set up ViewModels
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        sessionViewModel = new ViewModelProvider(requireActivity()).get(PracticeSessionViewModel.class);
        
        // Get categories for the spinner
        wordViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                // Add "All Categories" option
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                
                categoryAdapter.add("All Categories");
                for (String category : categories) {
                    categoryAdapter.add(category);
                }
                
                spinnerCategory.setAdapter(categoryAdapter);
            }
        });
        
        // Get recent spelling practice sessions
        sessionViewModel.getSessionsByType(1, "spelling").observe(getViewLifecycleOwner(), new Observer<List<PracticeSession>>() {
            @Override
            public void onChanged(List<PracticeSession> sessions) {
                adapter.submitList(sessions);
                
                // Show "no sessions" message if the list is empty
                if (sessions.isEmpty()) {
                    textViewNoSessions.setVisibility(View.VISIBLE);
                } else {
                    textViewNoSessions.setVisibility(View.GONE);
                }
            }
        });
        
        // Set up difficulty radio buttons
        radioGroupDifficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_easy) {
                    selectedDifficulty = 1;
                } else if (checkedId == R.id.radio_medium) {
                    selectedDifficulty = 3;
                } else if (checkedId == R.id.radio_hard) {
                    selectedDifficulty = 5;
                }
            }
        });
        
        // Set up click listener for start button
        buttonStartSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create dialog to choose between spelling and pronunciation
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                builder.setTitle("चुनें/Choose Practice Type");
                
                // Inflate custom layout for the dialog
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_practice_type_choice, null);
                builder.setView(dialogView);
                
                Button buttonSpelling = dialogView.findViewById(R.id.button_spelling_practice);
                Button buttonPronunciation = dialogView.findViewById(R.id.button_pronunciation_practice);
                
                final androidx.appcompat.app.AlertDialog dialog = builder.create();
                
                buttonSpelling.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        
                        Intent intent = new Intent(getActivity(), SpellingPracticeActivity.class);
                        intent.putExtra("difficulty", selectedDifficulty);
                        
                        // Get the selected category
                        String category = spinnerCategory.getSelectedItem().toString();
                        if (!category.equals("All Categories")) {
                            intent.putExtra("category", category);
                        }
                        
                        intent.putExtra("word_count", 10); // 10 words in a practice session
                        startActivity(intent);
                    }
                });
                
                buttonPronunciation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        
                        Intent intent = new Intent(getActivity(), PronunciationPracticeActivity.class);
                        intent.putExtra("difficulty", selectedDifficulty);
                        
                        // Get the selected category
                        String category = spinnerCategory.getSelectedItem().toString();
                        if (!category.equals("All Categories")) {
                            intent.putExtra("category", category);
                        }
                        
                        intent.putExtra("word_count", 10); // 10 words in a practice session
                        startActivity(intent);
                    }
                });
                
                dialog.show();
            }
        });
        
        // Set up click listener for session items
        adapter.setOnItemClickListener(new PracticeSessionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PracticeSession session) {
                // In a real app, this would open a session details activity
                // For now, we'll just restart the session if it's completed
                if (session.isCompleted()) {
                    Intent intent = new Intent(getActivity(), SpellingPracticeActivity.class);
                    intent.putExtra("difficulty", 1);
                    intent.putExtra("word_count", 10);
                    startActivity(intent);
                }
            }
        });
    }
}