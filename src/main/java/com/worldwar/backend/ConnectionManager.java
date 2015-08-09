package com.worldwar.backend;

import java.util.HashMap;

import com.worldwar.backend.processor.ChokeProcessor;
import com.worldwar.backend.processor.HandshakeProcessor;
import com.worldwar.backend.processor.KeepAliveProcessor;
import com.worldwar.backend.processor.Processor;
import com.worldwar.backend.processor.UnchokeProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ConnectionManager {
    private ConnectionStatus status;
    private ProcessorResolver resolver;
    ConnectionManager() {
        status = new ConnectionStatus();
        HashMap<MessageType, Processor> processors = new HashMap<>();

        processors.put(MessageType.HANDSHAKE, new HandshakeProcessor(status));
        processors.put(MessageType.KEEP_ALIVE, new KeepAliveProcessor(status));
        processors.put(MessageType.CHOKE, new ChokeProcessor(status));
        processors.put(MessageType.UNCHOKE, new UnchokeProcessor(status));

        resolver = new ProcessorResolver(processors);
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
            ProcessResult result = processor.process(ctx, in);
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
