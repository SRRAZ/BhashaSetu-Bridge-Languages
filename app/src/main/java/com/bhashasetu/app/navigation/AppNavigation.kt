package com.bhashasetu.app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhashasetu.app.ui.screens.*
import com.bhashasetu.app.ui.viewmodel.AppSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    settingsViewModel: AppSettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Bottom navigation items
    val bottomNavItems = listOf(
        BottomNavItem(
            route = NavigationRoutes.HOME,
            icon = Icons.Default.Home,
            labelEnglish = "Home",
            labelHindi = "होम"
        ),
        BottomNavItem(
            route = NavigationRoutes.LESSONS,
            icon = Icons.Default.School,
            labelEnglish = "Lessons",
            labelHindi = "पाठ"
        ),
        BottomNavItem(
            route = NavigationRoutes.PRACTICE,
            icon = Icons.Default.RecordVoiceOver,
            labelEnglish = "Practice",
            labelHindi = "अभ्यास"
        ),
        BottomNavItem(
            route = NavigationRoutes.ACHIEVEMENTS,
            icon = Icons.Default.EmojiEvents,
            labelEnglish = "Achievements",
            labelHindi = "उपलब्धियां"
        ),
        BottomNavItem(
            route = NavigationRoutes.PROFILE,
            icon = Icons.Default.Person,
            labelEnglish = "Profile",
            labelHindi = "प्रोफ़ाइल"
        )
    )

    Scaffold(
        bottomBar = {
            // Show bottom navigation only for main screens
            if (shouldShowBottomNav(currentDestination?.route)) {
                NavigationBar(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.labelEnglish
                                )
                            },
                            label = {
                                Text(
                                    text = item.labelEnglish, // TODO: Use language from settings
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    }
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                if (currentDestination?.route != item.route) {
                                    navController.navigate(item.route) {
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.HOME,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            // Home Screen
            composable(NavigationRoutes.HOME) {
                HomeScreen(
                    onNavigateToLessons = {
                        navController.navigate(NavigationRoutes.LESSONS)
                    },
                    onNavigateToQuiz = { lessonId ->
                        navController.navigate("${NavigationRoutes.QUIZ}/$lessonId")
                    },
                    onNavigateToPractice = {
                        navController.navigate(NavigationRoutes.PRACTICE)
                    },
                    onNavigateToAchievements = {
                        navController.navigate(NavigationRoutes.ACHIEVEMENTS)
                    }
                )
            }

            // Lessons List Screen
            composable(NavigationRoutes.LESSONS) {
                LessonListScreen(
                    onNavigateToLesson = { lessonId ->
                        navController.navigate("${NavigationRoutes.LESSON}/$lessonId")
                    },
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            // Individual Lesson Screen
            composable("${NavigationRoutes.LESSON}/{lessonId}") { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "1"
                LessonScreen(
                    lessonId = lessonId,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onNavigateToQuiz = { lessonId ->
                        navController.navigate("${NavigationRoutes.QUIZ}/$lessonId")
                    }
                )
            }

            // Quiz Screen
            composable("${NavigationRoutes.QUIZ}/{lessonId}") { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "1"
                QuizScreen(
                    lessonId = lessonId,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onQuizComplete = { score, total ->
                        // Navigate to quiz results or back to lesson
                        navController.navigateUp()
                    }
                )
            }

            // Pronunciation Practice Screen
            composable(NavigationRoutes.PRACTICE) {
                PronunciationPracticeScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            // Achievements Screen
            composable(NavigationRoutes.ACHIEVEMENTS) {
                AchievementScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            // Profile/Settings Screen
            composable(NavigationRoutes.PROFILE) {
                ProfileScreen(
                    onNavigateToSettings = {
                        navController.navigate(NavigationRoutes.SETTINGS)
                    },
                    onNavigateToAchievements = {
                        navController.navigate(NavigationRoutes.ACHIEVEMENTS)
                    }
                )
            }

            // Settings Screen
            composable(NavigationRoutes.SETTINGS) {
                SettingsScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            // Word Detail Screen
            composable("${NavigationRoutes.WORD_DETAIL}/{wordId}") { backStackEntry ->
                val wordId = backStackEntry.arguments?.getString("wordId") ?: "1"
                WordDetailScreen(
                    wordId = wordId,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            // Search Screen
            composable(NavigationRoutes.SEARCH) {
                SearchScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onNavigateToWord = { wordId ->
                        navController.navigate("${NavigationRoutes.WORD_DETAIL}/$wordId")
                    }
                )
            }

            // Statistics Screen
            composable(NavigationRoutes.STATISTICS) {
                StatisticsScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            // Favorites Screen
            composable(NavigationRoutes.FAVORITES) {
                FavoritesScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onNavigateToWord = { wordId ->
                        navController.navigate("${NavigationRoutes.WORD_DETAIL}/$wordId")
                    }
                )
            }
        }
    }
}

// Navigation helper functions
private fun shouldShowBottomNav(route: String?): Boolean {
    val mainRoutes = listOf(
        NavigationRoutes.HOME,
        NavigationRoutes.LESSONS,
        NavigationRoutes.PRACTICE,
        NavigationRoutes.ACHIEVEMENTS,
        NavigationRoutes.PROFILE
    )
    return route in mainRoutes
}

// Navigation routes constants
object NavigationRoutes {
    const val HOME = "home"
    const val LESSONS = "lessons"
    const val LESSON = "lesson"
    const val QUIZ = "quiz"
    const val PRACTICE = "practice"
    const val ACHIEVEMENTS = "achievements"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val WORD_DETAIL = "word_detail"
    const val SEARCH = "search"
    const val STATISTICS = "statistics"
    const val FAVORITES = "favorites"
}

// Data classes
data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelEnglish: String,
    val labelHindi: String
)

// Extension functions for NavController
fun NavHostController.navigateToLesson(lessonId: String) {
    navigate("${NavigationRoutes.LESSON}/$lessonId")
}

fun NavHostController.navigateToQuiz(lessonId: String) {
    navigate("${NavigationRoutes.QUIZ}/$lessonId")
}

fun NavHostController.navigateToWord(wordId: String) {
    navigate("${NavigationRoutes.WORD_DETAIL}/$wordId")
}

fun NavHostController.navigateUp(): Boolean {
    return navigateUp()
}

fun NavHostController.popBackStackSafely(): Boolean {
    return if (previousBackStackEntry != null) {
        popBackStack()
    } else {
        false
    }
}

// Navigation utilities
object NavigationUtils {
    fun getRouteWithArgs(route: String, vararg args: String): String {
        var result = route
        args.forEach { arg ->
            result = result.replaceFirst("{.*?}", arg)
        }
        return result
    }

    fun isMainDestination(route: String?): Boolean {
        return route in listOf(
            NavigationRoutes.HOME,
            NavigationRoutes.LESSONS,
            NavigationRoutes.PRACTICE,
            NavigationRoutes.ACHIEVEMENTS,
            NavigationRoutes.PROFILE
        )
    }

    fun getScreenTitle(route: String?, language: String = "en"): String {
        return when (route) {
            NavigationRoutes.HOME -> if (language == "hi") "होम" else "Home"
            NavigationRoutes.LESSONS -> if (language == "hi") "पाठ" else "Lessons"
            NavigationRoutes.PRACTICE -> if (language == "hi") "अभ्यास" else "Practice"
            NavigationRoutes.ACHIEVEMENTS -> if (language == "hi") "उपलब्धियां" else "Achievements"
            NavigationRoutes.PROFILE -> if (language == "hi") "प्रोफ़ाइल" else "Profile"
            NavigationRoutes.SETTINGS -> if (language == "hi") "सेटिंग्स" else "Settings"
            NavigationRoutes.SEARCH -> if (language == "hi") "खोजें" else "Search"
            NavigationRoutes.STATISTICS -> if (language == "hi") "आंकड़े" else "Statistics"
            NavigationRoutes.FAVORITES -> if (language == "hi") "पसंदीदा" else "Favorites"
            else -> if (language == "hi") "भाषा सेतु" else "BhashaSetu"
        }
    }
}

// Compose extension for safe navigation
@Composable
fun rememberAppNavController(): NavHostController {
    return rememberNavController()
}

// Navigation events
sealed class NavigationEvent {
    object NavigateUp : NavigationEvent()
    data class NavigateTo(val route: String) : NavigationEvent()
    data class NavigateToWithArgs(val route: String, val args: Map<String, String>) : NavigationEvent()
    data class PopBackStack(val route: String? = null, val inclusive: Boolean = false) : NavigationEvent()
}

// Navigation handler
class NavigationHandler(private val navController: NavHostController) {
    fun handleNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.NavigateUp -> {
                navController.navigateUp()
            }
            is NavigationEvent.NavigateTo -> {
                navController.navigate(event.route)
            }
            is NavigationEvent.NavigateToWithArgs -> {
                var route = event.route
                event.args.forEach { (key, value) ->
                    route = route.replace("{$key}", value)
                }
                navController.navigate(route)
            }
            is NavigationEvent.PopBackStack -> {
                if (event.route != null) {
                    navController.popBackStack(event.route, event.inclusive)
                } else {
                    navController.popBackStack()
                }
            }
        }
    }
}