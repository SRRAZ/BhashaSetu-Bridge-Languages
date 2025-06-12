package com.bhashasetu.app.model;

import com.bhashasetu.app.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Registry of all available achievements in the app.
 * This class provides factory methods to create all standard achievements.
 */
public class AchievementRegistry {

    // Achievement IDs
    // Vocabulary Achievements
    public static final String VOCAB_NOVICE = "vocab_novice";
    public static final String VOCAB_APPRENTICE = "vocab_apprentice";
    public static final String VOCAB_SCHOLAR = "vocab_scholar";
    public static final String VOCAB_MASTER = "vocab_master";
    
    // Pronunciation Achievements
    public static final String PRONUNCIATION_BEGINNER = "pronunciation_beginner";
    public static final String PRONUNCIATION_SPEAKER = "pronunciation_speaker";
    public static final String PRONUNCIATION_EXPERT = "pronunciation_expert";
    
    // Streak Achievements
    public static final String STREAK_STARTER = "streak_starter";
    public static final String STREAK_CONSISTENT = "streak_consistent";
    public static final String STREAK_DEDICATED = "streak_dedicated";
    
    // Game Achievements
    public static final String GAME_PLAYER = "game_player";
    public static final String GAME_ENTHUSIAST = "game_enthusiast";
    public static final String GAME_CHAMPION = "game_champion";
    public static final String WORD_SCRAMBLE_MASTER = "word_scramble_master";
    public static final String PICTURE_MATCH_EXPERT = "picture_match_expert";
    public static final String FLASHCARD_GURU = "flashcard_guru";
    public static final String PERFECT_SCORE = "perfect_score";
    
    // Mastery Achievements
    public static final String MASTERY_BEGINNER = "mastery_beginner";
    public static final String MASTERY_INTERMEDIATE = "mastery_intermediate";
    public static final String MASTERY_ADVANCED = "mastery_advanced";
    
    // Special Achievements
    public static final String MULTILINGUAL = "multilingual";
    public static final String NIGHT_OWL = "night_owl";
    public static final String EARLY_BIRD = "early_bird";
    
    /**
     * Create all standard achievements.
     * 
     * @return List of all achievements
     */
    public static List<Achievement> createAllAchievements() {
        List<Achievement> achievements = new ArrayList<>();
        
        // Add vocabulary achievements
        achievements.add(createVocabNovice());
        achievements.add(createVocabApprentice());
        achievements.add(createVocabScholar());
        achievements.add(createVocabMaster());
        
        // Add pronunciation achievements
        achievements.add(createPronunciationBeginner());
        achievements.add(createPronunciationSpeaker());
        achievements.add(createPronunciationExpert());
        
        // Add streak achievements
        achievements.add(createStreakStarter());
        achievements.add(createStreakConsistent());
        achievements.add(createStreakDedicated());
        
        // Add game achievements
        achievements.add(createGamePlayer());
        achievements.add(createGameEnthusiast());
        achievements.add(createGameChampion());
        achievements.add(createWordScrambleMaster());
        achievements.add(createPictureMatchExpert());
        achievements.add(createFlashcardGuru());
        achievements.add(createPerfectScore());
        
        // Add mastery achievements
        achievements.add(createMasteryBeginner());
        achievements.add(createMasteryIntermediate());
        achievements.add(createMasteryAdvanced());
        
        // Add special achievements
        achievements.add(createMultilingual());
        achievements.add(createNightOwl());
        achievements.add(createEarlyBird());
        
        return achievements;
    }
    
    /**
     * Create the "Vocabulary Novice" achievement.
     */
    public static Achievement createVocabNovice() {
        return new Achievement(
                VOCAB_NOVICE,
                "Vocabulary Novice",
                "Learn 10 new words",
                Achievement.TYPE_VOCABULARY,
                R.drawable.achievement_vocab_novice,
                10,
                10,
                false
        );
    }
    
    /**
     * Create the "Vocabulary Apprentice" achievement.
     */
    public static Achievement createVocabApprentice() {
        return new Achievement(
                VOCAB_APPRENTICE,
                "Vocabulary Apprentice",
                "Learn 50 new words",
                Achievement.TYPE_VOCABULARY,
                R.drawable.achievement_vocab_apprentice,
                25,
                50,
                false
        );
    }
    
