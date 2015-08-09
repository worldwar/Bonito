package com.worldwar.backend;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BitsTest {

    @Test
    public void shouldSetHighBitOfByte() {
        byte[] b = new byte[]{0b00000001};
        Bits.set(b, 0);
        byte expect = (byte)0b10000001;
        assertThat(b[0], is(expect));
    }

    @Test
    public void shouldSetLowBitOfByte() {
        byte[] b = new byte[]{0b00100000};
        Bits.set(b, 7);
        byte expect = (byte)0b00100001;
        assertThat(b[0], is(expect));
    }

    @Test
    public void shouldSetMiddleBitOfByte() {
        byte[] b = new byte[]{0b00100001};
        Bits.set(b, 5);
        byte expect = (byte)0b00100101;
        assertThat(b[0], is(expect));
    }

    @Test
    public void shouldClearHighBitOfByte() {
        byte[] b = new byte[]{(byte)0b10000001};
        Bits.clear(b, 0);
        byte expect = (byte)0b00000001;
        assertThat(b[0], is(expect));
    }

    @Test
    public void shouldClearLowBitOfByte() {
        byte[] b = new byte[]{(byte)0b10000001};
        Bits.clear(b, 7);
        byte expect = (byte)0b10000000;
        assertThat(b[0], is(expect));
    }

    @Test
    public void shouldClearMiddleBitOfByte() {
        byte[] b = new byte[]{(byte)0b11000001};
        Bits.clear(b, 1);
        byte expect = (byte)0b10000001;
        assertThat(b[0], is(expect));
    }

    @Test
    public void shouldSetBitOfMultipleBytes() {
        byte[] b = new byte[]{0b00000001, (byte)0b01000011};
        Bits.set(b, 8);
        byte expect = (byte)0b11000011;
        assertThat(b[1], is(expect));
    }

    @Test
    public void shouldClearBitOfMultipleBytes() {
        byte[] b = new byte[]{0b00000001, (byte)0b01000011};
        Bits.clear(b, 15);
        byte expect = (byte)0b01000010;
        assertThat(b[1], is(expect));
    }

    @Test
    public void shouldTestHighBitOfByte() {
        byte[] b = new byte[]{0b00000001};
        assertThat(Bits.test(b, 0), is(false));
    }

    @Test
    public void shouldTestLowBitOfByte() {
        byte[] b = new byte[]{0b00100001};
        assertThat(Bits.test(b, 7), is(true));
    }

    @Test
    public void shouldTestMiddleBitOfByte() {
        byte[] b = new byte[]{0b00100001};
        assertThat(Bits.test(b, 5), is(false));
    }

    @Test
    public void shouldTestBitOfMultipleBytes() {
        byte[] b = new byte[]{0b00000001, (byte)0b01000011};
        assertThat(Bits.test(b, 15), is(true));
    }
}