package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.PositionOnFrame;
import ru.itis.nightindvoika.util.RandomSingleton;

import java.util.HashMap;
import java.util.Random;

public class WitherSkeleton extends AttackEntity {
    private final Random random = RandomSingleton.getInstance();

    private final int[] secondPath = {2,4,7,9,13,15};
    private boolean chosenSecondPath;

    public WitherSkeleton() {
        super(new int[]{2,4,7,10,14,15}, 30, true, false, true, true);
        chosenSecondPath = false;

        positionsOnFrames = new HashMap<>();
        positionsOnFrames.put(2, PositionOnFrame.create(346, 613));
        positionsOnFrames.put(4, PositionOnFrame.create(375, 352));
        positionsOnFrames.put(7, PositionOnFrame.create(244, 314));
        positionsOnFrames.put(10, PositionOnFrame.create(280, 477));
        positionsOnFrames.put(14, PositionOnFrame.create(847, 213));
        positionsOnFrames.put(9, PositionOnFrame.create(139, 477));
        positionsOnFrames.put(13, PositionOnFrame.create(648, 301));
        positionsOnFrames.put(15, PositionOnFrame.create(668, 124));
    }

    public void moveForward() {
        if (currentPosition < endPosition) {
            if (currentPosition == 7) {
                chosenSecondPath = random.nextBoolean();
            }
            currentPathIndex++;
            currentPosition = chosenSecondPath ? secondPath[currentPathIndex] : path[currentPathIndex];
            moveCooldown();
        }
    }

    public void moveBack() {
        if (currentPosition > startPosition) {
            currentPathIndex--;
            currentPosition = chosenSecondPath ? secondPath[currentPathIndex] : path[currentPathIndex];
            moveCooldown();
        }
    }
}
