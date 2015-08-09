package com.worldwar.backend.processor;

import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.PeerMessage;
import com.worldwar.backend.ProcessResult;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class Processor {
    ConnectionStatus status;
    Processor(ConnectionStatus status) {
        this.status = status;
    }
    abstract public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in);
}
