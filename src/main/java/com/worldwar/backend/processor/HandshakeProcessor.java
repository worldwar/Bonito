package com.worldwar.backend.processor;

import java.util.Arrays;

import com.worldwar.backend.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HandshakeProcessor extends Processor {
    public HandshakeProcessor(ConnectionStatus status) {
        super(status);
    }

    public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        ProcessResult result = ProcessResult.IGNORE;
        byte[] peerId = ((HandshakeMessage)message).peerid();
        byte[] hashInfo = ((HandshakeMessage)message).hashinfo();
        if (status.handshakeDone()) {
            if (!Arrays.equals(status.getPeerId(), peerId)) {
                result = ProcessResult.DROP_CONNECTION;
            }
            if (!Arrays.equals(status.getHashInfo(), hashInfo)) {
                result = ProcessResult.DROP_CONNECTION;
            }
        } else {
            status.setHandshakeDone(true);
            status.setHashInfo(hashInfo);
            status.setPeerId(peerId);
            if (!status.handshakeSend()) {
                byte[] raw = Messages.handshake(hashInfo).raw();
                Channels.write(ctx, raw);
                System.out.println("HANDSHAKE in loop: " + Arrays.toString(raw));
                status.setPeerId(peerId);
                return new ProcessResult(ProcessResultType.HANDSHAKE_DONE, null);
            }
        }
        System.out.println("handshake - remote peer id: " + new String(peerId));
        return result;
    }
}
