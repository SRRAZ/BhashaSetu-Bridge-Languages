package com.example.englishhindi.cache;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Disk-based LRU cache implementation for efficient caching of objects to disk.
 * Based on the DiskLruCache pattern, optimized for performance and reliability.
 */
public final class DiskLruCache implements Serializable {
    private static final String TAG = "DiskLruCache";
    private static final int VERSION = 1;
    
    // JOURNAL_FILE used to track cache operations
    private static final String JOURNAL_FILE = "journal";
    
    // Magic values for journal entries
    private static final String CLEAN = "CLEAN";      // Entry has been written and is valid
    private static final String DIRTY = "DIRTY";      // Entry is being created/updated
    private static final String REMOVE = "REMOVE";    // Entry has been removed
    private static final String READ = "READ";        // Entry has been read
    
    // The root directory for this cache
    private final File directory;
    
    // Maximum size of this cache in bytes
    private final long maxSize;
    
    // Size of each value in this cache, in bytes
    private long size = 0;
    
    // Cache value metadata
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
    
    // True if this cache has been closed
    private boolean closed;
    
    // True if we've initialized from the journal
    private boolean initialized;
    
    // Used to run tasks in the background thread
    private final ThreadPoolExecutor executor;
    
    // Class representing an entry in the cache
    private static class Entry implements Serializable {
        private static final long serialVersionUID = 1L;
        
        String key;
        long size;
        long expiryTime;
        
        // Transient fields that don't get serialized
        transient DiskLruCache cache;
        transient long lastModified;
        transient boolean readable;
        transient boolean dirty;
        
        Entry(String key) {
            this.key = key;
        }
        
        /**
         * Get file for this entry.
         */
        File getFile() {
            return new File(cache.directory, key + ".cache");
        }
        
        /**
         * Get temporary file for writing this entry.
         */
        File getTempFile() {
            return new File(cache.directory, key + ".tmp");
        }
        
        /**
         * Check if this entry has expired.
         */
        boolean isExpired() {
            return expiryTime > 0 && System.currentTimeMillis() > expiryTime;
        }
    }
    
    /**
     * Editor to modify entries in the cache.
     */
    public final class Editor {
        private final Entry entry;
        private boolean hasErrors;
        
        private Editor(Entry entry) {
            this.entry = entry;
        }
        
        /**
         * Prepare a new output stream for writing the value.
         * The old value will be replaced when committed.
         *
         * @return Output stream for writing
         * @throws IOException If an I/O error occurs
         */
        public OutputStream newOutputStream() throws IOException {
            return newOutputStream(0);
        }
        
        /**
         * Prepare a new output stream for writing the value.
         * The old value will be replaced when committed.
         *
         * @param index Must be 0
         * @return Output stream for writing
         * @throws IOException If an I/O error occurs
         */
        public OutputStream newOutputStream(int index) throws IOException {
            if (index != 0) {
                throw new IllegalArgumentException("Expected index 0");
            }
            
            synchronized (DiskLruCache.this) {
                if (entry.currentEditor != this) {
                    throw new IllegalStateException("This editor was detached");
                }
                
                OutputStream outputStream = new FileOutputStream(entry.getTempFile());
                outputStream = new BufferedOutputStream(outputStream);
                return new FaultHidingOutputStream(outputStream);
            }
        }
        
        /**
         * Set expiry time for this entry.
         *
         * @param expiryTimeMillis Expiry time in milliseconds from epoch
         * @return This editor
         */
        public Editor setExpiryTime(long expiryTimeMillis) {
            synchronized (DiskLruCache.this) {
                entry.expiryTime = expiryTimeMillis;
            }
            return this;
        }
        
        /**
         * Commit changes to this entry.
         *
         * @throws IOException If an I/O error occurs
         */
        public void commit() throws IOException {
            if (hasErrors) {
                completeEdit(this, false);
                remove(entry.key); // Don't leave a partially-constructed entry
                return;
            }
            
            completeEdit(this, true);
        }
        
