package com.worldwar.backend;

import com.worldwar.backend.task.ConnectTask;
import com.worldwar.backend.task.TaskScheduler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class TorrentContext {
    private TorrentUnit unit;
    private List<InetSocketAddress> peers;

    public TorrentContext() {
        unit = new TorrentUnit();
        peers = new ArrayList<>();
    }

    public void start() {
        TaskScheduler.getInstance().emit(new ConnectTask(Randoms.pick(peers)));
    }

    public TorrentUnit getUnit() {
        return unit;
    }

    public void setUnit(TorrentUnit unit) {
        this.unit = unit;
    }

    public List<InetSocketAddress> getPeers() {
        return peers;
    }

    public void setPeers(List<InetSocketAddress> peers) {
        this.peers = peers;
    }
}
