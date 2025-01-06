package ru.itis.nightindvoika.entites.defaultAttackEntities;

import ru.itis.nightindvoika.entites.AttackEntity;

public class Zombie extends AttackEntity {
    public Zombie() {
        super(new int[]{1,5,6,12,11,14,15}, 20, false, true, true, true);
    }
}
