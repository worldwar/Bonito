package com.worldwar.backend.task;

import com.worldwar.backend.TorrentContext;
import com.worldwar.backend.TorrentRegister;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Callable;

public class PieceTask implements Callable<Void> {
    private int index;
    private int begin;
    private int length;
    private byte[] hashinfo;
    private ChannelHandlerContext ctx;

    public PieceTask(int index, int begin, int length, byte[] hashinfo, ChannelHandlerContext ctx) {
        this.index = index;
        this.begin = begin;
        this.length = length;
        this.hashinfo = hashinfo;
        this.ctx = ctx;
    }

    @Override
    public Void call() throws Exception {
        TorrentContext context = TorrentRegister.get(hashinfo);
        SendMessageTask piece = new SendMessageTaskFactory(ctx).piece(index, begin, length, context);
        TaskScheduler.getInstance().emit(piece);
        return null;
    }
}
