package com.bhashasetu.app.database.converters;

import androidx.room.TypeConverter;

import com.bhashasetu.app.model.exercise.MultipleChoiceExercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Type converter for lists of questions in Room database
 */
public class QuestionListConverter {
    
    private static final Gson gson = new Gson();
    
    @TypeConverter
    public static List<MultipleChoiceExercise.MultipleChoiceQuestion> toQuestionList(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<List<MultipleChoiceExercise.MultipleChoiceQuestion>>() {}.getType();
        return gson.fromJson(value, listType);
    }
    
    @TypeConverter
    public static String fromQuestionList(List<MultipleChoiceExercise.MultipleChoiceQuestion> list) {
        if (list == null) {
            return null;
        }
        return gson.toJson(list);
    }
}