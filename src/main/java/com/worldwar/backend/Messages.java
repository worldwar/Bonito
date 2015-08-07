package com.worldwar.backend;

import com.google.common.primitives.Bytes;

public class Messages {
    public static final String PROTOCOL_STRING = "BitTorrent protocol";
    public static final byte[] PEER_ID = peer_id();
    public static final byte[] RESERVED = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    public static final int RESERVED_LENGTH = 8;
    public static final int PEER_ID_LENGTH = 20;
    public static final int HASH_INFO_LENGTH = 20;

    private static byte[] peer_id() {
        String prefix = "-BN0001-";
        String result = prefix + Randoms.digital(12);
        return result.getBytes();
    }

    public static byte[] handshake(byte[] hash_info) {
        byte[] length = new byte[] {(byte)PROTOCOL_STRING.length()};
        return Bytes.concat(length, PROTOCOL_STRING.getBytes(), RESERVED, hash_info, PEER_ID);
    }
}
