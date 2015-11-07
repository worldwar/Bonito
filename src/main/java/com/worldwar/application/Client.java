package com.worldwar.application;

import com.worldwar.Metainfo;
import com.worldwar.Metainfos;
import com.worldwar.backend.*;
import com.worldwar.backend.task.InitRequestTask;
import com.worldwar.backend.task.TaskScheduler;
import io.netty.channel.ChannelFuture;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("peer id: " + new String(Messages.PEER_ID));
        ChannelFuture future = new Listener().listen(8888);
        String target = "tmp/elephant.jpg";
        String copiedTarget = "tmp/elephant.copied.jpg";
        Metainfo metainfo = Metainfos.generateMetainfo(target);
        TorrentContext context = TorrentContexts.make(metainfo, copiedTarget);
        TorrentRegister.register(context);
        TaskScheduler.getInstance().emit(new InitRequestTask());
        future.channel().closeFuture().sync();
    }
}
