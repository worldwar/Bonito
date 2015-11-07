package com.worldwar.backend;

import java.util.Arrays;

public class HandshakeMessage extends PeerMessage {

    public HandshakeMessage(byte[] content) {
        super(Messages.PROTOCOL_STRING.length(), null, null, MessageType.HANDSHAKE);
        int length = Messages.PROTOCOL_STRING.length();
        byte[] protocol = Arrays.copyOfRange(content, 0, length);
        byte[] bytes = Arrays.copyOfRange(content, length, length + 48);
        this.setId(protocol);
        this.setContent(bytes);
    }

    public byte[] hashinfo() {
        return Arrays.copyOfRange(getContent(), 8, 28);
    }

    public byte[] peerid() {
        return Arrays.copyOfRange(getContent(), 28, 48);
    }
}
