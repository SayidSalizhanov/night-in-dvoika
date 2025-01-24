package ru.itis.nightindvoika.entites.defaultAttackEntities;

import lombok.Getter;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.PositionOnFrame;
import ru.itis.nightindvoika.util.RandomSingleton;

import java.util.HashMap;
import java.util.Random;

@Getter
public class WitherSkeleton extends AttackEntity {
    private final Random random = RandomSingleton.getInstance();

    private final int[] secondPath = {2,4,7,9,13,15};
    private boolean chosenSecondPath;

    public WitherSkeleton() {
        super("witherSkeleton", new int[]{2,4,7,10,14,15}, 15, true, false, true, true);
        chosenSecondPath = false;

        positionsOnFrames = new HashMap<>();
        positionsOnFrames.put(2, PositionOnFrame.create(450, 797));
        positionsOnFrames.put(4, PositionOnFrame.create(488, 458));
        positionsOnFrames.put(7, PositionOnFrame.create(317, 408));
        positionsOnFrames.put(10, PositionOnFrame.create(364, 620));
        positionsOnFrames.put(14, PositionOnFrame.create(1101, 277));
        positionsOnFrames.put(9, PositionOnFrame.create(181, 620));
        positionsOnFrames.put(13, PositionOnFrame.create(842, 391));
        positionsOnFrames.put(15, PositionOnFrame.create(868, 161));
    }

    public void moveForward() {
        if (currentPosition < endPosition) {
            if (currentPosition == 7) {
                chosenSecondPath = random.nextBoolean();
            }
            currentPathIndex++;
            currentPosition = chosenSecondPath ? secondPath[currentPathIndex] : path[currentPathIndex];
            moveCooldown();

            entityInOfficeCheck();
        }
    }

    public void moveForward(boolean chosenSecondPath) {
        this.chosenSecondPath = chosenSecondPath;

        if (currentPosition < endPosition) {
            currentPathIndex++;
            currentPosition = chosenSecondPath ? secondPath[currentPathIndex] : path[currentPathIndex];
            moveCooldown();

            entityInOfficeCheck();
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
