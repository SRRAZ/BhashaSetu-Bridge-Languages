package com.bhashasetu.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhashasetu.app.navigation.AppNavigation
import com.bhashasetu.app.ui.common.ErrorHandling
import com.bhashasetu.app.ui.screens.*
import com.bhashasetu.app.ui.theme.AppLanguage
import com.bhashasetu.app.ui.theme.EnglishHindiTheme
import com.bhashasetu.app.ui.theme.LocalAppLanguage
import com.bhashasetu.app.ui.viewmodel.AppSettingsViewModel
import com.bhashasetu.app.ui.viewmodel.WordViewModel
import com.bhashasetu.app.ui.viewmodel.AchievementViewModel
import com.bhashasetu.app.util.LanguageManager
import com.bhashasetu.app.service.SyncService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Enhanced MainActivity with Jetpack Compose
 * Features:
 * - Complete navigation system with all screens
 * - Proper error handling and loading states
 * - Permission management for audio recording
 * - Background sync service integration
 * - Settings and language management
 * - Achievement system integration
 * - Comprehensive offline support
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // ViewModels
    private val appSettingsViewModel: AppSettingsViewModel by viewModels()
    private val wordViewModel: WordViewModel by viewModels()
    private val achievementViewModel: AchievementViewModel by viewModels()

    // Permission launcher for audio recording
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, user can now use pronunciation features
            Toast.makeText(this, "Microphone permission granted", Toast.LENGTH_SHORT).show()
        } else {
            // Permission denied, show explanation
            Toast.makeText(
                this,
                "Microphone permission is required for pronunciation practice",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Notification permission launcher (Android 13+)
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Notification permission granted
            Toast.makeText(this, "Notifications enabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen with custom logic
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Keep splash screen longer if needed
        splashScreen.setKeepOnScreenCondition {
            // Keep splash screen while loading initial data
            !wordViewModel.isInitialized.value
        }

        // Request permissions
        checkAndRequestPermissions()

        // Start background sync service
        startSyncService()

        setContent {
            BhashaSetuApp()
        }
    }

    @Composable
    private fun BhashaSetuApp() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        // Collect app settings
        val appSettings by appSettingsViewModel.appSettings.collectAsStateWithLifecycle()
        val isLoading by appSettingsViewModel.isLoading.collectAsStateWithLifecycle()
        val error by appSettingsViewModel.error.collectAsStateWithLifecycle()

        // Determine current language
        var appLanguage by remember {
            mutableStateOf(
                if (appSettings?.language == "hi") AppLanguage.HINDI else AppLanguage.ENGLISH
            )
        }

        // Update language when settings change
        LaunchedEffect(appSettings?.language) {
            appLanguage = if (appSettings?.language == "hi") AppLanguage.HINDI else AppLanguage.ENGLISH
        }

        // Apply theme with language and settings
        EnglishHindiTheme(
            language = appLanguage,
            dynamicColor = true,
            darkTheme = appSettings?.darkModeEnabled ?: false
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                if (isLoading) {
                    // Show loading screen
                    LoadingScreen()
                } else if (error != null) {
                    // Show error screen with retry option
                    ErrorScreen(
                        error = error,
                        onRetry = {
                            scope.launch {
                                appSettingsViewModel.refreshSettings()
                            }
                        }
                    )
                } else {
                    // Show main navigation
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    private fun LoadingScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.loading_app),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    @Composable
    private fun ErrorScreen(
        error: String,
        onRetry: () -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.error_loading_app),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
    }

    /**
     * Check and request necessary permissions
     */
    private fun checkAndRequestPermissions() {
        // Check microphone permission for pronunciation features
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

        // Check notification permission for Android 13+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * Start background sync service for offline content
     */
    private fun startSyncService() {
        try {
            val intent = Intent(this, SyncService::class.java)
            startService(intent)
        } catch (e: Exception) {
            // Handle service start failure gracefully
            android.util.Log.e("MainActivity", "Failed to start sync service", e)
        }
    }

    /**
     * Handle back button press in navigation
     */
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        // Refresh user progress and achievements when returning to app
        achievementViewModel.refreshAchievements()
        wordViewModel.refreshFavorites()
    }

    override fun onPause() {
        super.onPause()
        // Save any pending progress
        appSettingsViewModel.saveCurrentState()
    }

    companion object {
        private const val TAG = "MainActivity"

        // Intent extras
        const val EXTRA_NAVIGATE_TO_SCREEN = "navigate_to_screen"
        const val EXTRA_LESSON_ID = "lesson_id"
        const val EXTRA_WORD_ID = "word_id"

        // Screen navigation constants
        const val SCREEN_LESSONS = "lessons"
        const val SCREEN_PRACTICE = "practice"
        const val SCREEN_ACHIEVEMENTS = "achievements"
        const val SCREEN_SETTINGS = "settings"
    }
}

/**
 * Icons for material design
 */
private object Icons {
    val Default = androidx.compose.material.icons.Icons.Default
}