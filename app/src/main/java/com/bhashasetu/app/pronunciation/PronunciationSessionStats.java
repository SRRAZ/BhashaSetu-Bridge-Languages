package com.bhashasetu.app.pronunciation;

/**
 * Class containing statistics for a completed pronunciation practice session.
 * Used for feedback and progress tracking.
 */
public class PronunciationSessionStats {
    
    private int totalWords;
    private int completedWords;
    private int averageAccuracyScore;
    private int averageRhythmScore;
    private int averageIntonationScore;
    private double sessionDurationMinutes;
    private int improvementFromLastSession;
    
    public PronunciationSessionStats() {
        // Initialize with default values
        this.totalWords = 0;
        this.completedWords = 0;
        this.averageAccuracyScore = 0;
        this.averageRhythmScore = 0;
        this.averageIntonationScore = 0;
        this.sessionDurationMinutes = 0.0;
        this.improvementFromLastSession = 0;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public int getCompletedWords() {
        return completedWords;
    }

    public void setCompletedWords(int completedWords) {
        this.completedWords = completedWords;
    }

    public int getAverageAccuracyScore() {
        return averageAccuracyScore;
    }

    public void setAverageAccuracyScore(int averageAccuracyScore) {
        this.averageAccuracyScore = averageAccuracyScore;
    }

    public int getAverageRhythmScore() {
        return averageRhythmScore;
    }

    public void setAverageRhythmScore(int averageRhythmScore) {
        this.averageRhythmScore = averageRhythmScore;
    }

    public int getAverageIntonationScore() {
        return averageIntonationScore;
    }

    public void setAverageIntonationScore(int averageIntonationScore) {
        this.averageIntonationScore = averageIntonationScore;
    }

    public double getSessionDurationMinutes() {
        return sessionDurationMinutes;
    }

    public void setSessionDurationMinutes(double sessionDurationMinutes) {
        this.sessionDurationMinutes = sessionDurationMinutes;
    }

    public int getImprovementFromLastSession() {
        return improvementFromLastSession;
    }

    public void setImprovementFromLastSession(int improvementFromLastSession) {
        this.improvementFromLastSession = improvementFromLastSession;
    }
    
    /**
     * Calculate completion percentage for the session
     * @return Percentage of words completed (0-100)
     */
    public int getCompletionPercentage() {
        if (totalWords == 0) {
            return 0;
        }
        return (completedWords * 100) / totalWords;
    }
    
    /**
     * Get the average overall score, weighted by the importance of each component
     * @return Weighted average score (0-100)
     */
    public int getOverallAverageScore() {
        return (int)(averageAccuracyScore * 0.6 + averageRhythmScore * 0.2 + averageIntonationScore * 0.2);
    }
    
    /**
     * Get a qualitative rating for the overall session
     * @return String representing the qualitative rating
     */
    public String getQualitativeRating() {
        int overallScore = getOverallAverageScore();
        
        if (overallScore >= 90) {
            return "Excellent";
        } else if (overallScore >= 80) {
            return "Very Good";
        } else if (overallScore >= 70) {
            return "Good";
        } else if (overallScore >= 60) {
            return "Fair";
        } else if (overallScore >= 50) {
            return "Needs Practice";
        } else {
            return "Keep Practicing";
        }
    }
    
    /**
     * Check if this session deserves a special achievement
     * @return true if an achievement should be awarded
     */
    public boolean deservesPerfectPronunciationAchievement() {
        return completedWords >= 5 && averageAccuracyScore >= 90;
    }
    
    /**
     * Check if this session deserves a dedication achievement
     * @return true if a dedication achievement should be awarded
     */
    public boolean deservesDedicationAchievement() {
        return completedWords >= 10;
    }
}