package com.worldwar.backend.task;

import java.util.Arrays;
import java.util.concurrent.Callable;

import com.worldwar.backend.Channels;
import com.worldwar.backend.PeerMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class SendMessageTask implements Callable<Void> {
    private ChannelHandlerContext ctx = null;
    private PeerMessage message = null;
    private Channel channel = null;

    public SendMessageTask(ChannelHandlerContext ctx, PeerMessage message) {
        this.ctx = ctx;
        this.message = message;
    }

    public SendMessageTask(Channel channel, PeerMessage message) {
        this.channel= channel;
        this.message = message;
    }

    @Override
    public Void call() throws Exception {
        System.out.println(message.getType() + ":" + Arrays.toString(message.raw()));
        if (ctx != null) {
            Channels.write(ctx, message.raw());
        } else {
            Channels.write(channel, message.raw());
        }
        return null;
    }
}
