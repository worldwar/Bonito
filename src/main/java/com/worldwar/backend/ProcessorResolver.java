package com.worldwar.backend;

import java.util.Arrays;
import java.util.Map;

import com.worldwar.backend.processor.HandshakeProcessor;
import com.worldwar.backend.processor.KeepAliveProcessor;
import com.worldwar.backend.processor.Processor;

public class ProcessorResolver {
    private HandshakeProcessor handshakeProcessor;
    private KeepAliveProcessor keepAliveProcessor;
    private Map<Byte[], Processor> processors;

    public ProcessorResolver(HandshakeProcessor handshakeProcessor, KeepAliveProcessor keepAliveProcessor, Map<Byte[], Processor> processors) {
        this.handshakeProcessor = handshakeProcessor;
        this.keepAliveProcessor = keepAliveProcessor;
        this.processors = processors;
    }

    public Processor resolve(PeerMessage message) {
        if (message.getLength() == 0) {
            return keepAliveProcessor;
        } else if (Arrays.equals(message.getId(), Messages.PROTOCOL_STRING.getBytes())) {
            return handshakeProcessor;
        } else {
            return processors.get(message.getId());
        }
    }
}
