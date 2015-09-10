package com.worldwar.application;

import com.worldwar.backend.Connector;
import io.netty.channel.ChannelFuture;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture future = Connector.connect("localhost", 9999);
        future.channel().closeFuture().sync();
    }
}
