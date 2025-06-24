package com.bhashasetu.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.bhashasetu.app.data.model.AppSettings
import com.bhashasetu.app.ui.viewmodel.AppSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: AppSettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    var showLanguageDialog by remember { mutableStateOf(false) }
    var showDifficultyDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showGoalDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Learning Preferences Section
            item {
                SettingsSection(title = "Learning Preferences") {
                    Column {
                        SettingsItem(
                            icon = Icons.Default.Language,
                            title = "App Language",
                            subtitle = when (settings.language) {
                                "en" -> "English"
                                "hi" -> "हिंदी (Hindi)"
                                else -> "English"
                            },
                            onClick = { showLanguageDialog = true }
                        )

                        SettingsItem(
                            icon = Icons.Default.TrendingUp,
                            title = "Difficulty Level",
                            subtitle = settings.difficultyLevel,
                            onClick = { showDifficultyDialog = true }
                        )

                        SettingsItem(
                            icon = Icons.Default.Timer,
                            title = "Daily Goal",
                            subtitle = "${settings.dailyGoalMinutes} minutes per day",
                            onClick = { showGoalDialog = true }
                        )

                        SettingsSwitchItem(
                            icon = Icons.Default.CloudDownload,
                            title = "Offline Mode",
                            subtitle = "Download content for offline learning",
                            checked = settings.offlineModeEnabled,
                            onCheckedChange = { viewModel.updateOfflineMode(it) }
                        )
                    }
                }
            }

            // Notifications Section
            item {
                SettingsSection(title = "Notifications") {
                    Column {
                        SettingsSwitchItem(
                            icon = Icons.Default.Notifications,
                            title = "Push Notifications",
                            subtitle = "Receive learning reminders and updates",
                            checked = settings.notificationsEnabled,
                            onCheckedChange = { viewModel.updateNotifications(it) }
                        )

                        AnimatedVisibility(visible = settings.notificationsEnabled) {
                            Column {
                                SettingsItem(
                                    icon = Icons.Default.Schedule,
                                    title = "Daily Reminder",
                                    subtitle = "Remind me at ${settings.dailyReminderTime}",
                                    onClick = { showTimePickerDialog = true },
                                    indented = true
                                )

                                SettingsSwitchItem(
                                    icon = Icons.Default.EmojiEvents,
                                    title = "Achievement Notifications",
                                    subtitle = "Get notified about new achievements",
                                    checked = settings.achievementNotificationsEnabled,
                                    onCheckedChange = { viewModel.updateAchievementNotifications(it) },
                                    indented = true
                                )
                            }
                        }
                    }
                }
            }

            // Audio & Visual Section
            item {
                SettingsSection(title = "Audio & Visual") {
                    Column {
                        SettingsSwitchItem(
                            icon = Icons.Default.DarkMode,
                            title = "Dark Mode",
                            subtitle = "Use dark theme for better night reading",
                            checked = settings.darkModeEnabled,
                            onCheckedChange = { viewModel.updateDarkMode(it) }
                        )

                        SettingsSwitchItem(
                            icon = Icons.Default.VolumeUp,
                            title = "Audio Pronunciation",
                            subtitle = "Play audio for word pronunciations",
                            checked = settings.audioEnabled,
                            onCheckedChange = { viewModel.updateAudioEnabled(it) }
                        )

                        SettingsSwitchItem(
                            icon = Icons.Default.Animation,
                            title = "Animations",
                            subtitle = "Enable smooth transitions and effects",
                            checked = settings.animationsEnabled,
                            onCheckedChange = { viewModel.updateAnimations(it) }
                        )
                    }
                }
            }

            // Privacy & Data Section
            item {
                SettingsSection(title = "Privacy & Data") {
                    Column {
                        SettingsSwitchItem(
                            icon = Icons.Default.Analytics,
                            title = "Usage Analytics",
                            subtitle = "Help improve the app by sharing usage data",
                            checked = settings.analyticsEnabled,
                            onCheckedChange = { viewModel.updateAnalytics(it) }
                        )

                        SettingsItem(
                            icon = Icons.Default.DeleteSweep,
                            title = "Clear Learning Data",
                            subtitle = "Reset your progress and statistics",
                            onClick = { /* TODO: Show confirmation dialog */ },
                            destructive = true
                        )

                        SettingsItem(
                            icon = Icons.Default.Download,
                            title = "Export Data",
                            subtitle = "Download your learning progress",
                            onClick = { /* TODO: Export user data */ }
                        )
                    }
                }
            }

            // About Section
            item {
                SettingsSection(title = "About") {
                    Column {
                        SettingsItem(
                            icon = Icons.Default.Info,
                            title = "App Version",
                            subtitle = "BhashaSetu 1.0.0",
                            onClick = { /* TODO: Show version details */ }
                        )

                        SettingsItem(
                            icon = Icons.Default.Help,
                            title = "Help & Support",
                            subtitle = "Get help using the app",
                            onClick = { /* TODO: Open help */ }
                        )

                        SettingsItem(
                            icon = Icons.Default.Star,
                            title = "Rate the App",
                            subtitle = "Leave a review on the Play Store",
                            onClick = { /* TODO: Open Play Store */ }
                        )

                        SettingsItem(
                            icon = Icons.Default.Gavel,
                            title = "Privacy Policy",
                            subtitle = "Read our privacy policy",
                            onClick = { /* TODO: Open privacy policy */ }
                        )
                    }
                }
            }
        }
    }

    // Dialogs
    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = settings.language,
            onLanguageSelected = { language ->
                viewModel.updateLanguage(language)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }

    if (showDifficultyDialog) {
        DifficultySelectionDialog(
            currentDifficulty = settings.difficultyLevel,
            onDifficultySelected = { difficulty ->
                viewModel.updateDifficultyLevel(difficulty)
                showDifficultyDialog = false
            },
            onDismiss = { showDifficultyDialog = false }
        )
    }

    if (showTimePickerDialog) {
        TimePickerDialog(
            currentTime = settings.dailyReminderTime,
            onTimeSelected = { time ->
                viewModel.updateReminderTime(time)
                showTimePickerDialog = false
            },
            onDismiss = { showTimePickerDialog = false }
        )
    }

    if (showGoalDialog) {
        GoalSelectionDialog(
            currentGoal = settings.dailyGoalMinutes,
            onGoalSelected = { goal ->
                viewModel.updateDailyGoalMinutes(goal)
                showGoalDialog = false
            },
            onDismiss = { showGoalDialog = false }
        )
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    destructive: Boolean = false,
    indented: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = if (indented) 32.dp else 0.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (destructive) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = if (destructive) Color.Red else MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    indented: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = if (indented) 32.dp else 0.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

@Composable
private fun LanguageSelectionDialog(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf(
        "en" to "English",
        "hi" to "हिंदी (Hindi)"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Language",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                languages.forEach { (code, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageSelected(code) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentLanguage == code,
                            onClick = { onLanguageSelected(code) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun DifficultySelectionDialog(
    currentDifficulty: String,
    onDifficultySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val difficulties = listOf(
        "Beginner" to "Start with basic words and phrases",
        "Intermediate" to "Practice complex sentences and grammar",
        "Advanced" to "Master idioms and advanced vocabulary"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Difficulty Level",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                difficulties.forEach { (level, description) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDifficultySelected(level) }
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (currentDifficulty == level) {
                                MaterialTheme.colorScheme.primaryContainer
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = level,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun TimePickerDialog(
    currentTime: String,
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val timeOptions = listOf(
        "07:00", "08:00", "09:00", "10:00", "11:00",
        "12:00", "13:00", "14:00", "15:00", "16:00",
        "17:00", "18:00", "19:00", "20:00", "21:00"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Daily Reminder Time",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn {
                items(timeOptions) { time ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTimeSelected(time) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentTime == time,
                            onClick = { onTimeSelected(time) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = time,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun GoalSelectionDialog(
    currentGoal: Int,
    onGoalSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val goalOptions = listOf(5, 10, 15, 20, 30, 45, 60)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Daily Learning Goal",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                goalOptions.forEach { minutes ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onGoalSelected(minutes) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentGoal == minutes,
                            onClick = { onGoalSelected(minutes) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$minutes minutes per day",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}