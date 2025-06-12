package com.bhashasetu.app.model.exercise;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.QuestionListConverter;
import com.bhashasetu.app.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Multiple choice quiz exercise
 */
@Entity(tableName = "multiple_choice_exercises")
@TypeConverters({DateConverter.class, QuestionListConverter.class})
public class MultipleChoiceExercise extends Exercise {
    
    private List<MultipleChoiceQuestion> questions;
    private int currentQuestionIndex;
    private boolean showHindiFirst;
    
    public MultipleChoiceExercise() {
        super();
        this.setType(ExerciseType.MULTIPLE_CHOICE);
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.showHindiFirst = false;
    }
    
    public MultipleChoiceExercise(String title, int difficulty, int points, boolean showHindiFirst) {
        super(title, ExerciseType.MULTIPLE_CHOICE, difficulty, 0, points);
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.showHindiFirst = showHindiFirst;
    }
    
    /**
     * Generate questions from a list of words
     * @param words words to create questions from
     * @param optionsPerQuestion number of options per question
     */
    public void generateQuestionsFromWords(List<Word> words, int optionsPerQuestion) {
        if (words.size() < optionsPerQuestion) {
            throw new IllegalArgumentException("Not enough words to generate options");
        }
        
        this.questions.clear();
        this.setWordCount(words.size());
        this.setTotalQuestions(words.size());
        
        // Generate a question for each word
        for (Word correctWord : words) {
            // Create list of possible words for options (excluding the correct one)
            List<Word> otherWords = new ArrayList<>(words);
            otherWords.remove(correctWord);
            
            // Randomly select options from other words
            Collections.shuffle(otherWords);
            List<Word> selectedOptions = otherWords.subList(0, optionsPerQuestion - 1);
            
            // Create options list with the correct answer
            List<MultipleChoiceOption> options = new ArrayList<>();
            for (Word option : selectedOptions) {
                options.add(new MultipleChoiceOption(
                        option.getEnglishWord(),
                        option.getHindiWord(),
                        false
                ));
            }
            
            // Add the correct option
            options.add(new MultipleChoiceOption(
                    correctWord.getEnglishWord(),
                    correctWord.getHindiWord(),
                    true
            ));
            
            // Shuffle options
            Collections.shuffle(options);
            
            // Create the question
            MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                    correctWord.getId(),
                    correctWord.getEnglishWord(),
                    correctWord.getHindiWord(),
                    showHindiFirst,
                    options
            );
            
            questions.add(question);
        }
        
