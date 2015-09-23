package com.worldwar.application;

import com.worldwar.backend.*;
import io.netty.channel.ChannelFuture;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("peer id: " + new String(Messages.PEER_ID));
        ChannelFuture future = new Listener().listen(9999);
        TorrentContext context = TorrentContexts.make();
        context.setTargetPath("tmp/target.txt");
        context.setHashinfo(Messages.FAKE_HASH_INFO);
        context.setTargetSize(1024 * 7);
        context.setPieceLength(8);
        context.start();
        future.channel().closeFuture().sync();
    }
}
