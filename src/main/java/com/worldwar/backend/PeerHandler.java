package com.worldwar.backend;

import java.util.List;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PeerHandler extends ByteToMessageDecoder {
    private boolean handshakeDone = false;
    private HandshakeProcessor handshakeHandler = new HandshakeProcessor();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readByte();
        byte[] dst = new byte[length];
        in.readBytes(dst, 0, length);
        String protocol = new String(dst);
        if (Objects.equals(protocol, Messages.PROTOCOL_STRING)) {
            ProcessResult result = handshakeHandler.process(ctx, in);
            deal(result, ctx);
        } else {
            if (!handshakeDone) {
                dropConnect();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    private void dropConnect() {

    }

    private void deal(ProcessResult result, ChannelHandlerContext ctx) {
        switch (result.getType()) {
            case HANDSHAKE_DONE:
                handshakeDone = true;
                Channels.write(ctx, result.getValue());
            case DROP_CONNECTION:
                dropConnect();
        }
    }
}
