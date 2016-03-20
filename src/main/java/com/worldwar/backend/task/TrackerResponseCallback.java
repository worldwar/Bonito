package com.worldwar.backend.task;

import com.worldwar.backend.*;
import com.worldwar.utility.Numbers;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;

public class TrackerResponseCallback {
    private TorrentContext context;

    public TrackerResponseCallback(TorrentContext context) {
        this.context = context;
    }

    public Void callback(TrackerResponse response) throws Exception {
        byte[] bitfield = context.bitfield();
        long pieceCount = context.pieceCount();
        List<Map<String, Object>> peers = response.getPeers();
        if (peers == null || peers.size() == 0) {
            return null;
        }
        String ip = (String)peers.get(0).get("ip");
        int port = Numbers.number(peers.get(0).get("port"));
        PeerAddress peer = new PeerAddress(ip, port);
        for (int i = 0; i < pieceCount; i++) {
            if (!Bits.test(bitfield, i)) {
                int pieceLength = Constants.PIECE_LENGTH;
                int blockLength = Constants.BLOCK_LENGTH;
                long targetSize = context.getTargetSize();
                long remain = targetSize - i * pieceLength;
                int blockCount = Numbers.times(Numbers.min(pieceLength, remain), blockLength);
                for (int j = 0; j < blockCount; j++) {
                    Channel channel = Connector.createIfAbsent(peer, context.hashinfo());
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
