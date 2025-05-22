package com.example.englishhindi.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.englishhindi.R;
import com.example.englishhindi.ui.LoadingStateView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;

/**
 * Improved utility class for handling and displaying errors in a consistent way.
 * Provides methods for different error display options, error type identification,
 * and smart error recovery suggestions.
 */
public class ErrorHandler {
    private static final String TAG = "ErrorHandler";

    // Error severity levels for logging and analytics
    public enum ErrorSeverity {
        LOW,        // Minor errors, user can continue
        MEDIUM,     // Medium errors, functionality limited but usable
        HIGH,       // High severity, requires user intervention
        CRITICAL    // Critical errors, app cannot function properly
    }

    private ErrorHandler() {
        // Private constructor to prevent instantiation
    }

    /**
     * Handle an exception and return an appropriate error message.
     * @param context Context
     * @param throwable The exception
     * @return A user-friendly error message
     */
    public static String getErrorMessage(@NonNull Context context, @NonNull Throwable throwable) {
        String errorMessage;

        // Check if we're currently offline
        NetworkConnectionHandler connectionHandler = NetworkConnectionHandler.getInstance(context);
        boolean isOffline = !connectionHandler.isConnected();

        // Handle network errors with better context
        if (isOffline && isNetworkError(throwable)) {
            errorMessage = context.getString(R.string.error_no_internet);
        } else if (throwable instanceof UnknownHostException) {
            errorMessage = context.getString(R.string.error_no_internet);
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
            errorMessage = context.getString(R.string.error_connection_timeout);
        } else if (throwable instanceof IOException) {
            errorMessage = context.getString(R.string.error_check_connection);
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int statusCode = httpException.code();
            
            if (statusCode >= 500) {
                errorMessage = context.getString(R.string.error_server);
            } else if (statusCode == 404) {
                errorMessage = context.getString(R.string.error_data_load);
            } else if (statusCode == 401 || statusCode == 403) {
                errorMessage = context.getString(R.string.error_unauthorized); // Add this to strings.xml
            } else {
                errorMessage = context.getString(R.string.error_unknown);
            }
        } else {
            errorMessage = context.getString(R.string.error_unknown);
        }

        // Log the error for debugging
        Log.e(TAG, "Error: " + errorMessage, throwable);

        return errorMessage;
    }

    /**
     * Show an error message as a Toast with animation.
     * @param context Context
     * @param throwable The exception
     */
    public static void showToast(@NonNull Context context, @NonNull Throwable throwable) {
        String errorMessage = getErrorMessage(context, throwable);
        Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_LONG);
        
        View view = toast.getView();
        if (view != null) {
            // Apply animation to toast
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
            view.startAnimation(animation);
        }
        
