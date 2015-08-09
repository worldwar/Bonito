package com.worldwar.backend;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MessagesTest {

    @Test
    public void keepAliveMessageShouldHaveLengthZero() {
        PeerMessage message = Messages.keepAlive();
        assertThat(message.getLength(), is(0));
    }

    @Test
    public void keepAliveMessageShouldHaveTypeKeepAlive() {
        PeerMessage message = Messages.keepAlive();
        assertThat(message.getType(), is(MessageType.KEEP_ALIVE));
    }

    @Test
    public void handshakeMessageShouldHaveLengthEqualToProtocolString() {
        PeerMessage handshake = Messages.handshake();
        assertThat(handshake.getLength(), is(Messages.PROTOCOL_STRING.length()));
    }

    @Test
    public void handshakeMessageShouldHaveTypeHandshake() {
        PeerMessage handshake = Messages.handshake();
        assertThat(handshake.getType(), is(MessageType.HANDSHAKE));
    }
}