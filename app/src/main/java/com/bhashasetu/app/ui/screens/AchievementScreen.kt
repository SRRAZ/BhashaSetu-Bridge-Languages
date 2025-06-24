package com.bhashasetu.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bhashasetu.app.data.model.Achievement
import com.bhashasetu.app.ui.viewmodel.AchievementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementScreen(
    onNavigateBack: () -> Unit,
    viewModel: AchievementViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val achievements by viewModel.achievements.collectAsStateWithLifecycle()
    val userStats by viewModel.userStats.collectAsStateWithLifecycle()

    var selectedCategory by remember { mutableStateOf("All") }
    var showUnlockedOnly by remember { mutableStateOf(false) }

    val categories = listOf("All", "Learning", "Streak", "Quiz", "Social", "Special")

    val filteredAchievements = achievements.filter { achievement ->
        val categoryMatch = selectedCategory == "All" || achievement.category == selectedCategory
        val unlockedMatch = !showUnlockedOnly || achievement.isUnlocked
        categoryMatch && unlockedMatch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Achievements",
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
                IconButton(
                    onClick = { showUnlockedOnly = !showUnlockedOnly }
                ) {
                    Icon(
                        if (showUnlockedOnly) Icons.Default.FilterList else Icons.Default.FilterListOff,
                        contentDescription = "Filter Unlocked"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        // User Statistics
        UserStatsCard(userStats)

        // Category Filter
        LazyColumn {
            item {
                ScrollableTabRow(
                    selectedTabIndex = categories.indexOf(selectedCategory),
                    modifier = Modifier.padding(vertical = 8.dp),
                    edgePadding = 16.dp
                ) {
                    categories.forEach { category ->
                        Tab(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            text = {
                                Text(
                                    category,
                                    fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
            }

            // Achievement Grid
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredAchievements) { achievement ->
                        AchievementCard(
                            achievement = achievement,
                            onClick = {
                                // TODO: Show achievement details
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserStatsCard(stats: UserStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Level ${stats.level}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${stats.totalXP} XP",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Level Progress Circle
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = stats.levelProgress,
                        modifier = Modifier.size(60.dp),
                        strokeWidth = 6.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${(stats.levelProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.EmojiEvents,
                    value = stats.achievementsUnlocked.toString(),
                    label = "Achievements",
                    color = Color(0xFFFFD700) // Gold
                )

                StatItem(
                    icon = Icons.Default.LocalFireDepartment,
                    value = stats.currentStreak.toString(),
                    label = "Day Streak",
                    color = Color(0xFFFF6B35) // Orange-red
                )

                StatItem(
                    icon = Icons.Default.Quiz,
                    value = stats.quizzesCompleted.toString(),
                    label = "Quizzes",
                    color = MaterialTheme.colorScheme.secondary
                )

                StatItem(
                    icon = Icons.Default.School,
                    value = stats.wordsLearned.toString(),
                    label = "Words",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = color.copy(alpha = 0.2f),
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = color
                )
            }
        }

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

@Composable
private fun AchievementCard(
    achievement: Achievement,
    onClick: () -> Unit
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(achievement.isUnlocked) {
        if (achievement.isUnlocked) {
            scale.animateTo(
                targetValue = 1.1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring()
            )
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .scale(scale.value)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (achievement.isUnlocked) 8.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.isUnlocked) {
                when (achievement.rarity) {
                    "Legendary" -> Color(0xFFFFD700).copy(alpha = 0.1f)
                    "Epic" -> Color(0xFF9C27B0).copy(alpha = 0.1f)
                    "Rare" -> Color(0xFF2196F3).copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }
        ),
        border = if (achievement.isUnlocked) {
            BorderStroke(
                2.dp,
                when (achievement.rarity) {
                    "Legendary" -> Color(0xFFFFD700)
                    "Epic" -> Color(0xFF9C27B0)
                    "Rare" -> Color(0xFF2196F3)
                    else -> MaterialTheme.colorScheme.primary
                }
            )
        } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Achievement Icon
            Box(
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = if (achievement.isUnlocked) {
                        when (achievement.rarity) {
                            "Legendary" -> Color(0xFFFFD700).copy(alpha = 0.3f)
                            "Epic" -> Color(0xFF9C27B0).copy(alpha = 0.3f)
                            "Rare" -> Color(0xFF2196F3).copy(alpha = 0.3f)
                            else -> MaterialTheme.colorScheme.primaryContainer
                        }
                    } else {
                        Color.Gray.copy(alpha = 0.2f)
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = getAchievementIcon(achievement.type),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = if (achievement.isUnlocked) {
                                when (achievement.rarity) {
                                    "Legendary" -> Color(0xFFFFD700)
                                    "Epic" -> Color(0xFF9C27B0)
                                    "Rare" -> Color(0xFF2196F3)
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            } else {
                                Color.Gray
                            }
                        )
                    }
                }

                // Unlock Animation
                if (achievement.isUnlocked && achievement.rarity in listOf("Epic", "Legendary")) {
                    AnimatedVisibility(
                        visible = true,
                        enter = scaleIn() + fadeIn()
                    ) {
                        Icon(
                            Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .scale(1.2f),
                            tint = Color(0xFFFFD700).copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Achievement Title
            Text(
                text = achievement.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (achievement.isUnlocked) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    Color.Gray
                },
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Progress or Status
            if (achievement.isUnlocked) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Green.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "UNLOCKED",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            } else {
                Text(
                    text = "${achievement.progress}/${achievement.target}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                LinearProgressIndicator(
                    progress = achievement.progress.toFloat() / achievement.target,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(1.5.dp)),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            }

            // Rarity Indicator
            if (achievement.rarity != "Common") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = achievement.rarity,
                    style = MaterialTheme.typography.labelSmall,
                    color = when (achievement.rarity) {
                        "Legendary" -> Color(0xFFFFD700)
                        "Epic" -> Color(0xFF9C27B0)
                        "Rare" -> Color(0xFF2196F3)
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun getAchievementIcon(type: String): ImageVector {
    return when (type) {
        "first_lesson" -> Icons.Default.School
        "streak_3" -> Icons.Default.LocalFireDepartment
        "streak_7" -> Icons.Default.LocalFireDepartment
        "streak_30" -> Icons.Default.LocalFireDepartment
        "perfect_quiz" -> Icons.Default.Stars
        "quiz_master" -> Icons.Default.Quiz
        "word_collector" -> Icons.Default.Collections
        "pronunciation_expert" -> Icons.Default.RecordVoiceOver
        "speed_learner" -> Icons.Default.Speed
        "night_owl" -> Icons.Default.Nightlight
        "early_bird" -> Icons.Default.WbSunny
        "social_learner" -> Icons.Default.Group
        "achievement_hunter" -> Icons.Default.EmojiEvents
        else -> Icons.Default.Star
    }
}

// Data classes for the UI
data class UserStats(
    val level: Int = 1,
    val totalXP: Int = 0,
    val levelProgress: Float = 0f,
    val achievementsUnlocked: Int = 0,
    val currentStreak: Int = 0,
    val quizzesCompleted: Int = 0,
    val wordsLearned: Int = 0
)