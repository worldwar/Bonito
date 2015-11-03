package com.worldwar.backend;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;

public class Connector {
    private static EventLoopGroup group;
    private static Bootstrap bootstrap = null;
    private static ConcurrentHashMap<PeerAddress, Channel> channels;

    public static void init() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        channels = new ConcurrentHashMap<>();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true);
    }

    public static ChannelFuture connect(String address, int port) throws InterruptedException {
        if (bootstrap == null) {
            init();
        }
        ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
        register(new PeerAddress(address, port), channelFuture.channel());
        return channelFuture;
    }

    public static ChannelFuture connect(PeerAddress peerAddress, byte[] hashinfo) throws InterruptedException {
        if (bootstrap == null) {
            init();
        }
        bootstrap.handler(HandlerFactory.client(hashinfo));
        ChannelFuture channelFuture = bootstrap.connect(peerAddress.getAddress(), peerAddress.getPort()).sync();
        register(peerAddress, channelFuture.channel());
        return channelFuture;
    }

    public static void shutdownGracefully() {
        group.shutdownGracefully();
        bootstrap = null;
    }

    private static void register(PeerAddress peerAddress, Channel channel) {
        channels.put(peerAddress, channel);
    }

    public static void unregister(PeerAddress peerAddress) {
        channels.remove(peerAddress);
    }

    public static Channel get(PeerAddress peerAddress) {
        if (bootstrap == null) {
            init();
        }
        return channels.get(peerAddress);
    }

    public static Channel createIfAbsent(PeerAddress peerAddress, byte[] hashinfo) {
        Channel channel = get(peerAddress);
        if (channel == null) {
            try {
                ChannelFuture channelFuture = connect(peerAddress, hashinfo);
                channel = channelFuture.channel();
            } catch (InterruptedException e) {
                return null;
            }
        }
        return channel;
    }
}