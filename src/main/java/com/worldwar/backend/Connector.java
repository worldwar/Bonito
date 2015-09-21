package com.worldwar.backend;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Connector {
    private static EventLoopGroup group;
    private static Bootstrap bootstrap = null;

    public static void init() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(HandlerFactory.client());
                }
            });
    }

    public static ChannelFuture connect(String address, int port) throws InterruptedException {
        if (bootstrap == null) {
            init();
        }
        return bootstrap.connect(address, port).sync();
    }

    public static void shutdownGracefully() {
        group.shutdownGracefully();
        bootstrap = null;
    }
}