package com.worldwar.utility;

public class Numbers {
    public static int times(int x, int y) {
        return x / y + (x % y == 0 ? 0 : 1);
    }

    public static long times(long x, long y) {
        return x / y + (x % y == 0 ? 0 : 1);
    }

    public static int min(int x, long y) {
        return x < y ? x : (int)y;
    }
}
