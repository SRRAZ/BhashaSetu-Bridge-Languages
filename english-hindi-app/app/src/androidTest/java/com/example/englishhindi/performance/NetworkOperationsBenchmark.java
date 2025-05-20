package com.example.englishhindi.performance;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.englishhindi.util.ErrorHandler;
import com.example.englishhindi.util.NetworkConnectionHandler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Performance benchmark tests for network operations.
 */
@RunWith(AndroidJUnit4.class)
public class NetworkOperationsBenchmark {

    @Rule
    public final BenchmarkRule benchmarkRule = new BenchmarkRule();

    @Test
    public void benchmark_getErrorMessage() {
        // Create test exceptions
        UnknownHostException unknownHostException = new UnknownHostException("Test host not found");
        SocketTimeoutException timeoutException = new SocketTimeoutException("Test timeout");
        IOException ioException = new IOException("Test IO exception");
        RuntimeException runtimeException = new RuntimeException("Test runtime exception");

        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark error message generation for different exceptions
            ErrorHandler.getErrorMessage(ApplicationProvider.getApplicationContext(), unknownHostException);
            ErrorHandler.getErrorMessage(ApplicationProvider.getApplicationContext(), timeoutException);
            ErrorHandler.getErrorMessage(ApplicationProvider.getApplicationContext(), ioException);
            ErrorHandler.getErrorMessage(ApplicationProvider.getApplicationContext(), runtimeException);
        }
    }

    @Test
    public void benchmark_networkConnectionCheck() {
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark getting network connection status
            NetworkConnectionHandler handler = NetworkConnectionHandler.getInstance(
                    ApplicationProvider.getApplicationContext());
            handler.getCurrentStatus();
            handler.isConnected();
        }
    }

    @Test
    public void benchmark_isNetworkError() {
        // Create test exceptions
        UnknownHostException unknownHostException = new UnknownHostException("Test host not found");
        SocketTimeoutException timeoutException = new SocketTimeoutException("Test timeout");
        IOException ioException = new IOException("Test IO exception");
        RuntimeException runtimeException = new RuntimeException("Test runtime exception");

        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark network error checking
            ErrorHandler.isNetworkError(unknownHostException);
            ErrorHandler.isNetworkError(timeoutException);
            ErrorHandler.isNetworkError(ioException);
            ErrorHandler.isNetworkError(runtimeException);
        }
    }

    @Test
    public void benchmark_getErrorSeverity() {
        // Create test exceptions
        UnknownHostException unknownHostException = new UnknownHostException("Test host not found");
        SocketTimeoutException timeoutException = new SocketTimeoutException("Test timeout");
        IOException ioException = new IOException("Test IO exception");
        RuntimeException runtimeException = new RuntimeException("Test runtime exception");

        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            // Benchmark error severity determination
            ErrorHandler.getErrorSeverity(unknownHostException);
            ErrorHandler.getErrorSeverity(timeoutException);
            ErrorHandler.getErrorSeverity(ioException);
            ErrorHandler.getErrorSeverity(runtimeException);
        }
    }
}