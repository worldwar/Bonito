package com.worldwar.backend.task;

import java.util.concurrent.Callable;

public class PieceTask implements Callable<Void> {
    private int index;
    private int begin;
    private int length;

    public PieceTask(int index, int begin, int length) {
        this.index = index;
        this.begin = begin;
        this.length = length;
    }

    @Override
    public Void call() throws Exception {
        return null;
    }
}
