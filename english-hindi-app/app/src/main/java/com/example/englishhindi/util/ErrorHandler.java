package com.example.englishhindi.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.englishhindi.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;

/**
 * Utility class for handling and displaying errors in a consistent way.
 * Provides methods for different error display options and error type identification.
 */
public class ErrorHandler {
    private static final String TAG = "ErrorHandler";

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

        if (throwable instanceof UnknownHostException || throwable instanceof IOException) {
            errorMessage = context.getString(R.string.error_no_internet);
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
            errorMessage = context.getString(R.string.error_connection_timeout);
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int statusCode = httpException.code();
            
            if (statusCode >= 500) {
                errorMessage = context.getString(R.string.error_server);
            } else if (statusCode == 404) {
                errorMessage = context.getString(R.string.error_data_load);
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
     * Show an error message as a Toast.
     * @param context Context
     * @param throwable The exception
     */
    public static void showToast(@NonNull Context context, @NonNull Throwable throwable) {
        String errorMessage = getErrorMessage(context, throwable);
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
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
     * Show an error message as a Snackbar.
     * @param view A view to find a parent from
     * @param throwable The exception
     */
    public static void showSnackbar(@NonNull View view, @NonNull Throwable throwable) {
        String errorMessage = getErrorMessage(view.getContext(), throwable);
        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show();
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
        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
                .setAction(actionText, listener)
                .show();
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
        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry_button_text, v -> retryAction.run())
                .show();
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
     * Log detailed error information.
     * @param tag Log tag
     * @param message Error message
     * @param throwable The exception
     */
    public static void logDetailedError(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
        
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
    }
}