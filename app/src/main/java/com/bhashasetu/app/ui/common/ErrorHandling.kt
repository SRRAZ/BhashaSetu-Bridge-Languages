package com.bhashasetu.app.ui.common

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.delay

// Error types for the app
sealed class AppError(
    val message: String,
    val userMessage: String,
    val isRecoverable: Boolean = true
) {
    data class NetworkError(
        val error: String = "Network connection failed",
        val userMsg: String = "Please check your internet connection and try again."
    ) : AppError(error, userMsg, true)

    data class DatabaseError(
        val error: String = "Database operation failed",
        val userMsg: String = "Something went wrong with saving your data. Please try again."
    ) : AppError(error, userMsg, true)

    data class ValidationError(
        val error: String = "Input validation failed",
        val userMsg: String = "Please check your input and try again."
    ) : AppError(error, userMsg, true)

    data class AudioError(
        val error: String = "Audio playback failed",
        val userMsg: String = "Unable to play audio right now. Please try again."
    ) : AppError(error, userMsg, true)

    data class SpeechRecognitionError(
        val error: String = "Speech recognition failed",
        val userMsg: String = "Couldn't recognize your speech. Please try speaking more clearly."
    ) : AppError(error, userMsg, true)

    data class AuthenticationError(
        val error: String = "Authentication failed",
        val userMsg: String = "Please log in again to continue."
    ) : AppError(error, userMsg, false)

    data class PermissionError(
        val error: String = "Permission denied",
        val userMsg: String = "Please grant the required permissions to use this feature."
    ) : AppError(error, userMsg, true)

    data class StorageError(
        val error: String = "Storage operation failed",
        val userMsg: String = "Not enough storage space or unable to save data."
    ) : AppError(error, userMsg, true)

    data class UnknownError(
        val error: String = "Unknown error occurred",
        val userMsg: String = "Something unexpected happened. Please try again."
    ) : AppError(error, userMsg, true)
}

// Error severity levels
enum class ErrorSeverity {
    LOW,      // Minor issues, app continues normally
    MEDIUM,   // Noticeable issues, some features may not work
    HIGH,     // Major issues, core functionality affected
    CRITICAL  // App-breaking issues, immediate attention required
}

// Error UI state
data class ErrorUiState(
    val error: AppError? = null,
    val severity: ErrorSeverity = ErrorSeverity.LOW,
    val isVisible: Boolean = false,
    val autoHideDelay: Long = 5000L, // Auto-hide after 5 seconds
    val showRetryButton: Boolean = true,
    val showDetailsButton: Boolean = false
)

// Main error display component
@Composable
fun ErrorDisplay(
    error: AppError?,
    severity: ErrorSeverity = ErrorSeverity.MEDIUM,
    onRetry: (() -> Unit)? = null,
    onDismiss: () -> Unit,
    autoHide: Boolean = true,
    modifier: Modifier = Modifier
) {
    var isVisible by remember(error) { mutableStateOf(error != null) }

    LaunchedEffect(error) {
        if (error != null) {
            isVisible = true
            if (autoHide && severity == ErrorSeverity.LOW) {
                delay(5000L)
                isVisible = false
                delay(300L) // Wait for animation
                onDismiss()
            }
        }
    }

    AnimatedVisibility(
        visible = isVisible && error != null,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300)),
        modifier = modifier
    ) {
        if (error != null) {
            when (severity) {
                ErrorSeverity.LOW -> LightErrorCard(error, onRetry, onDismiss)
                ErrorSeverity.MEDIUM -> StandardErrorCard(error, onRetry, onDismiss)
                ErrorSeverity.HIGH -> SevereErrorCard(error, onRetry, onDismiss)
                ErrorSeverity.CRITICAL -> CriticalErrorCard(error, onRetry, onDismiss)
            }
        }
    }
}

