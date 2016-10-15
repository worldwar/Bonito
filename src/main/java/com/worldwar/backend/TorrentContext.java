package com.worldwar.backend;

import com.worldwar.backend.task.TaskScheduler;
import com.worldwar.backend.task.TorrentRequestTask;
import com.worldwar.utility.Numbers;
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
    private volatile long downloadedSize;
    private String targetPath;
    private int pieceLength;
    private byte[] hashinfo;
    private byte[] bitfield;
    private List<byte[]> pieces;
    private long pieceCount;
    private String announce;

    public TorrentContext() {
        unit = new TorrentUnit();
        peers = new ArrayList<>();
    }

    public void start() throws IOException {
        target = Systems.file(targetPath, targetSize);
        bitfield = Systems.calculateBitfield(target, pieces, pieceLength);
        downloadedSize = Bits.numberOfSetBits(bitfield) * pieceLength;
        pieceCount = Numbers.times(targetSize, (long)pieceLength);
        TaskScheduler.getInstance().emit(new TorrentRequestTask(this));
        TorrentRegister.register(this);
    }

    public double done() {
        return downloadedSize * 1.0 / targetSize;
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

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public TorrentContext setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
        return this;
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

    public byte[] bitfield() {
        return bitfield;
    }

    public long pieceCount() {
        return pieceCount;
    }

    public List<byte[]> getPieces() {
        return pieces;
    }

    public void setPieces(List<byte[]> pieces) {
        this.pieces = pieces;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }
}
