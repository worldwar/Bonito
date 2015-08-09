package com.worldwar.backend.processor;

import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.PeerMessage;
import com.worldwar.backend.ProcessResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class InterestedProcessor extends Processor{
    public InterestedProcessor(ConnectionStatus status) {
        super(status);
    }

    @Override
    public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        status.setPeerInterested(true);
        return ProcessResult.IGNORE;
    }
}
