package com.worldwar.backend;

import java.util.Random;

public class Randoms {
    private static Random random = new Random();
    public static String digital(int length) {
        StringBuilder result = new StringBuilder(10);
        for (int i = 0; i < length; i++) {
            int value = random.nextInt(10);
            result.append(value);
        }
        return result.toString();
    }
}
