package ru.itis.nightindvoika.action.attacker;

import lombok.Setter;
import ru.itis.nightindvoika.action.GameProcessAction;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.entites.defaultAttackEntities.WitherSkeleton;
import ru.itis.nightindvoika.enums.AttackEntityEnum;
import ru.itis.nightindvoika.mainClasses.GameEngine;

@Setter
public class AttackEntityMoveAction implements GameProcessAction {
    private String attackEntityKey;
    private AttackEntityEnum attackEntityEnum;

    @Override
    public void doSomeAction(GameEngine gameEngine) {
        AttackEntity attackEntity = gameEngine.getAttackEntities().get(attackEntityKey);

        switch (attackEntityEnum) {
            case AttackEntityEnum.FORWARD:
                attackEntity.moveForward();
                break;
            case AttackEntityEnum.BACK:
                attackEntity.moveBack();
                break;
            case AttackEntityEnum.WITHER_SKELETON_FIRST_PATH:
                ((WitherSkeleton) attackEntity).moveForward(false);
                break;
            case AttackEntityEnum.WITHER_SKELETON_SECOND_PATH:
                ((WitherSkeleton) attackEntity).moveForward(true);
                break;
        }
    }
}
