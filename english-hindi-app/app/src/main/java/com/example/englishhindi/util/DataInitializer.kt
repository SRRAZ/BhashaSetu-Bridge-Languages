package com.example.englishhindi.util

import android.content.Context
import android.util.Log
import com.example.englishhindi.data.model.*
import com.example.englishhindi.data.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for initializing app data from JSON assets
 */
@Singleton
class DataInitializer @Inject constructor(
    private val wordRepository: WordRepository,
    private val categoryRepository: CategoryRepository,
    private val lessonRepository: LessonRepository,
    private val quizRepository: QuizRepository,
    private val achievementRepository: AchievementRepository,
    private val userProgressRepository: UserProgressRepository,
    private val appSettingsRepository: AppSettingsRepository,
    private val dailyStreakRepository: DailyStreakRepository
) {
    
    /**
     * Initializes all app data from JSON assets
     */
    fun initializeAppData(context: Context, scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            try {
                // Check if data is already initialized
                val wordCount = wordRepository.getWordCount().value ?: 0
                if (wordCount > 0) {
                    Log.d(TAG, "Data already initialized, skipping...")
                    return@launch
                }
                
                // Initialize default settings if not already set
                initializeSettings()
                
                // Initialize daily streak
                initializeDailyStreak()
                
                // Load vocabulary categories
                loadVocabularyCategories(context)
                
                // Load achievements
                loadAchievements(context)
                
                // Load lessons
                loadLessons(context)
                
                // Load quizzes
                loadQuizzes(context)
                
                Log.d(TAG, "App data initialization completed successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing app data", e)
            }
        }
    }
    
    /**
     * Initializes default app settings
     */
    private suspend fun initializeSettings() {
        val settings = AppSettings(
            id = 1,
            language = "en",
            notificationsEnabled = true,
            dailyReminderTime = "09:00",
            soundEnabled = true,
            vibrationEnabled = true,
            darkModeEnabled = false,
            dailyWordCount = 5,
            fontSizeMultiplier = 1.0f,
            autoPlayAudio = true,
            showTransliteration = true
        )
        
        appSettingsRepository.insert(settings)
    }
    
    /**
     * Initializes daily streak data
     */
    private suspend fun initializeDailyStreak() {
        val dailyStreak = DailyStreak(
            id = 1,
            currentStreak = 0,
            longestStreak = 0,
            lastCheckInDate = null,
            streakStartDate = null,
            totalDaysStudied = 0
        )
        
        dailyStreakRepository.insert(dailyStreak)
    }
    
    /**
     * Loads vocabulary categories and words from JSON assets
     */
    private suspend fun loadVocabularyCategories(context: Context) {
        val vocabularyFiles = listOf(
            "vocabulary/basic_vocabulary.json",
            "vocabulary/food_vocabulary.json",
            "vocabulary/numbers_vocabulary.json",
            "vocabulary/family_vocabulary.json"
        )
        
        for (filePath in vocabularyFiles) {
            try {
                val jsonString = readAssetFile(context, filePath)
                val jsonObject = JSONObject(jsonString)
                
                // Parse category
                val categoryJson = jsonObject.getJSONObject("category")
                val category = Category(
                    id = categoryJson.getLong("id"),
                    name = categoryJson.getString("name"),
                    nameHindi = categoryJson.getString("nameHindi"),
                    description = categoryJson.getString("description"),
                    descriptionHindi = categoryJson.getString("descriptionHindi"),
                    level = categoryJson.optInt("level", 1)
                )
                
                // Insert category
                val categoryId = categoryRepository.insert(category)
                
                // Parse and insert words
                val wordsArray = jsonObject.getJSONArray("words")
                for (i in 0 until wordsArray.length()) {
                    val wordJson = wordsArray.getJSONObject(i)
                    val word = Word(
                        englishWord = wordJson.getString("englishWord"),
                        hindiWord = wordJson.getString("hindiWord"),
                        pronunciation = wordJson.getString("pronunciation"),
                        audioFileName = wordJson.optString("audioFileName", null),
                        imageFileName = wordJson.optString("imageFileName", null),
                        englishDefinition = wordJson.getString("englishDefinition"),
                        hindiDefinition = wordJson.getString("hindiDefinition"),
                        englishExample = wordJson.optString("englishExample", null),
                        hindiExample = wordJson.optString("hindiExample", null),
                        difficulty = wordJson.getInt("difficulty"),
                        dateAdded = Date(),
                        isFavorite = false,
                        notes = wordJson.optString("notes", null),
                        partsOfSpeech = wordJson.getString("partsOfSpeech"),
                        synonyms = wordJson.optString("synonyms", null),
                        antonyms = wordJson.optString("antonyms", null)
                    )
                    
                    // Insert word
                    val wordId = wordRepository.insert(word)
                    
                    // Link word to category
                    categoryRepository.addWordToCategory(wordId, categoryId)
                    
                    // Initialize user progress for this word
                    val userProgress = UserProgress(
                        wordId = wordId,
                        proficiencyLevel = 0,
                        correctAnswers = 0,
                        incorrectAnswers = 0,
                        lastReviewed = null,
                        nextReviewDate = null,
                        exposureCount = 0,
                        memorizationStatus = 0,
                        lastQuizScore = 0,
                        efFactor = 2.5f
                    )
                    
                    userProgressRepository.insert(userProgress)
                }
                
                Log.d(TAG, "Loaded vocabulary category: ${category.name} with ${wordsArray.length()} words")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading vocabulary file: $filePath", e)
            }
        }
    }
    
    /**
     * Loads achievement data from JSON assets
     */
    private suspend fun loadAchievements(context: Context) {
        try {
            val jsonString = readAssetFile(context, "achievements/achievements.json")
            val jsonObject = JSONObject(jsonString)
            val achievementsArray = jsonObject.getJSONArray("achievements")
            
            for (i in 0 until achievementsArray.length()) {
                val achievementJson = achievementsArray.getJSONObject(i)
                val achievement = Achievement(
                    id = achievementJson.getInt("id"),
                    title = achievementJson.getString("title"),
                    titleHindi = achievementJson.getString("titleHindi"),
                    description = achievementJson.getString("description"),
                    descriptionHindi = achievementJson.getString("descriptionHindi"),
                    iconFileName = achievementJson.getString("iconFileName"),
                    requiredValue = achievementJson.getInt("requiredValue"),
                    achievementType = achievementJson.getString("achievementType"),
                    unlocked = false,
                    unlockedDate = null,
                    rarity = achievementJson.getString("rarity"),
                    rewardPoints = achievementJson.getInt("rewardPoints"),
                    progress = 0
                )
                
                achievementRepository.insert(achievement)
            }
            
            Log.d(TAG, "Loaded ${achievementsArray.length()} achievements")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading achievements", e)
        }
    }
    
    /**
     * Loads lesson data from JSON assets
     */
    private suspend fun loadLessons(context: Context) {
        val lessonFiles = listOf(
            "lessons/lesson1.json",
            "lessons/lesson2.json"
        )
        
        for (filePath in lessonFiles) {
            try {
                val jsonString = readAssetFile(context, filePath)
                val jsonObject = JSONObject(jsonString)
                
                // Parse lesson
                val lessonJson = jsonObject.getJSONObject("lesson")
                val lesson = Lesson(
                    id = lessonJson.getLong("id"),
                    title = lessonJson.getString("title"),
                    titleHindi = lessonJson.getString("titleHindi"),
                    description = lessonJson.getString("description"),
                    descriptionHindi = lessonJson.getString("descriptionHindi"),
                    level = lessonJson.getInt("level"),
                    order = lessonJson.getInt("order"),
                    estimatedDurationMinutes = lessonJson.getInt("estimatedDurationMinutes"),
                    isCompleted = false,
                    imageFileName = lessonJson.optString("imageFileName", null),
                    prerequisiteLessonIds = lessonJson.optString("prerequisiteLessonIds", null)
                )
                
                // Insert lesson
                val lessonId = lessonRepository.insert(lesson)
                
                // Parse and link words to the lesson if the lesson content has vocabulary sections
                val contentArray = jsonObject.getJSONArray("content")
                for (i in 0 until contentArray.length()) {
                    val contentItem = contentArray.getJSONObject(i)
                    val contentType = contentItem.getString("type")
                    
                    if (contentType == "vocabulary" && contentItem.has("wordIds")) {
                        val wordIdsArray = contentItem.getJSONArray("wordIds")
                        for (j in 0 until wordIdsArray.length()) {
                            val wordId = wordIdsArray.getLong(j)
                            val lessonWord = LessonWord(
                                lessonId = lessonId,
                                wordId = wordId,
                                order = j
                            )
                            
                            lessonRepository.addWordToLesson(lessonWord)
                        }
                    }
                }
                
                Log.d(TAG, "Loaded lesson: ${lesson.title}")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading lesson file: $filePath", e)
            }
        }
    }
    
    /**
     * Loads quiz data from JSON assets
     */
    private suspend fun loadQuizzes(context: Context) {
        val quizFiles = listOf(
            "quizzes/quiz1.json",
            "quizzes/quiz2.json"
        )
        
        for (filePath in quizFiles) {
            try {
                val jsonString = readAssetFile(context, filePath)
                val jsonObject = JSONObject(jsonString)
                
                // Parse quiz
                val quizJson = jsonObject.getJSONObject("quiz")
                val quiz = Quiz(
                    id = quizJson.getLong("id"),
                    title = quizJson.getString("title"),
                    titleHindi = quizJson.getString("titleHindi"),
                    description = quizJson.getString("description"),
                    descriptionHindi = quizJson.getString("descriptionHindi"),
                    quizType = quizJson.getString("quizType"),
                    difficulty = quizJson.getInt("difficulty"),
                    lessonId = if (quizJson.has("lessonId")) quizJson.getLong("lessonId") else null,
                    categoryId = if (quizJson.has("categoryId")) quizJson.getLong("categoryId") else null,
                    createdDate = Date(),
                    lastAttemptDate = null,
                    bestScore = 0,
                    completionCount = 0,
                    isActive = true,
                    timeLimit = quizJson.optInt("timeLimit", 0).let { if (it == 0) null else it },
                    passingScore = quizJson.optInt("passingScore", 60)
                )
                
                // Insert quiz
                val quizId = quizRepository.insert(quiz)
                
                // Parse and insert questions
                val questionsArray = jsonObject.getJSONArray("questions")
                for (i in 0 until questionsArray.length()) {
                    val questionJson = questionsArray.getJSONObject(i)
                    
                    // Parse options
                    val options = if (questionJson.has("options")) {
                        if (questionJson.get("options") is JSONArray) {
                            val optionsArray = questionJson.getJSONArray("options")
                            val optionsList = mutableListOf<String>()
                            for (j in 0 until optionsArray.length()) {
                                optionsList.add(optionsArray.getString(j))
                            }
                            optionsList.joinToString(",")
                        } else {
                            questionJson.getString("options")
                        }
                    } else {
                        null
                    }
                    
                    // Parse Hindi options
                    val optionsHindi = if (questionJson.has("optionsHindi")) {
                        if (questionJson.get("optionsHindi") is JSONArray) {
                            val optionsArray = questionJson.getJSONArray("optionsHindi")
                            val optionsList = mutableListOf<String>()
                            for (j in 0 until optionsArray.length()) {
                                optionsList.add(optionsArray.getString(j))
                            }
                            optionsList.joinToString(",")
                        } else {
                            questionJson.getString("optionsHindi")
                        }
                    } else {
                        null
                    }
                    
                    val question = QuizQuestion(
                        id = questionJson.getLong("id"),
                        quizId = quizId,
                        questionType = questionJson.getString("questionType"),
                        questionText = questionJson.getString("questionText"),
                        questionTextHindi = questionJson.optString("questionTextHindi", null),
                        options = options,
                        optionsHindi = optionsHindi,
                        correctAnswer = questionJson.getString("correctAnswer"),
                        correctAnswerHindi = questionJson.optString("correctAnswerHindi", null),
                        explanation = questionJson.optString("explanation", null),
                        explanationHindi = questionJson.optString("explanationHindi", null),
                        wordId = if (questionJson.has("wordId")) questionJson.getLong("wordId") else null,
                        order = questionJson.getInt("order"),
                        points = questionJson.optInt("points", 1),
                        difficultyLevel = questionJson.optInt("difficultyLevel", 1),
                        audioFileName = questionJson.optString("audioFileName", null),
                        imageFileName = questionJson.optString("imageFileName", null)
                    )
                    
                    quizRepository.insertQuestion(question)
                }
                
                Log.d(TAG, "Loaded quiz: ${quiz.title} with ${questionsArray.length()} questions")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading quiz file: $filePath", e)
            }
        }
    }
    
    /**
     * Reads a file from the app's assets
     */
    private suspend fun readAssetFile(context: Context, filePath: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(filePath)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                
                inputStream.close()
                stringBuilder.toString()
            } catch (e: IOException) {
                Log.e(TAG, "Error reading asset file: $filePath", e)
                throw e
            }
        }
    }
    
    companion object {
        private const val TAG = "DataInitializer"
    }
}