package com.example.englishhindi

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.example.englishhindi.ui.screens.MainScreen
import com.example.englishhindi.ui.theme.AppLanguage
import com.example.englishhindi.ui.theme.EnglishHindiTheme
import com.example.englishhindi.ui.theme.LocalAppLanguage
import com.example.englishhindi.util.LanguageManager

/**
 * Main Activity using Jetpack Compose
 */
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