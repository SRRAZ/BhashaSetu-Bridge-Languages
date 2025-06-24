package com.bhashasetu.app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhashasetu.app.R
import com.bhashasetu.app.ui.screens.*

/**
 * Comprehensive Navigation System for BhashaSetu App
 *
 * Features:
 * - Modern bottom navigation with all major screens
 * - Smooth animations between screens
 * - Type-safe navigation with sealed classes
 * - Deep link support
 * - State preservation
 * - Bilingual support
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(screen.titleResId),
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            // Home Screen
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToLessons = {
                        navController.navigate(Screen.Lessons.route)
                    },
                    onNavigateToQuiz = { lessonId ->
                        navController.navigate("${Screen.Quiz.route}/$lessonId")
                    },
                    onNavigateToPractice = {
                        navController.navigate(Screen.Practice.route)
                    }
                )
            }

            // Lessons Screen
            composable(Screen.Lessons.route) {
                LessonScreen(
                    onNavigateToLesson = { lessonId ->
                        navController.navigate("${Screen.LessonDetail.route}/$lessonId")
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Lesson Detail Screen
            composable("${Screen.LessonDetail.route}/{lessonId}") { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
                LessonDetailScreen(
                    lessonId = lessonId,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onStartQuiz = { quizLessonId ->
                        navController.navigate("${Screen.Quiz.route}/$quizLessonId")
                    }
                )
            }

            // Practice Screen
            composable(Screen.Practice.route) {
                PracticeMenuScreen(
                    onNavigateToPronunciation = {
                        navController.navigate(Screen.Pronunciation.route)
                    },
                    onNavigateToFlashcards = {
                        navController.navigate(Screen.Flashcards.route)
                    },
                    onNavigateToQuiz = {
                        navController.navigate(Screen.Quiz.route)
                    }
                )
            }

            // Pronunciation Practice Screen
            composable(Screen.Pronunciation.route) {
                PronunciationPracticeScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Quiz Screen
            composable(Screen.Quiz.route) {
                QuizScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onQuizComplete = { score ->
                        // Navigate to results or back to lessons
                        navController.popBackStack()
                    }
                )
            }

            // Quiz with Lesson ID
            composable("${Screen.Quiz.route}/{lessonId}") { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
                QuizScreen(
                    lessonId = lessonId,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onQuizComplete = { score ->
                        navController.popBackStack()
                    }
                )
            }

            // Flashcards Screen
            composable(Screen.Flashcards.route) {
                FlashcardsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Profile Screen
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToAchievements = {
                        navController.navigate(Screen.Achievements.route)
                    },
                    onNavigateToSettings = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }

            // Achievements Screen
            composable(Screen.Achievements.route) {
                AchievementScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Settings Screen
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Word List Screen
            composable(Screen.WordList.route) {
                WordListScreen(
                    onNavigateToWordDetail = { wordId ->
                        navController.navigate("${Screen.WordDetail.route}/$wordId")
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Word Detail Screen
            composable("${Screen.WordDetail.route}/{wordId}") { backStackEntry ->
                val wordId = backStackEntry.arguments?.getString("wordId") ?: ""
                WordDetailScreen(
                    wordId = wordId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

/**
 * Sealed class for type-safe navigation
 */
sealed class Screen(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Lessons : Screen("lessons", R.string.lessons, Icons.Default.School)
    object Practice : Screen("practice", R.string.practice, Icons.Default.FitnessCenter)
    object Profile : Screen("profile", R.string.profile, Icons.Default.Person)
    object Settings : Screen("settings", R.string.settings, Icons.Default.Settings)

    // Detail screens (not in bottom nav)
    object LessonDetail : Screen("lesson_detail", R.string.lesson_detail, Icons.Default.MenuBook)
    object Quiz : Screen("quiz", R.string.quiz, Icons.Default.Quiz)
    object Pronunciation : Screen("pronunciation", R.string.pronunciation, Icons.Default.RecordVoiceOver)
    object Flashcards : Screen("flashcards", R.string.flashcards, Icons.Default.Style)
    object Achievements : Screen("achievements", R.string.achievements, Icons.Default.EmojiEvents)
    object WordList : Screen("word_list", R.string.word_list, Icons.Default.List)
    object WordDetail : Screen("word_detail", R.string.word_detail, Icons.Default.Article)
}

/**
 * Bottom navigation items
 */
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Lessons,
    Screen.Practice,
    Screen.Profile
)

/**
 * Practice Menu Screen to organize different practice types
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PracticeMenuScreen(
    onNavigateToPronunciation: () -> Unit,
    onNavigateToFlashcards: () -> Unit,
    onNavigateToQuiz: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.practice_menu),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        PracticeOptionCard(
            title = stringResource(R.string.pronunciation_practice),
            description = stringResource(R.string.pronunciation_practice_desc),
            icon = Icons.Default.RecordVoiceOver,
            onClick = onNavigateToPronunciation
        )

        PracticeOptionCard(
            title = stringResource(R.string.flashcards),
            description = stringResource(R.string.flashcards_desc),
            icon = Icons.Default.Style,
            onClick = onNavigateToFlashcards
        )

        PracticeOptionCard(
            title = stringResource(R.string.quick_quiz),
            description = stringResource(R.string.quick_quiz_desc),
            icon = Icons.Default.Quiz,
            onClick = onNavigateToQuiz
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PracticeOptionCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Lesson Detail Screen placeholder
 */
@Composable
private fun LessonDetailScreen(
    lessonId: String,
    onNavigateBack: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lesson Detail: $lessonId",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onStartQuiz(lessonId) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Quiz")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

/**
 * Flashcards Screen placeholder
 */
@Composable
private fun FlashcardsScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Flashcards Practice",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

/**
 * Word Detail Screen placeholder
 */
@Composable
private fun WordDetailScreen(
    wordId: String,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Word Detail: $wordId",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}