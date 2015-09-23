package com.worldwar.backend.processor;

import com.worldwar.backend.*;
import com.worldwar.utility.Systems;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.IOException;

public class PieceProcessor extends Processor {
    PieceProcessor(ConnectionStatus status) {
        super(status);
    }

    @Override
    public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        TorrentContext torrentContext = TorrentRegister.get(status.getHashInfo());
        if (torrentContext == null) {
            return ProcessResult.DROP_CONNECTION;
        }
        File target = torrentContext.getTarget();
        int index = Messages.indexOfPieceMessage(message);
        int begin = Messages.beginOfPieceMessage(message);
        byte[] block = Messages.blockOfPieceMessage(message);
        int pieceLength = torrentContext.getPieceLength();
        long offset = index * pieceLength + begin;
        try {
            Systems.write(target, offset, block);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ProcessResult.IGNORE;
    }
}
