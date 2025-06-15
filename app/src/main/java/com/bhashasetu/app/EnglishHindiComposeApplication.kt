package com.bhashasetu.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.os.StrictMode
import androidx.room.Room
import com.bhashasetu.app.data.db.AppDatabase
import com.bhashasetu.app.data.model.Word // Import Word to simplify usage
import com.bhashasetu.app.data.repository.WordRepository
import com.bhashasetu.app.data.repository.WordRepositoryImpl
import com.bhashasetu.app.util.LanguageManager
import com.bhashasetu.app.util.PreferenceManager
import java.util.concurrent.Executors
import com.bhashasetu.app.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Application class with Compose implementation
 */
@HiltAndroidApp
class EnglishHindiComposeApplication : Application() {

    // Repositories
    lateinit var wordRepository: WordRepository

    // Database
    lateinit var database: AppDatabase

    // Language manager
    lateinit var languageManager: LanguageManager

    // Preference manager
    lateinit var preferenceManager: PreferenceManager

    // Background executor
    private val executor = Executors.newFixedThreadPool(4)

    // Coroutine scope for database operations
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // Initialize StrictMode for debug builds
        if (BuildConfig.DEBUG) {
            initializeStrictMode()
        }

        // Initialize database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "english_hindi_learning_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        // Initialize repositories
        wordRepository = WordRepositoryImpl(database.wordDao())

        // Initialize language manager
        languageManager = LanguageManager(this)

        // Initialize preference manager
        preferenceManager = PreferenceManager(this)

        // Preload essential data in background
        preloadData()
    }

    /**
     * Sets up StrictMode for catching potential issues in development
     */
    private fun initializeStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )
    }

    /**
     * Preloads essential data in the background
     */
    private fun preloadData() {
        applicationScope.launch {
            // Check if initial data needs to be seeded
            val allWords = database.wordDao().getAllWords()

            if (allWords.isEmpty()) {
                // Seed initial data
                seedSampleData()
            }
        }
    }

    /**
     * Seeds some sample data for first-time users
     */
    private suspend fun seedSampleData() {
        // Basic greetings category
        val greetingsWords = listOf(
            Word( // Using imported Word
                englishWord = "Hello",
                hindiWord = "नमस्ते",
                englishPronunciation = "huh-loh",
                hindiPronunciation = "nuh-muh-stay",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Hello, how are you?",
                exampleSentenceHindi = "नमस्ते, आप कैसे हैं?"
            ),
            Word(
                englishWord = "Good morning",
                hindiWord = "सुप्रभात",
                englishPronunciation = "good mor-ning",
                hindiPronunciation = "soo-pruh-bhaat",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Good morning, did you sleep well?",
                exampleSentenceHindi = "सुप्रभात, क्या आपने अच्छी नींद ली?"
            ),
            Word(
                englishWord = "Thank you",
                hindiWord = "धन्यवाद",
                englishPronunciation = "thank-yoo",
                hindiPronunciation = "dhun-yuh-vaad",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Thank you for your help.",
                exampleSentenceHindi = "आपकी मदद के लिए धन्यवाद।"
            ),
            Word(
                englishWord = "Goodbye",
                hindiWord = "अलविदा",
                englishPronunciation = "good-bye",
                hindiPronunciation = "ul-vee-daa",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Goodbye, see you tomorrow.",
                exampleSentenceHindi = "अलविदा, कल मिलते हैं।"
            ),
            Word(
                englishWord = "Good night",
                hindiWord = "शुभ रात्रि",
                englishPronunciation = "good night",
                hindiPronunciation = "shubh raa-tree",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Good night, sleep well.",
                exampleSentenceHindi = "शुभ रात्रि, अच्छी नींद लें।"
            )
        )

        // Basic food category
        val foodWords = listOf(
            Word(
                englishWord = "Water",
                hindiWord = "पानी",
                englishPronunciation = "waa-ter",
                hindiPronunciation = "paa-nee",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "I would like a glass of water.",
                exampleSentenceHindi = "मुझे एक गिलास पानी चाहिए।"
            ),
            Word(
                englishWord = "Bread",
                hindiWord = "रोटी",
                englishPronunciation = "bred",
                hindiPronunciation = "ro-tee",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "I eat bread for breakfast.",
                exampleSentenceHindi = "मैं नाश्ते में रोटी खाता हूँ।"
            ),
            Word(
                englishWord = "Rice",
                hindiWord = "चावल",
                englishPronunciation = "rice",
                hindiPronunciation = "chaa-val",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "Rice is a staple food in many countries.",
                exampleSentenceHindi = "चावल कई देशों का मुख्य भोजन है।"
            ),
            Word(
                englishWord = "Tea",
                hindiWord = "चाय",
                englishPronunciation = "tee",
                hindiPronunciation = "chaay",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "Would you like some tea?",
                exampleSentenceHindi = "क्या आप कुछ चाय लेंगे?"
            ),
            Word(
                englishWord = "Fruit",
                hindiWord = "फल",
                englishPronunciation = "froot",
                hindiPronunciation = "phal",
                category = "Food & Drinks",
                difficulty = 2,
                exampleSentenceEnglish = "I eat fruit every day.",
                exampleSentenceHindi = "मैं हर दिन फल खाता हूँ।"
            )
        )

        // Basic numbers
        val numberWords = listOf(
            Word(
                englishWord = "One",
                hindiWord = "एक",
                englishPronunciation = "wun",
                hindiPronunciation = "ek",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I have one book.",
                exampleSentenceHindi = "मेरे पास एक किताब है।"
            ),
            Word(
                englishWord = "Two",
                hindiWord = "दो",
                englishPronunciation = "too",
                hindiPronunciation = "do",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I have two hands.",
                exampleSentenceHindi = "मेरे पास दो हाथ हैं।"
            ),
            Word(
                englishWord = "Three",
                hindiWord = "तीन",
                englishPronunciation = "three",
                hindiPronunciation = "teen",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I need three tickets.",
                exampleSentenceHindi = "मुझे तीन टिकट चाहिए।"
            ),
            Word(
                englishWord = "Four",
                hindiWord = "चार",
                englishPronunciation = "for",
                hindiPronunciation = "chaar",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "The table has four legs.",
                exampleSentenceHindi = "मेज के चार पैर हैं।"
            ),
            Word(
                englishWord = "Five",
                hindiWord = "पांच",
                englishPronunciation = "five",
                hindiPronunciation = "paanch",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I have five fingers on each hand.",
                exampleSentenceHindi = "मेरे हर हाथ में पांच उंगलियां हैं।"
            )
        )

        // Insert all words
        val allWords = greetingsWords + foodWords + numberWords
        val wordDao = database.wordDao()

        for (word in allWords) {
            try {
                wordDao.insert(word)
            } catch (e: Exception) {
            // Log error but continue with other words
            e.printStackTrace()
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        executor.shutdown()
    }

    companion object {
        private const val TAG = "EnglishHindiApp"
    }
}