package com.example.englishhindi.database;

import androidx.room.TypeConverter;

import com.example.englishhindi.model.Word;
import com.example.englishhindi.pronunciation.PronunciationScore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Type converters for Room database.
 * Handles conversions between complex objects and primitive types that can be stored in the database.
 */
public class Converters {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromStringList(List<String> strings) {
        return strings == null ? null : gson.toJson(strings);
    }

    @TypeConverter
    public static List<String> toStringList(String json) {
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String fromIntList(List<Integer> ints) {
        return ints == null ? null : gson.toJson(ints);
    }

    @TypeConverter
    public static List<Integer> toIntList(String json) {
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String fromWordList(List<Word> words) {
        return words == null ? null : gson.toJson(words);
    }

    @TypeConverter
    public static List<Word> toWordList(String json) {
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<Word>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String fromPronunciationScoreList(List<PronunciationScore> scores) {
        return scores == null ? null : gson.toJson(scores);
    }

    @TypeConverter
    public static List<PronunciationScore> toPronunciationScoreList(String json) {
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<PronunciationScore>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String fromWord(Word word) {
        return word == null ? null : gson.toJson(word);
    }

    @TypeConverter
    public static Word toWord(String json) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, Word.class);
    }

    @TypeConverter
    public static String fromLongList(List<Long> longs) {
        return longs == null ? null : gson.toJson(longs);
    }

    @TypeConverter
    public static List<Long> toLongList(String json) {
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<Long>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String fromDoubleList(List<Double> doubles) {
        return doubles == null ? null : gson.toJson(doubles);
    }

    @TypeConverter
    public static List<Double> toDoubleList(String json) {
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<Double>>() {}.getType();
        return gson.fromJson(json, type);
    }
}