package com.worldwar.backend;

import java.util.Objects;

import com.worldwar.backend.processor.HandshakeProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ConnectionManager {
    private ConnectionStatus status;
    private HandshakeProcessor handshakeHandler;

    ConnectionManager() {
        status = new ConnectionStatus();
        handshakeHandler = new HandshakeProcessor(status);
    }

    public void handle(ChannelHandlerContext ctx, ByteBuf in) {
        int length = in.readByte();
        byte[] dst = new byte[length];
        in.readBytes(dst, 0, length);
        String protocol = new String(dst);
        if (Objects.equals(protocol, Messages.PROTOCOL_STRING)) {
            ProcessResult result = handshakeHandler.process(ctx, in);
            deal(result, ctx);
        } else {
            if (!status.handshakeDone()) {
                dropConnect(ctx);
            }
        }
    }

    private void deal(ProcessResult result, ChannelHandlerContext ctx) {
        switch (result.getType()) {
            case HANDSHAKE_DONE:
                status.setHandshakeDone(true);
                Channels.write(ctx, result.getValue());
            case DROP_CONNECTION:
                dropConnect(ctx);
        }
    }

    public void dropConnect(ChannelHandlerContext ctx) {
        ctx.channel().close();
    }
}
