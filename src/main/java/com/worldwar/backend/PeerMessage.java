package com.worldwar.backend;

public class PeerMessage {
    private int length;
    private byte[] id;
    private byte[] content;
    private MessageType type;

    public PeerMessage(int length, byte[] id, byte[] content, MessageType type) {
        this.length = length;
        this.id = id;
        this.content = content;
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    //return human-friendly string to make test easier
    //would be bytes contain id + content
    public byte[] raw() {
        return "Hello, World!".getBytes();
    }
}
