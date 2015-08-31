package com.worldwar.backend.task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskScheduler {
    private ExecutorService executor = Executors.newScheduledThreadPool(8);
    private static TaskScheduler scheduler = null;
    private Timer timer = null;

    private TaskScheduler() {
        this.timer = new Timer();
    }

    public static synchronized TaskScheduler getInstance() {
        if (scheduler == null) {
            scheduler = new TaskScheduler();
        }
        return scheduler;
    }

    public void emit(Callable task) {
        executor.submit(task);
    }

    public void schedule(TimerTask timerTask, long seconds) {
        timer.schedule(timerTask, 1000, seconds * 1000);
    }
}
