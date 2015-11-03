package com.worldwar.backend.processor;

import com.worldwar.backend.PeerMessage;
import com.worldwar.backend.ProcessResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PendingProcessor extends Processor {
    public PendingProcessor() {
        super(null);
    }

    @Override
    public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        return ProcessResult.IGNORE;
    }
}
