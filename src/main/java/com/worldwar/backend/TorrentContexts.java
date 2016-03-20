package com.worldwar.backend;

import com.google.common.collect.Lists;
import com.worldwar.Metainfo;
import com.worldwar.Metainfos;

import java.net.InetSocketAddress;

public class TorrentContexts {
    public static TorrentContext make() {
        TorrentContext torrentContext = new TorrentContext();
        torrentContext.setPeers(Lists.newArrayList(new InetSocketAddress("localhost", 8888)));
        return torrentContext;
    }

    public static TorrentContext make(Metainfo metainfo, String target) {
        TorrentContext torrentContext = new TorrentContext();
        torrentContext.setPeers(Lists.newArrayList(new InetSocketAddress("localhost", 8888)));
        torrentContext.setPieceLength(metainfo.pieceLength());
        torrentContext.setTargetPath(target);
        torrentContext.setTargetSize(metainfo.targetLength());
        torrentContext.setHashinfo(Metainfos.hashinfo(metainfo));
        torrentContext.setPieces(metainfo.getInfo().getPieces());
        torrentContext.setAnnounce(metainfo.getAnnounce());
        return torrentContext;
    }

    public static TorrentContext from(RosterItem rosterItem) {
        Metainfo metainfo = Metainfos.read(rosterItem.getTorrent());
        return TorrentContexts.make(metainfo, rosterItem.getTarget());
    }

    public static boolean finished(byte[] bitfield, int pieceCount) {
        return Bits.every(bitfield, 0, pieceCount, Bits.identity());
    }
}
