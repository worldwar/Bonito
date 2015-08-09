package com.worldwar.backend;

import com.worldwar.backend.processor.ChokeProcessor;
import com.worldwar.backend.processor.HandshakeProcessor;
import com.worldwar.backend.processor.InterestedProcessor;
import com.worldwar.backend.processor.KeepAliveProcessor;
import com.worldwar.backend.processor.Processors;
import com.worldwar.backend.processor.UnchokeProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ProcessorResolverTest {
    private ProcessorResolver resolver;

    @Before
    public void before() {
        resolver = new ProcessorResolver(Processors.all(null));
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

    @Test
    public void shouldResolveInterestedMessage() {
        PeerMessage message = Messages.interested();
        assertThat(resolver.resolve(message), instanceOf(InterestedProcessor.class));
    }
}