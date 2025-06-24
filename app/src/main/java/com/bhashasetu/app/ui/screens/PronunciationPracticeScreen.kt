package com.bhashasetu.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.ui.viewmodel.WordViewModel
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PronunciationPracticeScreen(
    onNavigateBack: () -> Unit,
    viewModel: WordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val words by viewModel.getAllWords().collectAsStateWithLifecycle(initialValue = emptyList())

    var currentWordIndex by remember { mutableIntStateOf(0) }
    var isRecording by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var recordingProgress by remember { mutableFloatStateOf(0f) }
    var pronunciationScore by remember { mutableIntStateOf(-1) }
    var showFeedback by remember { mutableStateOf(false) }
    var audioAmplitude by remember { mutableFloatStateOf(0f) }
    var practiceSessionStats by remember { mutableStateOf(PracticeStats()) }

    // Mock audio amplitude animation
    LaunchedEffect(isRecording) {
        while (isRecording) {
            audioAmplitude = Random.nextFloat() * 100f
            delay(50)
        }
        if (!isRecording) {
            audioAmplitude = 0f
        }
    }

    // Practice words (first 8 words for focused practice)
    val practiceWords = remember(words) { words.take(8) }

    fun nextWord() {
        if (currentWordIndex < practiceWords.size - 1) {
            currentWordIndex++
            pronunciationScore = -1
            showFeedback = false
        }
    }

    fun previousWord() {
        if (currentWordIndex > 0) {
            currentWordIndex--
            pronunciationScore = -1
            showFeedback = false
        }
    }

    fun startRecording() {
        isRecording = true
        recordingProgress = 0f
        pronunciationScore = -1
        showFeedback = false

        // Simulate recording progress
        kotlin.run {
            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                for (i in 1..100) {
                    delay(30)
                    recordingProgress = i / 100f
                }
                stopRecording()
            }
        }
    }

    fun stopRecording() {
        isRecording = false
        recordingProgress = 1f

        // Simulate pronunciation analysis
        kotlin.run {
            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                delay(1000)
                // Generate random score for demo (70-100%)
                pronunciationScore = Random.nextInt(70, 101)
                showFeedback = true

                // Update practice stats
                practiceSessionStats = practiceSessionStats.copy(
                    wordsAttempted = practiceSessionStats.wordsAttempted + 1,
                    averageScore = ((practiceSessionStats.averageScore * (practiceSessionStats.wordsAttempted - 1)) + pronunciationScore) / practiceSessionStats.wordsAttempted,
                    bestScore = maxOf(practiceSessionStats.bestScore, pronunciationScore)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Pronunciation Practice",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* TODO: Open practice settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        if (practiceWords.isNotEmpty()) {
            val currentWord = practiceWords[currentWordIndex]

            // Practice Session Stats
            PracticeStatsCard(practiceSessionStats)

            // Main Practice Area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Word Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Practice Word ${currentWordIndex + 1}/${practiceWords.size}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = currentWord.english,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentWord.hindi,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Pronunciation Guide
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = "Pronunciation: ${currentWord.pronunciation ?: "/ˈɪŋɡlɪʃ/"}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(12.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Play Audio Button
                        Button(
                            onClick = {
                                isPlaying = true
                                // TODO: Play audio pronunciation
                                kotlin.run {
                                    kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                                        delay(2000)
                                        isPlaying = false
                                    }
                                }
                            },
                            enabled = !isPlaying && !isRecording,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            if (isPlaying) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(Icons.Default.VolumeUp, contentDescription = null)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isPlaying) "Playing..." else "Listen")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Audio Visualization
                AudioWaveform(
                    amplitude = audioAmplitude,
                    isRecording = isRecording,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Recording Button
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    if (isRecording) {
                        CircularProgressIndicator(
                            progress = recordingProgress,
                            modifier = Modifier.size(120.dp),
                            strokeWidth = 6.dp,
                            color = Color.Red
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = if (isRecording) Color.Red else MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(100.dp)
                            .clickable(enabled = !isPlaying) {
                                if (isRecording) stopRecording() else startRecording()
                            }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = if (isRecording) "Stop Recording" else "Start Recording",
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = when {
                        isRecording -> "Recording... Speak clearly!"
                        isPlaying -> "Playing pronunciation..."
                        else -> "Tap microphone to practice"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Feedback Section
                AnimatedVisibility(
                    visible = showFeedback,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    PronunciationFeedback(
                        score = pronunciationScore,
                        onNextWord = ::nextWord,
                        onRetry = {
                            pronunciationScore = -1
                            showFeedback = false
                        },
                        hasNextWord = currentWordIndex < practiceWords.size - 1
                    )
                }
            }

            // Navigation Controls
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = ::previousWord,
                        enabled = currentWordIndex > 0,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.NavigateBefore, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Previous")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "${currentWordIndex + 1} / ${practiceWords.size}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(
                        onClick = ::nextWord,
                        enabled = currentWordIndex < practiceWords.size - 1,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Next")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.NavigateNext, contentDescription = null)
                    }
                }
            }
        } else {
            // Loading State
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading pronunciation practice...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun AudioWaveform(
    amplitude: Float,
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedAmplitude by animateFloatAsState(
        targetValue = if (isRecording) amplitude else 0f,
        animationSpec = tween(100),
        label = "amplitude"
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val centerY = size.height / 2
            val barWidth = size.width / 50
            val spacing = barWidth * 0.5f

            for (i in 0 until 50) {
                val x = i * (barWidth + spacing)
                val normalizedAmplitude = (animatedAmplitude / 100f).coerceIn(0f, 1f)
                val barHeight = (20 + normalizedAmplitude * (size.height - 40)) *
                        (0.5f + 0.5f * sin(i * 0.5f + System.currentTimeMillis() * 0.01f).toFloat())

                drawLine(
                    color = if (isRecording) Color.Red else Color.Gray,
                    start = Offset(x, centerY - barHeight / 2),
                    end = Offset(x, centerY + barHeight / 2),
                    strokeWidth = barWidth
                )
            }
        }
    }
}

@Composable
private fun PronunciationFeedback(
    score: Int,
    onNextWord: () -> Unit,
    onRetry: () -> Unit,
    hasNextWord: Boolean
) {
    val isExcellent = score >= 90
    val isGood = score >= 75
    val isOkay = score >= 60

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isExcellent -> Color.Green.copy(alpha = 0.1f)
                isGood -> Color.Blue.copy(alpha = 0.1f)
                isOkay -> Color.Yellow.copy(alpha = 0.1f)
                else -> Color.Red.copy(alpha = 0.1f)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = when {
                    isExcellent -> Icons.Default.Stars
                    isGood -> Icons.Default.ThumbUp
                    isOkay -> Icons.Default.ThumbUpOffAlt
                    else -> Icons.Default.Refresh
                },
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = when {
                    isExcellent -> Color.Green
                    isGood -> Color.Blue
                    isOkay -> Color.Orange
                    else -> Color.Red
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$score%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = when {
                    isExcellent -> Color.Green
                    isGood -> Color.Blue
                    isOkay -> Color.Orange
                    else -> Color.Red
                }
            )

            Text(
                text = when {
                    isExcellent -> "Excellent pronunciation!"
                    isGood -> "Great job! Almost perfect."
                    isOkay -> "Good attempt. Keep practicing!"
                    else -> "Try again. Focus on clarity."
                },
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tips
            Text(
                text = when {
                    isExcellent -> "Perfect! You've mastered this word."
                    isGood -> "Tip: Pay attention to the ending sounds."
                    isOkay -> "Tip: Try speaking more slowly and clearly."
                    else -> "Tip: Listen to the audio again and mimic the pronunciation."
                },
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (hasNextWord) Arrangement.SpaceBetween else Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = onRetry,
                    modifier = if (hasNextWord) Modifier.weight(1f) else Modifier
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Try Again")
                }

                if (hasNextWord) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = onNextWord,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Next Word")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.NavigateNext, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
private fun PracticeStatsCard(stats: PracticeStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "Attempted",
                value = stats.wordsAttempted.toString(),
                icon = Icons.Default.Assignment
            )

            StatItem(
                label = "Average",
                value = "${stats.averageScore.toInt()}%",
                icon = Icons.Default.TrendingUp
            )

            StatItem(
                label = "Best Score",
                value = "${stats.bestScore}%",
                icon = Icons.Default.Stars
            )
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private data class PracticeStats(
    val wordsAttempted: Int = 0,
    val averageScore: Float = 0f,
    val bestScore: Int = 0
)