package com.bhashasetu.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bhashasetu.app.ui.theme.EnglishHindiTheme

/**
 * A component that displays a progress bar with a label and percentage.
 *
 * @param label The label for the progress bar
 * @param labelHindi The Hindi label for the progress bar
 * @param currentLanguage The currently selected language code ("en" or "hi")
 * @param current The current progress value
 * @param total The total progress value
 * @param color Optional color for the progress bar
 * @param modifier Optional modifier for styling
 */
@Composable
fun ProgressBar(
    label: String,
    labelHindi: String,
    currentLanguage: String,
    current: Int,
    total: Int,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    val percentage = if (total > 0) current.toFloat() / total.toFloat() else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(durationMillis = 1000),
        label = "ProgressAnimation"
    )
    
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Label based on selected language
            Text(
                text = if (currentLanguage == "hi") labelHindi else label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            // Progress text
            Text(
                text = if (currentLanguage == "hi") 
                    "$current / $total (${(percentage * 100).toInt()}%)" 
                else 
                    "$current / $total (${(percentage * 100).toInt()}%)",
                style = MaterialTheme.typography.bodySmall
            )
        }
        
        // Progress bar
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    EnglishHindiTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                ProgressBar(
                    label = "Words Learned",
                    labelHindi = "सीखे गए शब्द",
                    currentLanguage = "en",
                    current = 75,
                    total = 100,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                ProgressBar(
                    label = "Daily Goal",
                    labelHindi = "दैनिक लक्ष्य",
                    currentLanguage = "hi",
                    current = 3,
                    total = 5,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}