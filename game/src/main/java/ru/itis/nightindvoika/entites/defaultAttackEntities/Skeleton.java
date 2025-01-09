package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.PositionOnFrame;
import ru.itis.nightindvoika.util.RandomSingleton;

import java.util.HashMap;
import java.util.Random;

public class Skeleton extends AttackEntity {
    private final Random random = RandomSingleton.getInstance();

    public Skeleton() {
        super(new int[]{1,5,7,14,13,15}, 20, true, false, true, true);

        positionsOnFrames = new HashMap<>();
        positionsOnFrames.put(1, PositionOnFrame.create(480, 50));
        positionsOnFrames.put(5, PositionOnFrame.create(356, 181));
        positionsOnFrames.put(7, PositionOnFrame.create(165, 234));
        positionsOnFrames.put(14, PositionOnFrame.create(697, 274));
        positionsOnFrames.put(13, PositionOnFrame.create(649, 234));
        positionsOnFrames.put(15, PositionOnFrame.create(704, 124));
    }

    public void moveCooldown() {
        stop(random.nextInt(10, 30));
    }
}
