package com.worldwar.backend.task;

import com.worldwar.backend.TorrentContext;
import com.worldwar.backend.TorrentRegister;

import java.util.List;
import java.util.concurrent.Callable;

public class InitRequestTask implements Callable<Void> {
    @Override
    public Void call() throws Exception {
        List<TorrentContext> all = TorrentRegister.all();
        for (TorrentContext context : all) {
            context.start();
        }
        return null;
    }
}
