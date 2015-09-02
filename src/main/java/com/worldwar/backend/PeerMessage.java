package com.worldwar.backend;

import com.worldwar.utility.Lists;

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

    public byte[] raw() {
        return Lists.concat(new byte[] {(byte) length}, id, content);
    }
}
