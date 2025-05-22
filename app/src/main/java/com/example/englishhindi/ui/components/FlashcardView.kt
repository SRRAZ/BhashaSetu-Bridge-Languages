package com.example.englishhindi.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.englishhindi.data.model.Word
import com.example.englishhindi.ui.theme.EnglishHindiTheme
import java.util.*
import kotlin.math.abs

/**
 * A flashcard view for displaying and interacting with vocabulary words
 *
 * @param word The word to display
 * @param currentLanguage The current app language setting ("en" or "hi")
 * @param isFlipped Whether the card is showing the front (false) or back (true)
 * @param onFlip Callback when the card is flipped
 * @param onPlayPronunciation Callback when the pronunciation button is clicked
 * @param modifier Optional modifier for the component
 */
@Composable
fun FlashcardView(
    word: Word,
    currentLanguage: String,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onPlayPronunciation: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animation state for the flip
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "flipAnimation"
    )
    
    // Card content
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(16.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { onFlip() }
    ) {
        // Front of card (when rotation is < 90)
        if (rotation < 90f) {
            CardFace(
                word = word,
                currentLanguage = currentLanguage,
                isFront = true,
                onPlayPronunciation = onPlayPronunciation
            )
        } 
        // Back of card (when rotation is >= 90)
        else {
            CardFace(
                word = word,
                currentLanguage = currentLanguage,
                isFront = false,
                onPlayPronunciation = onPlayPronunciation,
                modifier = Modifier.graphicsLayer { rotationY = 180f } // Flip text to be readable
            )
        }
    }
}

/**
 * A single face (front or back) of the flashcard
 */
@Composable
private fun CardFace(
    word: Word,
    currentLanguage: String,
    isFront: Boolean,
    onPlayPronunciation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isFront) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Card label
            Text(
                text = if (isFront) {
                    if (currentLanguage == "hi") "हिंदी" else "English"
                } else {
                    if (currentLanguage == "hi") "English" else "हिंदी"
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.Start)
            )
            
            // Main word
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isFront) {
                        if (currentLanguage == "hi") word.hindiWord else word.englishWord
                    } else {
                        if (currentLanguage == "hi") word.englishWord else word.hindiWord
                    },
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            
            // Footer with part of speech and pronunciation
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Part of speech
                Text(
                    text = word.partsOfSpeech,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Pronunciation button (only show on the Hindi word side)
                if ((isFront && currentLanguage == "hi") || (!isFront && currentLanguage == "en")) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onPlayPronunciation() }
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play pronunciation",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = word.pronunciation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlashcardViewPreview() {
    val sampleWord = Word(
        id = 1,
        englishWord = "Apple",
        hindiWord = "सेब",
        pronunciation = "seb",
        englishDefinition = "A round fruit with red, yellow, or green skin and firm white flesh.",
        hindiDefinition = "एक गोल फल जिसका लाल, पीला या हरा छिलका और सफेद गूदा होता है।",
        englishExample = "I eat an apple every day.",
        hindiExample = "मैं हर दिन एक सेब खाता हूँ।",
        difficulty = 2,
        dateAdded = Date(),
        isFavorite = true,
        partsOfSpeech = "noun",
        synonyms = "fruit, produce",
        antonyms = ""
    )
    
    EnglishHindiTheme {
        Surface {
            var isFlipped by remember { mutableStateOf(false) }
            
            FlashcardView(
                word = sampleWord,
                currentLanguage = "en",
                isFlipped = isFlipped,
                onFlip = { isFlipped = !isFlipped },
                onPlayPronunciation = {}
            )
        }
    }
}