        /**
         * Abort this edit.
         */
        public void abort() throws IOException {
            completeEdit(this, false);
        }
        
        /**
         * Output stream that suppresses IOExceptions but keeps track of error state.
         */
        private class FaultHidingOutputStream extends FilterOutputStream {
            private FaultHidingOutputStream(OutputStream out) {
                super(out);
            }
            
            @Override
            public void write(int b) {
                try {
                    out.write(b);
                } catch (IOException e) {
                    hasErrors = true;
                }
            }
            
            @Override
            public void write(byte[] b, int off, int len) {
                try {
                    out.write(b, off, len);
                } catch (IOException e) {
                    hasErrors = true;
                }
            }
            
            @Override
            public void close() {
                try {
                    out.close();
                } catch (IOException e) {
                    hasErrors = true;
                }
            }
            
            @Override
            public void flush() {
                try {
                    out.flush();
                } catch (IOException e) {
                    hasErrors = true;
                }
            }
        }
    }
    
    /**
     * Snapshot for reading an immutable cache value.
     */
    public final class Snapshot implements Closeable {
        private final String key;
        private final long lastModified;
        private final InputStream input;
        
        private Snapshot(String key, long lastModified, InputStream input) {
            this.key = key;
            this.lastModified = lastModified;
            this.input = input;
        }
        
        /**
         * Get input stream for reading the cached value.
         *
         * @return Input stream
         */
        public InputStream getInputStream() {
            return input;
        }
        
        /**
         * Get the key for this snapshot.
         *
         * @return Key
         */
        public String getKey() {
            return key;
        }
        
        /**
         * Get last modified time for this entry.
         *
         * @return Last modified time in milliseconds
         */
        public long getLastModified() {
            return lastModified;
        }
        
        @Override
        public void close() {
            try {
                input.close();
            } catch (IOException e) {
                // Ignored
            }
        }
    }
    
    /**
     * Create a DiskLruCache instance.
     *
     * @param directory Directory for cache storage
     * @param maxSize   Maximum cache size in bytes
     * @return DiskLruCache instance
     */
    public static DiskLruCache open(File directory, long maxSize) throws IOException {
        return new DiskLruCache(directory, maxSize);
    }
    
    /**
     * Private constructor.
     *
     * @param directory Directory for cache storage
     * @param maxSize   Maximum cache size in bytes
     * @throws IOException If the directory cannot be created
     */
    private DiskLruCache(File directory, long maxSize) throws IOException {
        this.directory = directory;
        this.maxSize = maxSize;
        
        // Create a single-threaded executor for background operations
        this.executor = new ThreadPoolExecutor(
                0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        
        // Create cache directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Initialize the cache from disk
        initialize();
    }
    
    /**
     * Initialize the cache from disk.
     */
    private void initialize() throws IOException {
        if (initialized) {
            return;
        }
        
        // Read journal file if it exists
        File journalFile = new File(directory, JOURNAL_FILE);
        if (journalFile.exists()) {
            try {
                readJournal();
                initialized = true;
                return;
            } catch (Exception e) {
                Log.w(TAG, "Error reading journal: " + e);
                // If we can't read the journal, just start fresh
            }
        }
        
        // Create a new empty cache
        rebuildJournal();
        initialized = true;
    }
    
    /**
     * Read journal from disk.
     */
    private void readJournal() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(new File(directory, JOURNAL_FILE)));
            
            // Read version and entry count
            int version = in.readInt();
            if (version != VERSION) {
                throw new IOException("Unexpected journal version: " + version);
            }
            
