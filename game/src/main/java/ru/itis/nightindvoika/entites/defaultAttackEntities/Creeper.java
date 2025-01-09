package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.util.HashMap;

public class Creeper extends AttackEntity {
    public Creeper() {
        super(new int[]{2,4,3,11,14,15}, 25, false, true, false, false);

        positionsOnFrames = new HashMap<>();
        positionsOnFrames.put(2, PositionOnFrame.create(348, 586));
        positionsOnFrames.put(4, PositionOnFrame.create(452, 372));
        positionsOnFrames.put(3, PositionOnFrame.create(500, 450));
        positionsOnFrames.put(11, PositionOnFrame.create(939, 353));
        positionsOnFrames.put(14, PositionOnFrame.create(880, 240));
        positionsOnFrames.put(15, PositionOnFrame.create(680, 124));
    }
}
