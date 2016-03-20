package com.worldwar.backend.task;

import com.worldwar.backend.*;
import com.worldwar.utility.Systems;

import java.util.concurrent.Callable;

public class TorrentRequestTask implements Callable<Void>{
    private TorrentContext torrentContext;

    public TorrentRequestTask(TorrentContext context) {
        this.torrentContext = context;
    }

    @Override
    public Void call() throws Exception {
        TaskScheduler.getInstance().emit(new TrackerRequestTask(Systems.trackerRequest(torrentContext),
                new TrackerResponseCallback(torrentContext)));
        return null;
    }
}
