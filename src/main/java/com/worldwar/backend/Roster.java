package com.worldwar.backend;

import com.worldwar.backend.task.InitRequestTask;
import com.worldwar.backend.task.TaskScheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Roster {

    private List<RosterItem> torrents = new ArrayList<>();

    public List<RosterItem> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<RosterItem> torrents) {
        this.torrents = torrents;
    }

    public void register() {
        for (RosterItem item : torrents) {
            TorrentContext context = TorrentContexts.from(item);
            TorrentRegister.register(context);
        }
        TaskScheduler.getInstance().emit(new InitRequestTask());
    }

    public boolean addTorrent(RosterItem rosterItem) throws IOException {
        TorrentContext context = TorrentContexts.from(rosterItem);
        if (TorrentRegister.get(context.hashinfo()) != null) {
            return false;
        }
        TorrentRegister.register(context);
        torrents.add(rosterItem);
        return true;
    }
}
