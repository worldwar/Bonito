package com.worldwar.backend.task;

import com.worldwar.backend.Messages;
import io.netty.channel.ChannelHandlerContext;

public class SendMessageTaskFactory {
    private ChannelHandlerContext ctx;

    public SendMessageTaskFactory(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public SendMessageTask unchoke() {
        return new SendMessageTask(ctx, Messages.unchoke());
    }

    public SendMessageTask interested() {
        return new SendMessageTask(ctx, Messages.interested());
    }
}
