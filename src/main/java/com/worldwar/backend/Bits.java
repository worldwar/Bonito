package com.worldwar.backend;

import com.google.common.primitives.Ints;
import com.worldwar.utility.Numbers;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.Predicate;

public class Bits {
    public static void set(byte[] value, int index) {
        //index = 0 refers to the high bit of first byte.
        assert index >= 0 && index < value.length * 8;
        int bitIndex = 7 - index % 8;
        int byteIndex = index / 8;
        byte b = value[byteIndex];
        value[byteIndex] = (byte) (b | (1 << bitIndex));
    }

    public static void set(byte[] value, int start, int count) {
        for (int index = start; index < start + count; index++) {
            set(value, index);
        }
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

    /***
     * test a range of bit in the value satisfy the predicate.
     * the range is indicated by two indexes: start(included) and end(excluded).
     * index 0 indicates the high bit of first byte of value,
     * index value.length * 8 - 1 indicates the low bit of last byte of value.
     *
     * the value of the certain bit will converted to a boolean as the argument of the predicate
     * 0 is converted to false, and 1 is converted to true.
     *
     * @param value bytes to be tested
     * @param start index of first bit to be tested in value
     * @param end index of the bit next to the last bit tested in value
     * @param predicate function to test on a certain bit
     * @return true if all bits of value in the range satisfy the predicate, false if at last one
     * bits of value in the range don't satisfy the predicate
     */
    public static boolean every(byte[] value, int start, int end, Predicate<Boolean> predicate) {
        for (int i = start; i < end; i++) {
            if (!predicate.test(Bits.test(value, i))) {
                return false;
            }
        }
        return true;
    }

    public static int count(byte[] value, int start, int end, Predicate<Boolean> predicate) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            if (predicate.test(Bits.test(value, i))) {
                 sum++;
            }
        }
        return sum;
    }

    public static int[] ints(byte[] bytes) {
        int size = bytes.length;
        int range = 4;
        int count = Numbers.times(size, range);
        int[] result = new int[count];

        for (int i = 0; i < count; i++) {
            int to = ((i + 1) * range) > size ? size : (i + 1) * range;
            result[i] = intValue(bytes, i * range, to);
        }
        return result;
    }

    public static int intValue(byte[] bytes, int from, int to) {
        return Ints.fromByteArray(Arrays.copyOfRange(bytes, from, to));
    }

    public static Predicate<Boolean> identity() {
        return b -> b;
    }

    public static Predicate<Boolean> not() {
        return b -> !b;
    }

    public static byte[] bytes(int number) {
        return ByteBuffer.allocate(4).putInt(number).array();
    }

    public static int toInt(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return wrap.getInt();
    }

    public static int numberOfSetBits(byte[] bytes){
        return count(bytes, 0, bytes.length * 8, identity());
    }
}
