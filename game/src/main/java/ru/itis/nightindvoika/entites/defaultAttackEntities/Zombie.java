package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.util.HashMap;

public class Zombie extends AttackEntity {
    public Zombie() {
        super(new int[]{1,5,6,12,11,14,15}, 20, false, true, true, true);

        positionsOnFrames = new HashMap<>();
        positionsOnFrames.put(1, PositionOnFrame.create(494, 148));
        positionsOnFrames.put(5, PositionOnFrame.create(386, 110));
        positionsOnFrames.put(6, PositionOnFrame.create(346, 31));
        positionsOnFrames.put(12, PositionOnFrame.create(971, 110));
        positionsOnFrames.put(11, PositionOnFrame.create(937, 179));
        positionsOnFrames.put(14, PositionOnFrame.create(880, 185));
        positionsOnFrames.put(15, PositionOnFrame.create(691, 124));
    }
}
