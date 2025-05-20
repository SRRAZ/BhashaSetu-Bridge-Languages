package com.example.englishhindi.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Global executor pools for the whole application.
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads
 * don't wait behind webservice requests).
 */
public class AppExecutors {
    
    private static final int THREAD_COUNT = 3;
    
    private final ExecutorService diskIO;
    private final ExecutorService networkIO;
    private final ExecutorService mainThread;
    private final ScheduledExecutorService scheduler;
    
    private static class LazyHolder {
        static final AppExecutors INSTANCE = new AppExecutors();
    }
    
    public static AppExecutors getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    private AppExecutors() {
        this(Executors.newSingleThreadExecutor(new ThreadFactory() {
            private final AtomicInteger threadId = new AtomicInteger(0);
            
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("app_disk_io_" + threadId.getAndIncrement());
                thread.setPriority(Thread.NORM_PRIORITY - 1);
                return thread;
            }
        }), Executors.newFixedThreadPool(THREAD_COUNT, new ThreadFactory() {
            private final AtomicInteger threadId = new AtomicInteger(0);
            
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("app_network_io_" + threadId.getAndIncrement());
                return thread;
            }
        }), new MainThreadExecutor(), Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("app_scheduler");
                thread.setPriority(Thread.NORM_PRIORITY - 1);
                return thread;
            }
        }));
    }
    
    public AppExecutors(ExecutorService diskIO, ExecutorService networkIO, Executor mainThread,
                        ScheduledExecutorService scheduler) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = (ExecutorService) mainThread;
        this.scheduler = scheduler;
    }
    
    /**
     * Executor for disk IO operations
     * @return ExecutorService for disk operations
     */
    public ExecutorService diskIO() {
        return diskIO;
    }
    
    /**
     * Executor for network operations
     * @return ExecutorService for network operations
     */
    public ExecutorService networkIO() {
        return networkIO;
    }
    
    /**
     * Executor for main thread operations
     * @return Executor for main thread operations
     */
    public ExecutorService mainThread() {
        return mainThread;
    }
    
    /**
     * Scheduled executor for delayed/periodic tasks
     * @return ScheduledExecutorService for scheduled tasks
     */
    public ScheduledExecutorService scheduler() {
        return scheduler;
    }
    
    /**
     * Schedule a task to run after a delay
     * @param runnable Task to run
     * @param delay Delay time
     * @param unit Time unit for delay
     */
    public void schedule(Runnable runnable, long delay, TimeUnit unit) {
        scheduler.schedule(runnable, delay, unit);
    }
    
    /**
     * Schedule a task to run periodically
     * @param runnable Task to run
     * @param initialDelay Initial delay
     * @param period Period between executions
     * @param unit Time unit for delay and period
     */
    public void scheduleAtFixedRate(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(runnable, initialDelay, period, unit);
    }
    
    /**
     * Run a task on the main thread
     * @param runnable Task to run
     */
    public void mainThread(Runnable runnable) {
        mainThread.execute(runnable);
    }
    
    /**
     * Main thread executor that runs tasks on the main thread
     */
    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}