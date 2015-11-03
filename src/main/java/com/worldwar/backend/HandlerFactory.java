package com.worldwar.backend;

public class HandlerFactory {
    public static PeerHandler server() {
        return new PeerHandler(PeerHandler.HANDLER_TYPE_SERVER);
    }

    public static PeerHandler client(byte[] initialHashinfo) {
        PeerHandler peerHandler = new PeerHandler(PeerHandler.HANDLER_TYPE_CLIENT);
        peerHandler.setInitialHashinfo(initialHashinfo);
        return peerHandler;
    }
}