        toast.show();
    }

    /**
     * Show an error message as a Toast.
     * @param context Context
     * @param errorMessageResId Resource ID of the error message
     */
    public static void showToast(@NonNull Context context, @StringRes int errorMessageResId) {
        Toast.makeText(context, errorMessageResId, Toast.LENGTH_LONG).show();
    }

    /**
     * Show an error message as a Snackbar with improved styling.
     * @param view A view to find a parent from
     * @param throwable The exception
     */
    public static void showSnackbar(@NonNull View view, @NonNull Throwable throwable) {
        String errorMessage = getErrorMessage(view.getContext(), throwable);
        Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        
        // Style the Snackbar
        snackbarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.error_background));
        snackbar.setTextColor(ContextCompat.getColor(view.getContext(), R.color.error_text));
        
        snackbar.show();
    }

    /**
     * Show an error message as a Snackbar with an action.
     * @param view A view to find a parent from
     * @param throwable The exception
     * @param actionText The text to display for the action
     * @param listener The callback to be invoked when the action is clicked
     */
    public static void showSnackbarWithAction(@NonNull View view, @NonNull Throwable throwable,
                                             @NonNull String actionText,
                                             @NonNull View.OnClickListener listener) {
        String errorMessage = getErrorMessage(view.getContext(), throwable);
        Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
                .setAction(actionText, listener);
                
        View snackbarView = snackbar.getView();
        
        // Style the Snackbar
        snackbarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.error_background));
        snackbar.setTextColor(ContextCompat.getColor(view.getContext(), R.color.error_text));
        snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.error_action_text));
        
        snackbar.show();
    }

    /**
     * Show an error message as a Snackbar with a retry action.
     * @param view A view to find a parent from
     * @param throwable The exception
     * @param retryAction The action to perform on retry
     */
    public static void showRetrySnackbar(@NonNull View view, @NonNull Throwable throwable,
                                        @NonNull Runnable retryAction) {
        String errorMessage = getErrorMessage(view.getContext(), throwable);
        Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry_button_text, v -> retryAction.run());
                
        View snackbarView = snackbar.getView();
        
        // Style the Snackbar
        snackbarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.error_background));
        snackbar.setTextColor(ContextCompat.getColor(view.getContext(), R.color.error_text));
        snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.error_action_text));
        
        snackbar.show();
    }
    
    /**
     * Show a detailed error dialog with options to recover.
     * @param activity The activity context
     * @param throwable The exception
     * @param retryAction The action to perform on retry
     */
    public static void showErrorDialog(@NonNull FragmentActivity activity, 
                                      @NonNull Throwable throwable,
                                      @NonNull Runnable retryAction) {
        Context context = activity.getApplicationContext();
        String errorMessage = getErrorMessage(context, throwable);
        String title = context.getString(R.string.dialog_title_error);
        
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity)
                .setTitle(title)
                .setMessage(errorMessage)
                .setPositiveButton(R.string.retry_button_text, (dialog, which) -> retryAction.run())
                .setNegativeButton(R.string.dialog_button_cancel, (dialog, which) -> dialog.dismiss());
        
        // Add network settings button if it's a network error
        if (isNetworkError(throwable)) {
            builder.setNeutralButton(R.string.network_settings, (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                activity.startActivity(intent);
            });
        }
        
        builder.show();
    }
    
    /**
     * Update a LoadingStateView to show an error state with proper message.
     * @param loadingStateView The loading state view
     * @param throwable The exception
     * @param retryAction The action to perform on retry
     */
    public static void showLoadingError(@NonNull LoadingStateView loadingStateView,
                                      @NonNull Throwable throwable,
                                      @NonNull Runnable retryAction) {
        String errorMessage = getErrorMessage(loadingStateView.getContext(), throwable);
        loadingStateView.setErrorMessage(errorMessage);
        loadingStateView.setOnRetryClickListener(() -> retryAction.run());
        loadingStateView.setState(LoadingStateView.State.ERROR);
    }

    /**
     * Determine if the error is a network connectivity issue.
     * @param throwable The exception
     * @return True if it's a network connectivity issue
     */
    public static boolean isNetworkError(@NonNull Throwable throwable) {
        return throwable instanceof UnknownHostException ||
               throwable instanceof SocketTimeoutException ||
               throwable instanceof TimeoutException ||
               throwable instanceof IOException;
    }

    /**
     * Determine if the error is a server error.
     * @param throwable The exception
     * @return True if it's a server error
     */
    public static boolean isServerError(@NonNull Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int statusCode = httpException.code();
            return statusCode >= 500;
        }
        return false;
    }
    
    /**
     * Get the severity level of an error.
     * @param throwable The exception
     * @return The severity level
     */
    public static ErrorSeverity getErrorSeverity(@NonNull Throwable throwable) {
        if (isNetworkError(throwable)) {
            // Network errors are medium as they are usually temporary
            return ErrorSeverity.MEDIUM;
        } else if (isServerError(throwable)) {
            // Server errors are high as they affect core functionality
            return ErrorSeverity.HIGH;
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int statusCode = httpException.code();
            
            if (statusCode == 401 || statusCode == 403) {
                // Authentication errors are critical
                return ErrorSeverity.CRITICAL;
            } else {
                // Other HTTP errors are medium
                return ErrorSeverity.MEDIUM;
            }
        } else {
            // Unknown errors are high severity
            return ErrorSeverity.HIGH;
        }
    }
    
    /**
     * Log detailed error information with severity.
     * @param tag Log tag
     * @param message Error message
     * @param throwable The exception
     */
    public static void logDetailedError(String tag, String message, Throwable throwable) {
        ErrorSeverity severity = getErrorSeverity(throwable);
        
        // Log with appropriate level based on severity
        String severityStr = severity.toString();
        Log.e(tag, String.format("[%s] %s", severityStr, message), throwable);
        
        // Add additional debug information if needed
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            try {
                Log.e(tag, "Response code: " + httpException.code());
                Log.e(tag, "Response message: " + httpException.message());
                if (httpException.response() != null && httpException.response().errorBody() != null) {
                    Log.e(tag, "Error body: " + httpException.response().errorBody().string());
                }
            } catch (IOException e) {
                Log.e(tag, "Could not read error body", e);
            }
        }
        
        // Here we could also integrate with crash reporting services like Firebase Crashlytics
        // or perform additional error handling based on severity
    }
    
    /**
     * Get recommended actions based on error type.
     * @param context Context
     * @param throwable The exception
     * @return Array of recommended action strings
     */
    public static String[] getRecommendedActions(@NonNull Context context, @NonNull Throwable throwable) {
        if (isNetworkError(throwable)) {
            return new String[] {
                context.getString(R.string.check_internet_connection),
                context.getString(R.string.try_again_later),
                context.getString(R.string.use_offline_mode)
            };
        } else if (isServerError(throwable)) {
            return new String[] {
                context.getString(R.string.try_again_later),
                context.getString(R.string.check_for_app_updates)
            };
        } else {
            return new String[] {
                context.getString(R.string.try_again_later),
                context.getString(R.string.restart_app)
            };
        }
    }
}