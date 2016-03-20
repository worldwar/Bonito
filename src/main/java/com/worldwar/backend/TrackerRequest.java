package com.worldwar.backend;

public class TrackerRequest {
    private String url;
    private String info_hash;
    private String peer_id;
    private int port;
    private long uploaded;
    private long downloaded;
    private long left;
    private int compact;
    private boolean no_peer_id;
    private String event;
    private String ip;
    private int numwant;
    private String key;
    private String trackerid;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo_hash() {
        return info_hash;
    }

    public void setInfo_hash(String info_hash) {
        this.info_hash = info_hash;
    }

    public String getPeer_id() {
        return peer_id;
    }

    public void setPeer_id(String peer_id) {
        this.peer_id = peer_id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getUploaded() {
        return uploaded;
    }

    public void setUploaded(long uploaded) {
        this.uploaded = uploaded;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloaded) {
        this.downloaded = downloaded;
    }

    public long getLeft() {
        return left;
    }

    public void setLeft(long left) {
        this.left = left;
    }

    public int getCompact() {
        return compact;
    }

    public void setCompact(int compact) {
        this.compact = compact;
    }

    public boolean isNo_peer_id() {
        return no_peer_id;
    }

    public void setNo_peer_id(boolean no_peer_id) {
        this.no_peer_id = no_peer_id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getNumwant() {
        return numwant;
    }

    public void setNumwant(int numwant) {
        this.numwant = numwant;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTrackerid() {
        return trackerid;
    }

    public void setTrackerid(String trackerid) {
        this.trackerid = trackerid;
    }
}
