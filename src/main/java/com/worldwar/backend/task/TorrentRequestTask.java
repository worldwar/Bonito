package com.worldwar.backend.task;

import com.worldwar.backend.*;
import com.worldwar.utility.Numbers;
import io.netty.channel.Channel;

import java.util.concurrent.Callable;

public class TorrentRequestTask implements Callable<Void>{
    private TorrentContext torrentContext;

    public TorrentRequestTask(TorrentContext context) {
        this.torrentContext = context;
    }

    @Override
    public Void call() throws Exception {
        byte[] bitfield = torrentContext.bitfield();
        long pieceCount = torrentContext.pieceCount();
        PeerAddress peer = FakeTracker.send(torrentContext.hashinfo());
        for (int i = 0; i < pieceCount; i++) {
            if (!Bits.test(bitfield, i)) {
                int pieceLength = Constants.PIECE_LENGTH;
                int blockLength = Constants.BLOCK_LENGTH;
                long targetSize = torrentContext.getTargetSize();
                long remain = targetSize - i * pieceLength;
                int blockCount = Numbers.times(Numbers.min(pieceLength, remain), blockLength);
                for (int j = 0; j < blockCount; j++) {
                    Channel channel = Connector.createIfAbsent(peer, torrentContext.hashinfo());
                    Thread.sleep(500);
                    int begin = j * blockLength;
                    PeerMessage request = Messages.request(i, begin, Numbers.min(blockLength, targetSize - begin));
                    TaskScheduler.getInstance().emit(new SendMessageTask(channel, request));
                }
            }
        }
        return null;
    }
}
