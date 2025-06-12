package com.bhashasetu.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhashasetu.app.ui.theme.AppLanguage
import com.bhashasetu.app.ui.theme.LocalAppLanguage
import com.bhashasetu.app.ui.theme.localizedString

/**
 * Main screen with bottom navigation and content area
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Language setting
    val currentLanguage = LocalAppLanguage.current
    val isHindi = currentLanguage == AppLanguage.HINDI
    
    // Bottom navigation items
    val bottomNavItems = listOf(
        BottomNavItem(
            title = localizedString("Home", "होम"),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "home"
        ),
        BottomNavItem(
            title = localizedString("Lessons", "पाठ"),
            selectedIcon = Icons.Filled.Book,
            unselectedIcon = Icons.Outlined.Book,
            route = "lessons"
        ),
        BottomNavItem(
            title = localizedString("Practice", "अभ्यास"),
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            route = "practice"
        ),
        BottomNavItem(
            title = localizedString("Profile", "प्रोफाइल"),
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "profile"
        )
    )
    
    // Whether to show bottom navigation
    val showBottomNav = remember(currentRoute) {
        // Routes where bottom nav should be shown
        val bottomNavRoutes = listOf(
            "home", "lessons", "practice", "profile",
            "wordList", "wordDetail", "lessonList", "lessonDetail"
        )
        currentRoute in bottomNavRoutes
    }
    
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNav,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            selected = selected,
                            onClick = {
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
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        onNavigateToLessons = { navController.navigate("lessons") },
                        onNavigateToPractice = { navController.navigate("practice") },
                        onNavigateToWordList = { navController.navigate("wordList") }
                    )
                }
                
                composable("lessons") {
                    Text("Lessons Screen - Coming Soon")
                    // LessonListScreen(
                    //     onLessonClick = { lessonId -> 
                    //         navController.navigate("lessonDetail/$lessonId") 
                    //     }
                    // )
                }
                
                composable("practice") {
                    Text("Practice Screen - Coming Soon")
                    // PracticeScreen(
                    //     onPracticeTypeSelected = { type ->
                    //         navController.navigate("practice/$type")
                    //     }
                    // )
                }
                
                composable("profile") {
                    Text("Profile Screen - Coming Soon")
                    // ProfileScreen(
                    //     onSettingsClick = { navController.navigate("settings") }
                    // )
                }
                
                composable("wordList") {
                    WordListScreen(
                        onWordClick = { word ->
                            navController.navigate("wordDetail/${word.id}")
                        },
                        onAddWordClick = {
                            navController.navigate("addWord")
                        }
                    )
                }
                
                // Additional routes will be added as screens are implemented
            }
        }
    }
}

/**
 * Data class for bottom navigation items
 */
private data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)