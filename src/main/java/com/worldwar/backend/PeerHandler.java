package com.worldwar.backend;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PeerHandler extends ByteToMessageDecoder {
    private ConnectionManager manager = new ConnectionManager();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        manager.handle(ctx, in);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        manager.dropConnect(ctx);
    }
}
