package com.example.englishhindi.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat

// Custom composition locals for language-specific typography
val LocalAppLanguage = compositionLocalOf { AppLanguage.ENGLISH }
val LocalTypography = compositionLocalOf { englishTypography }
val LocalFontFamily = compositionLocalOf { robotoFamily }

// Language options for the app
enum class AppLanguage {
    ENGLISH,
    HINDI
}

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = onPrimary,
    primaryContainer = primaryLightColor,
    onPrimaryContainer = onPrimary,
    secondary = secondaryColor,
    onSecondary = onSecondary,
    secondaryContainer = secondaryLightColor,
    onSecondaryContainer = onSecondary,
    tertiary = accentColor,
    onTertiary = onPrimary,
    tertiaryContainer = accentLightColor,
    onTertiaryContainer = onPrimary,
    error = error,
    onError = onError,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface
)

// Dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = primaryDarkTheme,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryDarkDarkTheme,
    onPrimaryContainer = onPrimaryDark,
    secondary = secondaryColor,
    onSecondary = onPrimary,
    secondaryContainer = secondaryDarkColor,
    onSecondaryContainer = onPrimary,
    tertiary = accentColor,
    onTertiary = onPrimary,
    tertiaryContainer = accentDarkColor,
    onTertiaryContainer = onPrimary,
    error = error,
    onError = onError,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark
)

@Composable
fun EnglishHindiTheme(
    language: AppLanguage = AppLanguage.ENGLISH,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Determine the appropriate typography and font family based on language
    val typography = if (language == AppLanguage.HINDI) hindiTypography else englishTypography
    val fontFamily = if (language == AppLanguage.HINDI) notoSansDevanagariFamily else robotoFamily
    
    // Set up the color scheme based on dark theme or dynamic colors
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    // Apply the status bar color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    
    // Provide composition locals for language and typography
    CompositionLocalProvider(
        LocalAppLanguage provides language,
        LocalTypography provides typography,
        LocalFontFamily provides fontFamily
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}

// Helper function to get localized string for UI elements
@Composable
fun localizedString(english: String, hindi: String): String {
    val language = LocalAppLanguage.current
    return if (language == AppLanguage.HINDI) hindi else english
}