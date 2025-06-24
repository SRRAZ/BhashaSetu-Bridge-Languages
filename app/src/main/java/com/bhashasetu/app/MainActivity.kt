package com.bhashasetu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bhashasetu.app.ui.screens.MainScreen
import com.bhashasetu.app.ui.theme.AppLanguage
import com.bhashasetu.app.ui.theme.EnglishHindiTheme
import com.bhashasetu.app.ui.theme.LocalAppLanguage
import com.bhashasetu.app.util.LanguageManager

/**
 * Main Activity using Jetpack Compose
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        // Get language preferences
        val languageManager = (application as EnglishHindiComposeApplication).languageManager
        val currentLanguage = languageManager.getCurrentLanguage()
        val isHindi = currentLanguage == LanguageManager.LANGUAGE_HINDI
        
        setContent {
            // Remember language state
            var appLanguage by remember { 
                mutableStateOf(if (isHindi) AppLanguage.HINDI else AppLanguage.ENGLISH) 
            }
            // Apply theme with language settings
            EnglishHindiTheme(
                language = appLanguage,
                dynamicColor = true // Use Material You dynamic colors on Android 12+
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}