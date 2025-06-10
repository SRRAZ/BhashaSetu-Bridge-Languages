package com.bhashasetu.app.database.converters;

import androidx.room.TypeConverter;

import com.bhashasetu.app.model.exercise.MatchingExercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Type converter for matching exercise items in Room database
 */
public class MatchingItemListConverter {
    
    private static final Gson gson = new Gson();
    
    @TypeConverter
    public static List<MatchingExercise.MatchingItem> toMatchingItemList(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<List<MatchingExercise.MatchingItem>>() {}.getType();
        return gson.fromJson(value, listType);
    }
    
    @TypeConverter
    public static String fromMatchingItemList(List<MatchingExercise.MatchingItem> list) {
        if (list == null) {
            return null;
        }
        return gson.toJson(list);
    }
    
    @TypeConverter
    public static Map<Integer, Integer> toMatchMap(String value) {
        if (value == null) {
            return null;
        }
        Type mapType = new TypeToken<Map<Integer, Integer>>() {}.getType();
        return gson.fromJson(value, mapType);
    }
    
    @TypeConverter
    public static String fromMatchMap(Map<Integer, Integer> map) {
        if (map == null) {
            return null;
        }
        return gson.toJson(map);
    }
}