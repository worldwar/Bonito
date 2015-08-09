package com.worldwar.backend.processor;

import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.ProcessResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NotInterestedProcessor extends Processor{
    public NotInterestedProcessor(ConnectionStatus status) {
        super(status);
    }

    @Override
    public ProcessResult process(ChannelHandlerContext ctx, ByteBuf in) {
        status.setPeerInterested(false);
        return ProcessResult.IGNORE;
    }
}
