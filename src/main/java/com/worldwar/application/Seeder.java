package com.worldwar.application;

import com.worldwar.backend.*;
import com.worldwar.backend.task.InitRequestTask;
import com.worldwar.backend.task.TaskScheduler;
import com.worldwar.utility.Rosters;
import io.netty.channel.ChannelFuture;

import java.io.IOException;

public class Seeder {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("peer id: " + new String(Messages.PEER_ID));
        ChannelFuture future = new Listener().listen(9999);
        Roster roster = Rosters.from("tmp/seeder.bon");
        for (RosterItem item : roster.getTorrents()) {
            TorrentContext context = TorrentContexts.from(item);
            TorrentRegister.register(context);
        }
        TaskScheduler.getInstance().emit(new InitRequestTask());
        future.channel().closeFuture().sync();
    }
}
