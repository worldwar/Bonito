package com.worldwar.backend.processor;

import java.util.Arrays;

import com.worldwar.backend.Channels;
import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.Messages;
import com.worldwar.backend.PeerMessage;
import com.worldwar.backend.ProcessResult;
import com.worldwar.backend.ProcessResultType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HandshakeProcessor extends Processor {
    public HandshakeProcessor(ConnectionStatus status) {
        super(status);
    }

    public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        ProcessResult result = ProcessResult.IGNORE;
        byte[] reserved = new byte[Messages.RESERVED_LENGTH];
        byte[] hashInfo = new byte[Messages.HASH_INFO_LENGTH];
        byte[] peerId = new byte[Messages.PEER_ID_LENGTH];
        in.readBytes(reserved, 0, Messages.RESERVED_LENGTH);
        in.readBytes(hashInfo, 0, Messages.HASH_INFO_LENGTH);
        if (status.handshakeDone()) {
            in.readBytes(peerId, 0, Messages.PEER_ID_LENGTH);
            if (!Arrays.equals(status.getPeerId(), peerId)) {
                result = ProcessResult.DROP_CONNECTION;
            }
            if (!Arrays.equals(status.getHashInfo(), hashInfo)) {
                result = ProcessResult.DROP_CONNECTION;
            }
        } else {
            status.setHandshakeDone(true);
            status.setHashInfo(hashInfo);
            Channels.write(ctx, Messages.handshake(hashInfo).raw());
            in.readBytes(peerId, 0, Messages.PEER_ID_LENGTH);
            status.setPeerId(peerId);
            return new ProcessResult(ProcessResultType.HANDSHAKE_DONE, null);
        }
        return result;
    }
}
