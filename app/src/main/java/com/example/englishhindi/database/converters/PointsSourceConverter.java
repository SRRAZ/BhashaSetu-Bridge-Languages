package com.example.englishhindi.database.converters;

import androidx.room.TypeConverter;

import com.example.englishhindi.model.gamification.PointsSource;

/**
 * Type converter for PointsSource enum in Room database
 */
public class PointsSourceConverter {
    
    @TypeConverter
    public static PointsSource toPointsSource(String value) {
        return value == null ? null : PointsSource.valueOf(value);
    }
    
    @TypeConverter
    public static String fromPointsSource(PointsSource source) {
        return source == null ? null : source.name();
    }
}