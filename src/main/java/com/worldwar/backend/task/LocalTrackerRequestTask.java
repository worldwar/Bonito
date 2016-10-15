package com.worldwar.backend.task;

import com.google.common.collect.Lists;
import com.worldwar.backend.TrackerRequest;
import com.worldwar.backend.TrackerResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class LocalTrackerRequestTask implements Callable<Void> {
    private TrackerResponseCallback callable;

    public LocalTrackerRequestTask(TrackerRequest request, TrackerResponseCallback callback) {
        this.callable = callback;
    }

    @Override
    public Void call() throws Exception {
        Map<String, Object> peers = new HashMap<>();
        peers.put("port", 9999);
        peers.put("ip", "localhost");
        TrackerResponse trackerResponse = new TrackerResponse();
        trackerResponse.setPeers(Lists.newArrayList(peers));
        callable.callback(trackerResponse);
        return null;
    }
}
