package com.example.englishhindi.database.converters;

import androidx.room.TypeConverter;

import com.example.englishhindi.model.exercise.FillBlankExercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Type converter for fill-in-the-blank question lists in Room database
 */
public class FillBlankQuestionListConverter {
    
    private static final Gson gson = new Gson();
    
    @TypeConverter
    public static List<FillBlankExercise.FillBlankQuestion> toQuestionList(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<List<FillBlankExercise.FillBlankQuestion>>() {}.getType();
        return gson.fromJson(value, listType);
    }
    
    @TypeConverter
    public static String fromQuestionList(List<FillBlankExercise.FillBlankQuestion> list) {
        if (list == null) {
            return null;
        }
        return gson.toJson(list);
    }
}