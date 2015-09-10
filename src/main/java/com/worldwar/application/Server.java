package com.worldwar.application;

import com.worldwar.backend.Listener;
import io.netty.channel.ChannelFuture;

public class Server {
    public static void main(String[] args) {
        try {
            ChannelFuture future = new Listener().listen(9999);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
