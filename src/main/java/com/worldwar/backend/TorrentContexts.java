package com.worldwar.backend;

import com.google.common.collect.Lists;

import java.net.InetSocketAddress;

public class TorrentContexts {
    public static TorrentContext make() {
        TorrentContext torrentContext = new TorrentContext();
        torrentContext.setPeers(Lists.newArrayList(new InetSocketAddress("localhost", 8888)));
        return torrentContext;
    }
}
