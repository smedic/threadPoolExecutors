package smedic.threads;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Stevan Medic on 19.4.17..
 */

public class PriorityThreadFactory implements ThreadFactory {

    private final int threadPriority;

    public PriorityThreadFactory(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    @Override
    public Thread newThread(@NonNull final Runnable runnable) {
        Runnable wrapperRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    android.os.Process.setThreadPriority(threadPriority);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                runnable.run();
            }
        };
        return new Thread(wrapperRunnable);
    }

}