    /**
     * Create the "Vocabulary Scholar" achievement.
     */
    public static Achievement createVocabScholar() {
        return new Achievement(
                VOCAB_SCHOLAR,
                "Vocabulary Scholar",
                "Learn 200 new words",
                Achievement.TYPE_VOCABULARY,
                R.drawable.achievement_vocab_scholar,
                50,
                200,
                false
        );
    }
    
    /**
     * Create the "Vocabulary Master" achievement.
     */
    public static Achievement createVocabMaster() {
        return new Achievement(
                VOCAB_MASTER,
                "Vocabulary Master",
                "Learn 500 new words",
                Achievement.TYPE_VOCABULARY,
                R.drawable.achievement_vocab_master,
                100,
                500,
                false
        );
    }
    
    /**
     * Create the "Pronunciation Beginner" achievement.
     */
    public static Achievement createPronunciationBeginner() {
        return new Achievement(
                PRONUNCIATION_BEGINNER,
                "Pronunciation Beginner",
                "Practice pronunciation 10 times",
                Achievement.TYPE_PRONUNCIATION,
                R.drawable.achievement_pronunciation_beginner,
                10,
                10,
                false
        );
    }
    
    /**
     * Create the "Pronunciation Speaker" achievement.
     */
    public static Achievement createPronunciationSpeaker() {
        return new Achievement(
                PRONUNCIATION_SPEAKER,
                "Pronunciation Speaker",
                "Practice pronunciation 50 times",
                Achievement.TYPE_PRONUNCIATION,
                R.drawable.achievement_pronunciation_speaker,
                25,
                50,
                false
        );
    }
    
    /**
     * Create the "Pronunciation Expert" achievement.
     */
    public static Achievement createPronunciationExpert() {
        return new Achievement(
                PRONUNCIATION_EXPERT,
                "Pronunciation Expert",
                "Score 90% or higher on 25 pronunciation practices",
                Achievement.TYPE_PRONUNCIATION,
                R.drawable.achievement_pronunciation_expert,
                50,
                25,
                false
        );
    }
    
    /**
     * Create the "Streak Starter" achievement.
     */
    public static Achievement createStreakStarter() {
        return new Achievement(
                STREAK_STARTER,
                "Streak Starter",
                "Practice 3 days in a row",
                Achievement.TYPE_STREAK,
                R.drawable.achievement_streak_starter,
                10,
                3,
                false
        );
    }
    
    /**
     * Create the "Streak Consistent" achievement.
     */
    public static Achievement createStreakConsistent() {
        return new Achievement(
                STREAK_CONSISTENT,
                "Streak Consistent",
                "Practice 7 days in a row",
                Achievement.TYPE_STREAK,
                R.drawable.achievement_streak_consistent,
                25,
                7,
                false
        );
    }
    
    /**
     * Create the "Streak Dedicated" achievement.
     */
    public static Achievement createStreakDedicated() {
        return new Achievement(
                STREAK_DEDICATED,
                "Streak Dedicated",
                "Practice 30 days in a row",
                Achievement.TYPE_STREAK,
                R.drawable.achievement_streak_dedicated,
                100,
                30,
                false
        );
    }
    
    /**
     * Create the "Game Player" achievement.
     */
    public static Achievement createGamePlayer() {
        return new Achievement(
                GAME_PLAYER,
                "Game Player",
                "Play 5 vocabulary games",
                Achievement.TYPE_GAME,
                R.drawable.achievement_game_player,
                10,
                5,
                false
        );
    }
    
    /**
     * Create the "Game Enthusiast" achievement.
     */
    public static Achievement createGameEnthusiast() {
        return new Achievement(
                GAME_ENTHUSIAST,
                "Game Enthusiast",
                "Play 25 vocabulary games",
                Achievement.TYPE_GAME,
                R.drawable.achievement_game_enthusiast,
                25,
                25,
                false
        );
    }
    
