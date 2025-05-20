package com.example.englishhindi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.englishhindi.R;
import com.example.englishhindi.model.Lesson;
import com.example.englishhindi.viewmodel.LessonViewModel;

public class LessonContentFragment extends Fragment {
    
    private static final String ARG_LESSON_ID = "lesson_id";
    private static final String ARG_IS_HINDI = "is_hindi";
    
    private int lessonId;
    private boolean isHindi;
    private LessonViewModel lessonViewModel;
    private TextView textViewTitle;
    private WebView webViewContent;
    
    public static LessonContentFragment newInstance(int lessonId, boolean isHindi) {
        LessonContentFragment fragment = new LessonContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LESSON_ID, lessonId);
        args.putBoolean(ARG_IS_HINDI, isHindi);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lessonId = getArguments().getInt(ARG_LESSON_ID);
            isHindi = getArguments().getBoolean(ARG_IS_HINDI);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_content, container, false);
        textViewTitle = view.findViewById(R.id.text_view_lesson_title);
        webViewContent = view.findViewById(R.id.web_view_lesson_content);
        return view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        lessonViewModel = new ViewModelProvider(requireActivity()).get(LessonViewModel.class);
        
        lessonViewModel.getLessonById(lessonId).observe(getViewLifecycleOwner(), new Observer<Lesson>() {
            @Override
            public void onChanged(Lesson lesson) {
                if (lesson != null) {
                    updateUI(lesson);
                }
            }
        });
    }
    
    private void updateUI(Lesson lesson) {
        // Set the title and content based on language
        if (isHindi) {
            textViewTitle.setText(lesson.getTitleHindi());
            loadHtmlContent(lesson.getContentHindi());
        } else {
            textViewTitle.setText(lesson.getTitle());
            loadHtmlContent(lesson.getContent());
        }
    }
    
    private void loadHtmlContent(String htmlContent) {
        // Define the CSS style for the content
        String cssStyle = "<style>" +
                "body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; }" +
                "h1 { color: #2196F3; font-size: 24px; }" +
                "h2 { color: #009688; font-size: 20px; }" +
                "ul, ol { padding-left: 20px; }" +
                "li { margin-bottom: 8px; }" +
                "strong { color: #E91E63; }" +
                "</style>";
        
        // Prepare the HTML content
        String html = "<html><head>" + cssStyle + "</head><body>" + htmlContent + "</body></html>";
        
        // Load the HTML content into the WebView
        webViewContent.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }
}