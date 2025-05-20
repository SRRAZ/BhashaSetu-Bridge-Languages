package com.example.englishhindi.unit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.englishhindi.util.NetworkConnectionHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the NetworkConnectionHandler class.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class NetworkConnectionHandlerTest {

    @Mock
    private Context mockContext;

    @Mock
    private ConnectivityManager mockConnectivityManager;

    @Mock
    private NetworkCapabilities mockNetworkCapabilities;

    @Mock
    private NetworkInfo mockNetworkInfo;

    @Mock
    private Observer<NetworkConnectionHandler.ConnectionStatus> mockObserver;

    private NetworkConnectionHandler connectionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup for connectivity manager
        when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE))
                .thenReturn(mockConnectivityManager);

        // Initialize handler with mock context
        connectionHandler = NetworkConnectionHandler.getInstance(mockContext);
    }

    @Test
    public void getInstance_shouldReturnSameInstance() {
        // Get instance twice
        NetworkConnectionHandler instance1 = NetworkConnectionHandler.getInstance(mockContext);
        NetworkConnectionHandler instance2 = NetworkConnectionHandler.getInstance(mockContext);

        // Verify they are the same instance
        assertEquals(instance1, instance2);
    }

    @Test
    public void getCurrentStatus_shouldProvideInitialState() {
        // Get current status
        NetworkConnectionHandler.ConnectionStatus status = connectionHandler.getCurrentStatus();

        // Verify it's not null
        assertNotNull(status);
    }

    @Test
    public void getConnectionStatus_shouldReturnLiveData() {
        // Get connection status LiveData
        LiveData<NetworkConnectionHandler.ConnectionStatus> liveData = connectionHandler.getConnectionStatus();

        // Verify it's not null
        assertNotNull(liveData);
    }

    // Additional test for parsing network capabilities
    @Test
    @Config(sdk = {Build.VERSION_CODES.M})
    public void updateConnectionStatus_withWifiNetwork_shouldReturnCorrectStatus() throws Exception {
        // This test uses reflection to call private method directly for testing

        // Setup mock network capabilities
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                .thenReturn(true);
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                .thenReturn(true);
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED))
                .thenReturn(true);
        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                .thenReturn(true);
        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                .thenReturn(false);

        // Use reflection to set private field
        Field connectivityManagerField = NetworkConnectionHandler.class.getDeclaredField("connectivityManager");
        connectivityManagerField.setAccessible(true);
        connectivityManagerField.set(connectionHandler, mockConnectivityManager);

        // Mock getNetworkCapabilities call
        Method getNetworkCapabilitiesMethod = NetworkConnectionHandler.class.getDeclaredMethod("getNetworkCapabilities");
        getNetworkCapabilitiesMethod.setAccessible(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(null); // Return mock network
        when(mockConnectivityManager.getNetworkCapabilities(null)).thenReturn(mockNetworkCapabilities);

        // Call private updateConnectionStatus method
        Method updateConnectionStatusMethod = NetworkConnectionHandler.class.getDeclaredMethod("updateConnectionStatus");
        updateConnectionStatusMethod.setAccessible(true);
        updateConnectionStatusMethod.invoke(connectionHandler);

        // Verify current status has been updated to connected wifi
        NetworkConnectionHandler.ConnectionStatus status = connectionHandler.getCurrentStatus();
        assertTrue(status.isConnected());
        assertEquals(NetworkConnectionHandler.ConnectionType.WIFI, status.getConnectionType());
        assertFalse(status.isMetered());
    }

    // Test for cellular network
    @Test
    @Config(sdk = {Build.VERSION_CODES.M})
    public void updateConnectionStatus_withCellularNetwork_shouldReturnCorrectStatus() throws Exception {
        // This test uses reflection to call private method directly for testing

        // Setup mock network capabilities
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                .thenReturn(true);
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                .thenReturn(true);
        when(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED))
                .thenReturn(false);
        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                .thenReturn(false);
        when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                .thenReturn(true);

        // Use reflection to set private field
        Field connectivityManagerField = NetworkConnectionHandler.class.getDeclaredField("connectivityManager");
        connectivityManagerField.setAccessible(true);
        connectivityManagerField.set(connectionHandler, mockConnectivityManager);

        // Mock getNetworkCapabilities call
        Method getNetworkCapabilitiesMethod = NetworkConnectionHandler.class.getDeclaredMethod("getNetworkCapabilities");
        getNetworkCapabilitiesMethod.setAccessible(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(null); // Return mock network
        when(mockConnectivityManager.getNetworkCapabilities(null)).thenReturn(mockNetworkCapabilities);

        // Call private updateConnectionStatus method
        Method updateConnectionStatusMethod = NetworkConnectionHandler.class.getDeclaredMethod("updateConnectionStatus");
        updateConnectionStatusMethod.setAccessible(true);
        updateConnectionStatusMethod.invoke(connectionHandler);

        // Verify current status has been updated to connected cellular
        NetworkConnectionHandler.ConnectionStatus status = connectionHandler.getCurrentStatus();
        assertTrue(status.isConnected());
        assertEquals(NetworkConnectionHandler.ConnectionType.CELLULAR, status.getConnectionType());
        assertTrue(status.isMetered());
    }

    // Test for no network
    @Test
    @Config(sdk = {Build.VERSION_CODES.M})
    public void updateConnectionStatus_withNoNetwork_shouldReturnCorrectStatus() throws Exception {
        // Use reflection to set private field
        Field connectivityManagerField = NetworkConnectionHandler.class.getDeclaredField("connectivityManager");
        connectivityManagerField.setAccessible(true);
        connectivityManagerField.set(connectionHandler, mockConnectivityManager);

        // Mock getNetworkCapabilities call to return null (no network)
        Method getNetworkCapabilitiesMethod = NetworkConnectionHandler.class.getDeclaredMethod("getNetworkCapabilities");
        getNetworkCapabilitiesMethod.setAccessible(true);
        when(mockConnectivityManager.getActiveNetwork()).thenReturn(null);
        when(mockConnectivityManager.getNetworkCapabilities(null)).thenReturn(null);

        // Call private updateConnectionStatus method
        Method updateConnectionStatusMethod = NetworkConnectionHandler.class.getDeclaredMethod("updateConnectionStatus");
        updateConnectionStatusMethod.setAccessible(true);
        updateConnectionStatusMethod.invoke(connectionHandler);

        // Verify current status has been updated to not connected
        NetworkConnectionHandler.ConnectionStatus status = connectionHandler.getCurrentStatus();
        assertFalse(status.isConnected());
        assertEquals(NetworkConnectionHandler.ConnectionType.NONE, status.getConnectionType());
    }
}