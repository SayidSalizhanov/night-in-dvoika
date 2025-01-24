package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.util.HashMap;

public class Zombie extends AttackEntity {
    public Zombie() {
        super("zombie", new int[]{1,5,6,12,11,14,15}, 20, false, true, true, true);

        positionsOnFrames = new HashMap<>();
        positionsOnFrames.put(1, PositionOnFrame.create(642, 192));
        positionsOnFrames.put(5, PositionOnFrame.create(502, 143));
        positionsOnFrames.put(6, PositionOnFrame.create(450, 40));
        positionsOnFrames.put(12, PositionOnFrame.create(1262, 143));
        positionsOnFrames.put(11, PositionOnFrame.create(1218, 233));
        positionsOnFrames.put(14, PositionOnFrame.create(1144, 241));
        positionsOnFrames.put(15, PositionOnFrame.create(898, 161));
    }
}
