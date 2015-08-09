package com.worldwar.backend;

import java.util.HashMap;
import java.util.Map;

import com.worldwar.backend.processor.ChokeProcessor;
import com.worldwar.backend.processor.HandshakeProcessor;
import com.worldwar.backend.processor.KeepAliveProcessor;
import com.worldwar.backend.processor.Processor;
import com.worldwar.backend.processor.UnchokeProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class ProcessorResolverTest {
    private ProcessorResolver resolver;

    @Before
    public void before() {
        Map<MessageType, Processor> processors = new HashMap<>();
        processors.put(MessageType.HANDSHAKE, new HandshakeProcessor(null));
        processors.put(MessageType.KEEP_ALIVE, new KeepAliveProcessor(null));
        processors.put(MessageType.CHOKE, new ChokeProcessor(null));
        processors.put(MessageType.UNCHOKE, new UnchokeProcessor(null));
        resolver = new ProcessorResolver(processors);
    }

    @Test
    public void shouldResolveHandshakeMessage() {
        PeerMessage message = Messages.handshake();
        assertThat(resolver.resolve(message), instanceOf(HandshakeProcessor.class));
    }

    @Test
    public void shouldResolveKeepAliveMessage() {
        PeerMessage message = Messages.keepAlive();
        assertThat(resolver.resolve(message), instanceOf(KeepAliveProcessor.class));
    }

    @Test
    public void shouldResolveChokeMessage() {
        PeerMessage message = Messages.choke();
        assertThat(resolver.resolve(message), instanceOf(ChokeProcessor.class));
    }

    @Test
    public void shouldResolveUnchokeMessage() {
        PeerMessage message = Messages.unchoke();
        assertThat(resolver.resolve(message), instanceOf(UnchokeProcessor.class));
    }
}