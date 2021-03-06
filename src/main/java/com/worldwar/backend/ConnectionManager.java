package com.worldwar.backend;

import com.worldwar.backend.processor.Processor;
import com.worldwar.backend.processor.Processors;
import com.worldwar.backend.task.KeepAliveTimerTask;
import com.worldwar.backend.task.SendMessageTaskFactory;
import com.worldwar.backend.task.TaskScheduler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class ConnectionManager {
    private ConnectionStatus status;
    private ProcessorResolver resolver;
    private SendMessageTaskFactory sendMessageTaskFactory = null;

    ConnectionManager() {
        status = new ConnectionStatus();
        resolver = new ProcessorResolver(Processors.all(status));
    }

    public void init(ChannelHandlerContext ctx) {
        sendMessageTaskFactory = new SendMessageTaskFactory(ctx);
    }

    public void handle(ChannelHandlerContext ctx, ByteBuf in, PeerMessage message) {
        preProcess(message, ctx);
        process(message, ctx, in);
    }

    private void process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        Processor processor = resolver.resolve(message);
        if (processor == null) {
            dropConnect(ctx);
        } else {
            ProcessResult result = processor.process(message, ctx, in);
            deal(result, ctx);
        }
    }

    private void preProcess(PeerMessage message, ChannelHandlerContext ctx) {
        if (!status.handshakeDone() && message.getType() != MessageType.HANDSHAKE) {
            dropConnect(ctx);
        }
    }

    private void deal(ProcessResult result, ChannelHandlerContext ctx) {
        switch (result.getType()) {
            case HANDSHAKE_DONE:
                startKeepAliveTimer(ctx);
                initTasks();
                break;
            case DROP_CONNECTION:
                dropConnect(ctx);
        }
    }

    private void initTasks() {
        TaskScheduler.getInstance().emit(sendMessageTaskFactory.unchoke());
        TaskScheduler.getInstance().emit(sendMessageTaskFactory.interested());
    }

    private void startKeepAliveTimer(ChannelHandlerContext ctx) {
        TaskScheduler.getInstance().schedule(new KeepAliveTimerTask(ctx), 3 * 60);
    }

    public void dropConnect(ChannelHandlerContext ctx) {
        InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
        byte[] address = socketAddress.getAddress().getAddress();
        String s = new String(address);
        int port = socketAddress.getPort();
        Connector.unregister(new PeerAddress(s, port));
        ctx.channel().close();
    }

    public ConnectionStatus getStatus() {
        return status;
    }
}
