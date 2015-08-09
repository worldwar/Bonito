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

    @Test
    public void chokeMessageShouldHaveLengthOne() {
        PeerMessage choke = Messages.choke();
        assertThat(choke.getLength(), is(1));
    }

    @Test
    public void chokeMessageShouldHaveTypeChoke() {
        PeerMessage choke = Messages.choke();
        assertThat(choke.getType(), is(MessageType.CHOKE));
    }

    @Test
    public void unchokeMessageShouldHaveLengthOne() {
        PeerMessage unchoke = Messages.unchoke();
        assertThat(unchoke.getLength(), is(1));
    }

    @Test
    public void unchokeMessageShouldHaveTypeUnchoke() {
        PeerMessage unchoke = Messages.unchoke();
        assertThat(unchoke.getType(), is(MessageType.UNCHOKE));
    }

    @Test
    public void shouldGetRightTypeOfUnchokeMessage() {
        MessageType type = Messages.type(1, new byte[] {1});
        assertThat(type, is(MessageType.UNCHOKE));
    }

    @Test
    public void interestedMessageShouldHaveLenghtOne() {
        PeerMessage interested = Messages.interested();
        assertThat(interested.getLength(), is(1));
    }

    @Test
    public void interestedMessageShouldHaveTypeInterested() {
        PeerMessage interested = Messages.interested();
        assertThat(interested.getType(), is(MessageType.INTERESTED));
    }

    @Test
    public void shouldGetRightTypeOfInterestedMessage() {
        MessageType type = Messages.type(1, new byte[] {2});
        assertThat(type, is(MessageType.INTERESTED));
    }

    @Test
    public void notInterestedMessageShouldHaveLenghtOne() {
        PeerMessage notInterested = Messages.notInterested();
        assertThat(notInterested.getLength(), is(1));
    }

    @Test
    public void notInterestedMessageShouldHaveTypeNotInterested() {
        PeerMessage notInterested = Messages.notInterested();
        assertThat(notInterested.getType(), is(MessageType.NOT_INTERESTED));
    }

    @Test
    public void shouldGetRightTypeOfNotInterestedMessage() {
        MessageType type = Messages.type(1, new byte[] {3});
        assertThat(type, is(MessageType.NOT_INTERESTED));
    }
}