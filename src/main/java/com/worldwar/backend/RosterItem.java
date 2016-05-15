package com.worldwar.backend;

public class RosterItem {
    private String filename;
    private String target;
    private String torrent;
    private Long size;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTorrent() {
        return torrent;
    }

    public void setTorrent(String torrent) {
        this.torrent = torrent;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "RosterItem{" +
                "filename='" + filename + '\'' +
                ", target='" + target + '\'' +
                ", torrent='" + torrent + '\'' +
                '}';
    }
}
