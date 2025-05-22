package com.example.englishhindi.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.QuizActivity;
import com.example.englishhindi.R;
import com.example.englishhindi.adapter.QuizAdapter;
import com.example.englishhindi.model.Quiz;
import com.example.englishhindi.viewmodel.QuizViewModel;

import java.util.List;

public class QuizzesFragment extends Fragment {

    private QuizViewModel quizViewModel;
    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private TextView textViewNoQuizzes;
    private Spinner spinnerDifficulty;
    private Button buttonStartQuickQuiz;
    private SharedPreferences sharedPreferences;
    private int selectedDifficulty = 5; // Default to all difficulties

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quizzes, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_view_quizzes);
        textViewNoQuizzes = view.findViewById(R.id.text_view_no_quizzes);
        spinnerDifficulty = view.findViewById(R.id.spinner_difficulty);
        buttonStartQuickQuiz = view.findViewById(R.id.button_start_quick_quiz);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Get shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        
        // Set up adapter
        adapter = new QuizAdapter();
        adapter.setUseHindiQuestions(useHindiInterface);
        recyclerView.setAdapter(adapter);
        
        // Set up difficulty spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.difficulty_levels,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(spinnerAdapter);
        
        spinnerDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // All
                        selectedDifficulty = 5;
                        break;
                    case 1: // Easy
                        selectedDifficulty = 1;
                        break;
                    case 2: // Medium
                        selectedDifficulty = 3;
                        break;
                    case 3: // Hard
                        selectedDifficulty = 5;
                        break;
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDifficulty = 5; // Default to all difficulties
            }
        });
        
        // Set up ViewModel
        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        quizViewModel.getRandomQuizzes(10).observe(getViewLifecycleOwner(), new Observer<List<Quiz>>() {
            @Override
            public void onChanged(List<Quiz> quizzes) {
                adapter.submitList(quizzes);
                
                // Show "no quizzes" message if the list is empty
                if (quizzes.isEmpty()) {
                    textViewNoQuizzes.setVisibility(View.VISIBLE);
                } else {
                    textViewNoQuizzes.setVisibility(View.GONE);
                }
            }
        });
        
        // Set up click listeners
        adapter.setOnItemClickListener(new QuizAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quiz quiz) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("quiz_id", quiz.getId());
                intent.putExtra("single_quiz", true);
                startActivity(intent);
            }
        });
        
        buttonStartQuickQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("quick_quiz", true);
                intent.putExtra("difficulty", selectedDifficulty);
                intent.putExtra("quiz_count", 10); // 10 questions in a quick quiz
                startActivity(intent);
            }
        });
    }
}