@Composable
private fun LightErrorCard(
    error: AppError,
    onRetry: (() -> Unit)?,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = error.userMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )

            if (onRetry != null && error.isRecoverable) {
                TextButton(
                    onClick = {
                        onRetry()
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Retry", fontSize = 12.sp)
                }
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun StandardErrorCard(
    error: AppError,
    onRetry: (() -> Unit)?,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = getErrorIcon(error),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = getErrorTitle(error),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = error.userMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            if (onRetry != null && error.isRecoverable) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {
                            onRetry()
                            onDismiss()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Try Again")
                    }
                }
            }
        }
    }
}

@Composable
private fun SevereErrorCard(
    error: AppError,
    onRetry: (() -> Unit)?,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                modifier = Modifier.size(60.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getErrorIcon(error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = getErrorTitle(error),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = error.userMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (onRetry != null && error.isRecoverable) {
                Button(
                    onClick = {
                        onRetry()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Try Again")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dismiss")
            }
        }
    }
}

@Composable
private fun CriticalErrorCard(
    error: AppError,
    onRetry: (() -> Unit)?,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Red.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            border = BorderStroke(3.dp, Color.Red.copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.Red.copy(alpha = 0.2f),
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Critical Error",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = error.userMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (onRetry != null && error.isRecoverable) {
                    Button(
                        onClick = {
                            onRetry()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Retry", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                } else {
                    Text(
                        text = "Please restart the app",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.Red)
                ) {
                    Text("Close", color = Color.Red)
                }
            }
        }
    }
}

// Snackbar-style error display
@Composable
fun ErrorSnackbar(
    error: AppError,
    onRetry: (() -> Unit)? = null,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(error) {
        delay(4000L) // Auto-hide after 4 seconds
        isVisible = false
        delay(300L)
        onDismiss()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.inverseSurface,
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = getErrorIcon(error),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = error.userMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.weight(1f)
                )

                if (onRetry != null && error.isRecoverable) {
                    TextButton(
                        onClick = {
                            onRetry()
                            onDismiss()
                        }
                    ) {
                        Text(
                            "RETRY",
                            color = MaterialTheme.colorScheme.inversePrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// Empty state display
@Composable
fun EmptyStateDisplay(
    icon: ImageVector = Icons.Default.SearchOff,
    title: String,
    description: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(80.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (actionText != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onActionClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(actionText)
            }
        }
    }
}

// Helper functions
private fun getErrorIcon(error: AppError): ImageVector {
    return when (error) {
        is AppError.NetworkError -> Icons.Default.WifiOff
        is AppError.AudioError -> Icons.Default.VolumeOff
        is AppError.SpeechRecognitionError -> Icons.Default.MicOff
        is AppError.AuthenticationError -> Icons.Default.Lock
        is AppError.PermissionError -> Icons.Default.Security
        is AppError.StorageError -> Icons.Default.Storage
        is AppError.DatabaseError -> Icons.Default.Database
        is AppError.ValidationError -> Icons.Default.Error
        is AppError.UnknownError -> Icons.Default.Help
    }
}

private fun getErrorTitle(error: AppError): String {
    return when (error) {
        is AppError.NetworkError -> "Connection Error"
        is AppError.AudioError -> "Audio Error"
        is AppError.SpeechRecognitionError -> "Speech Recognition Failed"
        is AppError.AuthenticationError -> "Authentication Required"
        is AppError.PermissionError -> "Permission Required"
        is AppError.StorageError -> "Storage Error"
        is AppError.DatabaseError -> "Data Error"
        is AppError.ValidationError -> "Invalid Input"
        is AppError.UnknownError -> "Something Went Wrong"
    }
}

// Loading state display
@Composable
fun LoadingDisplay(
    message: String = "Loading...",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Error boundary for Compose
@Composable
fun ErrorBoundary(
    onError: (Throwable) -> Unit = {},
    content: @Composable () -> Unit
) {
    try {
        content()
    } catch (e: Throwable) {
        onError(e)
        CriticalErrorCard(
            error = AppError.UnknownError(
                error = e.message ?: "Unknown error",
                userMsg = "The app encountered an unexpected error. Please restart the app."
            ),
            onRetry = null,
            onDismiss = {}
        )
    }
}