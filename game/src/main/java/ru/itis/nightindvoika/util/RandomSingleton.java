package ru.itis.nightindvoika.util;

import java.util.Random;

public class RandomSingleton {
    private static final Random random = new Random();

    private RandomSingleton() {
    }

    public static Random getInstance() {
        return random;
    }
}
