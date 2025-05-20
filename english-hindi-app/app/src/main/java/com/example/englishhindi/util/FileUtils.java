package com.example.englishhindi.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Utility class for file operations including downloading, copying,
 * and checking file status.
 */
public class FileUtils {
    private static final String TAG = "FileUtils";
    
    // Connection and read timeouts in seconds
    private static final int CONNECTION_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 30;
    
    // Buffer size for file operations
    private static final int BUFFER_SIZE = 4096;
    
    // OkHttpClient instance for better connection management
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build();
    
    /**
     * Download a file from a URL
     * @param fileUrl Source URL
     * @param destFile Destination file
     * @return true if download successful
     */
    public static boolean downloadFile(String fileUrl, File destFile) {
        try {
            // Create parent directory if it doesn't exist
            File parentDir = destFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            // Use OkHttp for reliable downloads
            Request request = new Request.Builder()
                    .url(fileUrl)
                    .build();
            
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.e(TAG, "Download failed: " + response.code() + " - " + fileUrl);
                return false;
            }
            
            InputStream inputStream = response.body().byteStream();
            OutputStream outputStream = new FileOutputStream(destFile);
            
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error downloading file: " + fileUrl, e);
            return false;
        }
    }
    
    /**
     * Check if a file needs updating by comparing with remote timestamp
     * @param fileUrl Source URL
     * @param localFile Local file
     * @return true if update needed
     */
    public static boolean fileNeedsUpdate(String fileUrl, File localFile) {
        if (!localFile.exists()) {
            return true;
        }
        
        try {
            // Check Last-Modified header from server
            Request request = new Request.Builder()
                    .url(fileUrl)
                    .head() // Only get headers, not body
                    .build();
            
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.e(TAG, "Failed to check headers: " + response.code() + " - " + fileUrl);
                return false;
            }
            
            String lastModified = response.header("Last-Modified");
            if (lastModified == null) {
                // No Last-Modified header, can't compare
                return false;
            }
            
            // Parse Last-Modified header to timestamp
            long serverTimestamp = HttpDateParser.parseHttpDate(lastModified);
            long localTimestamp = localFile.lastModified();
            
            // If server file is newer, update is needed
            return serverTimestamp > localTimestamp;
        } catch (IOException e) {
            Log.e(TAG, "Error checking file update: " + fileUrl, e);
            return false;
        }
    }
    
    /**
     * Copy a file from one location to another
     * @param sourceFile Source file
     * @param destFile Destination file
     * @return true if copy successful
     */
    public static boolean copyFile(File sourceFile, File destFile) {
        try {
            // Create parent directory if it doesn't exist
            File parentDir = destFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            InputStream inputStream = new BufferedInputStream(new java.io.FileInputStream(sourceFile));
            OutputStream outputStream = new FileOutputStream(destFile);
            
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error copying file: " + sourceFile.getPath(), e);
            return false;
        }
    }
    
    /**
     * Save an asset file to internal storage
     * @param context Application context
     * @param assetName Asset filename
     * @param outputFile Output file
     * @return true if successful
     */
    public static boolean saveAssetToFile(Context context, String assetName, File outputFile) {
        try {
            // Create parent directory if it doesn't exist
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            InputStream inputStream = context.getAssets().open(assetName);
            OutputStream outputStream = new FileOutputStream(outputFile);
            
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving asset: " + assetName, e);
            return false;
        }
    }
    
    /**
     * Get the MIME type of a file from its extension
     * @param fileName Filename or path
     * @return MIME type or null if unknown
     */
    public static String getMimeType(String fileName) {
        String extension = getFileExtension(fileName);
        
        if (extension == null) {
            return null;
        }
        
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            case "ogg":
                return "audio/ogg";
            case "mp4":
                return "video/mp4";
            case "json":
                return "application/json";
            case "xml":
                return "application/xml";
            case "html":
                return "text/html";
            case "txt":
                return "text/plain";
            default:
                return null;
        }
    }
    
    /**
     * Get the file extension from a filename
     * @param fileName Filename or path
     * @return Extension without dot or null if none
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return null;
        }
        
        return fileName.substring(lastDotIndex + 1);
    }
    
    /**
     * Create a content URI for a file
     * @param context Application context
     * @param file File object
     * @return URI string
     */
    public static String getFileContentUri(Context context, File file) {
        return Uri.fromFile(file).toString();
    }
    
    /**
     * Get the file size in a human-readable format
     * @param size Size in bytes
     * @return Formatted size string
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0 B";
        }
        
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        
        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
    
    /**
     * Check if enough free space is available
     * @param dir Directory to check
     * @param sizeNeeded Size needed in bytes
     * @return true if enough space available
     */
    public static boolean hasEnoughFreeSpace(File dir, long sizeNeeded) {
        return dir.getUsableSpace() >= sizeNeeded;
    }
    
    /**
     * Delete a directory and all its contents
     * @param dir Directory to delete
     * @return true if successful
     */
    public static boolean deleteDirectory(File dir) {
        if (dir == null || !dir.exists()) {
            return true;
        }
        
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDirectory(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        
        return dir.delete();
    }
    
    /**
     * Helper class to parse HTTP date headers
     */
    private static class HttpDateParser {
        /**
         * Parse an HTTP date header value to a timestamp
         * @param dateStr Date string
         * @return Timestamp in milliseconds, or 0 if parsing failed
         */
        public static long parseHttpDate(String dateStr) {
            try {
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                        "EEE, dd MMM yyyy HH:mm:ss zzz", java.util.Locale.US);
                format.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
                java.util.Date date = format.parse(dateStr);
                return date.getTime();
            } catch (Exception e) {
                Log.e(TAG, "Error parsing HTTP date: " + dateStr, e);
                return 0;
            }
        }
    }
}