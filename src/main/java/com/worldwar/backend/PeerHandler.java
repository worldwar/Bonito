package com.worldwar.backend;

import java.util.List;

import com.worldwar.backend.task.SendMessageTask;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PeerHandler extends ByteToMessageDecoder {
    public static final int HANDLER_TYPE_SERVER = 1;
    public static final int HANDLER_TYPE_CLIENT = 2;
    private static final int PEER_MESSAGE_LENGTH_FIELD_BYTE_COUNT = 4;
    private static final int HANDSHAKE_LENGTH_FIELD_BYTE_COUNT = 1;

    private boolean handshakeDone = false;
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
        PeerMessage message;
        if (!handshakeDone) {
            message = decodeHandshake(in, out);
        } else {
            message = decodePeerMessage(in, out);

        }
        if (message != null) {
            manager.handle(ctx, in, message);
        }
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

    private PeerMessage decodePeerMessage(ByteBuf in, List<Object> out) {
        if (in.readableBytes() < PEER_MESSAGE_LENGTH_FIELD_BYTE_COUNT)
            return null;

        byte[] lengthBytes = new byte[PEER_MESSAGE_LENGTH_FIELD_BYTE_COUNT];
        in.markReaderIndex().readBytes(lengthBytes, 0, PEER_MESSAGE_LENGTH_FIELD_BYTE_COUNT);
        int length = Bits.toInt(lengthBytes);
        if (in.readableBytes() >= length) {
            byte[] content = new byte[length];
            in.readBytes(content, 0, length);
            PeerMessage message = Messages.message(length, content);
            out.add(message);
            return message;
        } else {
            in.resetReaderIndex();
        }
        return null;
    }

    private PeerMessage decodeHandshake(ByteBuf in, List<Object> out) {
        if (in.readableBytes() <= HANDSHAKE_LENGTH_FIELD_BYTE_COUNT)
            return null;
        int length = in.markReaderIndex().readByte();
        int actualLength = length + 48;
        if (in.readableBytes() >= actualLength) {
            byte[] content = new byte[actualLength];
            in.readBytes(content, 0, actualLength);
            PeerMessage message = new HandshakeMessage(content);
            out.add(message);
            handshakeDone = true;
            return message;
        } else {
            in.resetReaderIndex();
        }
        return null;
    }
}
