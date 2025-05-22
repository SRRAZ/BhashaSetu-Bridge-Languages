package com.example.englishhindi.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.englishhindi.data.model.Quiz
import com.example.englishhindi.ui.theme.EnglishHindiTheme
import java.util.Date

/**
 * A card component for displaying a quiz.
 *
 * @param quiz The Quiz object to display
 * @param currentLanguage The currently selected language code ("en" or "hi")
 * @param onClick Callback when the card is clicked
 * @param modifier Optional modifier for styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizCard(
    quiz: Quiz,
    currentLanguage: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quiz title based on selected language
                Text(
                    text = if (currentLanguage == "hi") quiz.titleHindi else quiz.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Difficulty indicator
                Row {
                    repeat(quiz.difficulty) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Quiz description
            Text(
                text = if (currentLanguage == "hi") quiz.descriptionHindi else quiz.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
            )

            // Quiz info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quiz type and completion info
                Column {
                    Text(
                        text = if (currentLanguage == "hi") "प्रकार: ${getQuizTypeInHindi(quiz.quizType)}" else "Type: ${quiz.quizType.capitalize()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    if (quiz.completionCount > 0) {
                        Text(
                            text = if (currentLanguage == "hi") 
                                "पूरा किया: ${quiz.completionCount} बार • सर्वश्रेष्ठ स्कोर: ${quiz.bestScore}%" 
                            else 
                                "Completed: ${quiz.completionCount} times • Best score: ${quiz.bestScore}%",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Start button
                FilledTonalButton(
                    onClick = onClick,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = if (currentLanguage == "hi") "शुरू करें" else "Start")
                }
            }
        }
    }
}

// Helper function to translate quiz types to Hindi
private fun getQuizTypeInHindi(quizType: String): String {
    return when (quizType) {
        "lesson" -> "पाठ"
        "daily" -> "दैनिक"
        "category" -> "श्रेणी"
        "custom" -> "कस्टम"
        else -> quizType
    }
}

// Helper extension function to capitalize the first letter
private fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}

@Preview(showBackground = true)
@Composable
fun QuizCardPreview() {
    val sampleQuiz = Quiz(
        id = 1,
        title = "Basic Vocabulary Quiz",
        titleHindi = "बुनियादी शब्दावली प्रश्नोत्तरी",
        description = "Test your knowledge of basic vocabulary words",
        descriptionHindi = "बुनियादी शब्दावली शब्दों के अपने ज्ञान का परीक्षण करें",
        quizType = "category",
        difficulty = 2,
        categoryId = 1,
        createdDate = Date(),
        bestScore = 85,
        completionCount = 3,
        passingScore = 70
    )
    
    EnglishHindiTheme {
        Surface {
            QuizCard(
                quiz = sampleQuiz,
                currentLanguage = "en",
                onClick = {}
            )
        }
    }
}