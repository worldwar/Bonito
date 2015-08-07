package com.worldwar.backend;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class Channels {
    public static void write(ChannelHandlerContext ctx, byte[] value) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(value);
        ctx.writeAndFlush(byteBuf);
    }
}
