package com.worldwar.backend;

import com.worldwar.backend.task.InitRequestTask;
import com.worldwar.backend.task.TaskScheduler;

import java.util.ArrayList;
import java.util.List;

public class Roster {
    private int count;
    private List<RosterItem> torrents = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
}
