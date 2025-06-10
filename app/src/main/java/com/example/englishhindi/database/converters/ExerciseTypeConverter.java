package com.bhashasetu.app.database.converters;

import androidx.room.TypeConverter;

import com.bhashasetu.app.model.exercise.ExerciseType;

/**
 * Type converter for ExerciseType enum in Room database
 */
public class ExerciseTypeConverter {
    
    @TypeConverter
    public static ExerciseType toExerciseType(String value) {
        return value == null ? null : ExerciseType.valueOf(value);
    }
    
    @TypeConverter
    public static String fromExerciseType(ExerciseType type) {
        return type == null ? null : type.name();
    }
}