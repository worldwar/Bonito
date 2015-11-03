package com.worldwar.backend.processor;

import com.worldwar.backend.Bits;
import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.PeerMessage;
import com.worldwar.backend.ProcessResult;
import com.worldwar.backend.task.PieceTask;
import com.worldwar.backend.task.TaskScheduler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class RequestProcessor extends Processor {
    RequestProcessor(ConnectionStatus status) {
        super(status);
    }

    @Override
    public ProcessResult process(PeerMessage message, ChannelHandlerContext ctx, ByteBuf in) {
        byte[] content = message.getContent();
        int[] ints = Bits.ints(content);
        TaskScheduler.getInstance().emit(new PieceTask(ints[0], ints[1], ints[2], status.getHashInfo(), ctx));
        System.out.println("request: index - " + ints[0] + ", begin - " + ints[1] + ", length - " + ints[2]);
        return ProcessResult.IGNORE;
    }
}
