package com.example.englishhindi.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.englishhindi.fragment.LessonContentFragment;
import com.example.englishhindi.fragment.LessonVocabularyFragment;

public class LessonPagerAdapter extends FragmentPagerAdapter {
    
    private int lessonId;
    
    public LessonPagerAdapter(@NonNull FragmentManager fm, int lessonId) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.lessonId = lessonId;
    }
    
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // English content
                return LessonContentFragment.newInstance(lessonId, false);
            case 1:
                // Hindi content
                return LessonContentFragment.newInstance(lessonId, true);
            case 2:
                // Vocabulary list
                return LessonVocabularyFragment.newInstance(lessonId);
            default:
                return LessonContentFragment.newInstance(lessonId, false);
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
                return "English";
            case 1:
                return "हिंदी";
            case 2:
                return "Vocabulary";
            default:
                return "Tab " + position;
        }
    }
}