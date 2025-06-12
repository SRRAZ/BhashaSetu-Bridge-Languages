package com.bhashasetu.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.WordDetailActivity;
import com.bhashasetu.app.adapter.WordAdapter;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.viewmodel.WordViewModel;

import java.util.List;

public class LessonVocabularyFragment extends Fragment {
    
    private static final String ARG_LESSON_ID = "lesson_id";
    
    private int lessonId;
    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private WordAdapter adapter;
    private TextView textViewNoWords;
    
    public static LessonVocabularyFragment newInstance(int lessonId) {
        LessonVocabularyFragment fragment = new LessonVocabularyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LESSON_ID, lessonId);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lessonId = getArguments().getInt(ARG_LESSON_ID);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_vocabulary, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_vocabulary);
        textViewNoWords = view.findViewById(R.id.text_view_no_words);
        return view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        
        // Set up adapter
        adapter = new WordAdapter();
        recyclerView.setAdapter(adapter);
        
        // Set up ViewModel
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        
        // Load words for this lesson
        wordViewModel.getWordsForLesson(lessonId).observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.submitList(words);
                
                // Show a message if there are no words
                if (words.isEmpty()) {
                    textViewNoWords.setVisibility(View.VISIBLE);
                } else {
                    textViewNoWords.setVisibility(View.GONE);
                }
            }
        });
        
        // Handle word item clicks
        adapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Word word) {
                Intent intent = new Intent(getActivity(), WordDetailActivity.class);
                intent.putExtra("word_id", word.getId());
                startActivity(intent);
            }
        });
    }
}