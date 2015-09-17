package com.worldwar.backend;

import com.worldwar.backend.processor.*;
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

    @Test
    public void shouldResolveNotInterestedMessage() {
        PeerMessage message = Messages.notInterested();
        assertThat(resolver.resolve(message), instanceOf(NotInterestedProcessor.class));
    }

    @Test
    public void shouldResolveHaveMessage() {
        PeerMessage message = Messages.have(0);
        assertThat(resolver.resolve(message), instanceOf(HaveProcessor.class));
    }

    @Test
    public void shouldResolveBitFieldMessage() {
        byte[] bytes = new byte[10];
        PeerMessage message = Messages.bitField(bytes);
        assertThat(resolver.resolve(message), instanceOf(BitFieldProcessor.class));
    }

    @Test
    public void shouldResolveRequestMessage() {
        PeerMessage message = Messages.request(1, 2, 3);
        assertThat(resolver.resolve(message), instanceOf(RequestProcessor.class));
    }
}