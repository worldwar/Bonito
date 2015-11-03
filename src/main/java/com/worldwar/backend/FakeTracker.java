package com.worldwar.backend;

public class FakeTracker {
    public static PeerAddress send(byte[] hashinfo) {
        return new PeerAddress("localhost", 9999);
    }
}
