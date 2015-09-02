package com.worldwar.utility;

public class Lists {
    public static byte[] concat(byte[]... byteArrays) {
        if (byteArrays == null) {
            return new byte[]{};
        }
        int length = 0;
        for (byte[] b : byteArrays) {
            if (b != null) {
                length += b.length;
            }
        }
        byte[] c = new byte[length];
        int index = 0;
        for (byte[] b : byteArrays) {
            if (b != null) {
                System.arraycopy(b, 0, c, index, b.length);
                index += b.length;
            }
        }
        return c;
    }
}