        // Shuffle all questions
        Collections.shuffle(questions);
    }
    
    public List<MultipleChoiceQuestion> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<MultipleChoiceQuestion> questions) {
        this.questions = questions;
    }
    
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }
    
    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }
    
    public boolean isShowHindiFirst() {
        return showHindiFirst;
    }
    
    public void setShowHindiFirst(boolean showHindiFirst) {
        this.showHindiFirst = showHindiFirst;
    }
    
    /**
     * Get the current question
     * @return current question or null if no more questions
     */
    public MultipleChoiceQuestion getCurrentQuestion() {
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
     * @param optionIndex index of the selected option
     * @return true if the answer is correct
     */
    public boolean answerCurrentQuestion(int optionIndex) {
        MultipleChoiceQuestion question = getCurrentQuestion();
        if (question == null || optionIndex < 0 || optionIndex >= question.getOptions().size()) {
            return false;
        }
        
        boolean isCorrect = question.getOptions().get(optionIndex).isCorrect();
        question.setAnswered(true);
        question.setSelectedOptionIndex(optionIndex);
        
        // Record the result
        recordAnswer(isCorrect);
        
        return isCorrect;
    }
    
    /**
     * Check if all questions have been answered
     * @return true if all questions have been answered
     */
    public boolean areAllQuestionsAnswered() {
        for (MultipleChoiceQuestion question : questions) {
            if (!question.isAnswered()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Nested class for multiple choice questions
     */
    public static class MultipleChoiceQuestion {
        private int wordId;
        private String englishWord;
        private String hindiWord;
        private boolean showHindiFirst;
        private List<MultipleChoiceOption> options;
        private boolean answered;
        private int selectedOptionIndex;
        
        @Ignore
        public MultipleChoiceQuestion() {
            this.options = new ArrayList<>();
            this.answered = false;
            this.selectedOptionIndex = -1;
        }
        
        public MultipleChoiceQuestion(int wordId, String englishWord, String hindiWord, 
                                      boolean showHindiFirst, List<MultipleChoiceOption> options) {
            this.wordId = wordId;
            this.englishWord = englishWord;
            this.hindiWord = hindiWord;
            this.showHindiFirst = showHindiFirst;
            this.options = options;
            this.answered = false;
            this.selectedOptionIndex = -1;
        }
        
        public int getWordId() {
            return wordId;
        }
        
        public void setWordId(int wordId) {
            this.wordId = wordId;
        }
        
        public String getEnglishWord() {
            return englishWord;
        }
        
        public void setEnglishWord(String englishWord) {
            this.englishWord = englishWord;
        }
        
        public String getHindiWord() {
            return hindiWord;
        }
        
        public void setHindiWord(String hindiWord) {
            this.hindiWord = hindiWord;
        }
        
        public boolean isShowHindiFirst() {
            return showHindiFirst;
        }
        
        public void setShowHindiFirst(boolean showHindiFirst) {
            this.showHindiFirst = showHindiFirst;
        }
        
        public List<MultipleChoiceOption> getOptions() {
            return options;
        }
        
        public void setOptions(List<MultipleChoiceOption> options) {
            this.options = options;
        }
        
        public boolean isAnswered() {
            return answered;
        }
        
        public void setAnswered(boolean answered) {
            this.answered = answered;
        }
        
        public int getSelectedOptionIndex() {
            return selectedOptionIndex;
        }
        
        public void setSelectedOptionIndex(int selectedOptionIndex) {
            this.selectedOptionIndex = selectedOptionIndex;
        }
        
        /**
         * Get the prompt to display for this question
         * @return the word to translate (English or Hindi based on settings)
         */
        public String getPrompt() {
            return showHindiFirst ? hindiWord : englishWord;
        }
        
        /**
         * Get the expected answer for this question
         * @return the expected translation
         */
        public String getExpectedAnswer() {
            return showHindiFirst ? englishWord : hindiWord;
        }
        
        /**
         * Get option display text (English or Hindi based on settings)
         * @return list of option text to display
         */
        public List<String> getDisplayOptions() {
            List<String> displayOptions = new ArrayList<>();
            for (MultipleChoiceOption option : options) {
                displayOptions.add(showHindiFirst ? option.getEnglishWord() : option.getHindiWord());
            }
            return displayOptions;
        }
        
        /**
         * Check if the selected answer is correct
         * @return true if correct answer was selected
         */
        public boolean isCorrectAnswerSelected() {
            if (!answered || selectedOptionIndex < 0 || selectedOptionIndex >= options.size()) {
                return false;
            }
            return options.get(selectedOptionIndex).isCorrect();
        }
    }
    
    /**
     * Nested class for multiple choice options
     */
    public static class MultipleChoiceOption {
        private String englishWord;
        private String hindiWord;
        private boolean isCorrect;
        
        @Ignore
        public MultipleChoiceOption() {
        }
        
        public MultipleChoiceOption(String englishWord, String hindiWord, boolean isCorrect) {
            this.englishWord = englishWord;
            this.hindiWord = hindiWord;
            this.isCorrect = isCorrect;
        }
        
        public String getEnglishWord() {
            return englishWord;
        }
        
        public void setEnglishWord(String englishWord) {
            this.englishWord = englishWord;
        }
        
        public String getHindiWord() {
            return hindiWord;
        }
        
        public void setHindiWord(String hindiWord) {
            this.hindiWord = hindiWord;
        }
        
        public boolean isCorrect() {
            return isCorrect;
        }
        
        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }
    }
}