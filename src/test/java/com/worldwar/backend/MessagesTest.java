package com.worldwar.backend;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

    @Test
    public void haveMessageShouldHaveLengthFive() {
        int index = 9;
        PeerMessage have = Messages.have(index);
        assertThat(have.getLength(), is(5));
    }

    @Test
    public void notInterestedMessageShouldHaveTypeHave() {
        int index = 9;
        PeerMessage notInterested = Messages.have(index);
        assertThat(notInterested.getType(), is(MessageType.HAVE));
    }

    @Test
    public void shouldGetRightTypeOfHaveMessage() {
        MessageType type = Messages.type(1, new byte[] {4});
        assertThat(type, is(MessageType.HAVE));
    }

    @Test
    public void haveMessageShouldHaveRightContent() {
        int index = 9;
        PeerMessage have = Messages.have(index);
        int actual = Ints.fromByteArray(have.getContent());
        assertThat(actual, is(index));
    }

    @Test
    public void bitFieldMessageShouldHaveRightType() {
        byte[] bytes = new byte[10];
        PeerMessage bitField = Messages.bitField(bytes);
        assertThat(bitField.getType(), is(MessageType.BITFIELD));
    }

    @Test
    public void shouldGetRightTypeOfRequestMessage() {
        MessageType type = Messages.type(13, new byte[] {6});
        assertThat(type, is(MessageType.REQUEST));
    }

    @Test
    public void requestMessageShouldHaveRightContent() {
        int index = 14;
        int begin = 300;
        int length = 1024 * 8;
        PeerMessage request = Messages.request(index, begin, length);
        byte[] content = request.getContent();
        byte[] indexBytes = Arrays.copyOfRange(content, 0, 4);
        byte[] beginBytes = Arrays.copyOfRange(content, 4, 8);
        byte[] lengthBytes = Arrays.copyOfRange(content, 8, 12);

        assertThat(Ints.fromByteArray(indexBytes), is(index));
        assertThat(Ints.fromByteArray(beginBytes), is(begin));
        assertThat(Ints.fromByteArray(lengthBytes), is(length));
    }

    @Test
    public void shouldGetRightTypeOfPieceMessage() {
        int contentLength = 1024 * 16;
        MessageType type = Messages.type(9 + contentLength, new byte[]{7});
        assertThat(type, is(MessageType.PIECE));
    }


    @Test
    public void pieceMessageShouldHaveRightContent() {
        int index = 14;
        int begin = 300;
        int length = 1024 * 8;
        byte[] block = new byte[length];
        PeerMessage request = Messages.piece(index, begin, block);

        assertThat(Messages.indexOfPieceMessage(request), is(index));
        assertThat(Messages.beginOfPieceMessage(request), is(begin));
        assertThat(Arrays.equals(block, Messages.blockOfPieceMessage(request)), is(true));
    }
}