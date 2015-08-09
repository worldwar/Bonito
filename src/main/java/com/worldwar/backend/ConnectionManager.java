package com.worldwar.backend;

import com.worldwar.backend.processor.Processor;
import com.worldwar.backend.processor.Processors;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ConnectionManager {
    private ConnectionStatus status;
    private ProcessorResolver resolver;
    ConnectionManager() {
        status = new ConnectionStatus();
        resolver = new ProcessorResolver(Processors.all(status));
    }

    public void handle(ChannelHandlerContext ctx, ByteBuf in) {
        PeerMessage message = readMessage(in);
        preProcess(message, ctx);
        process(message, ctx, in);
    }

    private void process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        Processor processor = resolver.resolve(message);
        if (processor == null) {
            dropConnect(ctx);
        } else {
            ProcessResult result = processor.process(message, ctx, in);
            deal(result, ctx);
        }
    }

    private void preProcess(PeerMessage message, ChannelHandlerContext ctx) {
        if (!status.handshakeDone() && message.getType() != MessageType.HANDSHAKE) {
            dropConnect(ctx);
        }
    }

    private PeerMessage readMessage(ByteBuf in) {
        int length = in.readByte();
        if (length == 0) {
            return Messages.keepAlive();
        } else {
            byte[] content = new byte[length];
            in.readBytes(content, 0, length);
            return Messages.message(length, content);
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
