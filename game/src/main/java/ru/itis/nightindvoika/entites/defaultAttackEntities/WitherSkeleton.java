package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.RandomSingleton;

import java.util.Random;

public class WitherSkeleton extends AttackEntity {
    private final Random random = RandomSingleton.getInstance();

    private final int[] secondPath = {2,4,7,9,13,15};
    private boolean chosenSecondPath;

    public WitherSkeleton() {
        super(new int[]{2,4,7,10,14,15}, 30, true, false, true, true);
        chosenSecondPath = false;
    }

    public void moveForward() {
        if (currentPosition < endPosition) {
            if (currentPosition == 7) {
                chosenSecondPath = random.nextBoolean();
            }
            currentPathIndex++;
            currentPosition = chosenSecondPath ? secondPath[currentPosition] : path[currentPathIndex];
            moveCooldown();
        }
    }

    public void moveBack() {
        if (currentPosition > startPosition) {
            currentPathIndex--;
            currentPosition = chosenSecondPath ? secondPath[currentPosition] : path[currentPathIndex];
            moveCooldown();
        }
    }
}
