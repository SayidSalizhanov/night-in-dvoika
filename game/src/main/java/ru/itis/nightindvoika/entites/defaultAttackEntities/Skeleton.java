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
        positionsOnFrames.put(1, PositionOnFrame.create(624, 65));
        positionsOnFrames.put(5, PositionOnFrame.create(463, 235));
        positionsOnFrames.put(7, PositionOnFrame.create(215, 304));
        positionsOnFrames.put(14, PositionOnFrame.create(906, 356));
        positionsOnFrames.put(13, PositionOnFrame.create(844, 304));
        positionsOnFrames.put(15, PositionOnFrame.create(915, 161));
    }

    public void moveCooldown() {
        stop(random.nextInt(10, 30));
    }
}
