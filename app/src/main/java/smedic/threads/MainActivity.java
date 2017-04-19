package smedic.threads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.Future;

import smedic.threads.priority.Priority;
import smedic.threads.priority.PriorityRunnable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Get the future of the task by submitting it to the pool
         */
        Future future = DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .submit(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                    }
                });

        /**
         * cancelling the task
         */
        future.cancel(true);
    }

    /**
     * Using it for Background Tasks
     */
    public void doSomeBackgroundWork() {
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                    }
                });
    }

    /**
     * Using it for Light-Weight Background Tasks
     */
    public void doSomeLightWeightBackgroundWork() {
        DefaultExecutorSupplier.getInstance().forLightWeightBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some light-weight background work here.
                    }
                });
    }

    /**
     * Using it for MainThread Tasks
     */
    public void doSomeMainThreadWork() {
        DefaultExecutorSupplier.getInstance().forMainThreadTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some Main Thread work here.
                    }
                });
    }

    /**
     * do some task at high priority
     */
    public void doSomeTaskAtHighPriority() {
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .submit(new PriorityRunnable(Priority.HIGH) {
                    @Override
                    public void run() {
                        // do some background work here at high priority.
                    }
                });
    }
}
