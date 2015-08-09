package com.worldwar.backend;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.primitives.Bytes;

public class Messages {
    public static final String PROTOCOL_STRING = "BitTorrent protocol";
    public static final byte[] PEER_ID = peer_id();
    public static final byte[] RESERVED = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    public static final int RESERVED_LENGTH = 8;
    public static final int PEER_ID_LENGTH = 20;
    public static final int HASH_INFO_LENGTH = 20;
    public static Map<Byte, MessageType> types;

    static {
        types = new HashMap<>();
        types.put((byte) 0, MessageType.CHOKE);
    }

    private static byte[] peer_id() {
        String prefix = "-BN0001-";
        String result = prefix + Randoms.digital(12);
        return result.getBytes();
    }

    public static byte[] handshake(byte[] hash_info) {
        byte[] length = new byte[] {(byte)PROTOCOL_STRING.length()};
        return Bytes.concat(length, PROTOCOL_STRING.getBytes(), RESERVED, hash_info, PEER_ID);
    }

    public static PeerMessage keepAlive() {
        return new PeerMessage(0, null, null, MessageType.KEEP_ALIVE);
    }

    public static PeerMessage handshake() {
        return new PeerMessage(PROTOCOL_STRING.length(), PROTOCOL_STRING.getBytes(), null, MessageType.HANDSHAKE);
    }

    public static PeerMessage choke() {
        return new PeerMessage(1, new byte[]{0}, null, MessageType.CHOKE);
    }

    public static PeerMessage message(int length, byte[] content) {
        if (length == 0) {
            return keepAlive();
        } else if (content == null) {
            return null;
        } else if (Arrays.equals(content, PROTOCOL_STRING.getBytes())) {
            return handshake();
        } else {
            return new PeerMessage(length, Arrays.copyOf(content, 1), Arrays.copyOfRange(content, 1, length), type(length, content));
        }
    }

    public static MessageType type(int length, byte[] id) {
        if (length == 0) {
            return MessageType.KEEP_ALIVE;
        } else if (Arrays.equals(id, PROTOCOL_STRING.getBytes())) {
            return MessageType.HANDSHAKE;
        } else {
            return types.get(id[0]);
        }
    }
}
