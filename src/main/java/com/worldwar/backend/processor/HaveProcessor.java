package com.worldwar.backend.processor;

import com.google.common.primitives.Ints;
import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.PeerMessage;
import com.worldwar.backend.ProcessResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HaveProcessor extends Processor {
    public HaveProcessor(ConnectionStatus status) {
        super(status);
    }

    @Override
    public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        int index = Ints.fromByteArray(message.getContent());
        if (index < 0 || index >= status.getPieceCount()) {
            return ProcessResult.DROP_CONNECTION;
        } else {
            status.setPeerHave(index);
        }
        return ProcessResult.IGNORE;
    }
}
