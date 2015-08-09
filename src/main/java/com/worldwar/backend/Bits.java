package com.worldwar.backend;

public class Bits {
    public static void set(byte[] value, int index) {
        //index = 0 refers to the high bit of first byte.
        assert index >= 0 && index < value.length * 8;
        int bitIndex = 7 - index % 8;
        int byteIndex = index / 8;
        byte b = value[byteIndex];
        value[byteIndex] = (byte) (b | (1 << bitIndex));
    }

    public static void clear(byte[] value, int index) {
        //index = 0 refers to the high bit of first byte.
        assert index >= 0 && index < value.length * 8;
        int bitIndex = 7 - index % 8;
        int byteIndex = index / 8;
        byte b = value[byteIndex];
        value[byteIndex] = (byte) (b & ~(1 << bitIndex));
    }

    public static boolean test(byte[] value, int index) {
        //index = 0 refers to the high bit of first byte.
        assert index >= 0 && index < value.length * 8;
        int bitIndex = 7 - index % 8;
        int byteIndex = index / 8;
        byte b = value[byteIndex];
        return (byte) (b & (1 << bitIndex)) != 0;
    }
}
