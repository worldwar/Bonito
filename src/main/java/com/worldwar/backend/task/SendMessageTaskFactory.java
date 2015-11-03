package com.worldwar.backend.task;

import com.worldwar.backend.Messages;
import com.worldwar.backend.TorrentContext;
import com.worldwar.utility.Systems;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.IOException;

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

    public SendMessageTask piece(int index, int begin, int length, TorrentContext context) throws IOException {
        File target = context.getTarget();
        byte[] piece = Systems.piece(target, index, begin, length);
        return new SendMessageTask(ctx, Messages.piece(index, begin, piece));
    }
}
