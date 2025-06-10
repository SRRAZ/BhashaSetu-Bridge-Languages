package com.bhashasetu.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.bhashasetu.app.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog fragment for selecting game difficulty and category.
 */
public class DifficultySelectionDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";

    private OnDifficultySelectedListener listener;
    private Spinner spinnerDifficulty;
    private Spinner spinnerCategory;

    /**
     * Create a new instance with a title.
     */
    public static DifficultySelectionDialogFragment newInstance(String title) {
        DifficultySelectionDialogFragment fragment = new DifficultySelectionDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Interface for difficulty selection callback.
     */
    public interface OnDifficultySelectedListener {
        void onDifficultySelected(int difficulty, String category);
    }

    /**
     * Set the difficulty selected listener.
     */
    public void setOnDifficultySelectedListener(OnDifficultySelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        
        // Get the custom view
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_difficulty_selection, null);
        
        // Get title from arguments
        String title = getArguments().getString(ARG_TITLE, getString(R.string.select_difficulty));
        builder.setTitle(title);
        
        // Initialize spinners
        spinnerDifficulty = view.findViewById(R.id.spinner_difficulty);
        spinnerCategory = view.findViewById(R.id.spinner_category);
        
        // Set up difficulty spinner
        List<String> difficulties = new ArrayList<>();
        difficulties.add(getString(R.string.beginner) + " (1)");
        difficulties.add(getString(R.string.easy) + " (2)");
        difficulties.add(getString(R.string.intermediate) + " (3)");
        difficulties.add(getString(R.string.advanced) + " (4)");
        difficulties.add(getString(R.string.expert) + " (5)");
        
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, difficulties);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(difficultyAdapter);
        
        // Set up category spinner
        List<String> categories = new ArrayList<>();
        categories.add(getString(R.string.all_categories));
        categories.add(getString(R.string.animals));
        categories.add(getString(R.string.colors));
        categories.add(getString(R.string.food));
        categories.add(getString(R.string.numbers));
        categories.add(getString(R.string.family));
        categories.add(getString(R.string.common_phrases));
        categories.add(getString(R.string.time));
        categories.add(getString(R.string.weather));
        categories.add(getString(R.string.travel));
        
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        
        // Set up buttons
        Button buttonStart = view.findViewById(R.id.button_start);
        Button buttonCancel = view.findViewById(R.id.button_cancel);
        
        buttonStart.setOnClickListener(v -> {
            if (listener != null) {
                // Convert difficulty selection to numeric value (1-5)
                int difficultyPosition = spinnerDifficulty.getSelectedItemPosition() + 1;
                
                // Get selected category (null for "All Categories")
                String category = spinnerCategory.getSelectedItemPosition() == 0 ? 
                        null : spinnerCategory.getSelectedItem().toString();
                
                listener.onDifficultySelected(difficultyPosition, category);
            }
            dismiss();
        });
        
        buttonCancel.setOnClickListener(v -> dismiss());
        
        builder.setView(view);
        return builder.create();
    }
}