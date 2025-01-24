package ru.itis.nightindvoika.action.attacker;

import lombok.Setter;
import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.enums.AttackEntityEnum;
import ru.itis.nightindvoika.mainClasses.GameEngine;

@Setter
public class AttackEntityMoveAction implements Action {
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
        }
    }
}
