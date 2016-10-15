package com.worldwar.ui;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

public class DoneTask extends Task<Void> {

    private final int waitTime; // milliseconds
    private final int pauseTime; // milliseconds

    public static final int NUM_ITERATIONS = 100;

    DoneTask(int waitTime, int pauseTime) {
        this.waitTime = waitTime;
        this.pauseTime = pauseTime;
    }

    @Override
    protected Void call() throws Exception {
        this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);
        this.updateMessage("Waiting...");
        Thread.sleep(waitTime);
        this.updateMessage("Running...");
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            updateProgress((1.0 * i) / NUM_ITERATIONS, 1);
            Thread.sleep(pauseTime);
        }
        this.updateMessage("Done");
        this.updateProgress(1, 1);
        return null;
    }

}
