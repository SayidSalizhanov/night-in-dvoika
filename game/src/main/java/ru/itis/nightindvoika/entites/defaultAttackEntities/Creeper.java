package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.util.HashMap;

public class Creeper extends AttackEntity {
    public Creeper() {
        super("creeper", new int[]{2,4,3,11,14,15}, 25, false, true, false, false);

        positionsOnFrames = new HashMap<>();
        positionsOnFrames.put(2, PositionOnFrame.create(452, 762));
        positionsOnFrames.put(4, PositionOnFrame.create(588, 484));
        positionsOnFrames.put(3, PositionOnFrame.create(650, 585));
        positionsOnFrames.put(11, PositionOnFrame.create(1221, 459));
        positionsOnFrames.put(14, PositionOnFrame.create(1144, 312));
        positionsOnFrames.put(15, PositionOnFrame.create(884, 161));
    }
}
