package com.bhashasetu.app.pronunciation;

/**
 * Class to hold comprehensive pronunciation progress statistics for a user.
 * Used for displaying overall progress in the app.
 */
public class PronunciationProgressStats {
    
    private int sessionCount;
    private int wordAttempts;
    private double averageAccuracyScore;
    private double bestAccuracyScore;
    private int uniqueWordsPracticed;
    
    public PronunciationProgressStats() {
        // Default constructor
    }
    
    public PronunciationProgressStats(int sessionCount, int wordAttempts, 
                                     double averageAccuracyScore, double bestAccuracyScore, 
                                     int uniqueWordsPracticed) {
        this.sessionCount = sessionCount;
        this.wordAttempts = wordAttempts;
        this.averageAccuracyScore = averageAccuracyScore;
        this.bestAccuracyScore = bestAccuracyScore;
        this.uniqueWordsPracticed = uniqueWordsPracticed;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }

    public int getWordAttempts() {
        return wordAttempts;
    }

    public void setWordAttempts(int wordAttempts) {
        this.wordAttempts = wordAttempts;
    }

    public double getAverageAccuracyScore() {
        return averageAccuracyScore;
    }

    public void setAverageAccuracyScore(double averageAccuracyScore) {
        this.averageAccuracyScore = averageAccuracyScore;
    }

    public double getBestAccuracyScore() {
        return bestAccuracyScore;
    }

    public void setBestAccuracyScore(double bestAccuracyScore) {
        this.bestAccuracyScore = bestAccuracyScore;
    }

    public int getUniqueWordsPracticed() {
        return uniqueWordsPracticed;
    }

    public void setUniqueWordsPracticed(int uniqueWordsPracticed) {
        this.uniqueWordsPracticed = uniqueWordsPracticed;
    }
    
    /**
     * Calculate the average words per session
     * @return Average number of words practiced per session
     */
    public double getAverageWordsPerSession() {
        if (sessionCount == 0) {
            return 0;
        }
        return (double) wordAttempts / sessionCount;
    }
    
    /**
     * Get a descriptive proficiency level based on overall stats
     * @return String representing the user's proficiency level
     */
    public String getProficiencyLevel() {
        // Calculate a combined metric considering:
        // - Number of unique words practiced
        // - Average accuracy
        // - Total sessions (indicating practice dedication)
        
        double proficiencyScore = 
            (uniqueWordsPracticed * 0.5) +  // More unique words = higher proficiency
            (averageAccuracyScore * 0.3) +  // Higher accuracy = higher proficiency
            (sessionCount * 2.0);           // More practice = higher proficiency
        
        if (proficiencyScore >= 200) {
            return "Master";
        } else if (proficiencyScore >= 100) {
            return "Advanced";
        } else if (proficiencyScore >= 50) {
            return "Intermediate";
        } else if (proficiencyScore >= 20) {
            return "Beginner";
        } else {
            return "Novice";
        }
    }
    
    /**
     * Get a numeric representation of overall progress (0-100)
     * @return Progress percentage
     */
    public int getOverallProgressPercentage() {
        // This is a simplified calculation and would be more sophisticated in a real app
        
        // Weight the different components
        double progress = 
            (Math.min(uniqueWordsPracticed, 100) * 0.4) +  // Cap at 100 words
            (averageAccuracyScore * 0.4) +                 // Average accuracy (0-100)
            (Math.min(sessionCount, 50) * 0.4);            // Cap at 50 sessions
        
        // Scale to 0-100 range
        return (int) Math.min(100, progress);
    }
}