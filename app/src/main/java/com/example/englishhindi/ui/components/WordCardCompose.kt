package com.example.englishhindi.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.englishhindi.data.model.Word
import com.example.englishhindi.ui.theme.LocalAppLanguage
import com.example.englishhindi.ui.theme.AppLanguage
import com.example.englishhindi.ui.theme.beginnerColor
import com.example.englishhindi.ui.theme.englishTypography
import com.example.englishhindi.ui.theme.highProgressColor
import com.example.englishhindi.ui.theme.hindiTypography
import com.example.englishhindi.ui.theme.lowProgressColor
import com.example.englishhindi.ui.theme.mediumProgressColor
import java.util.Date

/**
 * WordCard component in Jetpack Compose
 * Displays a word with English and Hindi translations
 */
@Composable
fun WordCard(
    word: Word,
    onCardClick: (Word) -> Unit = {},
    onFavoriteClick: (Word) -> Unit = {},
    onPlayPronunciationClick: (Word, Boolean) -> Unit = { _, _ -> },
    isHindiFirst: Boolean = LocalAppLanguage.current == AppLanguage.HINDI,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val masteryProgress = word.masteryLevel / 100f
    
    // Determine color based on mastery level
    val progressColor = when {
        masteryProgress < 0.4f -> lowProgressColor
        masteryProgress < 0.7f -> mediumProgressColor
        else -> highProgressColor
    }
    
    // Animated progress value
    val animatedProgress by animateFloatAsState(
        targetValue = masteryProgress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    
    // Language dependent typography
    val primaryTypography = if (isHindiFirst) hindiTypography else englishTypography
    val secondaryTypography = if (isHindiFirst) englishTypography else hindiTypography
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCardClick(word) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Card header with word and favorite button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Primary word text (English or Hindi based on isHindiFirst)
                Text(
                    text = if (isHindiFirst) word.hindiWord else word.englishWord,
                    style = primaryTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )
                
                // Favorite button
                IconButton(
                    onClick = { onFavoriteClick(word) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (word.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (word.isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (word.isFavorite) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            // Secondary word text
            Text(
                text = if (isHindiFirst) word.englishWord else word.hindiWord,
                style = secondaryTypography.titleMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Pronunciation guide
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pronunciation: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Text(
                    text = if (isHindiFirst) {
                        word.hindiPronunciation ?: ""
                    } else {
                        word.englishPronunciation ?: ""
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Play pronunciation button
                IconButton(
                    onClick = { onPlayPronunciationClick(word, isHindiFirst) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play pronunciation",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Mastery progress bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mastery: ${word.masteryLevel}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Text(
                        text = word.getMasteryLevelName(),
                        style = MaterialTheme.typography.bodySmall,
                        color = progressColor
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = progressColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Round
                )
            }
            
            // Category and difficulty
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category chip
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = word.category,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                
                // Difficulty stars
                Row {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = if (index < word.difficulty) 
                                MaterialTheme.colorScheme.secondary 
                            else 
                                MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                // Expand/collapse button
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Show less" else "Show more",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            // Expanded content (example sentences, etc.)
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    
                    // Example sentences if available
                    if (!word.exampleSentenceEnglish.isNullOrBlank() && !word.exampleSentenceHindi.isNullOrBlank()) {
                        Text(
                            text = "Example:",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        // Example in English
                        Text(
                            text = word.exampleSentenceEnglish,
                            style = englishTypography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        // Example in Hindi
                        Text(
                            text = word.exampleSentenceHindi,
                            style = hindiTypography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    
                    // Notes if available
                    if (!word.notes.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Notes:",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = word.notes,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    
                    // Last practiced date if available
                    word.lastPracticed?.let { lastPracticed ->
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Last practiced: ${lastPracticed.toString().split(" ")[0]}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordCardPreview() {
    val sampleWord = Word(
        id = 1,
        englishWord = "Hello",
        hindiWord = "नमस्ते",
        englishPronunciation = "heh-loh",
        hindiPronunciation = "nuh-muh-stay",
        category = "Greetings",
        difficulty = 2,
        isFavorite = true,
        timeAdded = Date(),
        lastPracticed = Date(),
        masteryLevel = 70,
        exampleSentenceEnglish = "Hello, how are you?",
        exampleSentenceHindi = "नमस्ते, आप कैसे हैं?",
        notes = "This is a common greeting used in both formal and informal situations."
    )
    
    MaterialTheme {
        WordCard(
            word = sampleWord,
            onCardClick = {},
            onFavoriteClick = {},
            onPlayPronunciationClick = { _, _ -> }
        )
    }
}