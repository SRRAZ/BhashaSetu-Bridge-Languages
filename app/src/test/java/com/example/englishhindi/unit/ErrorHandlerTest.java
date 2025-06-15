package com.bhashasetu.app.unit;

import android.content.Context;

import com.bhashasetu.app.R;
import com.bhashasetu.app.util.ErrorHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ErrorHandler class.
 */
@RunWith(RobolectricTestRunner.class)
public class ErrorHandlerTest {

    private static final String ERROR_NO_INTERNET = "No internet connection";
    private static final String ERROR_CONNECTION_TIMEOUT = "Connection timed out";
    private static final String ERROR_SERVER = "Server error. Please try again later.";
    private static final String ERROR_DATA_LOAD = "Could not load data";
    private static final String ERROR_UNKNOWN = "Unknown error occurred";
    private static final String ERROR_CHECK_CONNECTION = "Please check your internet connection";
    private static final String ERROR_UNAUTHORIZED = "You are not authorized to access this content";

    @Mock
    private Context mockContext;

    @Mock
    private HttpException mockHttpException;

    @Mock
    private Response<?> mockResponse;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock context
        when(mockContext.getString(R.string.error_no_internet)).thenReturn(ERROR_NO_INTERNET);
        when(mockContext.getString(R.string.error_connection_timeout)).thenReturn(ERROR_CONNECTION_TIMEOUT);
        when(mockContext.getString(R.string.error_server)).thenReturn(ERROR_SERVER);
        when(mockContext.getString(R.string.error_data_load)).thenReturn(ERROR_DATA_LOAD);
        when(mockContext.getString(R.string.error_unknown)).thenReturn(ERROR_UNKNOWN);
        when(mockContext.getString(R.string.error_check_connection)).thenReturn(ERROR_CHECK_CONNECTION);
        when(mockContext.getString(R.string.error_unauthorized)).thenReturn(ERROR_UNAUTHORIZED);
    }

    @Test
    public void getErrorMessage_withUnknownHostException_shouldReturnNoInternetError() {
        // Create the exception
        UnknownHostException exception = new UnknownHostException("Unknown host");

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, exception);

        // Verify the message
        assertEquals(ERROR_NO_INTERNET, message);
    }

    @Test
    public void getErrorMessage_withSocketTimeoutException_shouldReturnTimeoutError() {
        // Create the exception
        SocketTimeoutException exception = new SocketTimeoutException("Socket timed out");

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, exception);

        // Verify the message
        assertEquals(ERROR_CONNECTION_TIMEOUT, message);
    }

    @Test
    public void getErrorMessage_withTimeoutException_shouldReturnTimeoutError() {
        // Create the exception
        TimeoutException exception = new TimeoutException("Operation timed out");

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, exception);

        // Verify the message
        assertEquals(ERROR_CONNECTION_TIMEOUT, message);
    }

    @Test
    public void getErrorMessage_withIOException_shouldReturnCheckConnectionError() {
        // Create the exception
        IOException exception = new IOException("IO error");

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, exception);

        // Verify the message
        assertEquals(ERROR_CHECK_CONNECTION, message);
    }

    @Test
    public void getErrorMessage_withServerHttpException_shouldReturnServerError() {
        // Setup the HTTP exception for 500 error
        when(mockHttpException.code()).thenReturn(500);

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, mockHttpException);

        // Verify the message
        assertEquals(ERROR_SERVER, message);
    }

    @Test
    public void getErrorMessage_with404HttpException_shouldReturnDataLoadError() {
        // Setup the HTTP exception for 404 error
        when(mockHttpException.code()).thenReturn(404);

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, mockHttpException);

        // Verify the message
        assertEquals(ERROR_DATA_LOAD, message);
    }

    @Test
    public void getErrorMessage_with401HttpException_shouldReturnUnauthorizedError() {
        // Setup the HTTP exception for 401 error
        when(mockHttpException.code()).thenReturn(401);

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, mockHttpException);

        // Verify the message
        assertEquals(ERROR_UNAUTHORIZED, message);
    }

    @Test
    public void getErrorMessage_with403HttpException_shouldReturnUnauthorizedError() {
        // Setup the HTTP exception for 403 error
        when(mockHttpException.code()).thenReturn(403);

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, mockHttpException);

        // Verify the message
        assertEquals(ERROR_UNAUTHORIZED, message);
    }

    @Test
    public void getErrorMessage_withOtherHttpException_shouldReturnUnknownError() {
        // Setup the HTTP exception for other error code
        when(mockHttpException.code()).thenReturn(400);

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, mockHttpException);

        // Verify the message
        assertEquals(ERROR_UNKNOWN, message);
    }

    @Test
    public void getErrorMessage_withUnknownException_shouldReturnUnknownError() {
        // Create an unknown exception
        RuntimeException exception = new RuntimeException("Unknown error");

        // Get error message
        String message = ErrorHandler.getErrorMessage(mockContext, exception);

        // Verify the message
        assertEquals(ERROR_UNKNOWN, message);
    }

    @Test
    public void isNetworkError_withNetworkExceptions_shouldReturnTrue() {
        // Create network exceptions
        UnknownHostException unknownHostException = new UnknownHostException();
        SocketTimeoutException socketTimeoutException = new SocketTimeoutException();
        TimeoutException timeoutException = new TimeoutException();
        IOException ioException = new IOException();

        // Verify they are all identified as network errors
        assertTrue(ErrorHandler.isNetworkError(unknownHostException));
        assertTrue(ErrorHandler.isNetworkError(socketTimeoutException));
        assertTrue(ErrorHandler.isNetworkError(timeoutException));
        assertTrue(ErrorHandler.isNetworkError(ioException));
    }

    @Test
    public void isNetworkError_withOtherExceptions_shouldReturnFalse() {
        // Create non-network exceptions
        RuntimeException runtimeException = new RuntimeException();
        
        // Setup HTTP exception
        when(mockHttpException.code()).thenReturn(400);

        // Verify they are not identified as network errors
        assertFalse(ErrorHandler.isNetworkError(runtimeException));
        assertFalse(ErrorHandler.isNetworkError(mockHttpException));
    }

    @Test
    public void isServerError_withServerHttpException_shouldReturnTrue() {
        // Setup the HTTP exception for 500 error
        when(mockHttpException.code()).thenReturn(500);

        // Verify it's identified as a server error
        assertTrue(ErrorHandler.isServerError(mockHttpException));
    }

    @Test
    public void isServerError_withOtherHttpException_shouldReturnFalse() {
        // Setup the HTTP exception for non-server error
        when(mockHttpException.code()).thenReturn(400);

        // Verify it's not identified as a server error
        assertFalse(ErrorHandler.isServerError(mockHttpException));
    }

    @Test
    public void isServerError_withNonHttpException_shouldReturnFalse() {
        // Create non-HTTP exception
        RuntimeException exception = new RuntimeException();

        // Verify it's not identified as a server error
        assertFalse(ErrorHandler.isServerError(exception));
    }

    @Test
    public void getErrorSeverity_withNetworkError_shouldReturnMedium() {
        // Create network exception
        UnknownHostException exception = new UnknownHostException();

        // Verify severity
        assertEquals(ErrorHandler.ErrorSeverity.MEDIUM, ErrorHandler.getErrorSeverity(exception));
    }

    @Test
    public void getErrorSeverity_withServerError_shouldReturnHigh() {
        // Setup the HTTP exception for 500 error
        when(mockHttpException.code()).thenReturn(500);

        // Verify severity
        assertEquals(ErrorHandler.ErrorSeverity.HIGH, ErrorHandler.getErrorSeverity(mockHttpException));
    }

    @Test
    public void getErrorSeverity_withAuthError_shouldReturnCritical() {
        // Setup the HTTP exception for 401 error
        when(mockHttpException.code()).thenReturn(401);

        // Verify severity
        assertEquals(ErrorHandler.ErrorSeverity.CRITICAL, ErrorHandler.getErrorSeverity(mockHttpException));
    }

    @Test
    public void getErrorSeverity_withOtherHttpError_shouldReturnMedium() {
        // Setup the HTTP exception for other error
        when(mockHttpException.code()).thenReturn(400);

        // Verify severity
        assertEquals(ErrorHandler.ErrorSeverity.MEDIUM, ErrorHandler.getErrorSeverity(mockHttpException));
    }

    @Test
    public void getErrorSeverity_withUnknownError_shouldReturnHigh() {
        // Create unknown exception
        RuntimeException exception = new RuntimeException();

        // Verify severity
        assertEquals(ErrorHandler.ErrorSeverity.HIGH, ErrorHandler.getErrorSeverity(exception));
    }
}