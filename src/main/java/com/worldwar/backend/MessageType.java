package com.worldwar.backend;

public enum MessageType {
    HANDSHAKE,
    KEEP_ALIVE,
    CHOKE,
    UNCHOKE,
    INTERESTED,
    NOT_INTERESTED,
    HAVE,
    BITFIELD,
    REQUEST
}
