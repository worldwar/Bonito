package com.worldwar.backend;

public class ConnectionStatus {
    private boolean handshakeDone;
    private boolean handshakeSend;
    private boolean amChoking;
    private boolean amInterested;
    private boolean peerChoking;
    private boolean peerInterested;
    private byte[] peerId;
    private byte[] hashInfo;
    private int pieceCount;
    private byte[] amBitField;
    private byte[] peerBitField;

    ConnectionStatus() {
        handshakeDone = false;
        handshakeDone = false;
        amChoking = true;
        amInterested = false;
        peerChoking = true;
        peerInterested = false;
        peerId = null;
        hashInfo = null;
        pieceCount = 35;  //a fake number. Its value should come from meta info file.
        int bitFieldLength = pieceCount / 8 + (pieceCount % 8 == 0 ? 0 : 1);
        amBitField = new byte[bitFieldLength];
        peerBitField = new byte[bitFieldLength];
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

    public byte[] getPeerBitField() {
        return peerBitField;
    }

    public void setPeerBitField(byte[] peerBitField) {
        this.peerBitField = peerBitField;
    }

    public byte[] getAmBitField() {
        return amBitField;
    }

    public void setAmBitField(byte[] amBitField) {
        this.amBitField = amBitField;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }

    public void setPeerHave(int index) {
        Bits.set(peerBitField, index);
    }

    public boolean handshakeSend() {
        return handshakeSend;
    }

    public void setHandshakeSend(boolean handshakeSend) {
        this.handshakeSend = handshakeSend;
    }
}
