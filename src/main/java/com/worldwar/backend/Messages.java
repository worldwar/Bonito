package com.worldwar.backend;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.worldwar.utility.Lists;

public class Messages {
    public static final String PROTOCOL_STRING = "BitTorrent protocol";
    public static final byte[] FAKE_HASH_INFO = "AAAAABBBBBCCCCCDDDDD".getBytes();
    public static final byte[] PEER_ID = peer_id();
    public static final byte[] RESERVED = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    public static final int RESERVED_LENGTH = 8;
    public static final int PEER_ID_LENGTH = 20;
    public static final int HASH_INFO_LENGTH = 20;
    public static Map<Byte, MessageType> types;

    static {
        types = new HashMap<>();
        types.put((byte) 0, MessageType.CHOKE);
        types.put((byte) 1, MessageType.UNCHOKE);
        types.put((byte) 2, MessageType.INTERESTED);
        types.put((byte) 3, MessageType.NOT_INTERESTED);
        types.put((byte) 4, MessageType.HAVE);
        types.put((byte) 5, MessageType.BITFIELD);
        types.put((byte) 6, MessageType.REQUEST);
    }

    private static byte[] peer_id() {
        String prefix = "-BN0001-";
        String result = prefix + Randoms.digital(12);
        return result.getBytes();
    }

    public static PeerMessage keepAlive() {
        return new PeerMessage(0, null, null, MessageType.KEEP_ALIVE);
    }

    public static PeerMessage handshake() {
        return new PeerMessage(PROTOCOL_STRING.length(), PROTOCOL_STRING.getBytes(), null, MessageType.HANDSHAKE);
    }

    public static PeerMessage handshake(byte[] hash_info) {
        byte[] content = Lists.concat(RESERVED, hash_info, PEER_ID);
        return new PeerMessage(PROTOCOL_STRING.length(), PROTOCOL_STRING.getBytes(), content, MessageType.HANDSHAKE);
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

    public static PeerMessage unchoke() {
        return new PeerMessage(1, new byte[]{1}, null, MessageType.UNCHOKE);
    }

    public static PeerMessage interested() {
        return new PeerMessage(1, new byte[]{2}, null, MessageType.INTERESTED);
    }

    public static PeerMessage notInterested() {
        return new PeerMessage(1, new byte[]{3}, null, MessageType.NOT_INTERESTED);
    }

    public static PeerMessage have(int index) {
        byte[] content = Ints.toByteArray(index);
        return new PeerMessage(5, new byte[]{4}, content, MessageType.HAVE);
    }

    public static PeerMessage bitField(byte[] bytes) {
        return new PeerMessage(bytes.length + 1, new byte[]{5}, bytes, MessageType.BITFIELD);
    }

    public static PeerMessage request(int index, int begin, int length) {
        byte[] bytes = Bytes.concat(Ints.toByteArray(index), Ints.toByteArray(begin), Ints.toByteArray(length));
        return new PeerMessage(13, new byte[]{6}, bytes, MessageType.REQUEST);
    }
}
