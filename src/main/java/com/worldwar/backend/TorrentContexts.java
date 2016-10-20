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
        TorrentContext context = TorrentContexts.make(metainfo, rosterItem.getTarget());
        context.setDownloadedSize(rosterItem.getDownloaded());
        return context;
    }

    public static boolean finished(byte[] bitfield, int pieceCount) {
        return Bits.every(bitfield, 0, pieceCount, Bits.identity());
    }

    public static RosterItem rosterItem(String torrent, String target, String filename) {
        Metainfo metainfo = Metainfos.read(torrent);
        RosterItem rosterItem = new RosterItem();
        rosterItem.setTorrent(torrent);
        rosterItem.setTarget(target);
        rosterItem.setFilename(filename);
        rosterItem.setSize(metainfo.getInfo().getLength());
        rosterItem.setDownloaded(0L);
        return rosterItem;
    }
}