            int entryCount = in.readInt();
            for (int i = 0; i < entryCount; i++) {
                Entry entry = (Entry) in.readObject();
                entry.cache = this;
                lruEntries.put(entry.key, entry);
                size += entry.size;
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Journal failure", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
    
    /**
     * Rebuild the journal file from the current cache state.
     */
    private synchronized void rebuildJournal() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(
                    new FileOutputStream(new File(directory, JOURNAL_FILE + ".tmp")));
            
            // Write version and entry count
            out.writeInt(VERSION);
            out.writeInt(lruEntries.size());
            
            // Write all entries
            for (Entry entry : lruEntries.values()) {
                out.writeObject(entry);
            }
            
            out.flush();
            out.close();
            out = null;
            
            // Rename temp file to journal file
            File tempFile = new File(directory, JOURNAL_FILE + ".tmp");
            File journalFile = new File(directory, JOURNAL_FILE);
            tempFile.renameTo(journalFile);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
    
    /**
     * Get an entry from the cache.
     *
     * @param key Key to look up
     * @return Snapshot of the entry, or null if not found
     * @throws IOException If an I/O error occurs
     */
    public synchronized Snapshot get(String key) throws IOException {
        checkNotClosed();
        validateKey(key);
        
        Entry entry = lruEntries.get(key);
        if (entry == null || !entry.readable || entry.isExpired()) {
            return null;
        }
        
        // Open input stream for reading
        InputStream input;
        try {
            input = new BufferedInputStream(new FileInputStream(entry.getFile()));
        } catch (IOException e) {
            return null;
        }
        
        // Update last access time
        entry.lastModified = System.currentTimeMillis();
        
        return new Snapshot(key, entry.lastModified, input);
    }
    
    /**
     * Create or edit an entry in the cache.
     *
     * @param key Key to edit
     * @return Editor for the entry
     * @throws IOException If an I/O error occurs
     */
    public synchronized Editor edit(String key) throws IOException {
        checkNotClosed();
        validateKey(key);
        
        Entry entry = lruEntries.get(key);
        if (entry == null) {
            // Create a new entry
            entry = new Entry(key);
            entry.cache = this;
            lruEntries.put(key, entry);
        } else if (entry.isExpired()) {
            // Replace an expired entry
            remove(key);
            entry = new Entry(key);
            entry.cache = this;
            lruEntries.put(key, entry);
        } else if (entry.currentEditor != null) {
            // Another edit is in progress
            return null;
        }
        
        // Create an editor
        Editor editor = new Editor(entry);
        entry.currentEditor = editor;
        return editor;
    }
    
    /**
     * Completes an edit operation.
     *
     * @param editor    Editor that is completing
     * @param success   Whether the edit succeeded
     * @throws IOException If an I/O error occurs
     */
    private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
        Entry entry = editor.entry;
        if (entry.currentEditor != editor) {
            throw new IllegalStateException("Mismatched editor!");
        }
        
        if (success) {
            // Commit changes
            File tempFile = entry.getTempFile();
            File destFile = entry.getFile();
            
            // Get size of new file
            long newSize = tempFile.length();
            
            // Update cache size
            long oldSize = entry.size;
            entry.size = newSize;
            size = size - oldSize + newSize;
            
            // Move the temp file to the real file
            if (destFile.exists()) {
                destFile.delete();
            }
            tempFile.renameTo(destFile);
            
            // Update entry state
            entry.lastModified = System.currentTimeMillis();
            entry.readable = true;
            entry.dirty = false;
        } else {
            // Clean up the temp file
            entry.getTempFile().delete();
        }
        
        // Clear the editor
        entry.currentEditor = null;
        
        // Check if we need to trim the cache
        if (size > maxSize) {
            trimToSize();
        }
    }
    
    /**
     * Remove an entry from the cache.
     *
     * @param key Key to remove
     * @return true if the entry was removed, false otherwise
     * @throws IOException If an I/O error occurs
     */
    public synchronized boolean remove(String key) throws IOException {
        checkNotClosed();
        validateKey(key);
        
        Entry entry = lruEntries.get(key);
        if (entry == null) {
            return false;
        }
        
        // Remove the files
        entry.getFile().delete();
        entry.getTempFile().delete();
        
        // Update cache size
        size -= entry.size;
        
        // Remove from cache
        lruEntries.remove(key);
        
        return true;
    }
    
