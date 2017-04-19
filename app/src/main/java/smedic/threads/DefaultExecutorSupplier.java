package smedic.threads;

/**
 * Created by Stevan Medic on 19.4.17..
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import smedic.threads.priority.PriorityThreadPoolExecutor;

/**
 * Singleton class for default executor supplier
 */
public class DefaultExecutorSupplier {

    /**
     * Number of cores to decide the number of threads
     */
    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    /**
     * thread pool executor for background tasks
     */
    private final ThreadPoolExecutor backgroundTasksExecutor;

    /**
     * thread pool executor for light weight background tasks
     */
    private final ThreadPoolExecutor lightWeightBackgroundTasksExecutor;

    /**
     * thread pool executor for main thread tasks
     */
    private final Executor mainThreadExecutor;

    /**
     * an instance of DefaultExecutorSupplier
     */
    private static DefaultExecutorSupplier instance;

    /**
     * returns the instance of DefaultExecutorSupplier
     */
    public static DefaultExecutorSupplier getInstance() {
        if (instance == null) {
            synchronized (DefaultExecutorSupplier.class) {
                instance = new DefaultExecutorSupplier();
            }
        }
        return instance;
    }

    /**
     * constructor for DefaultExecutorSupplier
     */
    private DefaultExecutorSupplier() {

        // setting the thread factory
        ThreadFactory backgroundPriorityThreadFactory = new PriorityThreadFactory(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        // setting the thread pool executor for background tasks;
        // for priorities, used PriorityThreadPoolExecutor
        // if priority is not important, use regular ThreadPoolExecutor
        backgroundTasksExecutor = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );


        // setting the thread pool executor for mForLightWeightBackgroundTasks;
        // for priorities, used PriorityThreadPoolExecutor
        // if priority is not important, use regular ThreadPoolExecutor
        lightWeightBackgroundTasksExecutor = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );

        // setting the thread pool executor for mMainThreadExecutor;
        mainThreadExecutor = new MainThreadExecutor();
    }

    /**
     * returns the thread pool executor for background task
     */
    public ThreadPoolExecutor forBackgroundTasks() {
        return backgroundTasksExecutor;
    }

    /**
     * returns the thread pool executor for light weight background task
     */
    public ThreadPoolExecutor forLightWeightBackgroundTasks() {
        return lightWeightBackgroundTasksExecutor;
    }

    /**
     * returns the thread pool executor for main thread task
     */
    public Executor forMainThreadTasks() {
        return mainThreadExecutor;
    }
}
