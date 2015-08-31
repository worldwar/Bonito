package com.worldwar.backend.task;

import java.util.TimerTask;

import com.worldwar.backend.Messages;
import io.netty.channel.ChannelHandlerContext;

public class KeepAliveTimerTask extends TimerTask {
    private ChannelHandlerContext ctx;

    public KeepAliveTimerTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        TaskScheduler.getInstance().emit(new SendMessageTask(ctx, Messages.keepAlive()));
    }
}