    /**
     * Trim the cache to the maximum size.
     */
    private void trimToSize() {
        while (size > maxSize && !lruEntries.isEmpty()) {
            Map.Entry<String, Entry> toEvict = lruEntries.entrySet().iterator().next();
            try {
                remove(toEvict.getKey());
            } catch (IOException e) {
                // Continue with the next entry
            }
        }
    }
    
    /**
     * Get the current cache size in bytes.
     *
     * @return Cache size
     */
    public synchronized long size() {
        return size;
    }
    
    /**
     * Get the maximum cache size in bytes.
     *
     * @return Maximum cache size
     */
    public long maxSize() {
        return maxSize;
    }
    
    /**
     * Clear all entries from the cache.
     *
     * @throws IOException If an I/O error occurs
     */
    public synchronized void clear() throws IOException {
        checkNotClosed();
        
        // Delete all cache files
        for (Entry entry : new HashMap<>(lruEntries).values()) {
            entry.getFile().delete();
            entry.getTempFile().delete();
        }
        
        // Clear the entries
        lruEntries.clear();
        size = 0;
        
        // Rebuild the journal
        rebuildJournal();
    }
    
    /**
     * Close the cache and release any resources.
     *
     * @throws IOException If an I/O error occurs
     */
    public synchronized void close() throws IOException {
        if (closed) {
            return;
        }
        
        // Close all editors
        for (Entry entry : new ArrayList<>(lruEntries.values())) {
            if (entry.currentEditor != null) {
                entry.currentEditor.abort();
            }
        }
        
        // Rebuild the journal
        rebuildJournal();
        
        // Mark as closed
        closed = true;
        
        // Shutdown the executor
        executor.shutdown();
    }
    
    /**
     * Check if the cache is closed.
     */
    private void checkNotClosed() {
        if (closed) {
            throw new IllegalStateException("Cache is closed");
        }
    }
    
    /**
     * Validate a cache key.
     *
     * @param key Key to validate
     */
    private void validateKey(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("Key must not be null or empty");
        }
        
        // Check for invalid characters
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c <= ' ' || c >= 127 || c == '/' || c == '\\') {
                throw new IllegalArgumentException(
                        "Keys must not contain spaces, control characters, or: / \\");
            }
        }
    }
    
    /**
     * Check if an entry exists in the cache.
     *
     * @param key Key to check
     * @return true if the entry exists, false otherwise
     */
    public synchronized boolean contains(String key) {
        checkNotClosed();
        validateKey(key);
        
        Entry entry = lruEntries.get(key);
        return entry != null && entry.readable && !entry.isExpired();
    }
    
    /**
     * Get the number of entries in the cache.
     *
     * @return Entry count
     */
    public synchronized int getEntryCount() {
        return lruEntries.size();
    }
    
    /**
     * Remove all expired entries from the cache.
     */
    public synchronized void removeExpiredEntries() {
        // Collect expired entries
        List<String> keysToRemove = new ArrayList<>();
        for (Map.Entry<String, Entry> mapEntry : lruEntries.entrySet()) {
            Entry entry = mapEntry.getValue();
            if (entry.isExpired()) {
                keysToRemove.add(mapEntry.getKey());
            }
        }
        
        // Remove expired entries
        for (String key : keysToRemove) {
            try {
                remove(key);
            } catch (IOException e) {
                // Continue with the next entry
            }
        }
    }
    
    /**
     * Submit a task to run on the background thread.
     *
     * @param callable Task to run
     * @param <T>      Result type
     * @return Future representing the task
     */
    public <T> Future<T> submit(Callable<T> callable) {
        checkNotClosed();
        return executor.submit(callable);
    }
    
    /**
     * Get all keys in the cache.
     *
     * @return Set of keys
     */
    public synchronized Set<String> getKeys() {
        return new HashSet<>(lruEntries.keySet());
    }
    
    /**
     * Execute a flush operation, writing any pending changes to disk.
     *
     * @throws IOException If an I/O error occurs
     */
    public synchronized void flush() throws IOException {
        checkNotClosed();
        rebuildJournal();
    }
}