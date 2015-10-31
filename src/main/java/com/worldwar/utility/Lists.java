package com.worldwar.utility;


import java.util.List;

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

    public static byte[] concat(List<byte[]> list) {
        byte[][] objects = new byte[list.size()][];
        int i = 0;
        for (byte[] bytes : list) {
            objects[i++] = bytes;
        }
        return concat(objects);
    }

}
