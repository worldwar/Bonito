package com.worldwar.backend;

import com.worldwar.backend.task.ConnectTask;
import com.worldwar.backend.task.TaskScheduler;
import com.worldwar.utility.Systems;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class TorrentContext {
    private TorrentUnit unit;
    private List<InetSocketAddress> peers;
    private File target;

    public TorrentContext() {
        unit = new TorrentUnit();
        peers = new ArrayList<>();
    }

    public void start() throws IOException {
        target = Systems.file(unit.getTargetPath(), unit.targetSize());
        TaskScheduler.getInstance().emit(new ConnectTask(Randoms.pick(peers)));
        TorrentRegister.register(this);
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

    public byte[] hashinfo() {
        return unit.getHashinfo();
    }

    public File getTarget() {
        return target;
    }
}
