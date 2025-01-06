package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;

public class Creeper extends AttackEntity {
    public Creeper() {
        super(new int[]{2,4,3,11,14,15}, 25, false, true, false, false);
    }
}
