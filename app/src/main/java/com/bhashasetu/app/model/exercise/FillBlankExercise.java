package com.bhashasetu.app.model.exercise;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.FillBlankQuestionListConverter;
import com.bhashasetu.app.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fill-in-the-blank exercise
 */
@Entity(tableName = "fill_blank_exercises")
@TypeConverters({DateConverter.class, FillBlankQuestionListConverter.class})
public class FillBlankExercise extends Exercise {
    
    private List<FillBlankQuestion> questions;
    private int currentQuestionIndex;
    private boolean showHindiSentence;

    public FillBlankExercise(String title, int difficulty, int points, boolean showHindiSentence) {
        super(title, ExerciseType.FILL_BLANK, difficulty, 0, points);
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.showHindiSentence = showHindiSentence;
    }
    
    /**
     * Generate questions from a list of words with example sentences
     * @param words words to create questions from
     */
    public void generateQuestionsFromWords(List<Word> words) {
        this.questions.clear();
        this.setWordCount(words.size());
        this.setTotalQuestions(words.size());
        
        // Filter words with example sentences
        List<Word> wordsWithSentences = new ArrayList<>();
        for (Word word : words) {
            if (showHindiSentence) {
                if (word.getExampleSentenceHindi() != null && !word.getExampleSentenceHindi().isEmpty()) {
                    wordsWithSentences.add(word);
                }
            } else {
                if (word.getExampleSentenceEnglish() != null && !word.getExampleSentenceEnglish().isEmpty()) {
                    wordsWithSentences.add(word);
                }
            }
        }
        
        // If no words with sentences, return
        if (wordsWithSentences.isEmpty()) {
            return;
        }
        
        // Create questions for each word
        for (Word word : wordsWithSentences) {
            String sentence = showHindiSentence ? 
                    word.getExampleSentenceHindi() : 
                    word.getExampleSentenceEnglish();
            
            String targetWord = showHindiSentence ? 
                    word.getHindiWord() : 
                    word.getEnglishWord();
            
            // Create blank by replacing the word with underscores
            String sentenceWithBlank = createSentenceWithBlank(sentence, targetWord);
            
            // Create the question
            FillBlankQuestion question = new FillBlankQuestion(
                    word.getId(),
                    sentence,
                    sentenceWithBlank,
                    targetWord,
                    showHindiSentence ? word.getEnglishWord() : word.getHindiWord(), // hint is the word in other language
                    showHindiSentence
            );
            
            questions.add(question);
        }
        
        // Shuffle questions
        Collections.shuffle(questions);
        
        // Update total questions count to match filtered words
        this.setTotalQuestions(questions.size());
        this.setWordCount(questions.size());
    }
    
    /**
     * Create a sentence with the target word replaced by blanks
     * @param sentence the full sentence
     * @param targetWord the word to replace with blanks
     * @return sentence with blanks
     */
    private String createSentenceWithBlank(String sentence, String targetWord) {
        // Simple replacement - in a real app, this would be more sophisticated
        // to handle word boundaries, case sensitivity, etc.
        return sentence.replace(targetWord, "_____");
    }
    
    public List<FillBlankQuestion> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<FillBlankQuestion> questions) {
        this.questions = questions;
    }
    
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }
    
    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }
    
    public boolean isShowHindiSentence() {
        return showHindiSentence;
    }
    
    public void setShowHindiSentence(boolean showHindiSentence) {
        this.showHindiSentence = showHindiSentence;
    }
    
    /**
     * Get the current question
     * @return current question or null if no more questions
     */
    public FillBlankQuestion getCurrentQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }
    
    /**
     * Move to the next question
     * @return true if there is a next question, false if at the end
     */
    public boolean moveToNextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            return true;
        }
        return false;
    }
    
    /**
     * Move to the previous question
     * @return true if there is a previous question, false if at the beginning
     */
    public boolean moveToPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            return true;
        }
        return false;
    }
    
    /**
     * Answer the current question
     * @param answer the user's answer
     * @return true if the answer is correct
     */
    public boolean answerCurrentQuestion(String answer) {
        FillBlankQuestion question = getCurrentQuestion();
        if (question == null || answer == null) {
            return false;
        }
        
        question.setUserAnswer(answer);
        question.setAnswered(true);
        
        boolean isCorrect = question.isCorrect();
        recordAnswer(isCorrect);
        
        return isCorrect;
    }
    
    /**
     * Check if all questions have been answered
     * @return true if all questions have been answered
     */
    public boolean areAllQuestionsAnswered() {
        for (FillBlankQuestion question : questions) {
            if (!question.isAnswered()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Nested class for fill-in-the-blank questions
     */
    public static class FillBlankQuestion {
        private int wordId;
        private String originalSentence;
        private String sentenceWithBlank;
        private String correctAnswer;
        private String hint;
        private boolean isHindi;
        private String userAnswer;
        private boolean answered;
        
        @Ignore
        public FillBlankQuestion() {
            this.answered = false;
        }
        
        public FillBlankQuestion(int wordId, String originalSentence, String sentenceWithBlank, 
                                 String correctAnswer, String hint, boolean isHindi) {
            this.wordId = wordId;
            this.originalSentence = originalSentence;
            this.sentenceWithBlank = sentenceWithBlank;
            this.correctAnswer = correctAnswer;
            this.hint = hint;
            this.isHindi = isHindi;
            this.answered = false;
        }
        
        public int getWordId() {
            return wordId;
        }
        
        public void setWordId(int wordId) {
            this.wordId = wordId;
        }
        
        public String getOriginalSentence() {
            return originalSentence;
        }
        
        public void setOriginalSentence(String originalSentence) {
            this.originalSentence = originalSentence;
        }
        
        public String getSentenceWithBlank() {
            return sentenceWithBlank;
        }
        
        public void setSentenceWithBlank(String sentenceWithBlank) {
            this.sentenceWithBlank = sentenceWithBlank;
        }
        
        public String getCorrectAnswer() {
            return correctAnswer;
        }
        
        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }
        
        public String getHint() {
            return hint;
        }
        
        public void setHint(String hint) {
            this.hint = hint;
        }
        
        public boolean isHindi() {
            return isHindi;
        }
        
        public void setHindi(boolean hindi) {
            isHindi = hindi;
        }
        
        public String getUserAnswer() {
            return userAnswer;
        }
        
        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }
        
        public boolean isAnswered() {
            return answered;
        }
        
        public void setAnswered(boolean answered) {
            this.answered = answered;
        }
        
        /**
         * Check if the user's answer is correct
         * @return true if correct
         */
        public boolean isCorrect() {
            if (!answered || userAnswer == null) {
                return false;
            }
            
            // Simple comparison - in a real app, this would be more sophisticated
            // to handle case sensitivity, extra spaces, etc.
            return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
        }
    }
}