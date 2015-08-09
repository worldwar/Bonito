package com.worldwar.backend;

public class ConnectionStatus {
    private boolean handshakeDone;
    private boolean amChoking;
    private boolean amInterested;
    private boolean peerChoking;
    private boolean peerInterested;
    private byte[] peerId;
    private byte[] hashInfo;

    ConnectionStatus() {
        handshakeDone = false;
        amChoking = true;
        amInterested = false;
        peerChoking = true;
        peerInterested = false;
        peerId = null;
        hashInfo = null;
    }

    public boolean handshakeDone() {
        return handshakeDone;
    }

    public void setHandshakeDone(boolean handshakeDone) {
        this.handshakeDone = handshakeDone;
    }

    public boolean amChoking() {
        return amChoking;
    }

    public void setAmChoking(boolean amChoking) {
        this.amChoking = amChoking;
    }

    public boolean amInterested() {
        return amInterested;
    }

    public void setAmInterested(boolean amInterested) {
        this.amInterested = amInterested;
    }

    public boolean peerChoking() {
        return peerChoking;
    }

    public void setPeerChoking(boolean peerChoking) {
        this.peerChoking = peerChoking;
    }

    public boolean peerInterested() {
        return peerInterested;
    }

    public void setPeerInterested(boolean peerInterested) {
        this.peerInterested = peerInterested;
    }

    public byte[] getPeerId() {
        return peerId;
    }

    public void setPeerId(byte[] peerId) {
        this.peerId = peerId;
    }

    public byte[] getHashInfo() {
        return hashInfo;
    }

    public void setHashInfo(byte[] hashInfo) {
        this.hashInfo = hashInfo;
    }
}
