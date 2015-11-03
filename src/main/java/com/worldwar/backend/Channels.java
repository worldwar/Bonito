package com.worldwar.backend;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class Channels {
    public static void write(ChannelHandlerContext ctx, byte[] value) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(value);
        ctx.writeAndFlush(byteBuf);
    }

    public static void write(Channel channel, byte[] value) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(value);
        channel.writeAndFlush(byteBuf);
    }
}
