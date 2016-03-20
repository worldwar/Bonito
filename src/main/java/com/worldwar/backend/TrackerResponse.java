package com.worldwar.backend;

import java.util.List;
import java.util.Map;

public class TrackerResponse {
    private String failureReason;
    private String warningMessage;
    private int interval;
    private int minInterval;
    private String trackerId;
    private int complete;
    private int incomplete;
    private List<Map<String, Object>> peers;

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(int incomplete) {
        this.incomplete = incomplete;
    }

    public List<Map<String, Object>> getPeers() {
        return peers;
    }

    public void setPeers(List<Map<String, Object>> peers) {
        this.peers = peers;
    }
}
