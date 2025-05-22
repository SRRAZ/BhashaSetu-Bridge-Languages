package com.example.englishhindi.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.core.os.ConfigurationCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

/**
 * Manages application language settings with Kotlin Flow support
 */
class LanguageManager(private val context: Context) {

    private val preferences = PreferenceManager(context)
    private val _currentLanguage = MutableStateFlow(getCurrentLanguage())
    
    /**
     * Language state flow that can be observed
     */
    val currentLanguageFlow: StateFlow<String> = _currentLanguage
    
    /**
     * Set application language
     */
    fun setLanguage(languageCode: String) {
        preferences.setString(PreferenceManager.KEY_LANGUAGE, languageCode)
        updateResources(languageCode)
        _currentLanguage.value = languageCode
    }
    
    /**
     * Get current language
     */
    fun getCurrentLanguage(): String {
        return preferences.getString(PreferenceManager.KEY_LANGUAGE, LANGUAGE_ENGLISH)
    }
    
    /**
     * Check if current language is Hindi
     */
    fun isHindiMode(): Boolean {
        return LANGUAGE_HINDI == getCurrentLanguage()
    }
    
    /**
     * Toggle between English and Hindi
     */
    fun toggleLanguage() {
        val newLanguage = if (isHindiMode()) LANGUAGE_ENGLISH else LANGUAGE_HINDI
        setLanguage(newLanguage)
    }
    
    /**
     * Create a context with updated language configuration
     */
    fun createContextWithLanguage(baseContext: Context): Context {
        val languageCode = getCurrentLanguage()
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val config = Configuration(baseContext.resources.configuration)
        
        // Apply locale based on API level
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            baseContext.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            @Suppress("DEPRECATION")
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            baseContext
        }
    }
    
    /**
     * Update resources with new language
     */
    @SuppressLint("NewApi")
    private fun updateResources(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val resources = context.resources
        val config = Configuration(resources.configuration)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
    
    /**
     * Get the appropriate system locale
     */
    fun getSystemLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0) ?: Locale.getDefault()
        } else {
            @Suppress("DEPRECATION")
            Resources.getSystem().configuration.locale
        }
    }
    
    /**
     * Detect if the system locale is Hindi
     */
    fun isSystemLocaleHindi(): Boolean {
        val locale = getSystemLocale()
        return locale.language == "hi"
    }
    
    /**
     * Get a localized string based on current language
     */
    fun getLocalizedString(english: String, hindi: String): String {
        return if (isHindiMode()) hindi else english
    }
    
    companion object {
        const val LANGUAGE_HINDI = "hi"
        const val LANGUAGE_ENGLISH = "en"
    }
}