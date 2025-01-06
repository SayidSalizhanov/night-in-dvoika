package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.RandomSingleton;

import java.util.Random;

public class Skeleton extends AttackEntity {
    private final Random random = RandomSingleton.getInstance();

    public Skeleton() {
        super(new int[]{1,5,7,14,13,15}, 20, true, false, true, true);
    }

    public void moveCooldown() {
        stop(random.nextInt(10, 30));
    }
}
