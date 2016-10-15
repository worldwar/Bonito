package com.worldwar.application;

import com.worldwar.backend.*;
import com.worldwar.utility.Rosters;
import io.netty.channel.ChannelFuture;

import java.io.IOException;

public class Seeder {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("peer id: " + new String(Messages.PEER_ID));
        ChannelFuture future = new Listener().listen(9999);
        Roster roster = Rosters.from("seeder.json");
        roster.register();
        future.channel().closeFuture().sync();
    }
}
