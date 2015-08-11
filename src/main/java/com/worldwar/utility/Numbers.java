package com.worldwar.utility;

public class Numbers {
    public static int times(int x, int y) {
        return x / y + (x % y == 0 ? 0 : 1);
    }
}
