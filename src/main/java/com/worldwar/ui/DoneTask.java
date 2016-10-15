package com.worldwar.ui;

import com.worldwar.backend.TorrentContext;
import javafx.concurrent.Task;

public class DoneTask extends Task<Void> {

    private final int waitTime; // milliseconds
    private final int pauseTime; // milliseconds
    private final TorrentContext torrentContext;
    private final Torrenta torrenta;

    DoneTask(int waitTime, int pauseTime, TorrentContext torrentContext, Torrenta torrenta) {
        this.waitTime = waitTime;
        this.pauseTime = pauseTime;
        this.torrentContext = torrentContext;
        this.torrenta = torrenta;
    }

    @Override
    protected Void call() throws Exception {
        this.updateMessage("Waiting...");
        Thread.sleep(waitTime);
        this.updateMessage("Running...");
        while (true) {
            updateProgress(torrentContext.done(), 1);
            torrenta.setDone(torrentContext.done());
            Thread.sleep(pauseTime);
        }
    }

}
