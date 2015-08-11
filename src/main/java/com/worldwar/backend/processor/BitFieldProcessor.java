package com.worldwar.backend.processor;

import com.worldwar.backend.Bits;
import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.PeerMessage;
import com.worldwar.backend.ProcessResult;
import com.worldwar.utility.Numbers;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class BitFieldProcessor extends Processor {
    public BitFieldProcessor(ConnectionStatus status) {
        super(status);
    }

    @Override
    public
    ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        if (Numbers.times(status.getPieceCount(), 8) != message.getContent().length) {
            return ProcessResult.DROP_CONNECTION;
        }

        if (!Bits.every(message.getContent(), status.getPieceCount(), message.getContent().length * 8, (b) -> !b)) {
            return ProcessResult.DROP_CONNECTION;
        }
        status.setPeerBitField(message.getContent());
        return ProcessResult.IGNORE;
    }
}
