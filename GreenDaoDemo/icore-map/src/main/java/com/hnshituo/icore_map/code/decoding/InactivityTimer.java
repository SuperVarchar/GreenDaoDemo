package com.hnshituo.icore_map.code.decoding;

import android.app.Activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Wzh
 * @date 2016/7/14  14:09
 */
public class InactivityTimer {
    private static final int INACTIVITY_DELAY_SECONDS = 300;
    private final ScheduledExecutorService inactivityTimer = Executors.newSingleThreadScheduledExecutor(new InactivityTimer.DaemonThreadFactory());
    private final Activity activity;
    private ScheduledFuture<?> inactivityFuture = null;

    public InactivityTimer(Activity activity) {
        this.activity = activity;
        this.onActivity();
    }

    public void onActivity() {
        this.cancel();
        this.inactivityFuture = this.inactivityTimer.schedule(new FinishListener(this.activity), 300L, TimeUnit.SECONDS);
    }

    private void cancel() {
        if(this.inactivityFuture != null) {
            this.inactivityFuture.cancel(true);
            this.inactivityFuture = null;
        }

    }

    public void shutdown() {
        this.cancel();
        this.inactivityTimer.shutdown();
    }

    private static final class DaemonThreadFactory implements ThreadFactory {
        private DaemonThreadFactory() {
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        }
    }
}
