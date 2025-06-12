package com.bhashasetu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bhashasetu.app.ui.theme.EnglishHindiTheme

/**
 * A card component for displaying practice session results
 *
 * @param correctCount Number of correct answers
 * @param totalCount Total number of questions
 * @param streakDays Current streak days
 * @param pointsEarned Points earned in this session
 * @param currentLanguage The current app language setting ("en" or "hi")
 * @param onContinue Callback when the continue button is clicked
 * @param modifier Optional modifier for the component
 */
@Composable
fun PracticeResultCard(
    correctCount: Int,
    totalCount: Int,
    streakDays: Int,
    pointsEarned: Int,
    currentLanguage: String,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val percentage = if (totalCount > 0) (correctCount.toFloat() / totalCount) * 100 else 0f
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            LocalizedText(
                textEn = "Practice Complete!",
                textHi = "अभ्यास पूरा!",
                currentLanguage = currentLanguage,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Results circle
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${percentage.toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "$correctCount/$totalCount",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Streak stat
                StatItem(
                    icon = Icons.Default.TrendingUp,
                    value = streakDays.toString(),
                    labelEn = "Day Streak",
                    labelHi = "दिन स्ट्रीक",
                    currentLanguage = currentLanguage
                )
                
                // Points stat
                StatItem(
                    icon = Icons.Default.EmojiEvents,
                    value = "+$pointsEarned",
                    labelEn = "Points",
                    labelHi = "अंक",
                    currentLanguage = currentLanguage
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Continue button
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            ) {
                LocalizedText(
                    textEn = "Continue",
                    textHi = "जारी रखें",
                    currentLanguage = currentLanguage
                )
            }
        }
    }
}

/**
 * A single stat item with icon, value, and label
 */
@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    labelEn: String,
    labelHi: String,
    currentLanguage: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        LocalizedText(
            textEn = labelEn,
            textHi = labelHi,
            currentLanguage = currentLanguage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PracticeResultCardPreview() {
    EnglishHindiTheme {
        Surface {
            PracticeResultCard(
                correctCount = 8,
                totalCount = 10,
                streakDays = 3,
                pointsEarned = 25,
                currentLanguage = "en",
                onContinue = {}
            )
        }
    }
}