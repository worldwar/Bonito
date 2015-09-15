package com.worldwar.application;

import com.worldwar.backend.Listener;
import com.worldwar.backend.Messages;
import com.worldwar.backend.TorrentContext;
import com.worldwar.backend.TorrentContexts;
import io.netty.channel.ChannelFuture;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("peer id: " + new String(Messages.PEER_ID));
        ChannelFuture future = new Listener().listen(9999);
        TorrentContext context = TorrentContexts.make();
        context.start();
        future.channel().closeFuture().sync();
    }
}
