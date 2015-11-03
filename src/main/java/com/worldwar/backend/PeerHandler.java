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
    private byte[] initialHashinfo;

    public PeerHandler(int type) {
        this.type = type;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        manager.init(ctx);
        if (type == HANDLER_TYPE_CLIENT) {
            new SendMessageTask(ctx, Messages.handshake(initialHashinfo)).call();
            manager.getStatus().setHandshakeSend(true);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        manager.handle(ctx, in);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("error: " + cause.getMessage());
        manager.dropConnect(ctx);
    }

    @Override
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    }

    public ConnectionManager getConnectionManager() {
        return manager;
    }

    public void setInitialHashinfo(byte[] initialHashinfo) {
        this.initialHashinfo = initialHashinfo;
    }
}
