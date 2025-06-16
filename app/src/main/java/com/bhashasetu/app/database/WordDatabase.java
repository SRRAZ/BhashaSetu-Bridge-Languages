package com.bhashasetu.app.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bhashasetu.app.model.Achievement;
import com.bhashasetu.app.model.DeckWord;
import com.bhashasetu.app.model.FlashcardDeck;
import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.model.LessonWord;
import com.bhashasetu.app.model.PracticeSession;
import com.bhashasetu.app.model.Quiz;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.database.converters.AchievementTypeConverter;
import com.bhashasetu.app.database.converters.BadgeTierConverter;
import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.ExerciseTypeConverter;
import com.bhashasetu.app.database.converters.FillBlankQuestionListConverter;
import com.bhashasetu.app.database.converters.MatchingItemListConverter;
import com.bhashasetu.app.database.converters.PointsSourceConverter;
import com.bhashasetu.app.database.converters.QuestionListConverter;
import com.bhashasetu.app.model.exercise.Exercise;
import com.bhashasetu.app.model.exercise.FillBlankExercise;
import com.bhashasetu.app.model.exercise.MatchingExercise;
import com.bhashasetu.app.model.exercise.MultipleChoiceExercise;
//import com.bhashasetu.app.model.gamification.Achievement;
import com.bhashasetu.app.model.gamification.Badge;
import com.bhashasetu.app.model.gamification.UserLevel;
import com.bhashasetu.app.model.gamification.UserPoints;
import com.bhashasetu.app.model.gamification.UserStats;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Room database for the app
 */

@Database(entities = {
            Word.class, 
            Lesson.class, 
            LessonWord.class, 
            UserProgress.class, 
            Quiz.class, 
            FlashcardDeck.class, 
            DeckWord.class, 
            PracticeSession.class,
            Exercise.class,
            MultipleChoiceExercise.class,
            MatchingExercise.class,
            FillBlankExercise.class,
            Achievement.class,
            Badge.class,
            UserPoints.class,
            UserLevel.class,
            UserStats.class

        }, 
        version = 1, 
        exportSchema = false)
@TypeConverters({
        DateConverter.class,
        ExerciseTypeConverter.class,
        QuestionListConverter.class,
        MatchingItemListConverter.class,
        FillBlankQuestionListConverter.class,
        AchievementTypeConverter.class,
        BadgeTierConverter.class,
        PointsSourceConverter.class
})

