package com.worldwar.backend.task;

import java.util.concurrent.Callable;

import com.worldwar.backend.Channels;
import com.worldwar.backend.PeerMessage;
import io.netty.channel.ChannelHandlerContext;

public class SendMessageTask implements Callable<Void> {
    private ChannelHandlerContext ctx;
    private PeerMessage message;

    public SendMessageTask(ChannelHandlerContext ctx, PeerMessage message) {
        this.ctx = ctx;
        this.message = message;
    }

    @Override
    public Void call() throws Exception {
        Channels.write(ctx, message.raw());
        return null;
    }
}
