package com.worldwar.backend;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Listener {
    public ChannelFuture listen() throws InterruptedException {
        ServerBootstrap server = new ServerBootstrap()
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(HandlerFactory.server());
                }
            })
            .group(new NioEventLoopGroup(), new NioEventLoopGroup())
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.SO_BACKLOG, 128);
            return server.bind(9999).sync();
    }
}
