package com.worldwar.backend;

import java.util.concurrent.ConcurrentHashMap;

public class TorrentRegister {
    private static ConcurrentHashMap<byte[], TorrentContext> contexts;

    static {
        contexts = new ConcurrentHashMap<>();
    }

    public static TorrentContext get(byte[] hashinfo) {
        return contexts.get(hashinfo);
    }

    public static void register(TorrentContext context) {
        contexts.put(context.hashinfo(), context);
    }

    public static void unregister(TorrentContext context) {
        contexts.remove(context.hashinfo());
    }

    public static void unregisterAll() {
        contexts.clear();
    }
}