    /**
     * Create the "Game Champion" achievement.
     */
    public static Achievement createGameChampion() {
        return new Achievement(
                GAME_CHAMPION,
                "Game Champion",
                "Win 10 vocabulary games with a score of 100 or higher",
                Achievement.TYPE_GAME,
                R.drawable.achievement_game_champion,
                50,
                10,
                false
        );
    }
    
    /**
     * Create the "Word Scramble Master" achievement.
     */
    public static Achievement createWordScrambleMaster() {
        return new Achievement(
                WORD_SCRAMBLE_MASTER,
                "Word Scramble Master",
                "Unscramble 50 words correctly",
                Achievement.TYPE_GAME,
                R.drawable.achievement_word_scramble,
                25,
                50,
                false
        );
    }
    
    /**
     * Create the "Picture Match Expert" achievement.
     */
    public static Achievement createPictureMatchExpert() {
        return new Achievement(
                PICTURE_MATCH_EXPERT,
                "Picture Match Expert",
                "Match 50 pictures with their words correctly",
                Achievement.TYPE_GAME,
                R.drawable.achievement_picture_match,
                25,
                50,
                false
        );
    }
    
    /**
     * Create the "Flashcard Guru" achievement.
     */
    public static Achievement createFlashcardGuru() {
        return new Achievement(
                FLASHCARD_GURU,
                "Flashcard Guru",
                "Study 100 flashcards",
                Achievement.TYPE_GAME,
                R.drawable.achievement_flashcard,
                25,
                100,
                false
        );
    }
    
    /**
     * Create the "Perfect Score" achievement.
     */
    public static Achievement createPerfectScore() {
        return new Achievement(
                PERFECT_SCORE,
                "Perfect Score",
                "Get a perfect score in any game",
                Achievement.TYPE_GAME,
                R.drawable.achievement_perfect_score,
                50,
                1,
                false
        );
    }
    
    /**
     * Create the "Mastery Beginner" achievement.
     */
    public static Achievement createMasteryBeginner() {
        return new Achievement(
                MASTERY_BEGINNER,
                "Mastery Beginner",
                "Reach 50% mastery in 10 words",
                Achievement.TYPE_MASTERY,
                R.drawable.achievement_mastery_beginner,
                15,
                10,
                false
        );
    }
    
    /**
     * Create the "Mastery Intermediate" achievement.
     */
    public static Achievement createMasteryIntermediate() {
        return new Achievement(
                MASTERY_INTERMEDIATE,
                "Mastery Intermediate",
                "Reach 75% mastery in 25 words",
                Achievement.TYPE_MASTERY,
                R.drawable.achievement_mastery_intermediate,
                30,
                25,
                false
        );
    }
    
    /**
     * Create the "Mastery Advanced" achievement.
     */
    public static Achievement createMasteryAdvanced() {
        return new Achievement(
                MASTERY_ADVANCED,
                "Mastery Advanced",
                "Reach 100% mastery in 50 words",
                Achievement.TYPE_MASTERY,
                R.drawable.achievement_mastery_advanced,
                75,
                50,
                false
        );
    }
    
    /**
     * Create the "Multilingual" achievement.
     */
    public static Achievement createMultilingual() {
        return new Achievement(
                MULTILINGUAL,
                "Multilingual",
                "Switch between English and Hindi interfaces 5 times",
                Achievement.TYPE_MASTERY,
                R.drawable.achievement_multilingual,
                15,
                5,
                true
        );
    }
    
    /**
     * Create the "Night Owl" achievement.
     */
    public static Achievement createNightOwl() {
        return new Achievement(
                NIGHT_OWL,
                "Night Owl",
                "Study after 10pm for 5 days",
                Achievement.TYPE_STREAK,
                R.drawable.achievement_night_owl,
                20,
                5,
                true
        );
    }
    
    /**
     * Create the "Early Bird" achievement.
     */
    public static Achievement createEarlyBird() {
        return new Achievement(
                EARLY_BIRD,
                "Early Bird",
                "Study before 8am for 5 days",
                Achievement.TYPE_STREAK,
                R.drawable.achievement_early_bird,
                20,
                5,
                true
        );
    }
}