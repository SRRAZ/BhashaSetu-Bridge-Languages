package com.bhashasetu.app.pronunciation;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.bhashasetu.app.audio.AudioAnalyzer;
import com.bhashasetu.app.database.WordRepository;
import com.bhashasetu.app.model.PronunciationSession;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.util.AudioUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PronunciationViewModel extends AndroidViewModel {

    private WordRepository wordRepository;
    private PronunciationSessionRepository sessionRepository;
    private AudioAnalyzer audioAnalyzer;

    private MutableLiveData<List<Word>> pronunciationWords = new MutableLiveData<>();
    private MutableLiveData<PronunciationSession> currentSessionLiveData = new MutableLiveData<>();
    private MutableLiveData<PronunciationScore> currentWordScoreLiveData = new MutableLiveData<>();
    
    private PronunciationSession currentSession;

    public PronunciationViewModel(Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        sessionRepository = new PronunciationSessionRepository(application);
        audioAnalyzer = new AudioAnalyzer();
    }

    public LiveData<List<Word>> getPronunciationWords() {
        return pronunciationWords;
    }
    
    public LiveData<PronunciationSession> getCurrentSessionLiveData() {
        return currentSessionLiveData;
    }
    
    public LiveData<PronunciationScore> getCurrentWordScoreLiveData() {
        return currentWordScoreLiveData;
    }

    public void loadPronunciationWords() {
        AsyncTask.execute(() -> {
            List<Word> words = wordRepository.getAllWords();
            // Filter words suitable for pronunciation practice
            List<Word> practiceWords = filterWordsForPractice(words);
            pronunciationWords.postValue(practiceWords);
        });
    }

    private List<Word> filterWordsForPractice(List<Word> allWords) {
        // In a real app, we might filter based on difficulty, category, etc.
        // For now, just return all words that have English pronunciation
        List<Word> filteredWords = new ArrayList<>();
        for (Word word : allWords) {
            if (word.getEnglishWord() != null && !word.getEnglishWord().isEmpty()) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    public void startNewSession(List<Word> selectedWords) {
        currentSession = new PronunciationSession();
        currentSession.setStartTime(new Date());
        currentSession.setWords(selectedWords);
        
        // Initialize scores array
        List<PronunciationScore> scores = new ArrayList<>();
        for (int i = 0; i < selectedWords.size(); i++) {
            scores.add(null);
        }
        currentSession.setScores(scores);
        
        currentSessionLiveData.setValue(currentSession);
    }

    public void analyzePronunciation(Word word, String referenceAudioPath, String recordingPath) {
        AsyncTask.execute(() -> {
            // Use the audio analyzer to compare the recordings
            double accuracyScore = audioAnalyzer.compareAudioSamples(referenceAudioPath, recordingPath);
            
            // Get phonetic analysis of the recorded pronunciation
            String userPhonetics = AudioUtils.extractPhonetics(recordingPath);
            String referencePhonetics = AudioUtils.getPhoneticSpelling(word.getEnglishWord());
            
            // Create a detailed feedback on phonetic differences
            String phoneticFeedback = generatePhoneticFeedback(referencePhonetics, userPhonetics);
            
            // Calculate individual scores for different aspects
            double rhythmScore = audioAnalyzer.analyzeRhythm(referenceAudioPath, recordingPath);
            double intonationScore = audioAnalyzer.analyzeIntonation(referenceAudioPath, recordingPath);
            
            // Create a comprehensive score object
            PronunciationScore score = new PronunciationScore();
            score.setWord(word);
            score.setAccuracyScore((int) accuracyScore);
            score.setRhythmScore((int) rhythmScore);
            score.setIntonationScore((int) intonationScore);
            score.setPhoneticFeedback(phoneticFeedback);
            score.setRecordingPath(recordingPath);
            score.setTimestamp(new Date());
            
            // Post the score to the LiveData
            currentWordScoreLiveData.postValue(score);
        });
    }

    private String generatePhoneticFeedback(String referencePhonetics, String userPhonetics) {
        // A simplified algorithm to generate feedback on phonetic differences
        // In a real app, this would be more sophisticated
        StringBuilder feedback = new StringBuilder();
        
        // Create similarity matrix for each sound
        String[] refSounds = referencePhonetics.split(" ");
        String[] userSounds = userPhonetics.split(" ");
        
        // Find sounds that need improvement
        List<String> improvementNeeded = new ArrayList<>();
        for (int i = 0; i < refSounds.length && i < userSounds.length; i++) {
            if (!refSounds[i].equals(userSounds[i])) {
                improvementNeeded.add("'" + refSounds[i] + "'");
            }
        }
        
        if (improvementNeeded.isEmpty()) {
            feedback.append("Perfect phonetic pronunciation! All sounds match the reference.");
        } else {
            feedback.append("Focus on improving these sounds: ");
            feedback.append(String.join(", ", improvementNeeded));
        }
        
        return feedback.toString();
    }

    public void saveWordScore(int wordIndex, PronunciationScore score) {
        if (currentSession != null && wordIndex >= 0 && wordIndex < currentSession.getScores().size()) {
            currentSession.getScores().set(wordIndex, score);
            currentSessionLiveData.setValue(currentSession);
        }
    }

    public PronunciationSessionStats calculateSessionStats() {
        if (currentSession == null) {
            return new PronunciationSessionStats();
        }
        
        int totalWords = currentSession.getWords().size();
        int completedWords = 0;
        int totalAccuracyScore = 0;
        int totalRhythmScore = 0;
        int totalIntonationScore = 0;
        
        for (PronunciationScore score : currentSession.getScores()) {
            if (score != null) {
                completedWords++;
                totalAccuracyScore += score.getAccuracyScore();
                totalRhythmScore += score.getRhythmScore();
                totalIntonationScore += score.getIntonationScore();
            }
        }
        
        PronunciationSessionStats stats = new PronunciationSessionStats();
        stats.setTotalWords(totalWords);
        stats.setCompletedWords(completedWords);
        
        if (completedWords > 0) {
            stats.setAverageAccuracyScore(totalAccuracyScore / completedWords);
            stats.setAverageRhythmScore(totalRhythmScore / completedWords);
            stats.setAverageIntonationScore(totalIntonationScore / completedWords);
        }
        
        return stats;
    }

    public void saveSession() {
        if (currentSession != null) {
            currentSession.setEndTime(new Date());
            AsyncTask.execute(() -> {
                sessionRepository.insertSession(currentSession);
            });
        }
    }
}