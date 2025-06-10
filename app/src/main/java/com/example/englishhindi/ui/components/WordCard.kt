package com.bhashasetu.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.ui.theme.EnglishHindiTheme
import java.util.Date

/**
 * A card component for displaying a vocabulary word in both English and Hindi.
 *
 * @param word The Word object to display
 * @param currentLanguage The currently selected language code ("en" or "hi")
 * @param onFavoriteClick Callback when the favorite button is clicked
 * @param onPlayPronunciationClick Callback when the pronunciation button is clicked
 * @param onClick Callback when the card is clicked
 * @param showDetails Whether to show detailed information about the word
 * @param modifier Optional modifier for styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordCard(
    word: Word,
    currentLanguage: String,
    onFavoriteClick: () -> Unit,
    onPlayPronunciationClick: () -> Unit,
    onClick: () -> Unit,
    showDetails: Boolean = false,
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
                // Primary language word based on selected language
                Text(
                    text = if (currentLanguage == "hi") word.hindiWord else word.englishWord,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                // Favorite button
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (word.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (word.isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (word.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Secondary language word
            Text(
                text = if (currentLanguage == "hi") word.englishWord else word.hindiWord,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Pronunciation
            if (word.pronunciation.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = word.pronunciation,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Play pronunciation button
                    IconButton(
                        onClick = onPlayPronunciationClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play pronunciation",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Difficulty indicator
            LinearProgressIndicator(
                progress = { word.difficulty.toFloat() / 5 },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .height(4.dp),
                color = when (word.difficulty) {
                    1 -> Color.Green
                    2 -> Color(0xFF8BC34A) // Light Green
                    3 -> Color(0xFFFFC107) // Amber
                    4 -> Color(0xFFFF9800) // Orange
                    else -> Color.Red
                }
            )

            // Parts of speech
            Text(
                text = word.partsOfSpeech,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Show details if requested
            if (showDetails) {
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                // Definition
                Text(
                    text = if (currentLanguage == "hi") "अर्थ:" else "Definition:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (currentLanguage == "hi") word.hindiDefinition else word.englishDefinition,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )

                // Example (if available)
                if (!word.englishExample.isNullOrEmpty() && !word.hindiExample.isNullOrEmpty()) {
                    Text(
                        text = if (currentLanguage == "hi") "उदाहरण:" else "Example:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (currentLanguage == "hi") word.hindiExample else word.englishExample,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }

                // Synonyms (if available)
                if (!word.synonyms.isNullOrEmpty()) {
                    Text(
                        text = if (currentLanguage == "hi") "समानार्थी शब्द:" else "Synonyms:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = word.synonyms,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }

                // Antonyms (if available)
                if (!word.antonyms.isNullOrEmpty()) {
                    Text(
                        text = if (currentLanguage == "hi") "विपरीतार्थक शब्द:" else "Antonyms:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = word.antonyms,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }

                // Notes (if available)
                if (!word.notes.isNullOrEmpty()) {
                    Text(
                        text = if (currentLanguage == "hi") "नोट्स:" else "Notes:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = word.notes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
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
            WordCard(
                word = sampleWord,
                currentLanguage = "en",
                onFavoriteClick = {},
                onPlayPronunciationClick = {},
                onClick = {},
                showDetails = true
            )
        }
    }
}