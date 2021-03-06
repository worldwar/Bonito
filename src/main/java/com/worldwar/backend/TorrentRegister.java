package com.worldwar.backend;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TorrentRegister {
    private static ConcurrentHashMap<Integer, TorrentContext> contexts;

    static {
        contexts = new ConcurrentHashMap<>();
    }

    public static TorrentContext get(byte[] hashinfo) {
        return contexts.get(Arrays.hashCode(hashinfo));
    }

    public static void register(TorrentContext context) {
        contexts.put(Arrays.hashCode(context.hashinfo()), context);
    }

    public static void unregister(TorrentContext context) {
        contexts.remove(Arrays.hashCode(context.hashinfo()));
    }

    public static void unregisterAll() {
        contexts.clear();
    }

    public static List<TorrentContext> all() {
        return ImmutableList.copyOf(contexts.values());
    }
}
