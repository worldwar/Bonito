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
    private long targetSize;
    private String targetPath;
    private int pieceLength;
    private byte[] hashinfo;
    private byte[] bitfield;
    private List<byte[]> pieces;

    public TorrentContext() {
        unit = new TorrentUnit();
        peers = new ArrayList<>();
    }

    public void start() throws IOException {
        target = Systems.file(targetPath, targetSize);
        bitfield = Systems.calculateBitfield(target, pieces, pieceLength);
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
        return hashinfo;
    }

    public File getTarget() {
        return target;
    }

    public long getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(long targetSize) {
        this.targetSize = targetSize;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public int getPieceLength() {
        return pieceLength;
    }

    public void setPieceLength(int pieceLength) {
        this.pieceLength = pieceLength;
    }

    public void setHashinfo(byte[] hashinfo) {
        this.hashinfo = hashinfo;
    }
}
