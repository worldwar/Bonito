package com.worldwar.backend;

import com.worldwar.Metainfo;

public class TorrentUnit {
    private Metainfo metainfo;
    private byte[] hashinfo;
    private String metainfoPath;
    private String targetPath;
    private byte[] bitfield;

    public Metainfo getMetainfo() {
        return metainfo;
    }

    public void setMetainfo(Metainfo metainfo) {
        this.metainfo = metainfo;
    }

    public byte[] getHashinfo() {
        return hashinfo;
    }

    public void setHashinfo(byte[] hash_info) {
        this.hashinfo = hash_info;
    }

    public String getMetainfoPath() {
        return metainfoPath;
    }

    public void setMetainfoPath(String metainfoPath) {
        this.metainfoPath = metainfoPath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}
