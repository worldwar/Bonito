package com.worldwar.backend.processor;

import java.util.HashMap;
import java.util.Map;

import com.worldwar.backend.ConnectionStatus;
import com.worldwar.backend.MessageType;

public class Processors {
    public static Map<MessageType, Processor> all(ConnectionStatus status) {
        HashMap<MessageType, Processor> processors = new HashMap<>();
        processors.put(MessageType.HANDSHAKE, new HandshakeProcessor(status));
        processors.put(MessageType.KEEP_ALIVE, new KeepAliveProcessor(status));
        processors.put(MessageType.CHOKE, new ChokeProcessor(status));
        processors.put(MessageType.UNCHOKE, new UnchokeProcessor(status));
        processors.put(MessageType.INTERESTED, new InterestedProcessor(status));
        processors.put(MessageType.NOT_INTERESTED, new NotInterestedProcessor(status));
        processors.put(MessageType.HAVE, new HaveProcessor(status));
        processors.put(MessageType.BITFIELD, new BitFieldProcessor(status));
        processors.put(MessageType.REQUEST, new RequestProcessor(status));
        processors.put(MessageType.PIECE, new PieceProcessor(status));
        processors.put(MessageType.PENDING, new PendingProcessor());
        return processors;
    }
}
