package com.worldwar.backend;

import java.util.List;

public class Roster {
    private int count;
    private List<RosterItem> torrents;

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
}
