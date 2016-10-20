package com.worldwar.backend;

import com.worldwar.utility.Rosters;
import io.netty.channel.ChannelFuture;

import java.io.IOException;

public class Agent {

    private String config;

    private Listener listener;

    private Roster roster;

    /**
     * @param config
     */
    public Agent(String config) {
        this.config = config;
        roster = Rosters.from(config);
        listener = new Listener();
    }

    public ChannelFuture start() throws InterruptedException {
        ChannelFuture future = listener.listen(9999);
        roster.register();
        return future;
    }

    public boolean add(RosterItem rosterItem) throws IOException {
        boolean success = roster.addTorrent(rosterItem);
        if (success) {
            save();
        }
        return success;
    }

    public boolean add(String torrent, String target, String filename) throws IOException {
        RosterItem rosterItem = TorrentContexts.rosterItem(torrent, target, filename);
        return add(rosterItem);
    }

    public void save() {
        Rosters.write(roster, config);
    }

    public int count() {
        return roster.getTorrents().size();
    }
}
