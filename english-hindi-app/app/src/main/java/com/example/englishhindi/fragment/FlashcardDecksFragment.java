package com.example.englishhindi.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.FlashcardActivity;
import com.example.englishhindi.R;
import com.example.englishhindi.adapter.FlashcardDeckAdapter;
import com.example.englishhindi.model.FlashcardDeck;
import com.example.englishhindi.viewmodel.FlashcardDeckViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FlashcardDecksFragment extends Fragment {

    private FlashcardDeckViewModel flashcardDeckViewModel;
    private RecyclerView recyclerView;
    private FlashcardDeckAdapter adapter;
    private TextView textViewNoDecks;
    private FloatingActionButton fabAddDeck;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard_decks, container, false);
        
        recyclerView = view.findViewById(R.id.recycler_view_decks);
        textViewNoDecks = view.findViewById(R.id.text_view_no_decks);
        fabAddDeck = view.findViewById(R.id.fab_add_deck);
        
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
        adapter = new FlashcardDeckAdapter();
        adapter.setUseHindiTitles(useHindiInterface);
        recyclerView.setAdapter(adapter);
        
        // Set up ViewModel
        flashcardDeckViewModel = new ViewModelProvider(requireActivity()).get(FlashcardDeckViewModel.class);
        flashcardDeckViewModel.getAllDecks().observe(getViewLifecycleOwner(), new Observer<List<FlashcardDeck>>() {
            @Override
            public void onChanged(List<FlashcardDeck> decks) {
                adapter.submitList(decks);
                
                // Show "no decks" message if the list is empty
                if (decks.isEmpty()) {
                    textViewNoDecks.setVisibility(View.VISIBLE);
                } else {
                    textViewNoDecks.setVisibility(View.GONE);
                }
            }
        });
        
        // Set up click listeners
        adapter.setOnItemClickListener(new FlashcardDeckAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FlashcardDeck deck) {
                Intent intent = new Intent(getActivity(), FlashcardActivity.class);
                intent.putExtra("deck_id", deck.getId());
                startActivity(intent);
            }
        });
        
        adapter.setOnFavoriteClickListener(new FlashcardDeckAdapter.OnFavoriteClickListener() {
            @Override
            public void onFavoriteClick(FlashcardDeck deck) {
                flashcardDeckViewModel.toggleFavorite(deck.getId(), deck.isFavorite());
            }
        });
        
        fabAddDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // In a real app, this would open a dialog or activity to create a new deck
                // For now, we'll just create a sample deck
                FlashcardDeck newDeck = new FlashcardDeck(
                    "New Deck",
                    "नया डेक",
                    "A newly created flashcard deck.",
                    "एक नया बनाया गया फ्लैशकार्ड डेक।",
                    "Custom",
                    1
                );
                flashcardDeckViewModel.insert(newDeck);
            }
        });
    }
}