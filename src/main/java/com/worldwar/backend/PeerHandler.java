package com.worldwar.backend;

import java.util.List;

import com.worldwar.backend.task.SendMessageTask;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PeerHandler extends ByteToMessageDecoder {
    public static final int HANDLER_TYPE_SERVER = 1;
    public static final int HANDLER_TYPE_CLIENT = 2;
    private int type;
    private ConnectionManager manager = new ConnectionManager();

    public PeerHandler(int type) {
        this.type = type;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (type == HANDLER_TYPE_CLIENT) {
            new SendMessageTask(ctx, Messages.handshake(Messages.FAKE_HASH_INFO)).call();
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        manager.handle(ctx, in);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        int x = 0;
        System.out.println(x);
        manager.dropConnect(ctx);
    }

    @Override
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    }

    public ConnectionManager getConnectionManager() {
        return manager;
    }
}
