package ru.itis.nightindvoika.util;

import java.io.Serializable;
import java.util.Random;

public class RandomSingleton implements Serializable {
    private static final Random random = new Random();

    private RandomSingleton() {
    }

    public static Random getInstance() {
        return random;
    }
}
