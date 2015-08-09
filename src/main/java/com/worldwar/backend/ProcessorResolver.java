package com.worldwar.backend;

import java.util.Map;

import com.worldwar.backend.processor.Processor;

public class ProcessorResolver {
    private Map<MessageType, Processor> processors;

    public ProcessorResolver(Map<MessageType, Processor> processors) {
        this.processors = processors;
    }

    public Processor resolve(PeerMessage message) {
        return processors.get(message.getType());
    }
}