public abstract class WordDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "word_database";
    
    private static WordDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    
    public abstract WordDao wordDao();
    public abstract LessonDao lessonDao();
    public abstract UserProgressDao userProgressDao();
    public abstract QuizDao quizDao();
    public abstract FlashcardDeckDao flashcardDeckDao();
    public abstract PracticeSessionDao practiceSessionDao();
    public abstract ExerciseDao exerciseDao();
    public abstract AchievementDao achievementDao();
    public abstract GamificationAchievementDao gamificationAchievementDao();
    public abstract BadgeDao badgeDao();
    public abstract UserPointsDao userPointsDao();
    public abstract UserLevelDao userLevelDao();
    public abstract UserStatsDao userStatsDao();
    
    
    public static synchronized WordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WordDatabase.class, "english_hindi_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    
    /**
     * Callback for database creation
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;
        private LessonDao lessonDao;
        private UserProgressDao userProgressDao;
        private QuizDao quizDao;
        private FlashcardDeckDao flashcardDeckDao;
        
        private PopulateDbAsyncTask(WordDatabase db) {
            wordDao = db.wordDao();
            lessonDao = db.lessonDao();
            userProgressDao = db.userProgressDao();
            quizDao = db.quizDao();
            flashcardDeckDao = db.flashcardDeckDao();
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            // Add initial words to the database
            addInitialWords();
            
            // Add initial lessons
            addInitialLessons();
            
            // Add flashcard decks
            addInitialFlashcardDecks();
            
            // Add quizzes
            addInitialQuizzes();
            
            return null;
        }
        
        private void addInitialWords() {
            // Basic Greetings Category
            Word hello = new Word(
                "Hello", 
                "नमस्ते", 
                "huh-loh", 
                "namaste", 
                "Greetings", 
                1,
                "Hello, how are you?", 
                "नमस्ते, आप कैसे हैं?", 
                "Greeting", 
                "Formal and informal greeting"
            );
            long helloId = wordDao.insert(hello);
            
            Word goodMorning = new Word(
                "Good Morning", 
                "सुप्रभात", 
                "good mor-ning", 
                "suprabhat", 
                "Greetings", 
                1,
                "Good morning, did you sleep well?", 
                "सुप्रभात, क्या आप अच्छी तरह सोए?", 
                "Greeting", 
                "Morning greeting"
            );
            wordDao.insert(goodMorning);
            
            Word thankYou = new Word(
                "Thank you", 
                "धन्यवाद", 
                "thank-yoo", 
                "dhanyavaad", 
                "Greetings", 
                1,
                "Thank you for your help.", 
                "आपकी मदद के लिए धन्यवाद।", 
                "Expression", 
                "Expressing gratitude"
            );
            wordDao.insert(thankYou);
            
            // Basic Vocabulary
            Word yes = new Word(
                "Yes", 
                "हां", 
                "yes", 
                "haan", 
                "Basic", 
                1,
                "Yes, I understand.", 
                "हां, मैं समझता हूं।", 
                "Response", 
                "Affirmative response"
            );
            wordDao.insert(yes);
            
            Word no = new Word(
                "No", 
                "नहीं", 
                "noh", 
                "nahin", 
                "Basic", 
                1,
                "No, I don't want that.", 
                "नहीं, मुझे वह नहीं चाहिए।", 
                "Response", 
                "Negative response"
            );
            wordDao.insert(no);
            
            // Food Category
            Word water = new Word(
                "Water", 
                "पानी", 
                "waw-ter", 
                "paani", 
                "Food", 
                1,
                "Could I have some water, please?", 
                "क्या मुझे कुछ पानी मिल सकता है?", 
                "Noun", 
                "Beverage, essential for life"
            );
            wordDao.insert(water);
            
            Word food = new Word(
                "Food", 
                "खाना", 
                "food", 
                "khaana", 
                "Food", 
                1,
                "The food is delicious.", 
                "खाना स्वादिष्ट है।", 
                "Noun", 
                "Edible substances"
            );
            wordDao.insert(food);
            
            // Education Category
            Word book = new Word(
                "Book", 
                "किताब", 
                "book", 
                "kitaab", 
                "Education", 
                1,
                "I am reading a book.", 
                "मैं एक किताब पढ़ रहा हूं।", 
                "Noun", 
                "Reading material"
            );
            wordDao.insert(book);
            
            Word school = new Word(
                "School", 
                "विद्यालय", 
                "skool", 
                "vidyaalay", 
                "Education", 
                2,
                "My school is far from here.", 
                "मेरा विद्यालय यहां से दूर है।", 
                "Noun", 
                "Educational institution"
            );
            wordDao.insert(school);
            
            // People Category
            Word friend = new Word(
                "Friend", 
                "मित्र", 
                "frend", 
                "mitra", 
                "People", 
                1,
                "She is my good friend.", 
                "वह मेरी अच्छी मित्र है।", 
                "Noun", 
                "Person with whom one has a bond of mutual affection"
            );
            wordDao.insert(friend);
            
            Word family = new Word(
                "Family", 
                "परिवार", 
                "fam-uh-lee", 
                "parivaar", 
                "People", 
                1,
                "I love my family.", 
                "मैं अपने परिवार से प्यार करता हूं।", 
                "Noun", 
                "Group of people related by blood or marriage"
            );
            wordDao.insert(family);
            
            // Numbers Category
            for (int i = 0; i <= 10; i++) {
                String englishNumber;
                String hindiNumber;
                String englishPronunciation;
                String hindiPronunciation;
                
                switch (i) {
                    case 0:
                        englishNumber = "Zero";
                        hindiNumber = "शून्य";
                        englishPronunciation = "zee-roh";
                        hindiPronunciation = "shoonya";
                        break;
                    case 1:
                        englishNumber = "One";
                        hindiNumber = "एक";
                        englishPronunciation = "wun";
                        hindiPronunciation = "ek";
                        break;
                    case 2:
                        englishNumber = "Two";
                        hindiNumber = "दो";
                        englishPronunciation = "too";
                        hindiPronunciation = "do";
                        break;
                    case 3:
                        englishNumber = "Three";
                        hindiNumber = "तीन";
                        englishPronunciation = "three";
                        hindiPronunciation = "teen";
                        break;
                    case 4:
                        englishNumber = "Four";
                        hindiNumber = "चार";
                        englishPronunciation = "for";
                        hindiPronunciation = "chaar";
                        break;
                    case 5:
                        englishNumber = "Five";
                        hindiNumber = "पांच";
                        englishPronunciation = "fahyv";
                        hindiPronunciation = "paanch";
                        break;
                    case 6:
                        englishNumber = "Six";
                        hindiNumber = "छह";
                        englishPronunciation = "siks";
                        hindiPronunciation = "chhah";
                        break;
                    case 7:
                        englishNumber = "Seven";
                        hindiNumber = "सात";
                        englishPronunciation = "sev-uhn";
                        hindiPronunciation = "saat";
                        break;
                    case 8:
                        englishNumber = "Eight";
                        hindiNumber = "आठ";
                        englishPronunciation = "eyt";
                        hindiPronunciation = "aath";
                        break;
                    case 9:
                        englishNumber = "Nine";
                        hindiNumber = "नौ";
                        englishPronunciation = "nahyn";
                        hindiPronunciation = "nau";
                        break;
                    case 10:
                        englishNumber = "Ten";
                        hindiNumber = "दस";
                        englishPronunciation = "ten";
                        hindiPronunciation = "das";
                        break;
                    default:
                        englishNumber = "";
                        hindiNumber = "";
                        englishPronunciation = "";
                        hindiPronunciation = "";
                }
                
                Word number = new Word(
                    englishNumber,
                    hindiNumber,
                    englishPronunciation,
                    hindiPronunciation,
                    "Numbers",
                    1,
                    "I have " + englishNumber.toLowerCase() + " books.",
                    "मेरे पास " + hindiNumber + " किताबें हैं।",
                    "Number",
                    "Cardinal number"
                );
                
                wordDao.insert(number);
            }
        }
        
        private void addInitialLessons() {
            // Beginner Lessons
            Lesson greetingsLesson = new Lesson(
                "Basic Greetings",
                "मूल अभिवादन",
                "Learn common English greetings and how to respond to them.",
                "सामान्य अंग्रेजी अभिवादन और उनका जवाब कैसे दें, यह सीखें।",
                "Beginner",
                "easy",
                1,
                "<h1>Basic Greetings in English</h1>" +
                "<p>Greetings are an essential part of any language. Here are some common English greetings:</p>" +
                "<ul>" +
                "<li><strong>Hello</strong> - A universal greeting used any time of day</li>" +
                "<li><strong>Good Morning</strong> - Used from sunrise until noon</li>" +
                "<li><strong>Good Afternoon</strong> - Used from noon until evening</li>" +
                "<li><strong>Good Evening</strong> - Used in the evening and at night</li>" +
                "<li><strong>Hi</strong> - An informal version of Hello</li>" +
                "</ul>" +
                "<p>When someone greets you, it's polite to respond with a greeting followed by asking about their wellbeing:</p>" +
                "<p>\"Hello! How are you?\"</p>" +
                "<p>Common responses include:</p>" +
                "<ul>" +
                "<li>\"I'm fine, thank you. And you?\"</li>" +
                "<li>\"I'm good, thanks. How about you?\"</li>" +
                "<li>\"Not bad, and you?\"</li>" +
                "</ul>",
                
                "<h1>अंग्रेजी में मूल अभिवादन</h1>" +
                "<p>अभिवादन किसी भी भाषा का एक अनिवार्य हिस्सा हैं। यहां कुछ सामान्य अंग्रेजी अभिवादन हैं:</p>" +
                "<ul>" +
                "<li><strong>Hello (हैलो)</strong> - दिन के किसी भी समय उपयोग किया जाने वाला सार्वभौमिक अभिवादन</li>" +
                "<li><strong>Good Morning (गुड मॉर्निंग)</strong> - सूर्योदय से दोपहर तक उपयोग किया जाता है</li>" +
                "<li><strong>Good Afternoon (गुड आफ्टरनून)</strong> - दोपहर से शाम तक उपयोग किया जाता है</li>" +
                "<li><strong>Good Evening (गुड ईवनिंग)</strong> - शाम और रात में उपयोग किया जाता है</li>" +
                "<li><strong>Hi (हाय)</strong> - Hello का एक अनौपचारिक संस्करण</li>" +
                "</ul>" +
                "<p>जब कोई आपका अभिवादन करता है, तो उनके कल्याण के बारे में पूछते हुए अभिवादन के साथ प्रतिक्रिया देना विनम्र होता है:</p>" +
                "<p>\"Hello! How are you?\" (हैलो! आप कैसे हैं?)</p>" +
                "<p>सामान्य प्रतिक्रियाओं में शामिल हैं:</p>" +
                "<ul>" +
                "<li>\"I'm fine, thank you. And you?\" (मैं ठीक हूँ, धन्यवाद। और आप?)</li>" +
                "<li>\"I'm good, thanks. How about you?\" (मैं अच्छा हूँ, धन्यवाद। आप कैसे हैं?)</li>" +
                "<li>\"Not bad, and you?\" (बुरा नहीं, और आप?)</li>" +
                "</ul>",
                "https://example.com/lessons/greetings.jpg"
            );
            
            long greetingsLessonId = lessonDao.insert(greetingsLesson);
            
            // Link words to lessons
            lessonDao.insertLessonWord(new LessonWord((int)greetingsLessonId, 1, 1, "Common greeting"));
            lessonDao.insertLessonWord(new LessonWord((int)greetingsLessonId, 2, 2, "Morning greeting"));
            lessonDao.insertLessonWord(new LessonWord((int)greetingsLessonId, 3, 3, "Expression of gratitude"));
            
            // Numbers Lesson
            Lesson numbersLesson = new Lesson(
                "Numbers 1-10",
                "संख्याएँ 1-10",
                "Learn to count from 1 to 10 in English.",
                "अंग्रेजी में 1 से 10 तक गिनती करना सीखें।",
                "Beginner",
                "easy",
                2,
                "<h1>Numbers from 1 to 10</h1>" +
                "<p>Learning to count is one of the first steps in learning any language. Here are the numbers from 1 to 10 in English:</p>" +
                "<ol>" +
                "<li><strong>One</strong></li>" +
                "<li><strong>Two</strong></li>" +
                "<li><strong>Three</strong></li>" +
                "<li><strong>Four</strong></li>" +
                "<li><strong>Five</strong></li>" +
                "<li><strong>Six</strong></li>" +
                "<li><strong>Seven</strong></li>" +
                "<li><strong>Eight</strong></li>" +
                "<li><strong>Nine</strong></li>" +
                "<li><strong>Ten</strong></li>" +
                "</ol>" +
                "<p>You can use these numbers for counting, telling your age, phone numbers, and much more!</p>" +
                "<p>Practice saying each number aloud. Pay attention to the pronunciation.</p>",
                
                "<h1>1 से 10 तक की संख्याएँ</h1>" +
                "<p>गिनती सीखना किसी भी भाषा को सीखने में पहले कदमों में से एक है। यहां अंग्रेजी में 1 से 10 तक की संख्याएं हैं:</p>" +
                "<ol>" +
                "<li><strong>One (वन)</strong></li>" +
                "<li><strong>Two (टू)</strong></li>" +
                "<li><strong>Three (थ्री)</strong></li>" +
                "<li><strong>Four (फोर)</strong></li>" +
                "<li><strong>Five (फाइव)</strong></li>" +
                "<li><strong>Six (सिक्स)</strong></li>" +
                "<li><strong>Seven (सेवन)</strong></li>" +
                "<li><strong>Eight (एट)</strong></li>" +
                "<li><strong>Nine (नाइन)</strong></li>" +
                "<li><strong>Ten (टेन)</strong></li>" +
                "</ol>" +
                "<p>आप इन संख्याओं का उपयोग गिनती, अपनी उम्र बताने, फोन नंबरों के लिए और बहुत कुछ के लिए कर सकते हैं!</p>" +
                "<p>प्रत्येक संख्या को जोर से बोलने का अभ्यास करें। उच्चारण पर ध्यान दें।</p>",
                "https://example.com/lessons/numbers.jpg"
            );
            
            long numbersLessonId = lessonDao.insert(numbersLesson);
            
            // Link number words to the numbers lesson
            for (int i = 11; i <= 21; i++) {  // word IDs 11-21 are numbers 0-10
                lessonDao.insertLessonWord(new LessonWord((int)numbersLessonId, i, i - 10, "Count number"));
            }
            
            // Basic Conversation Lesson
            Lesson conversationLesson = new Lesson(
                "Basic Conversation",
                "मूल वार्तालाप",
                "Learn how to have a simple conversation in English.",
                "अंग्रेजी में सरल वार्तालाप कैसे करें, यह सीखें।",
                "Beginner",
                "easy",
                3,
                "<h1>Basic English Conversation</h1>" +
                "<p>Being able to have a simple conversation is a great way to practice your English. Here's a basic conversation pattern:</p>" +
                "<h2>Starting a Conversation</h2>" +
                "<ul>" +
                "<li>\"Hello! My name is [Your Name]. What's your name?\"</li>" +
                "<li>\"Nice to meet you, [Their Name].\"</li>" +
                "<li>\"How are you today?\"</li>" +
                "</ul>" +
                "<h2>Asking Simple Questions</h2>" +
                "<ul>" +
                "<li>\"Where are you from?\"</li>" +
                "<li>\"What do you do?\" (asking about their job)</li>" +
                "<li>\"Do you like [something]?\"</li>" +
                "</ul>" +
                "<h2>Ending a Conversation</h2>" +
                "<ul>" +
                "<li>\"It was nice talking with you.\"</li>" +
                "<li>\"I have to go now. See you later!\"</li>" +
                "<li>\"Goodbye!\" or \"Bye!\"</li>" +
                "</ul>" +
                "<p>Practice these phrases with a friend or language partner.</p>",
                
                "<h1>मूल अंग्रेजी वार्तालाप</h1>" +
                "<p>एक सरल वार्तालाप करने में सक्षम होना आपकी अंग्रेजी का अभ्यास करने का एक शानदार तरीका है। यहां एक मूल वार्तालाप पैटर्न है:</p>" +
                "<h2>वार्तालाप शुरू करना</h2>" +
                "<ul>" +
                "<li>\"Hello! My name is [आपका नाम]. What's your name?\" (हैलो! मेरा नाम [आपका नाम] है। आपका नाम क्या है?)</li>" +
                "<li>\"Nice to meet you, [उनका नाम].\" (आपसे मिलकर अच्छा लगा, [उनका नाम]।)</li>" +
                "<li>\"How are you today?\" (आज आप कैसे हैं?)</li>" +
                "</ul>" +
                "<h2>सरल प्रश्न पूछना</h2>" +
                "<ul>" +
                "<li>\"Where are you from?\" (आप कहां से हैं?)</li>" +
                "<li>\"What do you do?\" (आप क्या करते हैं?) (उनके काम के बारे में पूछना)</li>" +
                "<li>\"Do you like [कुछ]?\" (क्या आपको [कुछ] पसंद है?)</li>" +
                "</ul>" +
                "<h2>वार्तालाप समाप्त करना</h2>" +
                "<ul>" +
                "<li>\"It was nice talking with you.\" (आपसे बात करके अच्छा लगा।)</li>" +
                "<li>\"I have to go now. See you later!\" (मुझे अब जाना होगा। फिर मिलेंगे!)</li>" +
                "<li>\"Goodbye!\" या \"Bye!\" (अलविदा!)</li>" +
                "</ul>" +
                "<p>इन वाक्यांशों का अभ्यास किसी मित्र या भाषा साथी के साथ करें।</p>",
                "https://example.com/lessons/conversation.jpg"
            );
            
            long conversationLessonId = lessonDao.insert(conversationLesson);
            
            // Link relevant words to the conversation lesson
            lessonDao.insertLessonWord(new LessonWord((int)conversationLessonId, 1, 1, "Greeting"));
            lessonDao.insertLessonWord(new LessonWord((int)conversationLessonId, 3, 2, "Expressing thanks"));
            lessonDao.insertLessonWord(new LessonWord((int)conversationLessonId, 4, 3, "Affirmative response"));
            lessonDao.insertLessonWord(new LessonWord((int)conversationLessonId, 5, 4, "Negative response"));
            
            // Set up user progress for the default user (ID 1)
            // Track progress for lessons
            userProgressDao.insert(new UserProgress(1, "lesson", (int)greetingsLessonId));
            userProgressDao.insert(new UserProgress(1, "lesson", (int)numbersLessonId));
            userProgressDao.insert(new UserProgress(1, "lesson", (int)conversationLessonId));
            
            // Track progress for words
            for (int i = 1; i <= 21; i++) {  // First 21 words
                userProgressDao.insert(new UserProgress(1, "word", i));
            }
        }
        
        private void addInitialFlashcardDecks() {
            // Create beginner flashcard decks
            FlashcardDeck greetingsDeck = new FlashcardDeck(
                "Greetings and Expressions",
                "अभिवादन और अभिव्यक्तियां",
                "Learn common greetings and everyday expressions.",
                "सामान्य अभिवादन और रोजमर्रा की अभिव्यक्तियां सीखें।",
                "Beginner",
                1
            );
            long greetingsDeckId = flashcardDeckDao.insert(greetingsDeck);
            
            // Add words to the greetings deck
            flashcardDeckDao.insertDeckWord(new DeckWord((int)greetingsDeckId, 1, 1, "Universal greeting"));
            flashcardDeckDao.insertDeckWord(new DeckWord((int)greetingsDeckId, 2, 2, "Morning greeting"));
            flashcardDeckDao.insertDeckWord(new DeckWord((int)greetingsDeckId, 3, 3, "Expression of gratitude"));
            
            // Create numbers flashcard deck
            FlashcardDeck numbersDeck = new FlashcardDeck(
                "Numbers 0-10",
                "संख्याएँ 0-10",
                "Learn to count from 0 to 10 in English.",
                "अंग्रेजी में 0 से 10 तक गिनती करना सीखें।",
                "Beginner",
                1
            );
            long numbersDeckId = flashcardDeckDao.insert(numbersDeck);
            
            // Add words to the numbers deck
            for (int i = 11; i <= 21; i++) {  // word IDs 11-21 are numbers 0-10
                flashcardDeckDao.insertDeckWord(new DeckWord((int)numbersDeckId, i, i - 10, "Number"));
            }
            
            // Create food and drinks flashcard deck
            FlashcardDeck foodDeck = new FlashcardDeck(
                "Food and Drinks",
                "खाद्य और पेय",
                "Learn vocabulary related to food and drinks.",
                "भोजन और पेय से संबंधित शब्दावली सीखें।",
                "Beginner",
                1
            );
            long foodDeckId = flashcardDeckDao.insert(foodDeck);
            
            // Add words to the food deck
            flashcardDeckDao.insertDeckWord(new DeckWord((int)foodDeckId, 6, 1, "Basic beverage"));
            flashcardDeckDao.insertDeckWord(new DeckWord((int)foodDeckId, 7, 2, "General food term"));
        }
        
        private void addInitialQuizzes() {
            // Add quizzes for the Greetings lesson
            Quiz greetingsQuiz1 = new Quiz(
                1,  // Greetings lesson ID
                "Which greeting is used in the morning?",
                "सुबह में कौन सा अभिवादन प्रयोग किया जाता है?",
                "Good Morning",
                "Good Evening",
                "Good Afternoon",
                "Hello",
                "Good Morning is used from sunrise until noon.",
                "गुड मॉर्निंग सूर्योदय से दोपहर तक प्रयोग किया जाता है।",
                "multiple_choice",
                1
            );
            quizDao.insert(greetingsQuiz1);
            
            Quiz greetingsQuiz2 = new Quiz(
                1,  // Greetings lesson ID
                "How do you say 'Thank you' in Hindi?",
                "हिंदी में 'Thank you' कैसे कहते हैं?",
                "धन्यवाद",
                "नमस्ते",
                "आपका स्वागत है",
                "माफ़ कीजिये",
                "धन्यवाद (Dhanyavaad) means 'Thank you' in Hindi.",
                "धन्यवाद का अर्थ हिंदी में 'Thank you' होता है।",
                "multiple_choice",
                1
            );
            quizDao.insert(greetingsQuiz2);
            
            // Add quizzes for the Numbers lesson
            Quiz numbersQuiz1 = new Quiz(
                2,  // Numbers lesson ID
                "What is the correct spelling for the number 5?",
                "संख्या 5 के लिए सही वर्तनी क्या है?",
                "Five",
                "Fiv",
                "Fife",
                "Fyve",
                "Five is the correct spelling for the number 5.",
                "Five संख्या 5 के लिए सही वर्तनी है।",
                "multiple_choice",
                1
            );
            quizDao.insert(numbersQuiz1);
            
            Quiz numbersQuiz2 = new Quiz(
                2,  // Numbers lesson ID
                "How do you say the number 3 in Hindi?",
                "हिंदी में संख्या 3 कैसे कहते हैं?",
                "तीन",
                "दो",
                "चार",
                "एक",
                "तीन (teen) is the Hindi word for the number 3.",
                "तीन अंग्रेजी के 3 (three) के लिए हिंदी शब्द है।",
                "multiple_choice",
                1
            );
            quizDao.insert(numbersQuiz2);
            
            // Add fill-in-the-blank quizzes
            Quiz fillBlankQuiz = new Quiz(
                3,  // Conversation lesson ID
                "Complete the greeting: 'Hello, _____ are you?'",
                "अभिवादन पूरा करें: 'Hello, _____ are you?'",
                "how",
                "what",
                "when",
                "why",
                "'How are you?' is a common greeting question in English.",
                "'How are you?' अंग्रेजी में एक आम अभिवादन प्रश्न है।",
                "fill_blank",
                1
            );
            quizDao.insert(fillBlankQuiz);
            
            // Add pronunciation quiz
            Quiz pronunciationQuiz = new Quiz(
                1,  // Greetings lesson ID
                "How is 'Hello' pronounced in English?",
                "अंग्रेजी में 'Hello' का उच्चारण कैसे होता है?",
                "huh-loh",
                "hee-loh",
                "hah-loh",
                "hay-loh",
                "Hello is pronounced as 'huh-loh' with emphasis on the first syllable.",
                "Hello का उच्चारण 'हैलो' होता है, पहले शब्दांश पर जोर देकर।",
                "pronunciation",
                1
            );
            quizDao.insert(pronunciationQuiz);
        }
    }
}