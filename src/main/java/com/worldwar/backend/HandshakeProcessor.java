package com.worldwar.backend;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class HandshakeProcessor {
    private boolean done = false;
    private byte[] peer_id = null;
    private byte[] hash_info = null;
    public ProcessResult process(ChannelHandlerContext ctx, ByteBuf in) {
        ProcessResult result = ProcessResult.IGNORE;
        byte[] reserved = new byte[Messages.RESERVED_LENGTH];
        byte[] hash_info = new byte[Messages.HASH_INFO_LENGTH];
        byte[] peer_id = new byte[Messages.PEER_ID_LENGTH];
        in.readBytes(reserved, 0, Messages.RESERVED_LENGTH);
        in.readBytes(hash_info, 0, Messages.HASH_INFO_LENGTH);
        if (done) {
            in.readBytes(peer_id, 0, Messages.PEER_ID_LENGTH);
            if (!Arrays.equals(this.peer_id, peer_id)) {
                result = ProcessResult.DROP_CONNECTION;
            }
            if (!Arrays.equals(this.hash_info, hash_info)) {
                result = ProcessResult.DROP_CONNECTION;
            }
        } else {
            done = true;
            this.hash_info = hash_info;
            Channels.write(ctx, Messages.handshake(hash_info));
            in.readBytes(peer_id, 0, Messages.PEER_ID_LENGTH);
            this.peer_id = peer_id;
        }
        return result;
    }
}
