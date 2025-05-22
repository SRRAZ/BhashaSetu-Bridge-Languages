package com.example.englishhindi.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.englishhindi.fragment.FlashcardDecksFragment;
import com.example.englishhindi.fragment.QuizzesFragment;
import com.example.englishhindi.fragment.SpellingPracticeFragment;

public class PracticePagerAdapter extends FragmentPagerAdapter {
    
    public PracticePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FlashcardDecksFragment();
            case 1:
                return new QuizzesFragment();
            case 2:
                return new SpellingPracticeFragment();
            default:
                return new FlashcardDecksFragment();
        }
    }
    
    @Override
    public int getCount() {
        return 3;
    }
    
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Flashcards";
            case 1:
                return "Quizzes";
            case 2:
                return "Spelling";
            default:
                return "Tab " + position;
        }
    }
}